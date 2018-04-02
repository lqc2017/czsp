package czsp.authority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import czsp.common.util.MessageUtil;
import czsp.user.model.UserInfo;
import czsp.user.service.UserInfoService;

@At("/auth")
@IocBean
public class AuthorityModule {
	@Inject
	private UserInfoService userInfoService;

	/**
	 * 全琛 2018年3月17日 角色列表
	 */
	@At("/roleList")
	@Ok("jsp:/czsp/auth/show_role_list")
	public Map<String, Object> roleList(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
		map.put("userInfo", userInfo);
		return map;
	}

	/**
	 * 全琛 2018年3月17日 关联角色
	 */
	@At("/associate")
	@Ok("json")
	public Map<String, Object> associate(String roleId, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			String userRole = userInfo.getRoleId();
			if (StringUtils.isNotBlank(userRole))
				userInfo.setRoleId(userInfo.getRoleId() + "," + roleId);
			else
				userInfo.setRoleId(roleId);

			userInfoService.updateUser(userInfo);

			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年3月17日 解除关联
	 */
	@At("/remove")
	@Ok("json")
	public Map<String, Object> remove(String roleId, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			String[] roleArr = userInfo.getRoleId().split(",");

			List<String> roleList = new ArrayList<String>(Arrays.asList(roleArr));
			roleList.remove(roleId);
			userInfo.setRoleId(StringUtils.join(roleList, ","));

			userInfoService.updateUser(userInfo);

			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}
}
