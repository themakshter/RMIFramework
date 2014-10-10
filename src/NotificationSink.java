

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation of the NotificationSinkInterface
 * 
 * @author mak1g11
 * 
 */
public class NotificationSink extends UnicastRemoteObject implements
		NotificationSinkInterface {
	private Notification n; // Notification object attached to
							// sink

	/**
	 * Initialises the Notification object contained in the class and sets its
	 * message to an empty String
	 * 
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	protected NotificationSink() throws RemoteException {
		super();
		n = new Notification();
		n.setMessage("");
	}

	/**
	 * Sets the notification to the one in the parameter
	 */
	public void setNotification(Notification n) throws RemoteException {
		this.n = n;

	}

	/**
	 * Gets the notification message of the Notification object within
	 */
	public String getNotifMessage() throws RemoteException {
		return n.getMessage();
	}

}
