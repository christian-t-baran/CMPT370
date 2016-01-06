package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import teambuilder.TeamBuilderLogicLayer;
import view.GameOptionsPanel;
import view.GameWindows;
import view.TeamBuilderPanel;

/**
 * The controller that manages which window the user can see.
 * 
 * @author Group B2
 */
public class WindowManager implements ActionListener {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 693;

    /** The frame to hold the panel with the current view of the game. */
    private GameWindows view;

    private TeamBuilderLogicLayer teamInfo;

    private GameOptionsPanel gameOptions;

    private TeamBuilderPanel buildTeams;

    /** This is public so the controller can pass the board size to the run. */
    public int boardSize;
    
    private static Controller controller;
    
    /** Create a view object, and have the view display the initial welcome message. */
    public void start() {
        view = new GameWindows(HEIGHT, WIDTH);
        /** this is passed in as an ActionListener */
        view.showWelcomeWindow(this);
    }


    /** Helper function, to print an error dialog box with string */
    private void error(String str) {
        JOptionPane.showMessageDialog(null, str, "ERROR", JOptionPane.WARNING_MESSAGE);
    }

    // private void warning(String str) {
    // JOptionPane.showMessageDialog(null, str, "WARNING", JOptionPane.WARNING_MESSAGE);
    // }

