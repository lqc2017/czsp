package czsp.user.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.common.util.StringWrapUtil;
import czsp.user.dao.UserInfoDao;
import czsp.user.model.UserInfo;

@IocBean
public class UserInfoService {
	@Inject
	private UserInfoDao userInfoDao;

	/**
	 * 全琛 2018年3月17日
	 */
	public List getList() {
		return userInfoDao.getList();
	}

	/**
	 * 全琛 2018年3月17日
	 */
	public String addUser(UserInfo userInfo) {
		return userInfoDao.addUser(userInfo);
	}

	/**
	 * 全琛 2018年3月17日
	 */
	public List getListByCondition(UserInfo user) {
		return userInfoDao.getListByCondition(user);
	}

	/**
	 * 全琛 2018年3月17日
	 * 
	 * @param roleIds
	 *            角色id集合
	 * @return
	 */
	public List getListByRoleId(String roleIds) {
		String[] roleArr = roleIds.split(",");

		return userInfoDao.getListByRoleId(roleArr);
	}

	/**
	 * 全琛 2018年3月17日
	 * 
	 * @param roleIds
	 *            角色id集合
	 * @param notInUserIds
	 *            用户id集合
	 * @return
	 */
	public List getListByRoleId(String roleIds, List<String> notInUserIds) {
		String[] roleArr = roleIds.split(",");
		String userIds = StringWrapUtil.getSQLParamList(notInUserIds, null, null);

		return userInfoDao.getListByRoleId(roleArr, userIds);
	}

	public UserInfo getUserInfoByUserId(String userId) {
		return userInfoDao.getUserInfoByUserId(userId);
	}

	public void deleteUser(String userId) {
		userInfoDao.deleteUser(userId);
	}

	public void updateUser(UserInfo userInfo) {
		userInfoDao.updateUser(userInfo);
	}
}
