package czsp.common.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringWrapUtil {
	/**
	 * 全琛 2018年3月1日 根据数据库的字段值重新包装成in语句的查询条件
	 * 
	 * @param str
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @return
	 */
	public static String getSQLParamList(String str, String prefix, String suffix) {
		if (prefix == null)
			prefix = "";
		if (suffix == null)
			suffix = "";
		String[] strArr = str.split(",");
		for (String s : strArr) {
			s = "'" + prefix + s + suffix + "'";
		}
		return StringUtils.join(strArr, ",");
	}

	/**
	 * 全琛 2018年3月1日 根据List中的字符串重新包装成in语句的查询条件
	 * 
	 * @param strs
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String getSQLParamList(List<String> strs, String prefix, String suffix) {
		if (prefix == null)
			prefix = "";
		if (suffix == null)
			suffix = "";
		for (String s : strs) {
			s = "'" + prefix + s + suffix + "'";
		}
		return StringUtils.join(strs, ",");
	}

}
