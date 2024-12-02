package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer>[] lists = readLists();

        ArrayList<Integer> left = lists[0];
        ArrayList<Integer> right = lists[1];
        Collections.sort(left);

        Collections.sort(right);

        int[] pairDiff = new int[left.size()];

        for (int i = 0; i < left.size(); i++) pairDiff[i] = Math.abs(left.get(i) - right.get(i));

        System.out.println(sum(pairDiff));

        HashMap<Integer, Integer> hashMap = getRepetition(left, right);

        int[] simScore = new int[hashMap.size()];

        int i = 0;
        for (Integer integer : hashMap.keySet()) simScore[i++] = integer * hashMap.get(integer);

        System.out.println(sum(simScore));
    }

    public static ArrayList<Integer>[] readLists() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Day1.txt"));

        ArrayList<Integer>[] output = new ArrayList[2];

        for (int i = 0; i < output.length; i++) output[i] = new ArrayList<Integer>();

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\\s+");

            output[0].add(Integer.parseInt(line[0]));
            output[1].add(Integer.parseInt(line[1]));
        }

        scanner.close();

        return output;
    }

    public static int sum(int[] array) {
        if (array.length == 0) return 0;
        if (array.length == 1) return array[0];

        int[] newArray = new int[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, newArray.length);

        return array[0] + sum(newArray);
    }

    public static HashMap<Integer, Integer> getRepetition(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for (Integer value : leftList) {
            hashMap.putIfAbsent(value, 0);

            for (Integer integer : rightList)
                if (Objects.equals(value, integer)) hashMap.put(value, hashMap.get(value) + 1);
        }

        return hashMap;
    }
}