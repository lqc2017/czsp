package czsp.common.util;

import java.util.HashMap;
import java.util.Map;
import org.nutz.dao.entity.Record;

public class DicUtil {
	private static Map dicMap = new HashMap();
	private static DicUtil dicUtil = null;

	private DicUtil(){}

	public static DicUtil getInstance() {
        if( dicUtil == null ) {
        	dicUtil = new DicUtil();
        }
        return dicUtil;
    }

	public static Map getDicMap() {
		return dicMap;
	}

	public static void addDic(String name, Object obj) {
		dicMap.put(name, obj);
	}
	
	public static String getItemName(String dicId, String id) {
		Map<String,Record> map = (HashMap<String,Record>)dicMap.get(dicId);
		return map.get(id).get("name").toString();
	}
	
	public static String getItemCode(String dicId, String id) {
		Map<String,Record> map = (HashMap<String,Record>)dicMap.get(dicId);
		return map.get(id).get("code").toString();
	}
}
