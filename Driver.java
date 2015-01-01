import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * 
 * This is the driver class.
 * 
 * @author David Niaz
 * @author Benjamin John
 * 
 * @version 2014.11.06
 */

public class Driver {

	public static void main(String[] args) {

		ArrayList<Customer> customerList = readFile(); // Read the list of
														// customers

		int numberOfTellers = getNumberOfTellersFromUser(); // Get number of
															// tellers from user
		boolean hasDriveThru = getHasDriveThruFromUser(); // Get whether there
															// is a drive thru

		// Create a new bank
		Bank theBank = new Bank(numberOfTellers, hasDriveThru, customerList);
		theBank.startSimulation();
		theBank.printResults();
	}

	public static boolean getHasDriveThruFromUser() {

		String hasDriveThru;

		Scanner in = new Scanner(System.in);

		System.out.println("Is there a drive-thru? Enter true or false.");
		hasDriveThru = in.nextLine();

		return Boolean.parseBoolean(hasDriveThru);

	}

	public static int getNumberOfTellersFromUser() {

		int a;

		Scanner in = new Scanner(System.in);

		System.out.println("Enter the number of tellers.");
		a = in.nextInt();

		return a;

	}

	public static ArrayList<Customer> readFile() {

		/**
		 * This program demonstrates how to read from text files. It brings up a
		 * file-selection dialog box and, if the user selects a file, copies the
		 * file to the screen, one line at a time.
		 */

		ArrayList<Customer> customerList = new ArrayList<Customer>();
		// Create a file-selection dialog object
		JFileChooser chooser = new JFileChooser();

		try {
			// Display the dialog, and wait for return value. If they cancel
			// out of the selection, throw an error -- no file to read
			if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
				throw new Error("Input file not selected");

			// Grab the selected File info
			File inFile = chooser.getSelectedFile();

			// Create a scanner, and attach it to the file. Loop through
			// line at a time and print the contents to the screen.
			Scanner fileScanner = new Scanner(inFile);
			while (fileScanner.hasNext()) {
				String line = fileScanner.nextLine();

				String[] array = line.split("\t");

				int currentCustomerArrivalTime = Integer.parseInt(array[0]);
				int currentCustomerTransactionTime = Integer.parseInt(array[1]);
				boolean currentCustomerWalkUp = Boolean.parseBoolean(array[2]);

				Customer currentCustomer = new Customer(
						currentCustomerArrivalTime,
						currentCustomerTransactionTime, currentCustomerWalkUp);
				customerList.add(currentCustomer);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Data file not found.");
		} catch (Exception e) {
			System.err.println("A mysterious error occurred.");
			e.printStackTrace(System.err);
		}

		return customerList;
	}// readFile()

}// Driver Class
