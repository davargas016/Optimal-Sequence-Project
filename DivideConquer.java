//Import necessary libraries
import java.util.Scanner;

//Divide and Conquer Programming Class
public class DivideConquer {
	
	/**
	 * This is the driver method of the program
	 * @param args		the inputs from the command line
	 */
    public static void main(String[] args) {
    	//Scanner to allow the user to input the values in, separated by a comma
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the daily productivity values separated by commas:");
        //Places the values in the array
        String[] input = scanner.nextLine().split(",");
        int[] values = new int[input.length];

        //Changes each input from string to integer data type
        for (int i = 0; i < input.length; i++) {
            values[i] = Integer.parseInt(input[i].trim());
        }

        //Calls the method to find the max productivity chain within the values array
        findMaxSubarrayDNC(values);
        
        //Close scanner
        scanner.close();
    }//end of main

    /**
     * This method finds the max sub-array within the values array
     * @param values	The array holding all the productivity values
     */
    public static void findMaxSubarrayDNC(int[] values) {
    	//Creates an instance of the results to print out
        Result result = maxSubarray(values, 0, values.length - 1);
        printSubarray(values, result.start, result.end);
    }//end of findMaxSubarrayDNC

    /**
     * Class to hold the sub-array info
     *	Holds the start, end, and max of the sub-array
     */
    public static class Result {
        int maxSum, start, end;

        public Result(int maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }//End of result method
    }//End of result class

    /**
     * This method finds the max sub-array by using both the left and right side of the values array
     * @param values	The array holding the values that the user put in
     * @param left		The index of the left most element in the array
     * @param right		The index of the right most element in the array
     * @return			Returns a sub-array with the maximum productivity
     */
    public static Result maxSubarray(int[] values, int left, int right) {
    	//Base Case: If there is only one value in the array, then return the array with 
        if (left == right) {
            return new Result(values[left], left, right);
        }
        
        //Find the middle of the array
        int mid = (left + right) / 2;

        //Check the sub-array of each side starting from one of the ends to the middle
        //Also check if the two arrays create a longer sequence over the middle
        Result leftResult = maxSubarray(values, left, mid);
        Result rightResult = maxSubarray(values, mid + 1, right);
        Result crossResult = maxCrossingSubarray(values, left, mid, right);

        //Compare all sub-arrays at the time and return the sub-array that has the greatest maximum
        if (leftResult.maxSum >= rightResult.maxSum && leftResult.maxSum >= crossResult.maxSum) {
            return leftResult;
        } 
        else if (rightResult.maxSum >= leftResult.maxSum && rightResult.maxSum >= crossResult.maxSum) {
            return rightResult;
        } 
        else {
            return crossResult;
        }
    }//end of maxSubarray

    /**
     * This method checks if there is a longer sequence between the two sub-arrays that occurs over the middle
     * @param values	The array holding the values 
     * @param left		The index of the left most value in the array 
     * @param mid		The index of the middle value in the array
     * @param right		The index of the right most value in the array
     * @return			Return a sub-array that passes through the middle
     */
    public static Result maxCrossingSubarray(int[] values, int left, int mid, int right) {
    	//Initialize the variables to keep track of the left side of the array and middle
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeft = mid;

        //Loop to find the max of the left side
        for (int i = mid; i >= left; i--) {
        	//adds the values to the sum
            sum += values[i];
            //If the sum is grater than the previous sum, replace it and mark new start on the left
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }

        //Initialize the variable to keep track of the right side of the array
        //Also clear the sum 
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        int maxRight = mid;

        //Loop to find the max on the right side
        for (int i = mid + 1; i <= right; i++) {
        	//adds the values to the sum
            sum += values[i];
            //If the sum is grater than the previous sum, replace it and mark new start on the right
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }

        //Return the result with the combined sum of the left and right sub arrays
        return new Result(leftSum + rightSum, maxLeft, maxRight);
    }//end of maxCrossingSubarray

    /**
     *This method prints out the sub-array that makes the max productivity
     * @param values	The array with the productivity values	
     * @param start		The start value of the max productivity sub-array
     * @param end		The end value of the max productivity sub-array
     */
    public static void printSubarray(int[] values, int start, int end) {
    	//For loop starting at the start point to the end point in the sub-array
        for (int i = start; i <= end; i++) {
        	//Print the values
            System.out.print(values[i]);
            //If the value isn't the end, add a comma
            if (i < end) System.out.print(",");
        }
        System.out.println();
    }//end of printSubarray
}//end of program