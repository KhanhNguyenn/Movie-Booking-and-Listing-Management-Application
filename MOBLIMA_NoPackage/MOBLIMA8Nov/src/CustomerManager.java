
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * CustomerManager holds the list of Customers, and manages their transactions.
 *
 * @author SS3 Group 6
 *
 */
public class CustomerManager {
	protected static List <Customer> userList;

	/**
	 * Constructor method takes in the full list of existing transactions, and assigns transactions to their corresponding users, using the update function.
	 *
	 * @param bookings
	 */
	public CustomerManager(List <CustomerTransaction> bookings) {
		userList = new ArrayList <Customer>();
		for (int i=0; i<bookings.size(); i++) {
			CustomerTransaction curr = bookings.get(i);
			this.update(curr);
		}
	}

	/**
	 * The update method checks if a Customer exists, and if so, adds the transaction to the Customer's transaction history.
	 * If the Customer does not exist, it creates a new Customer, and adds the transaction to the new Customer's history.
	 *
	 * @param cust
	 */
	public void update(CustomerTransaction cust) {
		Customer target = findUser(cust.getName(),cust.getMobile(), cust.getEmail());
		if (target != null) {addCustomerTransactions(target,cust);}
		else {createCustomer(cust);}
	}

	/**
	 * This method searches the list of existing Customers to see if the Customer, defined by name, mobile and email, already exists.
	 *
	 * @param name
	 * @param mobile
	 * @param email
	 *
	 * @return Customer object if it exists, and null if the Customer does not exist.
	 */
	public Customer findUser(String name, int mobile, String email) {
		for (int i=0; i<userList.size(); i++) {
			Customer curr = userList.get(i);
			if(curr.sameCustomer(name, mobile, email)){
				return curr;
			}
		}
		return null;
	}

	/**
	 * This method creates a new Customer (when a booking is made and Customer does not exist)
	 * and adds it into the list of existing Customers.
	 *
	 * @param cust
	 */
	public void createCustomer(CustomerTransaction cust) {
		userList.add(new Customer(cust));
	}

	/**
	 * This method adds a new transaction to an existing Customer's history.
	 *
	 * @param target
	 * @param cust
	 */
	public void addCustomerTransactions(Customer target, CustomerTransaction cust) {
		target.addTransaction(cust);
	}

	/**
	 * This method displays a Customer's history, if the name, mobile number and email input correspond to an existing Customer.
	 */
	public void customerCheckBooking() {
		Scanner sc = new Scanner(System.in);

		//Collect customer name
		System.out.println("\nName: ");
		String name = sc.nextLine();

		//Collect customer mobile
		System.out.println("\nMobile Number: ");
		int mobile;
		while(true) {
			try {
				String mobileStr = sc.next();
				if (mobileStr.length() != 8) {
					throw new Exception();
				}
				else {
					mobile = Integer.parseInt(mobileStr);
					break;
				}
			}
			catch (Exception e) {
				System.out.print("Invalid number! Please enter again: ");
			}
		}

		//Collect customer email
		System.out.println("\nEmail: ");
		String email = sc.next();

		//find customer bookings
		Customer target = findUser(name,mobile,email);
		if (target != null) {
			int count = target.showHistory();
			System.out.println("\n==========================");
			System.out.println("\nTotal Records: "+count+"\n");
		}
		else {
			System.out.println("\n==========================");
			System.out.println("\nNo Records Found\n");
		}
	}
}
