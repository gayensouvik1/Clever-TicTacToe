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

    public GameManager(Player player1, Player player2){
        gameCurrentState = new GameState();
        this.player1 = player1;
        this.player2 = player2;
        numberOfCellsOccupied = 0;
    }

    /*if game is on the return value is 0,
    * if human wins return value is -1,
    * if comp wins return value is 1
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

    public void humanTurn(int input){
        board = gameCurrentState.getBoard();
        board[input] = -1;
        gameCurrentState.increamentNumberOfCellOccupied();
    }

    public void computerTurn(){
        board = gameCurrentState.getBoard();
        int bestSpot = calculateBestMove();
        board[bestSpot] = 1;
        gameCurrentState.increamentNumberOfCellOccupied();
    }

    private int calculateBestMove() {

        int bestScore = -10000000;
        int index = 0;
        for(int i=1;i<=9;i++){
            if(board[i]==0){
                board[i] = 1;
                int score = findNextBestMove(false);
                board[i] = 0;
//                System.out.println(i+" = "+score);
                if(score > bestScore){
                    bestScore = score;
                    index = i;
                }
            }
        }
        return index;
    }

    private int findNextBestMove(boolean isMaximizersTurn) {

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
                    score = Math.min(score, findNextBestMove(true));
                    board[i] = 0;
                }
            }
            return score;
        }else{
            int score = -10000000;
            for(int i=1;i<=9;i++){

                if(board[i]==0){
                    board[i] = 1;
                    score = Math.max(score, findNextBestMove(false));
                    board[i] = 0;
                }
            }
            return score;
        }
    }

    public void printBoard(){
        for(int i=1;i<=9;i++){
            if(board[i]==1)
                System.out.print("x ");
            else if(board[i]==-1)
                System.out.print("o ");
            else
                System.out.print("_ ");
            if(i%3==0)
                System.out.println();
        }
    }
}
