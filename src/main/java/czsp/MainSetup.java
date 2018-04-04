package czsp;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.Ioc;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import czsp.common.util.DicUtil;

public class MainSetup implements Setup{
	@Override
	public void init(NutConfig nc) {
		Ioc ioc = nc.getIoc(); // 拿到Ioc容器
		Dao dao = ioc.get(Dao.class); // 通过Ioc容器可以拿到你想要的ioc bean
		final Log log = Logs.getLog(MainSetup.class);

		// 初始化字典工具
		DicUtil.getInstance();
		// 加载所有字典表名称
		List<Record> records = dao.query("base_dic", null);
		for (Record record : records) {
			String tableName = record.get("TABLE_NAME").toString();
			//加载字典表记录
			List<Record> dicTable = dao.query(tableName, null);
			Map dicMap = new TreeMap();
			for(Record dicItem : dicTable){
				dicMap.put(dicItem.get("ID"), dicItem);
			}
			DicUtil.getInstance().addDic(record.get("DIC_ID").toString(), dicMap);
			log.info("already add dictable:"+tableName);
		}
	}

	@Override
	public void destroy(NutConfig nc) {
		// TODO Auto-generated method stub
		
	}
}
