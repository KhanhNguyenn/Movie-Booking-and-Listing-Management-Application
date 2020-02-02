
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


/**
 * This represents a Customer with existing transactions in the Moblima system.
 * Each instance of Customer is defined by a unique combination of name, mobile number and email.
 * Each instance holds a list of transactions, which represent the customer's booking history.
 *
 * @author SS3 Group 6
 *
 */
public class Customer {
  private String name;
  private int mobile;
  private String email;
  private List <CustomerTransaction> history;

  /**
   * Constructor method, which creates a new Customer from a new transaction, by taking name, mobile and email.
   * In addition, this transaction is added as the first transaction in the customer's history.
   *
   * @param cust
   */
  public Customer(CustomerTransaction cust) {
    this.name = cust.getName();
    this.mobile = cust.getMobile();
    this.email = cust.getEmail();
    this.history = new ArrayList <CustomerTransaction>();
    history.add(cust);
  }

  /**
   * Getter method for name attribute.
   *
   * @return String of Customer's name
   */
  public String getName() {return name;}

  /**
   * Getter method for mobile attribute.
   *
   * @return int representing Customer's mobile number
   */
  public int getMobile() {return mobile;}

  /**
   * Getter method for email attribute.
   *
   * @return String of Customer's email.
   */
  public String getEmail() {return email;}

  /**
   * This method determines this Customer corresponds to the combination of name, mobile and email passed into the function
   *
   * @param name
   * @param mobile
   * @param email
   * @return boolean indicating whether the Customer corresponds to these details
   */
  public boolean sameCustomer(String name, int mobile, String email) {
    if (this.getName().equalsIgnoreCase(name)&&
        this.getMobile()==mobile&&
        this.getEmail().equalsIgnoreCase(email)) {return true;}
    else {return false;}
  }

  /**
   * This method adds a new transaction to the Customer's history
   *
   * @param cust
   */
  public void addTransaction(CustomerTransaction cust) {
    history.add(cust);
  }

  /**
   * This method displays the Customer's history.
   *
   * @return int representing number of existing transactions
   */
  public int showHistory() {
    List <MovieListing> showtimes = ListingManager.getShowtimes();
    for (int i=0; i<history.size(); i++) {
      CustomerTransaction curr = history.get(i);
      for (int j=0; j<showtimes.size(); j++) {
        MovieListing show = showtimes.get(j);
        if (show.sameListing(curr.getListingID())) {
          curr.showTransaction(show);
        }
      }
    }
    return history.size();
  }

  /**
   * This method obtains the full list of distinct movies booked by the Customer
   *
   * @return ArrayList of Strings, representing titles of movies booked
   */
  public List<String> getMovieList(){
    Set<String> movieList= new HashSet<String>();
    List<MovieListing> showtimes= ListingManager.getShowtimes();
    for(int i=0;i<history.size();i++){
      CustomerTransaction curr= history.get(i);
      for(int j=0;j<showtimes.size(); j++){
        MovieListing show = showtimes.get(j);
        if (show.sameListing(curr.getListingID())){
          movieList.add(show.getTitle());

        }
      }
    }
    return new ArrayList<>(movieList);
  }
}
