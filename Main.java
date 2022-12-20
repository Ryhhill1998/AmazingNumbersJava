import java.util.Scanner;

public class Main {

    private static final String[] PROPERTIES = {"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "EVEN", "ODD"};

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
        System.out.println("Supported requests:\n" +
                "\t- enter a natural number to know its properties;\n" +
                "\t- enter two natural numbers to obtain the properties of the list:\n" +
                "\t\t* the first parameter represents a starting number;\n" +
                "\t\t* the second parameters show how many consecutive numbers are to be processed;\n" +
                "\t- two natural numbers and a property to search for;\n" +
                "\t- separate the parameters with one space;\n" +
                "\t- enter 0 to exit.");
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
                String[] splitRequests = request.split(" ");
                int length = splitRequests.length;

                if (length > 3) {
                    System.out.println("Please enter no more than 3 values!");
                } else {
                    long number = Long.parseLong(splitRequests[0]);

                    if (!firstParameterIsValid(number)) {
                        System.out.println("The first parameter should be a natural number or zero.");
                    } else if (number == 0) {
                        System.out.println("Goodbye!");
                        quit = true;
                    } else if (length == 1) {
                        displayProperties(number);
                    } else {
                        int count = Integer.parseInt(splitRequests[1]);

                        if (!secondParameterIsValid(count)) {
                            System.out.println("The second parameter should be a natural number.");
                        }

                        if (length == 2) {
                            for (long i = number; i < number + count; i++) {
                                displayPropertiesList(i);
                            }
                        } else {
                            String property = splitRequests[2].toUpperCase();

                            if (!thirdParameterIsValid(property)) {
                                System.out.println("The property [" + property + "] is wrong." +
                                        "\nAvailable properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]");
                            } else {
                                long[] foundNumbers = findNumbersWithProperty(number, count, property);

                                for (int i = 0; i < foundNumbers.length; i++) {
                                    displayPropertiesList(foundNumbers[i]);
                                }
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("please only enter numerical values!");
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
