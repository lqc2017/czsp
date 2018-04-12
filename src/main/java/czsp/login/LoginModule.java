package czsp.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.ViewModel;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import czsp.account.model.AccountInfo;
import czsp.account.model.AccountUser;
import czsp.account.service.AccountService;
import czsp.common.bean.RandomValidateCode;
import czsp.common.util.MessageUtil;
import czsp.user.model.UserInfo;
import czsp.user.service.UserInfoService;

@IocBean
public class LoginModule {
	@Inject
	private AccountService accountService;

	@Inject
	private UserInfoService userInfoService;

	/**
	 * 全琛 2018年4月12日 登陆页面
	 */
	@At("/login")
	@Ok("jsp:/czsp/login")
	public Map<String, Object> login() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	/**
	 * 全琛 2018年4月12日 登出
	 */
	@At("/logout")
	@Ok("jsp:/czsp/login")
	public Map<String, Object> clear() {
		Map<String, Object> map = new HashMap<String, Object>();
		Mvcs.getHttpSession().removeAttribute("userInfo");
		map.put("info", "您已成功登出");
		return map;
	}

	/**
	 * 全琛 2018年4月12日 获取验证码
	 */
	@At("/getVerify")
	@Ok("void")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);// 输出验证码图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 全琛 2018年4月12日 登录页面校验验证码
	 */
	@At("/checkVerify")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	public Map<String, Object> checkVerify(@Param("verifyCode") String verifyCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 从session中获取随机数
			String random = (String) Mvcs.getHttpSession().getAttribute("RANDOMVALIDATECODEKEY");
			if (random.equals(verifyCode)) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");// 验证码错误
				map.put("message", "验证码错误");
			}
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年4月12日 验证用户
	 */
	@At("/validate")
	@Ok("re:>>:/common/index")
	public String validate(@Param("..") AccountInfo accountInfo, ViewModel model) {
		if (accountService.findAccount(accountInfo)) {
			AccountInfo ai = accountService.getAccountInfoByUserName(accountInfo.getUserName());
			if (!StringUtils.equals(ai.getIsAvailable(), "1")) {
				model.addv("info", "当前账号不可用，请联系管理员。");
				return "jsp:/czsp/login";
			}
			// 查询相关用户信息(绑定的默认用户)
			AccountUser au = accountService.getAccountUserByUsername(accountInfo.getUserName());
			UserInfo userInfo = userInfoService.getUserInfoByUserId(au.getUserId());
			if (userInfo == null) {
				model.addv("info", "未查询到用该账号绑定的用户信息！");
				return "jsp:/czsp/login";
			}
			Mvcs.getHttpSession().setAttribute("userInfo", userInfo);
			return null;
		} else {
			model.addv("info", "找不到用户名或密码！");
			return "jsp:/czsp/login";
		}
	}
}
