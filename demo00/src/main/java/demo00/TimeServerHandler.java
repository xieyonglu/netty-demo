package demo00;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class TimeServerHandler extends /* ChannelHandlerAdapter */ChannelInboundHandlerAdapter {
	/**
	 * channelActive()�������������ӱ���������׼������ͨ��ʱ�����á�
	 * �����������������������һ������ǰʱ���32λ������Ϣ�Ĺ���������
	 *
	 * @param ctx
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		/*
		 * Scanner cin=new Scanner(System.in); System.out.println("�����뷢����Ϣ��");
		 * String name=cin.nextLine();
		 */
		String name = "HelloWorld!";
		/**
		 * Ϊ�˷���һ���µ���Ϣ��������Ҫ����һ�����������Ϣ���µĻ��塣
		 * ��Ϊ������Ҫд��һ��32λ�����������������Ҫһ��������4���ֽڵ�ByteBuf��
		 * ͨ��ChannelHandlerContext.alloc()�õ�һ����ǰ��ByteBufAllocator�� Ȼ�����һ���µĻ��塣
		 */
		final ByteBuf time = ctx.alloc().buffer(4);
		time.writeBytes(name.getBytes());
		/***
		 * ������һ��������Ҫ��дһ�������õ���Ϣ
		 * �����ǵ�һ�ȣ�flip���ģ��ѵ�����ʹ��NIO������Ϣʱ���ǵ���java.nio.ByteBuffer.flip()��
		 * ByteBuf֮����û�����������Ϊ������ָ�룬 һ����Ӧ������һ����Ӧд������
		 * ������ByteBuf��д�����ݵ�ʱ��дָ��������ͻ����ӣ� ͬʱ��ָ�������û�б仯��
		 * ��ָ��������дָ�������ֱ��������Ϣ�Ŀ�ʼ�ͽ�����
		 * �Ƚ�������NIO���岢û���ṩһ�ּ��ķ�ʽ���������Ϣ���ݵĿ�ʼ�ͽ�β�� ���������flip������
		 * �������ǵ���flip����������û�����ݻ��ߴ������ݱ�����ʱ��
		 * �������������������һ�����󲻻ᷢ����Netty�ϣ� ��Ϊ���Ƕ��ڲ�ͬ�Ĳ��������в�ͬ��ָ�롣
		 * ��ᷢ��������ʹ�÷�����������̱�ø��ӵ����ף� ��Ϊ���Ѿ�ϰ��һ��û��ʹ��flip�ķ�ʽ��
		 * ����һ������Ҫע�����ChannelHandlerContext.write()(��writeAndFlush())�����᷵��һ��ChannelFuture����
		 * һ��ChannelFuture������һ����û�з�����I/O������
		 * ����ζ���κ�һ������������������ϱ�ִ�У� ��Ϊ��Netty�����еĲ��������첽�ġ�
		 * �������Ҫ��write()�������ص�ChannelFuture��ɺ����close()������
		 * Ȼ������д�����Ѿ��������֪ͨ���ļ����ߡ�
		 */
		final ChannelFuture f = ctx.writeAndFlush(time); // (3)
		/**
		 * ��һ��д�����Ѿ���������֪ͨ�����ǣ�
		 * ���ֻ��Ҫ�򵥵��ڷ��ص�ChannelFuture������һ��ChannelFutureListener��
		 * �������ǹ�����һ��������ChannelFutureListener�������ڲ������ʱ�ر�Channel��
		 */
		f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				assert f == future;
				/***
				 * ��ע��,close()����Ҳ���ܲ�������رգ���Ҳ�᷵��һ��ChannelFuture��
				 */
				ctx.close();
			}
		});
	}

	// ���ս��
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("client:" + buf.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
