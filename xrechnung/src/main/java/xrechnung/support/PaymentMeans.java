package xrechnung.support;

import java.util.HashMap;
import java.util.Map;

/* 
 * United Nations Trade Data Interchange Directory (UNTDID), http://www.unece.org/fileadmin/DAM/trade/untdid/d16b/tred/tredi2.htm
 * UN/EDIFACT 4461  Payment means code

	TypeCode Code für die Zahlungsart  
	Datentyp: qdt:PaymentMeansCodeType  
	Beschreibung: Das als Code ausgedrückte erwartete oder genutzte Zahlungsmittel  
	Hinweis: Die Einträge aus der UNTDID 4461 Codeliste müssen verwendet werden 
	Es sollte zwischen SEPA- und Nicht- SEPA-Zahlungen unterschieden werden sowie zwischen 
	 Kreditzahlungen, Lastschriften, Kartenzahlungen und anderen Zahlungsmitteln 
	Synonym: Zahlungstyp 
	Kardinalität: 1 .. 1  
	EN16931-ID: BT-81  
	Anwendung: Insbesondere können folgende Codes verwendet werden: 

      10    In cash / Bargeld
              Payment by currency (including bills and coins) in circulation, including checking account deposits.

      20    Cheque / Scheck
              Payment by a pre-printed form on which instructions are given to an account holder (a bank or building society)
              to pay a stated sum to a named recipient.

      30    Credit transfer / Überweisung
              Payment by credit movement of funds from one account to another.
              
      31    Debit transfer
              Payment by debit movement of funds from one account to another.

      42    Payment to bank account
              Payment by an arrangement for settling debts that is operated by the Post Office.

      48    Bank card / Kartenzahlung
              Payment by means of a card issued by a bank or other financial institution.

      49    Direct debit / Lastschrift
              The amount is to be, or has been, directly debited to the customer's bank account.

+     57    Standing agreement / Dauerauftrag
              The payment means have been previously agreed between seller and buyer and thus are not stated again.

+     58    SEPA credit transfer / SCT
              Credit transfer inside the Single Euro Payment Area (SEPA) system.

+     59    SEPA direct debit / SDD
              Direct debit inside the Single Euro Payment Area (SEPA) system.

      97    Clearing between partners
              Amounts which two partners owe to each other to be compensated in order to avoid useless payments.

 */
public enum PaymentMeans {

	InstrumentNotDefined 	(1),
	InCash 				(10),
	Cheque				(20),
	CreditTransfer 		(30),
	DebitTransfer 		(31),
	PaymentToBankAccount 	(42),
	BankCard 			(48),
	DirectDebit 		(49),
	StandingAgreement 	(57),
	SEPACreditTransfer 	(58),
	SEPADirectDebit 	(59),
	ClearingBetweenPartners (97);
	
	/**
	 * @see <A HREF="http://www.unece.org/trade/untdid/d13b/tred/tred4461.htm">UN/EDIFACT 4461</A>
	 */
	public static final String SCHEME_ID  = "UN/EDIFACT 4461";

	PaymentMeans(int value) {
		this.value = value;
	}
	
	private final int value;
	
	int getValue() {
		return value;
	}

	public String getValueAsString() {
		return ""+value;
	}

    private static Map<Integer, PaymentMeans> map = new HashMap<Integer, PaymentMeans>();
    static {
        for (PaymentMeans documentNameCode : PaymentMeans.values()) {
            map.put(documentNameCode.value, documentNameCode);
        }
    }
    
    public static PaymentMeans valueOf(int code) {
        return map.get(code);
    }

    public static boolean isCreditTransfer(PaymentMeans code) {
		if(code==null) return false;
		return(code==DebitTransfer
			|| code==CreditTransfer 
			|| code==SEPACreditTransfer);
	}

    public static boolean isBankCard(PaymentMeans code) {
		if(code==null) return false;
		return (code==BankCard);
	}
	
    public static boolean isDirectDebit(PaymentMeans code) {
		if(code==null) return false;
		return(code==DirectDebit
			|| code==SEPADirectDebit);
	}

}
