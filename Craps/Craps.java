import java.util.*;
import java.io.*;
/*
 * Maverick Ho
 * Professor Bailey
 * Goal of the assignment is to play the game Craps
 * mmho@ucsc.edu
 */
public class Craps {
    public static void main(String [] args){
       Boolean win = true;
       Scanner scan = new Scanner(System.in);
       System.out.println("Enter the seed.");
       int seed = scan.nextInt();
       Random rng = new Random(seed);
       System.out.println("Enter in the starting pot.");
       int pot = scan.nextInt();
       System.out.println("Your pot size is " + pot);
       while (pot > 0){
	     int bet;
		 bet = getBet(scan,pot);
         if (winOrLose() == win){
           System.out.println("You win!");
           pot += bet;
           //adds money to the pot
           System.out.println("You now have " + pot);
		 }
         else{
              System.out.println("You lose!");
              pot -= bet;
              //loses money
              System.out.println("You now have " + pot);
		 }
	   }
    }
	private static int getBet(Scanner amount, int total){
		System.out.println("Enter in your bet");
		int bet = amount.nextInt();
		while (bet < 0 || bet > total){
            /*if bet is less than zero or
            more than the total*/
            System.out.println("not a valid bet");
            System.out.println("Enter in another bet");
            bet = amount.nextInt();
		}
        return bet;
	}
	public static int Dice(int n){
        return (int) (Math.random()*n);
	}
	public static int sumOfDice(){
		waitUser();
		int x = Dice(6) + 1;
		int y = Dice(6) + 1;
		int sum = x + y;
		System.out.println("The roll is " + x +","+ y);
		System.out.println("The point value is " + sum);
		return x + y;
	}
	private static void waitUser() {
	    Scanner inputScan = new Scanner(System.in);
	    System.out.print("Press enter to roll");
	    //press enter for the next roll
	    String input = inputScan.nextLine();
	}
    private static boolean winOrLose() {
    	int x = sumOfDice();   
        //return a win
    	if (x == 7 || x == 11) return true; 
        //doesn't return a win does nothing
        if (x == 2 || x == 3 || x == 12) return false; 
        while (true){
            //not one of the first throws win/lose conditions
            //if seven occurs before point returns false
            //if a same point results again I win.
            int y = sumOfDice();
            if (y == 7) return false; 
            if (y == x) return true;
        }
    }
}

