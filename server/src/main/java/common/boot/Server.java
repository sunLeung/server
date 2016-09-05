package common.boot;

import game.song.SongService;
import common.config.Config;
import common.config.LinuxPrinter;
import common.config.WindowsPrinter;
import common.db.FlushDB;
import common.log.Logger;
import common.log.LoggerManger;
import common.net.AdminServer;
import common.net.HttpProtocolContent;
import common.net.HttpServer;
import common.utils.TimerManagerUtils;

public class Server {
	private static Logger logger=LoggerManger.getLogger();
	public static boolean isTrace=true;
	
	public static void main(String[] args) {
		try {
			long s = System.currentTimeMillis();
			String os = System.getProperty("sun.desktop");
			System.out.println("[System INFO] Running desktop is " + os);
			if ("windows".equals(os)) {
				System.setOut(new WindowsPrinter(System.out));
				System.setErr(new WindowsPrinter(System.err));
			} else {
				LinuxPrinter pl = new LinuxPrinter(System.out);
				System.setOut(pl);
				System.setErr(pl);
				new Thread(pl).start();
			}
			init();
			HttpServer.startHttpServer(4000);
			AdminServer.startAdminServer(4001);
			logger.info("GameServer started.Use seconds "+ (System.currentTimeMillis()-s)/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void init(){
		Config.init();
		LoggerManger.initLoggerConfig(Config.LOGGER_CONFIG);
		HttpProtocolContent.init();
		SongService.initSongContent();
		FlushDB.init();
	}
	
	public static void stop(){
		try {
			FlushDB.flush();
			LoggerManger.stopFileWriter();
			TimerManagerUtils.destroyed();
			HttpServer.stopHttpServer();
			AdminServer.stopAdminServer();
			System.out.println("[INFO] GameServer stoped.");
			Config.PRINTER_RUN=false;
			Thread.sleep(1000);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
