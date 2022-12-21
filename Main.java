import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private static final String[] PROPERTIES = {
            "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE",
            "SUNNY", "HAPPY", "SAD", "JUMPING", "EVEN", "ODD",
            "-BUZZ", "-DUCK", "-PALINDROMIC", "-GAPFUL", "-SPY", "-SQUARE",
            "-SUNNY", "-HAPPY", "-SAD", "-JUMPING", "-EVEN", "-ODD"
    };

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
                "\t- two natural numbers and two properties to search for;\n" +
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

    private static ArrayList<String> getSearchProperties(String[] searchParameters) {
        ArrayList<String> properties = new ArrayList<>();

        for (int i = 2; i < searchParameters.length; i++) {
            properties.add(searchParameters[i].toUpperCase());
        }

        return properties;
    }

    private static void processSearch(String[] searchParameters) {
        long number = Long.parseLong(searchParameters[0]);
        int count = Integer.parseInt(searchParameters[1]);

        ArrayList<String> properties = getSearchProperties(searchParameters);

        if (parametersAreValid(number, count, properties) && !searchHasMutuallyExclusiveParameters(properties)) {
            ArrayList<Long> foundNumbers = findNumbersWithProperties(number, count, properties);

            for (Long n : foundNumbers) {
                displayPropertiesList(n);
            }
        }
    }

    private static boolean searchContainsEvenAndOdd(ArrayList<String> parameters) {
        return parameters.contains("EVEN") && parameters.contains("ODD");
    }

    private static boolean searchContainsNotEvenAndNotOdd(ArrayList<String> parameters) {
        return parameters.contains("-EVEN") && parameters.contains("-ODD");
    }

    private static boolean searchContainsDuckAndSpy(ArrayList<String> parameters) {
        return parameters.contains("DUCK") && parameters.contains("SPY");
    }

    private static boolean searchContainsNotDuckAndNotSpy(ArrayList<String> parameters) {
        return parameters.contains("-DUCK") && parameters.contains("-SPY");
    }

    private static boolean searchContainsSquareAndSunny(ArrayList<String> parameters) {
        return parameters.contains("SUNNY") && parameters.contains("SQUARE");
    }

    private static boolean searchContainsNotSquareAndNotSunny(ArrayList<String> parameters) {
        return parameters.contains("-SUNNY") && parameters.contains("-SQUARE");
    }

    private static boolean searchHasMutuallyExclusiveParameters(ArrayList<String> parameters) {
        boolean hasMutuallyExclusiveParameters = false;

        if (searchContainsEvenAndOdd(parameters)) {
            System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]");
            System.out.println("There are no numbers with these properties.");
            hasMutuallyExclusiveParameters = true;
        } else if (searchContainsNotEvenAndNotOdd(parameters)) {
            System.out.println("The request contains mutually exclusive properties: [-EVEN, -ODD]");
            System.out.println("There are no numbers with these properties.");
            hasMutuallyExclusiveParameters = true;
        } else if (searchContainsDuckAndSpy(parameters)) {
            System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
            System.out.println("There are no numbers with these properties.");
            hasMutuallyExclusiveParameters = true;
        } else if (searchContainsNotDuckAndNotSpy(parameters)) {
            System.out.println("The request contains mutually exclusive properties: [-DUCK, -SPY]");
            System.out.println("There are no numbers with these properties.");
            hasMutuallyExclusiveParameters = true;
        } else if (searchContainsSquareAndSunny(parameters)) {
            System.out.println("The request contains mutually exclusive properties: [SQUARE, SUNNY]");
            System.out.println("There are no numbers with these properties.");
            hasMutuallyExclusiveParameters = true;
        } else if (searchContainsNotSquareAndNotSunny(parameters)) {
            System.out.println("The request contains mutually exclusive properties: [-SQUARE, -SUNNY]");
            System.out.println("There are no numbers with these properties.");
            hasMutuallyExclusiveParameters = true;
        }

        return hasMutuallyExclusiveParameters;
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

    private static boolean parametersAreValid(long parameter1, int parameter2, ArrayList<String> parameter3) {
        boolean valid = true;

        if (!firstParameterIsValid(parameter1)) {
            System.out.println("The first parameter should be a natural number or zero.");
            valid = false;
        } else if (!secondParameterIsValid(parameter2)) {
            System.out.println("The second parameter should be a natural number.");
            valid = false;
        } else {
            ArrayList<String> invalidParameters = new ArrayList<>();
            for (int i = 0; i < parameter3.size(); i++) {
                String p = parameter3.get(i);
                if (!thirdParameterIsValid(p)) {
                    invalidParameters.add(p);
                    valid = false;
                }
            }
            if (!valid) {
                StringBuilder feedback = new StringBuilder("The propert");
                feedback.append(invalidParameters.size() == 1 ? "y" : "ies")
                        .append(" [").append(invalidParameters.get(0));

                for (int i = 1; i < invalidParameters.size(); i++) {
                    feedback.append(", ").append(invalidParameters.get(i));
                }

                feedback.append("] ").append(invalidParameters.size() == 1 ? "is" : "are").append(" wrong.");

                System.out.println(feedback +
                        "\nAvailable properties: " + Arrays.toString(PROPERTIES));
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
        System.out.printf("Properties of %,d\n", number);
        System.out.printf("%" + getDisplacement("buzz") + "sbuzz: %b\n", "", isBuzzNumber(number));
        System.out.printf("%" + getDisplacement("duck") + "sduck: %b\n", "", isDuckNumber(number));
        System.out.printf("%" + getDisplacement("palindromic") + "spalindromic: %b\n", "", isPalindrome(number));
        System.out.printf("%" + getDisplacement("gapful") + "sgapful: %b\n", "", isGapful(number));
        System.out.printf("%" + getDisplacement("spy") + "sspy: %b\n", "", isSpy(number));
        System.out.printf("%" + getDisplacement("square") + "ssquare: %b\n", "", isPerfectSquare(number));
        System.out.printf("%" + getDisplacement("sunny") + "ssunny: %b\n", "", isSunny(number));
        System.out.printf("%" + getDisplacement("jumping") + "sjumping: %b\n", "", isJumping(number));
        System.out.printf("%" + getDisplacement("happy") + "shappy: %b\n", "", isHappy(number));
        System.out.printf("%" + getDisplacement("sad") + "ssad: %b\n", "", isSad(number));
        System.out.printf("%" + getDisplacement("even") + "seven: %b\n", "", isEven(number));
        System.out.printf("%" + getDisplacement("odd") + "sodd: %b\n", "", isOdd(number));
    }

    private static int getDisplacement(String property) {
        return 12 - property.length();
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

        if (isPerfectSquare(number)) {
            addCommaIfRequired(description);
            description.append("square");
        }

        if (isSunny(number)) {
            addCommaIfRequired(description);
            description.append("sunny");
        }

        if (isJumping(number)) {
            addCommaIfRequired(description);
            description.append("jumping");
        }

        if (isHappy(number)) {
            addCommaIfRequired(description);
            description.append("happy");
        }

        if (isSad(number)) {
            addCommaIfRequired(description);
            description.append("sad");
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

    private static boolean isOdd(long number) {
        return !isEven(number);
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

    private static boolean isJumping(long number) {
        boolean jumping = true;
        long digit1;
        long digit2;

        while (number / 10 > 0) {
            digit1 = number % 10;
            number /= 10;
            digit2 = number % 10;

            if (Math.abs(digit1 - digit2) != 1) {
                jumping = false;
                break;
            }
        }

        return jumping;
    }

    private static boolean isHappy(long number) {
        boolean happy = false;
        HashSet<Long> numbersProcessed = new HashSet<>();
        long digitSum, digit;

        while (true) {
            if (number == 1) {
                happy = true;
                break;
            }

            if (numbersProcessed.contains(number)) {
                break;
            } else {
                numbersProcessed.add(number);
            }

            digitSum = 0;

            while (number > 0) {
                digit = number % 10;
                digitSum += Math.pow(digit, 2);
                number /= 10;
            }

            number = digitSum;
        }

        return happy;
    }

    private static boolean isSad(long number) {
        return !isHappy(number);
    }

    private static ArrayList<Long> findNumbersWithProperties(long start, int count, ArrayList<String> properties) {
        ArrayList<Long> foundNumbers = new ArrayList<>();

        while (count > 0) {
            if (numberHasAllProperties(start, properties)) {
                foundNumbers.add(start);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static boolean numberHasAllProperties(long number, ArrayList<String> properties) {
        boolean hasAllProperties = true;

        for (int i = 0; i < properties.size(); i++) {
            String property = properties.get(i);
            if (property.contains("-")) {
                if (numberHasProperty(number, property.replace("-", ""))) {
                    hasAllProperties = false;
                    break;
                }
            } else if (!numberHasProperty(number, property)) {
                hasAllProperties = false;
                break;
            }
        }

        return hasAllProperties;
    }

    private static boolean numberHasProperty(long number, String property) {
        boolean hasProperty = false;

        switch (property) {
            case "BUZZ":
                hasProperty = isBuzzNumber(number);
                break;
            case "DUCK":
                hasProperty = isDuckNumber(number);
                break;
            case "PALINDROMIC":
                hasProperty = isPalindrome(number);
                break;
            case "GAPFUL":
                hasProperty = isGapful(number);
                break;
            case "SPY":
                hasProperty = isSpy(number);
                break;
            case "SQUARE":
                hasProperty = isPerfectSquare(number);
                break;
            case "SUNNY":
                hasProperty = isSunny(number);
                break;
            case "JUMPING":
                hasProperty = isJumping(number);
                break;
            case "HAPPY":
                hasProperty = isHappy(number);
                break;
            case "SAD":
                hasProperty = isSad(number);
                break;
            case "EVEN":
                hasProperty = isEven(number);
                break;
            case "ODD":
                hasProperty = isOdd(number);
                break;
        }

        return hasProperty;
    }
}
