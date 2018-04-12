package czsp.account.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import czsp.account.dao.AccountInfoDao;
import czsp.account.dao.AccountUserDao;
import czsp.account.model.AccountInfo;
import czsp.account.model.AccountUser;
import czsp.common.Constants;
import czsp.common.bean.Pagination;
import czsp.common.bean.PinyinTool;
import czsp.common.bean.PinyinTool.Type;
import czsp.common.util.DicUtil;
import czsp.user.model.UserInfo;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
	public AccountUser getAccountUserByUserName(String username) {
		return accountUserDao.getAccountUserByUserName(username);
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

	/**
	 * 全琛 2018年4月13日 根据用户id查询相关联的用户-账号信息
	 */
	public AccountUser getAccountUserByUserId(String userId) {
		return accountUserDao.getAccountUserByUserId(userId);
	}

	/**
	 * 全琛 2018年4月13日 创建与用户id相关联的账号和绑定关系
	 * 
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public void create(UserInfo userInfo) throws BadHanyuPinyinOutputFormatCombination {
		PinyinTool pt = new PinyinTool();
		AccountInfo accountInfo = new AccountInfo();
		if (userInfo.getQxId() == null || userInfo.getQxId().equals("00"))
			accountInfo.setUserName(pt.toPinYin(userInfo.getName(), "", Type.LOWERCASE));
		else {
			String userName = DicUtil.getInstance().getItemCode(Constants.DIC_QX_NO, userInfo.getQxId());
			userName = userName.toLowerCase() + "_" + pt.toPinYin(userInfo.getName(), "", Type.LOWERCASE);
			accountInfo.setUserName(userName);
		}
		accountInfo.setPassword("111111");
		accountInfo.setCreateTime(new Date());
		accountInfo.setIsAvailable("1");
		accountInfo.setUpdateTime(new Date());

		AccountUser accountUser = new AccountUser();
		accountUser.setUserId(userInfo.getUserId());
		accountUser.setUserName(accountInfo.getUserName());
		accountUser.setIsDefault("1");
		accountUser.setCreateTime(new Date());

		Trans.exec(new Atom() {
			public void run() {
				accountInfoDao.add(accountInfo);
				accountUserDao.add(accountUser);
			}
		});
	}

}
