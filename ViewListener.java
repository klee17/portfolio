/**
*Keenan Lee:kbl3236
*11/6/2013
*
*/
import java.io.IOException;

public interface ViewListener{
	public void join(String name) throws IOException;

	public void flip(int x, String y) throws IOException;

	public void roll() throws IOException;

	public void done() throws IOException;

	public void exit() throws IOException;
}
