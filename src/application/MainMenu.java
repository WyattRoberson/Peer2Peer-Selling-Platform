package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MainMenu {

	private Map<String, Account> accounts = new HashMap<>();//The Hash Map that contains all usernames and their associated account obj
	private Account accountAccessed = null;//Account that is currently logged in
	private String userOption = "";//User option in the maindriver
	private Boolean menuVisiblity = true;// ???
	private Boolean signedIn = false;//Boolean that corresponds to whether an account is signed in or not
	private Scanner sc = new Scanner(System.in);//recyclable scanner
	private Set<String> accKeys = accounts.keySet();//Account usernames of all the accounts
	//Constructor for MainMenu
	public MainMenu() throws FileNotFoundException {
		File inputFile = new File("Accounts.txt");
		Scanner in = new Scanner(inputFile);
		
		while (in.hasNext()) {
			String user = in.nextLine();
			String pass = in.nextLine();
			accounts.put(user, new Account(user, pass));
		}
		in.close();
		
		this.populateProductsInAccounts();
	}

	// Shows Main Menu Options
	public void showMainMenu() {
		// If the User Isn't Signed In, Ask them to Sign In
		if (!this.signedIn) {
			String accountOptions = "Sign In\nCreate Account\n";
			System.out.println(accountOptions);
		} 
		// If the User Is Signed In, Show the Main Menu Options
		else {
			String menuOptions = "Browse Products\nView History\nSell Products\nCheck Messages\nSign Out\nDelete Account";
			System.out.println(menuOptions);
		}
	}

	// Signs User Into Account
	public void signIn() throws FileNotFoundException {
		while (!this.signedIn) {
			System.out.print("Enter Username: ");
			String user = sc.nextLine();
			System.out.print("Enter Password: ");
			String pass = sc.nextLine();
			
			// Checks if given Username & Password Exist
			File inputFile = new File("Accounts.txt");
			Scanner in = new Scanner(inputFile);
			
			while (in.hasNext()) {
				String info = in.nextLine();
				// If Username & Password are Valid, Sign In
				if (user.equals(info)) {
					if (pass.equals(in.nextLine())) {
						this.accountAccessed = accounts.get(user);
						System.out.println("You have successfully logged in.\n");
						this.signedIn = true;
					}
				}
			}
			in.close();
			// If Username & Password Don't Exist, Ask User to Try Again
			if (!this.signedIn) {
				System.out.println("There is no account with this login information.\nPlease try again.\n");
			}
		}
	}

	// Creates a New Account
	public void createAccount(String user, String pass) throws IOException {
		// Writes Given Username & Password into "Accounts.txt" database
		while(accKeys.contains(user) || user.equals("all")) {
			System.out.print("This username is not available, please try again: ");
			user = sc.nextLine();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("Accounts.txt", true));
		writer.write(user);
		writer.newLine();
		writer.write(pass);
		writer.newLine();
		writer.close();
		Account newAccount = new Account(user, pass);
		accounts.put(user, newAccount);
	}
	
	public void populateProductsInAccounts() throws FileNotFoundException {
		File inputFile = new File("ProductsForSale.txt");
		Scanner in = new Scanner(inputFile);
		
		while(in.hasNextLine()) {
			String accountName = in.nextLine();
			String itemCategory = in.nextLine();
			String itemName = in.nextLine();
			int itemPrice = Integer.parseInt(in.nextLine());
			String itemDescription = in.nextLine();
			String timeUsed = in.nextLine();
			
			for(String key : accounts.keySet()) {
				if(key.equals(accountName)) {
					Account itemAccount = accounts.get(key);
					Product item = new Product(itemName, itemPrice, itemCategory, itemDescription, timeUsed, itemAccount);
					accounts.get(accountName).getProductsForSale().add(item);
				}
			}
		}
		in.close();
	}

	// Shows Available Products for Sale, by Category
	public void showProducts() throws FileNotFoundException {
		boolean finished = false;//Boolean to break out of the search loop 
		String option = "";//Option variable 
		while (finished == false && !option.equals("goback")) {//
			System.out.println("Would you like to search by [Category] or by [Item Name]?");
			option = sc.nextLine().toLowerCase().replaceAll("\\s", "");
			if (option.equals("goback")) {
				break;
			}
			if (option.equals("category")) {// If statement for checking category
				// Asks User to Enter a Category, and Shows Valid Categories
				System.out.print("Please Enter a Category: ");
				for (String c : accountAccessed.getCategories()) {// Prints the category options
					System.out.println(c);
				}
				// [User Input] : Product Category
				String cat = sc.nextLine();
				while (!accountAccessed.getCategories().contains(cat)) {// Loops to request reentering category if it is
																		// incorrect
					System.out.println("That category is invalid, try again!");
					cat = sc.nextLine();
				}
				if (cat.equals("goback")) {
					break;
				}
				for (String key : accKeys) {// Loop that prints all the items associated with a category
					LinkedList<Product> pfs = accounts.get(key).getProductsForSale();
					// If Product Category is Empty, Ask User to Enter Another Category
					if (pfs == null) {
						System.out.println("That Category is Empty!");
						System.out.print("Please Enter a Category: ");
						cat = sc.nextLine().toLowerCase().replaceAll("\\s", "");
						if (cat.equals("goback")) {
							break;
						}
					} else {
						// If Category Exists, Show Products from that Category
						for (Product p : pfs) {
							if (p.getCategory().contentEquals(cat)) {
								System.out.println(p);
							}
						}
					}
					if (cat.equals("goback")) {
						break;
					}
				}
			} else if (option.equals("itemname")) {// If the option chosen is "name" then requests a name to search
				System.out.print("Please Enter a Name: ");
				LinkedList<Product> searchResults = new LinkedList<>();
				String name = sc.nextLine();
				for (String key : accKeys) {
					LinkedList<Product> pfs = accounts.get(key).getProductsForSale();
					for (int i = 0; i < pfs.size(); i++) {
						if (pfs.get(i).getItemName().equals(name)) {
							searchResults.add(pfs.get(i));
						}
					}
				}
				if (searchResults.size() == 0) {
					System.out.print("This item does not exist. ");
				} else {
					for (Product p : searchResults) {
						System.out.print(p);
					}
				}

			} else {
				System.out.println("That was not a valid option!");
			}
			System.out.println("Would you like to purchase any of these items?");
			option = sc.nextLine();
			option = option.toLowerCase().replaceAll("\\s", "");
			while(!(option.equals("yes") || option.equals("no"))) {
				System.out.println("Please type yes or no.");
				option = sc.nextLine();
				option = option.toLowerCase().replaceAll("\\s", "");
			}
			if(option.equals("yes")) {
				System.out.println("Type the item ID of the item you'd like to purchase: ");
				int intOpt = Integer.parseInt(sc.nextLine());
				boolean foundItem = false;
				for(String key : accounts.keySet()) {
					LinkedList<Product> products = accounts.get(key).getProductsForSale();
					for(Product product : products) {
						if(product.getItemID() == intOpt) {
							foundItem = true;
							System.out.println("You have just bought " + product.getItemName() + " from " + product.getOwner().getName());
							System.out.println("How would you rate this seller out of 1-5: ");
							double doubleOpt = sc.nextDouble();
							product.getOwner().setRating(doubleOpt);
							removeFromMarket(product);
						}
					}
				}
				if(!foundItem) {
					System.out.println("Sorry, no item with this ID exists.");
				}
			}
			System.out.println("Would you like to search again?");
			option = sc.nextLine();
			option = option.toLowerCase().replaceAll("\\s", "");
			while (!(option.equals("yes") || option.equals("no"))) {
				System.out.println("Please type yes or no");
				option = sc.nextLine().toLowerCase().replaceAll("\\s", "");
			}
			if (option.equals("no")) {
				finished = true;
			}
		}
	}
	
	public void removeFromMarket(Product removedProduct) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("ProductsForSale.txt");
		for(String key : accounts.keySet()) {
			LinkedList<Product> products = accounts.get(key).getProductsForSale();
			for(Product product : products) {
				if(!(product.getItemID() == removedProduct.getItemID())) {
					out.println(product.getOwner().getName());
					out.println(product.getCategory());
					out.println(product.getItemName());
					out.println(product.getPrice());
					out.println(product.getDescription());
					out.println(product.getUsage());
					out.close();
				}
				else {
					products.remove(removedProduct);
				}
			}
		}
	}
	
	// Shows Recently Bought/Sold Products
	public void showHistoryMenu() {
		System.out.println("Product History\nEdit Product Details\nBack");

		while (getUserOption().equals("producthistory") || getUserOption().equals("editproductdetails") || (!(getUserOption().equals("back")))) {
			setUserOption(sc.nextLine());
			
			if (getUserOption().equals("producthistory")) {
				// TODO Product History
			}

			if (getUserOption().contentEquals("editproductdetails")) {
				// TODO Edit Product Details, if not sold yet
			}
		}
		System.out.println();
	}

	// Shows Menu for Selling a Product
	public void showSellProduct() throws IOException {
		this.accountAccessed.sellProduct(sc);
	}

	// Shows Menu for Sending and Viewing Messages
	public void showMessageMenu() {
		System.out.println();
		
		while (getUserOption().equals("sendmessage") || getUserOption().equals("viewmessages") || (!(getUserOption().equals("back")))) {
			System.out.println("Send Message\nView Messages\nBack");
			setUserOption(sc.nextLine());
			
			if (getUserOption().equals("sendmessage")) {
				accountAccessed.message(accounts, sc);
			}
				
			if (getUserOption().contentEquals("viewmessages")) {
				
			}
		}
		System.out.println();
	}

	// Signs Out of Currently Signed In Account
	public void signOut() {
		this.accountAccessed = null;
		this.signedIn = false;
	}

	// Deletes the Currently Signed In Account
	public void deleteAccount() throws FileNotFoundException {
		PrintWriter out = null;
		
		try {
			out = new PrintWriter("Accounts.txt");
			
			for (Map.Entry<String, Account> entry : accounts.entrySet()) {
				String user = entry.getValue().getName();
				String pass = entry.getValue().getPassword();
				
				if (!(user.equals(this.getAccountAccessed().getName())
						&& pass.equals(this.getAccountAccessed().getPassword()))) {
					out.println(user);
					out.println(pass);
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if (out != null) {
				out.close();
			}
		}
		out.close();
		// After Account is Deleted, Sign Out
		signOut();
	}

	// Returns Map of Created Accounts
	public Map<String, Account> getAccounts() {
		return accounts;
	}

	// Sets Map of Created Accounts
	public void setAccounts(Map<String, Account> accounts) {
		this.accounts = accounts;
	}

	// Returns Currently Signed In Account
	public Account getAccountAccessed() {
		return accountAccessed;
	}

	// Sets Currently Signed In Account
	public void setAccountAccessed(Account accountAccessed) {
		this.accountAccessed = accountAccessed;
	}

	// Returns Option User Entered - ("Sign In", "Sell Products", "Delete Account", ...)
	public String getUserOption() {
		return userOption;
	}

	// Sets Option User Entered - ("Sign In", "Sell Products", "Delete Account", ...)
	public void setUserOption(String userOption) {
		this.userOption = userOption.toLowerCase().replaceAll("\\s", "");
	}

	// Returns Whether Main Menu is Visible, or Not
	public Boolean getMenuVisiblity() {
		return menuVisiblity;
	}

	// Sets Whether Main Menu is Visible, or Not
	public void setMenuVisibility(Boolean menuVisible) {
		this.menuVisiblity = menuVisible;
	}

	// Returns Whether an Account is Signed In, or Not
	public Boolean getIsSignedIn() {
		return signedIn;
	}

	// Sets Whether an Account is Signed In, or Not
	public void setIsSignedIn(Boolean signedIn) {
		this.signedIn = signedIn;
	}

	public static void main(String[] args) throws IOException {
		MainMenu menu = new MainMenu();
		
		// If Menu Visibility is True, Show Main Menu
		while (menu.getMenuVisiblity()) {
			menu.showMainMenu();
			// [User Input] : User Option
			menu.setUserOption(menu.sc.nextLine());
			
			// Show Sign In Menu
			if (menu.getUserOption().equals("signin")) {
				menu.signIn();
			} 
			// Show Create Account Menu
			else if (menu.getUserOption().equals("createaccount")) {
				// [User Input] : New Account Username
				System.out.print("Create A Username: ");
				String newUser = menu.sc.nextLine();
				
				// If Username Already Exists, Ask User to Try Again
				while (menu.accKeys.contains(newUser)) {
					System.out.println("That username exsists! Try again!");
					newUser = menu.sc.nextLine();
				}
				// [User Input] : New Account Password
				System.out.print("Create A Password: ");
				String newPass = menu.sc.nextLine();
				
				// Creates a New Account with Given Username and Password
				menu.createAccount(newUser, newPass);
			}

			// If a User is Signed In, Show Peer-to-Peer Selling Options
			if (menu.getIsSignedIn()) {
				if (menu.getUserOption().equals("browseproducts")) {
					// Shows List of Products Currently Being Sold
					menu.showProducts();
				} else if (menu.getUserOption().equals("viewhistory")) {
					// Shows List of Products Current User has Previously Bought/Sold,
					// & Allows Editing of Current User's Products up For Sale
					menu.showHistoryMenu();
				} else if (menu.getUserOption().equals("sellproducts")) {
					// Shows Menu for Posting a New Product to Sell
					menu.showSellProduct();
				} else if (menu.getUserOption().equals("checkmessages")) {
					// Shows Menu for Checking & Replying to Messages
					menu.showMessageMenu();
				} else if (menu.getUserOption().equals("signout")) {
					// Signs Current Account Out, & Redirects to Sign In Page
					menu.signOut();
				} else if (menu.getUserOption().equals("deleteaccount")) {
					// Deletes Currently Signed In Account
					menu.deleteAccount();
				}
			}
		}
	}
}