package czsp.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import czsp.common.util.DicUtil;
import czsp.common.util.MessageUtil;
import czsp.common.util.SessionUtil;
import czsp.user.model.UserInfo;

@IocBean
@At("/common")
public class CommonModule {
	/**
	 * 全琛 2018年4月12日 首页
	 */
	@At("/index")
	@Ok("jsp:/czsp/index")
	public Map<String, Object> login() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@At("/selectTown")
	@Ok("jsp:/czsp/common/select_town")
	public Map<String, Object> selectTown() {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = SessionUtil.getCurrenUser();
		List<Record> czList = new ArrayList<Record>();

		if (userInfo != null) {
			Map<String, Record> czMap = (TreeMap<String, Record>) DicUtil.getInstance().getDicMap()
					.get(Constants.DIC_QX_CZ_NO);
			for (String key : czMap.keySet()) {
				if (userInfo.getQxId().equals(czMap.get(key).get("qx_id"))) {
					czList.add(czMap.get(key));
				}
			}
		}

		map.put("czList", czList);
		return map;
	}

	/**
	 * 全琛 2018年4月4日 字典管理
	 */
	@At("/dicManage")
	@Ok("jsp:/czsp/common/manage/dic_manage")
	public Map<String, Object> dicManage() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	/**
	 * 全琛 2018年4月5日 人员管理
	 */
	@At("/humanManage")
	@Ok("jsp:/czsp/common/manage/human_manage")
	public Map<String, Object> humanManage() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	/**
	 * 全琛 2018年4月4日 字典列表
	 */
	@At("/dicList/?")
	@Ok("jsp:/czsp/common/manage/dic_list")
	public Map<String, Object> dicList(String dicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map dicMap = (TreeMap) DicUtil.getInstance().getDicMap().get(dicId);

		map.put("dicList", new ArrayList(dicMap.values()));
		return map;
	}

	/**
	 * 全琛 2018年4月6日 获得权限类型字典列表
	 */
	@At("/getPermissionType")
	@Ok("json")
	public Map<String, Object> getPermissionType() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map dicPT = (TreeMap) (DicUtil.getInstance().getDicMap().get(Constants.DIC_PERMISSION_TYPE));

			map.put("ptList", new ArrayList(dicPT.values()));
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", MessageUtil.getStackTraceInfo(e));
		}
		return map;
	}

	/**
	 * 全琛 2018年4月13日 流程管理
	 */
	@At("/wfManage")
	@Ok("jsp:/czsp/common/manage/workflow_manage")
	public Map<String, Object> wfManage() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
}
