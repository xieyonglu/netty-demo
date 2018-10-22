package demo02;

import java.io.File;
import java.io.FileOutputStream;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends /*ChannelHandlerAdapter*/ChannelInboundHandlerAdapter  {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Request request = (Request) msg;
		System.out.println("Server:" + request.getId() + "," + request.getName() + "," + request.getMessage());

		Response response = new Response();
		response.setId(request.getId());
		response.setName("response " + request.getId());
		response.setMessage("响应内容：" + request.getMessage());
		byte[] unGizpData = GzipUtils.unGzip(request.getAttachment());
		char separator = File.separatorChar;
		FileOutputStream outputStream = new FileOutputStream(
				System.getProperty("user.dir") + separator + "recieve" + separator + "1.png");
		outputStream.write(unGizpData);
		outputStream.flush();
		outputStream.close();
		ctx.writeAndFlush(response);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
}
