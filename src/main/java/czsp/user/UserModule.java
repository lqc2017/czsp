package czsp.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.MainSetup;
import czsp.common.Constants;
import czsp.common.bean.Pagination;
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
	@Ok("jsp:/czsp/user/show_list")
	public Map<String, Object> showList(@Param("..") UserInfo userCondition, int pageNumber, int pageSize) {
		System.out.println(userCondition.getQxId());
		pageNumber = pageNumber == 0 ? 1 : pageNumber;
		pageSize = pageSize == 0 ? 6 : pageSize;

		Map<String, Object> map = new HashMap<String, Object>();
		Pagination<UserInfo> pagination = userInfoService.getListByCondition(userCondition, pageNumber, pageSize);
		// 添加部门选项
		Map deptsMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());
		// 添加区县选项
		Map qxMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());

		map.put("pagination", pagination);
		map.put("departments", departments);
		map.put("qxList", qxList);
		map.put("dicUtil", DicUtil.getInstance());
		map.put("userCondition", userCondition);
		return map;
	}

	/**
	 * 全琛 2018年4月8日 人员列表（角色）
	 */
	@At("/selectByRole")
	@Ok("jsp:/czsp/user/select_by_role")
	public Map<String, Object> userList(@Param("..") UserInfo userCondition, int pageNumber, int pageSize) {
		pageNumber = pageNumber == 0 ? 1 : pageNumber;
		pageSize = pageSize == 0 ? 6 : pageSize;

		Map<String, Object> map = new HashMap<String, Object>();
		Pagination<UserInfo> pagination = userInfoService.getListByCondition(userCondition, pageNumber, pageSize);
		// 添加区县选项
		Map qxMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());
		// 角色名选项
		String roleName = DicUtil.getInstance().getItemName(Constants.DIC_AHTU_ROLE_NO, userCondition.getRoleId());

		map.put("pagination", pagination);
		map.put("qxList", qxList);
		map.put("roleName", roleName);
		map.put("dicUtil", DicUtil.getInstance());
		map.put("userCondition", userCondition);
		return map;
	}

	/**
	 * 全琛 2018年4月9日 添加用户界面
	 */
	@At("/add")
	@Ok("jsp:/czsp/user/add")
	public Map<String, Object> add() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 添加区县选项
		Map qxMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());
		// 添加部门选项
		Map deptsMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());

		map.put("qxList", qxList);
		map.put("departments", departments);
		return map;
	}

	/**
	 * 全琛 2018年4月9日 添加用户
	 */
	@At("/create")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> create(@Param("..") UserInfo user) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userInfoService.addUser(user);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月23日 清空session中的用户信息
	 */
	@At("/clear")
	@Ok("json")
	public Map<String, Object> clear() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Mvcs.getHttpSession().removeAttribute("userInfo");
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月23日 用户选择页面
	 */
	@At("/change")
	@Ok("jsp:/czsp/user/select_user")
	public Map<String, Object> change(@Param("..") UserInfo userCondition) {
		if (userCondition == null)
			userCondition = new UserInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		List users = userInfoService.getListByCondition(userCondition);
		// 添加部门选项
		Map deptsMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_AHTU_DEPT_NO));
		List departments = new ArrayList(deptsMap.values());
		// 添加区县选项
		Map qxMap = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_QX_NO));
		List qxList = new ArrayList(qxMap.values());

		map.put("userCondition", userCondition);
		map.put("users", users);
		map.put("departments", departments);
		map.put("qxList", qxList);
		return map;
	}

	/**
	 * 全琛 2018年3月23日 加载用户到session
	 */
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
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
		}
		return map;
	}
}
