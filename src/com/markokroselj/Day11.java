package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day11 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> stoneArrangement = readInitialArrangement();

        for (int i = 0; i < 25; i++) stoneArrangement = blink(stoneArrangement);
        System.out.println(stoneArrangement.size());
    }

    public static ArrayList<String> readInitialArrangement() throws FileNotFoundException {
        return
                new ArrayList<>(Arrays.stream(new Scanner(new File("src/Day11.txt")).nextLine().split("\\s+")).toList());
    }

    public static ArrayList<String> blink(ArrayList<String> stoneArrangement) {
        ArrayList<String> newStoneArrangement = new ArrayList<String>();
        for (String stone : stoneArrangement) {
            if (stone.equals("0")) newStoneArrangement.add("1");
            else if (stone.length() % 2 == 0) {

                newStoneArrangement.add(stone.substring(0, stone.length() / 2));
                String tmpRightStone = stone.substring(stone.length() / 2);

                if (tmpRightStone.startsWith("0") && tmpRightStone.length() > 1) {
                    boolean foundNonZero = false;
                    String rightStone = "";
                    for (int i = 0; i < tmpRightStone.length(); i++) {
                        if (tmpRightStone.charAt(i) != '0') foundNonZero = true;
                        if (foundNonZero) rightStone += tmpRightStone.charAt(i);
                    }
                    newStoneArrangement.add(foundNonZero ? rightStone : "0");
                } else newStoneArrangement.add(tmpRightStone);

            } else newStoneArrangement.add(String.valueOf(Long.parseLong(stone) * 2024));
        }
        return newStoneArrangement;
    }
}
