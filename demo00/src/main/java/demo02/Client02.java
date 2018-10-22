package demo02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Client02 {

	private static class SingleHodler {
		static final Client02 client = new Client02();
	}

	public static Client02 getInstance() {
		return SingleHodler.client;
	}

	private EventLoopGroup workerGroup;
	private Bootstrap bootstrap;
	private ChannelFuture future;

	private Client02() {
		workerGroup = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
				socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				socketChannel.pipeline().addLast(new ReadTimeoutHandler(5)); // 5秒后未与服务器通信，则断开连接。
				socketChannel.pipeline().addLast(new ClientHandler());
			}
		});
	}

	public void connect() {
		try {
			future = bootstrap.connect("127.0.0.1", 8765).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ChannelFuture getFuture() {
		if (future == null || !future.channel().isActive()) {
			this.connect();
		}
		return future;
	}

	public static void main(String[] args) throws InterruptedException {
		Client02 client = getInstance();
		ChannelFuture future = client.getFuture();

		for (int i = 1; i <= 3; i++) {
			Message message = new Message(i, "pro" + i, "数据信息" + i);
			future.channel().writeAndFlush(message);
			Thread.sleep(4000); // 休眠4秒后再发送数据
		}

		future.channel().closeFuture().sync();

		new Thread(() -> {
			try {
				System.out.println("子线程开始....");
				ChannelFuture f = client.getFuture();
				Message message = new Message(4, "pro" + 4, "数据信息" + 4);
				f.channel().writeAndFlush(message);
				f.channel().closeFuture().sync();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		System.out.println("主线程退出......");
	}
}
