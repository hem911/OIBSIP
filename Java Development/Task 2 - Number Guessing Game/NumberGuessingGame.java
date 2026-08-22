
import java.util.*;

public class NumberGuessingGame {

    public static void main(String ar[]) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
		int score=0;
		int rounds=0;
        while (true) {
			System.out.println("Select Level you want to play\n1.Easy\n2.Medium\n3.Hard");
			int level=sc.nextInt();
			int maxattempts=0;

			int x=0;
			if(level==1){
				System.out.println("You have 10 attempts to guess the number between 1 and 50");
				maxattempts=10;
				x = r.nextInt(50) + 1;
			}
			else if(level==2){
				System.out.println("You have 7 attempts to guess the number between 1 and 100");
				maxattempts=7;
				x = r.nextInt(100) + 1;
			}
			else{
				System.out.println("You have 5 attempts to guess the number between 1 and 200");
				maxattempts=5;
				x = r.nextInt(200) + 1;
			}
            rounds++;
            //System.out.print(x);
            int lastguess = 0;
            int attempts = 0;
            while (attempts < maxattempts) {
                System.out.println("enter your guess");
                int guess = sc.nextInt();
                lastguess = guess;
                attempts++;
                if (guess > x) {
                    System.out.println("Attempt:" + attempts);
                    System.out.println("Your guess is high");
                    //attempts++;
                } else if (guess < x) {
                    System.out.println("Attempt:" + attempts);
                    System.out.println("Your guess is low");
                    //attempts++;
                } else {
                    System.out.println("Attempt:" + attempts);
                    System.out.println("You guessed it right at Attempts:" + attempts);
                    System.out.println();
					score++;
                    System.out.println("Round "+rounds+" guessed in "+attempts+" attempts ");
                    System.out.println();
                    break;
                }

            }
            if (attempts == maxattempts && lastguess != x) {
                System.out.println("Round "+rounds+" lost after "+attempts+" attempts ");
                System.out.println("Attempts exceeded, the number was:" + x);
                System.out.println();
            }
            System.out.println("Do you want to play again");
            System.out.println("1.Yes");
            System.out.println("2.No");
            int choice = sc.nextInt();
            if (choice == 2) {
                break;
            }
        }
		System.out.println("Rounds Played:"+rounds);
		System.out.println("Score:"+score);

    }
}
