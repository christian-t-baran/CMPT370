package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import board.Board;
import control.Controller;

/**
 * A panel to allow the user to view a running simulation, go back to the welcome panel, or to
 * terminate the program.
 * 
 * @author Peggy Anderson (11171137) Group B2
 */
public class SimulationPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    static final int IMG_WIDTH = 40;
    static final int IMG_HEIGHT = 40;

    static final int BUTTON_SPACER_SIZE = 20;
    static final int BUTTON_WIDTH = 140;
    static final int BUTTON_HEIGHT = 30;

    private Board board;
    private GameInfoPanel gameInfo;
    
    public JTextArea consoleText;
    public JTextArea resultsText;


    /**
     * Paints the results on the main panelat 
     */
    private void results(Controller c) {
        JPanel panel = new JPanel(new BorderLayout() );
        
        gameInfo = GameInfoPanel.getGameInfo();
        gameInfo.initGameInfoPanel(c);
        panel.add( GameInfoPanel.getGameInfo() );
        
        add(panel, BorderLayout.LINE_START);
    }

    /**
     * Paints the board on the main panel
     */
    private void board(int size, Controller c) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);

        board = Board.getBoard();
        board.initBoard(size, c);
        
        panel.add( board );

        add(panel, BorderLayout.EAST);
    }

    /**
     * Adds a new panel with JButtons to manipulate the game (fastforward to completion,
     * fastforward, pause, play, stop) to the main panel
     * 
     * @param listener
     */
    private void buttons(ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 100, 10));
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);

        JButton fastForwardX2 = new JButton();
        JButton fastForward = new JButton();
        JButton pause = new JButton();
        JButton play = new JButton();
        JButton stop = new JButton();

        fastForwardX2.setBorderPainted(false);
        fastForward.setBorderPainted(false);
        pause.setBorderPainted(false);
        play.setBorderPainted(false);
        stop.setBorderPainted(false);

        try {
            Image ff2 = ImageIO.read(getClass().getResource("/imgs/FastForward2.png"));
            fastForwardX2.setIcon(new ImageIcon(ff2.getScaledInstance(IMG_WIDTH, IMG_HEIGHT,
                    Image.SCALE_DEFAULT)));
            Image ff = ImageIO.read(getClass().getResource("/imgs/FastForward.png"));
            fastForward.setIcon(new ImageIcon(ff.getScaledInstance(IMG_WIDTH, IMG_HEIGHT,
                    Image.SCALE_DEFAULT)));
            Image pauseImg = ImageIO.read(getClass().getResource("/imgs/pause.png"));
            pause.setIcon(new ImageIcon(pauseImg.getScaledInstance(IMG_WIDTH, IMG_HEIGHT,
                    Image.SCALE_DEFAULT)));
            Image resumeImg = ImageIO.read(getClass().getResource("/imgs/resume.png"));
            play.setIcon(new ImageIcon(resumeImg.getScaledInstance(IMG_WIDTH, IMG_HEIGHT,
                    Image.SCALE_DEFAULT)));
            Image stopImg = ImageIO.read(getClass().getResource("/imgs/stop.png"));
            stop.setIcon(new ImageIcon(stopImg.getScaledInstance(IMG_WIDTH, IMG_HEIGHT,
                    Image.SCALE_DEFAULT)));
        } catch (IOException e) {
        }

        fastForwardX2.setPreferredSize(new Dimension(40, 40));
        fastForwardX2.addActionListener(listener);
        fastForwardX2.setActionCommand("fastForwardX2");

        fastForward.setPreferredSize(new Dimension(40, 40));
        fastForward.addActionListener(listener);
        fastForward.setActionCommand("fastForward");

        pause.setPreferredSize(new Dimension(40, 40));
        pause.addActionListener(listener);
        pause.setActionCommand("pause");

        play.setPreferredSize(new Dimension(40, 40));
        play.addActionListener(listener);
        play.setActionCommand("play");

        stop.setPreferredSize(new Dimension(40, 40));
        stop.addActionListener(listener);
        stop.setActionCommand("stop");


        panel.add(stop);
        panel.add(pause);
        panel.add(play);
        panel.add(fastForward);
        panel.add(fastForwardX2);

        add(panel, BorderLayout.PAGE_START);

        panel.add(stop);
        panel.add(pause);
        panel.add(play);
        panel.add(fastForward);
        panel.add(fastForwardX2);

        add(panel, BorderLayout.PAGE_START);
    }

    /**
     * Adds a new panel with JButtons home and quit to the main panel
     * 
     * @param listener the class listening for the event that signals a button was pressed
     */
    private void addButtons(ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.WHITE);

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
     * Create the simulation panel with the game name, a button to pause the simulation, a button to
     * fast forward the simulation, a button to terminate the simulation (generates results), a text
     * area for up-to-date team statistics, an area to show current game scene, a button to go back
     * to the welcome panel, and a button to terminate the program.
     * 
     * @param width width of the panel
     * @param height height of the panel
     * @param listener the class listening for the event that signals a button was pressed
     * @param gameInfo the source for information about the game
     */
    public SimulationPanel(int width, int height, ActionListener listener, int boardSize, Controller controller) {
        setSize(width, height);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        results(controller);
        board(boardSize,controller);
        buttons(controller);
        addButtons(controller);
    }
}
