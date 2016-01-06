package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel to allow the user to select the running method of the simulation, allows them to go back
 * to team builder options, or allows them to terminate the program.
 * 
 * @author Peggy Anderson (11171137) Group B2
 */
public class RunOptionsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    static final int BUTTON_SPACER_SIZE = 20;
    static final int BUTTON_WIDTH = 140;
    static final int BUTTON_HEIGHT = 30;

    /**
     * Create the run options panel, with the game name, a button to select simulation mode, a
     * button to select test bend mode, a button to select results only mode, a button to go back to
     * team builder, a button to terminate the program.
     * 
     * @param width of the panel
     * @param height of the panel
     * @param listener the class listening for the event that signals a button was pressed
     */
    public RunOptionsPanel(int height, int width, ActionListener listener) {
        setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, height / 7)));

        JLabel label = new JLabel("Choose Running Style");
        label.setFont(new Font("Calibri", Font.PLAIN, 50));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        add(Box.createRigidArea(new Dimension(0, height / 18)));


        /** Add Simulation Button */
        JButton sim = new JButton("Simulation");
        sim.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sim.setBackground(Color.BLACK);
        sim.setOpaque(true);
        sim.setBorderPainted(false);
        sim.setForeground(Color.WHITE);
        sim.setActionCommand("simulation");
        sim.addActionListener(listener);
        sim.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(sim);
        add(Box.createRigidArea(new Dimension(0, BUTTON_SPACER_SIZE)));


        /** Add Results Button */
        JButton results = new JButton("Results");
        results.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        results.setBackground(Color.BLACK);
        results.setOpaque(true);
        results.setBorderPainted(false);
        results.setForeground(Color.WHITE);
        results.setActionCommand("results");
        results.addActionListener(listener);
        results.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(results);
        add(Box.createRigidArea(new Dimension(0, BUTTON_SPACER_SIZE)));


        /** Add Test Bench Button */
        JButton test = new JButton("Test Bench");
        test.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        test.setBackground(Color.BLACK);
        test.setOpaque(true);
        test.setBorderPainted(false);
        test.setForeground(Color.WHITE);
        test.setActionCommand("testbench");
        test.addActionListener(listener);
        test.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(test);
        add(Box.createRigidArea(new Dimension(0, BUTTON_SPACER_SIZE)));


        /** Add Quit Button */
        JButton quit = new JButton("Quit");
        quit.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        quit.setBackground(Color.BLACK);
        quit.setOpaque(true);
        quit.setBorderPainted(false);
        quit.setForeground(Color.WHITE);
        quit.addActionListener(e -> System.exit(0));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(quit);
    }
}
