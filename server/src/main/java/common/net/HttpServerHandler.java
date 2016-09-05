package common.net;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import game.player.PlayerService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import common.utils.Def;
import common.utils.HttpRespUtils;
import common.utils.StringUtils;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		if(msg instanceof  HttpPacket){
			HttpPacket packet=(HttpPacket)msg;
			if(StringUtils.isBlank(packet.getDeviceid())){
				HttpRespUtils.responseFail(ctx, Def.CODE_LOGIN_FAIL,"Deviceid must not be null.");
				return;
			}
			int protocol=packet.getProtocol();
			if(protocol>=0x0a){
				//验证玩家是否已经登录
				if(StringUtils.isBlank(packet.getToken())||!PlayerService.authPlayer(packet.getPlayerid(), packet.getDeviceid(), packet.getToken())){
					HttpRespUtils.responseFail(ctx, Def.CODE_LOGIN_FAIL,"Verify login authentication fail,please login again.");
					return;
				}
			}
			
			HttpAction action=HttpProtocolContent.httpProtocolContent.get(protocol);
			if(action!=null){
				HttpAction clone=action.clone();
				String result=clone.handle(packet);
				if(result==null)result="";
				FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
						HttpResponseStatus.OK, Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
				response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
				response.headers().set(HttpHeaders.Names.CONNECTION, "close");
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, result.getBytes().length);
				ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
				return;
			}
		}
		HttpRespUtils.responseFail(ctx, Def.CODE_ROUTE_FAIL,"Protocal not found.");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
