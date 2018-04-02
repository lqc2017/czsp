package czsp.common.util;

import java.util.Map;

import org.nutz.mvc.Mvcs;

import czsp.user.model.UserInfo;

public class SessionUtil {
	/**
	 * 全琛 2018年3月27日 用户登录判断
	 */
	public static String loginAuth(Map map) {
		UserInfo userInfo = (UserInfo) Mvcs.getHttpSession().getAttribute("userInfo");
		if (userInfo == null) {
			map.put("result", "fail");
			map.put("message", "没有用户信息！请先登录或激活。");
			return null;
		}
		return userInfo.getUserId();
	}

	/**
	 * 全琛 2018年3月27日 获取当前用户id
	 */
	public static String getCurrenUserId() {
		UserInfo userInfo = (UserInfo) Mvcs.getHttpSession().getAttribute("userInfo");
		return userInfo == null ? null : userInfo.getUserId();
	}

	/**
	 * 全琛 2018年3月21日 获得当前用户对象
	 */
	public static UserInfo getCurrenUser() {
		UserInfo userInfo = (UserInfo) Mvcs.getHttpSession().getAttribute("userInfo");
		return userInfo == null ? null : userInfo;
	}
}
