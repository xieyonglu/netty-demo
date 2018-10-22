package demo03;

import java.net.InetSocketAddress;

import demo02.MarshallingCodeCFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	public static void main(String[] args) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workerGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sc) throws Exception {
							sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
							sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
							sc.pipeline().addLast(new ClientHeartBeatHandler());
						}
					});
			ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888)).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
