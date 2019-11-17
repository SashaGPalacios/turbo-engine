import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.io.File; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class SimilarityMeasurement {
	
	private static Scanner reader;
	private static List<String> texts;
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	public static void main(String[] args) throws FileNotFoundException {
		System.out.print("Please input the increment you wish to use: ");
		int k = Integer.parseInt(args[1]);
		
		reader = new Scanner(new File(args[0]));
		
		texts = new ArrayList<String>();
		
		while(reader.hasNext()) { //inputs the file names into an array called "texts"
			String s = "";
			s = reader.nextLine();
			texts.add(s);
		}
		
		List<String> lines = new ArrayList<String>();
		
		for(int i = 0; i < texts.size(); i++) { //inputs the Strings for each text file into an array called "lines"
			lines.add(txtToString((texts.get(i))));
		}
		
		ArrayList<ArrayList<Integer>> codes = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < lines.size();i++) { //converts Strings into hashCodes and puts them into an array of hashCode arrays called "codes"
			String convert = lines.get(i);
			codes.add(makeHash(convert, k));
		}
		System.out.println();
		System.out.println("HashCodes:");
		for(int u = 0; u < codes.size(); u++) { //Test hashCodes
			System.out.println(codes.get(u));
		}
		
		ArrayList<ArrayList<Integer>> uniques = new ArrayList<ArrayList<Integer>>();
		
		for (int u = 0; u < codes.size(); u++) { //identifies the unique hashCodes for comparisons and inputs them into "uniques" array
			uniques.add(createUniques(codes.get(u)));
		}
		System.out.println();
		System.out.println("Unique HashCodes:");
		for(int v = 0; v < uniques.size(); v++) { //Test unique hashCodes
			System.out.println(uniques.get(v));
		}
		
		System.out.println();
		System.out.println("Degrees of Similarity:");
		calculateSim(uniques);
		
	}

	//Takes in a .txt file and returns a String without any special characters
	public static String txtToString(String input) throws FileNotFoundException {
			reader = new Scanner(new File(input));
			String s = "";
			while (reader.hasNext()) { //Converts the input file into a String
				s = s + reader.nextLine() + " ";
			}
			s = s.replaceAll("\\d", " "); //excludes numbers
			s = s.replaceAll("[ ! - / , . ( ) $ ; @ : -]", " ");
			return s;
		}
	
	//Takes in a String and a k-gram value and returns an array of hashCodes
	public static ArrayList<Integer> makeHash(String s, int k) {
		
		StringTokenizer st = new StringTokenizer(s);
		List<String> elements = new ArrayList<String>();
		
		while (st.hasMoreTokens()) { //Converts the String into an ArrayList called "elements"
			elements.add(st.nextToken());
		}
		
		ArrayList<Integer> S1 = new ArrayList<Integer>();
		for (int i = 0; i <= elements.size() - k; i++) { 
			String line = "";
			int token = i; //token takes in the starting point(i) and is used to move forward in the loop
			for (int j = k; j > 0; j--) { //Loop uses k to determine the iterations
				line = line + elements.get(token) + " "; //Concatenates elements into a single line
				token++;
			}
			S1.add(line.hashCode());
		}
		return S1;
	}

	//Takes in an array of Integers and returns an array of only the unique values
	public static ArrayList<Integer> createUniques(ArrayList<Integer> input) {
		ArrayList<Integer> dupeCodes = input;
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 0; i < dupeCodes.size(); i++) {
			set.add(dupeCodes.get(i));
		}
		ArrayList<Integer> uniqueCodes = new ArrayList<Integer>(set);
		
		return uniqueCodes;
	}

	//Takes in the array of hashCode arrays and prints out their similarity calculations
	public static void calculateSim(ArrayList<ArrayList<Integer>> input) {
		for(int j = 0; j < input.size() - 1; j++) {
			ArrayList<Integer> a = input.get(j);
			int compCount = 0;
			for (int s = j + 1; s < input.size(); s++) {
				ArrayList<Integer> b = input.get(s);
				ArrayList<Integer> c = new ArrayList<Integer>();
				for (Integer temp : a) { 
		            if (b.contains(temp)) { 
		                c.add(temp); 
		            } 
		        }
				compCount = c.size();
				int uniqueCount = (a.size() + b.size())-compCount;
				double compRatio = ((double)compCount/uniqueCount)*100;
				System.out.println(texts.get(j) + "/" + texts.get(s) + ": " + compCount + "/" + uniqueCount + " or " + df2.format(compRatio) + "%");
			}
		}
	}
}

