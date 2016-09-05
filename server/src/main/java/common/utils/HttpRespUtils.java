package common.utils;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

public class HttpRespUtils {
	
	public static void response(ChannelHandlerContext ctx,String data){
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				HttpResponseStatus.OK, Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set(HttpHeaders.Names.CONNECTION, "close");
		response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, data.getBytes().length);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void responseFail(ChannelHandlerContext ctx,int code,String msg){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		String data=JsonUtils.encode2Str(map);
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				HttpResponseStatus.OK, Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set(HttpHeaders.Names.CONNECTION, "close");
		response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, data.getBytes().length);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void responseSuccess(ChannelHandlerContext ctx,Object obj){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", Def.CODE_SUCCESS);
		map.put("data", obj);
		String data=JsonUtils.encode2Str(map);
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				HttpResponseStatus.OK, Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set(HttpHeaders.Names.CONNECTION, "close");
		response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, data.getBytes().length);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	
	
}
