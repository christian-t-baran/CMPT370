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
 * A panel to welcome the user and gives a choice to the user to continue to the process to create a
 * simulation or terminate the program.
 * 
 * @author Peggy Anderson (11171137) Group B2
 *
 */
public class WelcomePanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    static final int BUTTON_SPACER_SIZE = 20;
    static final int BUTTON_WIDTH = 140;
    static final int BUTTON_HEIGHT = 30;

    /**
     * Create a welcome panel, with the game name, a button to continue the simulation process, a
     * button to terminate the program.
     * 
     * @param width         width of the panel
     * @param height        height of the panel
     * @param listener      the class listening for the event that signals a button was pressed
     */
    public WelcomePanel(int width, int height, ActionListener listener) {
        setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(Box.createRigidArea(new Dimension(0, height/4)));
        
        JLabel label = new JLabel("Welcome to RoboSport 370");
        label.setFont(new Font("Calibri", Font.PLAIN, 72));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
        
        add(Box.createRigidArea(new Dimension(0, height/10)));
        
        JButton sim = new JButton("New Game");
        sim.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sim.setBackground(Color.BLACK);
        sim.setOpaque(true);
        sim.setBorderPainted(false);
        sim.setForeground(Color.WHITE);
        sim.setActionCommand("newgame");
        sim.addActionListener(listener);
        sim.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(sim);
        
        add(Box.createRigidArea(new Dimension(0, BUTTON_SPACER_SIZE)));
        
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
