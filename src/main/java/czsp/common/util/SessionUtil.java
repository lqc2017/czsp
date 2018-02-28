package czsp.common.util;

import java.util.Map;

import org.nutz.mvc.Mvcs;

import czsp.user.model.UserInfo;

public class SessionUtil {
	public static String loginAuth(Map map) {
		UserInfo userInfo = (UserInfo) Mvcs.getHttpSession().getAttribute("userInfo");
		if (userInfo == null) {
			map.put("result", "fail");
			map.put("message", "没有用户信息！请先登录或激活。");
			return null;
		}
		return userInfo.getUserId();
	}

	public static String getCurrenUserId() {
		UserInfo userInfo = (UserInfo) Mvcs.getHttpSession().getAttribute("userInfo");
		return userInfo == null ? null : userInfo.getUserId();
	}
}
