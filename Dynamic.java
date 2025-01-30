//Import necessary libraries
import java.util.Scanner;

//Dynamic Programming Class
public class Dynamic {
	
	/**
	 * This is the main driver class for the program
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
        findMaxSubarrayDP(values);
        
        //Close scanner
        scanner.close();
    }//end of main

    /**
     * This method determines the longest chain of values with the highest productivity 
     * @param arr	The array of values that the user put in
     */
    public static void findMaxSubarrayDP(int[] values) {
    	//Initialize variables to keep track of start and end of a max chain, the highest chain and the temporary start
        int maxEndingHere = values[0];
        int maxSoFar = values[0];
        int start = 0;
        int end = 0;
        int tempStart = 0;

        //for loop to find the longest chain
        for (int i = 1; i < values.length; i++) {
        	//checks to see if the next value is greater than the last value with the current value
            if (values[i] > maxEndingHere + values[i]) {
            	//If true, then create a new sub array
                maxEndingHere = values[i];
                tempStart = i;
            } 
            
            //Else, add the current element to the array
            else {
                maxEndingHere += values[i];
            }

            //If the current max with the new value is greater than the previous max, update start, end, and max
            if (maxEndingHere > maxSoFar) {
                maxSoFar = maxEndingHere;
                start = tempStart;
                end = i;
            }
        }
        
        //calls the method to print the sub-array with the new max using the values array and the start and end variables
        printSubarray(values, start, end);
    }//end of findMaxSubarrayDP

    /**
     * This method prints out the sub-array that makes the max productivity
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