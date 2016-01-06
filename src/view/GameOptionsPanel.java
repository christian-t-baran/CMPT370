package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * A panel to allow the user to select the number of teams for the simulation, and how many
 * mountains/forests they would like on the map, or allows them to terminate the program.
 * 
 * @author Peggy Anderson (11171137) Group B2
 */
public class GameOptionsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    static final int BUTTON_SPACER_SIZE = 20;
    static final int BUTTON_WIDTH = 140;
    static final int BUTTON_HEIGHT = 30;

    private JComboBox<String> board;
    private JComboBox<String> teams;


    /**
     * Adds a new panel with the JComboBox to select board options (amount of mountains/forests) to
     * the main panel
     * 
     * @param width size of panel
     * @param listener the class listening for the event that signals a button was pressed
     */
    private void boardOptions(int width, ActionListener listener) {
        // Add Mountain Options
        JPanel boardOptions = new JPanel();
        boardOptions.setMaximumSize(new Dimension(width, 60));
        boardOptions.setBackground(Color.BLACK);
        boardOptions.setForeground(Color.WHITE);

        JLabel selectBoard = new JLabel("Board Size:            ");
        selectBoard.setFont(new Font("Calibri", Font.PLAIN, 24));
        selectBoard.setForeground(Color.WHITE);
        boardOptions.add(selectBoard);

        board = new JComboBox<String>();
        board.addItem("Select");
        board.setPreferredSize(new Dimension(140, 30));
        board.setBackground(Color.BLACK);
        board.setOpaque(false);
        board.setForeground(Color.WHITE);
        board.setFont(new Font("Calibri", Font.PLAIN, 24));
        board.setSelectedItem(4);
        board.addActionListener(listener);
        boardOptions.add(board);
        
        add(boardOptions);
    }


    /**
     * Adds a new panel with the JComboBox to select team sizes to the main panel
     * 
     * @param width size of panel
     * @param listener the class listening for the event that signals a button was pressed
     */
    private void teamOptions(int width, ActionListener listener) {
        // Add Team Options
        JPanel teamOptions = new JPanel();
        teamOptions.setMaximumSize(new Dimension(width, 60));
        teamOptions.setBackground(Color.BLACK);
        teamOptions.setForeground(Color.WHITE);

        JLabel selectTeams = new JLabel("Team Size:             ");
        selectTeams.setFont(new Font("Calibri", Font.PLAIN, 24));
        selectTeams.setForeground(Color.WHITE);
        teamOptions.add(selectTeams);

        String[] teamStrings = {"Select", "2", "3", "6"};
        teams = new JComboBox<String>(teamStrings);
        teams.setPreferredSize(new Dimension(140, 30));
        teams.setBackground(Color.BLACK);
        teams.setOpaque(false);
        teams.setForeground(Color.WHITE);
        teams.setFont(new Font("Calibri", Font.PLAIN, 24));
        teams.setSelectedItem(2);
        teams.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unchecked")
                JComboBox<String> src = (JComboBox<String>) e.getSource();
                String teamValue = src.getSelectedItem().toString();
                int teams = Integer.parseInt(teamValue);
                board.removeAllItems();
                if (teams != 0) {
                    if (2 == teams) {
                        board.addItem("Select");
                        board.addItem("7");
                        board.addItem("9");
                        board.addItem("11");
                    } else if (3 == teams) {
                        board.addItem("Select");
                        board.addItem("9");
                        board.addItem("11");
                    } else if (6 == teams) {
                        board.addItem("Select");
                        board.addItem("9");
                        board.addItem("11");
                    }
                } 
            }
        });
        teamOptions.add(teams);

        add(teamOptions);
    }


    /**
     * @return item selected from team JCombobox
     */
    public String getTeamsOptions() {
        return (String) teams.getSelectedItem();
    }


    /**
     * @return item selected from mountains JCombobox
     */
    public String getBoardOptions() {
        return (String) board.getSelectedItem();
    }


    /**
     * Adds a new panel with JButtons select and quit to the main panel
     * 
     * @param listener the class listening for the event that signals a button was pressed
     */
    private void addButtons(ActionListener listener) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);

        // Add select button
        JButton select = new JButton("Select");
        select.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        select.setBackground(Color.BLACK);
        select.setOpaque(true);
        select.setBorderPainted(false);
        select.setForeground(Color.WHITE);
        select.addActionListener(listener);
        select.setActionCommand("selectgameoptions");
        select.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(select);
        panel.add(Box.createHorizontalStrut(BUTTON_SPACER_SIZE));

        JButton quit = new JButton("Quit");
        quit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        quit.setBackground(Color.BLACK);
        quit.setOpaque(true);
        quit.setBorderPainted(false);
        quit.setForeground(Color.WHITE);
        quit.addActionListener(e -> System.exit(0));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(quit);

        add(panel);
    }


    /**
     * Create the options panel, with the game name, a drop down menu to select the board size, a
     * drop down menu to select the number of teams, a button to close terminate the program.
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param listener the class listening for the event that signals a button was pressed
     */
    public GameOptionsPanel(int width, int height, ActionListener listener) {
        setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0, height / 5)));


        JLabel label2 = new JLabel("Game Configurations");
        label2.setFont(new Font("Calibri", Font.PLAIN, 50));
        label2.setForeground(Color.WHITE);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label2);

        add(Box.createRigidArea(new Dimension(0, height / 12)));

        teamOptions(width, listener);

        add(Box.createRigidArea(new Dimension(0, 5)));

        boardOptions(width, listener);

        add(Box.createRigidArea(new Dimension(0, BUTTON_SPACER_SIZE)));

        addButtons(listener);
    }
}
