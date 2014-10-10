

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Notification class which is to be passed around remote objects
 * and changed to show messages. It is serializable because it needs
 * to moved around.
 * @author mak1g11
 * 
 */
public class Notification implements Serializable {
	private String message;

	/**
	 * Sets the message variable contained in Notification
	 * @param s The message to be set
	 * @throws RemoteException Any issues which take place 
	 * because of the communication being remote
	 */
	public void setMessage(String s) throws RemoteException {
		message = s;
	}

	/**
	 * Gets the message variable contained in Notification
	 * @return message variable contained
	 * @throws RemoteException Any issues which take place 
	 * because of the communication being remote
	 */
	public String getMessage() throws RemoteException {
		return message;
	}
}
