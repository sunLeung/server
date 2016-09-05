package protocol.http;

import game.player.Player;
import game.player.PlayerCache;
import game.player.PlayerService;
import game.song.Song;
import game.song.SongBean;
import game.song.SongService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import com.aliyun.common.auth.ServiceSignature;
import com.aliyun.common.comm.RequestMessage;
import com.aliyun.openservices.oss.internal.SignUtils;
import com.fasterxml.jackson.databind.JsonNode;
import common.config.Config;
import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;
import common.utils.StringUtils;

@HttpProtocol(Def.PROTOCOL_RESOURCE)
public class ResourceAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		String action = JsonUtils.getString("action", node);
		if("checkResourceAuth".equals(action)){
			return checkResourceAuth(packet);
		}else if("getPlayerSongs".equals(action)){
			return getPlayerSongs(packet);
		}else if("getSongList".equals(action)){
			return getSongList(packet);
		}else if("querySong".equals(action)){
			return querySong(packet);
		}else if("buySong".equals(action)){
			return buySong(packet);
		}else if("getOSSUploadSign".equals(action)){
			return getOSSUploadSign(packet);
		}
		return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_FAIL, "Can not find action:"+action);
	}
	
	public static String checkResourceAuth(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		int resourceid = JsonUtils.getInt("resourceid", node);
		int playerid=packet.getPlayerid();
		if(resourceid!=-1&&playerid!=-1){
			SongBean song=SongService.getSong(resourceid);
			if(song!=null&&song.getPrice()<=0){
				Map<String, Object> r=new HashMap<String, Object>();
				Player p=PlayerCache.getPlayer(playerid);
				r.put("secret", p.getBean().getSecret());
				return JsonRespUtils.success(r);
			}
			boolean b=PlayerService.hasThisSong(playerid, resourceid);
			if(b){
				Map<String, Object> r=new HashMap<String, Object>();
				Player p=PlayerCache.getPlayer(playerid);
				r.put("secret", p.getBean().getSecret());
				return JsonRespUtils.success(r);
			}
		}
		return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_FAIL, "Player has not this resource.");
	}
	
	/**
	 * 获取歌曲库前多少条歌曲
	 * @param packet
	 * @return
	 */
	public static String getSongList(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		long time = JsonUtils.getLong("time", node);
		if(time!=-1){
			List<SongBean> list=SongService.getSongBeanList(time);
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			for(SongBean bean:list){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("id", bean.getId());
				map.put("name", bean.getName());
				map.put("singer", bean.getSinger());
				map.put("mp3URL", bean.getMp3URL());
				map.put("bpm", bean.getBpm());
				map.put("level", bean.getLevel());
				Player topPlayer=PlayerCache.getPlayer(bean.getTopplayer());
				if(topPlayer!=null){
					map.put("topPlayerName", topPlayer.getBean().getName());
				}else{
					map.put("topPlayerName", "");
				}
				boolean hasRight=false;
				Player player=PlayerCache.getPlayer(packet.getPlayerid());
				if(player!=null){
					List<Integer> ls=player.getSongs();
					if(ls!=null&&ls.contains(bean.getId())){
						hasRight=true;
					}
					//免费歌曲也有权限
					if(bean.getPrice()<=0){
						hasRight=true;
					}
				}
				map.put("topScore", bean.getTopscore());
				map.put("state", bean.getState());
				map.put("price", bean.getPrice());
				map.put("createTime", bean.getCreateTime());
				map.put("md5", bean.getMd5());
				map.put("hasRight", hasRight);
				result.add(map);
			}
			return JsonRespUtils.success(result);
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "Error param 'time'.");
	}
	/**
	 * 获取玩家所有歌曲信息
	 * @param packet
	 * @return
	 */
	public static String getPlayerSongs(HttpPacket packet){
		Player p = PlayerCache.getPlayer(packet.getPlayerid());
		if(p!=null){
			List<Integer> songs=p.getSongs();
			List<SongBean> songlist=new ArrayList<SongBean>();
			if(songs!=null){
				for(Integer songid:songs){
					SongBean s=SongService.getSong(songid);
					if(s!=null)
						songlist.add(s);
				}
			}
			return JsonRespUtils.success(songlist);
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "This player does not exist.");
	}
	
	/**
	 * 获取歌曲数据
	 * @param packet
	 * @return
	 */
	public static String getSongInfo(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		int resourceid = JsonUtils.getInt("resourceid", node);
		if(resourceid!=-1){
			SongBean song=SongService.getSong(resourceid);
			if(song!=null){
				return JsonRespUtils.success(song);
			}
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "This song does not exist.");
	}
	
	
	/**
	 * 歌曲查找
	 * @param packet
	 * @return
	 */
	public static String querySong(HttpPacket packet){
		List<SongBean> result=new ArrayList<SongBean>();
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		String key=JsonUtils.getString("key", node);
		int option=JsonUtils.getInt("option", node);
		if(option==1){//根据作者查找
			for(Song song:SongService.getSongBeanList()){
				String singer=song.getBean().getSinger();
				if(StringUtils.isNotBlank(singer)&&singer.contains(key)){
					result.add(song.getBean());
				}
			}
		}else{//根据歌曲名查找
			for(Song song:SongService.getSongBeanList()){
				String name=song.getBean().getName();
				if(StringUtils.isNotBlank(name)&&name.contains(key)){
					result.add(song.getBean());
				}
			}
		}
		return JsonRespUtils.success(result);
	}
	
	/**
	 * 购买歌曲
	 * @param packet
	 * @return
	 */
	public static String buySong(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		int resourceid = JsonUtils.getInt("resourceid", node);
		int playerid=packet.getPlayerid();
		if(resourceid!=-1&&playerid!=-1){
			SongBean songbean=SongService.getSong(resourceid);
			Player p=PlayerCache.getPlayer(playerid);
			if(songbean!=null&&p!=null){
				int price=songbean.getPrice();
				if(price<=0){
					return JsonRespUtils.success(resourceid);
				}
				for(Integer sid:p.getBean().songs){
					if(sid==resourceid){
						return JsonRespUtils.success(resourceid);
					}
				}
				if(p.incMoney(-price, "buy_song")>=0){
					p.addSong(resourceid);
					return JsonRespUtils.success(resourceid);
				}
			}
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "Buy song fail.");
	}
	
	/**
	 * 获取oss上传权限
	 * @param packet
	 * @return
	 */
	public static String getOSSUploadSign(HttpPacket packet){
		String data=packet.getData();
		JsonNode node=JsonUtils.decode(data);
		String object= JsonUtils.getString("object", node);
		
		if(StringUtils.isNotBlank(object)){
			int playerid=packet.getPlayerid();
			String resourcePath="/"+Config.BUCKET_NAME+"/"+playerid+"/"+object;
			SimpleDateFormat rfc822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
			rfc822DateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
			String date = rfc822DateFormat.format(new Date());
			
			String contentType= JsonUtils.getString("Content-Type", node);
			if(StringUtils.isBlank(contentType)){
				contentType="application/octet-stream";
			}
			
			Map<String,String> headers=new HashMap<String, String>();
			headers.put("Content-Type", contentType);
			headers.put("Date", date);
			
			RequestMessage request = new RequestMessage();
			request.setHeaders(headers);
			String canonicalString = SignUtils.buildCanonicalString("PUT", resourcePath, request, null);
			System.out.println(canonicalString);
			String signature = ServiceSignature.create().computeSignature(Config.ACCESS_KEY, canonicalString);
			
			Map<String,String> result=new HashMap<String, String>();
			result.put("Content-Type", contentType);
			result.put("sign", signature);
			result.put("Date", date);
			result.put("access_id", Config.ACCESS_ID);
			result.put("endpoint", Config.ENDPOINT);
			result.put("bucket_name", Config.BUCKET_NAME);
			result.put("keyname", playerid+"/"+object);
			return JsonRespUtils.success(result);
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "Create sign fail.");
	}

	
}
