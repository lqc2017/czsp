package czsp.authority.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.authority.dao.PermissionRoleDao;
import czsp.authority.model.PermissionObject;

@IocBean
public class PermissionService {
	@Inject
	private PermissionRoleDao permissionRoleDao;

	/**
	 * 全琛 2018年4月6日 获得所有权限对象
	 */
	public List getList() {
		return permissionRoleDao.getList();
	}

	/**
	 * 全琛 2018年4月6日 更新权限对象
	 */
	public void update(PermissionObject po) {
		permissionRoleDao.update(po);
	}

	/**
	 * 全琛 2018年4月6日 新增权限对象
	 */
	public void add(PermissionObject po) {
		permissionRoleDao.add(po);
	}

}
