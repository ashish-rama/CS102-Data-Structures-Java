package llHomework;

import java.util.ArrayList;

/**
 * LLHomeworkFunctions; CS102 Data Structures (Evan Korth) Assignment 3 (Part 1 and 2)
 * @author Ashish Ramachandran (ar3986)
 */
public class LLHomeworkFunctions {

	static public void main(String [] args) {
	}
	
	/**
	 * Checks if two passed LLNode<T> lists are equal (all elements are the same and in order, lists same length)
	 * @param list1 the first list
	 * @param list2 the second list
	 * @return true if the lists are equal.  Assume both lists terminate.
	 */
	static public <T> boolean equalLists(LLNode<T> list1, LLNode<T> list2) {
		//loops while lists are not pointing to a null location; for each iteration, reassign to the next LLNode<T> in the list
		for(; list1 != null && list2 != null; list1 = list1.link, list2 = list2.link) {
			if(!list1.info.equals(list2.info))
				return false;
		}
		
		//if the for loop ended early because of different sizes of lists, check if they both ended as null (same size)
		if(list1 == null && list2 == null)
			return true;
		return false;
	}
	
	/**
	 * Checks if a passed LLNode<T> list terminates or is it a recursive list
	 * @param list1 the list to evaluate
	 * @return true if the list eventually terminates, and false if the list points back at one of it's
	 *  previous nodes.
	 */
	static public <T> boolean terminates(LLNode<T> list) {
		//creates an ArrayList of LLNode<T> that we have already seen. either we will come upon it again (list does not terminate) or
		//the list reaches null (list does terminate)
		ArrayList<LLNode<T>> elementsSeen = new ArrayList<LLNode<T>>();
		for(; list != null; list = list.link) {
			for(LLNode<T> node : elementsSeen) {
				if(node == list)
					return false;
			}
			elementsSeen.add(list);
		}
		return true;
	}
}