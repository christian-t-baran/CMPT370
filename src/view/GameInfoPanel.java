package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import control.Controller;

/**
 * Singleton class to hold the GameResults
 */
public class GameInfoPanel extends JPanel {
	private static GameInfoPanel gameInfo = null;

    private static final long serialVersionUID = 1L;
    private final int WIDTH = 400;
    private final int HEIGHT = 1100;

    private JTextArea consoleText;
    private JTextArea resultsText;
    
    /** Allows the width and height of the font to be calculated */
    FontMetrics metrics;


    private Controller theController;

    /**
     * Sets up the panel layout
     */
    private GameInfoPanel() {
    	setLayout( new BorderLayout() );
    	setPreferredSize( new Dimension(WIDTH, HEIGHT) );

    	resultsText = new JTextArea();
    	resultsText.setBackground(Color.BLACK);
		resultsText.setForeground(Color.WHITE);
		resultsText.setEditable(false);
		resultsText.setPreferredSize(new Dimension(30, 150));
		JScrollPane sp = new JScrollPane(resultsText);
        sp.setPreferredSize(new Dimension(30, 250));
        sp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    	consoleText = new JTextArea();
    	consoleText.setBackground(Color.BLACK);
    	consoleText.setForeground(Color.WHITE);
    	consoleText.setEditable(false);
    	JScrollPane sp2 = new JScrollPane(consoleText);
    	sp2.setPreferredSize(new Dimension(30, 350));
    	sp2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	
    	//sp2.setPreferredSize( new Dimension(400, 300));
    	
    	setBackground( Color.BLACK );
    	
        add(sp, BorderLayout.PAGE_START);
        add(sp2, BorderLayout.PAGE_END);

    }

    /**
     * @return Returns an instance of the GameInfoPanel
     */
    public static GameInfoPanel getGameInfo(){
		if(gameInfo == null){
			gameInfo = new GameInfoPanel();
		}
		return gameInfo;
    }
    
    /**
     * Initializes the GameInfoPanel
     * @param c the controller
     */
    public void initGameInfoPanel(Controller c){
    	if(theController == null){
    		theController = c;
    	}
    }
    
    @Override
    public synchronized void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	// Only paint results if game is over	
    	if( theController.gameIsOver() ){
        	resultsText.setText("");
    		consoleText.setText("");

        	resultsText.append( theController.getGameResults() );
    	}
    	// Otherwise print turn info
    	else{
        	resultsText.setText("");
//    		consoleText.setText("");

    		resultsText.append( theController.getTurnInfo() );
    
        	String consoleLines = "";
        	
        	for(String line : theController.getConsole() ){
        		consoleLines += "\n" + line;
        	}
        	
        	consoleText.append("\n");
        	consoleText.append( consoleLines );	
    	}
    }
}
