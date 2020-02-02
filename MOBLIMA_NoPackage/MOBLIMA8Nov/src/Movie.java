
import java.util.*;


/**
 * Each object of this class represents a Movie, and holds attributes such as
 * movieCode (which is a unique identifier), title, status, ageRating, synopsis, cast and director.
 *
 * Also, it holds a list Ratings which created by Customers who watch this movie,
 * through which it derives the value of avgRating
 *
 * @author SS3 Group 6
 *
 */
public class Movie {
	private char movieCode;
	private String title;
	private String status;
	private String ageRating;

	private String synopsis;
	private String cast;
	private String director;

	private double avgRating;

	private List <Rating> movieRatingList;

	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Constructor method, which initializes a Movie object based on the parameters passed in.
	 *
	 * @param movieCode
	 * @param title
	 * @param status
	 * @param rating
	 * @param synopsis
	 * @param cast
	 * @param director
	 */
	public Movie(char movieCode, String title, String status, String rating, String synopsis, String cast, String director) {
		this.movieCode = movieCode;
		this.title = title;
		this.status = status;
		this.ageRating = rating;
		this.synopsis = synopsis;
		this.cast = cast;
		this.director = director;

		this.movieRatingList = new ArrayList <Rating>();
		avgRating = this.calculateAvgRating();
	}

	/**
	 * Getter method for movieCode
	 *
	 * @return char which represents movieCode
	 */
	public char getCode() {return movieCode;}

	/**
	 * Getter method for Title
	 *
	 * @return String representing movie title
	 */
	public String getTitle() {return title;}

	/**
	 * Getter method for showing status
	 *
	 * @return String representing status
	 */
	public String getStatus() {return status;}

	/**
	 * Getter method for Age Rating
	 *
	 * @return String representing Age Rating
	 */
	public String getAgeRating() {return ageRating;}

	/**
	 * Getter method for Synopsis
	 *
	 * @return String representing synopsis
	 */
	public String getSynopsis() {return synopsis;}

	/**
	 * Getter method for movie cast
	 *
	 * @return String representing cast
	 */
	public String getCast() {return cast;}

	/**
	 * Getter method for director name
	 *
	 * @return String representing director name
	 */
	public String getDirector() {return director;}

	/**
	 * Getter method for avgRating attribute
	 *
	 * @return double representing avgRating
	 */
	public double getAvgRating() {
		return avgRating;
	}

	/**
	 * This method displays the most basic information about the Movie
	 */
	public void showMovie() {
		System.out.println("\n========================");
		System.out.println("("+movieCode+")");
		System.out.println("Title: "+title);
		System.out.println("Status: "+status);
	}

	/**
	 * This method displays the full details of the Movie
	 */
	public void displayMovieDetails() {
		System.out.println("\n========================");
		System.out.println("\n"+this.getTitle()+" ("+this.getAgeRating()+")");

		System.out.println("\n+============+");
		for (int i=0; i<10; i++) {
			if (i==4) {System.out.print("|   POSTER   |   ");}
			else{System.out.print("|            |   ");}

			switch(i) {
			case 2:
				double rating = getAvgRating();
				if (rating<0)  System.out.printf("RATING: Average rating unavailable (%d Reviews)\n", movieRatingList.size());
				else System.out.printf("RATING: %.1f (%d Reviews)\n", rating, movieRatingList.size());
				break;
			case 4:
				System.out.println("CAST: "+this.getCast());
				break;
			case 6:
				System.out.println("DIRECTOR: "+this.getDirector());
				break;
			case 8:
				System.out.println("");
				break;
			default:
				System.out.println();
				break;
			}
		}
		System.out.println("+============+");

		System.out.println("\nSYNOPSIS: \n\n"+this.getSynopsis());
	}

	/**
	 * This method shows all available Customer ratings and reviews for the Movie.
	 */
	public void showRatings() {
		int count = 0;

		System.out.println("\n\n======================================================================="
				+ "\n\n\t\t\t     REVIEWS:\n\n"
				+ "=======================================================================");
		for (int i = 0; i < movieRatingList.size(); i++) {
			Rating cur;
			cur = movieRatingList.get(i);

			System.out.println("\n" + "------------------------------ (" + (++count)+
					") ------------------------------------");
			cur.displayRating();
		}

		if (count == 0) {
			System.out.println("\nNo Reviews available");
		}
	}

	/**
	 * This method calculates the average rating of the movie,
	 * when there are more than 1 Customer ratings available
	 *
	 * @return double representing average rating
	 */
	public double calculateAvgRating() {
		double sum = 0;
		double count = 0;

		if (movieRatingList.size() <= 1) {return -1;}

		for (int i = 0; i < movieRatingList.size(); i++) {
			sum += movieRatingList.get(i).getRating();
			count++;
		}

		if (count == 0) {return -1;}
		else {return sum/count;}
	}

	/**
	 * This method adds a new Rating to the list of existing Ratings for the Movie
	 *
	 * @param x, which represents new Rating object
	 */
	public void addRating(Rating x) {
		this.movieRatingList.add(x);
		Collections.sort(this.movieRatingList);
		this.avgRating = this.calculateAvgRating();
	}

	/**
	 * This method changes an existing Rating, when a Customer updates his rating.
	 *
	 * @param oldRecord
	 * @param newRecord
	 */
	public void modifyRating(Rating oldRecord, Rating newRecord) {
		this.movieRatingList.remove(oldRecord);
		this.movieRatingList.add(newRecord);
		this.avgRating = this.calculateAvgRating();
	}

	/**
	 * This method changes the status of a movie.
	 * It can only be changed to End of Showing when there are no more existing listings.
	 *
	 * @param stillHasShows, which is a boolean indicating if there are still listings
	 */
	public void changeStatus(boolean stillHasShows) {
		System.out.println("\nChoose new showing status:\n");
		System.out.println("(1) Coming Soon\n(2) Preview\n(3) Now Showing\n(4) End of Showing");

		int choice = sc.inputInt(1, 4);

		switch (choice) {
		case 1:
			this.status = "Coming Soon";
			break;
		case 2:
			this.status = "Preview";
			break;
		case 3:
			this.status = "Now Showing";
			break;
		case 4:
			if (stillHasShows) {
				System.out.println("\nMovie still has existing listings! Cannot change to End of Showing.");
				return;
			}
			this.status = "End of Showing";
			break;
		default:
			break;
		}
		System.out.println("\nStatus successfully changed to: "+this.getStatus());
	}

	/**
	 * This methods checks if a movie is bookable, meaning status is Preview/Now Showing
	 *
	 * @return boolean indicating if movie is bookable
	 */
	public boolean bookable() {
		if (status.contentEquals("Now Showing")||status.contentEquals("Preview")) {
			return true;
		}
		else {return false;}
	}
}
