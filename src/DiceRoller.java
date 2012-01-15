package BeardyRoller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * DiceRoller<br>
 * <br>
 * The DiceRoller class creates the GUI for the 5punk BeardyRoller app and handles the<br>
 * dice roll calls.  The app is designed for SLA Industries, Shadowrun v. 4, and Eclipse Phase RPGs.<br>
 * <br>
 * This is in fact a code.<br>
 * 
 * @author Joshua Krell a.k.a. "deject"
 * @version 0.92
 * @date 1/14/2012
 *
 */


public class DiceRoller implements ActionListener, ItemListener {

	/** the dice rolling object */
	private Dice spunkDice = new Dice();
	
	/** boolean for Edge use */
	private boolean shadowEdge = false;
	
	//graphical components
	private JFrame main;
	private JTabbedPane gameChoosingPane;
	private JPanel slaPanel, slaModPanel, slaResultPanel, shadowPanel,
                shadowDPPanel, shadowResultPanel, eclipsePanel, eclipseTNPanel,
                eclipseResultPanel;
	private JLabel slaModLabel, shadowDPLabel, slaResultLabel,
                shadowResultLabel, eclipseLabel, eclipseTNLabel,
                eclipseResultLabel;
	private JTextField slaModField, shadowDPField, eclipseTNField;
	private JTextArea slaResultArea, shadowResultArea, eclipseResultArea;
	private JButton slaRollButton, shadowRollButton, eclipseRollButton;
	private JCheckBox shadowEdgeCheck;
	private JPopupMenu rightClickMenu;
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem exit, about, copyItem;
	private ImageIcon beardIcon;
	
	/** About message. */
	private String aboutMessage = "5punk BeardyRoller v0.92\n"
		+ "Programmed by: Joshua \"deject\" Krell\n"
		+ "Date: 14 January 2012\n"
		+ "Visit: http://www.5punk.co.uk\n"
		+ "Copyright (c) 2012 Joshua Krell, all rights reserved.";

	
	/**
	 * Constructs the GUI.
	 */
	public DiceRoller(){
		
		PopupListener pop = new PopupListener();
		
		// construct SLA components
		slaPanel = new JPanel(new GridLayout(4, 1));
		slaModPanel = new JPanel(new GridLayout());
		slaModLabel = new JLabel("Modifier:");
		slaModField = new JTextField("0", 3);
		slaModField.setActionCommand("rollSLA");
		slaRollButton = new JButton("Roll the Dice!");
		slaRollButton.setActionCommand("rollSLA");
		slaResultPanel = new JPanel(new FlowLayout());
		slaResultLabel = new JLabel("Result: ");
		slaResultArea = new JTextArea(2, 25);
		slaResultArea.setLineWrap(true);
		
		
		// construct Shadowrun components
		shadowPanel = new JPanel(new GridLayout(4, 1));
		shadowDPPanel = new JPanel(new GridLayout());
		shadowDPLabel = new JLabel("Dice Pool: ");
		shadowDPField = new JTextField("1", 2);
		shadowDPField.setActionCommand("rollShadow");
		shadowEdgeCheck = new JCheckBox("includes Edge dice?");
		shadowRollButton = new JButton("Roll the Dice!");
		shadowRollButton.setActionCommand("rollShadow");
		shadowResultPanel = new JPanel(new FlowLayout());
		shadowResultLabel = new JLabel("Result: ");
		shadowResultArea = new JTextArea(2, 25);
		shadowResultArea.setLineWrap(true);
		rightClickMenu = new JPopupMenu();
		copyItem = new JMenuItem("Copy");
                
                // construct Eclipse Phase components
		eclipsePanel = new JPanel(new GridLayout(4, 1));
		eclipseTNPanel = new JPanel(new GridLayout());
		eclipseTNLabel = new JLabel("Target Number:");
		eclipseTNField = new JTextField("0", 3);
		eclipseTNField.setActionCommand("rollEclipse");
		eclipseRollButton = new JButton("Roll the Dice!");
		eclipseRollButton.setActionCommand("rollEclipse");
		eclipseResultPanel = new JPanel(new FlowLayout());
		eclipseResultLabel = new JLabel("Result: ");
		eclipseResultArea = new JTextArea(2, 25);
		eclipseResultArea.setLineWrap(true);
                
		// construct the menu bar
		exit = new JMenuItem("Exit");
		file = new JMenu("File");
		file.add(exit);
		about = new JMenuItem("About");
		help = new JMenu("Help");
		help.add(about);
		menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(help);
		
		// Register event listeners
		slaRollButton.addActionListener(this);
		slaModField.addActionListener(this);
		shadowRollButton.addActionListener(this);
		shadowDPField.addActionListener(this);
		shadowEdgeCheck.addItemListener(this);
                eclipseRollButton.addActionListener(this);
                eclipseTNField.addActionListener(this);
		copyItem.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);
		
