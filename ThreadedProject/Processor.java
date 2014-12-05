/**
*Keenan Lee:kbl3236
*9/16/2013
*parses a single line of the file
*searching for the required key
*/

public class Processor{
	
	//private variables
	private String key;
	private String opKey;
	private String seq;
	private String start;
	private int count;

	/**
	*basic constructor for the Processor class
	*expects two valid Strings
	*processes and instanciates all private variables
	*/
	public Processor(String key, String seq){
		count = 0;
		this.key = key.toLowerCase();
		this.seq = seq;
		int sp = seq.indexOf(' ');
		start = this.seq.substring(0,sp);
		this.seq = seq.substring(sp+1);	
		this.seq = this.seq.toLowerCase();
		opKey = "";
		for(int i = key.length()-1; i >= 0; i--){
			char temp = this.key.charAt(i);
			if(temp == 'g')
				opKey = opKey + "c";
			else if(temp == 'c')
				opKey = opKey + "g";
			else if(temp == 'a')
				opKey = opKey + "t";
			else if(temp == 't')
				opKey = opKey + "a";
		}
				
	}
	/**
	*processes the sequence with its key
	*and its reverse key
	*makes perminate changes to sequence
	*/
	public void parse(){
		String temp = seq;

		seq = seq.replace(key,key.toUpperCase());
		if(!temp.equals(seq))
			count++;

		seq = seq.replace(opKey, opKey.toUpperCase());
		if(!temp.equals(seq))
			count++;
	}

	/**
	*returns the sequenced plus
	*the begaining parts that are striped off 
	*for processing
	*/
	public String getSequence(){
		return start + " " + seq;
	}

	/**
	*returns if a match has been found in this sequnece
	*if returns false it mean either
	*no match, or it hasn't been processed yet
	*/
	public boolean matchFound(){
		return count != 0;
	}
 
}
