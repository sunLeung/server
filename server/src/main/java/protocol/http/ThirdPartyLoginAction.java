package protocol.http;

import game.player.Player;
import game.player.PlayerService;

import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;

@HttpProtocol(Def.PROTOCOL_THIRDPARTY_LOGIN)
public class ThirdPartyLoginAction extends HttpAction {

	@Override
	public String excute(HttpPacket packet) {
		try {
			String data = packet.getData();
			Player p = PlayerService.thirdPartylogin(packet.getDeviceid(),packet.getIp(), data);
			if (p != null) {
				return JsonRespUtils.success(p.getBean());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return JsonRespUtils.fail(Def.CODE_LOGIN_EXCEPTION,
					"Login exception");
		}
		return JsonRespUtils.fail(Def.CODE_FAIL, "Login Fail");
	}

}
