package baykov.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn implements ActionListener {
    private final JFrame frame = new JFrame("My Log In");
    private final JLabel logInLabel = new JLabel("Log In");
    private final JLabel username = new JLabel("username: ");
    private final JTextField usernameText = new JTextField();
    private final JLabel password = new JLabel("password: ");
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton buttonLogIn = new JButton("Log in");

    public LogIn() {

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setBounds(240,220,600,400);
        frame.setLocationRelativeTo(null);
        // makes the frame appear in the center of the screen
        frame.setBackground(Color.DARK_GRAY);
        frame.setForeground(Color.WHITE);
        frame.setLayout(null);
        frame.setResizable(false);
        // can't be resized
        frame.setOpacity(1);
        // makes the frame visible

        logInLabel.setBounds(250, 50, 120, 40);
        logInLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        logInLabel.setHorizontalAlignment(SwingConstants.CENTER);

        username.setBounds(120,110,100,25);
        username.setFont(new Font("Calibri", Font.PLAIN, 15));

        usernameText.setBounds(230,110, 200,25);
        usernameText.setFont(new Font("Calibri", Font.PLAIN,15));

        password.setBounds(120,160, 100, 25);
        password.setFont(new Font("Calibri", Font.PLAIN,15));

        passwordField.setBounds(230,160,200,25);
        passwordField.setFont(new Font("Calibri", Font.PLAIN,15));

        buttonLogIn.setBounds(260, 230, 100, 30);
        buttonLogIn.setFont(new Font("Calibri", Font.BOLD,15));
        buttonLogIn.setFocusable(false);
        buttonLogIn.addActionListener(this);

        frame.add(buttonLogIn);
        frame.add(passwordField);
        frame.add(password);
        frame.add(usernameText);
        frame.add(username);
        frame.add(logInLabel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonLogIn) {
            if (usernameText.getText().isEmpty() || passwordField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Username and Password");
            } else if (usernameText.getText().equals("root") && passwordField.getText().equals("123")) {
                new MainFrame();
                this.frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Username or Password");
                usernameText.setText(null);
                passwordField.setText(null);
            }
        }
    }
}
