package protocol.http;

import game.player.Player;
import game.player.PlayerBean;
import game.player.PlayerCache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;
import common.utils.StringUtils;

@HttpProtocol(Def.PROTOCOL_PLAYER)
public class PlayerAction extends HttpAction {

	@Override
	public String excute(HttpPacket packet) {
		try {
			int playerid = packet.getPlayerid();
			if (playerid != -1) {
				Player p = PlayerCache.getPlayer(playerid);
				if (p != null) {
					if (StringUtils.isNotBlank(packet.getData())) {
						JsonNode node = JsonUtils.decode(packet.getData());
						String attribute = JsonUtils.getString("attribute",node);
						if (StringUtils.isNotBlank(attribute)) {
							String[] attrs = attribute.split("#");
							Map<String, Object> data = new HashMap<String, Object>();
							for (String attr : attrs) {
								PlayerBean bean = p.getBean();
								Field field = bean.getClass().getDeclaredField(attr);
								field.setAccessible(true);
								Object val = field.get(bean);
								data.put(attr, val);
							}
							return JsonRespUtils.success(data);
						}else{
							return JsonRespUtils.success(p.getBean());
						}
					} else {
						return JsonRespUtils.success(p.getBean());
					}
				}
			}
			return JsonRespUtils.fail(Def.CODE_QUERY_PLAYER_FAIL, "Query player data fail.");
		} catch (Exception e) {
			e.printStackTrace();
			return JsonRespUtils.fail(Def.CODE_QUERY_PLAYER_EXCEPTION, "Query player data exception.");
		}
	}
}
