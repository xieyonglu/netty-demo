package demo00;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ����˴���ͨ��. ResponseServerHandler �̳��� ChannelHandlerAdapter��
 * �����ʵ����ChannelHandler�ӿڣ� ChannelHandler�ṩ������¼�����Ľӿڷ����� Ȼ������Ը�����Щ������
 * ���ڽ���ֻ��Ҫ�̳�ChannelHandlerAdapter����������Լ�ȥʵ�ֽӿڷ����� ������������Ӧ
 */
public class ResponseServerHandler extends ChannelInboundHandlerAdapter {
	/**
	 * �������Ǹ�����chanelRead()�¼��������� ÿ���ӿͻ����յ��µ�����ʱ�� ������������յ���Ϣʱ�����ã�
	 * ChannelHandlerContext�����ṩ���������� ʹ���ܹ��������ָ�����I/O�¼��Ͳ�����
	 * �������ǵ�����write(Object)���������ֵذѽ��ܵ�����Ϣд��
	 * 
	 * @param ctx
	 *            ͨ���������������Ϣ
	 * @param msg
	 *            ���յ���Ϣ
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf in = (ByteBuf) msg;
		System.out.println(in.toString(CharsetUtil.UTF_8));
		ctx.write(msg);
		// cxt.writeAndFlush(msg)
		// ��ע�⣬�����Ҳ�����Ҫ��ʽ���ͷţ���Ϊ�ڽ����ʱ��netty�Ѿ��Զ��ͷ�
		// ReferenceCountUtil.release(msg);
	}

	/**
	 * ctx.write(Object)��������ʹ��Ϣд�뵽ͨ���ϣ� �������������ڲ�������Ҫ����ctx.flush()�������ѻ�����������ǿ�������
	 * �����������channelRead�������ø�����cxt.writeAndFlush(msg)�Դﵽͬ����Ŀ��
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * ����������ڷ����쳣ʱ����
	 *
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		/***
		 * �����쳣�󣬹ر�����
		 */
		cause.printStackTrace();
		ctx.close();
	}
}
