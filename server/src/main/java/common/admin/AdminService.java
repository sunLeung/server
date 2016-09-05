package common.admin;

import game.dao.SongDao;
import game.song.SongBean;

import java.util.HashMap;
import java.util.Map;

import common.boot.Server;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;

public class AdminService {
	/**
	 * 停服
	 */
	public static void stopServer(){
		Server.stop();
	}
	
	/**
	 * 创建新歌曲
	 * @param s
	 * @return
	 */
	public static String createSong(String s){
		System.out.println("do createSong method.");
		SongBean obj=(SongBean)JsonUtils.objectFromJson(s, SongBean.class);
		if(obj!=null){
			int i=SongDao.save(obj);
			if(i>0){
				Map<String,Object> result=new HashMap<String, Object>();
				result.put("id", i);
				return JsonRespUtils.success(result);
			}
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "create song fail.");
	}
	
}	
