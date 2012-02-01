package BeardyRoller;

import java.util.Date;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

/**
 * DiceRoller<br> <br> The DiceRoller class creates the GUI for the 5punk
 * BeardyRoller app and handles the<br> dice roll calls. The app is designed for
 * SLA Industries, Shadowrun v. 4, and Eclipse Phase RPGs.<br> <br> This is in
 * fact a code.<br>
 *
 * @author Joshua Krell a.k.a. "deject"
 * @version 0.93 @date 1/22/2012
 *
 */
public class DiceRoller implements ActionListener, ItemListener {

    /**
     * File name for output text file.
     */
    private static String OUTPUT_FILE = "rolls.txt";
    /**
     * the dice rolling object
     */
    private Dice spunkDice = new Dice();
    /**
     * boolean for Edge use
     */
    private boolean slaTextOut = false, shadowTextOut = false,
            shadowEdge = false, eclipseTextOut = false;
    //graphical components
    private JFrame main;
    private JTabbedPane gameChoosingPane;
    private JPanel slaPanel, slaModPanel, slaCheckBoxPanel, slaResultPanel,
            shadowPanel, shadowDPPanel, shadowCheckBoxPanel,
            shadowResultPanel, eclipsePanel, eclipseTNPanel,
            eclipseCheckBoxPanel, eclipseResultPanel;
    private JLabel slaModLabel, shadowDPLabel, slaResultLabel,
            shadowResultLabel, eclipseTNLabel,
            eclipseResultLabel;
    private JTextField slaModField, shadowDPField, eclipseTNField;
    private JTextArea slaResultArea, shadowResultArea, eclipseResultArea;
    private JButton slaRollButton, shadowRollButton, eclipseRollButton;
    private JCheckBox slaTextOutCheck, shadowTextOutCheck, shadowEdgeCheck,
            eclipseTextOutCheck;
    private JPopupMenu rightClickMenu;
    private JMenuBar menuBar;
    private JMenu file, help;
    private JMenuItem exit, about, copyItem;
    private ImageIcon beardIcon;
    /**
     * About message.
     */
    private static String ABOUT_MESSAGE = "5punk BeardyRoller v0.93\n"
            + "Programmed by: Joshua \"deject\" Krell\n"
            + "Date: 22 January 2012\n"
            + "Visit: http://www.5punk.co.uk\n"
            + "Copyright (c) 2012 Joshua Krell, all rights reserved.";

