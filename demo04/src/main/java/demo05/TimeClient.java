package demo05;

import demo02.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


public class TimeClient {

	public void connect(int port, String host) throws Exception {
		// ���ÿ͑���NIO�Q�̽M
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			 .option(ChannelOption.TCP_NODELAY, true)
			 .handler(new ChannelInitializer<SocketChannel>() {
				 @Override
				 public void initChannel(SocketChannel ch) throws Exception {
					 ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					 ch.pipeline().addLast(new StringDecoder());
					 ch.pipeline().addLast(new TimeClientHandler());
				 }
			 });
			
			// ���𮐲����Ӳ���
			ChannelFuture f = b.connect(host, port).sync();
			
			// �ȴ��ͻ�����·�ر�
			f.channel().closeFuture().sync();
		} finally {
			// �����˳����ͷ�NIO�߳���
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		if(args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch(NumberFormatException e) {
				// ����Ĭ��ֵ
			}
		}
		new TimeClient().connect(port, "127.0.0.1");
	}
	
}


