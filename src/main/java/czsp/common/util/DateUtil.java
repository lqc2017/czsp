package czsp.common.util;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DateUtil {
	/**
	 * 全琛 2018年3月24日
	 * 
	 * @param scope
	 *            范围，当前年份+-scope
	 * @return
	 */
	public static List<String> getYearList(int scope) {
		// 大于100不做返回
		if (scope > 100)
			return null;
		LinkedList<String> yearList = new LinkedList<String>();
		Calendar cld = Calendar.getInstance();
		int curYear = cld.get(Calendar.YEAR);
		yearList.add("" + curYear);
		for (int i = 1; i <= scope; i++) {
			yearList.addFirst("" + (curYear - i));
			yearList.addLast("" + (curYear + i));
		}
		return yearList;
	}

	/**
	 * 全琛 2018年3月24日
	 * 
	 * @param foward
	 *            当前年份向前
	 * @param back
	 *            当前年份向后
	 * @return
	 */
	public static List<String> getYearList(int back, int foward) {
		LinkedList<String> yearList = new LinkedList<String>();
		Calendar cld = Calendar.getInstance();
		int curYear = cld.get(Calendar.YEAR);
		yearList.add("" + curYear);
		for (int i = 1; i <= back; i++) {
			yearList.addLast("" + (curYear + i));
		}
		for (int i = 1; i <= foward; i++) {
			yearList.addLast("" + (curYear - i));
		}
		return yearList;
	}
}