    /** Helper function, to print yes/no dialog box with string */
    private int yesno(String str, String type) {
        return JOptionPane.showConfirmDialog(null, str, type, JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        if (actionCommand.equals("newgame")) {
            /** this is passed in as an ActionListener */
            gameOptions = view.showGameOptionsWindow(this);
        } else if (actionCommand.equals("selectgameoptions")) {
            /** Initialize game to have number of teams selected by user */
            String numTeams = ((String) (gameOptions.getTeamsOptions()));
            switch (numTeams) {
                case "2":
                    teamInfo = new TeamBuilderLogicLayer(2);
                    break;
                case "3":
                    teamInfo = new TeamBuilderLogicLayer(3);
                    break;
                case "6":
                    teamInfo = new TeamBuilderLogicLayer(6);
                    break;
                default:
                    error("Select a valid number of teams.");
                    break;
            }

            if ("Select" == gameOptions.getBoardOptions()) {
                error("Select a valid board size.");
            } else {
                boardSize = Integer.parseInt(gameOptions.getBoardOptions());
                buildTeams = view.showTeamBuilderWindow(this, teamInfo);
            }
        } else if (actionCommand.equals("addrobot")) {
            /** Add selected robot to the selected team */
            String selectedRobot = ((String) (buildTeams.getRobotOption()));
            int selectedTeam = buildTeams.getTeamOption();
            int robotSerialNumber = 0;

            /** make sure a valid robot has been chosen */
            if (!selectedRobot.equals("Select Robot Type")) {
                /** get only the integer value from the robot string */
                robotSerialNumber =
                        Integer.parseInt(selectedRobot.substring(0, selectedRobot.indexOf(" ")));

                switch (selectedTeam) {
                // TODO figure out if can shorten this for one generic case
                    case 1:
                        if (!teamInfo.isFull(1)) {
                            buildTeams.team1.append(selectedRobot + "\n");
                            teamInfo.addRobotToTeam(1, robotSerialNumber);
                            break;
                        } else {
                            error("Teams can only have 4 robots.");
                            break;
                        }
                    case 2:
                        if (!teamInfo.isFull(2)) {
                            buildTeams.team2.append(selectedRobot + "\n");
                            teamInfo.addRobotToTeam(2, robotSerialNumber);
                            break;
                        } else {
                            error("Teams can only have 4 robots.");
                            break;
                        }
                    case 3:
                        if (!teamInfo.isFull(3)) {
                            buildTeams.team3.append(selectedRobot + "\n");
                            teamInfo.addRobotToTeam(3, robotSerialNumber);
                            break;
                        } else {
                            error("Teams can only have 4 robots.");
                            break;
                        }
                    case 4:
                        if (!teamInfo.isFull(4)) {
                            buildTeams.team4.append(selectedRobot + "\n");
                            teamInfo.addRobotToTeam(4, robotSerialNumber);
                            break;
                        } else {
                            error("Teams can only have 4 robots.");
                            break;
                        }
                    case 5:
                        if (!teamInfo.isFull(5)) {
                            buildTeams.team5.append(selectedRobot + "\n");

                            teamInfo.addRobotToTeam(5, robotSerialNumber);
                            break;
                        } else {
                            error("Teams can only have 4 robots.");
                            break;
                        }
                    case 6:
                        if (!teamInfo.isFull(6)) {
                            buildTeams.team6.append(selectedRobot + "\n");
                            teamInfo.addRobotToTeam(6, robotSerialNumber);
                            break;
                        } else {
                            error("Teams can only have 4 robots.");
                            break;
                        }
                    default:
                        error("Select a valid team.");
                        break;
                }
            } else if (selectedRobot.equals("Select Robot Type")) {
                error("Select a valid robot.");
            }
        } else if (actionCommand.equals("removerobots")) {
            int selectedTeam = buildTeams.getTeamOption();

            if (0 == selectedTeam) {
                error("Select a valid team.");
            } else {
                /**
                 * Prompt user to see if they want to remove all robots from the selected team,
                 * check their answer
                 */
                if ((yesno("Remove all robots from Team " + selectedTeam, "WARNING") == 0)) {
                    switch (selectedTeam) {
                        case 1:
                            if (!teamInfo.isEmpty(1)) {
                                buildTeams.team1.setText(null);
                                teamInfo.removeRobotsFromTeam(1);
                                break;
                            } else {
                                error("No robots to remove.");
                                break;
                            }
                        case 2:
                            if (!teamInfo.isEmpty(2)) {
                                buildTeams.team2.setText(null);
                                teamInfo.removeRobotsFromTeam(2);
                                break;
                            } else {
                                error("No robots to remove.");
                                break;
                            }
                        case 3:
                            if (!teamInfo.isEmpty(3)) {
                                buildTeams.team3.setText(null);
                                teamInfo.removeRobotsFromTeam(3);
                                break;
                            } else {
                                error("No robots to remove.");
                                break;
                            }
                        case 4:
                            if (!teamInfo.isEmpty(4)) {
                                buildTeams.team4.setText(null);
                                teamInfo.removeRobotsFromTeam(4);
                                break;
                            } else {
                                error("No robots to remove.");
                                break;
                            }
                        case 5:
                            if (!teamInfo.isEmpty(5)) {
                                buildTeams.team5.setText(null);
                                teamInfo.removeRobotsFromTeam(5);
                                break;
                            } else {
                                error("No robots to remove.");
                                break;
                            }
                        case 6:
                            if (!teamInfo.isEmpty(6)) {
                                buildTeams.team6.setText(null);
                                teamInfo.removeRobotsFromTeam(6);
                                break;
                            } else {
                                error("No robots to remove.");
                                break;
                            }
                    }
                }
            }
        } else if (actionCommand.equals("selectteamoptions")) {
            // TODO when done testing uncomment this block
            switch (teamInfo.getNumberOfTeams()) {
                case 2:
                    if (!teamInfo.isFull(1) || !teamInfo.isFull(2)) {
                        error("Make sure teams have 4 robots in them.");
                        break;
                    } else {
                        view.showRunOptionsWindow(this);
                        break;
                    }
                case 3:
                    if (!teamInfo.isFull(1) || !teamInfo.isFull(2) || !teamInfo.isFull(3)) {
                        error("Make sure teams have 4 robots in them.");
                        break;
                    } else {
                        view.showRunOptionsWindow(this);
                        break;
                    }
                case 6:
                    if (!teamInfo.isFull(1) || !teamInfo.isFull(2) || !teamInfo.isFull(3)
                            || !teamInfo.isFull(4) || !teamInfo.isFull(5) || !teamInfo.isFull(6)) {
                        error("Make sure teams have 4 robots in them.");
                        break;
                    } else {
                        view.showRunOptionsWindow(this);
                    }
            }
            // view.showRunOptionsWindow(this);
        } else if (actionCommand.equals("home")) {
            /** Prompt user if they're sure that they want to start over, check result */
            if ((yesno("Going Home Will Remove All Progress \nAre You Sure?", "WARNING")) == 0) {
                view.showWelcomeWindow(this);
            }
        } else if (actionCommand.equals("simulation")) {
        	controller = new Controller(teamInfo.startMatch(), boardSize);
            view.showSimulationWindow(controller, boardSize, controller);
            controller.begin();
        } else if (actionCommand.equals("results")) {
        	JOptionPane.showMessageDialog(null, "This mode does not work at this time, sorry.",
       				"ERROR", JOptionPane.WARNING_MESSAGE);
        } else if (actionCommand.equals("advresults")) {
            JOptionPane.showMessageDialog(null, "This mode does not work at this time, sorry.",
       				"ERROR", JOptionPane.WARNING_MESSAGE);
        } else if (actionCommand.equals("testbench")) {
        	controller = new Controller(teamInfo.startMatch(), boardSize);
            view.showTestBenchWindow(controller, boardSize, controller);
            controller.begin();
        }
    }
}
