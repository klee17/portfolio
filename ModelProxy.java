/**
*Keenan Lee:kbl3236
*11/6/2013
*the modle proxy for main
*/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.DatagramSocket;

import java.net.Socket;


public class ModelProxy implements ViewListener{

	private DatagramSocket mailbox;
	private SocketAddress destination;
	private ModelListener modelListener;

	private Socket socket;
	private ObjectOutputStream o;
	private PrintWriter out;
	private ObjectInputStream i;
	private BufferedReader in;

	/**
	*constructor 
	*not currently used, but kept in because im parinoied
	*/
	public ModelProxy(DatagramSocket m, SocketAddress d)throws IOException{
		mailbox = m;
		destination = d;
	}

	/**
	*Construcor
	*expects a valid socket
	*/
	public ModelProxy(Socket sock)throws IOException{
		socket = sock;
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader
		(new InputStreamReader(socket.getInputStream()));
	}

	/**
	*Sets the modelListener to mod
	*expects mod to be a vlaid modelListener
	*also starts the ReaderThread
	*/
	public void setModelListener(ModelListener mod){
		modelListener = mod;
		new ReaderThread().start();
	}
	
	/**
	*the join command
	*sends out join and the players name 
	*at begining of game
	*/
	public void join(String name) throws IOException{
		out.println("join " + name);
		out.flush();
	}

	/**
	*flips tile x to state y
	*this is then sent to the server
	*/
	public void flip(int x, String y) throws IOException{
		out.print("tile " + x + " " + y + "\n");
		out.flush();
	}

	/**
	*tells the server to roll the die
	*/
	public void roll() throws IOException{
		out.print("roll" + "\n");
		out.flush();
	}

	/**
	*declares the end of a turn
	*/
	public void done() throws IOException{
		out.println("done");
		out.flush();
	}

	/**
	*ends and exits the program
	*notifies the server so it can notify the other player
	*/
	public void exit() throws IOException{
		out.println("quit");
		out.flush();
	}

	/**
	*massive class for handleing things from the server
	*logic is confusing at best
	*/
	private class ReaderThread extends Thread{
		/**
		*run is required for threaded classes
		*will start the listening process
		*/
		public void run(){
			String s = "";
			try{
				for(;;){
					s = in.readLine();
					//joined
					if(s.contains("joined")){
						if(s.contains("1"))
							modelListener.setPlayerNum(1);
						else
							modelListener.setPlayerNum(2);
						String[] q = s.split(" ");
						modelListener.setPlayers(q[1],q[2]);
					//new dice
					}else if(s.contains("dice")){
							String tX = s.charAt(5)+"";
							String tY = s.charAt(7)+"";
							int x = Integer.parseInt(tX);
							int y = Integer.parseInt(tY);
							modelListener.die(x,y);
					//other player quit
					}else if(s.contains("quit")){
						try{
							socket.close();
							System.exit(1);
						}catch(IOException ex){}
					//getting a score
					}else if(s.contains("score")){
						String t = s.charAt(6)+"";
						int p = Integer.parseInt(t);
						String q = s.substring(8);
						int b = Integer.parseInt(q);
						modelListener.getScore(p,b);
					//processing a turn
					}else if(s.contains("turn")){
						String t = s.charAt(5)+"";
						int b = Integer.parseInt(t);
						modelListener.checkTurn(b);
					//updates tiles on my board
					}else if(s.contains("tile")){
						String t = s.charAt(5)+"";
						int b = Integer.parseInt(t);
						String q = s.substring(7);
						modelListener.flipTile(b,q);
					//declared winner
					}else if(s.contains("win")){
						String t = s.charAt(4)+"";
						int b = Integer.parseInt(t);
						modelListener.winner(b);
					}
				}
			}catch(IOException exc){}
			finally{ try{
					socket.close();
				}catch(IOException exc){}
			}
		}
	
	}

}
