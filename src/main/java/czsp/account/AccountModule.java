package czsp.account;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.account.model.AccountInfo;
import czsp.account.model.AccountUser;
import czsp.account.service.AccountService;
import czsp.common.bean.Pagination;
import czsp.common.util.MessageUtil;
import czsp.user.model.UserInfo;
import czsp.user.service.UserInfoService;

@At("/account")
@IocBean
public class AccountModule {
	@Inject
	AccountService accountService;
	
	@Inject
	UserInfoService userInfoService;

	/**
	 * 全琛 2018年4月12日 账号列表
	 */
	@At("/list")
	@Ok("jsp:/czsp/account/show_list")
	public Map<String, Object> showList(AccountInfo accountCondition, int pageNumber, int pageSize) {
		pageNumber = pageNumber == 0 ? 1 : pageNumber;
		pageSize = pageSize == 0 ? 6 : pageSize;
		if (accountCondition == null)
			accountCondition = new AccountInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		Pagination<AccountInfo> pagination = accountService.getListByCondition(accountCondition, pageNumber, pageSize);

		map.put("pagination", pagination);
		return map;
	}

	/**
	 * 全琛 2018年4月12日 编辑页面
	 */
	@At("/edit/?")
	@Ok("jsp:/czsp/account/edit")
	public Map<String, Object> edit(String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		AccountInfo accountInfo = accountService.getAccountInfoByUserName(userName);

		map.put("accountInfo", accountInfo);
		return map;
	}

	/**
	 * 全琛 2018年4月12日 更新账户信息
	 */
	@At("/update")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> update(@Param("..") AccountInfo accountInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			accountService.update(accountInfo);
			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	@At("/create/?")
	@Ok("json")
	public Map<String, Object> create(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查询是否已有帮绑定的账号
			AccountUser au = accountService.getAccountUserByUserId(userId);
			if (au != null) {
				map.put("result", "fail");
				map.put("message", "已存在与用户相关联的账号");
			}else{
				UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
				accountService.create(userInfo);
				map.put("result", "success");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}
}
