package server;

import pupbic_interfase.ClientMethods;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;
import java.util.logging.Logger;

public class Server extends JFrame {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    private final JFileChooser fileChooser = new JFileChooser();
    private JPanel rootPanel;
    private JTextField portTextField;
    private JTextArea textArea;
    private Registry registry;
    private RegisteredParticipant participants;
    private static final  String NAME = "Registerable";
    private JLabel labelCont;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager(){
                public void checkConnect(String host, int port, Object context) { }
                public void checkConnect(String host,int port) { }
                public void checkPermission(Permission perm) { }
            }); }
        System.setProperty("java.rmi.server.codebase", "http://localhost:80/RMI/ http://localhost:80/RMI/client.jar");

        EventQueue.invokeLater(() -> {
            try {
                Server frame = new Server();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Server() throws HeadlessException, UnknownHostException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server");
        setBounds(100, 100, 800, 600);
        fileChooser.setCurrentDirectory(new java.io.File("."));

        ClientMethodsImpl clientMethods = new ClientMethodsImpl();
        participants = clientMethods.getParticipants();

        participants.addDRegisteredParticipantChangeListener(e ->{
            textArea.setText(participants.toString());
            labelCont.setText(String.valueOf(participants.size()));
        });


        //root panel
        rootPanel = new JPanel(new BorderLayout(0,0));
        setContentPane(rootPanel);

        //Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JToggleButton startButton = new JToggleButton("Start");
        JToggleButton stopButton = new JToggleButton("Stop");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton exitButton = new JButton("Exit");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(startButton);
        buttonGroup.add(stopButton);
        buttonGroup.setSelected(stopButton.getModel(), true);

        startButton.addActionListener(e-> {
            try {
                ClientMethods stub = (ClientMethods) UnicastRemoteObject.exportObject(clientMethods, Integer.parseInt(portTextField.getText()));
                registry = LocateRegistry.createRegistry(Integer.parseInt(portTextField.getText()));
                LOG.info(InetAddress.getLocalHost().toString());
                registry.rebind(NAME, stub);
                LOG.info("Server is ready to work");
                JOptionPane.showMessageDialog(null, "Server is ready to work","Server is ready to work", JOptionPane.INFORMATION_MESSAGE);

            } catch (RemoteException | UnknownHostException ex) {
                ex.printStackTrace();
            }
        });

        stopButton.addActionListener(e->{
            try {
                registry.unbind(NAME);
                UnicastRemoteObject.unexportObject(clientMethods,true);
                LOG.info("server is stopped");
                JOptionPane.showMessageDialog(null, "Server is stopped","Server is stopped", JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException | NotBoundException remoteException) {
                remoteException.printStackTrace();
            }
        });

        saveButton.addActionListener(e->save());
        loadButton.addActionListener(e ->load(clientMethods));
        exitButton.addActionListener(e-> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);
        rootPanel.add(buttonPanel,BorderLayout.SOUTH);

        FlowLayout dataPanelFlowLayout = new FlowLayout();
        dataPanelFlowLayout.setAlignment(FlowLayout.CENTER);
        dataPanelFlowLayout.setHgap(10);
        dataPanelFlowLayout.setVgap(10);
        JPanel dataPanel = new JPanel(dataPanelFlowLayout);
        portTextField = new JTextField("1049",10);
        JLabel hostLabel = new JLabel("host : " + InetAddress.getLocalHost()+" ");
        dataPanel.add(hostLabel);
        dataPanel.add(new JLabel("port : "));
        dataPanel.add(portTextField);
        dataPanel.add(new JLabel("count : "));
        labelCont = new JLabel(String.valueOf(participants.size()));
        dataPanel.add(labelCont);
        rootPanel.add(dataPanel,BorderLayout.NORTH);

        textArea = new JTextArea();
        Border border = textArea.getBorder();
        Border margin = new EmptyBorder(20,20,20,20);
        textArea.setBorder(new CompoundBorder(border, margin));
        JScrollPane scrollPane = new JScrollPane(textArea);
        rootPanel.add(scrollPane,BorderLayout.CENTER);
    }

    private void load(ClientMethodsImpl clientMethods) {
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
            String fileName = fileChooser.getSelectedFile().getPath();
            try {
                participants = RegisteredParticipateSaveToXml.unmarshalOutXMLs(fileName);
                clientMethods.setParticipants(participants);
            } catch (JAXBException jaxbException) {
                jaxbException.printStackTrace();
            }
        }
    }

    private void save() {
        if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(null)) {
            String path = fileChooser.getSelectedFile().getPath();
            try {
                String fileName = fileChooser.getSelectedFile().getName();
                String[] st = fileName.trim().split("\\.");
                if (st.length < 2){

                    String newFilename = fileName+".xml";
                    path = path.replace(fileName,newFilename);

                }else if(!st[st.length-1].equals("xml")){
                    path = path.replace(st[st.length-1],"xml");
                }
                RegisteredParticipateSaveToXml.marshalDataToXML(path,participants);
                JOptionPane.showMessageDialog(null,
                        "File " + path.trim() + " saved!", "Результати збережені ",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (JAXBException jaxbException) {
                JOptionPane.showMessageDialog(null,
                        "File " + path.trim() + "not saved, error!!", "Результати не збережені",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
