package common.config;

import java.io.File;

/**
 * 
 * @Description 项目配置
 * @author liangyx
 * @date 2013-7-1
 * @version V1.0
 */
public class Config {
	public static String ROOT_DIR=System.getProperty("user.dir");
	/**配置文件根目录*/
	public static String CONFIG_DIR="config";
	/**守护线程运行间隔*/
	public static int WATCH_SECOND=10;
	/**日志配置文件*/
	public static String LOGGER_CONFIG="logger.xx";
	/**数据库配置*/
	public static String DB_CONFIG="c3p0-config.xml";
	/**运行日志输出目录*/
	public static String SYSTEM_OUT_DIR="sysout";
	/**是否后台输出*/
	public static Boolean PRINTER_RUN=true;
	/**尝试获取输出错误是否打开*/
	public static Boolean TRY_GET_ERROR_STACK=false;
	/**管理服安全认证头*/
	public static String SECURITY="himan";
	/**数据定时回写时间*/
	public static int FLUSH_MINUTE=30;
	
	/**OSS*/
	public static final String ACCESS_ID = "tVKghDy7sZX3ly5D";
	public static final String ACCESS_KEY = "HgIDU5tojurLK9AlL6g1YZCcPZN2Lg";
	public static final String BUCKET_NAME = "mg-player-data";
	public static final String ENDPOINT="oss-cn-qingdao.aliyuncs.com";
	
	/**
	 * 初始化配置
	 */
	public static void init(){
		CONFIG_DIR=ROOT_DIR+File.separator+CONFIG_DIR+File.separator;
		LOGGER_CONFIG=CONFIG_DIR+LOGGER_CONFIG;
		DB_CONFIG=CONFIG_DIR+DB_CONFIG;
		SYSTEM_OUT_DIR=ROOT_DIR+File.separator+SYSTEM_OUT_DIR+File.separator;
	}
}
