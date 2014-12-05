/**
*Keenan Lee:kbl3236
*handles some internal work for each game, and its box
*keeps track of the score, die rolls and such of the game
*passes commands off to others to deal with
*/

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;


public class ShutTheBoxModel implements ViewListener{

	private Box b;
	private String player1;
	private String player2;
	private int turn;
	private int s1;
	private int s2;
	private LinkedList<ModelListener> listen = 
		new LinkedList<ModelListener>();
	
	/**
	*basic constructor fot the Model
	*takes no paramters
	*/
	public ShutTheBoxModel(){
		b = new Box();
		turn = 1;
	}

	/**
	*adds a ModleListener to this
	*/
	public synchronized void addModelListener(ModelListener ml){
		//try{
			listen.add(ml);
		//}catch(IOException e){}
	}

	/**
	*does nothing in this class
	*/
	public void join(ViewProxy prox, String n)throws IOException{}

	/**
	*internally flips the tile
	*then informs the clients
	*/
	public void flip(int x, String y) throws IOException{
		//System.out.println(" im a getting a " +  y);
		b.flip(x,y);
		Iterator<ModelListener> iter = listen.iterator();
		while(iter.hasNext()){
			ModelListener lis = iter.next();
			try{
				lis.flipTile(x,y);
			}catch(IOException e){
				iter.remove();
			}
		}
	}

	/**
	*internally rolls the die 
	*then informs the clients of the results
	*/
	public void roll() throws IOException{
		int[] die = b.roll();
		Iterator<ModelListener> iter = listen.iterator();
		while(iter.hasNext()){
			ModelListener lis = iter.next();
			try{
				lis.die(die[0],die[1]);
			}catch(IOException e){
				iter.remove();
			}
		}
	}

	/**
	*handles the case of a finnished turn
	*either ends game and gets scores
	*or tells player 2 to start
	*/
	public void done() throws IOException{
		Iterator<ModelListener> iter = listen.iterator();
		if(turn == 1){
			++turn;
			s1 = b.getScore();
			System.out.println(s1);
			while(iter.hasNext()){
				ModelListener lis = iter.next();
				try{
					lis.getScore(1,s1);
					lis.checkTurn(turn);
				}catch(IOException e){
					iter.remove();
				}
			}

		}else if(turn == 2){
			turn = 1;
			s2 = b.getScore();
			System.out.println(s2 + "!!!");
			while(iter.hasNext()){
				ModelListener lis = iter.next();
				try{
					lis.getScore(2,s2);
					if(s1<s2)
						lis.winner(1);
					else if(s1 == s2)
						lis.winner(0);
					else
						lis.winner(2);
					lis.checkTurn(turn);
				}catch(IOException e){
					iter.remove();
				}
			}
			s2 = 0;
			s1 = 0;
		}
	}

	/**
	*used when a client has exited
	*informs othe client about it
	*/
	public void exit() throws IOException{
		Iterator<ModelListener> iter = listen.iterator();
		while(iter.hasNext()){
			ModelListener lis = iter.next();
			try{
				lis.startTurn();
			}catch(IOException e){
				iter.remove();
			}
		}
	}

	/**
	*informs both clients that there are 2 players
	*and then begins the game
	*/
	public void joined(){
		Iterator<ModelListener> iter = listen.iterator();
		while(iter.hasNext()){
			ModelListener lis = iter.next();
			try{
				lis.setPlayers(player1,player2);
				lis.checkTurn(turn);
			}catch(IOException e){
				iter.remove();
			}

		}

	}

	/**
	*allows player one to be named
	*/
	public void player1(String n){
		player1 = n;
	}

	/**
	*allows player 2 to be named
	*/
	public void player2(String n){
		player2 = n;
	}
}