		// Register mouse listener
		slaResultArea.addMouseListener(pop);
		shadowResultArea.addMouseListener(pop);
		
		// construct right-click menu
		rightClickMenu.add(copyItem);
		
		// construct SLA panel
		slaModPanel.add(slaModLabel);
		slaModPanel.add(slaModField);
		slaResultLabel.add(rightClickMenu);
		slaResultPanel.add(slaResultLabel);
		slaResultPanel.add(slaResultArea);
		slaPanel.add(slaModPanel);
		slaPanel.add(new JLabel(""));
		slaPanel.add(slaRollButton);
		slaPanel.add(slaResultPanel);
		
		// construct Shadowrun panel
		shadowDPPanel.add(shadowDPLabel);
		shadowDPPanel.add(shadowDPField);
		shadowResultPanel.add(rightClickMenu);
		shadowResultPanel.add(shadowResultLabel);
		shadowResultPanel.add(shadowResultArea);
		shadowPanel.add(shadowDPPanel);
		shadowPanel.add(shadowEdgeCheck);
		shadowPanel.add(shadowRollButton);
		shadowPanel.add(shadowResultPanel);
                
                // construct Eclipse Phase panel
                eclipseTNPanel.add(eclipseTNLabel);
		eclipseTNPanel.add(eclipseTNField);
		eclipseResultLabel.add(rightClickMenu);
		eclipseResultPanel.add(eclipseResultLabel);
		eclipseResultPanel.add(eclipseResultArea);
		eclipsePanel.add(eclipseTNPanel);
		eclipsePanel.add(new JLabel(""));
		eclipsePanel.add(eclipseRollButton);
		eclipsePanel.add(eclipseResultPanel);
		
		
		// add everything into main window
		main = new JFrame("5punk BeardyRoller");
		main.setJMenuBar(menuBar);
		getBeard();
		main.setIconImage(beardIcon.getImage());
		gameChoosingPane = new JTabbedPane();
		gameChoosingPane.addTab("SLA Industries", slaPanel);
		gameChoosingPane.addTab("Shadowrun v.4", shadowPanel);
                gameChoosingPane.addTab("Eclipse Phase", eclipsePanel);
                gameChoosingPane.setPreferredSize(new Dimension(340, 230));
                main.add(gameChoosingPane);
		
		main.setLocationByPlatform(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		//main.setResizable(false);
		main.setVisible(true);
			
	}
	
	/**
	 * Action handling for buttons and text fields.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e){
		
		if (e.getActionCommand().equals("rollSLA")){
			
			slaResultArea.setText(slaRoll());
			
		} else if (e.getActionCommand().equals("rollShadow")){
			
			shadowResultArea.setText(shadowRoll(Integer.parseInt(shadowDPField.getText()), shadowEdge));
			
		} else if (e.getActionCommand().equals("rollEclipse")){
			
			eclipseResultArea.setText(eclipseRoll());
			
		} else if (e.getSource() == copyItem){
			if (((JPopupMenu) copyItem.getParent()).getInvoker() == slaResultArea){
				slaResultArea.selectAll();
				slaResultArea.copy();
			} else {
				shadowResultArea.selectAll();
				shadowResultArea.copy();
			}
			
		} else if (e.getSource() == exit){
		
			System.exit(0);
			
		} else if (e.getSource() == about){
			
			showAbout();
			
		}
		
	}
	
	/**
	 * Action handling for telling app if character is rolling Edge dice
	 */
	public void itemStateChanged(ItemEvent e){
		
		if (e.getStateChange() == ItemEvent.SELECTED){
			shadowEdge = true;
		} else if (e.getStateChange() == ItemEvent.DESELECTED){
			shadowEdge = false;
		}
	}
	
