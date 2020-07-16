import models.Player;
import models.PlayerType;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by souvik.gayen on 16/07/20
 */
public class Main {

    public static void main(String args[]){

        Scanner scanner = new Scanner(System.in);
        Player computer = new Player("Computer", PlayerType.COMPUTER);
        System.out.println("Input your name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName, PlayerType.HUMAN);

        System.out.println("Choose Head(0) or Tails(1) : ");
        int toss = scanner.nextInt();
        int coin = flipAcoin();
        if(coin==0)
            System.out.println("It's a Head");
        else
            System.out.println("It's a Tail");
        printInitialBoard();
        GameManager gameManager = gameManager = new GameManager(computer,player);
        if(coin != toss)
            gameManager.computerTurn();

        while (gameManager.isGameFinished()==0 && gameManager.gameCurrentState.getNumberOfCellsOccupied()!=9){
            gameManager.printBoard();
            System.out.println("Choose a spot : ");
            int input = scanner.nextInt();
            gameManager.playTurn(input);
        }
        gameManager.printBoard();
    }

    public static void printInitialBoard(){
        System.out.println("Positions are numbered this way.");
        for(int i=1;i<=9;i++){
            System.out.print(i+" ");
            if(i%3==0)
                System.out.println();
        }
        System.out.println();
    }

    private static int flipAcoin() {
        return new Random().nextInt(2);
    }
}
