package com.example.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.service.ImportService;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ImportBean implements Serializable {

    private static final Logger L = LoggerFactory.getLogger(ImportBean.class);

    @Inject
    private ImportService importService;

    private List<ImportService.RowDiff> diffs;
    private String importMessage;
    private String lastImportDateTime;
    private String lastImportFileName;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if (file == null) {
            L.warn("File upload event received but file is null");
            return;
        }
        L.info("File upload started: {}", file.getFileName());
        try (InputStream in = file.getInputStream()) {
            lastImportFileName = file.getFileName();
            lastImportDateTime = LocalDateTime.now().format(DATE_FORMATTER);
            diffs = importService.parseAndCompare(in);
            L.info("File {} processed successfully. Found {} diffs.", file.getFileName(), diffs.size());
            importMessage = importService.generateImportMessage(diffs);
            L.info("Import message:\n{}", importMessage);
        } catch (Exception ex) {
            L.error("Error processing file {}", file.getFileName(), ex);
            lastImportFileName = file.getFileName();
            lastImportDateTime = LocalDateTime.now().format(DATE_FORMATTER);
            importMessage = "Import fehlgeschlagen: " + ex.getMessage();
        }
    }

    public List<ImportService.RowDiff> getDiffs() { 
        L.info("Diffs: {}", diffs);
        return diffs;
    }

    public String getImportMessage() {
        return importMessage;
    }

    public String getLastImportDateTime() {
        return lastImportDateTime;
    }

    public String getLastImportFileName() {
        return lastImportFileName;
    }
}