    /**
     * Constructs the GUI.
     */
    public DiceRoller() {

        PopupListener pop = new PopupListener();

        // construct SLA components
        slaPanel = new JPanel(new GridLayout(4, 1));
        slaModPanel = new JPanel(new GridLayout());
        slaModLabel = new JLabel("Modifier:");
        slaModField = new JTextField("0", 3);
        slaModField.setActionCommand("rollSLA");
        slaTextOutCheck = new JCheckBox("Output to " + OUTPUT_FILE + "?");
        slaRollButton = new JButton("Roll the Dice!");
        slaRollButton.setActionCommand("rollSLA");
        slaCheckBoxPanel = new JPanel(new FlowLayout(0));
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
        shadowTextOutCheck = new JCheckBox("Output to " + OUTPUT_FILE + "?");
        shadowEdgeCheck = new JCheckBox("includes Edge dice?");
        shadowRollButton = new JButton("Roll the Dice!");
        shadowRollButton.setActionCommand("rollShadow");
        shadowCheckBoxPanel = new JPanel(new FlowLayout(0));
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
        eclipseTextOutCheck = new JCheckBox("Output to " + OUTPUT_FILE + "?");
        eclipseRollButton = new JButton("Roll the Dice!");
        eclipseRollButton.setActionCommand("rollEclipse");
        eclipseCheckBoxPanel = new JPanel(new FlowLayout(0));
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
        slaTextOutCheck.addItemListener(this);
        shadowRollButton.addActionListener(this);
        shadowDPField.addActionListener(this);
        shadowTextOutCheck.addItemListener(this);
        shadowEdgeCheck.addItemListener(this);
        eclipseRollButton.addActionListener(this);
        eclipseTNField.addActionListener(this);
        eclipseTextOutCheck.addItemListener(this);
        copyItem.addActionListener(this);
        exit.addActionListener(this);
        about.addActionListener(this);

        // Register mouse listener
        slaResultArea.addMouseListener(pop);
        shadowResultArea.addMouseListener(pop);
        eclipseResultArea.addMouseListener(pop);

        // construct right-click menu
        rightClickMenu.add(copyItem);

        // construct SLA panel
        slaModPanel.add(slaModLabel);
        slaModPanel.add(slaModField);
        slaCheckBoxPanel.add(slaTextOutCheck);
        slaResultLabel.add(rightClickMenu);
        slaResultPanel.add(slaResultLabel);
        slaResultPanel.add(slaResultArea);
        slaPanel.add(slaModPanel);
        slaPanel.add(slaCheckBoxPanel);
        slaPanel.add(slaRollButton);
        slaPanel.add(slaResultPanel);

        // construct Shadowrun panel
        shadowDPPanel.add(shadowDPLabel);
        shadowDPPanel.add(shadowDPField);
        shadowCheckBoxPanel.add(shadowTextOutCheck);
        shadowCheckBoxPanel.add(shadowEdgeCheck);
        shadowResultPanel.add(rightClickMenu);
        shadowResultPanel.add(shadowResultLabel);
        shadowResultPanel.add(shadowResultArea);
        shadowPanel.add(shadowDPPanel);
        shadowPanel.add(shadowCheckBoxPanel);
        shadowPanel.add(shadowRollButton);
        shadowPanel.add(shadowResultPanel);

        // construct Eclipse Phase panel
        eclipseTNPanel.add(eclipseTNLabel);
        eclipseTNPanel.add(eclipseTNField);
        eclipseCheckBoxPanel.add(eclipseTextOutCheck);
        eclipseResultLabel.add(rightClickMenu);
        eclipseResultPanel.add(eclipseResultLabel);
        eclipseResultPanel.add(eclipseResultArea);
        eclipsePanel.add(eclipseTNPanel);
        eclipsePanel.add(eclipseTextOutCheck);
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
        main.setResizable(false);
        main.setVisible(true);

    }

