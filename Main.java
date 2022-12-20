import java.util.ArrayList;
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

    private static String[] getSearchProperties(String[] searchParameters) {
        String[] properties = new String[searchParameters.length - 2];

        for (int i = 2; i < searchParameters.length; i++) {
            properties[i - 2] = searchParameters[i];
        }

        return properties;
    }

    private static void processSearch(String[] searchParameters) {
        long number = Long.parseLong(searchParameters[0]);
        int count = Integer.parseInt(searchParameters[1]);

        String[] properties = getSearchProperties(searchParameters);
        String property = searchParameters[2].toUpperCase();

        if (parametersAreValid(number, count, properties)) {
            ArrayList<Long> foundNumbers = findNumbersWithProperty(number, count, property);

            for (Long n : foundNumbers) {
                displayPropertiesList(n);
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
        boolean valid = true;

        if (!firstParameterIsValid(parameter1)) {
            System.out.println("The first parameter should be a natural number or zero.");
            valid = false;
        } else if (!secondParameterIsValid(parameter2)) {
            System.out.println("The second parameter should be a natural number.");
            valid = false;
        }

        return valid;
    }

    private static boolean parametersAreValid(long parameter1, int parameter2, String[] parameter3) {
        boolean valid = true;

        if (!firstParameterIsValid(parameter1)) {
            System.out.println("The first parameter should be a natural number or zero.");
            valid = false;
        } else if (!secondParameterIsValid(parameter2)) {
            System.out.println("The second parameter should be a natural number.");
            valid = false;
        } else {
            for (int i = 0; i < parameter3.length; i++) {
                String p = parameter3[i].toUpperCase();
                if (!thirdParameterIsValid(p)) {
                    System.out.println("The property [" + p + "] is wrong." +
                            "\nAvailable properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]");
                    valid = false;
                    break;
                }
            }
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

    private static boolean isPerfectSquare(long number) {
        long squareRoot = (long) Math.sqrt(number);
        return Math.pow(squareRoot, 2) == number;
    }

    private static boolean isSunny(long number) {
        return isPerfectSquare(number + 1);
    }

    private static ArrayList<Long> findNumbersWithProperties(long start, int count, String[] properties) {
        ArrayList<Long> foundNumbers = findNumbersWithProperty(start, count, properties[0]);

        for (int i = 1; i < properties.length; i++) {
            foundNumbers = filterNumbersByProperty(foundNumbers, properties[i]);
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findNumbersWithProperty(long start, int count, String property) {
        ArrayList<Long> foundNumbers;

        switch (property) {
            case "BUZZ":
                foundNumbers = findBuzzNumbers(start, count);
                break;
            case "DUCK":
                foundNumbers = findDuckNumbers(start, count);
                break;
            case "PALINDROMIC":
                foundNumbers = findPalindromicNumbers(start, count);
                break;
            case "GAPFUL":
                foundNumbers = findGapfulNumbers(start, count);
                break;
            case "SPY":
                foundNumbers = findSpyNumbers(start, count);
                break;
//            case "SQUARE":
//                foundNumbers = f(start, count);
//                break;
//            case "SUNNY":
//                foundNumbers = findBuzzNumbers(start, count);
//                break;
            case "EVEN":
                foundNumbers = findEvenNumbers(start, count);
                break;
            case "ODD":
                foundNumbers = findOddNumbers(start, count);
                break;
            default:
                foundNumbers = null;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> filterNumbersByProperty(ArrayList<Long> numbers, String property) {
        ArrayList<Long> filteredNumbers;

        switch (property) {
            case "BUZZ":
                filteredNumbers = filterBuzzNumbers(numbers);
                break;
            case "DUCK":
                filteredNumbers = filterDuckNumbers(numbers);
                break;
            case "PALINDROMIC":
                filteredNumbers = filterPalindromicNumbers(numbers);
                break;
            case "GAPFUL":
                filteredNumbers = filterGapfulNumbers(numbers);
                break;
            case "SPY":
                filteredNumbers = filterSpyNumbers(numbers);
                break;
//            case "SQUARE":
//                foundNumbers = f(start, count);
//                break;
//            case "SUNNY":
//                foundNumbers = findBuzzNumbers(start, count);
//                break;
            case "EVEN":
                filteredNumbers = filterEvenNumbers(numbers);
                break;
            case "ODD":
                filteredNumbers = filterOddNumbers(numbers);
                break;
            default:
                filteredNumbers = null;
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> findBuzzNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findDuckNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findPalindromicNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findGapfulNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findSpyNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findEvenNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> findOddNumbers(long start, long count) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (!isEven(start)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static ArrayList<Long> filterBuzzNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (isBuzzNumber(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> filterDuckNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (isDuckNumber(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> filterPalindromicNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (isPalindrome(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> filterGapfulNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (isGapful(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> filterSpyNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (isSpy(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> filterEvenNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (isEven(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }

    private static ArrayList<Long> filterOddNumbers(ArrayList<Long> numbers) {
        ArrayList<Long> filteredNumbers = new ArrayList<>();

        for (long num : numbers) {
            if (!isEven(num)) {
                filteredNumbers.add(num);
            }
        }

        return filteredNumbers;
    }
}
