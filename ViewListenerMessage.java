/**
*Keenan Lee:kbl3236
*
*
*/

public abstract class ViewListenerMessage implements Serializable{
	public abstract void process(ViewListener listen) throws IOException;
}
