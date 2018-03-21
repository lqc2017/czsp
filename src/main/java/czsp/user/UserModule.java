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
import czsp.user.model.UserInfo;
import czsp.user.service.UserInfoService;

@At("/user")
@IocBean
public class UserModule {

	@Inject
	private UserInfoService userInfoService;

	final Log log = Logs.getLog(MainSetup.class);

	@At("/list")
	@Ok("jsp:/czsp/user/showList")
	public Map<String, Object> showList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List users = userInfoService.getList();
		// 添加部门选项
		Map deptsMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());
		// 添加区县选项
		Map qxMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());

		map.put("users", users);
		map.put("departments", departments);
		map.put("qxList", qxList);
		return map;
	}

	@At("/create")
	@Ok(">>:/user/list")
	public Map<String, Object> create(@Param("..") UserInfo user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			log.debug("new userId:" + userInfoService.addUser(user));
		} catch (Exception e) {
			log.error(MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	@At("/activate")
	@Ok("json")
	public Map<String, Object> activate() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (Mvcs.getHttpSession().getAttribute("userInfo") == null) {
			UserInfo userInfo = userInfoService.getUserInfoByUserId("100001");
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
		List users = userInfoService.getListByCondition(userInfo);
		// 添加部门选项
		Map deptsMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());
		// 添加区县选项
		Map qxMap = (HashMap) (DicUtil.getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());

		map.put("userInfo", userInfo);
		map.put("users", users);
		map.put("departments", departments);
		map.put("qxList", qxList);
		return map;
	}

	@At("/select")
	@Ok("json")
	public Map<String, Object> select(@Param("userId") String userId) {
		System.out.println(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		if (userInfo == null) {
			map.put("result", "fail");
			map.put("message", "找不到该用户");
		} else {
			Mvcs.getHttpSession().setAttribute("userInfo", userInfo);
			map.put("result", "success");
		}
		return map;
	}

	@At("/delete/?")
	@Ok("json")
	public Map<String, Object> delete(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userInfoService.deleteUser(userId);
		} catch (Exception e) {
			map.put("result", "fail");
		}
		map.put("result", "success");
		return map;
	}
}
