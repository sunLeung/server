package game.dao;

import static common.db.DbUtils.dbUtils;
import game.player.Player;
import game.player.PlayerBean;
import game.player.PlayerCache;

import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import common.log.Logger;
import common.log.LoggerManger;


public class PlayerDao {
	private static Logger log=LoggerManger.getLogger();
	
	
	public static int save(PlayerBean bean){
		try {
			return dbUtils.insert(bean);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int update(PlayerBean bean){
		try {
			return dbUtils.update(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 加载玩家数据
	 * @param playerid
	 * @return
	 */
	public static PlayerBean loadById(int playerid){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where id=?", playerid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByEmail(String email){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where email=?", email);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByPhone(String phone){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where phone=?", phone);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByName(String name){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where name=?", name);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByThirdParty(String thirdParty){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where thirdParty=?", thirdParty);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 回写玩家数据
	 */
	public static void flushPlayer(){
		try {
			Map<Integer,Player> map=PlayerCache.getPlayerCacheContent();
			for(Entry<Integer,Player> entry:map.entrySet()){
				Player p=entry.getValue();
				if(p.getUpdateTime()!=-1){
					int rs=dbUtils.update(p.getBean());
					if(rs!=1){
						log.error("flush playerbean errer,playerid is:"+p.getId());
					}else{
						p.setUpdateTime(-1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}
}
