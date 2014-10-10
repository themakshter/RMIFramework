

import java.rmi.*;


/**
 * The interface for the NotificationSource to be implemented
 * @author mak1g11
 *
 */
public interface NotificationSourceInterface extends Remote {
	/**
	 * Adds a NotificationSinkInteface to its registry
	 * @param ns NotificationSinkInteface to be added to the registry
	 * @throws RemoteException	Any issues which might take place because
	 * of the remote communication
	 */
	public void addSink(NotificationSinkInterface ns) throws RemoteException;
	
	/**
	 * Removes the specified NotificationSinkInterface from its registry
	 * @param ns NotificationSinkInterface to be removed
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	public void removeSink(NotificationSinkInterface ns) throws RemoteException;

	/**
	 *  Notifies the specified NotificationSinkInterface with some information already present
	 * @param ns NotificationSinkInterface to be notified
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	public void notifySink(NotificationSinkInterface ns) throws RemoteException;

	/**
	 * Notifies the specified NotificationSinkInterface
	 * @param ns NotificationSinkInterface to be notified
	 * @param message String which is to be set as the message of the Notification object
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	public void notifySink(NotificationSinkInterface ns,String message) throws RemoteException;
	
	/**
	 *	Shows all the NotificationSinkIntefaces connected to the NotificationSourceInterface
	 * @return The list of the NotificationSinkIntefaces as String
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	public String showConnectedSinks() throws RemoteException;
	
	/**
	 * Notifies all the NotificationSinkInterfaces contained in registry with the message
	 * @param s	String which will be sent as a Notification object
	 * @throws RemoteException Any issues which might take place because
	 * of the remote communication
	 */
	public void notifyAllSinks(String s) throws RemoteException;
}
