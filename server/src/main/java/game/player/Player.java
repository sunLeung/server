package game.player;

import game.dao.PlayerDao;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import common.log.LoggerManger;
import common.utils.Def;
import common.utils.SecurityUtils;

public class Player {
	private PlayerBean bean;
	private long loadTime;
	private long updateTime;

	public int getId(){
		return bean.getId();
	}
	
	public String getDeviceid() {
		return bean.getDeviceid();
	}
	
	public String getToken() {
		return bean.getToken();
	}
	
	public PlayerBean getBean() {
		return bean;
	}

	public void setBean(PlayerBean bean) {
		this.bean = bean;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getPassword(){
		String pwd=this.getBean().getPassword();
		return SecurityUtils.decryptPassword(pwd);
	}
	
	public void setPassword(String password){
		String pwd=SecurityUtils.encryptPassword(password);
		this.bean.setPassword(pwd);
	}
	
	public List<Integer> getSongs(){
		return this.bean.songs;
	}
	
	public AtomicInteger getMoney(){
		return this.bean.money;
	}
	
	public int incMoney(int m,String log){
		int result=0;
		synchronized (this.bean) {
			int money=this.bean.money.get();
			int moneyAfter=money+m;
			if(moneyAfter<0){
				return -1;
			}
			if(moneyAfter>=Integer.MAX_VALUE){
				this.bean.money.set(Integer.MAX_VALUE);
			}else{
				this.bean.money.set(moneyAfter);
			}
			result=this.bean.money.get();
			PlayerDao.update(bean);
			StringBuilder sbLog=new StringBuilder();
			sbLog.append(log).append("@").append(this.getId()).append("@").append(money).append("@").append(result);
			LoggerManger.getLogger(Def.MONEY_LOG).info(sbLog.toString());
		}
		return result;
	}
	
	/**
	 * 给用户加歌曲
	 * @param songid
	 * @return
	 */
	public int addSong(int songid){
		for(Integer sid:this.bean.songs){
			if(sid==songid){
				return 0;
			}
		}
		this.bean.songs.add(songid);
		return 1;
	}
}
