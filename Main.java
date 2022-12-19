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
                System.out.println("Properties of " + number);
                boolean numberIsEven = isEven(number);
                System.out.printf("%-8seven: %b\n", "", numberIsEven);
                System.out.printf("%-9sodd: %b\n", "", !numberIsEven);
                System.out.printf("%-8sbuzz: %b\n", "", isBuzzNumber(number));
                System.out.printf("%-8sduck: %b\n", "", isDuckNumber(number));
            }
        } catch (InputMismatchException e) {
            System.out.println("Not a number!");
        }
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
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

    private static boolean endsIn7(int number) {
        return number % 10 == 7;
    }

    private static boolean isBuzzNumber(int number) {
        return endsIn7(number) || isDivisibleBy7(number);
    }

    private static boolean isDuckNumber(int number) {
        boolean duckNumber = false;
        String stringNumber = "" + number;

        for (int i = 0; i < stringNumber.length(); i++) {
            if (stringNumber.charAt(i) == '0') {
                duckNumber = true;
                break;
            }
        }

        return duckNumber;
    }
}