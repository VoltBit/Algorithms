package sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Sorting {

    public static void main(String[] args) {
        Random rand = new Random();
        IntStream input = rand.ints(10,100,200);
        int inputArray[];
        inputArray = input.toArray();
        System.out.println("Array: " + Arrays.toString(inputArray));
        MergeSort.initialize();
        MergeSort.initialize(inputArray,inputArray.length);

        MergeSort.mergeSort(inputArray, 0, inputArray.length);
        System.out.println("Sorted: " + Arrays.toString(inputArray));
    }
}