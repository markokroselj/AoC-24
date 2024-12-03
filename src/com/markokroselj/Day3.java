package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(sum(evalInstructions(readCorruptedMemory())));
    }

    public static ArrayList<String> readCorruptedMemory() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Day3.txt"));

        StringBuilder memory = new StringBuilder();

        while (scanner.hasNextLine()) memory.append(scanner.nextLine());

        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Matcher matcher = pattern.matcher(memory);

        ArrayList<String> instructions = new ArrayList<String>();

        while (matcher.find()) instructions.add(matcher.group());

        return instructions;
    }

    public static int[] evalInstructions(ArrayList<String> instructions) {

        int[] results = new int[instructions.size()];

        for (int i = 0; i < instructions.size(); i++) {
            String instruction = instructions.get(i).substring(4);

            String firstNumber = "", secondNumber = "";

            boolean saveToSecond = false;
            for (int j = 0; j < instruction.length(); j++) {
                if (instruction.charAt(j) == ')') break;

                if (instruction.charAt(j) == ',') {
                    saveToSecond = true;
                    continue;
                }

                if (saveToSecond) secondNumber += instruction.charAt(j);
                else firstNumber += instruction.charAt(j);

            }

            results[i] = Integer.parseInt(firstNumber) * Integer.parseInt(secondNumber);
        }

        return results;
    }

    public static int sum(int[] array) {
        if (array.length == 0) return 0;
        if (array.length == 1) return array[0];

        int[] newArray = new int[array.length - 1];
        for (int i = 0; i < newArray.length; i++) newArray[i] = array[i + 1];

        return array[0] + sum(newArray);
    }
}
