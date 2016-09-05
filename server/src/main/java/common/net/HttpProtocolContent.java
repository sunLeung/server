package common.net;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import common.log.Logger;
import common.log.LoggerManger;
import common.utils.AnnoUtils;

public class HttpProtocolContent {
	private static Logger log=LoggerManger.getLogger();
	public static Map<Integer, HttpAction> httpProtocolContent = new ConcurrentHashMap<Integer, HttpAction>();
	
	public static void init() {
		try {
			Set<Class<?>> set = AnnoUtils.getClasses("protocol.http");
			for (Class<?> clz : set) {
				HttpProtocol handler = clz.getAnnotation(HttpProtocol.class);
				if (handler != null) {
					int value = handler.value();
					if (httpProtocolContent.get(value) != null){
						throw new IllegalArgumentException("[error] same http protocol ('" + value + "' in "+ clz.getName() + " & "+ httpProtocolContent.get(value).getClass().getName()+ ")");
					}
					httpProtocolContent.put(value, (HttpAction) clz.newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
}
