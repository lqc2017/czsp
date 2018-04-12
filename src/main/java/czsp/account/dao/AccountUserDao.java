package czsp.account.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.account.model.AccountUser;

@IocBean
public class AccountUserDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年4月12日 根据用户名查询相默认关联的用户-账号信息
	 */
	public AccountUser getAccountUserByUserName(String username) {
		return dao.fetch(AccountUser.class, Cnd.where("userName", "=", username).and("isDefault", "=", "1"));
	}

	/**
	 * 全琛 2018年4月13日 根据用户id查询相关联的用户-账号信息
	 */
	public AccountUser getAccountUserByUserId(String userId) {
		return dao.fetch(AccountUser.class, Cnd.where("userId", "=", userId));
	}

	/**
	 * 全琛 2018年4月13日 添加账号-用户关联关系
	 */
	public void add(AccountUser accountUser) {
		dao.insert(accountUser);
	}

}
