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
import czsp.common.util.SessionUtil;
import czsp.user.model.UserInfo;

@IocBean
@At("/common")
public class CommonModule {
	@At("/selectTown")
	@Ok("jsp:/czsp/common/select_town")
	public Map<String, Object> selectTown() {
		Map<String, Object> map = new HashMap<String, Object>();
		UserInfo userInfo = SessionUtil.getCurrenUser();
		List<Record> czList = new ArrayList<Record>();

		if (userInfo != null) {
			Map<String, Record> czMap = (HashMap<String, Record>) DicUtil.getInstance().getDicMap()
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
	 * 全琛 2018年4月4日 字典列表
	 */
	@At("/dicList/?")
	@Ok("jsp:/czsp/common/manage/dic_list")
	public Map<String, Object> dicList(String dicId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map dicMap = (TreeMap)DicUtil.getInstance().getDicMap().get(dicId);
		
		map.put("dicList", new ArrayList(dicMap.values()));
		return map;
	}
}
