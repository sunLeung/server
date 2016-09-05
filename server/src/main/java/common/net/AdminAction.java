package common.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;


public interface AdminAction{
	public String excute(ChannelHandlerContext ctx, FullHttpRequest request);
}
