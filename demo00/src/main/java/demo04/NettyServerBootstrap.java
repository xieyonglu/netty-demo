package demo04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerBootstrap {
	 
	private static final Logger logger = LoggerFactory.getLogger(NettyServerBootstrap.class);
	private Integer port;
	private SocketChannel socketChannel;
	public NettyServerBootstrap(Integer port) throws Exception {
		this.port = port;
		bind(port);
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public SocketChannel getSocketChannel() {
		return socketChannel;
	}
	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	private void bind(int serverPort) throws Exception {
		// 连接处理group
		EventLoopGroup boss = new NioEventLoopGroup();
		// 事件处理group
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		// 绑定处理group
		bootstrap.group(boss, worker);
		bootstrap.channel(NioServerSocketChannel.class);
		// 保持连接数
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024 * 1024);
		// 有数据立即发送
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		// 保持连接
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
       // 处理新连接
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				// 增加任务处理
				ChannelPipeline p = sc.pipeline();
				p.addLast(new MessageDecoder(), new MessageEncoder(), new NettyServerHandler());
			 }
		});
 
		ChannelFuture f = bootstrap.bind(serverPort).sync();
		if (f.isSuccess()) {
			logger.info("long connection started success");
		} else {
			logger.error("long connection started fail");
		}
	}
}
