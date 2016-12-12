package geoloc;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.maxmind.geoip.Location;

public class GeoWindow {

	private JFrame frame;
	private JTextField textField;

	private JLabel lblCountry;
	private JLabel lblCity;
	private JLabel lbllongitude;
	private JLabel lblLatitude;
	private JLabel lblEnterIp;
	private JButton btnClear;
	//private JButton btnBrowse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GeoWindow window = new GeoWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GeoWindow() {
		GeoLocation.load(new File("GeoDB.dat"));
		initialize();
	}

	/*private void browseButton()
	{
		btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(new Rectangle(10, 10, 10, 10));
	    frame.getContentPane().add(btnBrowse);
	         
	    btnBrowse.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        JFileChooser fileChooser = new JFileChooser();
	 
	        // For Directory
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	 
	        fileChooser.setAcceptAllFileFilterUsed(false);
	 
	        int rVal = fileChooser.showOpenDialog(null);
	        if (rVal == JFileChooser.APPROVE_OPTION)
	        {
	        	if (fileChooser.getSelectedFile().getName().equalsIgnoreCase("GeoDB.dat"))
	        	{
	        		GeoLocation.load(fileChooser.getSelectedFile().getAbsoluteFile());
	        		initialize();
	        	}
				else
					JOptionPane.showMessageDialog(null, "Bad file.", "", JOptionPane.ERROR_MESSAGE);
			}
	      }
	    });
	}*/
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(new Rectangle(100, 100, 100, 100));
		frame.setAutoRequestFocus(false);
		frame.setBounds(100, 100, 450, 167);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*if (GeoLocation.lookup == null)
		{
			browseButton();
			return;
		}*/
		
		JButton btnTest = new JButton("Check IP");
		btnTest.setContentAreaFilled(false);
		btnTest.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		btnTest.setBounds(new Rectangle(0, 0, 10, 10));
		btnTest.setMinimumSize(new Dimension(21, 23));
		btnTest.setMaximumSize(new Dimension(21, 23));
		textField = new JTextField();
		textField.setColumns(10);

		lblCountry = new JLabel("Country:");
		lblCity = new JLabel("City:");
		lbllongitude = new JLabel("Longitude:");
		lblLatitude = new JLabel("Latitude:");
		btnClear = new JButton("clear");
		lblEnterIp = new JLabel("Enter IP:");
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblEnterIp)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClear))
								.addComponent(lblCountry, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblCity, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
							.addGap(285))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lbllongitude, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
							.addGap(285))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblLatitude, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(285, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterIp)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnClear))
					.addGap(18)
					.addComponent(lblCountry)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCity)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbllongitude)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLatitude)
					.addContainerGap(34, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);

		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				lblCountry.setText("");
				lblCity.setText("");
				lbllongitude.setText("");
				lblLatitude.setText("");
			}
		});
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String ip = textField.getText();
				if (ip.isEmpty() || !checkIPv4(ip)) {
					JOptionPane.showMessageDialog(null, "You have entered invalid IP address! Example: 10.10.10.10",
							"Invalid IP Address", JOptionPane.ERROR_MESSAGE);
					initialize();
					return;
				}

				String country, city;
				float lat, log;
				Location data = GeoLocation.getLocation(ip);

				if (data == null) {
					JOptionPane.showMessageDialog(null, "Cannot find data for IP: " + ip,
							"Sorry, i cannot find data for IP: " + ip, JOptionPane.ERROR_MESSAGE);
					initialize();
					return;
				}

				country = data.countryName;
				city = data.city;
				lat = data.latitude;
				log = data.longitude;

				lblCountry.setText("Country: " + country);
				lblCity.setText("City: " + city);
				lbllongitude.setText("Longitude: " + log);
				lblLatitude.setText("Latitude: " + lat);
			}
		});
	}

	private final boolean checkIPv4(final String ip) {
		boolean isIPv4;
		try {
			final InetAddress inet = InetAddress.getByName(ip);
			isIPv4 = inet.getHostAddress().equals(ip) && inet instanceof Inet4Address;
		} catch (final UnknownHostException e) {
			isIPv4 = false;
		}
		return isIPv4;
	}
}
