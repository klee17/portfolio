/**
*Keenan Lee
*9/15/2013
*Main part of the MiniBlast program, conatins main
*Semaphores are used to handle the priniting of data
*This was done due to my general like of Semaphores
*and because only one thread should be printing at a time
*so locks are not needed
*/

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class MiniBlast{

	/**
	*expects 2 command line arguments, a valid file to parse
	*and a String to search the file for
	*implaments and cleans up threads
	*throws Throable to make my life easier
	*/
	public static void main(String[] args) throws Throwable{
		String query;
		File f;
		Scanner sc;
		Semaphore printAcc = new Semaphore(1);
		if(args.length != 2){
			System.err.println("Error, needs two command line arguments");
			return;
		}
		f = new File(args[0]);
		query = args[1];
		try{
			sc =  new Scanner(f);
		}catch(FileNotFoundException e ){
			System.err.println("Bad file");
			return;
		}

		ExecutorService pool = Executors.newCachedThreadPool();
		LinkedList<Future<Processor>> results = new LinkedList<Future<Processor>>();

		while(sc.hasNext()){
			String temp = sc.nextLine();
			pool.execute(new ProcessorRunable(query,temp,printAcc));
		}

		for(Future<Processor> result : results){
			Processor pro = result.get();
			if(pro.matchFound())
				System.out.println(pro.getSequence());

		}

		pool.shutdown();
	}
}
