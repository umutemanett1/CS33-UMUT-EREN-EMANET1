/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author umut.eren.emanet
 */
public class Connect4 {

    /*
             * @param args the command line arguments
             */
    public static final int BWIDTH = 50;
    static final int BHEIGHT = 50;
    static final int LEFTOFFSET = 200;
    static final int TOPOFFSET = 60;
    static final int INDENT = 4;
    static final int MAXROW = 6;
    static final int MAXCOL = 7;
    static final char PLAYER= 'P';
    static final char COMPUTER= 'C';
    private static GameButton[][] myButtons;
    static JTextField rightColChoice;
    static JButton rightEnter, cleanButton;
    static JLabel rightLabel,
            moveRemainedLabel;
    static JFrame mainFrame;
    static int lastCol = -1, lastRow = -1;
    static int randomSel, count = 0, moveRemained = MAXROW * MAXCOL;

//Get column string value which is selected by player 
// used for winner search in column.
    public static String vertical() {
        String s = "";
        for (int h = 0; h < MAXROW; h++) {
            s += myButtons[h][lastCol].getState();
        }
        return s;
    }
//Get row string value which is selected by player
// used for winner search in row

    public static String horizontal() {
        String s = "";
        for (int i = 0; i < MAXCOL; i++) {
            s += myButtons[lastRow][i].getState();
        }
        return s;
    }
//Left Diagonal Fill by players
// used for winner search left diagonal

    public static String leftDiagonal() {
        String s = "";
        for (int h = 0; h < MAXROW; h++) {
            int w = lastCol + lastRow - h;
            if (w >= 0 && w < MAXCOL) {
                s += myButtons[h][w].getState();
            }
        }
        return s;
    }
//Right diagonal fill by players
// used for winner search in right diagonal

    public static String rightDiagonal() {
        String s = "";
        for (int h = 0; h < MAXROW; h++) {
            int w = lastCol - lastRow + h;

            if (w >= 0 && w < MAXCOL) {
                s += myButtons[h][w].getState();
            }
        }
        return s;
    }
    
     public static void computerMove() {
        int testInd = 0;
        for (int i = 0; i < MAXCOL; i++) {
            testInd = (lastCol + i) % 6;
            System.out.println(" Row " + (lastRow) + " Col : " + testInd);
            if (lastRow > 0 && myButtons[lastRow - 1][testInd].getState() == 'E') {
                System.out.println(" Row " + (lastRow - 1) + " Col : " + testInd);
                break;
            } else {
                System.out.println(" Row " + (lastRow - 1) + " Col : " + testInd + " Val " + myButtons[lastRow + 1][testInd].getState());
            }
        }
        insertChoice(COMPUTER, testInd);
        System.out.println("Inserted column is " + testInd);
    }
// inserts players disk into valid column. If column is full returns error.

    public static int insertChoice(char leftRight, int col) {
        String str = "";
        if (!(0 <= col && col < MAXCOL)) {
            JOptionPane.showMessageDialog(mainFrame, "Column must be between 0 and " + (MAXCOL - 1));
            return 0;
        }
        for (int h = MAXROW - 1; h >= 0; h--) {
            if (myButtons[h][col].getState() == 'E') {
                lastRow = h;
                lastCol = col;
                myButtons[lastRow][lastCol].setState(leftRight);
                myButtons[lastRow][lastCol].setText(str + leftRight);
                if (leftRight == COMPUTER) {
                    myButtons[lastRow][lastCol].setBackground(Color.red);
                } else if (leftRight == PLAYER) {
                    myButtons[lastRow][lastCol].setBackground(Color.blue);
                }
                return 1;
            }
        }
        return 0;
    }
//Checks that one of the player wins the game or not after each disk insertion.

    public static boolean gameIsOver() {
        if (lastCol == -1) {
            JOptionPane.showMessageDialog(mainFrame, "No move has been made yet");
            return false;
        }

        char tLeftRight = myButtons[lastRow][lastCol].getState();
        // winning streak with the last play symbol
        String winnerStr = String.format("%c%c%c%c", tLeftRight, tLeftRight, tLeftRight, tLeftRight);

        // check if there is 4 disk with same column next to each other started from last inserted column 
        // and inserted row. 
        // In predefined four patterns.
        if ((horizontal().contains(winnerStr))
                || (vertical().contains(winnerStr))
                || (leftDiagonal().contains(winnerStr))
                || (rightDiagonal().contains(winnerStr))) {
            return true;
        } else {
            return false;
        }
    }
//Puts the GUI starting settings.

