package common.db;

import game.dao.PlayerDao;

import java.util.concurrent.TimeUnit;

import common.config.Config;
import common.log.Logger;
import common.log.LoggerManger;
import common.utils.TimerManagerUtils;

public class FlushDB {
	private static Logger logger=LoggerManger.getLogger();
	public static void flush(){
		logger.info("start flush db.");
		PlayerDao.flushPlayer();
		logger.info("end flush db.");
	}
	
	public static void init(){
		TimerManagerUtils.schedule(new Runnable() {
			@Override
			public void run() {
				flush();
			}
		}, Config.FLUSH_MINUTE, TimeUnit.MINUTES);
	}
}
