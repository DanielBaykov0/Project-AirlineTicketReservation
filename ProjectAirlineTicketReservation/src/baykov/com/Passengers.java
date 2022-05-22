package baykov.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.Connection;

public class Passengers implements ActionListener {

    private final JFrame frame = new JFrame("Passengers");
    private final JLabel label = new JLabel("Manage Passengers");
    private final JTable table = new JTable();
    private final JLabel passengerName = new JLabel("Name");
    private final JTextField passengerNameText = new JTextField();
    private final JLabel nationality = new JLabel("Nationality");
    private final JTextField nationalityText = new JTextField();
    private final JLabel gender = new JLabel("Gender");
    private JComboBox<String> genderBox;
    private final JLabel passportNumber = new JLabel("Passport Number");
    private final JTextField passportNumberText = new JTextField();
    private final JLabel address = new JLabel("Address");
    private final JTextField addressText = new JTextField();
    private final JLabel phone = new JLabel("Phone");
    private final JTextField phoneText = new JTextField();
    private final JButton buttonSave = new JButton("Save");
    private final JButton buttonEdit = new JButton("Edit");
    private final JButton buttonDelete = new JButton("Delete");
    private final JButton buttonBack = new JButton("Back");

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Passengers() {

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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passengerTableMouseClick(evt);
            }
        });

        label.setBounds(250, 50, 400, 35);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Calibri", Font.BOLD, 30));
        label.setForeground(Color.BLACK);

        passengerName.setBounds(75, 100, 100, 20);
        passengerName.setFont(new Font("Calibri", Font.BOLD, 15));
        passengerName.setForeground(Color.BLACK);

        passengerNameText.setBounds(50, 130, 100, 25);
        passengerNameText.setFont(new Font("Calibri", Font.PLAIN, 15));
        passengerNameText.setForeground(Color.BLACK);

        nationality.setBounds(180, 100, 100, 20);
        nationality.setFont(new Font("Calibri", Font.BOLD, 15));
        nationality.setForeground(Color.BLACK);

        nationalityText.setBounds(180, 130, 100, 25);
        nationalityText.setFont(new Font("Calibri", Font.PLAIN, 15));
        nationalityText.setForeground(Color.BLACK);

        gender.setBounds(320, 100, 100, 20);
        gender.setFont(new Font("Calibri", Font.BOLD, 15));
        gender.setForeground(Color.BLACK);

        String[] genderItems = {"Male", "Female"};
        genderBox = new JComboBox<>(genderItems);
        genderBox.setBounds(310, 130, 100, 25);

        passportNumber.setBounds(440, 100, 150, 20);
        passportNumber.setFont(new Font("Calibri", Font.BOLD, 15));
        passportNumber.setForeground(Color.BLACK);

        passportNumberText.setBounds(460, 130, 100, 25);
        passportNumberText.setFont(new Font("Calibri", Font.PLAIN, 15));
        passportNumberText.setForeground(Color.BLACK);

        address.setBounds(620, 100, 100, 20);
        address.setFont(new Font("Calibri", Font.BOLD, 15));
        address.setForeground(Color.BLACK);

        addressText.setBounds(610, 130, 100, 25);
        addressText.setFont(new Font("Calibri", Font.PLAIN, 15));
        addressText.setForeground(Color.BLACK);

        phone.setBounds(770, 100, 100, 20);
        phone.setFont(new Font("Calibri", Font.BOLD, 15));
        phone.setForeground(Color.BLACK);

        phoneText.setBounds(750, 130, 100, 25);
        phoneText.setFont(new Font("Calibri", Font.PLAIN, 15));
        phoneText.setForeground(Color.BLACK);

        buttonSave.setBounds(150, 200, 100, 25);
        buttonSave.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonSave.setFocusable(false);
        buttonSave.addActionListener(this);

        buttonEdit.setBounds(300, 200, 100, 25);
        buttonEdit.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonEdit.setFocusable(false);
        buttonEdit.addActionListener(this);

        buttonDelete.setBounds(450, 200, 100, 25);
        buttonDelete.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonDelete.setFocusable(false);
        buttonDelete.addActionListener(this);

        buttonBack.setBounds(600, 200, 100, 25);
        buttonBack.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonBack.setFocusable(false);
        buttonBack.addActionListener(this);

        frame.add(buttonBack);
        frame.add(buttonDelete);
        frame.add(buttonEdit);
        frame.add(buttonSave);
        frame.add(phoneText);
        frame.add(phone);
        frame.add(addressText);
        frame.add(address);
        frame.add(passportNumberText);
        frame.add(passportNumber);
        frame.add(genderBox);
        frame.add(gender);
        frame.add(nationalityText);
        frame.add(nationality);
        frame.add(passengerNameText);
        frame.add(passengerName);
        frame.add(label);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void displayPassengers() {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM passengers");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {

        passengerNameText.setText("");
        nationalityText.setText("");
        passportNumberText.setText("");
        addressText.setText("");
        phoneText.setText("");
    }

    String string = "";
    
    private void passengerTableMouseClick(MouseEvent evt) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        int index = table.getSelectedRow();
        string = defaultTableModel.getValueAt(index, 1).toString();
        nationalityText.setText(defaultTableModel.getValueAt(index, 2).toString());
        genderBox.setSelectedItem(defaultTableModel.getValueAt(index, 3).toString());
        passportNumberText.setText(defaultTableModel.getValueAt(index, 4).toString());
        addressText.setText(defaultTableModel.getValueAt(index, 5).toString());
        phoneText.setText(defaultTableModel.getValueAt(index, 6).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonBack) {
            new MainFrame();
            this.frame.dispose();
        }

        if (e.getSource() == buttonEdit) {
            if (passengerNameText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Select a passenger");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
                    String query = "UPDATE passengers SET nat=?, gender=?, pass=?, address=?, phone=? WHERE name=?";
                    PreparedStatement edit = connection.prepareStatement(query);
                    edit.setString(6, passengerNameText.getText());
                    edit.setString(1, nationalityText.getText());
                    edit.setString(2, genderBox.getSelectedItem().toString());
                    edit.setString(3, passportNumberText.getText());
                    edit.setString(4, addressText.getText());
                    edit.setString(5, phoneText.getText());

                    int row = edit.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Passenger Updated");
                    connection.close();
                    displayPassengers();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        if (e.getSource() == buttonDelete) {
            if (string.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a passenger");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
                    String query = "DELETE FROM passengers WHERE name ='" + string + "'";
                    Statement del = connection.createStatement();
                    del.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Passenger Deleted");
                    displayPassengers();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        if (e.getSource() == buttonSave) {
            if (passengerNameText.getText().isEmpty() || nationalityText.getText().isEmpty() || passportNumberText.getText().isEmpty()
                    || addressText.getText().isEmpty() || phoneText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Missing information");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "wolf", "hangman");
                    PreparedStatement add = connection.prepareStatement("INSERT INTO passengers(name, nat, gender, pass, address, phone) VALUES(?, ?, ?, ?, ?, ?)");
                    add.setString(1, passengerNameText.getText());
                    add.setString(2, nationalityText.getText());
                    add.setString(3, genderBox.getSelectedItem().toString());
                    add.setString(4, passportNumberText.getText());
                    add.setString(5, addressText.getText());
                    add.setString(6, phoneText.getText());

                    int row = add.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Passenger Added");
                    add.close();
                    connection.close();
                    displayPassengers();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}














