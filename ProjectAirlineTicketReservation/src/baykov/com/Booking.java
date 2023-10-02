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

public class Booking extends Component implements ActionListener {

    private final JFrame frame = new JFrame("Tickets Booking");
    private final JTable table = new JTable();
    private final JLabel booking = new JLabel("Booking");
    private final JLabel passengerName = new JLabel("Name");
    private final JComboBox passengerNameBox;
    private final JLabel flightCode = new JLabel("Flight Code");
    private final JComboBox flightCodeBox;
    private final JLabel gender = new JLabel("Gender");
    private final JTextField genderText = new JTextField();
    private final JLabel passportNumber = new JLabel("Passport Number");
    private final JTextField passportNumberText = new JTextField();
    private final JLabel amount = new JLabel("Amount");
    private final JTextField amountText = new JTextField();
    private final JLabel nationality = new JLabel("Nationality");
    private final JTextField nationalityText = new JTextField();
    private final JButton buttonBook = new JButton("Book");
    private final JButton buttonReset = new JButton("Reset");
    private final JButton buttonBack = new JButton("Back");

    java.sql.Connection connection = null;
    Statement statement = null;
    Statement st = null;
    ResultSet resultSet = null;
    ResultSet rs = null;

    public Booking() {

        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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

        booking.setBounds(280, 40, 300, 30);
        booking.setFont(new Font("Calibri", Font.BOLD, 30));
        booking.setForeground(Color.BLACK);
        booking.setHorizontalAlignment(SwingConstants.CENTER);

        flightCode.setBounds(40, 90, 100, 25);
        flightCode.setFont(new Font("Calibri", Font.BOLD, 15));
        flightCode.setForeground(Color.BLACK);

        flightCodeBox = new JComboBox<>();
        flightCodeBox.setBounds(40, 120, 100, 25);

        passengerName.setBounds(190, 90, 100, 25);
        passengerName.setFont(new Font("Calibri", Font.BOLD, 15));
        passengerName.setForeground(Color.BLACK);

        passengerNameBox = new JComboBox<>();
        passengerNameBox.setBounds(170, 120, 100, 25);
        passengerNameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPassengerName();
                getPassengerData();
            }
        });

        gender.setBounds(320, 90, 100, 25);
        gender.setFont(new Font("Calibri", Font.BOLD, 15));
        gender.setForeground(Color.BLACK);

        genderText.setBounds(300, 120, 100, 25);
        genderText.setFont(new Font("Calibri", Font.PLAIN, 15));

        passportNumber.setBounds(430, 90, 150, 25);
        passportNumber.setFont(new Font("Calibri", Font.BOLD, 15));
        passportNumber.setForeground(Color.BLACK);

        passportNumberText.setBounds(450, 120, 100, 25);
        passportNumberText.setFont(new Font("Calibri", Font.PLAIN, 15));

        amount.setBounds(610, 90, 100, 25);
        amount.setFont(new Font("Calibri", Font.BOLD, 15));
        amount.setForeground(Color.BLACK);

        amountText.setBounds(600, 120, 100, 25);
        amountText.setFont(new Font("Calibri", Font.PLAIN, 15));

        nationality.setBounds(730, 90, 100, 25);
        nationality.setFont(new Font("Calibri", Font.BOLD, 15));
        nationality.setForeground(Color.BLACK);

        nationalityText.setBounds(730, 120, 100, 25);
        nationalityText.setFont(new Font("Calibri", Font.PLAIN, 15));

        buttonBook.setBounds(250, 190, 100, 25);
        buttonBook.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonBook.setFocusable(false);
        buttonBook.addActionListener(this);

        buttonReset.setBounds(400, 190, 100, 25);
        buttonReset.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonReset.setFocusable(false);
        buttonReset.addActionListener(this);

        buttonBack.setBounds(550, 190, 100, 25);
        buttonBack.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonBack.setFocusable(false);
        buttonBack.addActionListener(this);

        frame.add(buttonBack);
        frame.add(buttonReset);
        frame.add(buttonBook);
        frame.add(nationalityText);
        frame.add(nationality);
        frame.add(amountText);
        frame.add(amount);
        frame.add(passportNumberText);
        frame.add(passportNumber);
        frame.add(genderText);
        frame.add(gender);
        frame.add(flightCodeBox);
        frame.add(flightCode);
        frame.add(passengerNameBox);
        frame.add(passengerName);
        frame.add(booking);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void getPassengerName() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
            st = connection.createStatement();
            String query = "SELECT * FROM passengers";
            rs = st.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                passengerNameBox.addItem(name);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getPassengerData() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
            st = connection.createStatement();
            String query = "SELECT * FROM passengers WHERE name='" + passengerNameBox.getSelectedItem().toString() + "'";
            rs = st.executeQuery(query);
            if (rs.next()) {
                genderText.setText(rs.getString("gender"));
                passportNumberText.setText(rs.getString("pass"));
                nationalityText.setText(rs.getString("nat"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getFlight() {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
            st = connection.createStatement();
            String query = "SELECT * FROM flight";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String fCode = rs.getString("flight_code");
                flightCodeBox.addItem(fCode);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void displayBooking() {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM booking");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        passportNumberText.setText("");
        genderText.setText("");
        amountText.setText("");
        nationalityText.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == flightCodeBox) {
            getFlight();
        }

        if (e.getSource() == passengerNameBox) {
            getPassengerName();
            getPassengerData();
        }

        if (e.getSource() == buttonBack) {
            new MainFrame();
            this.frame.dispose();
        }

        if (e.getSource() == buttonReset) {
            clear();
        }

        if (e.getSource() == buttonBook) {
            if (passengerNameBox.getSelectedIndex() == -1 || flightCodeBox.getSelectedIndex() == -1 || genderText.getText().isEmpty()
                    || passportNumberText.getText().isEmpty() || amountText.getText().isEmpty() || nationalityText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Missing information");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
                    PreparedStatement add = connection.prepareStatement("INSERT INTO booking(name, flight_code, gender, pass, amount, nat) VALUES(?, ?, ?, ?, ?, ?)");
                    add.setString(1, passengerNameBox.getSelectedItem().toString());
                    add.setString(2, flightCodeBox.getSelectedItem().toString());
                    add.setString(3, genderText.getText());
                    add.setString(4, passportNumberText.getText());
                    add.setInt(5, Integer.parseInt(amountText.getText()));
                    add.setString(6, nationalityText.getText());

                    int row = add.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Ticket Booked");
                    add.close();
                    connection.close();
                    displayBooking();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
