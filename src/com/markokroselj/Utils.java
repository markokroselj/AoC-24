package com.markokroselj;

public class Utils {
    public static int sum(int[] array) {
        if (array.length == 0) return 0;
        if (array.length == 1) return array[0];

        int[] newArray = new int[array.length - 1];
        for (int i = 0; i < newArray.length; i++) newArray[i] = array[i + 1];

        return array[0] + sum(newArray);
    }
}
