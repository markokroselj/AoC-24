package com.markokroselj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(numberOfSafeReports(readReports()));

        ArrayList<Integer[]> reports = readReports();
        for (int q = 0; q < reports.size(); q++) {
            if (!reportIsSafe(reports.get(q))) {
                Integer[] newReport = (tryToFix(reports.get(q)));
                if (newReport != null) reports.set(q, newReport);
            }
        }
        System.out.println(numberOfSafeReports(reports));
    }

    public static ArrayList<Integer[]> readReports() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/Day2.txt"));

        ArrayList<Integer[]> reports = new ArrayList();

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\\s+");

            Integer[] report = new Integer[line.length];

            for (int i = 0; i < line.length; i++) report[i] = Integer.parseInt(line[i]);

            reports.add(report);
        }
        scanner.close();

        return reports;
    }

    public static int numberOfSafeReports(ArrayList<Integer[]> reports) {
        int amount = 0;
        for (Integer[] report : reports) if (reportIsSafe(report)) amount++;
        return amount;
    }

    public static boolean reportIsSafe(Integer[] report) {
        boolean allIncreasing = true;
        boolean allDecreasing = true;

        for (int i = 1; i < report.length; i++) {
            if (Math.abs(report[i - 1] - report[i]) < 1 || Math.abs(report[i - 1] - report[i]) > 3) return false;

            if (report[i - 1] > report[i]) allIncreasing = false;
            else if (report[i - 1] < report[i]) allDecreasing = false;
        }

        return allIncreasing ^ allDecreasing;
    }

    public static Integer[] tryToFix(Integer[] report) {
        int index = 0;
        int indexToRemove = 0;
        while (index < report.length) {
            Integer[] newReport = new Integer[report.length - 1];
            int index1 = 0;
            for (int i = 0; i < report.length; i++) {
                if (i == indexToRemove) continue;
                newReport[index1++] = report[i];
            }
            if (reportIsSafe(newReport)) return newReport;

            indexToRemove++;
            index++;
        }
        return null;
    }
}
