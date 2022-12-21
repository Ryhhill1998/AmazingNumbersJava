import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

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
                "\t\t* the second parameter shows how many consecutive numbers are to be printed;\n" +
                "\t- two natural numbers and properties to search for;\n" +
                "\t- a property preceded by minus must not be present in numbers;\n" +
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
            EvaluatedNumber num = new EvaluatedNumber(number);
            num.evaluateProperties();
            displayProperties(num);
        }
    }

    private static void processRangeRequest(String[] parameters) {
        long number = Long.parseLong(parameters[0]);
        int count = Integer.parseInt(parameters[1]);

        if (parametersAreValid(number, count)) {
            for (long i = number; i < number + count; i++) {
                EvaluatedNumber num = new EvaluatedNumber(i);
                num.evaluateProperties();
                displayPropertiesList(num);
            }
        }
    }

    private static ArrayList<String> getSearchProperties(String[] searchParameters) {
        ArrayList<String> searchProperties = new ArrayList<>();
        HashSet<String> propertiesAdded = new HashSet<>();

        for (int i = 2; i < searchParameters.length; i++) {
            String property = searchParameters[i].toUpperCase();
            if (!propertiesAdded.contains(property)) {
                searchProperties.add(property);
                propertiesAdded.add(property);
            }
        }

        if (!validateSearchProperties(searchProperties)) {
            searchProperties = null;
        }

        return searchProperties;
    }

    private static boolean validateSearchProperties(ArrayList<String> searchProperties) {
        boolean isValid = true;
        HashSet<String> conflicts = new HashSet<>();
        HashSet<String> inverses = new HashSet<>();

        ArrayList<String> invalidProperties = new ArrayList<>();
        ArrayList<String> mutuallyExclusiveClashes = new ArrayList<>();

        for (String property : searchProperties) {
            property = property.toUpperCase();

            if (conflicts.contains(property)) {
                mutuallyExclusiveClashes.add(property);
                mutuallyExclusiveClashes.add(getConflict(property));
                isValid = false;
            }

            if (inverses.contains(property)) {
                mutuallyExclusiveClashes.add(property);
                mutuallyExclusiveClashes.add(getInverse(property));
                isValid = false;
            }

            if (!propertyExists(property.replace("-", ""))) {
                invalidProperties.add(property);
                isValid = false;
            }

            conflicts.add(getConflict(property));
            inverses.add(getInverse(property));
        }

        if (invalidProperties.size() > 0) {
            provideInvalidPropertiesFeedback(invalidProperties);
        }

        if (mutuallyExclusiveClashes.size() > 0) {
            provideMutuallyExclusiveClashesFeedback(mutuallyExclusiveClashes);
        }

        return isValid;
    }

    private static void provideInvalidPropertiesFeedback(ArrayList<String> invalidProperties) {
        StringBuilder feedback = new StringBuilder("The propert");
        feedback.append(invalidProperties.size() > 1 ? "ies" : "y").append(" [");

        for (int i = 0; i < invalidProperties.size(); i++) {
            if (i > 0) {
                feedback.append(", ");
            }
            feedback.append(invalidProperties.get(i));
        }

        feedback.append("] ").append(invalidProperties.size() > 1 ? "are" : "is").append(" wrong.\n");
        feedback.append("Available properties:");
        Property[] availableProperties = Property.values();

        for (int i = 0; i < availableProperties.length; i++) {
            if (i > 0) {
                feedback.append(", ");
            }
            feedback.append(availableProperties[i]);
        }

        feedback.append("]");

        System.out.println(feedback);
    }

    private static void provideMutuallyExclusiveClashesFeedback(ArrayList<String> mutuallyExclusiveClashes) {
        StringBuilder feedback = new StringBuilder("The request contains mutually exclusive properties: [");

        for (int i = 0; i < mutuallyExclusiveClashes.size(); i++) {
            if (i > 0) {
                feedback.append(", ");
            }
            feedback.append(mutuallyExclusiveClashes.get(i));
        }

        feedback.append("]\nThere are no numbers with these properties.");

        System.out.println(feedback);
    }

    private static String getConflict(String property) {
        return switch (property) {
            case "EVEN" -> "ODD";
            case "ODD" -> "EVEN";
            case "-EVEN" -> "-ODD";
            case "-ODD" -> "-EVEN";
            case "DUCK" -> "SPY";
            case "SPY" -> "DUCK";
            case "SUNNY" -> "SQUARE";
            case "SQUARE" -> "SUNNY";
            case "SAD" -> "HAPPY";
            case "HAPPY" -> "SAD";
            default -> null;
        };
    }

    private static String getInverse(String property) {
        String inverse = "";

        if (property.startsWith("-")) {
            inverse = property.replace("-", "");
        } else {
            inverse += "-";
            inverse += property;
        }

        return inverse;
    }

    private static boolean propertyExists(String propertyName) {
        boolean propertyExists = false;
        Property[] propertyValues = Property.values();

        for (int i = 0; i < propertyValues.length; i++) {
            Property p = propertyValues[i];
            if (String.valueOf(p).equals(propertyName)) {
                propertyExists = true;
                break;
            }
        }

        return propertyExists;
    }

    private static void processSearch(String[] searchParameters) {
        long number = Long.parseLong(searchParameters[0]);
        int count = Integer.parseInt(searchParameters[1]);

        ArrayList<String> properties = getSearchProperties(searchParameters);

        if (properties == null) {
            return;
        }

        if (parametersAreValid(number, count)) {
            ArrayList<EvaluatedNumber> foundNumbers = findNumbersWithProperties(number, count, properties);

            for (EvaluatedNumber n : foundNumbers) {
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

    private static boolean firstParameterIsValid(long parameter) {
        return parameter >= 0;
    }

    private static boolean secondParameterIsValid(long parameter) {
        return parameter > 0;
    }

    private static void displayProperties(EvaluatedNumber number) {
        System.out.println(number.getPropertiesDescription());
    }

    private static void displayPropertiesList(EvaluatedNumber number) {
        System.out.println(number.getPropertiesList());
    }

    private static ArrayList<EvaluatedNumber> findNumbersWithProperties(long start, int count, ArrayList<String> properties) {
        ArrayList<EvaluatedNumber> foundNumbers = new ArrayList<>();

        while (count > 0) {
            EvaluatedNumber number = new EvaluatedNumber(start);
            if (numberHasAllProperties(number, properties)) {
                number.evaluateProperties();
                foundNumbers.add(number);
                count--;
            }

            start++;
        }

        return foundNumbers;
    }

    private static boolean numberHasAllProperties(EvaluatedNumber number, ArrayList<String> properties) {
        boolean hasAllProperties = true;

        for (int i = 0; i < properties.size(); i++) {
            String p = properties.get(i);

            if (String.valueOf(p).startsWith("-")) {
                Property property = Property.valueOf(p.replace("-", ""));
                if (number.evaluateProperty(property)) {
                    hasAllProperties = false;
                    break;
                }
            } else if (!number.evaluateProperty(Property.valueOf(p))) {
                hasAllProperties = false;
                break;
            }
        }

        return hasAllProperties;
    }
}
