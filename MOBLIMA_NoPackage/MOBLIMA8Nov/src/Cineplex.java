/**
 * Object of type Cinepex hold list of cinemas it contains.
 *
 * @author SS3-grp6
 *
 */
public class Cineplex {

	public Cinema[] theatres;

	/**
	 * Construct cinemplex with initial number of cinemas in eah type:
	 *  imax, XL, L, M, S.
	 *
	 * @param imax
	 * @param XL
	 * @param L
	 * @param M
	 * @param S
	 */
	public Cineplex(int imax, int XL, int L, int M, int S) {
		int numCinemas = imax + XL + L + M + S;
		theatres = new Cinema[numCinemas];

		for (int i = 0; i<imax; i++) {
			theatres[i] = new ImaxCinema();
		}
		for (int i=imax; i<imax+XL; i++) {
			theatres[i] = new XLcinema();
		}

		for (int i=imax+XL; i<imax+XL+L; i++) {
			theatres[i] = new LCinema();
		}

		for (int i=imax+XL+L; i<numCinemas-S; i++) {
			theatres[i] = new MCinema();
		}

		for (int i=numCinemas-S; i<numCinemas; i++) {
			theatres[i] = new SCinema();
		}
	}

}
