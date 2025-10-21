package org.programming.labs.kariuk.Lab1;

import java.util.Scanner;
import java.util.Random;

public class Lab_1 {

    private static final int MINIMUM_RANDOM_VALUE = -20;
    private static final int MAXIMUM_RANDOM_VALUE = 20;
    private static final int MINIMUM_MATRIX_DIMENSION = 1;
    private static final int MAXIMUM_MATRIX_DIMENSION = 20;
    private static final int MANUAL_CREATION_CHOICE = 1;
    private static final int RANDOM_CREATION_CHOICE = 2;

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("=== Integer Matrix Operations Program ===");

        int matrixRowCount = getValidMatrixDimension(inputScanner, "Enter number of rows (" + MINIMUM_MATRIX_DIMENSION + "–" + MAXIMUM_MATRIX_DIMENSION + "): ");
        int matrixColumnCount = getValidMatrixDimension(inputScanner, "Enter number of columns (" + MINIMUM_MATRIX_DIMENSION + "–" + MAXIMUM_MATRIX_DIMENSION + "): ");

        int matrixCreationChoice = promptForCreationChoice(inputScanner);

        int[][] integerMatrix;

        if (matrixCreationChoice == MANUAL_CREATION_CHOICE) {
            integerMatrix = createMatrixByManualInput(inputScanner, matrixRowCount, matrixColumnCount);
        } else {
            integerMatrix = createMatrixByRandomGeneration(matrixRowCount, matrixColumnCount);
        }

        System.out.println("\nCreated Matrix:");
        printMatrixToConsole(integerMatrix);

        int minimumElementValue = findMinimumElement(integerMatrix);
        int maximumElementValue = findMaximumElement(integerMatrix);
        double arithmeticMeanValue = calculateArithmeticMean(integerMatrix);
        double geometricMeanValue = calculateGeometricMean(integerMatrix);

        displayCalculationResults(minimumElementValue, maximumElementValue, arithmeticMeanValue, geometricMeanValue);

