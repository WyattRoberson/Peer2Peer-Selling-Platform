package application;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Account {
	

	private String name, password;//The account name and account password
	private double rating = 5;//Account rating
	private int numberOfRatings = 1;//Number of ratings the given account has 
	private LinkedList<Product> productsForSale = new LinkedList<Product>();//list of the products for sale
	private LinkedList<Product> productHistory = new LinkedList<Product>();//the products history of items sold and forsale currently
	private Map<String,LinkedList<Message>> messageHistory;//Message history and the account it is associated with
	private LinkedList<String> categories = new LinkedList<String> //The list of categories
	(Arrays.asList("Electronics", "Hobbies", "Vehicles", "Clothing & Accessories", "Home & Garden", "Rentals", "Entertainment", 
			"Classifieds", "Family", "Home Sales", "Deals"));

	
	// Account Constructor
	//@param: String name - Account UserNam, String password - Password to access the account
	public Account(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	// Asks user if they would like to Continue Selling or Return to Main Menu
	//@param: Scanner sc - this is the scanner passed from the MainMenu so a single 
	// scanner can continue to be used
	public void showOptions(Scanner sc) throws IOException {
		String userOption = "";
		System.out.println();
		userOption =userOption.toLowerCase().replaceAll("\\s", "");
		while (!(userOption.equals("sell") || userOption.equals("back"))) {
			System.out.println("Sell\nBack");
			userOption = sc.nextLine();
			userOption = userOption.toLowerCase().replaceAll("\\s", "");
			
			if (userOption.equals("sell")) {
				// Continue Selling
				sellProduct(sc);
			}
		}
	}
	
	// Shows Sell Product Menu
	//@param: Scanner sc - this is the scanner passed from the MainMenu so a single 
	// scanner can continue to be used
	public void sellProduct(Scanner sc) throws IOException {
		String itemName = "";
		String itemCategory = "null";
		String itemDescription = "";
		String timeUsed = "";
		String itemPrice = "";
		int SENTINEL = 0;
		
		// [User Input] : Product Name
		System.out.println("Item you're selling: ");
		itemName = sc.nextLine();
		// [User Input] : Product Description - (Only Accepts Pre-Defined Categories)
		while (SENTINEL == 0 && !itemName.toLowerCase().replaceAll("\\s", "").equals("goback")) {
			System.out.println("Pick a category below:\nElectronics\nHobbies\nVehicles\nClothing & Accessories\nHome & Garden\nRentals\nEntertainment\nClassifieds\nFamily\nHome Sales\nDeals");
			itemCategory = sc.nextLine();
			if(itemName.toLowerCase().replaceAll("\\s", "").equals("goback")) {break;}
			for (String actualCategory : categories) {
				if (actualCategory.equals(itemCategory)) {
					SENTINEL = 1;
				}
			}
			if (SENTINEL == 0) {
				System.out.println("\nThat is not a valid category.\nPlease try again.\n");
			}
		}
		// [User Input] : Product Description
		System.out.println("Write a description of your product: ");
		itemDescription = sc.nextLine();
		// [User Input] : Amount of time the Product has been Used
		System.out.println("How long have you used this item: ");
		timeUsed = sc.nextLine();
		// [User Input] : Product Price
		System.out.print("Price: $");
		itemPrice = sc.nextLine();
		int iPrice = Integer.parseInt(itemPrice);
		// Creates a new Product using User's Inputs
		Product product = new Product(itemName, iPrice, itemCategory, itemDescription, timeUsed, this);
		// Adds Product to the Public Market
		product.addToMarket();
		this.addProductForSale(product);//Updates the products for sale
		this.addProductHistory(product);//Updates the product history
		
		// Asks user if they would like to Continue Selling or Return to Main Menu
		showOptions(sc);
	}
	
	//@return: String name - Returns the Name of an Account
	public String getName() {
		return name;
	}
	
	// Sets the Name of an Account
	//@param: String name - name to be set 
	public void setName(String name) {
		this.name = name;
	}
	
	// Returns an Array of valid Product Categories
	//@return: String[] categories - Array of the categories

	public LinkedList<String> getCategories() {
		return categories;
	}
	
	// Returns the Password of an Account
	//@return: String password - password for the account
	public String getPassword() {
		return password;
	}
	
	// Sets the Password of an Account
	//@param: String password - new password for the account
	public void setPassword(String password) {
		this.password = password;
	}
	
	// Returns the Rating of an Account
	//@return: double rating - The rating of the user
	public double getRating() {
		return rating;
	}
	
	// Calculates and Sets the Rating of an Account
	//@param: double rating - the new additional rating
	public void setRating(double newRating) {
		this.numberOfRatings += 1;
		this.rating = (this.rating * (numberOfRatings - 1) + newRating) / numberOfRatings;
	}
	
	// Returns Products an Account has For Sale
	//@return: LinkedList<Product> productsForSale - linked list containing all the products this account has for sale
	public LinkedList<Product> getProductsForSale() {
		return productsForSale;
	}
	
	// Sets Products an Account has For Sale
	// @param: LinkedList<Product> productsForSale - 
	private void addProductForSale(Product p) {
		productsForSale.add(p);
	}
	
	// Returns Product History of an Account
	//@return: String prodHist - The string representation of the entire product history of this account
	public String getProductHistory() {
		String prodHistory = "";
		for(Product p : productHistory) {
			prodHistory = p.toString() + " ";
		}
		return prodHistory;
	}
	
	// Sets the Product History of an Account
	//@param: Product p - adds a product to the history
	private void addProductHistory(Product p) {
		productHistory.add(p);
	}
	//Sends a message to another account
	//@param: Map<String, Account> s - the Map of accounts to allow easy access to all accounts,
	//Scanner sc - scanner to be reused
	public void message(Map<String, Account> s, Scanner sc) {
		System.out.println("Who would you like to message?");
		Set<String> sKeys = s.keySet();
		String accToMsg = sc.nextLine();
		String msg = "";
		if (!accToMsg.toLowerCase().replaceAll("\\s", "").equals("goback")) {
			while (!sKeys.contains(accToMsg) && !accToMsg.toLowerCase().replaceAll("\\s", "").equals("goback")) {
				System.out.println("That account doesn't exsist, would you like a list of Account names?" + "\n"
						+ "type yes or no");
				accToMsg = sc.nextLine();
				if(accToMsg.toLowerCase().replaceAll("\\s", "").equals("goback")) {break;}
				if (accToMsg.toLowerCase().replaceAll("\\s", "").equals("yes")) {
					for (String n : sKeys) {
						System.out.println("Account Name: ");
						System.out.println(n);
					}
				}
				System.out.print("Please enter a user to message: ");
				accToMsg = sc.nextLine();
			}
			Account acc = s.get(accToMsg);
			System.out.println("What should the message say?");
			msg = sc.nextLine();
			if(!accToMsg.toLowerCase().replaceAll("\\s", "").equals("goback") && !msg.toLowerCase().replaceAll("\\s", "").equals("goback")) {
				
				Message m = new Message(msg, this, acc);
				LinkedList<Message> llM = messageHistory.get(acc.getName());
				llM.add(m);
				messageHistory.put(accToMsg, llM);
				acc.setMessageHistory(this, llM);
				s.put(accToMsg, acc);
			}
		}
	}

	//Needs Done!
//	public String getMessageHistory() {
//		Set<String> names = messageHistory.keySet();
//		System.out.print("Type the name of the user you wish view the messages of: ");
//		Scanner nameScanner = new Scanner(System.in);
//		String userName = nameScanner.nextLine();
//		while(!names.contains(userName) && !name.toLowerCase().replaceAll("\\s","").equals("goback")) {
//			System.out.println("User Name was Invalid, Please Try Again!");
//			System.out.print("Type the name of the user you wish view the messages of: ");
//			userName = nameScanner.nextLine();
//		}
//		for(Message m : MessageHi)
//	}
	
	//helper function to add a message to message history if recieving a message
	//@param: Account a - Account that is recieving the message, Message m - the actual message
	public void setMessageHistory(Account a, LinkedList<Message> m) {
		messageHistory.put(a.getName(),m);
	}
}
