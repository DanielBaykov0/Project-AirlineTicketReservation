package baykov.com;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.*;

public class FlightInfo implements ActionListener {

    private final JFrame frame = new JFrame("Flight Info");
    private final JTable table = new JTable();
    private final JLabel flightInformation = new JLabel("Flight Information");
    private final JLabel flightCodeLabel = new JLabel("Flight code");
    private final JTextField flightCodeText = new JTextField();
    private final JLabel sourceLabel = new JLabel("Source");
    private JComboBox<String> sourceBox;
    private final JLabel destinationLabel = new JLabel("Destination");
    private JComboBox<String> destinationBox;
    private final JLabel takeOfDate = new JLabel("Take of date");
    private final JTextField takeOfDateText = new JTextField();
    private final JLabel numberOfSeats = new JLabel("Number of seats");
    private final JTextField numberOfSeatsText = new JTextField();
    private final JButton buttonSave = new JButton("Save");
    private final JButton buttonEdit = new JButton("Edit");
    private final JButton buttonDelete = new JButton("Delete");
    private final JButton buttonBack = new JButton("Back");

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public FlightInfo() {

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
        // gets headers names
        table.setBackground(Color.WHITE);
        table.setFont(new Font("Calibri", Font.PLAIN, 15));
        table.setDefaultEditor(Object.class, null);
        // table can't be edited
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                flightTableMouseClick(evt);
            }
        });

        flightInformation.setBounds(250, 50, 400, 35);
        flightInformation.setHorizontalAlignment(SwingConstants.CENTER);
        flightInformation.setFont(new Font("Calibri", Font.BOLD, 30));
        flightInformation.setForeground(Color.BLACK);

        flightCodeLabel.setBounds(50, 100, 100, 20);
        flightCodeLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        flightCodeLabel.setForeground(Color.BLACK);

        flightCodeText.setBounds(50, 130, 100, 25);
        flightCodeText.setFont(new Font("Calibri", Font.PLAIN, 15));
        flightCodeText.setForeground(Color.BLACK);

        sourceLabel.setBounds(220, 100, 100, 20);
        sourceLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        sourceLabel.setForeground(Color.BLACK);

        String[] sourceItems = {"Bulgaria", "China", "Russia", "United States", "Australia", "Saudi Arabia"};
        sourceBox = new JComboBox<>(sourceItems);
        // adds items to the combobox
        sourceBox.setBounds(200, 130, 100, 25);

        destinationLabel.setBounds(370, 100, 100, 20);
        destinationLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        destinationLabel.setForeground(Color.BLACK);

        String[] destinationItems = {"Bulgaria", "China", "Russia", "United States", "Australia", "Saudi Arabia"};
        destinationBox = new JComboBox<>(destinationItems);
        destinationBox.setBounds(370, 130, 100, 25);

        takeOfDate.setBounds(520, 100, 120, 20);
        takeOfDate.setFont(new Font("Calibri", Font.BOLD, 15));
        takeOfDate.setForeground(Color.BLACK);

        takeOfDateText.setBounds(520, 130, 100, 25);
        takeOfDateText.setFont(new Font("Calibri", Font.PLAIN, 15));
        takeOfDateText.setForeground(Color.BLACK);

        numberOfSeats.setBounds(670, 100, 140, 20);
        numberOfSeats.setFont(new Font("Calibri", Font.BOLD, 15));
        numberOfSeats.setForeground(Color.BLACK);

        numberOfSeatsText.setBounds(680, 130, 100, 25);
        numberOfSeatsText.setFont(new Font("Calibri", Font.PLAIN, 15));
        numberOfSeatsText.setForeground(Color.BLACK);

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

        frame.add(buttonSave);
        frame.add(buttonEdit);
        frame.add(buttonDelete);
        frame.add(buttonBack);
        frame.add(destinationLabel);
        frame.add(destinationBox);
        frame.add(takeOfDate);
        frame.add(takeOfDateText);
        frame.add(numberOfSeats);
        frame.add(numberOfSeatsText);
        frame.add(sourceLabel);
        frame.add(sourceBox);
        frame.add(flightCodeText);
        frame.add(flightCodeLabel);
        frame.add(flightInformation);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    public void displayFlight() {

        // displays all the flights in the table
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM flight");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {

        // clears fields after doing an action
        flightCodeText.setText("");
        takeOfDateText.setText("");
        numberOfSeatsText.setText("");
    }

    String key = "";

    private void flightTableMouseClick(MouseEvent evt) {
        // makes the table clickable
        // gets the value from the database and displays it in the textfields
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        int index = table.getSelectedRow();
        key = defaultTableModel.getValueAt(index, 0).toString();
        // String key = flight_code value, which is in column 0
        sourceBox.setSelectedItem(defaultTableModel.getValueAt(index, 1).toString());
        destinationBox.setSelectedItem(defaultTableModel.getValueAt(index, 2).toString());
        takeOfDateText.setText(defaultTableModel.getValueAt(index, 3).toString());
        numberOfSeatsText.setText(defaultTableModel.getValueAt(index, 4).toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonBack) {
            new MainFrame();
            this.frame.dispose();
        }

        if (e.getSource() == buttonEdit) {
            if (key.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a flight");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
                    String query = "UPDATE flight SET source=?, destination=?, take_of_date=?, seats=? WHERE flight_code=?";
                    PreparedStatement edit = connection.prepareStatement(query);
                    edit.setString(5, key);
                    edit.setString(1, sourceBox.getSelectedItem().toString());
                    edit.setString(2, destinationBox.getSelectedItem().toString());
                    edit.setString(3, takeOfDateText.getText());
                    edit.setString(4, numberOfSeatsText.getText());

                    int row = edit.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Flight Updated");
                    connection.close();
                    displayFlight();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        if (e.getSource() == buttonDelete) {
            if (key.equals("")) {
                JOptionPane.showMessageDialog(null, "Select a flight");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
                    String query = "DELETE FROM flight WHERE flight_code ='" + key + "'";
                    Statement del = connection.createStatement();
                    del.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Flight Deleted");
                    displayFlight();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        if (e.getSource() == buttonSave) {
            if (flightCodeText.getText().isEmpty() || sourceBox.getSelectedIndex() == -1 || destinationBox.getSelectedIndex() == -1
                    || takeOfDateText.getText().isEmpty() || numberOfSeatsText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Missing information");
            } else {
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/airline?currentSchema=air", "postgres", "hangman96z");
                    PreparedStatement add = connection.prepareStatement("INSERT INTO flight VALUES(?, ?, ?, ?, ?)");
                    add.setString(1, flightCodeText.getText());
                    add.setString(2, sourceBox.getSelectedItem().toString());
                    add.setString(3, destinationBox.getSelectedItem().toString());
                    add.setString(4, takeOfDateText.getText());
                    add.setString(5, numberOfSeatsText.getText());

                    int row = add.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Flight Added");
                    add.close();
                    connection.close();
                    displayFlight();
                    clear();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}

