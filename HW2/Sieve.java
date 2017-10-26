package HW2;

import java.util.Scanner;

/**
 * Sieve class based on the Sieve of Eratosthenes that computes all the prime numbers up to some number n
 * CS102: Data Structures Assignment 2
 * @author Ashish Ramachandran (ar3986)
 */
public class Sieve {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Sieve sieve = new Sieve();
		
		System.out.println("Please enter upper bound:");
		sieve.primesTo(scan.nextInt());
		scan.close();
	}
	
	/**
	 * Prints all the primes from 2 to <code>n</code>
	 * Logic: It finds all the primes by going from p = 2 to sqrt(n) and checks all elements in queue1 if they are
	 * divisible by p. If they are not divisible by p, then they are enqueued to queue2 (dequeued from queue1) until 
	 * queue1 is empty. The process repeats, evaluating the elements of queue2 (dequeuing elements until empty) with
	 * the iterated p. Repeats evaluation of opposite queue until p > sqrt(n). The remaining elements (all prime) are 
	 * added to a new queue of only prime numbers and all of the elements of this queue are printed.
	 * @param n the integer to evaluate to
	 */
	public void primesTo(int n) {
		if(n < 2) {
			System.out.println("Error: Input must be a number greater than 2.");
			return;
		} else if(n == 2) {
			System.out.println("Primes up to 2 are: 2");
			return;
		}
		//Create queue1, queue2, and primes queue; create boolean to help with organizing which queue is populated
		BoundedQueueInterface<Integer> numbers = new ArrayBndQueue<Integer>(n);
		BoundedQueueInterface<Integer> workingQueue = new ArrayBndQueue<Integer>(n);
		BoundedQueueInterface<Integer> primes = new ArrayBndQueue<Integer>(n);
		boolean populatedFirstQueue = true;
		
		//Populate the numbers queue
		for(int i = 2; i <= n; i++)
			numbers.enqueue(i);
		
		//Dequeues first number, p
		int p = numbers.dequeue();
		while(p <= Math.sqrt(n)) {
			//Puts p in primes queue
			primes.enqueue(p);
			
			//Determines which queue to populate (enqueue) and which queue to remove from (dequeue) based on
			//the boolean populatedFirstQueue
			BoundedQueueInterface<Integer> queueToPopulate = populatedFirstQueue ? workingQueue : numbers;
			BoundedQueueInterface<Integer> queuePopulatingFrom = populatedFirstQueue ? numbers : workingQueue;
			
			//Going through the queue, if the element is NOT divisible by p, add it to the other queue
			while(!queuePopulatingFrom.isEmpty()) {
				int element = queuePopulatingFrom.dequeue();
				if(element % p != 0)
					queueToPopulate.enqueue(element);
			}
			
			//Iterate/reset p value and flip boolean
			p = queueToPopulate.dequeue();
			populatedFirstQueue = !populatedFirstQueue;
		}
		//Add the last p value to the primes list to avoid an off-by-one error
		primes.enqueue(p);

		//Find the populated queue to read from to complete the final populating of primes queue
		BoundedQueueInterface<Integer> queueToReadFrom = numbers.isEmpty() ? workingQueue : numbers;
		
		//Put everything from populated queue into primes queue
		while(!queueToReadFrom.isEmpty())
			primes.enqueue(queueToReadFrom.dequeue());
		
		System.out.print("Primes up to " + n + " are: ");
		
		//Print all the primes from 2 to n
		while(!primes.isEmpty())
			System.out.print(primes.dequeue() + ", ");
	}

}
