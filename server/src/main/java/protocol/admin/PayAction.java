package protocol.admin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import common.net.AdminAction;

public class PayAction implements AdminAction {

	@Override
	public String excute(ChannelHandlerContext ctx, FullHttpRequest request) {
		try {
			int playerid=Integer.valueOf(request.headers().get("playerid"));
			int money=Integer.valueOf(request.headers().get("money"));
			int unionid=Integer.valueOf(request.headers().get("unionid"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
