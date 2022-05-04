package com.company;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] arr = new int[10];
        int[] arr1 = new int[10];
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++){
            int number = 1 + rnd.nextInt(10 + 1);
            arr[i] = number;
            arr1[i] = number;
        }

        long startTime = System.nanoTime();
        Sorter.sort(arr);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        long startTime1 = System.nanoTime();
        Arrays.sort(arr1);
        long endTime1 = System.nanoTime();
        long duration1 = (endTime1 - startTime1);

        System.out.println(Arrays.toString(arr));
        System.out.println(duration + "\n" + duration1);
    }
}
