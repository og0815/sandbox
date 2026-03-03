package com.example.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.entity.Property;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ImportService {

    private static final Logger L = LoggerFactory.getLogger(ImportService.class);

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    public static class RowDiff {

        private Long pk;
        private boolean missingReference = false;
        private Map<String, Object[]> changedFields = new LinkedHashMap<>();

        public Long getPk() {
            return pk;
        }

        public void setPk(Long pk) {
            this.pk = pk;
        }

        public boolean isMissingReference() {
            return missingReference;
        }

        public void setMissingReference(boolean missingReference) {
            this.missingReference = missingReference;
        }

        public Map<String, Object[]> getChangedFields() {
            return changedFields;
        }

        public void setChangedFields(Map<String, Object[]> changedFields) {
            this.changedFields = changedFields;
        }

    }

    public List<RowDiff> parseAndCompare(InputStream in) throws Exception {
        L.info("Starting Excel file parsing and comparison...");
        List<RowDiff> results = new ArrayList<>();

        try (XSSFWorkbook wb = new XSSFWorkbook(in)) {
            XSSFSheet sheet = wb.getSheetAt(0);
            L.debug("Excel sheet loaded: {}", sheet.getSheetName());

            Iterator<Row> rows = sheet.iterator();
            if (!rows.hasNext()) {
                L.warn("No rows found in Excel sheet");
                return results;
            }

            // Header parsing: map column index -> known field name
            Row headerRow = rows.next();
            Map<Integer, String> colToField = new HashMap<>();
            for (Cell headerCell : headerRow) {
                String header = headerCell.getStringCellValue().trim();
                switch (header) {
                    case "Liegenschaft: Bezeichnung":
                        colToField.put(headerCell.getColumnIndex(), "appelation");
                        break;
                    case "Liegenschaft: Liegenschaftstyp":
                        colToField.put(headerCell.getColumnIndex(), "objectGroup");
                        break;
                    case "Liegenschaft: Liegenschaftsnummer":
                        colToField.put(headerCell.getColumnIndex(), "PK_IDObject");
                        break;
                    case "Anschrift: Strasse und Hausnummer":
                        colToField.put(headerCell.getColumnIndex(), "street");
                        break;
                    case "Anschrift: Postleitzahl":
                        colToField.put(headerCell.getColumnIndex(), "zip");
                        break;
                    case "Anschrift: Ort":
                        colToField.put(headerCell.getColumnIndex(), "city");
                        break;
                    case "Anschrift: Breitengrad":
                        colToField.put(headerCell.getColumnIndex(), "latitude");
                        break;
                    case "Anschrift: Längengrad":
                        colToField.put(headerCell.getColumnIndex(), "longitude");
                        break;
                    case "Liegenschaft: (KSE) Stammdatenergänzung Liegenschaft: Verfügbare Linien":
                        colToField.put(headerCell.getColumnIndex(), "line");
                        break;
                    case "Liegenschaft: (KSE) Stammdatenergänzung Liegenschaft: Linie Bereich":
                        colToField.put(headerCell.getColumnIndex(), "lineSection");
                        break;
                    case "Liegenschaft: (KSE) Stammdatenergänzung Liegenschaft: Fahrzeit zum HBF [in Minuten]":
                        colToField.put(headerCell.getColumnIndex(), "driveTimeToCentralStation");
                        break;
                    case "Liegenschaft: (KSE) Stammdatenergänzung Liegenschaft: Gesamtstellplätze":
                        colToField.put(headerCell.getColumnIndex(), "capacityMax");
                        break;
                    case "Liegenschaft: (KSE) Stammdatenergänzung Liegenschaft: Behindertenstellplätze":
                        colToField.put(headerCell.getColumnIndex(), "capacityHandicapped");
                        break;
                    case "Liegenschaft: (KSE) Stammdatenergänzung Liegenschaft: Mietstellplätze":
                        colToField.put(headerCell.getColumnIndex(), "capacityRent");
                        break;
                    default:
                        break;
                }
            }
            L.debug("Parsed {} column mappings from header", colToField.size());

            int rowNumber = 0;
            while (rows.hasNext()) {
                rowNumber++;
                Row r = rows.next();
                Long pkVal = null;
                Map<String, Object> rowValues = new HashMap<>();

                for (Map.Entry<Integer, String> e : colToField.entrySet()) {
                    Cell cell = r.getCell(e.getKey());
                    String field = e.getValue();
                    if (cell == null) {
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case STRING:
                            var v = cell.getStringCellValue().trim();
                            L.info("Found Type String for Field:{} with Value:{}", field, v);
                            rowValues.put(field, v);
                            break;
                        case NUMERIC:
                            double d = cell.getNumericCellValue();
                            if ("PK_IDObject".equals(field) || "zip".equals(field) || field.startsWith("capacity") || "driveTimeToCentralStation".equals(field)) {
                                L.info("Found Type long for Field:{} with Value:{}", field, d);
                                rowValues.put(field, (long) d);
                            } else {
                                L.info("Found Type double for Field:{} with Value:{}", field, d);
                                rowValues.put(field, d);
                            }
                            break;
                        case BLANK:
                        default:
                            break;
                    }
                    if ("PK_IDObject".equals(field)) {
                        Object o = rowValues.get(field);
                        if (o instanceof Long) {
                            pkVal = (Long) o;
                        } else if (o instanceof Double) {
                            pkVal = ((Double) o).longValue();
                        } else if (o instanceof String) {
                            try {
                                pkVal = Long.parseLong((String) o);
                            } catch (Exception ex) {
                                pkVal = null;
                            }
                        }
                    }
                }

                ImportService.RowDiff diff = new ImportService.RowDiff();
                diff.pk = pkVal;

                Property existing = null;
                if (pkVal != null) {
                    List<Property> list = em.createQuery("SELECT p FROM Property p WHERE p.PK_IDObject = :pk", Property.class)
                            .setParameter("pk", pkVal)
                            .setMaxResults(1)
                            .getResultList();
                    if (!list.isEmpty()) {
                        existing = list.get(0);
                    }
                }

                if (existing == null) {
                    diff.missingReference = true;
                    results.add(diff);
                    continue;
                }

                // compare a subset of fields
                compareField(diff, "appelation", existing.getAppelation(), rowValues.get("appelation"));
                compareField(diff, "objectGroup", existing.getObjectGroup(), rowValues.get("objectGroup"));
                compareField(diff, "street", existing.getStreet(), rowValues.get("street"));
                compareField(diff, "zip", existing.getZip(), rowValues.get("zip"));
                compareField(diff, "city", existing.getCity(), rowValues.get("city"));
                compareField(diff, "latitude", existing.getLatitude(), rowValues.get("latitude"));
                compareField(diff, "longitude", existing.getLongitude(), rowValues.get("longitude"));
                compareField(diff, "line", existing.getLine(), rowValues.get("line"));
                compareField(diff, "lineSection", existing.getLineSection(), rowValues.get("lineSection"));
                compareField(diff, "driveTimeToCentralStation", existing.getDriveTimeToCentralStation(), rowValues.get("driveTimeToCentralStation"));
                compareField(diff, "capacityMax", existing.getCapacityMax(), rowValues.get("capacityMax"));
                compareField(diff, "capacityHandicapped", existing.getCapacityHandicapped(), rowValues.get("capacityHandicapped"));
                compareField(diff, "capacityRent", existing.getCapacityRent(), rowValues.get("capacityRent"));

                // if there are changes, apply them to the entity and merge
                if (!diff.changedFields.isEmpty()) {
                    // update fields from rowValues where present
                    if (rowValues.containsKey("appelation")) {
                        existing.setAppelation((String) rowValues.get("appelation"));
                    }
                    if (rowValues.containsKey("objectGroup")) {
                        existing.setObjectGroup((String) rowValues.get("objectGroup"));
                    }
                    if (rowValues.containsKey("street")) {
                        existing.setStreet((String) rowValues.get("street"));
                    }
                    if (rowValues.containsKey("zip")) {
                        existing.setZip(Integer.parseInt(rowValues.get("zip").toString()));
                    }
                    if (rowValues.containsKey("city")) {
                        existing.setCity((String) rowValues.get("city"));
                    }
                    if (rowValues.containsKey("latitude")) {
                        existing.setLatitude((Double) rowValues.get("latitude"));
                    }
                    if (rowValues.containsKey("longitude")) {
                        existing.setLongitude((Double) rowValues.get("longitude"));
                    }
                    if (rowValues.containsKey("line")) {
                        existing.setLine((String) rowValues.get("line"));
                    }
                    if (rowValues.containsKey("lineSection")) {
                        existing.setLineSection((String) rowValues.get("lineSection"));
                    }
                    if (rowValues.containsKey("driveTimeToCentralStation")) {
                        existing.setDriveTimeToCentralStation(((Long) rowValues.get("driveTimeToCentralStation")).intValue());
                    }
                    if (rowValues.containsKey("capacityMax")) {
                        existing.setCapacityMax(((Long) rowValues.get("capacityMax")).intValue());
                    }
                    if (rowValues.containsKey("capacityHandicapped")) {
                        existing.setCapacityHandicapped(((Long) rowValues.get("capacityHandicapped")).intValue());
                    }
                    if (rowValues.containsKey("capacityRent")) {
                        existing.setCapacityRent(((Long) rowValues.get("capacityRent")).intValue());
                    }

                    // entity is already managed after retrieval; changes will be flushed automatically
                    results.add(diff);
                }
            }
        }

        return results;
    }

    private void compareField(RowDiff diff, String fieldName, Object existingVal, Object incomingVal) {
        if (existingVal == null && incomingVal == null) {
            return;
        }
        if (existingVal == null && incomingVal != null) {
            diff.changedFields.put(fieldName, new Object[]{existingVal, incomingVal});
            return;
        }
        if (existingVal != null && incomingVal == null) {
            diff.changedFields.put(fieldName, new Object[]{existingVal, incomingVal});
            return;
        }
        // Normalize numbers
        if (existingVal instanceof Number && incomingVal instanceof Number) {
            double e = ((Number) existingVal).doubleValue();
            double i = ((Number) incomingVal).doubleValue();
            if (Double.compare(e, i) != 0) {
                diff.changedFields.put(fieldName, new Object[]{existingVal, incomingVal});
            }
            return;
        }
        if (!existingVal.equals(incomingVal)) {
            diff.changedFields.put(fieldName, new Object[]{existingVal, incomingVal});
        }
    }

    public List<Property> getAllProperties() {
        return em.createQuery("SELECT p FROM Property p ORDER BY p.id", Property.class)
                .getResultList();
    }

    public String generateImportMessage(List<RowDiff> results) {
        int successCount = 0;
        int problemCount = 0;
        List<String> details = new ArrayList<>();

        for (RowDiff diff : results) {
            if (diff.isMissingReference()) {
                problemCount++;
                if (diff.getPk() != null) {
                    details.add("- Liegenschaft mit Nummer " + diff.getPk() + " nicht in Datenbank gefunden");
                }
            } else if (!diff.getChangedFields().isEmpty()) {
                successCount++;
                if (diff.getPk() != null) {
                    for (Map.Entry<String, Object[]> change : diff.getChangedFields().entrySet()) {
                        String fieldName = change.getKey();
                        Object[] values = change.getValue();
                        String excelValue = values[1] != null ? values[1].toString() : "";
                        details.add("- Liegenschaft: " + diff.getPk() + " aktuallisert: Feld \"" + fieldName + "\": Wert \"" + excelValue + "\"");
                    }
                }
            }
        }

        // Generate first paragraph (summary)
        String firstParagraph;
        if (successCount == 0 && problemCount == 0) {
            firstParagraph = "Import war nicht nötig, es sind keine Änderungen gefunden wurden.";
        } else if (problemCount == 0) {
            firstParagraph = "Import war erfolgreich, es wurden " + successCount + " Datensätze aktuallisert.";
        } else if (successCount > 0) {
            firstParagraph = "Import war teilweise erfolgreich, es konnten " + successCount + " Datensätze aktuallisert werden und es gab " + problemCount + " Probleme.";
        } else {
            firstParagraph = "Import war nicht erfolgreich, es konnte kein Datensatz oder Feld zugeordnent werden.";
        }

        // Generate second paragraph (details)
        StringBuilder message = new StringBuilder(firstParagraph);
        if (!details.isEmpty()) {
            message.append("\n\n");
            for (String detail : details) {
                message.append(detail).append("\n");
            }
        }

        return message.toString();
    }
}
