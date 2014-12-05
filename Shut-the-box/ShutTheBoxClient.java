/**
*Keenan Lee:kbl3236
*11/5/2013
*shut the box client
*/
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.Socket;

public class ShutTheBoxClient{

	public static void main(String[] args) throws Exception{
		if(args.length != 3){
			System.err.println("Error, invalid number of command line args");
			return;
		}
		String host =  args[0];
		int port = Integer.parseInt (args[1]);
		String playerName = args[2];
		
		Socket sock = new Socket();
		sock.connect(new InetSocketAddress(host,port));
		ShutTheBoxUI veiw = new ShutTheBoxUI();
		ModelProxy proxy = new ModelProxy(sock);
		veiw.setViewListener(proxy);
		veiw.setName(playerName);
		proxy.setModelListener(veiw);
		veiw.joined();
	}

}
