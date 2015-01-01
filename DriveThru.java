import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a drive thru.
 * 
 * @author David Niaz
 * @author Benjamin John
 * 
 * @version 2014.11.06
 */
public class DriveThru {

	private Queue<Customer> queue;

	public DriveThru() {
		this.queue = new LinkedList<Customer>();
	}

	/**
	 * This method adds a customer to the drivethru queue.
	 * 
	 * @param customer
	 *            Customer to be added.
	 */
	public void addToQueue(Customer customer) {
		queue.add(customer);
	}

	/**
	 * This method is a getter for the queue.
	 * 
	 * @return The global queue for the DriveThru object.
	 */
	public Queue<Customer> getQueue() {
		return queue;
	}

	/**
	 * This method removes a customer from the queue.
	 * 
	 * @return The customer that was removed from the queue.
	 */
	public Customer removeFromQueue() {
		return queue.poll();
	}

}
