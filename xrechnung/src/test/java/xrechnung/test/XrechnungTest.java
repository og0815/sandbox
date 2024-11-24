package xrechnung.test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.Month;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.jupiter.api.Test;
import xrechnung.support.DocumentNameCode;
import xrechnung.support.PaymentMeans;
import xrechnung.ubl.BranchType;
import xrechnung.ubl.BuyerReferenceType;
import xrechnung.ubl.CbcNameType;
import xrechnung.ubl.CustomizationIDType;
import xrechnung.ubl.FinancialAccountType;
import xrechnung.ubl.IDType;
import xrechnung.ubl.InstructionNoteType;
import xrechnung.ubl.InvoiceType;
import xrechnung.ubl.InvoiceTypeCodeType;
import xrechnung.ubl.IssueDateType;
import xrechnung.ubl.NoteType;
import xrechnung.ubl.OrderReferenceType;
import xrechnung.ubl.PaymentIDType;
import xrechnung.ubl.PaymentMeansCodeType;
import xrechnung.ubl.PaymentMeansType;
import xrechnung.ubl.SalesOrderIDType;

/**
 *
 * @author oliver.guenther
 */
public class XrechnungTest {

    public static final String PROFILE_XRECHNUNG = "urn:cen.eu:en16931:2017#compliant#urn:xoev-de:kosit:standard:xrechnung_2.0";

    // Todo: add XMlRootElement über xjb
    // https://stackoverflow.com/questions/819720/no-xmlrootelement-generated-by-jaxb
    // Namespaceprefix mapper
    // validation des outputs
    // pdf merge
    
    @Test
    public void xrechnung() {
        InvoiceType invoiceType = new InvoiceType();

        CustomizationIDType customizationId = new CustomizationIDType();
        customizationId.setValue(PROFILE_XRECHNUNG);
        invoiceType.setCustomizationID(customizationId);
        InvoiceTypeCodeType typeCode = new InvoiceTypeCodeType();
        typeCode.setValue(DocumentNameCode.CommercialInvoice.getValueAsString());
        invoiceType.setInvoiceTypeCode(typeCode);

        IDType idType = new IDType();
        idType.setValue("123456XX");
        invoiceType.setID(idType);

        IssueDateType issueDate = new IssueDateType();
        LocalDateTime ldt = LocalDateTime.of(2024, Month.MARCH, 1, 10, 0);
        XMLGregorianCalendar cal = null;
        try {
            cal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        cal.setYear(ldt.getYear());
        cal.setMonth(ldt.getMonthValue());
        cal.setDay(ldt.getDayOfMonth());
        cal.setHour(ldt.getHour());
        cal.setMinute(ldt.getMinute());
        cal.setSecond(ldt.getSecond());
        issueDate.setValue(cal);

        NoteType noteType = new NoteType();
        noteType.setValue("Es gelten unsere Allgem. Geschäftsbedingungen, die Sie unter […] finden.");
        invoiceType.getNote().add(noteType);

        SalesOrderIDType salesOrderID = new SalesOrderIDType();
        salesOrderID.setValue("1234567890");   // optional

        OrderReferenceType orderReferenceType = new OrderReferenceType();
        orderReferenceType.setSalesOrderID(salesOrderID);
        invoiceType.setOrderReference(orderReferenceType);

        BuyerReferenceType buyerReference = new BuyerReferenceType();
        buyerReference.setValue("04011000-12345-34");
        invoiceType.setBuyerReference(buyerReference);

        FinancialAccountType financialAccountType = new FinancialAccountType();

        IDType idType2 = new IDType();
        idType2.setValue("NL03 INGB 0004489902"); // IBAN
        financialAccountType.setID(idType2);

        CbcNameType nameType = new CbcNameType();
        nameType.setValue("Ing Bank"); // Bank Name
        financialAccountType.setName(nameType);

        BranchType branchType = new BranchType();
        IDType idType3 = new IDType();
        idType3.setSchemeID("BIC"); // Konstante
        idType3.setValue("INGBNL2AXXX"); // BIC
        branchType.setID(idType3);

        financialAccountType.setFinancialInstitutionBranch(branchType);

        PaymentMeansType paymentMeansType = new PaymentMeansType();
        PaymentMeansCodeType paymentMeansCodeValue = new PaymentMeansCodeType();
        paymentMeansCodeValue.setValue(PaymentMeans.InCash.getValueAsString());
        paymentMeansType.setPaymentMeansCode(paymentMeansCodeValue);
 
        InstructionNoteType instructionNoteType = new InstructionNoteType();
        instructionNoteType.setValue("Text zur Zahlungsanweisung");
        paymentMeansType.getInstructionNote().add(instructionNoteType);

        PaymentIDType paymentIDtype = new PaymentIDType();
        paymentIDtype.setValue("Verwendungszeweck");
        paymentMeansType.getPaymentID().add(paymentIDtype);

        paymentMeansType.setPayerFinancialAccount(financialAccountType);
        invoiceType.getPaymentMeans().add(paymentMeansType);

        try {
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(InvoiceType.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(invoiceType, sw);
            System.out.println(sw.toString());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
