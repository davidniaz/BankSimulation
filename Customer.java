/**
 * This class represents a customer going to the bank.
 * 
 * @author David Niaz
 * @author Benjamin John
 * 
 * @version 2014.11.06
 */

public class Customer implements Comparable<Customer> {

	private int arrivalTime;
	private int transactionTime;
	private boolean walkUp;
	private int nextSignificantTime;
	private boolean hasBeenHelped;

	public Customer(int arrivalTime, int transactionTime, boolean walkUp) {
		this.arrivalTime = arrivalTime;
		this.transactionTime = transactionTime;
		this.walkUp = walkUp;
		this.nextSignificantTime = arrivalTime;
		this.hasBeenHelped = false;
	}

	/**
	 * Getter method for whether customer has been helped or not
	 * 
	 * @return whether customer has been helped or not
	 */
	public boolean hasBeenHelped() {
		return this.hasBeenHelped;
	}

	/**
	 * Setter method for whether customer has been helped or not
	 */
	public void setHasBeenHelped(boolean hasBeenHelped) {
		this.hasBeenHelped = hasBeenHelped;
	}

	/**
	 * Getter method for the Arrival Time
	 * 
	 * @return arrival time
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Getter method for the transaction time
	 * 
	 * @return transaction time
	 */
	public int getTransactionTime() {
		return transactionTime;
	}

	/**
	 * Gets whether the customer is a walkup or not
	 * 
	 * @return boolean of Walkup
	 */
	public boolean isWalkUp() {
		return walkUp;
	}

	/**
	 * Getter method for the next Significant time
	 * 
	 * @return transaction time
	 */
	public int getnextSignificantTime() {
		return this.nextSignificantTime;
	}

	/**
	 * 
	 * Setter method for the next Significant time
	 * 
	 */
	public void setnextSignificantTime(int passedNextSignificantTime) {
		this.nextSignificantTime = passedNextSignificantTime;
	}

	/**
	 * Compares the current customer and another customer and returns an int of
	 * the difference of the nextSignificantTimes
	 * 
	 * @param compareCustomer
	 *            Customer object passed
	 * @return an int containing the difference in significant times
	 * 
	 */
	public int compareTo(Customer compareCustomer) {
		int compareSignificantTime = compareCustomer.getnextSignificantTime();
		return this.nextSignificantTime - compareSignificantTime;// Descending
																	// order
	}

}// Customer Class