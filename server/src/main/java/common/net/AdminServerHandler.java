package common.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

import protocol.admin.GmAction;
import protocol.admin.PayAction;

import common.config.Config;
import common.utils.Def;
import common.utils.HttpRespUtils;

public class AdminServerHandler extends ChannelInboundHandlerAdapter {
	private static Map<String,AdminAction> actions=new HashMap<String, AdminAction>();
	
	static{
		actions.put("admin", new GmAction());
		actions.put("pay", new PayAction());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		try {
			if(msg instanceof  FullHttpRequest){
				FullHttpRequest request=(FullHttpRequest)msg;
				if (!request.getDecoderResult().isSuccess()) {
					ctx.close();
					return;
				}
				String security=request.headers().get("security");
				if(Config.SECURITY.equals(security)){
					String uri=request.getUri();
					String path="";
					int index = uri.indexOf('/', 1);
					if (index == -1) {
						path = uri.substring(1);
					} else {
						path = uri.substring(1, index);
					}
					AdminAction action=actions.get(path);
					if(action!=null){
						String result=action.excute(ctx, request);
						HttpRespUtils.response(ctx, result);
					}else{
						HttpRespUtils.responseFail(ctx, Def.CODE_FAIL,"Request handler not found.");
					}
				}else{
					HttpRespUtils.responseFail(ctx, Def.CODE_FAIL,"Unauthorized request.");
				}
			}else{
				HttpRespUtils.responseFail(ctx, Def.CODE_FAIL,"It is not a FullHttpRequest.");
			}
		} catch (Exception e) {
			HttpRespUtils.responseFail(ctx, Def.CODE_EXCEPTION,"Parse request exception.");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
