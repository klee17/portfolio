/**
*Keenan Lee:kbl3236
*contains the internal game board
*and handels die rolls and such
*/

public class Box{
	
	private boolean[] tiles;

	private int[] die;

	/**
	*basic constructor for the box
	*take no paramaters
	*/
	public Box(){
		tiles = new boolean[9];
		for(int i = 0; i < tiles.length; ++i){
			tiles[i] = true;
		}
		die = new int[2];
	}


	/**
	*flips internally the tile at position i to s
	*/
	public void flip(int i,String s){
		if(s.equals("down")){
			tiles[i-1] = false;
		}else{
			tiles[i-1] = true;
		}
	}

	/**
	*returns 2 new values for the die rools
	*/
	public int[] roll(){
		die[0] = (int)(Math.random()*6) + 1;
		die[1] = (int)(Math.random()*6) + 1;
		return die;
	}

	/**
	*returns the current score of the box
	*/
	public int getScore(){
		int count = 0;
		for(int i = 0; i < tiles.length; ++i){
			System.out.println(tiles[i]);
			if(tiles[i] == true)
				count += i+1;
		}
		return count;
	}
}
