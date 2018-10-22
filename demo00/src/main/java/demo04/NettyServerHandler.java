package demo04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


@Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<RequestInfoVO> {
	private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
 
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestInfoVO msg) throws Exception {
		//
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
	}
 
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
	}
 
 
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		
	}

}
