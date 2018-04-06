package czsp.common.util;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.entity.Record;

public class DicUtil {
	private static Map dicMap = new ConcurrentHashMap();
	private static DicUtil dicUtil = null;

	private DicUtil(){}

	public static DicUtil getInstance() {
        if( dicUtil == null ) {
        	dicUtil = new DicUtil();
        }
        return dicUtil;
    }

	public Map getDicMap() {
		return dicMap;
	}

	public void addDic(String name, Object obj) {
		dicMap.put(name, obj);
	}
	
	public String getItemName(String dicId, String id) {
		if(dicId == null || StringUtils.isBlank(dicId) || id == null || StringUtils.isBlank(id))
			return null;
		Map<String,Record> map = (TreeMap<String,Record>)dicMap.get(dicId);
		return map.get(id).get("name").toString();
	}
	
	public String getItemCode(String dicId, String id) {
		if(dicId == null || StringUtils.isBlank(dicId) || id == null || StringUtils.isBlank(id))
			return null;
		Map<String,Record> map = (TreeMap<String,Record>)dicMap.get(dicId);
		return map.get(id).get("code").toString();
	}
}
