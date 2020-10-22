package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simulator.control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;


public class AddWindow extends JDialog {

	//constante que decide el tamaño de el JFr
	private final int tamDivisor = 3;
	
	private Controller ctrl;
	private JButton cancelButton, okButton;
	private JPanel addPanel;
	private JLabel bodiesLabel, idLabel, posLabel, velLabel, accLabel, lossfacLabel, lossfrecLabel;
	private JComboBox<String> bodiesBox;
	private JTextField idText, massText, pos1, pos2, vel1, vel2, acc1, acc2, lossfactor, lossfrec;
	
	public AddWindow(Controller ctrl) {
		super();
		this.ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		
		
		this.setResizable(false);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		addPanel = new JPanel();
		addPanel.setLayout(null);
		
		
		//CHOOSE BODY
		bodiesLabel = new JLabel("Choose Body: ");
		bodiesLabel.setBounds(50, 50, 100, 20);
		
		String possibilities[] = new String[ctrl.getBodiesFactory().getInfo().size()];
		for (int i = 0; i < possibilities.length; i++) {
			possibilities[i] = ctrl.getBodiesFactory().getInfo().get(i).getString("desc");
		}
		bodiesBox = new JComboBox<String>(possibilities);	
		bodiesBox.setBounds(150, 50, 200, 20);
		bodiesBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (bodiesBox.getSelectedItem() == possibilities[1]) { //Esto esta mal hay que ver que numero es de possibilities
					lossfactor.setEnabled(true);
					lossfrec.setEnabled(true);
				}
				else {
					lossfactor.setEnabled(false);
					lossfrec.setEnabled(false);
				}

			}
		});

		addPanel.add(bodiesLabel);
		addPanel.add(bodiesBox);

		//SET ID AND MASS
		idLabel = new JLabel("Id and Mass: ");
		idLabel.setBounds(50,85,100,20);
		
		idText = new JTextField("Body Id", 5);
		idText.setToolTipText("Body Id");
		idText.setBounds(150, 85, 200, 20);
		idText.setEditable(true);
		
		massText = new JTextField("Body Mass", 5);
		massText.setToolTipText("Body Mass");
		massText.setBounds(360, 85, 200, 20);
		massText.setEditable(true);
		
		addPanel.add(idLabel);
		addPanel.add(idText);
		addPanel.add(massText);

		
		//SET POS
		posLabel = new JLabel("Position( X, Y ): ");
		posLabel.setBounds(50,120,100,20);
		
		pos1 = new JTextField("0.0E0", 5);
		pos1.setToolTipText("0.0E0");
		pos1.setBounds(150, 120, 200, 20);
		pos1.setEditable(true);
		
		pos2 = new JTextField("0.0E0", 5);
		pos2.setToolTipText("0.0E0");
		pos2.setBounds(360, 120, 200, 20);
		pos2.setEditable(true);
		
		addPanel.add(posLabel);
		addPanel.add(pos1);
		addPanel.add(pos2);
		
		//SET VEL
		velLabel = new JLabel("Velocity( X, Y ):");
		velLabel.setBounds(50,155,100,20);
		
		vel1 = new JTextField("0.0E0", 5);
		vel1.setToolTipText("0.0E0");
		vel1.setBounds(150, 155, 200, 20);
		vel1.setEditable(true);
		
		vel2 = new JTextField("0.0E0", 5);
		vel2.setToolTipText("0.0E0");
		vel2.setBounds(360, 155, 200, 20);
		vel2.setEditable(true);
		
		addPanel.add(velLabel);
		addPanel.add(vel1);
		addPanel.add(vel2);
		
		//SET ACC
		accLabel = new JLabel("Aceleration( X, Y ): ");
		accLabel.setBounds(50,190,100,20);
		
		acc1 = new JTextField("0.0E0", 5);
		acc1.setToolTipText("0.0E0");
		acc1.setBounds(150, 190, 200, 20);
		acc1.setEditable(true);
		
		acc2 = new JTextField("0.0E0", 5);
		acc2.setToolTipText("0.0E0");
		acc2.setBounds(360, 190, 200, 20);
		acc2.setEditable(true);
		
		addPanel.add(accLabel);
		addPanel.add(acc1);
		addPanel.add(acc2);
		
		//SET LOSS FACTOR
		lossfacLabel = new JLabel("Loss factor: ");
		lossfacLabel.setBounds(50,225,100,20);
		
		lossfactor = new JTextField("Loss factor 0-1", 5);
		lossfactor.setToolTipText("Loss factor 0-1");
		lossfactor.setBounds(150, 225, 200, 20);
		lossfactor.setEnabled(false);
		lossfactor.setEditable(true);
		
		addPanel.add(lossfacLabel);
		addPanel.add(lossfactor);
		
		//SET LOSS FREQUENCY
		lossfrecLabel = new JLabel("Loss frequency: ");
		lossfrecLabel.setBounds(50,260,100,20);
		
		lossfrec = new JTextField("Loss frequency", 5);
		lossfrec.setToolTipText("Loss frequency");
		lossfrec.setBounds(150, 260, 200, 20);
		lossfrec.setEnabled(false);
		lossfrec.setEditable(true);
		
		addPanel.add(lossfrecLabel);
		addPanel.add(lossfrec);
		
		//CancelButton
		
		cancelButton = new JButton("Cancel");
		cancelButton.setToolTipText("Cancel");
		cancelButton.setBounds(360, 230, 95, 40);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		addPanel.add(cancelButton);
		
		//okButton
		
		okButton = new JButton("Add Body");
		okButton.setToolTipText("Add body");
		okButton.setBounds(465, 230, 95, 40);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				double data[] = new double[2];
				data[0] = Double.parseDouble(pos1.getText());
				data[1] = Double.parseDouble(pos2.getText());
				Vector p = new Vector(data);	
				
				data[0] = Double.parseDouble(vel1.getText());
				data[1] = Double.parseDouble(vel2.getText());
				Vector v = new Vector(data);		
				
				data[0] = Double.parseDouble(acc1.getText());
				data[1] = Double.parseDouble(acc2.getText());
				Vector a = new Vector(data);	
				
				if (bodiesBox.getSelectedItem().equals("Basic Body") ) {
					ctrl.addBody(new Body(idText.getText(), v, a ,p, Double.parseDouble(massText.getText())  ));
				}
				else {
					ctrl.addBody(new MassLossingBody(idText.getText(), v, a ,p, Double.parseDouble(massText.getText()), Double.parseDouble(lossfrec.getText()),Double.parseDouble(lossfactor.getText())));
				}
				dispose();
			}
		});
		
		addPanel.add(okButton);
		
		//FINAL SETTINGS

		addPanel.setVisible(true);
		this.add(addPanel);
		this.setModal(true);
		this.setContentPane(addPanel);
		Dimension size = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/tamDivisor, Toolkit.getDefaultToolkit().getScreenSize().height/tamDivisor);
		this.setSize(size);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - size.width/2 , Toolkit.getDefaultToolkit().getScreenSize().height/2 - size.height/2);
		this.setVisible(true);
		
		
		
	

	}
	
	

	
}
