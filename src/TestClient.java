

import java.net.MalformedURLException;
import java.rmi.*;

/**
 * Creates a few NotificationSinks and tests to see how the remote
 * NotificationSource interacts with them
 * 
 * @author mak1g11
 * 
 */
public class TestClient {
	private static NotificationSourceInterface nsi1, nsi2;
	private static NotificationSink ns1, ns2, ns3;

	public static void main(String[] args) {
		RMIHelper.addClassToCodebase(TestClient.class);
		TestClient.intialise();
		TestClient.refreshSinks();
		TestClient.Test1();
		TestClient.refreshSinks();
		TestClient.Test2();
		TestClient.refreshSinks();
		TestClient.Test3();
		TestClient.refreshSinks();
		TestClient.Test4();
	}

	/**
	 * Finds the NotificationSources bound in the registry
	 */
	public static void intialise() {
		try {
			nsi1 = (NotificationSourceInterface) Naming
					.lookup("rmi://localhost/source1");
			nsi2 = (NotificationSourceInterface) Naming
					.lookup("rmi://localhost/source2");
		} catch (Exception e) {
			//error statement
			System.err
					.println("Uh-oh, Something went wrong :/. The server hasn't bound the sources properly or the wrong names are being called");
		}
	}

	/**
	 * NotificationSinks are set to new ones with their Notifications set to
	 * empty string
	 */
	public static void refreshSinks() {
		try {
			ns1 = new NotificationSink();
			ns2 = new NotificationSink();
			ns3 = new NotificationSink();
		} catch (RemoteException re) {
			System.err
					.println("Uh-oh, Something went wrong. Looks like the Sinks weren't able to be created correctly.");
		}

	}

	/**
	 * Test which adds one sink and removes it to see if nothing goes wrong
	 */
	public static void Test1() {
		try {
			nsi1.addSink(ns1);
			System.out.println("Added a sink to a source successfully!");
			System.out.println(nsi1.showConnectedSinks()
					+ " connected currently");
			nsi1.notifySink(ns1, "otfication changed by ns1");
			System.out.println("ns1 : " + ns1.getNotifMessage());
			nsi1.removeSink(ns1);
			System.out.println(nsi1.showConnectedSinks()
					+ "connected currently");
		} catch (Exception e) {
			System.err
					.println("Something went wrong. Either the sink isn't registered or the source isn't or something similar");
		}

	}

	/**
	 * Multiple sinking is tested here(more than on NotificationSink added to a NotificationSource
	 */
	public static void Test2() {
		try {
			nsi1.addSink(ns1);
			nsi1.addSink(ns2);
			nsi1.addSink(ns3);
			System.out.println(nsi1.showConnectedSinks()
					+ "connected currently");
			nsi1.notifyAllSinks("notification changed by nsi1");
			System.out.println("ns1: " + ns1.getNotifMessage());
			System.out.println("ns2: " + ns2.getNotifMessage());
			System.out.println("ns3: " + ns3.getNotifMessage());
			nsi1.removeSink(ns1);
			System.out.println(nsi1.showConnectedSinks());
		} catch (Exception e) {
			System.err.println("Uh oh something went wrong, test failed!");
		}
	}

	/**
	 * Multiple sourcing is tested here (A sink is registered with two sources)
	 */
	public static void Test3() {
		try {
			nsi1.addSink(ns1);
			nsi2.addSink(ns1);
			nsi1.notifySink(ns1, "notified by nsi1");
			System.out.println("ns1: " + ns1.getNotifMessage());
			nsi2.notifySink(ns1, "notified by nsi2");
			System.out.println("ns1: " + ns1.getNotifMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Some error cases are tested (adding a sink to a source twice)
	 */
	public static void Test4() {
		try {
			nsi1.addSink(ns1);
			nsi1.addSink(ns1);
			System.out.println(nsi1.showConnectedSinks());
			nsi1.notifySink(ns2, "notified by nsi1");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
