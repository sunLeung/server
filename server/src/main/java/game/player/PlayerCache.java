package game.player;

import game.dao.PlayerDao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerCache {
	private static Map<Integer,Player> playerCacheContent=new ConcurrentHashMap<Integer, Player>();

	public static Map<Integer, Player> getPlayerCacheContent() {
		return playerCacheContent;
	}

	public static void setPlayerCacheContent(Map<Integer, Player> playerCacheContent) {
		PlayerCache.playerCacheContent = playerCacheContent;
	}
	
	/**
	 * 获取玩家对象
	 * @param playerid
	 * @return
	 */
	public static Player getPlayer(int playerid){
		Player p=getPlayerCacheContent().get(playerid);
		if(p==null){
			PlayerBean bean=PlayerDao.loadById(playerid);
			if(bean!=null){
				p=initPlayer(bean);
			}
		}else{
			p.setUpdateTime(System.currentTimeMillis());
		}
		return p;
	}
	
	/**
	 * 初始化玩家
	 * @param bean
	 * @return
	 */
	public static Player initPlayer(PlayerBean bean){
		Player p=new Player();
		p.setBean(bean);
		p.setLoadTime(System.currentTimeMillis());
		p.setUpdateTime(System.currentTimeMillis());
		getPlayerCacheContent().put(bean.getId(), p);
		return p;
	}
}
