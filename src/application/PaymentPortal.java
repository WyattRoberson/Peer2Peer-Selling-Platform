package application;

public interface PaymentPortal {
	
	String paymentInvoice = "";
	
	public static void purchaseProduct() {
		// Purchases product and creates an Invoice
		
	}
	
	public static String showInvoice() {
		// Returns Invoice
		return paymentInvoice;
	}
}
