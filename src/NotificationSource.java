

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The implementation of the NotificationSourceInterface
 * 
 * @author mak1g11
 * 
 */
public class NotificationSource extends UnicastRemoteObject implements
		NotificationSourceInterface {
	private ArrayList<NotificationSinkInterface> registry; // ArrayList to
															// contain the
															// NotificationSinkIntefaces
	private HashMap<String, String> info; // HashMap to contain the information
											// from the XMLParser

	/**
	 * Initialises the registry ArrayList
	 * 
	 * @throws RemoteException
	 *             Any issues which might take place because of the remote
	 *             communication
	 */
	protected NotificationSource() throws RemoteException {
		super();
		registry = new ArrayList<NotificationSinkInterface>();
	}

	/**
	 * Sets the HashMap got from the XMLParser
	 * 
	 * @param map
	 *            HashMap from the XMLParser
	 */
	public void setMap(HashMap<String, String> map) {
		info = map;
	}

	/**
	 * First checks if the NotificationSinkInterface is not already contained in
	 * the registry. If it's not, adds it.
	 */
	public void addSink(NotificationSinkInterface ns) throws RemoteException {
		if (!registry.contains(ns)) {
			registry.add(ns);
		}
	}

	/**
	 * Finds the NotificationSinkInterface contained in the registry and sets
	 * the Notification as with the message specified
	 */
	public void notifySink(NotificationSinkInterface ns, String message)
			throws RemoteException {
		Notification n = new Notification(); // Makes a new Notification
		n.setMessage(message); // Sets its message as the String parameter

		// Finds the NotificationSinkInterface and sets its Notification
		// object to the one made above
		for (NotificationSinkInterface nst : registry)
			if (nst.equals(ns)) {
				nst.setNotification(n);
				break;
			}
	}

	/**
	 * Finds the NotificationSinkInterface specified in the registry and gets
	 * the league from it and sends back value in the HashMap
	 */
	public void notifySink(NotificationSinkInterface ns) throws RemoteException {
		// Makes a new Notification object and sets the message to the value of
		// the
		// String in HashMap
		Notification n = new Notification();
		n.setMessage(info.get(ns.getNotifMessage())); // NotificationSinkInterface
														// will contain the
														// league information in
														// the message of its
														// Notification object
		for (NotificationSinkInterface nst : registry)
			if (nst.equals(ns)) {
				nst.setNotification(n);
				break;
			}
	}

	/**Goes through he registry and notifies every NotificationSinkInterface contained
	 */
	public void notifyAllSinks(String s) throws RemoteException {
		for (NotificationSinkInterface nst : registry) {
			Notification n = new Notification();
			n.setMessage(s);
			nst.setNotification(n);
		}
	}

	/**
	 * Removes the specified NotificationSinkInterface
	 */
	public void removeSink(NotificationSinkInterface ns) throws RemoteException {
		registry.remove(ns);
	}

	/**
	 * Returns the NotificationSinkInterfaces in the form of a String to be printed out
	 */
	public String showConnectedSinks() throws RemoteException {
		String result = "";
		for (NotificationSinkInterface nst : registry) {
			result += " " + nst;
		}
		return result;
	}
}
