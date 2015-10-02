package ar.edu.itba.it.paw.util;

public class DateUtils {

	
	public static boolean isDate(int day, int month, int year){
		int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
		if((year%4==0 && year%100!=0 || year%400==0))
				days[1]++;
		if(month > 0 && day>days[month-1])
			return false;
		return true;
	}
}
