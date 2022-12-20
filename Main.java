import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printWelcome();

        boolean quit = false;

        while (!quit) {
            printNewLine();
            quit = getRequest();
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
                "\n\t- enter two natural numbers to obtain the properties of the list" +
                "\n\t\t* the first parameter represents a starting number" +
                "\n\t\t* the second parameter shows how many consecutive numbers are to be printed" +
                "\n\t- separate the parameters with one space" +
                "\n\t- enter 0 to exit.");
    }

    private static boolean getRequest() {
        boolean quit = false;

        System.out.print("Enter a request: ");
        Scanner scanner = new Scanner(System.in);
        String request = scanner.nextLine().trim();
        printNewLine();

        if (request.isEmpty()) {
            printSupportedRequests();
        } else {
            try {
                long[] parsedRequests = parseRequest(request);

                if (parsedRequests.length == 2) {
                    quit = processRequest(parsedRequests[0], parsedRequests[1]);
                } else if (parsedRequests.length == 1) {
                    quit = processRequest(parsedRequests[0], 0);
                } else {
                    System.out.println("Please do not enter more than two values!");
                }
            } catch (NumberFormatException e) {
                System.out.println("please only enter numerical values!");
            }
        }

        return quit;
    }

    private static long[] parseRequest(String request) {
        String[] splitRequests = request.split(" ");
        long[] parsedRequests = new long[splitRequests.length];

        for (int i = 0; i < parsedRequests.length; i++) {
            parsedRequests[i] = Long.parseLong(splitRequests[i]);
        }

        return parsedRequests;
    }

    private static boolean processRequest(long request, int type) {
        boolean quit = false;

        if (!firstParameterIsValid(request)) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else if (request == 0) {
            System.out.println("Goodbye!");
            quit = true;
        } else {
            // display properties of number
            if (type == 0) {
                displayProperties(request);
            } else {
                displayPropertiesList(request);
            }
        }

        return quit;
    }

    private static boolean firstParameterIsValid(long parameter) {
        return parameter >= 0;
    }

    private static boolean secondParameterIsValid(long parameter) {
        return parameter > 0;
    }

    private static boolean processRequest(long request, long range) {
        boolean quit = false;

        if (!firstParameterIsValid(request)) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else if (!secondParameterIsValid(range)) {
            System.out.println("The second parameter should be a natural number.");
        } else {
            request--;

            for (int i = 0; i < range; i++) {
                quit = processRequest(++request, 1);
            }
        }

        return quit;
    }

    private static void displayProperties(long number) {
        int displacement = 12;
        System.out.printf("Properties of %,d\n", number);
        System.out.printf("%" + (displacement - "buzz".length()) + "sbuzz: %b\n", "", isBuzzNumber(number));
        System.out.printf("%" + (displacement - "duck".length()) + "sduck: %b\n", "", isDuckNumber(number));
        System.out.printf("%" + (displacement - "palindromic".length()) + "spalindromic: %b\n", "", isPalindrome(number));
        System.out.printf("%" + (displacement - "gapful".length()) + "sgapful: %b\n", "", isGapful(number));
        boolean numberIsEven = isEven(number);
        System.out.printf("%" + (displacement - "even".length()) + "seven: %b\n", "", numberIsEven);
        System.out.printf("%" + (displacement - "odd".length()) + "sodd: %b\n", "", !numberIsEven);
    }

    private static void displayPropertiesList(long number) {
        StringBuilder description = new StringBuilder();

        if (isBuzzNumber(number)) {
            description.append("buzz");
        }

        if (isDuckNumber(number)) {
            addCommaIfRequired(description);
            description.append("duck");
        }

        if (isPalindrome(number)) {
            addCommaIfRequired(description);
            description.append("palindrome");
        }

        if (isGapful(number)) {
            addCommaIfRequired(description);
            description.append("gapful");
        }

        addCommaIfRequired(description);

        if (isEven(number)) {
            description.append("even");
        } else {
            description.append("odd");
        }

        System.out.printf("%16d is %s\n", number, description);
    }

    private static void addCommaIfRequired(StringBuilder stringBuilder) {
        if (!stringBuilder.isEmpty()) {
            stringBuilder.append(", ");
        }
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

    private static boolean isGapful(long number) {
        if ((number + "").length() < 3) {
            return false;
        }

        boolean gapful = false;

        long firstDigit = number;
        long lastDigit = number % 10;

        while (firstDigit >= 10) {
            firstDigit /= 10;
        }

        long concatenation = Long.parseLong("" + firstDigit + lastDigit);

        if (number % concatenation == 0) {
            gapful = true;
        }

        return gapful;
    }

    private static boolean isSpy(long number) {
        boolean spy = false;

        long digitSum = 0;
        long digitProduct = 1;

        while (number > 0) {
            long lastDigit = number % 10;
            digitSum += lastDigit;
            digitProduct *= lastDigit;
            number /= 10;
        }

        if (digitSum == digitProduct) {
            spy = true;
        }

        return spy;
    }

    private static long[] findNumbersWithProperty(long start, long count, String property) {
        return switch (property) {
            case "buzz" -> findBuzzNumbers(start, count);
            case "duck" -> findDuckNumbers(start, count);
            case "palindromic" -> findPalindromicNumbers(start, count);
            case "gapful" -> findGapfulNumbers(start, count);
            case "spy" -> findSpyNumbers(start, count);
            case "even" -> findEvenNumbers(start, count);
            case "odd" -> findOddNumbers(start, count);
            default -> new long[(int) count];
        };
    }

    private static long[] findBuzzNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (isBuzzNumber(start)) {
                numbers[i] = start;
                count--;
                i++;
            }
            start++;
        }

        return numbers;
    }

    private static long[] findDuckNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (isDuckNumber(start)) {
                numbers[i] = start;
                count--;
                i++;
            }
            start++;
        }

        return numbers;
    }

    private static long[] findPalindromicNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (isPalindrome(start)) {
                numbers[i] = start;
                count--;
                i++;
            }
            start++;
        }

        return numbers;
    }

    private static long[] findGapfulNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (isGapful(start)) {
                numbers[i] = start;
                count--;
                i++;
            }
            start++;
        }

        return numbers;
    }

    private static long[] findSpyNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (isSpy(start)) {
                numbers[i] = start;
                count--;
                i++;
            }

            start++;
        }

        return numbers;
    }

    private static long[] findEvenNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (isEven(start)) {
                numbers[i] = start;
                count--;
                i++;
            }
            start++;
        }

        return numbers;
    }

    private static long[] findOddNumbers(long start, long count) {
        long[] numbers = new long[(int) count];
        int i = 0;

        while (count > 0) {
            if (!isEven(start)) {
                numbers[i] = start;
                count--;
                i++;
            }
            start++;
        }

        return numbers;
    }
}
