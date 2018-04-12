package czsp.account.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

import czsp.account.model.AccountInfo;
import czsp.common.bean.Pagination;

@IocBean
public class AccountInfoDao {
	Ioc ioc = Mvcs.getIoc();
	Dao dao = ioc.get(Dao.class, "dao");

	/**
	 * 全琛 2018年4月12日 根据账号密码查询是否存在相关账号
	 */
	public boolean findAccount(String userName, String password) {
		AccountInfo ai = new AccountInfo();
		ai = dao.fetch(AccountInfo.class, Cnd.where("userName", "=", userName).and("password", "=", password));
		if (ai != null)
			return true;
		else
			return false;
	}

	/**
	 * 全琛 2018年4月12日 分页查询
	 */
	public Pagination<AccountInfo> getListByCondition(AccountInfo accountCondition, int pageNumber, int pageSize) {
		Criteria cri = Cnd.cri();
		Pager pager = dao.createPager(pageNumber, pageSize);
		Pagination<AccountInfo> pagination = new Pagination<AccountInfo>();

		if (accountCondition.getUserName() != null)
			cri.where().andLike("userName", accountCondition.getUserName());

		cri.getOrderBy().asc("userName");

		pager.setRecordCount(dao.count(AccountInfo.class, cri));

		pagination.setList(dao.query(AccountInfo.class, cri, pager));
		pagination.setPager(pager);
		return pagination;
	}

	/**
	 * 全琛 2018年4月12日 根据登录名查询账号信息
	 */
	public AccountInfo getAccountInfoByUserName(String userName) {
		return dao.fetch(AccountInfo.class, Cnd.where("userName", "=", userName));
	}

	/**
	 * 全琛 2018年4月12日 修改账号信息
	 */
	public void update(AccountInfo accountInfo) {
		dao.updateIgnoreNull(accountInfo);
	}

	/**
	 * 全琛 2018年4月13日 添加账号
	 */
	public void add(AccountInfo accountInfo) {
		dao.insert(accountInfo);
	}
}
