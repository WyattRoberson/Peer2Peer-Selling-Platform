package application;

import java.util.LinkedList;

public class History {
	
	private LinkedList<Product> productSellHistory, productBuyHistory;
	
	public LinkedList<Product> getSellHistory() {
		// Returns Sell History
		return productSellHistory;
	}
	
	public void setSellHistory(Product item) {
		// Adds item to Sell History
		productSellHistory.add(item);
	}

	public LinkedList<Product> getBuyHistory() {
		// Returns Buy History
		return productBuyHistory;
	}
	
	public void setBuyHistory(Product item) {
		// Adds item to Buy History
		productBuyHistory.add(item);
	}
	
	public void setDetails(String itemName, int price, String category, String description, int usage, Account owner) {
		// Edit details of any product that has been posted and not sold yet
		Product.setItemName(itemName);
		Product.setPrice(price);
		Product.setCategory(category);
		Product.setDescription(description);
		Product.setUsage(usage);
		Product.setOwner(owner);
	}
}
