package game.song;

import game.dao.SongDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SongService {
	/**所有歌曲*/
	private static Map<Integer,Song> songMapContent=new ConcurrentHashMap<Integer, Song>();
	/**按照Song id 排序的所用Song list*/
	private static List<Song> songListContent=new ArrayList<Song>();
	
	private static Comparator<Map.Entry<Integer,Song>> mapComparator=new Comparator<Map.Entry<Integer,Song>>() {
		@Override
		public int compare(Entry<Integer,Song> o1, Entry<Integer,Song> o2) {
			return o1.getKey()-o2.getKey(); 
		}
	};
	
	private static Comparator<Song> listComparator=new Comparator<Song>() {
		@Override
		public int compare(Song o1, Song o2) {
			return (int) (o1.getBean().getCreateTime()-o2.getBean().getCreateTime());
		}
	};
	
	/**
	 * 加载所有歌曲
	 */
	public static void initSongContent(){
		List<SongBean> list=SongDao.load();
		if(list!=null&&list.size()>0){
			Map<Integer,Song> mapTemp=new ConcurrentHashMap<Integer, Song>();
			List<Song> listTemp=new ArrayList<Song>();
			for(SongBean bean:list){
				Song song=new Song(bean);
				mapTemp.put(bean.getId(), song);
				listTemp.add(song);
			}
			Collections.sort(listTemp,listComparator);
			songMapContent=mapTemp;
			songListContent=listTemp;
		}
	}
	
	/**
	 * 获取歌曲
	 * @param songid
	 * @return
	 */
	public static SongBean getSong(int songid){
		Song song=songMapContent.get(songid);
		if(song!=null){
			return song.getBean();
		}
		return null;
	}
	
	/**
	 * 获取某个时间点的歌曲列表
	 * @param time
	 * @return
	 */
	public static List<SongBean> getSongBeanList(long time){
		List<SongBean> result=new ArrayList<SongBean>();
		for(Song s:songListContent){
			if(s.getBean().getCreateTime()>=time){
				result.add(s.getBean());
			}
		}
		return result;
	}
	
	/**
	 * 获取所有歌曲
	 * @return
	 */
	public static List<Song> getSongBeanList(){
		return songListContent;
	}
}
