package application;

public class Message {
	private String message;
	private Account fromUser, toUser;
	
	// Message Constructor
	public Message(String message, Account fromUser, Account toUser) {
		this.message = message.trim();
		this.fromUser = fromUser;
		this.toUser = toUser;
	}

	// Returns Message
	//@return: String outputString - the string representation of message
	// where the body of the message is no longer than 40 characters (including white space) 
	public String toString() {
		char[] messageArray = message.toCharArray();
		String msgString = "";
		String outputString = "-= MESSAGE =-" + "\n" + "------------------------------------------" + "\n" + "From: " + fromUser.getName()
		+ "\n" + "To: " + toUser.getName() +"\n";
		int count = 0;
		for(char c : messageArray) {
			if(count >= 40) {
				msgString = msgString + c + "\n";
				count = 0;
			} else {
				msgString = msgString + c;
				count++;
			}
		}
		return outputString = outputString + "\n" + msgString + "\n" + "------------------------------------------";
		
	}

	// Sets Message
	//@return: String message - message body
	public String getMessage() {
		return message;
	}

	// Returns Account Message is From
	//@return: Account fromUser - the user that 
	public Account getFromUser() {
		return fromUser;
	}

	

	// Returns Account Message is To 
	public Account getToUser() {
		return toUser;
	}
}
