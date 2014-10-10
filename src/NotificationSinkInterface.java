

import java.rmi.*;

/**
 * The interface for the NotificationSink class to be implemented
 * @author mak1g11
 *
 */
public interface NotificationSinkInterface extends Remote {
	
	/**
	 * 
	 * @param n	The notification which is to be set containing 
	 * information about the event the sink wants to be informed
	 * about.
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	public void setNotification(Notification n) throws RemoteException;

	/**
	 * 	Gets the message conatined by the Notification object contained in the Sink
	 * @return Message contained by the notification
	 * @throws RemoteException
	 */
	public String getNotifMessage() throws RemoteException;
}