    /**
     * Action handling for buttons and text fields.
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("rollSLA")) {

            // check if user wants to output to a file
            if (slaTextOut) {

                try {
                    // ask how many rolls?
                    int numOfRolls = Integer.valueOf(JOptionPane.showInputDialog(main, "How many rolls?", "How many?", JOptionPane.QUESTION_MESSAGE));
                    // do the rolls
                    for (int i = 0; i < numOfRolls; ++i) {

                        slaResultArea.setText(slaRoll());

                    }
                } catch (NumberFormatException nfe) {
                    // do nothing because user either entered something not a number or clicked cancel
                }

            } else {

                // just do one roll
                slaResultArea.setText(slaRoll());

            }

        } else if (e.getActionCommand().equals("rollShadow")) {

            // check if user wants to output to a file
            if (shadowTextOut) {

                try {
                    // ask how many rolls?
                    int numOfRolls = Integer.valueOf(JOptionPane.showInputDialog(main, "How many rolls?", "How many?", JOptionPane.QUESTION_MESSAGE));
                    // do the rolls
                    for (int i = 0; i < numOfRolls; ++i) {

                        shadowResultArea.setText(shadowRoll());

                    }
                } catch (NumberFormatException nfe) {
                    // do nothing because user either entered something not a number or clicked cancel
                }
            } else {

                // just do one roll
                shadowResultArea.setText(shadowRoll());

            }

        } else if (e.getActionCommand().equals("rollEclipse")) {

            // check if user wants to output to a file
            if (eclipseTextOut) {

                try {
                    // ask how many rolls?
                    int numOfRolls = Integer.valueOf(JOptionPane.showInputDialog(main, "How many rolls?", "How many?", JOptionPane.QUESTION_MESSAGE));
                    // do the rolls
                    for (int i = 0; i < numOfRolls; ++i) {

                        eclipseResultArea.setText(eclipseRoll());

                    }
                } catch (NumberFormatException nfe) {
                    // do nothing because user either entered something not a number or clicked cancel
                }

            } else {

                // just do one roll
                eclipseResultArea.setText(eclipseRoll());

            }

        } else if (e.getSource() == copyItem) {

            if (((JPopupMenu) copyItem.getParent()).getInvoker() == slaResultArea) {

                slaResultArea.selectAll();
                slaResultArea.copy();

            } else if (((JPopupMenu) copyItem.getParent()).getInvoker() == shadowResultArea) {

                shadowResultArea.selectAll();
                shadowResultArea.copy();

            } else if (((JPopupMenu) copyItem.getParent()).getInvoker() == eclipseResultArea) {

                eclipseResultArea.selectAll();
                eclipseResultArea.copy();

            }

        } else if (e.getSource() == exit) {

            System.exit(0);

        } else if (e.getSource() == about) {

            showAbout();

        }

    }

    /**
     * Action handling for telling app if character is rolling Edge dice
     */
    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {

            if (e.getSource() == slaTextOutCheck) {

                slaTextOut = true;

            } else if (e.getSource() == shadowTextOutCheck) {

                shadowTextOut = true;

            } else if (e.getSource() == shadowEdgeCheck) {

                shadowEdge = true;

            } else if (e.getSource() == eclipseTextOutCheck) {

                eclipseTextOut = true;

            }

        } else if (e.getStateChange() == ItemEvent.DESELECTED) {

            if (e.getSource() == slaTextOutCheck) {

                slaTextOut = false;

            } else if (e.getSource() == shadowTextOutCheck) {

                shadowTextOut = false;

            } else if (e.getSource() == shadowEdgeCheck) {

                shadowEdge = false;

            } else if (e.getSource() == eclipseTextOutCheck) {

                eclipseTextOut = false;
            }

        }

    }

    /**
     * Right-click menu handling.
     */
    private class PopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            maybeShowPopup(e);

        }

        @Override
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
     * @return String	Results of dice roll
     */
    private String slaRoll() {

        // roll the dice
        int[] rolls = spunkDice.rollD10(2);

        // get modifier
        int modifier = 0;

        try {
            modifier = Integer.parseInt(slaModField.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(main, slaModField.getText() + " is not a number!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
        }

        // compute the total value on the roll
        int total = rolls[0] + rolls[1] + modifier;

        // put results in string form
        String result = rolls[0] + ", " + rolls[1] + " + " + modifier + " = " + total;

        if (rolls[0] == 1 && rolls[1] == 1) {
            result += " ERRORZ!";
        }

        // write to output file if box is checked
        if (slaTextOut) {
            writeOutputToFile(result);
        }

        return result;
    }

    /**
     * Rolls Shadowrun character's dice pool for an action or skill.
     *
     * @param dicePool	Size of character's dice pool
     * @return String	Results of dice roll
     */
    private String shadowRoll() {

        // placeholder for dice pool
        int dicePool = 0;

        // counters for number of hits, ones rolled, sixes rolled, and total dice rolled.
        int hits = 0;
        int ones = 0;
        int sixes = 0;
        int diceRolled = 0;

        // get dice pool
        try {

            dicePool = Integer.parseInt(shadowDPField.getText());
            diceRolled += dicePool;

        } catch (NumberFormatException nfe) {

            JOptionPane.showMessageDialog(main, shadowDPField.getText() + " is not a number!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);

        }

        // roll the dice
        int[] roll = spunkDice.rollD6(dicePool);

        String result = "";

        // parse dice rolls
        for (int index = 0; index < roll.length; index++) {

            // add value to string for display
            int rollValue = roll[index];
            result += rollValue;
            if (index < (roll.length - 1)) {
                result += ", ";
            }

            // count hits and ones (for glitches), and sixes (for Edge)
            if (rollValue == 5) {
                hits++;
            } else if (rollValue == 6) {
                hits++;
                sixes++;
            } else if (rollValue == 1) {
                ones++;
            }
        }

        // Re-rolling for Rule of Six
        if (shadowEdge) {
            while (sixes > 0) {

                // Add space for extra rolls
                result += ", ";
                diceRolled += sixes;
                int[] edgeRoll = spunkDice.rollD6(sixes);

                //  After sixes are re-rolled, reset the sixes count
                sixes = 0;

                // parse dice rolls
                for (int index = 0; index < edgeRoll.length; index++) {

                    // add value to string for display
                    int rollValue = edgeRoll[index];
                    result += rollValue;
                    if (index < (edgeRoll.length - 1)) {
                        result += ", ";
                    }

                    // count hits and ones (for glitches), and sixes (for Edge)
                    if (rollValue == 5) {
                        hits++;
                    } else if (rollValue == 6) {
                        hits++;
                        sixes++;
                    } else if (rollValue == 1) {
                        ones++;
                    }
                }
            }
        }

        result += ": " + hits;
        if (hits == 1) {
            result += " hit.";
        } else {
            result += " hits.";
        }

        // check for glitches
        if (ones >= (diceRolled / 2)) {
            // critical glitch?
            if (hits > 0) {
                result += " G!";
            } else {
                result += " CG!";
            }
        }

        // write to file if output box is checked
        if (shadowTextOut) {
            writeOutputToFile(result);
        }

        return result;
    }

    /**
     * Rolls Eclipse Phase system 2d10 for target percentage. Determines test
     * success or failure, as well as Excellent Success/Severe Failure.
     *
     * @return String Result of dice roll
     */
    private String eclipseRoll() {

        // initialize output string
        String result = "";

        // get target number from text area
        int targetNumber = 0;

        try {
            targetNumber = Integer.parseInt(eclipseTNField.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(main, eclipseTNField.getText() + " is not a number!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
        }

        // roll 2d10 and use Eclipse Phase system to determine result
        int[] rolls = spunkDice.rollD10(2);
        --rolls[0];
        --rolls[1];
        int rollResult = (rolls[0] * 10) + rolls[1];

        // push dice roll onto outupt string
        result += rolls[0] + ", " + rolls[1] + ": " + rollResult;

        // detect critical
        if (rolls[0] == rolls[1]) {
            result += " Critical!";
        }

        // determine success/fail and MoS/F
        if (rollResult > targetNumber) {
            if ((rollResult - targetNumber) > 30) {
                result += " Severe";
            }
            result += " Failure!";
        } else {
            if ((targetNumber - rollResult) > 30) {
                result += " Excellent";
            }
            result += " Success!";
        }

        // write to file if output box is checked
        if (eclipseTextOut) {
            writeOutputToFile(result);
        }
        // return output string
        return result;
    }

    /**
     * Displays an about box.
     */
    private void showAbout() {

        JOptionPane.showMessageDialog(main, ABOUT_MESSAGE, "About 5punk BeardyRoller", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Gets the beard image.
     */
    private void getBeard() {

        beardIcon = new ImageIcon(getClass().getResource("/img/beard.gif"));

    }

    /**
     * Writes the passed dice roll results to the output file
     *
     * @param outputString String containing dice roll to write to file
     */
    private void writeOutputToFile(String outputString) {

        File textOutFile = new File(OUTPUT_FILE);
        Date current = new Date();

        // make file if it isn't there
        if (!textOutFile.exists()) {
            try {
                textOutFile.createNewFile();
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(textOutFile));
                    bw.write(current.toString() + ": " + outputString);
                    bw.newLine();
                    bw.close();
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(main, "Couldn't write to the output file!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(main, "Couldn't make the output file!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(textOutFile, true));
                bw.write(current.toString() + ": " + outputString);
                bw.newLine();
                bw.close();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(main, "Couldn't write to the output file!", "ERRORZ!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Calls DiceRoller() constructor
     */
    public static void main(String[] argv) {

        DiceRoller diceRoller = new DiceRoller();

    }
}
