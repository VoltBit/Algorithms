package sorting;

import java.util.Arrays;

public class MergeSort {
    static int[] aux;
    static final int MAX_SIZE = 100;
    public static void initialize(){
        aux = new int[MAX_SIZE];
    }
    public static void initialize(int[] v, int n){
        aux = new int[MAX_SIZE];
        int i = 0;
        for(i = 0; i < n; i++){
            aux[i] = v[i];
        }
    }
    public static void mergeSort(int[] v, int start,int end){
        if(start == end) return;
        int mid;
        mid = (start + end) / 2;
        mergeSort(v, start, mid);
        mergeSort(v, mid + 1, end);
        merge(v, start, end);
    }
    private static void merge(int[] v, int start, int end){
        int mid = (start + end) / 2;
        int i = start;
        int j = mid + 1;
        int k = 0;
        while(i < mid && j < end){
            if(v[i] < v[j]) aux[k++] = v[i++];
            else aux[k++] = v[j++];
        }
        while(i < mid) aux[k++] = v[i++];

        while(j < end) aux[k++] = v[j++];
        copy(v, aux, start, end);
    }
    
    private static void copy(int[] dest, int[] source, int start, int end){
        int i;
        for(i = start; i < end; i++){
            dest[i] = source[i];
        }
        System.out.println("Array: " + Arrays.toString(dest));
    }
}