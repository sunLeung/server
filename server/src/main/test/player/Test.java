package player;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import common.utils.HttpUtils;
import common.utils.JsonUtils;


public class Test {
//	private static String url="http://127.0.0.1:4000";
//	private static String url="http://115.28.234.110:4001/admin";
	private static String url="http://115.28.234.110:4000";
	public static void main(String[] args) throws ParseException {
//		query();
//		save();
//		stop();
//		login();
//		createSong();
//		getossSign();
//		buySong();
		getSongList();
	}
	
	public static void save(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("deviceid", "akdsfjlquaasdfdsfnn");
		requestProperty.put("protocol", "0x01");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("name", "liangyx1");
		body.put("email", "liangyx1@gmail.com");
		body.put("password1", "123456");
		body.put("password2", "123456");
		body.put("sex", 1);
		String data=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,data);
		System.out.println(a);
	}
	
	public static void query(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("deviceid", "akdsfjlquadsfnn");
		requestProperty.put("protocol", "0x0a");
		requestProperty.put("playerid", "4");
		requestProperty.put("token", "1720f6a8d2834dacb05eba07548246d6");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("attribute", "phone#name");
		String data=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,data);
		System.out.println(a);
	}
	
	public static void login(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("protocol", "0x00");
		requestProperty.put("deviceid", "liangyuxin");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("identity", "liangyx1@gmail.com");
		body.put("password", "123456");
		String data=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,data);
		System.out.println(a);
	}
	
	public static void stop(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("security", "himan");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("method", "stopServer");
		String data=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,data);
		System.out.println(a);
	}
	
	public static void createSong(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("security", "himan");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("method", "createSong");
		Map<String,Object> data=new HashMap<String, Object>();
		data.put("name", "喜欢你");
		data.put("mp3URL", "http://halo.com");
		body.put("data", data);
		String d=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,d);
		System.out.println(a);
	}
	
	public static void getossSign(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("protocol", "0x0b");
		requestProperty.put("deviceid", "liangyuxin");
		requestProperty.put("token", "dc75a4e133224a288fbd006f815fb7fb");
		requestProperty.put("playerid", "36");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("action", "getOSSUploadSign");
		body.put("object", "minyi.jpg");
		body.put("Content-Type", "image/jpeg");
		String d=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,d);
		System.out.println(a);
	}
	
	public static void buySong(){
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("protocol", "0x0b");
		requestProperty.put("deviceid", "liangyuxin");
		requestProperty.put("token", "ea45d17460134c4db96410db536c0b1d");
		requestProperty.put("playerid", "36");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("action", "buySong");
		body.put("resourceid", "3");
		String d=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,d);
		System.out.println(a);
	}
	
	public static void getSongList() throws ParseException{
		Map<String,String> requestProperty=new HashMap<String, String>();
		requestProperty.put("protocol", "0x0b");
		requestProperty.put("deviceid", "liangyuxin");
		requestProperty.put("token", "3ebd596960804111a9e66a4e0b143754");
		requestProperty.put("playerid", "36");
		
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("action", "getSongList");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		body.put("time", sdf.parse("2013-01-01").getTime());
		String d=JsonUtils.encode2Str(body);
		String a=HttpUtils.doPost(url, requestProperty,d);
		System.out.println(a);
	}
}
