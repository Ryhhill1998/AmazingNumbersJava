import java.util.ArrayList;
import java.util.HashSet;

public class EvaluatedNumber {

    private long value;
    private ArrayList<Property> properties;

    public EvaluatedNumber(long value) {
        this.value = value;
        properties = new ArrayList<>();
    }

    public boolean addProperty(Property p) {
        return properties.add(p);
    }

    public void evaluateProperties() {
        for (Property p : Property.values()) {
            if (!properties.contains(p)) {
                evaluateProperty(p);
            }
        }
    }

    public boolean evaluateProperty(Property p) {
        boolean hasProperty = false;

        if (numberHasProperty(p)) {
            addProperty(p);
            hasProperty = true;
        }

        return hasProperty;
    }

    public String getPropertiesDescription() {
        StringBuilder propertiesDescription = new StringBuilder(String.format("Properties of %,d\n", value));

        for (Property p : Property.values()) {
            propertiesDescription.append(formatProperty(p, properties.contains(p)));
        }

        return propertiesDescription.toString();
    }

    private String formatProperty(Property p, boolean hasProperty) {
        String propertyName = p.toString().toLowerCase();
        return String.format("%" + getDisplacement(propertyName) + "s%s: %b\n", "", propertyName, hasProperty);
    }

    private static int getDisplacement(String property) {
        return 12 - property.length();
    }

    public String getPropertiesList() {
        StringBuilder propertiesList = new StringBuilder(String.format("%,16d is ", value));

        for (int i = 0; i < properties.size(); i++) {
            Property p = properties.get(i);
            if (i > 0) {
                propertiesList.append(", ");
            }
            propertiesList.append(p.toString().toLowerCase());
        }

        return propertiesList.toString();
    }

    private boolean numberHasProperty(Property p) {
        return switch (p) {
            case BUZZ -> isBuzzNumber(value);
            case DUCK -> isDuckNumber(value);
            case PALINDROMIC -> isPalindrome(value);
            case GAPFUL -> isGapful(value);
            case SPY -> isSpy(value);
            case SQUARE -> isPerfectSquare(value);
            case SUNNY -> isSunny(value);
            case HAPPY -> isHappy(value);
            case SAD -> isSad(value);
            case JUMPING -> isJumping(value);
            case EVEN -> isEven(value);
            case ODD -> isOdd(value);
        };
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
}
