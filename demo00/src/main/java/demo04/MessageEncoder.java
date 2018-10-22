package demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<RequestInfoVO> {
	 
	private static final String DEFAULT_ENCODE = "utf-8";
 
	private static final int MAGIC_NUMBER = 0x0CAFFEE0;
 
	public MessageEncoder() {
	}
 
	@Override
	protected void encode(ChannelHandlerContext ctx, RequestInfoVO msg, ByteBuf out) throws Exception {
 
		@SuppressWarnings("resource")
		ByteBufOutputStream writer = new ByteBufOutputStream(out);
		byte[] body = null;
 
		if (null != msg && null != msg.getBody() && "" != msg.getBody()) {
			body = msg.getBody().getBytes(DEFAULT_ENCODE);
		}
 
		writer.writeInt(MAGIC_NUMBER);
 
		writer.writeByte(1);
 
		writer.writeByte(msg.getType());
 
		writer.writeInt(msg.getSequence());
 
		if (null == body || 0 == body.length) {
			writer.writeInt(0);
		} else {
			writer.writeInt(body.length);
			writer.write(body);
		}
	}
 
}
