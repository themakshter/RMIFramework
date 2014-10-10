

/**
 * Collection of static methods that help you set up RMI. 
 * @author sebastian
 */
public final class RMIHelper {
	/**
	 * The RMI policy file dictates who has permission to download
	 * code from the server. An "allow_all" policy gives permission
	 * to everybody.
	 */
	public static void setPolicyFile() {
		System.setProperty("java.security.policy", PolicyFileLocator.
				getLocationOfPolicyFile());
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
	}
	
	/**
	 * Classes that can be downloaded must be in the shared codebase
	 * of the Java RMI server. This method adds a class to the codebase.
	 * @param classToAdd The class that you want to add to the codebase.
	 */
	public static void addClassToCodebase(Class<?> classToAdd) {
		System.setProperty("java.rmi.server.codebase", classToAdd
			.getProtectionDomain().getCodeSource().getLocation().toString());
	}
}
