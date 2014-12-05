/**
*Keenan Lee
*9/15/2013
*Class that handles threading using Semaphores
*implements Runnable
*/

import java.util.concurrent.Semaphore;

public class ProcessorRunable implements Runnable{
	private String key;
	private String seq;
	private Semaphore sem;
	
	/**
	*Basic contructor for ProcessorRunnable
	*expects a valid string for the key,
	*another for the sequence
	*And a valid Semaphore
	*/
	public ProcessorRunable(String k, String s, Semaphore se){
		key = k;
		seq = s;
		sem = se;
	}

	/**
	*Creates a Processor and then parses it
	*if there was a match in the sequence
	*then it gets access to the Semaphore and prints
	*/
	public void run(){
		Processor pro = new Processor(key, seq);

		pro.parse();
		if(pro.matchFound()){
			sem.acquireUninterruptibly();
			System.out.println(pro.getSequence());
			sem.release();
		}

	}

}
