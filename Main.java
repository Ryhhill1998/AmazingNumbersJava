import java.util.Scanner;

public class Main {

    private static final String[] PROPERTIES = {"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "EVEN", "ODD"};

    public static void main(String[] args) {
        printWelcome();

        boolean quit = false;

        while (!quit) {
            printNewLine();
            String request = getRequest();

            try {
                quit = processRequest(request);
            } catch (NumberFormatException e) {
                System.out.println("Make sure that parameters 1 and 2 are natural numbers and " +
                        "any further parameters are strings.");
            }
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
        System.out.println("Supported requests:\n" +
                "\t- enter a natural number to know its properties;\n" +
                "\t- enter two natural numbers to obtain the properties of the list:\n" +
                "\t\t* the first parameter represents a starting number;\n" +
                "\t\t* the second parameters show how many consecutive numbers are to be processed;\n" +
                "\t- two natural numbers and a property to search for;\n" +
                "\t- separate the parameters with one space;\n" +
                "\t- enter 0 to exit.");
    }

    private static String getRequest() {
        System.out.print("Enter a request: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    private static boolean processRequest(String request) {
        boolean quit = false;

        if (request.isEmpty()) {
            printSupportedRequests();
        } else {
            String[] splitRequests = request.split(" ");
            int length = splitRequests.length;

            if (length == 1) {
                if (splitRequests[0].equals("0")) {
                    quit = true;
                } else {
                    processSingleRequest(splitRequests);
                }
            } else if (length == 2) {
                processRangeRequest(splitRequests);
            } else {
                processSearch(splitRequests);
            }
        }

        return quit;
    }

    private static void processSingleRequest(String[] request) {
        long number = Long.parseLong(request[0]);

        if (parametersAreValid(number)) {
            displayProperties(number);
        }
    }

    private static void processRangeRequest(String[] parameters) {
        long number = Long.parseLong(parameters[0]);
        int count = Integer.parseInt(parameters[1]);

        if (parametersAreValid(number, count)) {
            for (long i = number; i < number + count; i++) {
                displayPropertiesList(i);
            }
        }
    }

    private static void processSearch(String[] searchParameters) {
        long number = Long.parseLong(searchParameters[0]);
        int count = Integer.parseInt(searchParameters[1]);
        String property = searchParameters[2].toUpperCase();

        if (parametersAreValid(number, count, property)) {
            long[] foundNumbers = findNumbersWithProperty(number, count, property);

            for (int i = 0; i < foundNumbers.length; i++) {
                displayPropertiesList(foundNumbers[i]);
            }
        }
    }

    private static boolean parametersAreValid(long parameter) {
        boolean valid = true;

        if (!firstParameterIsValid(parameter)) {
            System.out.println("The first parameter should be a natural number or zero.");
            valid = false;
        }

        return valid;
    }

    private static boolean parametersAreValid(long parameter1, int parameter2) {
        boolean valid = false;

        if (!firstParameterIsValid(parameter1)) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else if (!secondParameterIsValid(parameter2)) {
            System.out.println("The second parameter should be a natural number.");
        } else {
            valid = true;
        }

        return valid;
    }

    private static boolean parametersAreValid(long parameter1, int parameter2, String parameter3) {
        boolean valid = false;

        if (!firstParameterIsValid(parameter1)) {
            System.out.println("The first parameter should be a natural number or zero.");
        } else if (!secondParameterIsValid(parameter2)) {
            System.out.println("The second parameter should be a natural number.");
        } else if (!thirdParameterIsValid(parameter3)) {
            System.out.println("The property [" + parameter3 + "] is wrong." +
                    "\nAvailable properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]");
        } else {
            valid = true;
        }

        return valid;
    }

    private static boolean firstParameterIsValid(long parameter) {
        return parameter >= 0;
    }

    private static boolean secondParameterIsValid(long parameter) {
        return parameter > 0;
    }

    private static boolean thirdParameterIsValid(String parameter) {
        boolean valid = false;

        for (int i = 0; i < PROPERTIES.length; i++) {
            if (PROPERTIES[i].equals(parameter)) {
                valid = true;
                break;
            }
        }

        return valid;
    }

    private static void displayProperties(long number) {
        int displacement = 12;
        System.out.printf("Properties of %,d\n", number);
        System.out.printf("%" + (displacement - "buzz".length()) + "sbuzz: %b\n", "", isBuzzNumber(number));
        System.out.printf("%" + (displacement - "duck".length()) + "sduck: %b\n", "", isDuckNumber(number));
        System.out.printf("%" + (displacement - "palindromic".length()) + "spalindromic: %b\n", "", isPalindrome(number));
        System.out.printf("%" + (displacement - "gapful".length()) + "sgapful: %b\n", "", isGapful(number));
        System.out.printf("%" + (displacement - "spy".length()) + "sspy: %b\n", "", isSpy(number));
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

        if (isSpy(number)) {
            addCommaIfRequired(description);
            description.append("spy");
        }

        addCommaIfRequired(description);

        if (isEven(number)) {
            description.append("even");
        } else {
            description.append("odd");
        }

        System.out.printf("%,16d is %s\n", number, description);
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

    private static long[] findNumbersWithProperty(long start, int count, String property) {
        return switch (property) {
            case "BUZZ" -> findBuzzNumbers(start, count);
            case "DUCK" -> findDuckNumbers(start, count);
            case "PALINDROMIC" -> findPalindromicNumbers(start, count);
            case "GAPFUL" -> findGapfulNumbers(start, count);
            case "SPY" -> findSpyNumbers(start, count);
            case "EVEN" -> findEvenNumbers(start, count);
            case "ODD" -> findOddNumbers(start, count);
            default -> null;
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