	/**
	 * Right-click menu handling.
	 */
	private class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    private void maybeShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	            rightClickMenu.show(e.getComponent(),
	                       e.getX(), e.getY());
	        }
	    }

	}
	
	/**
	 * Rolls 2d10 for SLA Industries tests.
	 * 
	 * @return String		Results of dice roll
	 */
	private String slaRoll(){
		
		// roll the dice & shift from 0-9 to 1-10
		int[] rolls = spunkDice.rollD10(2);
                rolls[0]++;
                rolls[1]++;
				
		// get modifier
		int modifier = 0;
		
		try{
			modifier = Integer.parseInt(slaModField.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(main, slaModField.getText() + " is not a number!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
		}
		
		// compute the total value on the roll
		int total = rolls[0] + rolls[1] + modifier;
		
		// put results in string form
		String result = rolls[0] + ", " + rolls[1] + " + " + modifier + " = " + total;
		
		if(rolls[0] == 1 && rolls[1] == 1){
			result += " ERRORZ!";
		}
		
		return result;
	}
	
	/**
	 * Rolls Shadowrun character's dice pool for an action or skill.
	 * 
	 * @param dicePool		Size of character's dice pool
	 * @return String		Results of dice roll
	 */
	private String shadowRoll(int dicePool, boolean edge){
		
		// counters for number of hits, ones rolled, sixes rolled, and total dice rolled.
		int hits = 0;
		int ones = 0;
		int sixes = 0;
		int diceRolled = dicePool;
		
		// roll the dice
		int[] roll = spunkDice.rollD6(dicePool);
		
		String result = "";
		
		// parse dice rolls
		for (int index = 0; index < roll.length; index++){
			
			// add value to string for display
			int rollValue = roll[index];
			result += rollValue;
			if (index < (roll.length - 1)){
				result += ", ";
			}
			
			// count hits and ones (for glitches), and sixes (for Edge)
			if (rollValue == 5){				
				hits++;
			} else if (rollValue == 6){
				hits++;
				sixes++;
			} else if (rollValue == 1){
				ones++;
			}
		}
		
		// Re-rolling for Rule of Six
		if (edge){
			while(sixes > 0){
				
				// Add space for extra rolls
				result += ", ";
				diceRolled += sixes;
				int[] edgeRoll = spunkDice.rollD6(sixes);
				
				//  After sixes are re-rolled, reset the sixes count
				sixes = 0;
				
				// parse dice rolls
				for (int index = 0; index < edgeRoll.length; index++){
					
					// add value to string for display
					int rollValue = edgeRoll[index];
					result += rollValue;
					if (index < (edgeRoll.length - 1)){
						result += ", ";
					}
					
					// count hits and ones (for glitches), and sixes (for Edge)
					if (rollValue == 5){				
						hits++;
					} else if (rollValue == 6){
						hits++;
						sixes++;
					} else if (rollValue == 1){
						ones++;
					}
				}
			}
		}
		
		result += ": " + hits;
		if (hits == 1){
			result += " hit.";
		} else {
			result += " hits.";
		}
		
		// check for glitches
		if (ones >= ((double)diceRolled/2)){
			// critical glitch?
			if (hits > 0){
				result += " G!";
			} else {
				result += " CG!";
			}
		}
		
		return result;
	}
	
        /**
         * Rolls Eclipse Phase system 2d10 for target percentage. Determines test
         * success or failure, as well as Excellent Success/Severe Failure.
         * 
         * @return String               Result of dice roll
         */
        private String eclipseRoll(){
            
            // initialize output string
            String result = "";
            
            // get target number from text area
            int targetNumber = 0;
            
            try{
			targetNumber = Integer.parseInt(eclipseTNField.getText());
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(main, eclipseTNField.getText() + " is not a number!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
		}
            
            // roll 2d10 and use Eclipse Phase system to determine result
            int[] rolls = spunkDice.rollD10(2);
            int rollResult = (rolls[0] * 10) + rolls[1];
            
            // push dice roll onto outupt string
            result += rolls[0] + ", " + rolls[1] + ": " + rollResult;
            
            // detect critical
            if (rolls[0] == rolls[1]){
                result += " Critical!";
            }
            
            // determine success/fail and MoS/F
            if (rollResult > targetNumber){
                if ((rollResult - targetNumber) > 30){
                    result += " Severe";
                }
                result += " Failure!";
            }
            else {
                if ((targetNumber - rollResult) > 30){
                    result += " Excellent";
                }
                result += " Success!";
            }
            
            // return output string
            return result;
        }
        
	/**
	 * Displays an about box.
	 */
	public void showAbout(){
		
		JOptionPane.showMessageDialog(main, aboutMessage, "About 5punk BeardyRoller", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	/**
	 * Gets the beard image.
	 */
	public void getBeard(){
		
		beardIcon = new ImageIcon(getClass().getResource("/img/beard.gif"));

	}
	
	/**
	 * Calls DiceRoller() constructor 
	 */
	public static void main(String[] argv){
            DiceRoller diceRoller = new DiceRoller();
	}
}
