package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> lines = readLines();

        System.out.println(horizontalMatches(lines) + horizontalMatches(getColumns(lines)) + diagonalMatches(lines));
    }


    public static ArrayList<String> readLines() throws FileNotFoundException {
        ArrayList<String> input = new ArrayList<String>();

        Scanner scanner = new Scanner(new File("src/Day4.txt"));
        while (scanner.hasNextLine()) input.add(scanner.nextLine());

        return input;
    }

    public static int horizontalMatches(ArrayList<String> input) {
        Pattern pattern = Pattern.compile("XMAS");
        Pattern backwardsPattern = Pattern.compile("SAMX");

        int numberOfMatches = 0;

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) numberOfMatches++;

            matcher = backwardsPattern.matcher(line);
            while (matcher.find()) numberOfMatches++;
        }

        return numberOfMatches;
    }

    public static ArrayList<String> getColumns(ArrayList<String> lines) {
        ArrayList<String> columns = new ArrayList<>();

        for (int i = 0; i < lines.get(0).length(); i++) {
            String column = "";
            for (String line : lines) column += line.charAt(i);
            columns.add(column);
        }

        return columns;
    }

    public static int diagonalMatches(ArrayList<String> lines) {
        int counter = 0;
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == 'X' && j < lines.get(i).length() - 3 && i < lines.size() - 3) {
                    if (lines.get(i + 1).charAt(j + 1) == 'M' && lines.get(i + 2).charAt(j + 2) == 'A' && lines.get(i + 3).charAt(j + 3) == 'S') {
                        counter++;
                    }
                } else if (lines.get(i).charAt(j) == 'S' && j < lines.get(i).length() - 3 && i < lines.size() - 3) {
                    if (lines.get(i + 1).charAt(j + 1) == 'A' && lines.get(i + 2).charAt(j + 2) == 'M' && lines.get(i + 3).charAt(j + 3) == 'X') {
                        counter++;
                    }
                }

                if (lines.get(i).charAt(j) == 'X' && j > 2 && i < lines.size() - 3) {
                    if (lines.get(i + 1).charAt(j - 1) == 'M' && lines.get(i + 2).charAt(j - 2) == 'A' && lines.get(i + 3).charAt(j - 3) == 'S') {
                        counter++;
                    }
                }
                if (lines.get(i).charAt(j) == 'S' && j > 2 && i < lines.size() - 3) {
                    if (lines.get(i + 1).charAt(j - 1) == 'A' && lines.get(i + 2).charAt(j - 2) == 'M' && lines.get(i + 3).charAt(j - 3) == 'X') {
                        counter++;
                    }
                }
            }
        }

        return counter;
    }

}