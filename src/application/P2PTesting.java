package application;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class P2PTesting {

	private Account account = new Account("Username", "Password");
	private Map<String, Account> accounts = new HashMap<>();
	private String userOption = "Sign In";
	
	//MainMenu class test that checks the getAccounts method to see if a map of created accounts can be returned
	@Test
	public void getAccountstest() throws FileNotFoundException {
		MainMenu getAccountsTest = new MainMenu();
		accounts.put("Username", this.account);
		Map<String, Account> getAccountsOutput = getAccountsTest.getAccounts();
		getAccountsOutput.put("Username",this.account);
		assertEquals(accounts.get(this.account),getAccountsOutput.get(this.account));
	}
	
	//Tests the MainMenu class to see if the getUserOption method can return a user option
	@Test
	public void getUserOptionTest() throws FileNotFoundException {
		MainMenu getUserOptionTest = new MainMenu();
		String getUserOptionOutput = getUserOptionTest.getUserOption();
		getUserOptionOutput += "Sign In";
		assertEquals(userOption,getUserOptionOutput);
	}
	
	//Test for the Product class to see if its getPrice method will return the price
	@Test
	public void getPriceTest() throws IOException {
		Product getPriceTest = new Product("itemName", 15, "category", "description", "usage", account);
		int getPriceOutput = getPriceTest.getPrice();
		assertEquals(15,getPriceOutput);
	}
	
	//A test for the Product class that checks the getUsage method to see if it will return the usage for the product
	@Test
	public void getUsageTest() throws IOException {
		Product getUsageTest = new Product("itemName", 15, "category", "description", "usage", account);
		String getUsageOutput = getUsageTest.getUsage();
		assertEquals("usage",getUsageOutput);
	}
	
	//Tests the Product class to see if the getItemName method will return the name of the item
	@Test
	public void getItemNameTest() throws IOException {
		Product getItemNameTest = new Product("itemName", 15, "category", "description", "usage", account);
		String getItemNameOutput = getItemNameTest.getItemName();
		assertEquals("itemName",getItemNameOutput);
	}
	
	//A Product class test which checks the getCategory method to see if it will return the category of an item
	@Test
	public void getCategoryTest() throws IOException {
		Product getCategoryTest = new Product("itemName", 15, "category", "description", "usage", account);
		String getCategoryOutput = getCategoryTest.getCategory();
		assertEquals("category",getCategoryOutput);
	}
	
	//Product class test to test the getOwner method and check whether it can return the owner of an item
	@Test
	public void getOwnerTest() throws IOException {
		Product getOwnerTest = new Product("itemName", 15, "category", "description", "usage", account);
		Account getOwnerOutput = getOwnerTest.getOwner();
		assertEquals(account,getOwnerOutput);
	}
	
	//An Account class test which tests the getName method and returns the name associated with a account
	@Test
	public void getNameTest() {
		Account getNameTest = new Account("name", "password");
		String getNameOutput = getNameTest.getName();
		assertEquals("name",getNameOutput);
	}
	
	//A Account class test that test the getPassword method and checks if it return a account password
	@Test
	public void getPasswordTest() {
		Account getPasswordTest = new Account("name", "password");
		String getPasswordOutput = getPasswordTest.getPassword();
		assertEquals("password",getPasswordOutput);
	}
	
	//A message class test which tests the getMessage to see if it will return a message
	@Test
	public void getMessageTest() {
		Message getMessageTest = new Message("message", account, account);
		String getMessageOutput = getMessageTest.getMessage();
		assertEquals("message",getMessageOutput);
	}
	
	//Tests the message class to see if the getFromUser method returns the user who sent the message
	@Test
	public void getfromUserTest() {
		Message getfromUserTest = new Message("message", account, account);
		Account getfromUserOutput = getfromUserTest.getFromUser();
		assertEquals(account,getfromUserOutput);
	}
	
	//This test tests the message class to check if its getToUser method returns the user who the message is going to
	@Test
	public void getToUserTest() {
		Message getToUserTest = new Message("message", account, account);
		Account getToUserOutput = getToUserTest.getToUser();
		assertEquals(account,getToUserOutput);
	}
}