    public static void initGame() {
        for (int i = 0; i < MAXROW; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                myButtons[i][j].setText("");
                myButtons[i][j].setState('E');
                myButtons[i][j].setBackground(Color.gray);
            }
        }
        count = 0;
        lastCol = lastRow = -1;
        moveRemained = 42;
        //leftColChoice.setText("");
        rightColChoice.setText("");
        updateRemainedMoveLabel(moveRemained);
    }

    public static void updateRemainedMoveLabel(int move) {
        String msgStr = "Remaining move " + move;
        moveRemainedLabel.setText(msgStr);
    }

    public static void main(String[] args) {
        JPanel backPanel;

        mainFrame = new JFrame("Game Panel");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(820, 600));
        backPanel = new JPanel();
        count = 0;
        myButtons = new GameButton[MAXROW][MAXCOL];
        for (int i = 0; i < MAXROW; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                myButtons[i][j] = new GameButton(i, j, 'E');
                (myButtons[i][j]).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameButton tButton = (GameButton) e.getSource();
                    }
                });
            }
        }
        rightLabel = new JLabel("PLAYER");
        rightLabel.setBounds(680, 70, 80, 32);
        rightColChoice = new JTextField();
        rightColChoice.setBounds(650, 100, 100, 32);
        rightEnter = new JButton("ENTER");
        rightEnter.setBounds(660, 150, 80, 32);
        rightEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    int k = Integer.parseInt(rightColChoice.getText());
                    if (k >= 0 && k <= MAXCOL) {
                        if (insertChoice(PLAYER, k) <= 0) {
                            JOptionPane.showMessageDialog(mainFrame, "Invalid input or column is full");
                            return;
                        }
                        if (gameIsOver()) {
                            JOptionPane.showMessageDialog(mainFrame, "Game is won by Right Player");
                        }
                        count++;
                        moveRemained--;
                        updateRemainedMoveLabel(moveRemained);
                        if (count >= MAXCOL * MAXROW) {
                            JOptionPane.showMessageDialog(mainFrame, "No Winner for the game");
                        }
                        //computerMove();
                        //Thread.sleep(1000);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                computerMove();
                                if (gameIsOver()) {
                                    JOptionPane.showMessageDialog(mainFrame, "Game is won by Computer");
                                }
                                moveRemained--;
                                updateRemainedMoveLabel(moveRemained);
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Enter valid value  0 to 5");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(mainFrame, "Invalıd input: " + rightColChoice.getText());
                }
            }
        });
        moveRemainedLabel = new JLabel();
        moveRemainedLabel.setBounds(340, 420, 180, 32);
        //This button erases current board and sets the GUI to initaial state
        cleanButton = new JButton("CLEAR");
        cleanButton.setBounds(354, 480, 80, 32);
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                initGame();
            }
        });
        //GUI is fixed all objects are placed relative to left upper corner
        backPanel.setLayout(null);
        //Draw the disks as button. Set as empty cell. Button holds player label L or R 
        //Empty cell has label E Right player label R Left Player Label L.
        for (int i = 0; i < MAXROW; i++) {
            for (int j = 0; j < MAXCOL; j++) {
                myButtons[i][j].setBounds(LEFTOFFSET + j * (BHEIGHT + INDENT), TOPOFFSET + i * (BWIDTH + INDENT), BWIDTH, BHEIGHT);
                char b = myButtons[i][j].getState();
                String str = "" + b;
                myButtons[i][j].setOpaque(true);
           
                if (b == 'E') {
                    myButtons[i][j].setBackground(Color.gray);
                    
                } else if (b == COMPUTER) {
                    myButtons[i][j].setText(str);
                    myButtons[i][j].setBackground(Color.blue);
                } else if (b == PLAYER) {
                    myButtons[i][j].setText(str);
                    myButtons[i][j].setBackground(Color.red);
                }
                backPanel.add(myButtons[i][j]);
            }
        }
        backPanel.add(rightLabel);
        backPanel.add(rightColChoice);
        backPanel.add(rightEnter);
        backPanel.add(moveRemainedLabel);
        backPanel.add(cleanButton);
        mainFrame.getContentPane().add(backPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        updateRemainedMoveLabel(moveRemained);
    }
}
