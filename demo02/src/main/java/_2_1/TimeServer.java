package _2_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import _2_2.TimeServerHandler;

/**
 * 
 * @author lilinfeng
 * @date 2014年2月14日
 * @version 1.0
 *
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {
		int port = 8080;
		
		if(args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				// 采用默認值
			}
		}
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port: " + port);
			Socket socket = null;
			while(true) {
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		} finally {
			if(server != null) {
				System.out.println("The time server close");
				server.close();
				server = null;
			}
		}
	}
	
}
