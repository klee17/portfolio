/**
*Keenan Lee:kbl3236
*
*
*/
import java.io.IOException;
public interface ModelListener{
	
	/**
	*
	*/
	public void joined() throws IOException;
	
	public void setPlayers(String a, String b) throws IOException;

	public void setPlayerNum(int n) throws IOException;

	public void startTurn() throws IOException;

	public void die(int x, int y) throws IOException;

	public void checkTurn(int b) throws IOException;

	public void getScore(int p, int s) throws IOException;

	public void flipTile(int p, String d) throws IOException;

	public void winner(int w) throws IOException;
}
