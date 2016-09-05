package common.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

import common.log.Logger;
import common.log.LoggerManger;

public class AdminServer {
	private static Logger logger=LoggerManger.getLogger();
	private static ChannelFuture future;
	private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);;
	private static EventLoopGroup workerGroup = new NioEventLoopGroup();;
	
	public static void startAdminServer(final int port){
		try {
		    ServerBootstrap b = new ServerBootstrap();
		    b.group(bossGroup, workerGroup)
			    .channel(NioServerSocketChannel.class)
			    .childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
					    ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
					    ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
					    ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
					    ch.pipeline().addLast("AdminServerHandler",new AdminServerHandler());
					}
			    });
			future = b.bind(new InetSocketAddress(port)).sync();
			logger.info("Admin server started.Listening:" + port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 
	public static void stopAdminServer(){
		if(future!=null)
			future.channel().close();
		if(bossGroup!=null)
			bossGroup.shutdownGracefully();
		if(workerGroup!=null)
			workerGroup.shutdownGracefully();
	} 
}
