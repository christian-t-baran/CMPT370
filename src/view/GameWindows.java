package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import control.Controller;

import teambuilder.TeamBuilderLogicLayer;

/**
 * The GameWindows is a frame used to display various views of the game and it�s results.
 * 
 * @author Peggy Anderson (11171137) Group B2
 */
public class GameWindows extends JFrame {
    private static final long serialVersionUID = 1L;

    static final int OPTIONS_WIDTH = 350;
    static final int OPTIONS_HEIGHT = 100;

    /**
     * This will initialize the frame for the various windows throughout the game, where height and
     * width are the dimensions of the frame.
     * 
     * @param height
     * @param width
     */
    public GameWindows(int height, int width) {
        setTitle("RoboSport 370");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /**
         * Width and height passed in are those for the game panel, so add enough height for the
         * information panel, and pack the frame with the contentPane size set to its preferred
         * size.
         */
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
        pane.setPreferredSize(new Dimension(width, height));
        pack();
    }

    /**
     * Sets the frame visible showing the panel passed in.
     * 
     * @param panel panel to be shown in view
     */
    public void displayPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().validate();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Construct the welcome view, making it visible.
     * 
     * @param listener the listener for the button pressed
     */
    public void showWelcomeWindow(ActionListener listener) {
        WelcomePanel panel = new WelcomePanel(1150, 550, listener);
        displayPanel(panel);
    }

    /**
     * Construct and makes visible a view showing the options view that will allow the user to
     * select team and board sizes for the game.
     * 
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
    public GameOptionsPanel showGameOptionsWindow(ActionListener listener) {
        GameOptionsPanel panel = new GameOptionsPanel(getWidth(), getHeight(), listener);
        displayPanel(panel);
        return panel;
    }

    /**
     * Construct and makes visible a view showing the team builder window that will allow the user
     * to select robots and place them into teams, also gives the option to import script.
     * 
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
    public TeamBuilderPanel showTeamBuilderWindow(ActionListener listener,
            TeamBuilderLogicLayer teamInfo) {
        TeamBuilderPanel panel = new TeamBuilderPanel(getWidth(), getHeight(), listener, teamInfo);
        displayPanel(panel);
        return panel;
    }

    /**
     * Construct and makes visible a view showing the options view that will allow the user to
     * select team and board sizes for the game.
     * 
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
    public void showRunOptionsWindow(ActionListener listener) {
        RunOptionsPanel panel = new RunOptionsPanel(getWidth(), getHeight(), listener);
        displayPanel(panel);
    }


    /**
     * Construct and makes visible a view showing the simulation window that will show the game to
     * the user. A panel to display the game is in the middle right hand side. A panel with team
     * statistics is on the left. A panel with the options to pause/fast forward/stop game will is
     * at the top. The panels get the needed game information via the Logger, and the options panel
     * will pass events with the listener to the controller.
     * 
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
    public void showSimulationWindow(ActionListener listener, int size, Controller c) {
        SimulationPanel panel = new SimulationPanel(getWidth(), getHeight(), listener, size, c);
        displayPanel(panel);
    }


    /** PUT TESTBENCH ON THE BACK BURNER */

     /**
     * Construct and makes visible a view showing the test bench window that will show a test
     bench
     * version of the game to the user. A panel to display the game is in the middle right hand
     * side. A panel with team/robot statistics is on the left. A panel with the options to
     * pause/fast forward/stop game will is at the top. The panels get the needed game information
     * via the Logger, and the options panel will pass events with the listener to the controller.
     *
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
     public void showTestBenchWindow(ActionListener listener, int size, Controller c) {
         TestBenchPanel panel = new TestBenchPanel(getWidth(), getHeight(), listener, size, c);
         displayPanel(panel);
     }
    
    /**
     * Construct and makes visible a view showing the results window that will show the results of
     * game to the user.
     * 
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
    public void showResultsWindow(ActionListener listener) {
        ResultsPanel panel = new ResultsPanel(getWidth(), getHeight(), listener);
        displayPanel(panel);
    }

    /**
     * Construct and makes visible a view showing the advanced statistics results window that will
     * show the results of game to the user.
     * 
     * @param gameInfo the access to the information about the game
     * @param listener the listener for the button
     */
    public void showAdvResultsWindow(ActionListener listener) {
        AdvResultsPanel panel = new AdvResultsPanel(getWidth(), getHeight(), listener);
        displayPanel(panel);
    }
}
