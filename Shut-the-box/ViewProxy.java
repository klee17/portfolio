/**
*Keenan Lee:kbl3236
*handels the view server side
*
*
*
*Uses TCP for the transport layer, this is done because
*its what im more familier with by its nature
*/

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.InputStreamReader;

public class ViewProxy implements ModelListener{
	
	private Socket sock;
	private PrintWriter out;
	private BufferedReader in;
	private ViewListener view;

	/**
	*basic constructor
	*takes a single socket as a paramater
	*/
	public ViewProxy(Socket s)throws IOException{
		sock = s;
		out = new PrintWriter(sock.getOutputStream(), true);
		in = new BufferedReader 
			(new InputStreamReader(sock.getInputStream()));
	}
	
	/**
	*sets the servers ViewListerner
	*takes a valid ViewListener as a paramater
	*/
	public void setViewListener(ViewListener vl){
		if(view == null){
			view = vl;
			new ReaderThread() .start();
		}else{
			view = vl;
		}
	}

	/**
	*not used
	*/
	public void joined() throws IOException{}

	/**
	*tells a player that both players have joined and who they are
	*take two valid name as paraters
	*/
	public void setPlayers(String a, String b){
		out.println("j " + a + " " + b);
		out.flush();
	}

	/**
	*not used
	*/
	public void setPlayerNum(int n) throws IOException{}
	
	/**
	*poor name choice
	*tell the player to exit the game
	*i know its bad but it was easy and im lazy
	*/
	public void startTurn() throws IOException{
		out.println("q");
		out.flush();
	}

	/**
	*tells the player the new values of the die
	*expects 2 valid int 1-6
	*/
	public void die(int x, int y) throws IOException{
		out.println("d " + x + " " + y);
		out.flush();
	}
	/**
	*tells the player that it is nopw thier turn
	*/
	public void checkTurn(int b) throws IOException{
		//System.out.println("its someones turn");
		out.println("t " + b);
		out.flush();
	}
	
	/**
	*tells the player the current score of player p
	*/
	public void getScore(int p, int s) throws IOException{
		out.println("s " + p + " " + s);
		out.flush();
	}

	/**
	*instructs the player to flip tile p
	*to position d
	*/
	public void flipTile(int p, String d) throws IOException{
		//System.out.println("tile " + p +  " was fliped");
		out.println("f " + p + " " + d);
		System.out.println("f " + p + " " + d);
		out.flush();
	}

	/**
	*declares which player won
	*uses a 1 for p1, and 2 for p2, and a 0 for a tie
	*/
	public void winner(int w) throws IOException{
		out.println("w " + w);
		out.flush();
	}

	private class ReaderThread extends Thread{
		public void run(){
			try{
				for(;;){
					String s = in.readLine();
					//joined
					if(s.charAt(0) == 'j'){
						String n = s.substring(2);
						view.join(ViewProxy.this,n);
					//tile flip
					}else if(s.charAt(0) == 't'){
						String a = s.charAt(2)+"";
						int x = Integer.parseInt(a);
						String y = s.substring(4);
						view.flip(x,y);
					//roll
					}else if(s.charAt(0) == 'r'){
						view.roll();
					//turn done
					}else if(s.charAt(0) == 'd'){
						view.done();
					//quit
					}else if(s.charAt(0) == 'q'){
						view.exit();
					}
					//all else ignoured
				}

			}catch(Exception e){}
			finally{
				try{sock.close();}
				catch(IOException e){}
			}
		}
	}
}
