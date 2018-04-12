package czsp.account.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import czsp.account.dao.AccountInfoDao;
import czsp.account.dao.AccountUserDao;
import czsp.account.model.AccountInfo;
import czsp.account.model.AccountUser;
import czsp.common.bean.Pagination;

@IocBean
public class AccountService {
	@Inject
	private AccountUserDao accountUserDao;

	@Inject
	private AccountInfoDao accountInfoDao;

	/**
	 * 全琛 2018年4月12日 根据账号密码查询是否存在相关账号
	 */
	public boolean findAccount(AccountInfo accountInfo) {
		if (StringUtils.isBlank(accountInfo.getUserName()) || StringUtils.isBlank(accountInfo.getUserName()))
			return false;
		else
			return accountInfoDao.findAccount(accountInfo.getUserName(), accountInfo.getPassword());
	}

	/**
	 * 全琛 2018年4月12日 根据用户名查询相关联的用户-账号信息
	 */
	public AccountUser getAccountUserByUsername(String username) {
		return accountUserDao.getAccountUserByUsername(username);
	}

	/**
	 * 全琛 2018年4月12日 分页查询
	 */
	public Pagination<AccountInfo> getListByCondition(AccountInfo accountCondition, int pageNumber, int pageSize) {
		return accountInfoDao.getListByCondition(accountCondition, pageNumber, pageSize);
	}

	/**
	 * 全琛 2018年4月12日 根据登录名查询账号信息
	 */
	public AccountInfo getAccountInfoByUserName(String userName) {
		return accountInfoDao.getAccountInfoByUserName(userName);
	}

	/**
	 * 全琛 2018年4月12日 更新账号信息
	 */
	public void update(AccountInfo accountInfo) {
		accountInfo.setUpdateTime(new Date());
		accountInfoDao.update(accountInfo);
	}

}
