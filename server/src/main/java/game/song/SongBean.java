package game.song;

import common.db.Pojo;

public class SongBean extends Pojo{
	private static final long serialVersionUID = 1L;
	/**歌曲名字*/
	private String name;
	/**歌手名字*/
	private String singer;
	/**歌曲mp3地址*/
	private String mp3URL;
	/**一分钟节拍数量*/
	private int bpm;
	/**歌曲难度*/
	private int level;
	/**最高分玩家*/
	private int topplayer;
	/**最高分数*/
	private int topscore;
	/**歌曲状态容器 0=是否支持爵士鼓,1=是否支持钢琴,2=是否支持吉他,3=是否支持bass 4=是否对外发布*/
	private int state;
	/**歌曲价格*/
	private int price;
	/**备注字段*/
	private String remark;
	/**歌曲创建日期*/
	private long createTime;
	/**曲谱md5值*/
	private String md5;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getMp3URL() {
		return mp3URL;
	}
	public void setMp3URL(String mp3url) {
		mp3URL = mp3url;
	}
	public int getBpm() {
		return bpm;
	}
	public void setBpm(int bpm) {
		this.bpm = bpm;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getTopplayer() {
		return topplayer;
	}
	public void setTopplayer(int topplayer) {
		this.topplayer = topplayer;
	}
	public int getTopscore() {
		return topscore;
	}
	public void setTopscore(int topscore) {
		this.topscore = topscore;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
