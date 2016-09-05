package game.dao;

import static common.db.DbUtils.dbUtils;
import game.song.SongBean;

import java.sql.SQLException;
import java.util.List;

import common.log.Logger;
import common.log.LoggerManger;

public class SongDao {
	private static Logger log=LoggerManger.getLogger();
	
	public static List<SongBean> load(){
		List<SongBean> list=null;
		try {
			list=dbUtils.query(SongBean.class, "");
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public static int save(SongBean bean){
		try {
			return dbUtils.insert(bean);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
}
