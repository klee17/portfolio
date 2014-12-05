/**
*Keenan Lee:kbl3236
*
*
*/

import java.io.IOException;
import java.util.HashMap;

public class SessionManager implements ViewListener{

	private HashMap<String,ShutTheBoxModel> sessions = 
		new HashMap<String, ShutTheBoxModel>();
	private int sessNum = 0;

	public SessionManager(){}

	public synchronized void join(ViewProxy prox, String n)throws IOException{
		ShutTheBoxModel mod = sessions.get("Session " + sessNum);
		Boolean play2 = false;
		System.out.println("dudes name " + n);
		if(mod == null){
			mod = new ShutTheBoxModel();
			mod.player1(n);
			sessions.put("Session " + sessNum ,mod);
			//System.out.println("player 1 in");
		}else{
			mod.player2(n);
			play2 = true;
			sessNum++;
			//System.out.println("player 2 in");
		}
		mod.addModelListener(prox);
		prox.setViewListener(mod);

		if(play2){
			//System.out.println("Both are in, trying to work now");
			mod.joined();
		}

	}

	public void flip(int x, String y) throws IOException{}

	public void roll() throws IOException{}

	public void done() throws IOException{}

	public void exit() throws IOException{}
}
