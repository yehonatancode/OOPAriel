package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	@Override
	public int compare(Monom arg0, Monom arg1) {
		return arg1.get_power() - arg0.get_power();
	}

	// ******** add your code below *********
	

}
