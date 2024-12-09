package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day7 {
    static ArrayList<String> variations = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(totalCalibrationResult(getCalibrationEquations()));
    }

    public static ArrayList<String> getCalibrationEquations() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Day7.txt"));
        ArrayList<String> input = new ArrayList<String>();
        while (scanner.hasNextLine()) input.add(scanner.nextLine());
        scanner.close();
        return input;
    }

    private static void operatorsVariations(String operators, int k, StringBuffer output) {
        if (k == 0) {
            variations.add(output.toString());
            return;
        }

        for (int i = 0; i < operators.length(); i++) {
            output.append(operators.charAt(i));
            operatorsVariations(operators, k - 1, output);
            output.deleteCharAt(output.length() - 1);
        }
    }

    public static void setOperatorsVariations(String operators, int k) {
        if (!variations.isEmpty()) variations.clear();
        operatorsVariations(operators, k, new StringBuffer());
    }


    public static boolean isPossiblyTrue(long testValue, long[] operands) {
        setOperatorsVariations("+*", operands.length - 1);
        for (String operators : variations) {
            long result = 0;
            for (int j = 0; j < operands.length - 1; j++) {
                if (j == 0)
                    result = operators.charAt(j) == '+' ? operands[j] + operands[j + 1] : operands[j] * operands[j + 1];
                else result = operators.charAt(j) == '+' ? result + operands[j + 1] : result * operands[j + 1];
            }
            if (result == testValue) return true;
        }
        return false;
    }

    public static long totalCalibrationResult(ArrayList<String> equations) {
        long sum = 0;
        for (String equation : equations) {
            long testValue = Long.parseLong(equation.split(":")[0]);
            long[] operands = Arrays.stream(equation.split(":\\s+")[1].split("\\s+")).mapToLong(Long::parseLong).toArray();
            if (isPossiblyTrue(testValue, operands)) sum += testValue;
        }

        return sum;
    }
}
