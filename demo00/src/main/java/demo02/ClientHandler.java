package demo02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends /*ChannelHandlerAdapter*/SimpleChannelInboundHandler<ByteBuf> {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, /*Object msg*/ ByteBuf in) throws Exception {
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
//		Response response = (Response) msg;
//		System.out.println("Client:" + response.getId() + "," + response.getName() + "," + response.getMessage());
	}
}
