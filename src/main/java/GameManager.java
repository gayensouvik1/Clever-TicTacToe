import models.GameState;
import models.Player;

/**
 * Created by souvik.gayen on 16/07/20
 */
public class GameManager {
    GameState gameCurrentState;
    int[] board;
    Player player1,player2;
    int numberOfCellsOccupied;
    int firstTurn;

    public GameManager(Player player1, Player player2){
        gameCurrentState = new GameState();
        this.player1 = player1;
        this.player2 = player2;
        numberOfCellsOccupied = 0;
        //assuming by default human plays the first turn. If not, the caller can toggle it.
        firstTurn = -1;
    }

    /*if game is not finished the return value is 0,
    * if human wins return value is -ve value,
    * if comp wins return value is +ve value,
    * */
    public int isGameFinished(){
        board = gameCurrentState.getBoard();
        return checkWon(1,1) + checkWon(4,1) + checkWon(7,1)
                + checkWon(1,3) + checkWon(2,3) + checkWon(3,3)
                + checkWon(1,4) + checkWon(3,2);
    }

    public int checkWon(int start, int commonDiff){
        if( board[start] == board[start+commonDiff] && board[start+commonDiff] == board[start+2*commonDiff])
            return board[start];
        return 0;
    }



    public void playTurn(int input) {
        board = gameCurrentState.getBoard();

        if(board[input]==0){
            humanTurn(input);

            if(gameCurrentState.getNumberOfCellsOccupied() == 9)
                System.out.println("Match tied");
            else if(isGameFinished()==0){
                computerTurn();
                if(isGameFinished()!=0){

                    System.out.println("Computer has won");
                }
                else if(gameCurrentState.getNumberOfCellsOccupied() == 9)
                    System.out.println("Match tied");
            }else{
                System.out.println("You have won");
            }

        }else{
            System.out.println("Invalid Turn, Try again");
        }
    }

    /* Function for playing human's turn.
     */

    public void humanTurn(int input){
        board = gameCurrentState.getBoard();
        board[input] = -1;
        gameCurrentState.increamentNumberOfCellOccupied();
    }

    /* Function for playing computer's turn.
     */
    public void computerTurn(){
        board = gameCurrentState.getBoard();
        int bestSpot = calculateBestMove();
        board[bestSpot] = 1;
        gameCurrentState.increamentNumberOfCellOccupied();
    }


    /* Calculates the best possible move depending on the current state.
    This is computed in each and every computer turn.

    PS: One alternative way is to precompute best move and store them against each state in a database/map.
    Drawback for this approach is, it will take a huge space if game board size increases.
     */
    private int calculateBestMove() {

        int bestScore = -10000000;
        int index = 0;
        for(int i=1;i<=9;i++){
            if(board[i]==0){
                board[i] = 1;
                int score = minmax(false);
                board[i] = 0;
                if(score > bestScore){
                    bestScore = score;
                    index = i;
                }
            }
        }
        return index;
    }

    private int minmax(boolean isMaximizersTurn) {

        if(isGameFinished()>0)
            return 1;
        else if(isGameFinished()<0)
            return -1;
        else{
            int count = 0;
            for(int i=1;i<=9;i++){
                if(board[i]!=0)
                    count++;
            }
            if(count == 9)
                return 0;
        }

        if(!isMaximizersTurn){
            int score = 10000000;
            for(int i=1;i<=9;i++){

                if(board[i]==0){
                    board[i] = -1;
                    score = Math.min(score, minmax(true));
                    board[i] = 0;
                }
            }
            return score;
        }else{
            int score = -10000000;
            for(int i=1;i<=9;i++){

                if(board[i]==0){
                    board[i] = 1;
                    score = Math.max(score, minmax(false));
                    board[i] = 0;
                }
            }
            return score;
        }
    }

    public void printBoard(){
        for(int i=1;i<=9;i++){
            if(board[i]==firstTurn)
                System.out.print("x ");
            else if(board[i]==-1*firstTurn)
                System.out.print("o ");
            else
                System.out.print("_ ");
            if(i%3==0)
                System.out.println();
        }
    }

    public void toggleFirstTurn(){
        firstTurn = 1;
    }
}
