package game.song;

import common.utils.Def;

public class Song {
	private SongBean bean;
	
	public Song(SongBean bean){
		this.bean=bean;
	}

	public SongBean getBean() {
		return bean;
	}

	public void setBean(SongBean bean) {
		this.bean = bean;
	}
	
	public boolean updateJazzLevel(int level){
		if(level>Def.SONG_MaxLevel_JAZZ){
			return false;
		}
		SongBean bean=this.getBean();
		synchronized (bean) {
			int oldLevel=bean.getLevel();
			level=(level<<(0*8));
			oldLevel &=~(255<<(0*8));
			int r=oldLevel+level;
			bean.setLevel(r);
			return true;
		}
	}
	
	public boolean updatePianoLevel(int level){
		if(level>Def.SONG_MaxLevel_JAZZ){
			return false;
		}
		SongBean bean=this.getBean();
		synchronized (bean) {
			int oldLevel=bean.getLevel();
			level=(level<<(1*8));
			oldLevel &=~(255<<(1*8));
			int r=oldLevel+level;
			bean.setLevel(r);
			return true;
		}
	}
	public boolean updateGuitarLevel(int level){
		if(level>Def.SONG_MaxLevel_JAZZ){
			return false;
		}
		SongBean bean=this.getBean();
		synchronized (bean) {
			int oldLevel=bean.getLevel();
			level=(level<<(2*8));
			oldLevel &=~(255<<(2*8));
			int r=oldLevel+level;
			bean.setLevel(r);
			return true;
		}
	}
	
	public boolean updateBassLevel(int level){
		if(level>Def.SONG_MaxLevel_JAZZ){
			return false;
		}
		SongBean bean=this.getBean();
		synchronized (bean) {
			int oldLevel=bean.getLevel();
			level=(level<<(3*8));
			oldLevel &=~(255<<(3*8));
			int r=oldLevel+level;
			bean.setLevel(r);
			return true;
		}
	}
	
	public void updateState(int posi,boolean value){
		int v = this.getBean().getState();
		if (value)
			v |= posi;
		else
			v &= ~posi;
		this.getBean().setState(v);
	}
}
