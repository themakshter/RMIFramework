

import java.rmi.Naming;

/**
 * Sets up the Server which initialises and binds two NotificationSource objects
 * 
 * @author mak1g11
 * 
 */
public class NotificationServer {

	public static void main(String[] args) {
		RMIHelper.addClassToCodebase(NotificationServer.class);
		// Surrounded potential exceptions with try and catch blocks
		try {
			NotificationSource match = new NotificationSource();
			Naming.rebind("match", match); // will have league results
			NotificationSource stand = new NotificationSource();
			Naming.rebind("standing", stand); // will have the league standings
			match.setMap(XMLParser.getMatches()); // HashMap is obtained from
													// the XMLParser
			stand.setMap(XMLParser.getStandings()); // HashMap is obtained from
													// the XMLParser
			System.out.println("Sources made and info set!"); // Message to
															  // inform that
															  // the server is
															  // up
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
