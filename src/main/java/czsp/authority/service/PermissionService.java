package czsp.authority.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.authority.dao.PermissionObjectDao;
import czsp.authority.dao.PermissionRoleDao;
import czsp.authority.model.PermissionObject;
import czsp.authority.model.PermissionRole;

@IocBean
public class PermissionService {
	@Inject
	private PermissionRoleDao permissionRoleDao;

	@Inject
	private PermissionObjectDao permissionObjectDao;

	/**
	 * 全琛 2018年4月6日 获得所有权限对象
	 */
	public List getList() {
		return permissionObjectDao.getList();
	}

	/**
	 * 全琛 2018年4月8日 获得对应角色的所有权限
	 */
	public Map getListByRoleId(String roleId) {
		HashMap<String, PermissionObject> map = new HashMap<String, PermissionObject>();
		permissionRoleDao.getPermissionByRoleId(roleId, map);

		for (String objectId : map.keySet()) {
			PermissionObject po = permissionObjectDao.getPermissionObjectByObjectId(objectId);
			map.put(po.getObjectId(), po);
		}
		return map;
	}

	/**
	 * 全琛 2018年4月6日 更新权限对象
	 */
	public void update(PermissionObject po) {
		permissionObjectDao.update(po);
	}

	/**
	 * 全琛 2018年4月6日 新增权限对象
	 */
	public void add(PermissionObject po) {
		po.setCreateTime(new Date());
		permissionObjectDao.add(po);
	}

	/**
	 * 全琛 2018年4月8日 删除角色权限关联
	 */
	public void deltePmsRole(String roleId, String objectId) {
		permissionRoleDao.deltePmsRole(roleId, objectId);
	}

	/**
	 * 全琛 2018年4月8日 获取角色未绑定的其他权限
	 */
	public List getOtherPermissions(String roleId) {
		List<PermissionRole> permissionRoleList = permissionRoleDao.getListByRoleId(roleId);
		List<String> poIds = new ArrayList<String>();
		for (PermissionRole po : permissionRoleList) {
			poIds.add(po.getObjectId());
		}
		return permissionObjectDao.getList(poIds);
	}

	/**
	 * 全琛 2018年4月8日 添加角色权限关联
	 */
	public void addPmsRole(String roleId, String objectId) {
		PermissionRole pr = new PermissionRole(roleId,objectId);
		permissionRoleDao.addPmsRole(pr);
	}

}
