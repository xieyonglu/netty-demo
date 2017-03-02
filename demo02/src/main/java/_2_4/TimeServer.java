package _2_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import _2_2.TimeServerHandler;
import _2_5.TimeServerHandlerExecutePool;


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
			
			TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000); // 創建I/O任務綫程池
			
			while(true) {
				socket = server.accept();
				//new Thread(new TimeServerHandler(socket)).start();
				singleExecutor.execute(new TimeServerHandler(socket));
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
