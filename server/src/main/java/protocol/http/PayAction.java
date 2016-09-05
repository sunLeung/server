package protocol.http;

import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;

@HttpProtocol(Def.PROTOCOL_PAY)
public class PayAction extends HttpAction {

	@Override
	public String excute(HttpPacket packet) {
		
		return null;
	}

}
