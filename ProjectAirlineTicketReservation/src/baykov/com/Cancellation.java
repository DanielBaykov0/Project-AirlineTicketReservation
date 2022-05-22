package baykov.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cancellation implements ActionListener {

    private final JFrame frame = new JFrame("Cancellation");
    private final JTable table = new JTable();
    private final JLabel cancellation = new JLabel("Ticket Cancellation");
    private final JLabel flightCode = new JLabel("Flight Code");
    private final JComboBox flightCodeBox;
    private final JLabel name = new JLabel("Name");
    private final JTextField nameText = new JTextField();
    private final JLabel cancelDate = new JLabel("Cancel Date");
    private final JTextField cancelDateText = new JTextField();
    private final JButton buttonCancel = new JButton("Cancel");
    private final JButton buttonReset = new JButton("Reset");
    private final JButton buttonBack = new JButton("Back");

    java.sql.Connection connection = null;
    Statement statement = null;
    Statement st = null;
    ResultSet resultSet = null;
    ResultSet rs = null;

    public Cancellation() {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 900, 600);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setForeground(Color.BLACK);
        frame.setOpacity(1);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 300, 860, 250);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        table.setBackground(Color.WHITE);
        table.setFont(new Font("Calibri", Font.PLAIN, 15));
        table.setDefaultEditor(Object.class, null);
        // table can't be edited

        cancellation.setBounds(250, 50, 350, 50);
        cancellation.setFont(new Font("Calibri", Font.BOLD, 30));
        cancellation.setHorizontalAlignment(SwingConstants.CENTER);
        cancellation.setForeground(Color.BLACK);

        flightCode.setBounds(200, 140, 100, 25);
        flightCode.setFont(new Font("Calibri", Font.BOLD, 15));
        flightCode.setForeground(Color.BLACK);

        flightCodeBox = new JComboBox<>();
        flightCodeBox.setBounds(200, 170, 100, 25);
        flightCodeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getName();
            }
        });

        name.setBounds(420, 140, 100, 25);
        name.setFont(new Font("Calibri", Font.BOLD, 15));
        name.setForeground(Color.BLACK);

        nameText.setBounds(400, 170, 100, 25);
        nameText.setFont(new Font("Calibri", Font.PLAIN, 15));

        cancelDate.setBounds(600, 140, 100, 25);
        cancelDate.setFont(new Font("Calibri", Font.BOLD, 15));
        cancelDate.setForeground(Color.BLACK);

        cancelDateText.setBounds(600, 170, 100, 25);
        cancelDateText.setFont(new Font("Calibri", Font.PLAIN, 15));

        buttonCancel.setBounds(250, 220, 100, 25);
        buttonCancel.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonCancel.setFocusable(false);
        buttonCancel.addActionListener(this);

        buttonReset.setBounds(400, 220, 100, 25);
        buttonReset.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonReset.setFocusable(false);
        buttonReset.addActionListener(this);

        buttonBack.setBounds(550, 220, 100, 25);
        buttonBack.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonBack.setFocusable(false);
        buttonBack.addActionListener(this);

        frame.add(buttonBack);
        frame.add(buttonReset);
        frame.add(buttonCancel);
        frame.add(cancelDateText);
        frame.add(cancelDate);
        frame.add(nameText);
        frame.add(name);
        frame.add(flightCodeBox);
        frame.add(flightCode);
        frame.add(cancellation);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void getFlightCode() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
            statement = connection.createStatement();
            String query = "SELECT * FROM booking";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String fCode = resultSet.getString("flight_code");
                flightCodeBox.addItem(fCode);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getName() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
            st = connection.createStatement();
            String query = "SELECT * FROM booking WHERE flight_code='" + flightCodeBox.getSelectedItem().toString() + "'";
            rs = st.executeQuery(query);

            while (rs.next()) {
                nameText.setText(rs.getString("name"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void clear() {
        nameText.setText("");
        cancelDateText.setText("");
    }

    private void cancel() {

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
                String query = "DELETE FROM booking WHERE flight_code ='" + flightCodeBox.getSelectedItem().toString() + "'";
                Statement del = connection.createStatement();
                del.executeUpdate(query);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
    }

    public void displayCancellation() {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM cancellation");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == flightCodeBox) {
            getName();
        }

        if (e.getSource() == buttonReset) {
            clear();
        }

        if (e.getSource() == buttonBack) {
            new MainFrame();
            this.frame.dispose();
        }

        if (e.getSource() == buttonCancel) {
            if (flightCodeBox.getSelectedIndex() == -1 || nameText.getText().isEmpty()
                    || cancelDateText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Missing information");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
                    PreparedStatement add = connection.prepareStatement("INSERT INTO cancellation(name, flight_code, cancel_date) VALUES(?, ?, ?)");
                    add.setString(1, nameText.getText());
                    add.setString(2, flightCodeBox.getSelectedItem().toString());
                    add.setString(3, cancelDateText.getText());

                    int row = add.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Ticket Canceled");
                    add.close();
                    connection.close();
                    cancel();
                    displayCancellation();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}

