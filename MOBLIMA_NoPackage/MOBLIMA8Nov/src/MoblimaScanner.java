
import java.util.Scanner;

/**
 * The class is to handle all IOs from users
 *
 * @author SS3-grp6
 *
 */
 public class MoblimaScanner {
 	private static final Scanner sc = new Scanner(System.in);

 	/**
 	 * Construct MoblimaScanner
 	 */
 	public MoblimaScanner() {
 	}

 	/**
 	 * Get input choice from list of choices start from (first) to (last)
 	 *
 	 * @param first -start choice
 	 * @param last  -last choice
 	 * @return choice
 	 * @throws Exception for invalid choice
 	 */
 	public int inputInt(int first, int last) {
 		System.out.print("\nYour selection (Input -1 to go BACK/EXIT): ");
 		try {
 			int sel = nextInt();

 			if (sel == -1) {
 				return sel;
 			}
 			if (sel < first || sel > last) {
 				throw new Exception();
 			} else {
 				return sel;
 			}
 		} catch (Exception e) {
 			System.out.println("Invalid Input!");
 			sc.nextLine();
 			return inputInt(first, last);
 		}
 	}

 	/**
 	 * Get movie code from input.
 	 *
 	 * @return movieCode
 	 * @throws Exception for invalid input
 	 */
 	public int inputMovieCode() {
 		System.out.print("\nPlease select a Film (Input '-1' to go back): ");
 		String input = next();
 		if (input.contentEquals("-1")) {
 			return -1;
 		}

 		try {
 			if (input.length() > 1) {
 				throw new Exception();
 			}
 			char sel = Character.toUpperCase(input.charAt(0));

 			for (int i = 0; i < MovieManager.getCatalogue().size(); i++) {
 				Movie cur = MovieManager.getCatalogue().get(i);
 				if (cur.getCode() == sel && cur.bookable()) {
 					return i;
 				}
 			}
 			throw new Exception();
 		} catch (Exception e) {
 			System.out.println("Invalid Input!");
 			return inputMovieCode();
 		}
 	}

 	/**
 	 * Get seat selection from input (e.g 'A7')
 	 *
 	 * @return arr[] -arr[0]:row, arr[1]:column
 	 * @throws Exception for invalid input
 	 */
 	public int[] inputSeat() throws Exception {
 		System.out.print("Your Selection (E.g. A3, B7): ");

 		// try {
 		String sel = next();
 		sel = sel.toUpperCase();
 		if (sel.length() > 3) {
 			throw new Exception();
 		}

 		int[] result = new int[2];
 		result[0] = sel.charAt(0) - 'A';
 		result[1] = Integer.parseInt(sel.substring(1));

 		return result;

 		/*
 		 * } catch(Exception e) { System.out.println("Invalid Input!\n"); return
 		 * inputSeat(); }
 		 *
 		 */

 	}

 	/**
 	 * Get mobile number from input.
 	 *
 	 * @return mobileNumber
 	 * @throws Exception for invalid input
 	 */
 	public int inputMobileNum() {
 		System.out.print("\nPlease enter your mobile phone number (Input -1 to exit): ");
 		try {
 			String mobile = next();
 			nextLine();
 			if (mobile.contentEquals("-1")) {
 				return -1;
 			}

 			if (mobile.length() != 8) {
 				throw new Exception();
 			} else {
 				return Integer.parseInt(mobile);
 			}
 		} catch (Exception e) {
 			System.out.print("Invalid number! Try again.\n");
 			return inputMobileNum();
 		}
 	}

 	/**
 	 * Get an integer from input.
 	 *
 	 * @return integer number
 	 */
 	public int nextInt() {
 		return sc.nextInt();
 	}

 	/**
 	 * Get a doule from input.
 	 *
 	 * @return double number
 	 */
 	public double nextDouble() {
 		return sc.nextDouble();
 	}

 	/**
 	 * Get a character from input.
 	 *
 	 * @return character
 	 */
 	public char nextChar() {
 		return Character.toUpperCase(next().charAt(0));
 	}

 	/**
 	 * Get a string from input.
 	 *
 	 * @return string
 	 */
 	public String next() {
 		return sc.next();
 	}

 	/**
 	 * Get a line of input.
 	 *
 	 * @return line of input
 	 */
 	public String nextLine() {
 		return sc.nextLine();
 	}
 }
