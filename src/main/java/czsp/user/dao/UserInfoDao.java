package czsp.user.dao;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
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

		if (user.getDepartmentId() != null && !"".equals(user.getDepartmentId().trim()))
			cri.where().andEquals("departmentId", user.getDepartmentId().trim());
		
		if (user.getQxId() != null && !"".equals(user.getQxId().trim()))
			cri.where().andEquals("qxId", user.getQxId().trim());

		return dao.query(UserInfo.class, cri);
	}

	/**
	 * 全琛 2018年2月28日 根据角色获取用户列表
	 */
	public List getListByRoleId(String[] roleArr) {
		Criteria cri = Cnd.cri();
		for (String role : roleArr) {
			cri.where().orLike("roleId", role);
		}
		return dao.query(UserInfo.class, cri);
	}

	/**
	 * 全琛 2018年3月1日 根据角色获取和排除条件筛选人员
	 */
	public List getListByRoleId(String[] roleArr, String userIds) {
		Criteria cri = Cnd.cri();
		for (String role : roleArr) {
			cri.where().orLike("roleId", role);
		}
		cri.where().andNotIn("userId", userIds);
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

}
