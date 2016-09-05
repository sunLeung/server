package game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import common.db.Pojo;
import common.utils.StringUtils;

public class PlayerBean extends Pojo{
	private static final long serialVersionUID = 1L;
	public String name;
	public String email;
	public String phone;
	/**0女,1男*/
	public int sex;
	public String password;
	public String thirdParty;
	/**用户秘钥*/
	public String secret;
	public String token;
	public String deviceid;
	public AtomicInteger money=new AtomicInteger();
	public List<Integer> songs=new ArrayList<Integer>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getMoney() {
		return money.get();
	}
	public void setMoney(int money) {
		this.money = new AtomicInteger(money);
	}
	public String getSongs() {
		StringBuilder sb=new StringBuilder();
		for(Integer i:songs){
			sb.append(i).append(",");
		}
		return sb.toString();
	}
	public void setSongs(String songs) {
		if(StringUtils.isNotBlank(songs)){
			String[] temp=songs.split(",");
			for(String s:temp){
				this.songs.add(Integer.valueOf(s));
			}
		}
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
