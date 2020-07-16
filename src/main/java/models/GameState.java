package models;

import java.util.Arrays;

/**
 * Created by souvik.gayen on 16/07/20
 */
public class GameState {

    int[] board;
    int numberOfCellsOccupied;

    public int getNumberOfCellsOccupied() {
        return numberOfCellsOccupied;
    }
    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public void increamentNumberOfCellOccupied(){
        numberOfCellsOccupied++;
    }

    public GameState(){
        board = new int[10];
        Arrays.fill(board,0);
        numberOfCellsOccupied = 0;
    }
}
