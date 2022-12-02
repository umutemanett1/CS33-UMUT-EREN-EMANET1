/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import javax.swing.JButton;

/**
 *
 * @author umut.eren.emanet
 */
public class GameButton extends JButton{
    public 
        int rowInd, colInd;
        char state;
    GameButton(int rowInd, int colInd, char state)
    {
        this.rowInd = colInd;
        this.colInd = rowInd;
        this.state = state;
    }
    public int getRowInd()
    {
        return rowInd;
    }
    public int getColInd()
    {
        return colInd;
    }
    
    public char getState()
    {
        return state;
    }
    public void setState(char state)
    {
        this.state=state;
    }

}
