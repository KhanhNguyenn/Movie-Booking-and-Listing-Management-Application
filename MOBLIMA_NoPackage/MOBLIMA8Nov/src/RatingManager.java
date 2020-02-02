
import java.util.*;
import java.text.SimpleDateFormat;



/**
 * RatingManager manages operations on list of rating records.
 * It support add, modify and display ratings in order.
 * @author SS3-grp6
 *
 */
 public class RatingManager {
	protected static List<Rating> ratingList;
	private final String RATING_FILENAME = "Ratings";
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Construct RatingManager() with initializing list of current Ratings
	 * in sorted order
	 */
	public RatingManager() {
		ratingList = new ArrayList<Rating>();

		try {
			List<Rating> al = DataStore.readRating(RATING_FILENAME);
			for (int i = 0; i < al.size(); i++) {
				Rating cur = al.get(i);
				ratingList.add(cur);
				Movie target = MovieManager.findMovie(cur.getTitle());
				target.addRating(cur);
			}
		} catch (Exception e) {
			System.out.println("IOException > " + e.getMessage());
		}
		Collections.sort(ratingList);
	}

	/**
	 * Main method for giving ratings.
	 * Customers enter their information so that they can only choose movies
	 * they have rated to rate or review. If customer has already rated a movie,
	 * the new rating, review will modify the ole one of that user.
	 *
	 * @param Xbooking -bookingManager
	 */
	// main method for giving rating
	public void rate(BookingManager Xbooking) {

		// Collect customer name
		System.out.print("\nName (Input -1 to exit): ");
		sc.nextLine();
		String name = sc.nextLine();
		if (name.contentEquals("-1")) {return;}

		// Collect customer mobile
		int mobile = sc.inputMobileNum();
		if (mobile == -1) {return;}

		// Collect customer email
		System.out.print("\nEmail (Input -1 to exit): ");
		String email = sc.nextLine();
		if (email.contentEquals("-1")) {return;}

		System.out.println("=================================");
		System.out.println("\nMovies user watched: \n");
		Customer curUser = Xbooking.Xuser.findUser(name, mobile, email);

		if (curUser == null) {
			System.out.println("No records found. Users need to watch a movie before rating it!");
			return;
		}

		List<String> movieList = curUser.getMovieList();
		Map<Integer, String> movieChoice = new HashMap<>();
		for (int i = 0; i < movieList.size(); i++) {
			String titleTemp = movieList.get(i);
			int choice = i + 1;
			movieChoice.put(choice, titleTemp);
			System.out.println(choice + ". " + titleTemp);
		}

		System.out.println("\n===============================");
		System.out.println("\nEnter the movie to rate and review. ");
		int choice = sc.inputInt(1, movieList.size());
		if (choice == -1) {return;}

		String title = movieChoice.get(choice);

		System.out.println("\nEnter your rating (0 to 5)");
		int rating = sc.inputInt(0, 5);
		if(rating == -1) {return;}

		System.out.print("\nEnter your review: ");
		sc.nextLine();
		String review = sc.nextLine();

		try {
			this.add(name, mobile, email, title, rating, review);
			System.out.println("\nYour review has been recorded. Thank you!");
		} catch (Exception e) {
			System.out.println("\nError in processing review, due to invalid input");
			e.printStackTrace();
		}
	}

	/**
	 * Find rating of a customer to a specific movie title.
	 *
	 * @param name
	 * @param mobile
	 * @param email
	 * @param title
	 * @return Rating record if exists, otherwise null
	 */
	private Rating findPreviousRating(String name, int mobile, String email, String title) {
		for (int i = 0; i < ratingList.size(); i++) {
			Rating cur = ratingList.get(i);
			if (cur.getName().equals(name) && cur.getMobile() == mobile && cur.getEmail().equals(email)
					&& cur.getTitle().equals(title)) {
				return cur;
			}
		}
		return null;
	}

	/**
	 * Add Rating record of a customer to specific movie title.<br>
	 * Check whether if exists rating record of customer to movie. If not, add new
	 * rating record, otherwise override the old one.
	 *
	 * @param name
	 * @param mobile
	 * @param email
	 * @param title
	 * @param rating
	 * @param review
	 * @throws Exception
	 */
	public void add(String name, int mobile, String email, String title, int rating, String review) throws Exception {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String date_time = formatter.format(date);
		Rating target = findPreviousRating(name, mobile, email, title);
		Rating newRating = new Rating(name, mobile, email, title, rating, review, date_time);
		// if no user record

		if (target == null) {
			addNewRating(newRating, title);
		} else {// if exists override the old one
			modifyRating(target, newRating, title);
		}

		Collections.sort(ratingList);
		DataStore.saveRating(RATING_FILENAME, ratingList);
	}

	/**
	 * Add new record to Rating list.
	 *
	 * @param newRecord
	 * @param title
	 */
	public void addNewRating(Rating newRecord, String title) {
		ratingList.add(newRecord);
		Movie target = MovieManager.findMovie(title);
		target.addRating(newRecord);
		return;
	}

	/**
	 * Modify old rating record with new one.
	 *
	 * @param oldRecord
	 * @param newRecord
	 * @param title
	 */
	public void modifyRating(Rating oldRecord, Rating newRecord, String title) {
		Movie target = MovieManager.findMovie(newRecord.getTitle());
		ratingList.remove(oldRecord);
		ratingList.add(newRecord);
		target.modifyRating(oldRecord, newRecord);
		return;
	}

	/**
	 * List of movie and its rating in the order of average rating from highest to lowest.
	 *
	 */
	public static void listByRatings(){
		List<Movie> movieList = new ArrayList(MovieManager.catalogue);
		Map<String,Double> avgRatings = new HashMap<>();
		List<Double> ratings = new ArrayList<Double>();

		for (int i = 0; i < movieList.size(); i++) {
			Movie cur = movieList.get(i);
			avgRatings.put(cur.getTitle(), cur.getAvgRating());
			ratings.add(cur.getAvgRating());
	 	}
		Collections.sort(ratings);

		String title;

		System.out.println("\n========================");
		System.out.println("\nMovies from highest to lowest ratings: \n");

		for (int i = 1; i<=5; i++) {
			double max = ratings.get(ratings.size()-i);
			for (int j = 0; j < movieList.size(); j++) {
				title = movieList.get(j).getTitle();
				if (avgRatings.get(title)==max) {
					if (max == -1) {
						movieList.remove(j);
					}
					else{
						System.out.printf("("+i+") %s : %.1f\n", title, max);
						System.out.println();
						movieList.remove(j);
						break;
					}

				}
			}
		}
	}
}
