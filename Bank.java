import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * This class represents the bank and simulates the interactions between
 * customers and tellers in the bank.
 * 
 * @author David Niaz
 * @author Benjamin John
 * 
 * @version 2014.11.06
 */
public class Bank {

	private int globalTime;
	private int numberOfTellers;
	private boolean hasDriveThru;
	private ArrayList<Teller> tellerList;
	private PriorityQueue<Customer> sortedCustomerList;
	private DriveThru driveThru;
	private int numberOfCustomers;
	private int totalWaitTime;
	private ArrayList<Integer> allWaitTimes;
	private boolean driveThruIsBeingHelped;

	public Bank(int numberOfTellers, boolean hasDriveThru,
			ArrayList<Customer> customerList) {

		if (hasDriveThru == true) {
			this.driveThru = new DriveThru();
		}

		this.numberOfTellers = numberOfTellers;
		this.hasDriveThru = hasDriveThru;
		this.numberOfCustomers = customerList.size();
		this.totalWaitTime = 0;
		this.sortedCustomerList = new PriorityQueue<Customer>();
		this.tellerList = new ArrayList<Teller>();
		this.allWaitTimes = new ArrayList<Integer>();

		for (int i = 0; i < this.numberOfTellers; i++) {
			this.tellerList.add(new Teller()); // create a new Teller and add it
												// to the list
		}

		for (int i = 0; i < customerList.size(); i++) // enter all customer into
														// priority queue
		{
			this.sortedCustomerList.offer(customerList.get(i));
		}

	}

	/**
	 * This method starts the simulation and continues until all customers have
	 * completed their transactions.
	 */
	public void startSimulation() {

		// Continue simulation until all customers have completed their
		// transactions
		while (this.sortedCustomerList.peek() != null) {
			Customer currentCustomer = this.sortedCustomerList.poll();

			globalTime = currentCustomer.getnextSignificantTime();

			// Handle customer arrivals (both walk-up and drive-up)
			if (currentCustomer.hasBeenHelped() == false) {
				customerEntersBank(currentCustomer);
			}

			// Available tellers help the next Customer in line
			for (int i = 0; i < this.numberOfTellers; i++) {

				Teller teller = tellerList.get(i);

				// Customer leaves bank if their transaction time is up
				if (teller.getCurrentCustomerBeingHelped() == currentCustomer) {
					if (currentCustomer.isWalkUp() == false) {
						this.driveThruIsBeingHelped = false;
					}
					teller.setCurrentCustomerBeingHelped(null);
					teller.setTimeUntilNotBusy(globalTime);
				}

				// Teller checks the driveThru queue before helping next person
				// in line.
				if (hasDriveThru && this.driveThruIsBeingHelped == false) {
					checkDriveThru(teller);
				}

				// Teller helps the next person in line if the teller is not
				// busy.
				helpNextCustomer(teller);

			}// for

		}// while

		for (int i = 0; i < this.tellerList.size(); i++) {
			this.tellerList.get(i).addToIdleTime(
					globalTime - tellerList.get(i).getTimeUntilNotBusy());
		}

	}// startSimulation

	/**
	 * This method takes the passed Customer object and inserts it into it's
	 * appropriate queue.
	 * 
	 * @param currentCustomer
	 *            The Customer Object to be processed
	 */
	public void customerEntersBank(Customer currentCustomer) {
		if (currentCustomer.isWalkUp() == false && hasDriveThru) // If the customer drives up at the drive thru

		{
			driveThru.addToQueue(currentCustomer);
		} else {
			// Find the shortest Line
			
			Teller shortestLine = null;
			for (int i = 0; i < this.numberOfTellers; i++) {
				if (tellerList.get(i).getIsBusy() == false
						&& tellerList.get(i).getQueueSize() == 0)// If there is a teller who isn't busy and has no one in their line
				{
					shortestLine = tellerList.get(i);
				}
			}
			
			if(shortestLine == null) {
				shortestLine = tellerList.get(0);
				for (int i = 0; i < this.numberOfTellers; i++) {
					
					if (tellerList.get(i).getQueueSize() < shortestLine.getQueueSize())// Otherwise find the teller with the shortest line
					{
						shortestLine = tellerList.get(i);
					}
				}
			}

			shortestLine.addToQueue(currentCustomer);

		}// else
	}

