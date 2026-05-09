package org.example;

import java.util.*;

/**
 * @author 12
 * Create By 下午2:59
 */
public class Solution {

    private static int MOD = 1000000007;

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int low, int high) {
        if(low<high){
            int index = getIndex(arr,low,high);
            quickSortHelper(arr,low,index-1);
            quickSortHelper(arr,index+1,high);
        }
    }

    private static int getIndex(int[] arr, int low, int high) {
        int mid = arr[high];
        int i = low-1;
        for(int j=low;j<high;j++){
            if(arr[j]<=mid){
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,high);
        return i+1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int InversePairs (int[] nums) {
        // write code here
        long count = 0;
        int[] sort = nums.clone();
        quickSort(sort);

        for (int i = 0; i < nums.length; i++) {
            if(nums[i]>sort[i]){
                count += sort[i]-nums[i];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    count++;
                    count %= MOD;
                }
            }
        }
        return (int) (count % 1000000007);
    }
}
