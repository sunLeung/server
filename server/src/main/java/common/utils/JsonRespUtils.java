package common.utils;

import java.util.HashMap;
import java.util.Map;

public class JsonRespUtils {
	
	public static String fail(int code,String msg){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		String data=JsonUtils.encode2Str(map);
		return data;
	}
	
	public static String success(Object obj){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", Def.CODE_SUCCESS);
		map.put("data", obj);
		String data=JsonUtils.encode2Str(map);
		return data;
	}
}
