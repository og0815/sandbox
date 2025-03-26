/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xrechnung.support;

/**
 *
 * @author oliver.guenther
 */
public enum DocumentNameCode {
    
    Order(220),
    OrderChange(230),
    OrderResponse(231),
    PartialConstructionInvoice(875),
    PartialFinalConstructionInvoice(876),
    FinalConstructionInvoice(877),
    PartialInvoice(326),
    CommercialInvoice(380),
    CorrectedInvoice(384),
    SelfbilledInvoice(389),
    CreditNote(381),
    CommissionNote(382),
    // used for AdditionalSupportingDocs:
    ProductSpecification(6), // Order-X-No: 	82
    ValidatedPricedTender(50), // BT-17 "Price/sales catalogue response" / Ausschreibung oder das Los
    InvoicingDataSheet(130), // AdditionalSupportingDoc is BT-18 / Rechnungsdatenblatt
    RelatedDocument(916);	// AdditionalSupportingDoc is BG-24 / Related document / Referenzpapier

    /**
     * @see <A HREF="http://www.unece.org/trade/untdid/d13b/tred/tred1001.htm">UN/EDIFACT 1001</A>
     */
    public static final String SCHEME_ID = "UN/EDIFACT 1001";

    DocumentNameCode(int value) {
        this.value = value;
    }

    private final int value;

    int getValue() {
        return value;
    }

    public String getValueAsString() {
        return "" + value;
    }

}
