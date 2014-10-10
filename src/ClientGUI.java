

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The application which allows the user to view football results or standings
 * @author mak1g11
 * 
 */
public class ClientGUI {
	private NotificationSourceInterface matchSource, standingSource;
	boolean match, standing, connected;
	private NotificationSink nst;
	private String[] options = { "Barclays Premier League", "La Liga",
			"Serie A", "Bundesliga" };
	public JPanel panel;
	private JButton refresh;
	private JRadioButton matches, standings;
	private JComboBox<String> leagues;
	private JTextArea view;

	/**
	 * GUI is intialised in the constructor
	 * @throws RemoteException
	 */
	public ClientGUI() throws RemoteException {
		RMIHelper.addClassToCodebase(ClientGUI.class);
		connected = false;
		initiate();
		connect();
	}

	/**
	 * The registry is searched for the NotificationSources present. An error is produced if the Sources are not found
	 */
	public void connect() {
		try {
			view.setText("Connecting to sources...");
			matchSource = (NotificationSourceInterface) Naming
					.lookup("rmi://localhost/match");
			standingSource = (NotificationSourceInterface) Naming
					.lookup("rmi://localhost/standing");
			view.setText("Connected! Please choose a feed to display!");
			connected = true;
			nst = new NotificationSink();
		} catch (Exception e) {
			connected = false;
			view.setText("Can't connect to sources!, please try again!");
		}
	}

/**
 * ClientGUI object is created in the main method 
*/
	public static void main(String[] args) throws RemoteException {
		ClientGUI cg = new ClientGUI();
	}

	/**
	 * All the GUI components are added
	 * @throws RemoteException
	 */
	public void initiate() throws RemoteException {
		final JFrame demo = new JFrame("Football!");
		panel = new JPanel();
		refresh = new JButton("Refresh");
		matches = new JRadioButton("Matches");
		standings = new JRadioButton("Standings");
		leagues = new JComboBox<String>(options);
		view = new JTextArea();
		panel.setLayout(new FlowLayout());
		ButtonGroup bg = new ButtonGroup();
		bg.add(matches);
		bg.add(standings);
		refresh.addActionListener(new ButtonListener());
		matches.addItemListener(new RadioListener());
		standings.addItemListener(new RadioListener());
		JScrollPane scroll = new JScrollPane(view);
		scroll.setPreferredSize(new Dimension(350, 150));
		view.setEditable(false);		//TextArea will show the results it can't be tampered with
		panel.add(matches);
		panel.add(standings);
		panel.add(leagues);
		panel.add(scroll);
		panel.add(refresh);
		demo.add(panel);
		demo.setContentPane(panel);
		demo.setSize(430, 270);
		demo.setVisible(true);			
		demo.setResizable(false);		//GUI is kept to a set size to allow for the layout1
		demo.setLocationRelativeTo(null);
		/**
		 * When the window is closed, the sink needs to be remvoed from the source registry
		 */
		demo.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (standingSource != null && matchSource != null) {
					try {
						standingSource.removeSink(nst);
						matchSource.removeSink(nst);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				demo.dispose();
			}
		});
	}

	/**
	 * Listener added for the Refresh button
	 * @author mak1g11
	 * 
	 */
	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			/**
			 * It is checked which of the sources has been selected and Notification is sent accordingly
			 */
			if (match) {
				try {
					Notification n = new Notification();
					n.setMessage((String) leagues.getSelectedItem());
					nst.setNotification(n);
					matchSource.notifySink(nst);
					view.setText(nst.getNotifMessage());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else if (standing) {
				try {
					Notification n = new Notification();
					n.setMessage((String) leagues.getSelectedItem());
					nst.setNotification(n);
					standingSource.notifySink(nst);
					view.setText(nst.getNotifMessage());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			} else if (!connected) {
				view.setText("Error! There is no connection!");
			} else if (!(match || standing)) {
				view.setText("Error! Please choose one of the options!");
			}
		}
	}

	/**
	 * Custom Listener for the JRadioButtons
	 * @author mak1g11
	 * 
	 */
	public class RadioListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			/**
			 * The GUI must be connected first however
			 */
			if (connected) {
				//if the user wants to see the results
				if (matches.isSelected()) {
					try {
						matchSource.addSink(nst);
						match = true;
						standing = false;
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				//user wants to see the league standings
				} else if (standings.isSelected()) {
					try {
						standingSource.addSink(nst);
						standing = true;
						match = false;
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}

				}
			}
		}

	}

}
