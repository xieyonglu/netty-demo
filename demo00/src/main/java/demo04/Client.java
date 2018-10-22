package demo04;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Client {
	public static void main(String[] args) throws Exception {
		NettyClientBootstrap bootstrap = new NettyClientBootstrap(9999, "127.0.0.1");
		int i = 1;
 
		while (true) {
			TimeUnit.SECONDS.sleep(2);
			RequestInfoVO req = new RequestInfoVO();
			req.setSequence(123456);
			req.setType((byte) 1);
			req.setSequence(0);
			req.setBody(String.valueOf((new Date()).getTime()));
			bootstrap.getSocketChannel().writeAndFlush(req);
			i++;
		}
	}
}
