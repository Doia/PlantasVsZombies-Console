package GUI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import Logic.Cliente.Cliente;
import Logic.Servidor.Server;

public class InitialWindow extends javax.swing.JFrame {

	
	private ArrayList<Integer> puertos;
	int clientPort;
	InetAddress ipServer;
	InetAddress ipCliente;
	int serverPort;
	boolean modeBatch;
	
    /**
     * Creates new form InitialWindow
     */
    public InitialWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    	 modeCheckBox = new javax.swing.JCheckBox();
         serverBox = new javax.swing.JComboBox();
         IpLabel = new javax.swing.JLabel();
         ServerLabel = new javax.swing.JLabel();
         okButton = new javax.swing.JButton();
         PortLabel = new javax.swing.JLabel();
         exampleLabel = new javax.swing.JLabel();
         ipTextArea = new javax.swing.JTextArea();
         portTextArea = new javax.swing.JTextArea();
         ServerPortLabel = new javax.swing.JLabel();
         serverPortTextArea = new javax.swing.JTextArea();
         batchCheckBox = new javax.swing.JCheckBox();

         setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

         modeCheckBox.setText("Local mode");

         serverBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cliente","Servidor"}));
         serverBox.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 serverBoxActionPerformed(evt);
             }
         });

         IpLabel.setText("IP Server");

         ServerLabel.setText("Server/Client");

         okButton.setText("OK");
         okButton.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 okButtonActionPerformed(evt);
             }
         });

         PortLabel.setText("Port:");

         exampleLabel.setText("Ejemplo mas de un puerto: 500;501;605");

         ipTextArea.setColumns(20);
         ipTextArea.setRows(5);
         ipTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         portTextArea.setColumns(20);
         portTextArea.setRows(5);
         portTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         ServerPortLabel.setText("Server port");

         serverPortTextArea.setColumns(20);
         serverPortTextArea.setRows(5);
         serverPortTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());

         batchCheckBox.setText("Bach Mode");

         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(layout.createSequentialGroup()
                 .addGap(34, 34, 34)
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(IpLabel)
                     .addComponent(ServerLabel)
                     .addComponent(ServerPortLabel))
                 .addGap(18, 18, 18)
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                         .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addComponent(ipTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                             .addComponent(serverBox, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                         .addGap(37, 37, 37)
                         .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addGroup(layout.createSequentialGroup()
                                 .addComponent(PortLabel)
                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                 .addComponent(portTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                             .addComponent(exampleLabel))
                         .addGap(44, 44, 44)
                         .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                             .addComponent(batchCheckBox)
                             .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                             .addComponent(modeCheckBox)))
                     .addComponent(serverPortTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                 .addContainerGap(59, Short.MAX_VALUE))
         );
         layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(layout.createSequentialGroup()
                 .addGap(29, 29, 29)
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(serverBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(modeCheckBox)
                     .addComponent(ServerLabel)
                     .addComponent(PortLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(portTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                 .addGap(18, 18, 18)
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(exampleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(batchCheckBox)
                     .addComponent(ipTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(IpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                 .addGap(11, 11, 11)
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(serverPortTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addComponent(ServerPortLabel)
                     .addComponent(okButton))
                 .addContainerGap(25, Short.MAX_VALUE))
         );

        this.setLocation(500, 400);
        this.setVisible(true);
        pack();
    }// </editor-fold>                        

    
    private void serverBoxActionPerformed(java.awt.event.ActionEvent evt) {       
    	String item = (String) serverBox.getSelectedItem();
        if (serverBox.getSelectedItem().equals("Servidor")){
        	IpLabel.setVisible(false);
        	exampleLabel.setVisible(false);
        	ipTextArea.setVisible(false);
        	batchCheckBox.setVisible(false);
        	serverPortTextArea.setVisible(false);
        	ServerPortLabel.setVisible(false);
        }
        else {
        	IpLabel.setVisible(true);
        	exampleLabel.setVisible(true);
        	ipTextArea.setVisible(true);
        	batchCheckBox.setVisible(true);
        	serverPortTextArea.setVisible(true);
        	ServerPortLabel.setVisible(true);
        }
    }  
    
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (serverBox.getSelectedItem().equals("Servidor")) {
        	createServer();
        }
        else {
        	createClient();
        }
    }                                        

    private void createServer() {
		
    	try {
			parseServerOptions();
			Server server = new Server(serverPort);
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void parseServerOptions() throws Exception{
		String textPuertos = portTextArea.getText();
		serverPort = Integer.parseInt(textPuertos);
	}

	private void createClient() {
		
		try {
			parseClientOptions();
			Cliente cliente = new Cliente(ipServer, ipCliente, puertos, serverPort, modeBatch);
			cliente.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void parseClientOptions() throws Exception {
		int n;
		puertos = new ArrayList<Integer>();
		String textPuertos = portTextArea.getText();
		//clientPort = Integer.parseInt(textPuertos);
		String[] listaPuertos = textPuertos.split(";");
		for (int i = 0; i < listaPuertos.length; i++) {
			n = Integer.parseInt(listaPuertos[i]);
			puertos.add(n);
		}
		serverPort = Integer.parseInt(serverPortTextArea.getText());
		if (modeCheckBox.isSelected()) { //modoLocal
			ipCliente = InetAddress.getLocalHost();
		}
		else{
			ipCliente = getPublicIp();
		}	
		ipServer = InetAddress.getByName(ipTextArea.getText());
		modeBatch = !batchCheckBox.isSelected();
	}

	
	
	private InetAddress getPublicIp() throws Exception {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = in.readLine(); //you get the IP as a String
		return InetAddress.getByName(ip);
	}



	// Variables declaration - do not modify   
	
    private javax.swing.JLabel ServerPortLabel;
    private javax.swing.JTextArea serverPortTextArea;
    private javax.swing.JCheckBox batchCheckBox;
    private javax.swing.JLabel IpLabel;
    private javax.swing.JLabel PortLabel;
    private javax.swing.JLabel ServerLabel;
    private javax.swing.JLabel exampleLabel;
    private javax.swing.JTextArea ipTextArea;
    private javax.swing.JCheckBox modeCheckBox;
    private javax.swing.JButton okButton;
    private javax.swing.JTextArea portTextArea;
    private javax.swing.JComboBox serverBox;
    // End of variables declaration                   
}
