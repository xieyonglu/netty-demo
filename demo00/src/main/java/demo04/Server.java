package demo04;

public class Server {
	public static void main(String[] args) {
		try {
			new NettyServerBootstrap(9999);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}