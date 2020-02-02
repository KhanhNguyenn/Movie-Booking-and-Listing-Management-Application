
import java.util.*;

/**
 * Rating class hold for a rating record.
 * It contains anem, mobile, email, title,
 * rating, review, date_time.
 *
 * @author SS3-grp6
 *
 */
public class Rating implements Comparable<Rating> {
	private String name;
	private int mobile;
	private String email;

	private String title;
	private int rating;
	private String review;
	private String date_time;

	/**
	 * Construct Rating object with initial name, mobile, email, movie title, rating, review, date_time).
	 *
	 * @param name
	 * @param mobile
	 * @param email
	 * @param title
	 * @param rating
	 * @param review
	 * @param date_time
	 */
	public Rating(String name, int mobile, String email, String title, int rating, String review, String date_time) {
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.title = title;
		this.rating = rating;
		this.review = review;
		this.date_time = date_time;
	}

	/**
	 * Get name of customer rating
	 *
	 * @return name
	 */
	public String getName() {return this.name;}
	/**
	 * Get mobile of customer rating
	 *
	 * @return mobile
	 */
	public int getMobile() {return this.mobile;}
	/**
	 * Get email of customer rating
	 *
	 * @return email
	 */
	public String getEmail() {return this.email;}
	/**
	 * Get title of movie of this rating
	 *
	 * @return title
	 */
	public String getTitle() {return this.title;}
	/**
	 * Get rating of movie
	 *
	 * @return rating
	 */
	public int getRating() {return this.rating;}
	/**
	 * Get reviewe of movie.
	 *
	 * @return review
	 */
	public String getReview() {return this.review;}
	/**
	 * Get date_time of this rating
	 *
	 * @return date_time
	 */
	public String getDateTime() {return this.date_time;}

	/**
	 * Set, modify rating of this record
	 *
	 * @param rating
	 */
	public void setRate(int rating) {this.rating = rating;}
	/**
	 * Set, modify review of this record
	 *
	 * @param review
	 */
	public void setReview(String review) {this.review = review;}
	/**
	 * Set, modify date_time of this record
	 *
	 * @param date_time
	 */
	public void setDateTime(String date_time) {this.date_time = date_time;}

	/**
	 * Display information of rating consisting of name, date_time, rating and review.
	 */
	public void displayRating() {
		System.out.printf("\n%S",this.getName());
		System.out.printf("\n%s\t\t\t%s",this.getDateTime(),(this.getRating()+" stars\n"));
		System.out.println("\n  \"" + this.getReview()+"\"");

	}

	/**
	 * Override compareTo() in Comparable to sort movies by sorting and then date_time of ratings.
	 *
	 */
	@Override
	public int compareTo(Rating other) {
		if (this.getTitle().compareTo(other.getTitle()) > 0) {return 1;}
		else if (this.getTitle().compareTo(other.getTitle()) == 0) {
			if (this.getRating() < other.getRating()) {return 1;}
			else if (this.getRating() == other.getRating()){
				if (this.getDateTime().compareTo(other.getDateTime())>0) {return 1;}
				else {return -1;}
			}
			else {return -1;}
		}
		else {return 1;}
	}
}
