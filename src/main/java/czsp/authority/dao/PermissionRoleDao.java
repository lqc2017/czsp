package czsp.authority.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.authority.model.PermissionRole;

@IocBean
public class PermissionRoleDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年4月5日 根据角色id获取所有权限对象id
	 * 
	 * @param map
	 */
	public void getPermissionByRoleId(String roleId, HashMap map) {

		Sql sql = Sqls.create("SELECT object_id FROM permission_role WHERE role_id = '" + roleId + "'");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				while (rs.next()) {
					map.put(rs.getString("object_id"), null);
				}
				return map;
			}
		});
		dao.execute(sql);
	}

	/**
	 * 全琛 2018年4月8日 获得对应角色的所有权限
	 */
	public List getListByRoleId(String roleId) {
		Criteria cri = Cnd.cri();
		if (StringUtils.isNotBlank(roleId))
			cri.where().andEquals("roleId", roleId);
		cri.getOrderBy().asc("objectId");

		List list = dao.query(PermissionRole.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年4月8日 删除角色权限关联
	 */
	public void deltePmsRole(String roleId, String objectId) {
		dao.deletex(PermissionRole.class, roleId, objectId);
	}

	/**
	 * 全琛 2018年4月8日 添加角色权限关联
	 */
	public void addPmsRole(PermissionRole permissionRole) {
		dao.insert(permissionRole);
	}

}
