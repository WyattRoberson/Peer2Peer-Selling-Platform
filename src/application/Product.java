package application;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Product {
	private int price;//Price of the product
	private String itemName, description, category, usage;//Item's name, description, the category it's under, and its usage
	private Account owner;//The account that owns this product
	private static int iD = 0;//Static variable to change the id each time a new product is created
	private int itemID;//The unique item id of the item
	
	//Product Constructor
	//@param: String itemName - The name of the item, int price - the price, String category - the category of the item, 
	//String description - the description of the item, String usage - the usage of the item, Account owner - the current owner of the item
	public Product(String itemName, int price, String category, String description, String usage, Account owner) {
		iD++;
		this.itemID= iD;
		this.itemName = itemName;
		this.price = price;
		this.category = category;
		this.description = description;
		this.usage = usage;
		this.owner = owner;
	}
	
	//Add Product to "ProductsForSale.txt" database
	public void addToMarket() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("ProductsForSale.txt", true));
		writer.write(owner.getName());
		writer.newLine();
		writer.write(category);
		writer.newLine();
		writer.write(itemName);
		writer.newLine();
		writer.write(String.valueOf(price));
		writer.newLine();
		writer.write(description);
		writer.newLine();
		writer.write(usage);
		writer.newLine();
		writer.close();
	}

	// Returns Product as a String
	//@return: String prodString - the string representation of the product
	public String toString() {
		String prodString = "---------------------------------------"+ "\n"+ "Item Name: " + itemName + "\n" + "Item Price: " + price + "\n" + "Item ID: " + itemID
				+ "\n" + "Item Description: " + description + "\n" + "Catergory: " + category + "\n" + "Usage: " + usage
				+ "\n" + "Owner: " + owner.getName() + "\n" + "Owner Rating: " + owner.getRating() + "\n" + "---------------------------------------\n";
		return prodString;
	}

	// Returns Price of Product
	//@return: int price - the price of the item
	public int getPrice() {
		return price;
	}
	
	// Sets Price of Product
	//@param: int price - the price of the item
	public void setPrice(int price) {
		this.price = price;
	}
	
	// Returns Usage of Product
	//@return: int usage - the usage of the item
	public String getUsage() {
		return usage;
	}
	
	// Sets Usage of Product
	//@param: String usage - the usage of the item
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	// Returns Name of Product
	//@return: String itemName - the item name of the product
	public String getItemName() {
		return itemName;
	}
	
	// Sets Name of Product
	//@param: String itemName - the new item name of the product
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	// Returns Item ID of Product
	//@return: int itemID - the id of the item
	public int getItemID() {
		return itemID;
	}
	
	// Returns Description of Product
	//@return: String description - the description of the item
	public String getDescription() {
		return description;
	}
	
	// Sets Description of Product
	//@param: String description - the description of the item
	public void setDescription(String description) {
		this.description = description;
	}
	
	// Returns Category of Product
	//@return: String category - the category this item is under
	public String getCategory() {
		return category;
	}
	
	// Sets Category of Product
	//@param: String category - category the item is under
	public void setCategory(String category) {
		this.category = category;
	}
	
	// Returns Account that Owns the Product
	//@return: Account owner - the account owner of this product
	public Account getOwner() {
		return owner;
	}
	
	// Sets Account that Owns the Product
	//@param: Account owner - changes the owner of the product
	public void setOwner(Account owner) {
		this.owner = owner;
	}
}
