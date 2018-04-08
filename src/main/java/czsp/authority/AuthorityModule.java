package czsp.authority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.ViewModel;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.authority.model.PermissionObject;
import czsp.authority.service.PermissionService;
import czsp.common.Constants;
import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.user.model.UserInfo;
import czsp.user.service.UserInfoService;

@At("/auth")
@IocBean
public class AuthorityModule {
	@Inject
	private UserInfoService userInfoService;

	@Inject
	private PermissionService permissionService;

	/**
	 * 全琛 2018年3月17日 角色列表
	 */
	@At("/roleList")
	@Ok("re:jsp:/czsp/auth/show_role_list")
	public String roleList(String userId, ViewModel model) {
		if (userId != null) {
			UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
			model.addv("userInfo", userInfo);
			return null;
		} else {
			return "jsp:/czsp/auth/show_role_list2";
		}
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

	/**
	 * 全琛 2018年4月6日 获取所有权限对象
	 */
	@At("/permissionList")
	@Ok("jsp:/czsp/auth/permission/show_permission_list")
	public Map<String, Object> permissionList() {
		Map<String, Object> map = new HashMap<String, Object>();

		List permissionList = permissionService.getList();
		Map dicPT = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_PERMISSION_TYPE));

		map.put("permissionList", permissionList);
		map.put("dicPT", dicPT);
		return map;
	}

	/**
	 * 全琛 2018年4月6日 更新权限对象
	 */
	@At("/editPermission")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> editPermission(@Param("..") PermissionObject po) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			permissionService.update(po);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年4月6日 添加权限对象
	 */
	@At("/addPermission")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> addPermission(@Param("..") PermissionObject po) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			permissionService.add(po);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年4月8日 查看角色对应的所有权限
	 */
	@At("/pmsDetail/?")
	@Ok("jsp:/czsp/auth/permission/show_permission_detail")
	public Map<String, Object> permissionDetail(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map permissionRoleList = permissionService.getListByRoleId(roleId);
		String roleName = DicUtil.getInstance().getItemName(Constants.DIC_AHTU_ROLE_NO, roleId);

		map.put("permissionRoleList", permissionRoleList);
		map.put("roleName", roleName);
		map.put("roleId", roleId);
		return map;
	}

	/**
	 * 全琛 2018年4月8日 删除角色权限关联
	 */
	@At("/deletPmsRole")
	@Ok("json")
	public Map<String, Object> deletPmsRole(String roleId, String objectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			permissionService.deltePmsRole(roleId, objectId);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年4月8日 获取角色未绑定的其他权限
	 */
	@At("/getOtherPermissions/?")
	@Ok("json")
	public Map<String, Object> getOtherPermissions(String roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List poList = permissionService.getOtherPermissions(roleId);
			System.out.println(poList.size());
			map.put("poList", poList);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年4月8日 添加角色权限关联
	 */
	@At("/addPmsRole")
	@Ok("json")
	public Map<String, Object> addPmsRole(String roleId, String objectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			permissionService.addPmsRole(roleId, objectId);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}
}
