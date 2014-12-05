/**
*Keenan Lee:kbl3236
*3/12/2013
*main method for the server
*/

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ShutTheBoxServer{

	/**
	*Main method for the server, expects 2 command line arguments
	*uses TCP/IP for connection functions
	*/
	public static void main(String[] args) throws Exception{
		if(args.length != 2){
			System.err.println("Usage: java ShutTheBoxClient <host> <port>");
			System.exit(1);
		}
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		ServerSocket sock = new ServerSocket();
		sock.bind(new InetSocketAddress(host,port));

		SessionManager man = new SessionManager();

		for(;;){
			Socket socket = sock.accept();	
			ViewProxy prox = new ViewProxy(socket);
			prox.setViewListener(man);
		}
	}
}
