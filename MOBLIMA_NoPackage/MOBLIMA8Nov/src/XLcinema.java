
/**
 * XLcinema is a subclass of cinema, with 26 rows and 3 sections.
 *
 * @author SS3 Group 6
 *
 */
public class XLcinema extends Cinema{

	/**
	 * Constructor for XLcinema, which uses superclass constructor, with specified XL size and layout, and records cinema width.
	 */
	public XLcinema(){
		super(3,26);

		for (int i = 0; i<rows; i++) {
			for (int j = 0; j<sections; j++) {
				if (i<rows-2) {
					if (j==0||j==2) {
						layout[i][j] = new NormalRow(6);
					}
					else if (j==1){
						layout[i][j] = new NormalRow(12);
					}
				}
				else {
					if (j==0||j==2) {
						layout[i][j] = new CoupleRow(6);
					}
					else if (j==1){
						layout[i][j] = new CoupleRow(12);
					}
				}
			}
		}

		this.width = 0;
		for (int i=0; i<sections; i++) {
			this.width += layout[0][i].getWidth();
		}
	}

	/**
	 * Check whether the cinema is premium type.
	 *
	 * @return false;
	 */

	public boolean isPremium(){
		return false;
	}
}
