package czsp.user.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import czsp.user.model.UserInfo;

@IocBean
public class UserInfoDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年2月27日 添加用户
	 */
	public String addUser(UserInfo userInfo) {
		return dao.insert(userInfo).getUserId();
	}

	/**
	 * 全琛 2018年2月27日 获取用户信息列表
	 */
	public List getList() {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().asc("userId");
		List list = dao.query(UserInfo.class, cri);
		return list;
	}

	/**
	 * 全琛 2018年2月27日 根据主键获取用户
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		return dao.fetch(UserInfo.class, userId);
	}

	/**
	 * 全琛 2018年2月27日 根据条件获取用户列表
	 */
	public List getListByCondition(UserInfo user) {
		Criteria cri = Cnd.cri();

		if (user.getName() != null)
			cri.where().andLike("name", user.getName());

		if (StringUtils.isNotBlank(user.getDepartmentId()))
			cri.where().andEquals("departmentId", user.getDepartmentId());

		if (StringUtils.isNotBlank(user.getQxId()))
			cri.where().andEquals("qxId", user.getQxId());

		return dao.query(UserInfo.class, cri);
	}

	/**
	 * 全琛 2018年3月17日 删除用户
	 */
	public void deleteUser(String userId) {
		dao.delete(UserInfo.class, userId);
	}

	/**
	 * 全琛 2018年3月17日 更新用户
	 */
	public void updateUser(UserInfo userInfo) {
		dao.update(userInfo);
	}

	/**
	 * 全琛 2018年3月28日 据角色获取、区县和排除条件筛选人员
	 */
	public List getListByRoleId(String[] roleArr, String userIds, String qxId) {
		Criteria cri = Cnd.cri();
		for (String role : roleArr) {
			cri.where().orLike("roleId", role);
		}
		if (StringUtils.isNotBlank(userIds))
			cri.where().andNotIn("userId", userIds);
		if (StringUtils.isNotBlank(qxId))
			cri.where().and("qxId", "=", qxId);
		return dao.query(UserInfo.class, cri);
	}

	/**
	 * 全琛 2018年4月3日 适配service层公用方法
	 * 
	 * @param roleArr
	 * @param object
	 * @param qxId
	 * @param isQxOp
	 * @return
	 */
	public List<String> getUserIdsByRoleId(String[] roleArr, String userIds, String qxId, String isQxOp) {
		List<String> criteria = new ArrayList<String>();
		// 判断按是否具备数组中任一个角色
		if (roleArr != null && roleArr.length > 0) {
			List<String> orExpression = new ArrayList<String>();
			for (String role : roleArr) {
				String s = "role_id like ";
				s += "'%" + role + "%'";
				orExpression.add(s);
			}
			criteria.add("(" + StringUtils.join(orExpression, " or ") + ")");
		}
		// 不在排除列表中
		if (StringUtils.isNotBlank(userIds)) {
			criteria.add("user_id not in (" + userIds + ")");
		}
		// 按区县划分
		if (StringUtils.equals(isQxOp, "1") && StringUtils.isNotBlank(qxId)) {
			criteria.add("qx_id = " + qxId + "");
		}

		Sql sql = Sqls.create("SELECT user_id FROM user_info WHERE " + StringUtils.join(criteria, " and "));
		sql.setCallback(new SqlCallback() {
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<String> list = new ArrayList<String>();
				while (rs.next())
					list.add(rs.getString("user_id"));
				return list;
			}
		});
		dao.execute(sql);
		return sql.getList(String.class);

	}

}
