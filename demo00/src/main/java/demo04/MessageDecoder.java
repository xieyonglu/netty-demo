package demo04;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {
	private static final int MAGIC_NUMBER = 0x0CAFFEE0;
	public MessageDecoder() {
 
	}
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 14) {
			return;
		}
		// 标记开始读取位置
		in.markReaderIndex();
 
		int magic_number = in.readInt();
 
		if (MAGIC_NUMBER != magic_number) {
			ctx.close();
			return;
		}
 
		@SuppressWarnings("unused")
		byte version = in.readByte();
 
		byte type = in.readByte();
		int squence = in.readInt();
		int length = in.readInt();
 
		if (length < 0) {
			ctx.close();
			return;
		}
 
		if (in.readableBytes() < length) {
			// 重置到开始读取位置
			in.resetReaderIndex();
			return;
		}
 
		byte[] body = new byte[length];
		in.readBytes(body);
 
		RequestInfoVO req = new RequestInfoVO();
		req.setBody(new String(body, "utf-8"));
		req.setType(type);
		req.setSequence(squence);
		out.add(req);
	}

}
