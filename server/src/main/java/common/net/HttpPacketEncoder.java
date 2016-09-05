package common.net;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Set;

public class HttpPacketEncoder extends MessageToMessageEncoder<FullHttpResponse> {
	@Override
	protected void encode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out){
		try {
			System.out.println("\n===============response head==================");
			Set<String> set=msg.headers().names();
			for(String s:set){
				System.out.println(s+" : "+msg.headers().get(s));
			}
			System.out.println("==============response head end================\n");
			
			String data = msg.content().toString(CharsetUtil.UTF_8);
			
			System.out.println("\n===============response body==================");
			System.out.println(data);
			System.out.println("==============response body end================\n");
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
					msg.getStatus(), Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
			response.headers().add(msg.headers());
			out.add(response);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.close();
		}
	}
}
