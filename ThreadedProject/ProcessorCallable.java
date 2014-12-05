/**
*Keenan Lee:kbl3236
*9/17/2013
*java class used for threading 
*implements Callable
*/

import java.util.concurrent.Callable;

public class ProcessorCallable implements Callable<Processor>{

	String key;
	String seq;
	/**
	*basic contsructor for ProcessorCallable
	*expects two valid strings, a sequence
	*and a key to compare it with
	*/
	public ProcessorCallable(String k, String s){
		key = k;
		seq = s;
	}

	/**
	*creates and uses a Procesor
	*its then returned for later use
	*/
	public Processor call(){
		Processor pro = new Processor(key, seq);
		pro.parse();

		return pro;
	}
}
