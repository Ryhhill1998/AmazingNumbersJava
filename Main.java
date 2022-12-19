import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printWelcome();

        boolean quit = false;

        while (!quit) {
            printNewLine();
            long request = getRequest();
            quit = processRequest(request);
        }
    }

    private static void printNewLine() {
        System.out.println();
    }

    private static void printWelcome() {
        System.out.println("Welcome to Amazing Numbers!");
        printNewLine();
        printSupportedRequests();
    }

    private static void printSupportedRequests() {
        System.out.println("Supported requests:" +
                "\n\t- enter a natural number to know its properties" +
                "\n\t- enter 0 to exit");
    }

    private static long getRequest() {
        long request = -1;

        try {
            System.out.print("Enter a request: ");
            Scanner scanner = new Scanner(System.in);
            request = scanner.nextLong();
        } catch (InputMismatchException ignored) {
        }

        printNewLine();

        return request;
    }

    private static boolean processRequest(long request) {
        boolean quit = false;

        if (request == 0) {
            System.out.println("Goodbye!");
            quit = true;
        } else if (request < 0) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else {
            // display properties of number
            displayProperties(request);
        }

        return quit;
    }

    private static void displayProperties(long number) {
        System.out.printf("Properties of %,d\n", number);
        boolean numberIsEven = isEven(number);
        System.out.printf("%-8seven: %b\n", "", numberIsEven);
        System.out.printf("%-9sodd: %b\n", "", !numberIsEven);
        System.out.printf("%-8sbuzz: %b\n", "", isBuzzNumber(number));
        System.out.printf("%-8sduck: %b\n", "", isDuckNumber(number));
        System.out.printf("%-1spalindromic: %b\n", "", isPalindrome(number));
    }

    private static boolean isEven(long number) {
        return number % 2 == 0;
    }

    private static boolean isDivisibleBy7(long number) {
        boolean divisibleBy7 = false;

        long lastDigit = number % 10;
        long remainder = number / 10;
        remainder -= lastDigit * 2;

        if (remainder % 7 == 0) {
            divisibleBy7 = true;
        }

        return divisibleBy7;
    }

    private static boolean endsIn7(long number) {
        return number % 10 == 7;
    }

    private static boolean isBuzzNumber(long number) {
        return endsIn7(number) || isDivisibleBy7(number);
    }

    private static boolean isDuckNumber(long number) {
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

    private static boolean isPalindrome(long number) {
        String stringNumber = "" + number;
        boolean palindrome = true;

        int i = 0;
        int j = stringNumber.length() - 1;

        while (i < j) {
            if (stringNumber.charAt(i) != stringNumber.charAt(j)) {
                palindrome = false;
                break;
            }

            i++;
            j--;
        }

        return palindrome;
    }
}