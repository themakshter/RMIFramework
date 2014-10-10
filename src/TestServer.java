

import java.rmi.*;
import java.rmi.server.*;

/**
 * Creates a server which creates and binds two NotificationSources for testing
 * @author mak1g11
 *
 */

public class TestServer {
	
	public static void main(String[] args) {
		RMIHelper.addClassToCodebase(TestServer.class);
		try {
			// create 2 NotificationSourceThings
			for (int i = 1; i < 3; i++) {
				NotificationSource n = new NotificationSource();
				Naming.rebind("source" + i, n);
			}
			//Message to inform that action has been successful
			System.out.println("Made and bound the NotificationSources!");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
