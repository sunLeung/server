package protocol.http;

import game.dao.PlayerDao;
import game.player.Player;
import game.player.PlayerBean;
import game.player.PlayerCache;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;

import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;
import common.utils.SecurityUtils;
import common.utils.StringUtils;

@HttpProtocol(Def.PROTOCOL_REGISTER)
public class RegisterAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		try {
			String data=packet.getData();
			JsonNode node=JsonUtils.decode(data);
			String name = JsonUtils.getString("name",node);
			int sex = JsonUtils.getInt("sex",node);
			String phone = JsonUtils.getString("phone",node);
			String email = JsonUtils.getString("email",node);
			String password1 = JsonUtils.getString("password1",node);
			String password2 = JsonUtils.getString("password2",node);
			
			if(StringUtils.isBlank(name)||sex==-1||(StringUtils.isBlank(phone)&&StringUtils.isBlank(email))||StringUtils.isBlank(password1)||StringUtils.isBlank(password2)){
				return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "The necessary parameters cannot be null.");
			}
			
			if(!password1.equals(password2)){
				return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Two the password is not the same.");
			}
			
			if(password1.length()<6||password1.length()>16){
				return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Password length must between 6 and 16.");
			}
			
			if(StringUtils.isNotBlank(phone)){
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher isNum = pattern.matcher(phone);
				if(!isNum.matches()){
					return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Phone parameter is wrong.");
				}
			}
			
			if(StringUtils.isNotBlank(email)){
				Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
				Matcher isEmail = pattern.matcher(email);
				if(!isEmail.matches()){
					return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Email parameter is wrong.");
				}
			}
			PlayerBean p=PlayerDao.loadByName(name);
			if(p!=null){
				return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Name has been registered.");
			}
			
			p=PlayerDao.loadByPhone(phone);
			if(p!=null){
				return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Phone has been registered.");
			}
			
			p=PlayerDao.loadByEmail(email);
			if(p!=null){
				return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Email has been registered.");
			}
			
			PlayerBean bean=new PlayerBean();
			bean.setDeviceid(packet.getDeviceid());
			bean.setEmail(email);
			bean.setName(name);
			bean.setPassword(SecurityUtils.encryptPassword(password1));
			bean.setSecret(SecurityUtils.createUUIDString());
			bean.setPhone(phone);
			bean.setSex(sex);
			bean.setToken(SecurityUtils.createUUIDString());
			
			bean.setMoney(300);
			
			int id=PlayerDao.save(bean);
			if(id!=-1){
				Player player=PlayerCache.getPlayer(id);
				Map<String,Object> r=new HashMap<String, Object>();
				r.put("id", player.getBean().getId());
				r.put("name", player.getBean().getName());
				r.put("token", player.getBean().getToken());
				return JsonRespUtils.success(r);
			}
			return JsonRespUtils.fail(Def.CODE_REGISTER_FAIL, "Create player fail.");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonRespUtils.fail(Def.CODE_REGISTER_EXCEPTION, "Catch exception.");
		}
	}
	
}