	/**
	 * This method is called when the teller needs to check whether or not there
	 * is a customer waiting at the drive thru window.
	 * 
	 * @param teller
	 *            The teller who is checking the drive thru window.
	 */
	public void checkDriveThru(Teller teller) {
		// if there is someone at the drive thru, help that person.
		if (driveThru.getQueue().peek() != null
				&& teller.getCurrentCustomerBeingHelped() == null) {
			this.driveThruIsBeingHelped = true;
			Customer driveThruCustomer;
			driveThruCustomer = driveThru.removeFromQueue();
			teller.setCurrentCustomerBeingHelped(driveThruCustomer);
			teller.addNumberOfCustomersHelped();
			teller.addToIdleTime(this.globalTime - teller.getTimeUntilNotBusy());
			this.totalWaitTime += (globalTime - driveThruCustomer
					.getArrivalTime());
			allWaitTimes.add((globalTime - driveThruCustomer.getArrivalTime()));
			teller.setBusy(true);
			teller.setTimeUntilNotBusy(globalTime
					+ driveThruCustomer.getTransactionTime());
			driveThruCustomer.setnextSignificantTime(globalTime
					+ driveThruCustomer.getTransactionTime());
			driveThruCustomer.setHasBeenHelped(true);
			sortedCustomerList.offer(driveThruCustomer);// Add the customer back
														// into the priority
														// queue (with their
														// nextSignificantTime
														// updated)
		}
	}

	/**
	 * This method is called when the teller has finished helping a customer and
	 * needs to help the next customer in line.
	 * 
	 * @param teller
	 *            The teller that is doing the transactions.
	 */
	public void helpNextCustomer(Teller teller) {
		if (teller.getQueueSize() > 0
				&& teller.getCurrentCustomerBeingHelped() == null) {
			Customer walkupCustomer;
			walkupCustomer = teller.removeFromQueue();
			teller.setCurrentCustomerBeingHelped(walkupCustomer);
			teller.addToIdleTime(this.globalTime - teller.getTimeUntilNotBusy());
			teller.addNumberOfCustomersHelped();
			this.totalWaitTime += (globalTime - walkupCustomer.getArrivalTime());
			allWaitTimes.add((globalTime - walkupCustomer.getArrivalTime()));
			teller.setBusy(true);
			teller.setTimeUntilNotBusy(globalTime
					+ walkupCustomer.getTransactionTime());
			walkupCustomer.setnextSignificantTime(globalTime
					+ walkupCustomer.getTransactionTime());
			walkupCustomer.setHasBeenHelped(true);
			sortedCustomerList.offer(walkupCustomer);
		}
	}

	/**
	 * This method prints out the results from the simulation.
	 */
	public void printResults() {
		System.out.println("\nRESULTS:\n");

		// The total elapsed time, in seconds
		System.out.println("Total Elapsed Time: 			" + this.globalTime
				+ " seconds.");

		// The total number of customers helped
		System.out.println("Total Number of Customers Helped: 	"
				+ this.numberOfCustomers + " customers.");

		// The average customer waiting time, include the standard deviation
		double sdOfWaitTimes = findStandardDeviation(allWaitTimes);

		System.out.println("\nAverage Customer Wait Time: 		"
				+ this.totalWaitTime / this.numberOfCustomers + " seconds.");
		System.out.println("Standard Deviation of Wait Times:       "
				+ sdOfWaitTimes + " seconds.\n");

		// For each teller, the number of customers helped
		for (int i = 0; i < this.numberOfTellers; i++) {
			System.out.println("Teller " + i + " helped "
					+ tellerList.get(i).getNumberOfCustomersHelped()
					+ " customers.");
		}

		// For each teller, the percentage of time they were idle, include the
		// standard deviation
		System.out.print("\n");
		for (int i = 0; i < this.numberOfTellers; i++) {
			double sdOfIdleTimes = findStandardDeviation(tellerList.get(i).getIdleTimes());
			double idlePercentage = Math.round(tellerList.get(i).getTotalIdleTime()
					* 100.0 / globalTime);
			if(sdOfIdleTimes >= 0) {
				System.out.println("Teller " + i + " was idle " + idlePercentage
						+ "% of the time, with a standard Deviation of " + sdOfIdleTimes + " seconds.");
			}
			else {
				System.out.println("Teller " + i + " was idle " + idlePercentage
						+ "% of the time, with a standard Deviation of " + "N/A" + " seconds.");
			}
		}
	}

	/**
	 * This method finds the standard deviation of a set of integers.
	 * 
	 * @param a
	 *            The list of integers
	 * @return The standard Deviation
	 */
	public double findStandardDeviation(ArrayList<Integer> a) {
		if(a.size() == 1) {return -1;}
		int sum = 0;
		double mean = mean(a);

		for (Integer i : a)
			sum += Math.pow((i - mean), 2);
		return Math.sqrt(sum / (a.size() - 1)); 
	}

	/**
	 * This method finds the mean of a list of integers.
	 * 
	 * @param a
	 *            The list of integers
	 * @return The mean
	 */
	public double mean(ArrayList<Integer> a) {
		int sum = sum(a);
		double mean = 0;
		mean = sum / (a.size() * 1.0);
		return mean;
	}

	/**
	 * This method finds the sum of a list of integers
	 * 
	 * @param a
	 *            The list of integers
	 * @return The Sum
	 */
	public int sum(ArrayList<Integer> a) {
		if (a.size() > 0) {
			int sum = 0;

			for (Integer i : a) {
				sum += i;
			}
			return sum;
		}
		return 0;
	}

}// bank

