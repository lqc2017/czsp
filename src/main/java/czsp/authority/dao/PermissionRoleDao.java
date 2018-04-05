package czsp.authority.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

@IocBean
public class PermissionRoleDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年4月5日 根据角色id获取所有权限对象id
	 * @param map 
	 */
	public void getPermissionByRoleId(String roleId, HashMap map) {

		Sql sql = Sqls.create("SELECT object_id FROM permission_role WHERE role_id = '" + roleId + "'");
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				while (rs.next()){
					map.put(rs.getString("object_id"), null);
				}
				return map;
			}
		});
		dao.execute(sql);
	}

}
