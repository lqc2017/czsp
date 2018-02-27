package czsp.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.user.dao.UserInfoDao;
import czsp.user.model.UserInfo;

@At("/user")
@IocBean
public class UserModule {

	@Inject
	private UserInfoDao userInfoDao;

	final Log log = Logs.getLog(MainSetup.class);

	@At("/showList")
	@Ok("jsp:/czsp/user/showList")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List users = userInfoDao.getList();
		Map deptsMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());

		map.put("users", users);
		map.put("departments", departments);
		return map;
	}

	@At("/create")
	@Ok(">>:/user/showList")
	public Map<String, Object> create(@Param("..") UserInfo user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.debug("new instanceId:" + userInfoDao.addUser(user));
		} catch (Exception e) {
		}
		return map;
	}

	@At("/ativate")
	@Ok("json")
	public Map<String, Object> ativate() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (Mvcs.getHttpSession().getAttribute("userInfo") == null) {
			UserInfo userInfo = userInfoDao.getUserInfoByUserId("100000");
			Mvcs.getHttpSession().setAttribute("userInfo", userInfo);
			map.put("result", "success");
		} else {
			map.put("result", "fail");
			map.put("message", "状态已激活");
		}
		return map;
	}

	@At("/clear")
	@Ok("json")
	public Map<String, Object> clear() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Mvcs.getHttpSession().removeAttribute("userInfo");
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", map.put("message", MessageUtil.getStackTraceInfo(e)));
		}
		return map;
	}

	@At("/change")
	@Ok("jsp:/czsp/user/selectUser")
	public Map<String, Object> change(@Param("..") UserInfo userInfo) {
		if (userInfo == null)
			userInfo = new UserInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		List users = userInfoDao.getListByCondition(userInfo);
		Map deptsMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());

		map.put("userInfo", userInfo);
		map.put("users", users);
		map.put("departments", departments);
		return map;
	}

	@At("/select")
	@Ok("json")
	public Map<String, Object> select(@Param("userId")String userId) {
		System.out.println(userId);
		Map<String, Object> map = new HashMap<String, Object>();
			UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);
			if(userInfo == null){
			map.put("result", "fail");
			map.put("message", "找不到该用户");
			}else{
				Mvcs.getHttpSession().setAttribute("userInfo",userInfo);
				map.put("result", "success");
			}
		return map;
	}

	public static String loginAuth(Map map) {
		UserInfo userInfo = (UserInfo) Mvcs.getHttpSession().getAttribute("userInfo");
		if (userInfo == null) {
			map.put("result", "fail");
			map.put("message", "没有用户信息！请先登录或激活。");
			return null;
		}
		return userInfo.getUserId();
	}
}
