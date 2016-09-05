package protocol.admin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.JsonNode;
import common.net.AdminAction;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;
import common.utils.StringUtils;

public class GmAction implements AdminAction {

	@Override
	public String excute(ChannelHandlerContext ctx, FullHttpRequest request) {
		try {
			String req = request.content().toString(CharsetUtil.UTF_8);
			if(StringUtils.isNotBlank(req)){
				JsonNode node=JsonUtils.decode(req);
				String clazz="common.admin.AdminService";
				String method=JsonUtils.getString("method", node);
				String data="";
				if(node.has("data")&&node.hasNonNull("data")&&node.get("data").isObject()){
					data=node.get("data").toString();
				}
				if(StringUtils.isNotBlank(method)&&StringUtils.isBlank(data)){
					Class c=Class.forName(clazz);
					Method m=c.getDeclaredMethod(method);
					Object r=m.invoke(c);
					String rstr="";
					if(r!=null)rstr=r.toString();
					return JsonRespUtils.success(rstr);
				}else if(StringUtils.isNotBlank(method)&&StringUtils.isNotBlank(data)){
					Class c=Class.forName(clazz);
					Method m=c.getDeclaredMethod(method,String.class);
					Object r=m.invoke(c, data);
					String rstr="";
					if(r!=null)rstr=r.toString();
					return JsonRespUtils.success(rstr);
				}
			}
			return JsonRespUtils.fail(Def.CODE_FAIL, "GmAction execute fail.");
		} catch (Exception e) {
			return JsonRespUtils.fail(Def.CODE_EXCEPTION, "GmAction catch exception.");
		}
	}

}
