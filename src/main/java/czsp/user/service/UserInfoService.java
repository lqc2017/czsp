package czsp.user.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.authority.dao.PermissionRoleDao;
import czsp.authority.model.PermissionObject;
import czsp.common.bean.Pagination;
import czsp.common.util.StringWrapUtil;
import czsp.user.dao.UserInfoDao;
import czsp.user.model.UserInfo;

@IocBean
public class UserInfoService {
	@Inject
	private UserInfoDao userInfoDao;

	@Inject
	private PermissionRoleDao permissionRoleDao;

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
		return userInfoDao.getListByRoleId(roleArr, null, null);
	}

	/**
	 * 全琛 2018年3月28日
	 * 
	 * @param roleIds
	 *            角色id集合
	 * @param qxId
	 *            区县id
	 * @return
	 */
	public List getListByRoleId(String roleIds, String qxId) {
		String[] roleArr = roleIds.split(",");
		return userInfoDao.getListByRoleId(roleArr, null, qxId);
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
		return userInfoDao.getListByRoleId(roleArr, userIds, null);
	}

	/**
	 * 全琛 2018年3月28日
	 * 
	 * @param roleIds
	 *            角色id集合
	 * @param notInUserIds
	 *            用户id集合
	 * @param qxId
	 *            区县id
	 * @return
	 */
	public List getListByRoleId(String roleIds, List<String> notInUserIds, String qxId) {
		String[] roleArr = roleIds.split(",");
		String userIds = StringWrapUtil.getSQLParamList(notInUserIds, null, null);
		return userInfoDao.getListByRoleId(roleArr, userIds, qxId);
	}

	/**
	 * 全琛 2018年4月5日
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfoByUserId(String userId) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserId(userId);

		HashMap<String, PermissionObject> map = new HashMap<String, PermissionObject>();
		// 加载对应权限
		if (StringUtils.isNotBlank(userInfo.getRoleId())) {
			String[] roleArr = userInfo.getRoleId().split(",");
			for (String roleId : roleArr) {
				permissionRoleDao.getPermissionByRoleId(roleId, map);
			}
		}

		userInfo.setPermission(map);
		return userInfo;
	}

	public void deleteUser(String userId) {
		userInfoDao.deleteUser(userId);
	}

	public void updateUser(UserInfo userInfo) {
		userInfoDao.updateUser(userInfo);
	}

	/**
	 * 全琛
	 * 2018年4月5日 分页查询
	 * @param userCondition
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Pagination<UserInfo> getListByCondition(UserInfo userCondition, int pageNumber, int pageSize) {
		return userInfoDao.getListByCondition(userCondition,pageNumber,pageSize);
	}
}
