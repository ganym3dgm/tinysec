package tinysec.ui.views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutView extends JFrame {
	private JButton jButton = null;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;

	public AboutView() {
		this.initialize();
	}

	private void close() {
		this.dispose();
	}

	private JButton getJButton() {
		if (this.jButton == null) {
			this.jButton = new JButton();
			this.jButton.setBounds(138, 160, 59, 25);
			this.jButton.setText("OK");
			this.jButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					AboutView.this.close();
				}
			});
		}
		return this.jButton;
	}

	private JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jContentPane = new JPanel();
			JLabel jLabel1 = new JLabel();
			this.jContentPane.setLayout(null);
			jLabel1.setBounds(136, 110, 62, 14);
			jLabel1.setText("TinySec v1.0");
			this.jContentPane.add(jLabel1, null);
			this.jContentPane.add(this.getJButton(), null);
			this.jContentPane.add(this.getJPanel(), null);

			JLabel labelLinkDonation = new JLabel("<html><a href=''>Donate</a></html>");
			labelLinkDonation.setCursor(new Cursor(Cursor.HAND_CURSOR));
			labelLinkDonation.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop()
								.browse(new URI("https://www.paypal.com/donate/?hosted_button_id=5KM4X9PDQSE7J"));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			});
			labelLinkDonation.setBounds(150, 135, 35, 14);
			jContentPane.add(labelLinkDonation);
		}
		return this.jContentPane;
	}

	private JPanel getJPanel() {
		if (this.jPanel == null) {
			this.jPanel = new JPanel();
			JLabel jLabel = new JLabel();
			this.jPanel.setLayout(null);
			this.jPanel.setBounds(1, 1, 361, 98);
			this.jPanel.setBackground(Color.white);
			jLabel.setText("");
			jLabel.setIcon(new ImageIcon(this.getClass().getResource("/icons/tinySec.png")));
			jLabel.setBounds(134, 14, 64, 64);
			this.jPanel.add(jLabel, null);
		}
		return this.jPanel;
	}

	private void initialize() {
		this.setTitle("About TinySec");
		this.setContentPane(this.getJContentPane());
		this.setSize(370, 235);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		this.setLocation(x, y);
		this.setResizable(false);
	}
}