        inputScanner.close();
    }


    private static int getValidMatrixDimension(Scanner inputScanner, String promptMessage) {
        int dimensionSize = 0;
        boolean isInputValid = false;

        while (!isInputValid) {
            System.out.print(promptMessage);
            if (inputScanner.hasNextInt()) {
                dimensionSize = inputScanner.nextInt();
                if (isMatrixSizeValid(dimensionSize)) {
                    isInputValid = true;
                } else {
                    displayDimensionInputError();
                }
            } else {
                displayDimensionInputError();
                inputScanner.next();
            }
        }
        return dimensionSize;
    }

    private static boolean isMatrixSizeValid(int dimensionSize) {
        return dimensionSize >= MINIMUM_MATRIX_DIMENSION && dimensionSize <= MAXIMUM_MATRIX_DIMENSION;
    }

    private static void displayDimensionInputError()
    {
        System.out.println("Error: Enter an integer between " + MINIMUM_MATRIX_DIMENSION + " and " + MAXIMUM_MATRIX_DIMENSION + ".");
    }

    private static int promptForCreationChoice(Scanner inputScanner) {
        System.out.println("Choose matrix creation method:");
        System.out.println(MANUAL_CREATION_CHOICE + " - Manual Input");
        System.out.println(RANDOM_CREATION_CHOICE + " - Random Generation");
        return getValidChoice(inputScanner);
    }

    private static int getValidChoice(Scanner inputScanner) {
        int userChoice = 0;
        boolean isChoiceValid = false;

        while (!isChoiceValid) {
            System.out.print("Your choice: ");
            if (inputScanner.hasNextInt()) {
                userChoice = inputScanner.nextInt();
                if (isCreationChoiceValid(userChoice)) {
                    isChoiceValid = true;
                } else {
                    displayChoiceInputError();
                }
            } else {
                displayChoiceInputError();
                inputScanner.next();
            }
        }
        return userChoice;
    }

    private static boolean isCreationChoiceValid(int choice) {
        return choice == MANUAL_CREATION_CHOICE || choice == RANDOM_CREATION_CHOICE;
    }

    private static void displayChoiceInputError() {
        System.out.println("Error: Enter number " + MANUAL_CREATION_CHOICE + " or " + RANDOM_CREATION_CHOICE + ".");
    }


    private static int[][] createMatrixByManualInput(Scanner inputScanner, int rowCount, int columnCount) {
        int[][] matrixData = new int[rowCount][columnCount];
        System.out.println("Enter matrix elements (" + rowCount + "×" + columnCount + "):");

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrixData[rowIndex][columnIndex] = getValidIntegerElement(inputScanner, rowIndex + 1, columnIndex + 1);
            }
        }
        return matrixData;
    }

    private static int getValidIntegerElement(Scanner inputScanner, int displayRowIndex, int displayColumnIndex) {
        int elementValue = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print("Element [" + displayRowIndex + "][" + displayColumnIndex + "]: ");
            if (inputScanner.hasNextInt()) {
                elementValue = inputScanner.nextInt();
                isValid = true;
            } else {
                System.out.println("Error: Must enter an integer!");
                inputScanner.next();
            }
        }
        return elementValue;
    }

    private static int[][] createMatrixByRandomGeneration(int rowCount, int columnCount) {
        int[][] matrixData = new int[rowCount][columnCount];
        Random randomGenerator = new Random();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                matrixData[rowIndex][columnIndex] = generateRandomInteger(randomGenerator);
            }
        }
        return matrixData;
    }

    private static int generateRandomInteger(Random randomGenerator) {
        return randomGenerator.nextInt(MAXIMUM_RANDOM_VALUE - MINIMUM_RANDOM_VALUE + 1) + MINIMUM_RANDOM_VALUE;
    }


    private static void printMatrixToConsole(int[][] matrixData) {
        for (int[] matrixRow : matrixData) {
            for (int elementValue : matrixRow) {
                System.out.printf("%6d", elementValue);
            }
            System.out.println();
        }
    }

    private static void displayCalculationResults(int minimum, int maximum, double arithmeticMean, double geometricMean) {
        System.out.println("\n=== Calculation Results ===");
        System.out.println("Minimum Element: " + minimum);
        System.out.println("Maximum Element: " + maximum);
        System.out.printf("Arithmetic Mean: %.2f\n", arithmeticMean);
        System.out.printf("Geometric Mean: %.2f\n", geometricMean);
    }


    private static int findMinimumElement(int[][] matrixData) {
        int minimumValue = matrixData[0][0];
        for (int[] matrixRow : matrixData) {
            for (int elementValue : matrixRow) {
                if (elementValue < minimumValue) {
                    minimumValue = elementValue;
                }
            }
        }
        return minimumValue;
    }

    private static int findMaximumElement(int[][] matrixData) {
        int maximumValue = matrixData[0][0];
        for (int[] matrixRow : matrixData) {
            for (int elementValue : matrixRow) {
                if (elementValue > maximumValue) {
                    maximumValue = elementValue;
                }
            }
        }
        return maximumValue;
    }

    private static double calculateArithmeticMean(int[][] matrixData) {
        long sumOfElements = 0;
        int elementCount = 0;

        for (int[] matrixRow : matrixData) {
            for (int elementValue : matrixRow) {
                sumOfElements += elementValue;
                elementCount++;
            }
        }

        return (double) sumOfElements / elementCount;
    }

    private static double calculateGeometricMean(int[][] matrixData) {
        double productOfPositiveElements = 1.0;
        int positiveElementCount = 0;

        for (int[] matrixRow : matrixData) {
            for (int elementValue : matrixRow) {
                if (elementValue > 0) {
                    productOfPositiveElements *= elementValue;
                    positiveElementCount++;
                }
            }
        }

        if (positiveElementCount == 0) {
            System.out.println("The matrix contains no positive elements. Cannot calculate geometric mean.");
            return 0.0;
        }

        return Math.pow(productOfPositiveElements, 1.0 / positiveElementCount);
    }
}
