package protocol.http;

import game.player.Player;
import game.player.PlayerService;

import com.fasterxml.jackson.databind.JsonNode;

import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;
import common.utils.StringUtils;

@HttpProtocol(Def.PROTOCOL_LOGIN)
public class LoginAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		try {
			String data=packet.getData();
			JsonNode node=JsonUtils.decode(data);
			String identity = JsonUtils.getString("identity", node);
			String password = JsonUtils.getString("password",node);
			if(StringUtils.isNotBlank(identity)&&StringUtils.isNotBlank(password)){
				Player p=PlayerService.login(identity, password, packet.getDeviceid());
				if(p!=null){
					return JsonRespUtils.success(p.getBean());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return JsonRespUtils.fail(Def.CODE_LOGIN_EXCEPTION, "Login exception");
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "The password is incorrect.");
	}
	
}
