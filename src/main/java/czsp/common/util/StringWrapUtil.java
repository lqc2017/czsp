package czsp.common.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringWrapUtil {
	public static List<String> getSQLParamList(String str, String prefix, String suffix) {
		if (prefix == null)
			prefix = "";
		if (suffix == null)
			suffix = "";
		String[] strArr = str.split(",");
		System.out.println(strArr.toString());
		for (String s : strArr) {
			s = "'" + prefix + s + suffix + "'";
		}
		System.out.println("测试:" + StringUtils.join(strArr, ","));
		return Arrays.asList(strArr);
	}

}
