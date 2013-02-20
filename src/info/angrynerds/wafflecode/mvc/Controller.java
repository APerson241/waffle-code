package info.angrynerds.wafflecode.mvc;

import info.angrynerds.wafflecode.network.WaffleClient;

public interface Controller {
	public void runApplication();

	public WaffleClient getClient();

	public String getUsername();
	
	public void setIP(String ip);
}
