package client;

import pupbic_interfase.ClientMethods;
import pupbic_interfase.Participant;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Permission;
import java.util.logging.Logger;

import static javax.swing.GroupLayout.Alignment.*;

public class Client extends JFrame {

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    private final JFileChooser fileChooser = new JFileChooser();
    private JPanel rootPanel;
    private JTextField portTextField;
    private static final String NAME = "Registerable";
    private JLabel labelCont;
    private JTextField hostTextField;


    private JTextField nameTextField;
    private JTextField familyTextField;
    private JTextField organizationTextField;
    private JTextField reportTextField;
    private JTextField emailTextField;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager() {
                public void checkConnect(String host, int port, Object context) {
                }

                public void checkConnect(String host, int port) {
                }

                public void checkPermission(Permission perm) {
                }
            });

        }


        EventQueue.invokeLater(() -> {
            try {
                Client frame = new Client();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Client() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Client");
        setBounds(100, 100, 800, 600);
        fileChooser.setCurrentDirectory(new java.io.File("."));

        rootPanel = new JPanel(new BorderLayout(0, 0));
        setContentPane(rootPanel);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("Register");
        JButton clearButton = new JButton("Clear");
        JButton getInfoButton = new JButton("GetInfo");
        JButton exitButton = new JButton("Finish");

        registerButton.addActionListener(e -> {
            try {
                Registry registry = LocateRegistry.getRegistry(hostTextField.getText(), Integer.parseInt(portTextField.getText()));
                ClientMethods comp = (ClientMethods) registry.lookup(NAME);
                comp.register(new Participant(
                        nameTextField.getText()
                        , familyTextField.getText()
                        , organizationTextField.getText()
                        , reportTextField.getText()
                        , emailTextField.getText()
                ));
                JOptionPane.showMessageDialog(null, "Registered successful","Registered", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        clearButton.addActionListener(e -> {
            nameTextField.setText("");
            familyTextField.setText("");
            organizationTextField.setText("");
            reportTextField.setText("");
            emailTextField.setText("");
        });

        getInfoButton.addActionListener(e -> {
            try {
                Registry registry = LocateRegistry.getRegistry(hostTextField.getText(), Integer.parseInt(portTextField.getText()));
                ClientMethods comp = (ClientMethods) registry.lookup(NAME);

                JDialog d = new JDialog(this, "Info");
                StringBuilder stringBuilder = new StringBuilder();
                comp.getAllClient().forEach(x -> stringBuilder
                        .append("-------------------------------")
                        .append(System.lineSeparator())
                        .append(x)
                );
                JTextArea textArea= new JTextArea(stringBuilder.toString());
                JScrollPane scrollPane = new JScrollPane(textArea);
                d.add(scrollPane);
                d.setSize(400, 400);
                d.setVisible(true);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(getInfoButton);
        buttonPanel.add(exitButton);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        FlowLayout dataPanelFlowLayout = new FlowLayout();
        dataPanelFlowLayout.setAlignment(FlowLayout.CENTER);
        dataPanelFlowLayout.setHgap(10);
        dataPanelFlowLayout.setVgap(10);
        JPanel dataPanel = new JPanel(dataPanelFlowLayout);

        Label hostLabel = new Label("host :");
        hostTextField = new JTextField("localhost", 10);
        dataPanel.add(hostLabel);
        dataPanel.add(hostTextField);

        portTextField = new JTextField("1049", 10);
        dataPanel.add(new JLabel("port : "));
        dataPanel.add(portTextField);

        dataPanel.add(new JLabel("count : "));
        labelCont = new JLabel(String.valueOf(0));
        dataPanel.add(labelCont);

        rootPanel.add(dataPanel, BorderLayout.NORTH);

        JPanel userData = new JPanel();
        nameTextField = new JTextField(20);
        familyTextField = new JTextField(20);
        organizationTextField = new JTextField(20);
        reportTextField = new JTextField(20);
        emailTextField = new JTextField(20);

        Font font = new Font("name", Font.PLAIN, 17);
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        JLabel familyLabel = new JLabel("Family");
        familyLabel.setFont(font);
        JLabel organizationLabel = new JLabel("Organization");
        organizationLabel.setFont(font);
        JLabel reportLabel = new JLabel("Report");
        reportLabel.setFont(font);
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(font);

        GroupLayout layout = new GroupLayout(userData);
        userData.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(TRAILING)
                        .addComponent(nameLabel)
                        .addComponent(familyLabel)
                        .addComponent(organizationLabel)
                        .addComponent(reportLabel)
                        .addComponent(emailLabel))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(nameTextField)
                        .addComponent(familyTextField)
                        .addComponent(organizationTextField)
                        .addComponent(reportTextField)
                        .addComponent(emailTextField))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(familyLabel)
                        .addComponent(familyTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(organizationLabel)
                        .addComponent(reportTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(reportLabel)
                        .addComponent(organizationTextField))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(emailLabel)
                        .addComponent(emailTextField))
        );
        rootPanel.add(userData, BorderLayout.CENTER);
    }
}
