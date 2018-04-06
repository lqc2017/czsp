package czsp.authority.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Criteria;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.authority.model.PermissionObject;

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
	 * 全琛 2018年4月6日 获得所有权限对象
	 */
	public List getList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("objectId");
		List list = dao.query(PermissionObject.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年4月6日 更新权限对象
	 */
	public void update(PermissionObject po) {
		dao.updateIgnoreNull(po);
	}

	/**
	 * 全琛 2018年4月6日 新增权限对象
	 */
	public void add(PermissionObject po) {
		dao.insert(po);
	}

}
