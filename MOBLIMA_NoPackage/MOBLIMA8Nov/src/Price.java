
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
* Price class hold object for price systems and operations on price system.
*
* @author SS3-grp6
*/
public class Price {
	protected static final String PRICE_FILENAME = "Prices";
	private static double basePrice;
	private static double markUpIMAX;
	private static double ageDiscount;
	private static double markUpSpecialDay;
	private static double markUp3D;

	private static ArrayList <Double> priceList = new ArrayList <Double>();
	private static final MoblimaScanner sc = new MoblimaScanner();

	/**
	 * Construct Price with initializing price system
	 */
	public Price() {
		try{
			priceList = DataStore.readPrices(PRICE_FILENAME);
			basePrice = priceList.get(0);

			markUpIMAX = priceList.get(1);

			ageDiscount = priceList.get(2);

			markUpSpecialDay = priceList.get(3);

			markUp3D = priceList.get(4);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}


	}

	/**
	 * Get base price.
	 *
	 * @return base price
	 */
	public static double getBasePrice() {
		return basePrice;
	}
	/**
	 * Set base price.
	 *
	 * @param newBasePrice
	 */
	public static void setBasePrice(double newBasePrice) {
		basePrice = newBasePrice;
	}

	/**
	 * Get markupIMAX(additional price for imax cinema)
	 *
	 * @return markupIMAX
	 */
	public static double getMarkUpIMAX() {
		return markUpIMAX;
	}
	/**
	 * Set markupIMAX (addition price for imax cinema).
	 *
	 * @param markUpIMAX
	 */
	public static void setMarkUpIMAX(double markUpIMAX) {
		Price.markUpIMAX = markUpIMAX;
	}

	/**
	 * Get discount for age.
	 *
	 * @return ageDiscount
	 */
	public static double getAgeDiscount() {
		return ageDiscount;
	}
	/**
	 * Set age discount.
	 *
	 * @param ageDiscount
	 */
	public static void setAgeDiscount(double ageDiscount) {
		Price.ageDiscount = ageDiscount;
	}

	/**
	 * Get additional price for special date showtime.
	 *
	 * @return additional price for special date
	 */
	public static double getMarkUpSpecialDay() {
		return markUpSpecialDay;
	}
	/**
	 * Set additional price for special date
	 * @param newSpecialDayRate
	 */
	public static void setMarkUpSpecialDay(double newSpecialDayRate) {
		Price.markUpSpecialDay = newSpecialDayRate;
	}

	/**
	 * Get additional price for 3D movie.
	 *
	 * @return additional price for 3D movie
	 */
	public static double getMarkUp3D() {return markUp3D; }
	/**
	 * Set additional price for 3D movie.
	 *
	 * @param markUp3D
	 */
	public static void setMarkUp3D(double markUp3D) {Price.markUp3D = markUp3D;}

	/**
	 * Update price system and save back to file.
	 */
	public static void updatePrice() {
		int choice = 0;
		while (true)
		{
			try{
				System.out.println("\nUpdate Prices:\n");
				System.out.println("(1) Update base price");

				System.out.println("(2) Update IMAX Mark Up");

				System.out.println("(3) Update Youth/Elderly Discount");

				System.out.println("(4) Update Weekend/Holiday Mark Up");

				System.out.println("(5) Update 3D Mark Up");

				choice = sc.inputInt(1, 6);

				switch(choice)
				{
					case 1:
						System.out.printf("\nThe current base price is $%.2f\n", getBasePrice());
						System.out.print("Please enter the new base price: ");
						double newBasePrice = sc.nextDouble();
						setBasePrice(newBasePrice);
						System.out.printf("\nThe base price has been changed to $%.2f\n", getBasePrice());

						priceList.clear();
						priceList.add(basePrice);
						priceList.add(markUpIMAX);
						priceList.add(ageDiscount);
						priceList.add(markUpSpecialDay);
						priceList.add(markUp3D);

						DataStore.savePrices(PRICE_FILENAME, priceList);
						break;

					case 2:
						System.out.printf("\nThe IMAX Premium is $%.2f\n", getMarkUpIMAX());
						System.out.print("Please enter the new IMAX Premium: ");
						double newIMAXRate = sc.nextDouble();
						setMarkUpIMAX(newIMAXRate);
						System.out.printf("\nThe IMAX Premium has been changed to $%.2f\n", getMarkUpIMAX());

						priceList.clear();
						priceList.add(basePrice);
						priceList.add(markUpIMAX);
						priceList.add(ageDiscount);
						priceList.add(markUpSpecialDay);
						priceList.add(markUp3D);

						DataStore.savePrices(PRICE_FILENAME, priceList);
						break;

					case 3:
						System.out.printf("\nThe current Youth/Elderly Discount is $%.2f\n", getAgeDiscount());
						System.out.print("Please enter the new Youth/Elderly Discount: ");
						double newAgeDiscount = sc.nextDouble();
						setAgeDiscount(newAgeDiscount);
						System.out.printf("\nThe Youth/Elderly Discount Rate has been changed to $%.2f\n", getAgeDiscount());

						priceList.clear();
						priceList.add(basePrice);
						priceList.add(markUpIMAX);
						priceList.add(ageDiscount);
						priceList.add(markUpSpecialDay);
						priceList.add(markUp3D);

						DataStore.savePrices(PRICE_FILENAME, priceList);
						break;

					case 4:
						System.out.printf("\nThe current Weekend/Holiday Premium is $%.2f\n", getMarkUpSpecialDay());
						System.out.print("Please enter the new Weekend/Holiday Premium: ");
						double newSpecialDayRate = sc.nextDouble();
						setMarkUpSpecialDay(newSpecialDayRate);
						System.out.printf("\nThe Weekend/Holiday Premium has been changed to $%.2f\n", getMarkUpSpecialDay());

						priceList.clear();
						priceList.add(basePrice);
						priceList.add(markUpIMAX);
						priceList.add(ageDiscount);
						priceList.add(markUpSpecialDay);
						priceList.add(markUp3D);

						DataStore.savePrices(PRICE_FILENAME, priceList);
						break;

					case 5:
						System.out.printf("\nThe current 3D Premium is $%.2f\n", getMarkUp3D());
						System.out.print("Please enter the new 3D Premium: ");
						double new3DRate = sc.nextDouble();
						setMarkUp3D(new3DRate);
						System.out.printf("\nThe 3D Premium has been changed to $%.2f\n", getMarkUp3D());

						priceList.clear();
						priceList.add(basePrice);
						priceList.add(markUpIMAX);
						priceList.add(ageDiscount);
						priceList.add(markUpSpecialDay);
						priceList.add(markUp3D);

						DataStore.savePrices(PRICE_FILENAME, priceList);
						break;
					default:
						return;
				}
			}
			catch (Exception e)
			{
				System.out.println("\nPlease ensure proper input.");
				return;
			}
		}
	}
}
