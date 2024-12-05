package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Day5 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> input = readInput();
        System.out.println(Utils.sum(getMiddlePageOrders(rightOrder(getPageOrderingRules(input), getPagesToProduce(input)))));

    }

    public static ArrayList<String> readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Day5.txt"));
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) lines.add(scanner.nextLine());
        scanner.close();
        return lines;
    }

    public static ArrayList<String> getPageOrderingRules(ArrayList<String> input) {
        ArrayList<String> rules = new ArrayList<>();

        for (String line : input) {
            if (line.equals("")) break;
            rules.add(line);
        }

        return rules;
    }

    public static ArrayList<Integer[]> getPagesToProduce(ArrayList<String> input) {
        ArrayList<Integer[]> pages = new ArrayList<>();

        boolean enabled = false;
        for (String line : input) {
            if (enabled)
                pages.add(Arrays.stream(line.split(",")).map(Integer::valueOf).toList().toArray(new Integer[0]));

            if (line.equals("")) enabled = true;
        }

        return pages;
    }

    public static ArrayList<Integer[]> rightOrder(ArrayList<String> pageOrderingRules, ArrayList<Integer[]> pagesToProduce) {
        ArrayList<Integer[]> correctPageOrders = new ArrayList<>();

        for (Integer[] update : pagesToProduce) {

            boolean allCorrect = true;
            for (int j = 0; j < update.length; j++) {
                int x = update[j];
                for (int k = j + 1; k < update.length; k++) {
                    boolean found = false;
                    for (String pageOrderingRule : pageOrderingRules) {
                        if (pageOrderingRule.equals(x + "|" + update[k])) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        allCorrect = false;
                        break;
                    }
                }
            }

            if (allCorrect) correctPageOrders.add(update);
        }

        return correctPageOrders;
    }

    public static int[] getMiddlePageOrders(ArrayList<Integer[]> orders) {
        int[] middles = new int[orders.size()];
        for (int i = 0; i < orders.size(); i++) middles[i] = orders.get(i)[orders.get(i).length / 2];
        return middles;
    }

}
