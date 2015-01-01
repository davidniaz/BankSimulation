import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author David Niaz
 * @author Benjamin John
 * 
 * @version 2014.11.06
 */

public class Teller {

	private Queue<Customer> queue;
	private boolean isBusy;
	private Customer currentCustomerBeingHelped;
	private int timeUntilNotBusy;
	private int numberOfCustomersHelped;
	private ArrayList<Integer> idleTime;

	public Teller() {
		this.queue = new LinkedList<Customer>();
		this.numberOfCustomersHelped = 0;
		this.idleTime = new ArrayList<Integer>();
		this.isBusy = false;
		this.currentCustomerBeingHelped = null;
	}

	/**
	 * This method increments the numberOfCustomersHelped for the teller object.
	 */
	public void addNumberOfCustomersHelped() {
		this.numberOfCustomersHelped = numberOfCustomersHelped + 1;
	}

	/**
	 * This method adds to the Idle Time of the teller.
	 * 
	 * @param passedTime
	 *            This is the amount of time to be added.
	 */
	public void addToIdleTime(int passedTime) {
		this.idleTime.add(passedTime);
	}

	/**
	 * This method adds a customer to the teller's queue.
	 * 
	 * @param customer
	 *            Customer to be added to the teller's queue.
	 */
	public void addToQueue(Customer customer) {
		queue.add(customer);
	}

	/**
	 * This method returns the current customer being helped.
	 * 
	 * @return The current customer being helped.
	 */
	public Customer getCurrentCustomerBeingHelped() {
		return currentCustomerBeingHelped;
	}

	/**
	 * This method returns the List of all idleTimes.
	 * 
	 * @return The idle times for the teller.
	 */
	public ArrayList<Integer> getIdleTimes() {
		return idleTime;
	}
	
	/**
	 * This method returns the total of all the idleTimes.
	 * 
	 * @return The total idle time for the teller.
	 */
	public int getTotalIdleTime() {
		int totalTime = 0;
		for(int i = 0; i < idleTime.size(); i++) {
			totalTime += idleTime.get(i);
		}
		return totalTime;
	}

	/**
	 * This method returns whether or not the teller is busy.
	 * 
	 * @return A boolean of whether or not the teller is busy.
	 */
	public boolean getIsBusy() {
		return this.isBusy;
	}

	/**
	 * This method gets the number of customers being helped.
	 * 
	 * @return The number of customers being helped.
	 */
	public int getNumberOfCustomersHelped() {
		return numberOfCustomersHelped;
	}

	/**
	 * This method returns the teller's queue.
	 * 
	 * @return The teller's queue.
	 */
	public Queue<Customer> getQueue() {
		return queue;
	}

	/**
	 * This method returns the teller's queue size.
	 * 
	 * @return The teller's queue size.
	 */
	public int getQueueSize() {
		return this.queue.size();
	}

	/**
	 * This method returns an int of the time until the teller is no longer
	 * busy.
	 * 
	 * @return The time until the teller is no longer busy.
	 */
	public int getTimeUntilNotBusy() {
		return timeUntilNotBusy;
	}

	/**
	 * This method removes a customer from the queue and returns it.
	 * 
	 * @return Customer that has been removed from the queue.
	 */
	public Customer removeFromQueue() {
		return queue.poll();
	}

	/**
	 * This method sets whether the teller is busy or not.
	 * 
	 * @param isBusy
	 *            boolean of whether the teller is busy or not to be set.
	 */
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	/**
	 * This method sets the current customer being helped.
	 * 
	 * @param A
	 *            customer object of the current customer being helped.
	 */
	public void setCurrentCustomerBeingHelped(
			Customer currentCustomerBeingHelped) {
		this.currentCustomerBeingHelped = currentCustomerBeingHelped;
	}

	/**
	 * This method sets the time until the teller is not busy.
	 * 
	 * @param timeUntilNotBusy
	 *            The time until the teller is not busy.
	 */
	public void setTimeUntilNotBusy(int timeUntilNotBusy) {
		this.timeUntilNotBusy = timeUntilNotBusy;
	}

}
