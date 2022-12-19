import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter a natural number (positive integer): ");

        try {
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            if (number <= 0) {
                System.out.println("This number is not natural!");
            } else {
                String parity = number % 2 == 0 ? "Even" : "Odd";
                System.out.printf("This number is %s.\n", parity);
                checkBuzzNumber(number);
            }
        } catch (InputMismatchException e) {
            System.out.println("Not a number!");
        }
    }

    private static boolean isDivisibleBy7(int number) {
        boolean divisibleBy7 = false;

        int lastDigit = number % 10;
        int remainder = number / 10;
        remainder -= lastDigit * 2;

        if (remainder % 7 == 0) {
            divisibleBy7 = true;
        }

        return divisibleBy7;
    }

    private static void checkBuzzNumber(int number) {
        boolean endsIn7 = number % 10 == 7;
        boolean divisibleBy7 = isDivisibleBy7(number);

        System.out.println("It is" + (endsIn7 || divisibleBy7 ? "" : " not") + " a Buzz number.");
        System.out.println("Explanation:");

        if (endsIn7 && divisibleBy7) {
            System.out.println(number + " is divisible by 7 and ends with 7.");
        } else if (endsIn7) {
            System.out.println(number + " ends with 7.");
        } else if (divisibleBy7) {
            System.out.println(number + " is divisible by 7.");
        } else {
            System.out.println(number + " is neither divisible by 7 nor does it end with 7");
        }
    }
}