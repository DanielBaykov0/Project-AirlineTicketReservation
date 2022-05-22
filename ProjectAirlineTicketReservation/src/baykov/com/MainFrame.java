package baykov.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener {

    private JFrame frame = new JFrame("Airline Ticket Reservation System");
    private JLabel image = new JLabel();
    private JLabel label = new JLabel("My Airline System");
    private JLabel label2 = new JLabel();
    private JButton buttonFlights = new JButton("Flights");
    private JButton buttonPassengers = new JButton("Passengers");
    private JButton buttonBooking = new JButton("Booking");
    private JButton buttonCancellation = new JButton("Cancellation");

    public MainFrame() {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(660, 515);
        frame.setLocationRelativeTo(null);
        // makes the frame appear in the center of the screen
        frame.setResizable(false);
        frame.setLayout(null);

        image.setIcon(new ImageIcon("/home/baykov/IdeaProjects/ProjectAirlineTicketReservation/plane.png"));
        // add an image
        image.setBounds(0,80,660,320);

        label.setBounds(0,0,660,80);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Calibri", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        // makes the label visible

        label2.setBounds(0,400,660,80);
        label2.setBackground(Color.BLACK);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setOpaque(true);

        buttonFlights.setBounds(40, 420, 100, 50);
        buttonFlights.setFont(new Font("Calibri", Font.BOLD, 18));
        buttonFlights.setBackground(Color.BLACK);
        // makes the button black
        buttonFlights.setForeground(Color.WHITE);
        // name of the button is white
        buttonFlights.setBorder(null);
        // border of the button not visible
        buttonFlights.setFocusable(false);
        buttonFlights.addActionListener(this);

        buttonPassengers.setBounds(170, 420, 120, 50);
        buttonPassengers.setFont(new Font("Calibri", Font.BOLD, 18));
        buttonPassengers.setBackground(Color.BLACK);
        buttonPassengers.setForeground(Color.WHITE);
        buttonPassengers.setBorder(null);
        buttonPassengers.setFocusable(false);
        buttonPassengers.addActionListener(this);

        buttonBooking.setBounds(320, 420, 120, 50);
        buttonBooking.setFont(new Font("Calibri", Font.BOLD, 18));
        buttonBooking.setBackground(Color.BLACK);
        buttonBooking.setForeground(Color.WHITE);
        buttonBooking.setBorder(null);
        buttonBooking.setFocusable(false);
        buttonBooking.addActionListener(this);

        buttonCancellation.setBounds(470, 420, 150, 50);
        buttonCancellation.setFont(new Font("Calibri", Font.BOLD, 18));
        buttonCancellation.setBackground(Color.BLACK);
        buttonCancellation.setForeground(Color.WHITE);
        buttonCancellation.setBorder(null);
        buttonCancellation.setFocusable(false);
        buttonCancellation.addActionListener(this);

        frame.add(buttonFlights);
        frame.add(buttonPassengers);
        frame.add(buttonBooking);
        frame.add(buttonCancellation);
        frame.add(label);
        frame.add(label2);
        frame.add(image);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonFlights) {
            try {
                frame.dispose();
                new FlightInfo().displayFlight();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (e.getSource() == buttonPassengers) {
            try {
                frame.dispose();
                Passengers passengers = new Passengers();
                passengers.displayPassengers();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (e.getSource() == buttonBooking) {
            try {
                frame.dispose();
                Booking booking = new Booking();
                booking.displayBooking();
                booking.getPassengerName();
                booking.getFlight();
                booking.getPassengerData();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (e.getSource() == buttonCancellation) {
            try {
                frame.dispose();
                Cancellation cancellation = new Cancellation();
                cancellation.getFlightCode();
                cancellation.getName();
                cancellation.displayCancellation();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
