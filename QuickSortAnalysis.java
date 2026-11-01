/*
 * DAA LP3 Practical
 * Experiment: Analysis of Quick Sort using Deterministic
 * and Randomized Variants
 *
 * Aim:
 * To implement and analyze Quick Sort using deterministic
 * and randomized pivot selection.
 *
 * Deterministic Quick Sort:
 * - Always selects the last element as the pivot.
 * - Pivot selection is predictable.
 *
 * Randomized Quick Sort:
 * - Selects a random element as the pivot.
 * - Randomization reduces the chance of repeatedly getting
 *   a bad pivot for certain input arrangements.
 *
 * Quick Sort Steps:
 * 1. Select a pivot.
 * 2. Partition the array around the pivot.
 * 3. Recursively sort the left part.
 * 4. Recursively sort the right part.
 *
 * Time Complexity:
 * Best Case    : O(n log n)
 * Average Case : O(n log n)
 * Worst Case   : O(n^2)
 *
 * Space Complexity:
 * Average Case : O(log n) recursion stack
 * Worst Case   : O(n) recursion stack
 *
 * Sample Input:
 * 5
 * 50 20 40 10 30
 *
 * Sample Output:
 * Deterministic Quick Sort:
 * 10 20 30 40 50
 *
 * Randomized Quick Sort:
 * 10 20 30 40 50
 *
 * Viva Points:
 * 1. Quick Sort is a divide-and-conquer algorithm.
 * 2. The pivot divides the array into two parts.
 * 3. Deterministic Quick Sort uses a fixed pivot-selection rule.
 * 4. Randomized Quick Sort selects the pivot randomly.
 * 5. Randomization does not change the worst-case O(n^2),
 *    but makes consistently bad pivot choices less likely.
 */

import java.util.*;

public class QuickSortAnalysis {

    // =========================================================
    // DETERMINISTIC QUICK SORT
    // =========================================================

    // Partition using the last element as pivot
    static int deterministicPartition(int[] arr, int low, int high) {

        int pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j] <= pivot) {

                i++;

                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place pivot at its correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    static void deterministicQuickSort(int[] arr, int low, int high) {

        if (low < high) {

            int pivotIndex =
                    deterministicPartition(arr, low, high);

            deterministicQuickSort(arr, low, pivotIndex - 1);

            deterministicQuickSort(arr, pivotIndex + 1, high);
        }
    }

    // =========================================================
    // RANDOMIZED QUICK SORT
    // =========================================================

    // Partition for randomized Quick Sort
    static int randomizedPartition(int[] arr, int low, int high) {

        Random random = new Random();

        // Select a random pivot index
        int randomIndex = low +
                random.nextInt(high - low + 1);

        // Move random pivot to the last position
        int temp = arr[randomIndex];
        arr[randomIndex] = arr[high];
        arr[high] = temp;

        // Now use normal partition
        int pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j] <= pivot) {

                i++;

                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place pivot at its correct position
        temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    static void randomizedQuickSort(int[] arr, int low, int high) {

        if (low < high) {

            int pivotIndex =
                    randomizedPartition(arr, low, high);

            randomizedQuickSort(arr, low, pivotIndex - 1);

            randomizedQuickSort(arr, pivotIndex + 1, high);
        }
    }

    // Display array
    static void printArray(int[] arr) {

        for (int value : arr) {
            System.out.print(value + " ");
        }

        System.out.println();
    }

    // Copy array
    static int[] copyArray(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter number of elements: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("\nNumber of elements must be positive.");
            sc.close();
            return;
        }

        int[] arr = new int[n];

        System.out.println("\nEnter elements:");

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Create two copies so that both algorithms
        // receive the same original input.
        int[] deterministicArray = copyArray(arr);
        int[] randomizedArray = copyArray(arr);

        // Deterministic Quick Sort
        deterministicQuickSort(
                deterministicArray, 0, n - 1);

        // Randomized Quick Sort
        randomizedQuickSort(
                randomizedArray, 0, n - 1);

        System.out.println("\nDeterministic Quick Sort:");
        printArray(deterministicArray);

        System.out.println("\nRandomized Quick Sort:");
        printArray(randomizedArray);

        sc.close();
    }
}