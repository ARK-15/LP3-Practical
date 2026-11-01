
/*
 * DAA LP3 Practical
 * Experiment: 0-1 Knapsack using Dynamic Programming
 *
 * Aim:
 * To solve the 0-1 Knapsack problem using Dynamic Programming.
 *
 * Problem:
 * Given n items, each having a weight and a profit, select items
 * to maximize total profit without exceeding the knapsack capacity.
 *
 * 0-1 Rule:
 * Each item can either be selected (1) or not selected (0).
 * An item cannot be selected more than once.
 *
 * Algorithm:
 * 1. Create a DP table dp[n+1][W+1].
 * 2. dp[i][w] stores the maximum profit using the first i items
 *    with knapsack capacity w.
 * 3. If the current item's weight exceeds w, do not select it.
 * 4. Otherwise, choose the maximum of:
 *    - Excluding the item
 *    - Including the item + best profit for remaining capacity
 * 5. dp[n][W] gives the maximum profit.
 *
 * Recurrence:
 * If wt[i-1] > w:
 *     dp[i][w] = dp[i-1][w]
 * Else:
 *     dp[i][w] = max(dp[i-1][w],
 *                    profit[i-1] + dp[i-1][w-wt[i-1]])
 *
 * Base Case:
 * dp[0][w] = 0 and dp[i][0] = 0.
 *
 * Time Complexity: O(n * W)
 * Space Complexity: O(n * W)
 *
 * Sample Input:
 * Number of items: 3
 * Weights: 10 20 30
 * Profits: 60 100 120
 * Capacity: 50
 *
 * Sample Output:
 * Selection Array: [0, 1, 1]
 * Total Weight: 50
 * Maximum Profit: 220
 *
 * Viva:
 * 1. 0-1 means an item is either selected or not selected.
 * 2. DP avoids solving the same subproblems repeatedly.
 * 3. Greedy does not always give the optimal answer for 0-1 Knapsack.
 * 4. W is the knapsack capacity and n is the number of items.
 */


import java.util.Scanner;
import java.util.Arrays;

public class KnapsackDP {

    static int knapsack(int[] wt, int[] profit, int n, int W,
                        int[] selected) {

        int[][] dp = new int[n + 1][W + 1];

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {

                if (wt[i - 1] > w) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = Math.max(
                        dp[i - 1][w],
                        profit[i - 1] + dp[i - 1][w - wt[i - 1]]
                    );
                }
            }
        }

        // Backtrack to find selected items
        int w = W;

        for (int i = n; i >= 1; i--) {

            // If value differs from previous row,
            // current item was selected
            if (dp[i][w] != dp[i - 1][w]) {

                selected[i - 1] = 1;
                w = w - wt[i - 1];
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter number of items: ");
        int n = sc.nextInt();

        System.out.print("\nEnter knapsack capacity: ");
        int W = sc.nextInt();

        if (n < 0 || W < 0) {
            System.out.println("\nInvalid input.");
            sc.close();
            return;
        }

        int[] wt = new int[n];
        int[] profit = new int[n];
        int[] selected = new int[n];

        System.out.println("\nEnter weights of items:");
        for (int i = 0; i < n; i++) {
            wt[i] = sc.nextInt();

            if (wt[i] <= 0) {
                System.out.println("\nWeights must be positive.");
                sc.close();
                return;
            }
        }

        System.out.println("\nEnter profits of items:");
        for (int i = 0; i < n; i++) {
            profit[i] = sc.nextInt();

            if (profit[i] < 0) {
                System.out.println("\nProfits cannot be negative.");
                sc.close();
                return;
            }
        }

        // Find maximum profit and selected items
        int maxProfit = knapsack(wt, profit, n, W, selected);

        // Calculate total weight
        int totalWeight = 0;

        for (int i = 0; i < n; i++) {
            totalWeight += selected[i] * wt[i];
        }

        // Display results
        System.out.println("\nSelection Array: "
                + Arrays.toString(selected));

        System.out.println("Total Weight: " + totalWeight);

        System.out.println("Maximum Profit: " + maxProfit);

        sc.close();
    }
}


/*
 * 0-1 Knapsack using Recursion
 *
 * Time Complexity: O(2^n)
 * Space Complexity: O(1) + O(n) = O(n)
 *
 * No extra data structure is used to store subproblem results.
 * Recursive calls use O(n) auxiliary stack space.
 

static int knapsack(int[] wt, int[] profit, int n, int W) {

    // Base case: no items or no capacity
    if (n == 0 || W == 0)
        return 0;

    // If the current item cannot fit, exclude it
    if (wt[n - 1] > W)
        return knapsack(wt, profit, n - 1, W);

    // Maximum profit from including or excluding the item
    int include = profit[n - 1]
            + knapsack(wt, profit, n - 1, W - wt[n - 1]);

    int exclude = knapsack(wt, profit, n - 1, W);

    return Math.max(include, exclude);
}
*/
