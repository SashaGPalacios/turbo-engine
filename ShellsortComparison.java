import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ShellsortComparison {
	private static Scanner reader;
	private static Scanner input;
	private static int comparisons;
	private static int count[]; 
	private static double times[];
	
	public static void main(String[] args) throws FileNotFoundException {
		reader = new Scanner(new File("3Doubles.txt")); //Takes in text file from the command line
		ArrayList<Double> data = new ArrayList<Double>();
		while(reader.hasNext() ) {
			double d = reader.nextDouble();
			data.add(d);
		}
		input = new Scanner(System.in);
		System.out.print("Please input the number of trials you wish to run: "); //Takes in a value for the variable "trials"
		int trials = input.nextInt();
		
		count = new int[4]; //Inputs the number of comparisons for each sequence into the array "count"
		times = new double[4]; //Input the average time for each sequence into the array "times"
		
		int N = data.size(); //Sets the data size equal to N
		
		//Inputs the values for each sequence into their own ArrayList and prints them
		ArrayList<Integer> seq1 = sequence1(N);
		ArrayList<Integer> seq2 = sequence2(N);
		ArrayList<Integer> seq3 = sequence3(N);
		ArrayList<Integer> seq4 = sequence4(N);
		System.out.println();
		System.out.println("Sequence Values: ");
		int num = 1;
		sequenceValues(seq1, num);
		sequenceValues(seq2, num+1);
		sequenceValues(seq3, num+2);
		sequenceValues(seq4, num+3);
		System.out.println();
		System.out.println("-------------------------------------------------------------------------");
		
		//Calls on the averageTime method to calculate the average time for each sequence
		int num2 = 1;
		averageTime(data, seq1, num2, trials);
		averageTime(data, seq2, num2+1, trials);
		averageTime(data, seq3, num2+2, trials);
		averageTime(data, seq4, num2+3, trials);
		System.out.println("Average Times: \n");
		for(int i = 0; i < times.length; i++) {
			System.out.println("Average time using Sequence " + (i+1) + ": " + times[i] + " ms");
		}
		
		//Prints the number of comparisons for each sequence
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Comparisons: \n");
		for(int i = 0; i < count.length; i++) {
			System.out.println("Comparisons using Sequence " + (i+1) + ": " + count[i] );
		}
		
		//Prints the ratios by comparing each of the sequences times to every other sequence
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Ratios: \n");
		for(int i = 0; i < times.length; i++) {
			for(int j = (i+1); j < times.length; j++) {
				System.out.println("S" + (i+1) + "/S" + (j+1) + "= " + (times[i]/times[j]));
			}
		}
	}
	
	//Prints the values for each sequence
	public static void sequenceValues(ArrayList<Integer> sequence, int num) {
		System.out.println();
		System.out.print("Sequence " + num + " Values: ");
		for(int u = 0; u < sequence.size(); u++) { 
			System.out.print(sequence.get(u) + " ");
		}
		num++;
	}
	
	//Takes in data, sequence values, sequence number and number of trials then returns an average time along with the number of comparisons
	public static void averageTime(ArrayList<Double> data, ArrayList<Integer> sequence, int num, int trials) {
		double duration = 0.0;
		comparisons = 0;
		for (int i = 1; i <= trials; i++) {
			double start = System.nanoTime ();
			sort(data, sequence);
			duration += (System.nanoTime () - start);
		}
		times[num-1]= (duration/trials);
		count[num-1] = comparisons;
	}
	
	//Calculates the values for sequence 1(2^i) using N/2 as a limit
	public static ArrayList<Integer> sequence1(int size) {
		ArrayList<Integer> seq1 = new ArrayList<Integer>();
		int N = size/2;
		int output = 1;
		for (int i = 1; output < N; i++) {
			seq1.add(output);
			output = (int)Math.pow(2,i);
		}
		return seq1;
	}
	
	//Calculates the values for sequence 1(4^i + 3*2^(i-1)+1) using N/2 as a limit
	public static ArrayList<Integer> sequence2(int size) {
		ArrayList<Integer> seq2 = new ArrayList<Integer>();
		int N = size/2;
		int output = 1;
		for (int i = 1; output < N; i++) {
			seq2.add(output);
			output = (int)(Math.pow(4, i)+(3*Math.pow(2, i-1))+1);
		}
		return seq2;
	}

	//Calculates the values for sequence 1(2^p*3^q) using N/2 as a limit
	public static ArrayList<Integer> sequence3(int size) {
		ArrayList<Integer> seq3 = new ArrayList<Integer>();
		int output_of_2 = 1;
		int N = size/2;
		while(output_of_2 < N) {
			int output_of_2and3 = output_of_2;
			while(output_of_2and3 < N) {
				seq3.add(output_of_2and3);
				output_of_2and3 *= 3;
			}
			output_of_2 *= 2;
		}
		Collections.sort(seq3);
		return seq3;
	}
	
	//Calculates the values for sequence 1(2^i-1) using N/2 as a limit
	public static ArrayList<Integer> sequence4(int size) {
		ArrayList<Integer> seq4 = new ArrayList<Integer>();
		int N = size/2;
		int output = 1;
		for (int i = 2; output < N; i++) {
			seq4.add(output);
			output = (int)(Math.pow(2, i)-1);
		}
		return seq4;
	}	
	
	public static void sort(ArrayList<Double> a, ArrayList<Integer> incs) {   
		int n = a.size();
		for(int k = 0; k < incs.size(); k++) {
			int h = incs.get(k);
			for (int i = h; i < n; i++) {
				for (int j = i; j >= h && less(a.get(j), a.get(j-h)); j -= h) {
					exch(a, j, j - h);
				}
			}  
		}
	}

	public static boolean less(Double v, Double w) {
		comparisons++;
		return (v.compareTo(w) < 0);
	}

	public static void exch(ArrayList<Double> a, int i, int j) {
		Collections.swap(a, i, j);
	} 
	
}

//Name: Sasha Palacios
//Last Revision: 11/20/19
