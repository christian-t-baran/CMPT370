package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import teambuilder.TeamBuilderLogicLayer;

/**
 * A panel to allow the user to build the robot teams, with the option to import a script for a
 * robot not already included in the library, a button to go back to the options panel, and a button
 * to terminate the program.
 * 
 * @author Peggy Anderson (11171137) Group B2
 */
public class TeamBuilderPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    static final int BUTTON_SPACER_SIZE = 20;
    static final int BUTTON_WIDTH = 140;
    static final int BUTTON_HEIGHT = 30;
    static final int TEXT_WIDTH = 350;
    static final int TEXT_HEIGHT = 200;

    public JComboBox<String> robots;
    public JComboBox<String> selectedTeam;

    // JTextAreas made public so it can be added to and erased in the WindowManager 
    public JTextArea team1;
    public JTextArea team2;
    public JTextArea team3;
    public JTextArea team4;
    public JTextArea team5;
    public JTextArea team6;

    /**
     * Add JComboBox for selecting a robot type and a team number, and the add and remove buttons to
     * add/remove robots from the selected team
     * 
     * @param listener the class listening for the event that signals a button was pressed
     * @param teamInfo the access to the information about the teams
     */
    private void selectRobots(ActionListener listener, TeamBuilderLogicLayer teamInfo) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);


        // Select Robot Drop Down
        robots = new JComboBox<String>();
        robots.addItem("Select Robot Type");
        for (int i = 0; i < teamInfo.getRobotList().size(); i++){
            robots.addItem(teamInfo.getRobotList().get(i));
        }
        robots.setPreferredSize(new Dimension(250, 30));
        robots.setBackground(Color.BLACK);
        robots.setOpaque(true);
        robots.setForeground(Color.WHITE);
        robots.setFont(new Font("Calibri", Font.PLAIN, 24));
        robots.addActionListener(listener);
        panel.add(robots);
        panel.add(Box.createHorizontalStrut(BUTTON_SPACER_SIZE));


        // Select Team Drop Down
        selectedTeam = new JComboBox<String>();
        selectedTeam.addItem("Select Team");
        try {
            for (int i = 0; i < teamInfo.getNumberOfTeams(); i++) {
                selectedTeam.addItem("Team " + (i + 1));
            }
        } catch (Exception e) {

        }
        selectedTeam.setPreferredSize(new Dimension(250, 30));
        selectedTeam.setBackground(Color.BLACK);
        selectedTeam.setOpaque(true);
        selectedTeam.setForeground(Color.WHITE);
        selectedTeam.setFont(new Font("Calibri", Font.PLAIN, 24));
        selectedTeam.addActionListener(listener);
        panel.add(selectedTeam);
        panel.add(Box.createHorizontalStrut(BUTTON_SPACER_SIZE));

        // Add Button
        JButton add = new JButton("Add");
        add.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        add.setBackground(Color.BLACK);
        add.setOpaque(true);
        add.setBorderPainted(false);
        add.setForeground(Color.WHITE);
        add.addActionListener(listener);
        add.setActionCommand("addrobot");
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(add);
        panel.add(Box.createHorizontalStrut(BUTTON_SPACER_SIZE));


        // Remove button
        JButton remove = new JButton("Remove");
        remove.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        remove.setBackground(Color.BLACK);
        remove.setOpaque(true);
        remove.setBorderPainted(false);
        remove.setForeground(Color.WHITE);
        remove.addActionListener(listener);
        remove.setActionCommand("removerobots");
        remove.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(remove);

        add(panel);
    }


    /**
     * @return item selected from robots JCombobox
     */
    public String getRobotOption() {
        return (String) robots.getSelectedItem();
    }


    /**
     * @return item selected from selectedTeam JCombobox
     */
    public int getTeamOption() {
        return selectedTeam.getSelectedIndex();
    }


    /** Adds a new panel with JTextAreas for each team to the main panel */
    private void teamWindows() {
        team1 = new JTextArea();
        team2 = new JTextArea();
        team3 = new JTextArea();
        team4 = new JTextArea();
        team5 = new JTextArea();
        team6 = new JTextArea();

        team1.setBackground(Color.BLACK);
        team2.setBackground(Color.BLACK);
        team3.setBackground(Color.BLACK);
        team4.setBackground(Color.BLACK);
        team5.setBackground(Color.BLACK);
        team6.setBackground(Color.BLACK);

        team1.setForeground(Color.WHITE);
        team2.setForeground(Color.WHITE);
        team3.setForeground(Color.WHITE);
        team4.setForeground(Color.WHITE);
        team5.setForeground(Color.WHITE);
        team6.setForeground(Color.WHITE);

        team1.setEditable(false);
        team2.setEditable(false);
        team3.setEditable(false);
        team4.setEditable(false);
        team5.setEditable(false);
        team6.setEditable(false);

        team1.setColumns(5);
        team2.setColumns(5);
        team3.setColumns(5);
        team4.setColumns(5);
        team5.setColumns(5);
        team6.setColumns(5);


        JPanel teams1To3 = new JPanel();
        teams1To3.setBackground(Color.BLACK);
        teams1To3.setForeground(Color.WHITE);

        JPanel teams4To6 = new JPanel();
        teams4To6.setBackground(Color.BLACK);
        teams4To6.setForeground(Color.WHITE);

        team1.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));
        team2.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));
        team3.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));
        team4.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));
        team5.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));
        team6.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));

        teams1To3.add(team1);
        teams1To3.add(team2);
        teams1To3.add(team3);
        teams4To6.add(team4);
        teams4To6.add(team5);
        teams4To6.add(team6);

        add(teams1To3);
        add(teams4To6);
    }

    /**
     * Adds a new panel with JButtons select, home, and quit to the main panel
     * 
     * @param listener the class listening for the event that signals a button was pressed
     */
    private void addButtons(ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);

        // Select Button
        JButton select = new JButton("Select");
        select.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        select.setBackground(Color.BLACK);
        select.setOpaque(true);
        select.setBorderPainted(false);
        select.setForeground(Color.WHITE);
        select.addActionListener(listener);
        select.setActionCommand("selectteamoptions");
        panel.add(select);


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

        add(panel);
    }


    /**
     * Create the team builder panel, with the game name, a text area for each team that will
     * display robots (if any) selected to be in the team, a drop down menu to select a robot type
     * to insert to robot team, a button to add a robot to a team, a button to remove a robot from a
     * team, select the board size, a button to continue the simulation process, a button to go back
     * to the options panel, and a button to close terminate the program.
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param listener the class listening for the event that signals a button was pressed
     * @param teamInfo the access to the information about the teams
     */
    public TeamBuilderPanel(int width, int height, ActionListener listener,
            TeamBuilderLogicLayer teamInfo) {
        setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, height / 35)));

        JLabel label = new JLabel("Build Teams");
        label.setFont(new Font("Calibri", Font.PLAIN, 50));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        selectRobots(listener, teamInfo);
        teamWindows();
        addButtons(listener);
    }
}
