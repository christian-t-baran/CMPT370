package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * A panel to allow the user to view the results of a simulation, and allows the user to go back to
 * the welcome panel, and to terminate the program.
 * 
 * @author Peggy Anderson (11171137) Group B2
 */
public class ResultsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    static final int BUTTON_SPACER_SIZE = 20;
    static final int BUTTON_WIDTH = 140;
    static final int BUTTON_HEIGHT = 30;


    /**
     * Adds a new panel with JButtons home and quit to the main panel
     * 
     * @param listener the class listening for the event that signals a button was pressed
     */
    private void addButtons(ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);


        // Advance Results Button
        JButton advResults = new JButton("Advanced Results");
        advResults.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        advResults.setBackground(Color.BLACK);
        advResults.setOpaque(true);
        advResults.setBorderPainted(false);
        advResults.setForeground(Color.WHITE);
        advResults.addActionListener(listener);
        advResults.setActionCommand("advresults");
        panel.add(advResults);


        // Home Button
        JButton home = new JButton("Home");
        home.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        home.setBackground(Color.BLACK);
        home.setOpaque(true);
        home.setBorderPainted(false);
        home.setForeground(Color.WHITE);
        home.addActionListener(listener);
        home.setActionCommand("home");
        panel.add(home);


        // Quit Button
        JButton quit = new JButton("Quit");
        quit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        quit.setBackground(Color.BLACK);
        quit.setOpaque(true);
        quit.setBorderPainted(false);
        quit.setForeground(Color.WHITE);
        quit.addActionListener(e -> System.exit(0));
        panel.add(quit);

        add(panel, BorderLayout.PAGE_END);
    }

    /**
     * Create the results panel, with the game name, a text area to display the results of the
     * simulation ran that was not visible to the user, a button to view advanced statistics, a
     * button to go back to the welcome panel, and a button to terminate the program.
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param listener the class listening for the event that signals a button was pressed
     */
    public ResultsPanel(int height, int width, ActionListener listener) {
        setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Add Results Label
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);
        JLabel label = new JLabel("Results");
        label.setFont(new Font("Calibri", Font.PLAIN, 50));
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(Box.createRigidArea(new Dimension(0, height / 10)));
        panel.add(label);
        add(panel, BorderLayout.PAGE_START);


        // TODO ADD RESULTS
        // add(/*way results are being added*/, BorderLayout.CENTER);

        addButtons(listener);
    }
}
