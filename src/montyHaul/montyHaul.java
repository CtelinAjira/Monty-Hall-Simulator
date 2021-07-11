package montyHaul;

import java.util.Random;
import java.lang.String;

public class montyHaul {
	private static Random rand = new Random();
	private static int numDoors = 3; // Monty Haul worked with a 3-door game
	private static int STAY = 0;
	private static int SWAP = 1;
	private static int COIN = 2;
	
	private static int coinFlip() {
		/*
		 * 0 = stay, 1 = swap
		 */
		return rand.nextInt(2);
	}
	
	private static int whereIsCar() {
		/*
		 * The car is behind this door
		 */
		int doors = rand.nextInt(numDoors);
		return doors;
	}
	
	private static int openGoat(int guess, int car) {
		/*
		 * Monty Haul opens a goat door
		 */
		int goat = 0;
		for(int i = 0; i < numDoors; i++) {
			goat = i;
			if ((goat != car) && (goat != guess)) {
				break;
			}
		}
		return goat;
	}
	
	private static int stay(int guess) {
		/*
		 * stay on your original door (really doesn't need its own function, but still...)
		 */
		return guess;
	}
	
	private static int swap(int guess, int goat) {
		/*
		 * switch to the door that hasn't been touched yet by either you or Monty Haul
		 */
		int choice = 0;
		for(int i = 0; i < numDoors; i++) {
			if(i != guess && i != goat) {
				choice = i;
			}
		}
		return choice;
	}
	
	private static int stayOrSwap(int guess, int goat, int method, int coin) {
		/*
		 * which method are you using?
		 * 0 = Stay
		 * 1 = Switch
		 * 2 = Coin Flip
		 */
		if(method == 0) {
			return stay(guess);
		} else if(method == 1) {
			return swap(guess, goat);
		} else {
			if (coin == 0) {
				return stay(guess);
			} else {
				return swap(guess, goat);
			}
		}
	}
	
	private static String getMethodString(int method, int coin) {
		String decision = "";
		if(method == STAY) {
			decision += "Stay";
		} 
		if (method == SWAP) {
			decision += "Switch";
		}
		if (method == COIN) {
			decision += "CoinFlip";
			if(coin == 0) {
				decision += "(Stay)";
			} else {
				decision += "(Switch)";
			}
		}
		return decision;
	}
	
	private static int MontyHaul(int method) {
		/*
		 * Let's Make A Deal!
		 */
		int car = whereIsCar(); // what number is the car behind?
		int guess = rand.nextInt(numDoors); // what number did you guess?
		int goat = openGoat(guess, car); // what number is the known goat behind?
		int coinFlip = coinFlip(); // relevant for the coin flip method
		
		System.out.print("Door #");
		System.out.print(guess+1); // outputting number + 1 for those who don't think in Java array logic
		System.out.print(",");
		System.out.print("Door #");
		System.out.print(goat+1); // outputting number + 1 for those who don't think in Java array logic
		System.out.print(",");
		System.out.print(getMethodString(method, coinFlip));
		System.out.print(",");
		guess = stayOrSwap(guess, goat, method, coinFlip);
		if (car == guess) {
			// You won a brand new car!!
			System.out.println("Car,");
			return 1;
		} else {
			// it's a goat
			System.out.println("Goat,");
			return 0;
		}
	}
	
	private static void RepeatTrials(int method, int repetitions) {
		/*
		 * let's play this multiple times!
		 */
		double wins = 0.0;
		System.out.println("Initial Guess,Known Goat,Method,Prize,");
		for(int i = 0; i < repetitions; i++) {
			wins += MontyHaul(method);
		}
		System.out.println("");
		
		System.out.print("Cars Won per Game: ");
		System.out.println(wins/repetitions);
	}
	
	public static void main(String[] args) {
		int doorMethod = rand.nextInt(3); // 0 = stay, 1 = swap, 2 = coin flip
		int iter = 1000; // number of trials
		
		RepeatTrials(doorMethod, iter);
	}
}
