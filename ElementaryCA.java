/**
*Keenan Lee: kbl3236
*10/9/2013
*the main class for lab2
*
*
*/

import edu.rit.pj2.Task;
import edu.rit.pj2.Loop;
import edu.rit.pj2.IntVbl;

public class ElementaryCA extends Task{
	private String rule;
	private int steps;
	private int index[];
	private int min[];
	private int max[];
	private int stepIn[];
	private int sCount;

	/**
	*main method, everything runs out of it
	*its poor design style but i dont care
	*
	*/
	public void main(String[] args){
		if(args.length < 3){
			System.err.println("Error, incorect number of args");
			return;
		}
		rule = args[0];
		try{
			index = new int[Integer.parseInt(args[1])];
			steps = Integer.parseInt(args[2]);
			for(int i = 3; i < args.length; ++i){
				int t = Integer.parseInt(args[i]);
				index[t] = 1;
			}
		}catch(NumberFormatException e){}
		max = new int[2];
		min = new int[2];
		max[0] = getCount();
		max[1] = 0;
		min[0] = getCount();
		min[1] = 0;
		sCount = 0;
		stepIn = new int[index.length];
	

		for(int i = 0; i < steps; ++i){
		sCount = i;
		stepIn = new int[index.length];
		int temp = getCount();
		if(temp > max[0]){
			max[0] = temp;
			max[1] = sCount;
		}
		if(temp < min[0]){
			min[0] = temp;
			min[1] = sCount;
		}
		parallelFor(0, index.length - 1).exec(new Loop()
			{
			/**
			*Method inherited and used at begining
			*currently not used but may be in the future
			*/
			public void start(){
			}
			
			/**
			*Handles all logic and work for the 
			*next step in index[i]
			*runs in parallel
			*/
			public void run(int i){
				int iLeft;
				int iRight;
				int temp = index[i];
				int ti = 0;
				if(i == 0)
					iLeft = index[index.length - 1];
				else
					iLeft = index[i - 1];
				if(i == index.length - 1)
					iRight = index[0];
				else
					iRight = index[i + 1];

				if(iLeft == 0 && temp == 0 && iRight == 0){
					if(rule.substring(0,1).equals("0"))
						ti = 0;
					else
						ti = 1;
				}else if(iLeft == 0 && temp == 0 && iRight == 1){
					if(rule.substring(1,2).equals("0"))
						ti = 0;
					else
						ti = 1;
				}else if(iLeft == 0 && temp == 1 && iRight == 0){
					if(rule.substring(2,3).equals("0"))
						ti = 0;
					else
						ti = 1;
				}else if(iLeft == 0 && temp == 1 && iRight == 1){
					if(rule.substring(3,4).equals("0"))
						ti = 0;
					else
						ti = 1;

				}else if(iLeft == 1 && temp == 0 && iRight == 0){
					if(rule.substring(4,5).equals("0"))
						ti = 0;
					else
						ti = 1;
				}else if(iLeft == 1 && temp == 0 && iRight == 1){
					if(rule.substring(5,6).equals("0"))
						ti = 0;
					else
						ti = 1;
				}else if(iLeft == 1 && temp == 1 && iRight == 0){
					if(rule.substring(6,7).equals("0"))
						ti = 0;
					else 
						ti = 1;
				}else if(iLeft == 1 && temp == 1 && iRight == 1){
					if(rule.substring(7,8).equals("0"))
						ti = 0;
					else
						ti = 1;
				}
				stepIn[i] = ti;
			}
			});
			index = stepIn;
		}
		System.out.println("Minimum popcount: " + min[0] +" at step " + min[1]);
		System.out.println("Maximum popcount: " + max[0] +" at step " + max[1]);
		System.out.println("Final popcount: " + getCount() + " at step " + steps);
	}

	/**
	*prints out the state of the board
	*used for debugging
	*/
	private void printLife(int s, int p){
		System.out.print(s + " | " );
		for(int i = 0; i < index.length; ++i){
			System.out.print(index[i]);
		}
		System.out.println(" | " + p);
	}
	
	/**
	*returns the current count of life on board
	*/
	private int getCount(){
		int c = 0;
		for(int i = 0; i < index.length; ++i){
			if(index[i] == 1)
				++c;
		}
		return c;
	}
}
