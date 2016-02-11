package alda.heap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a D-ary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Theo Walther, altering the work of Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>> {
	private static final int DEFAULT_CAPACITY = 100;
	private int d;

	private int currentSize; // Number of elements in heap

	private AnyType[] array; // The heap array

	/**
	 * Construct a binary heap.
	 */
	public DHeap() {
		this(2);
	}

	/**
	 * Construct a D-heap.
	 * 
	 * @param d
	 *            the number of children per node.
	 */
	public DHeap(int d) {
		if (d < 2)
			throw new IllegalArgumentException();
		currentSize = 0;
		array = (AnyType[]) new Comparable[DEFAULT_CAPACITY + 1];
		this.d = d;
	}

	/**
	 * Construct the binary heap given an array of items.
	 */
	// public DHeap(AnyType[] items) {
	// currentSize = items.length;
	// array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
	//
	// int i = 1;
	// for (AnyType item : items)
	// array[i++] = item;
	// buildHeap();
	// }

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * d + 1);

		// Percolate up
		int hole = ++currentSize;
		for (; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole))
			array[hole] = array[parentIndex(hole)];
		array[hole] = x;
	}

	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 0; i < old.length; i++)
			array[i] = old[i];
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}

	/**
	 * Establish heap order property from an arbitrary arrangement of items.
	 * Runs in linear time.
	 */
	private void buildHeap() {
		for (int i = currentSize / d; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		int child = firstChildIndex(hole);
		AnyType tmp = array[hole];

		for (; firstChildIndex(hole) <= currentSize; hole = child) {
			child = firstChildIndex(hole);
			int tempChild = firstChildIndex(hole);
			// if (child != currentSize
			// && array[child + 1].compareTo(array[child]) < 0)
			for (int i = 0; i < d && tempChild != currentSize; i++, tempChild++) {
				if (array[child + 1].compareTo(array[child]) < 0) {
					child++;
				}
			}
			if (array[child].compareTo(tmp) < 0)
				array[hole] = array[child];
			else
				break;
			// array[hole] = tmp;
		}
	}

	// Test program
	// public static void main(String[] args) {
	// int numItems = 10000;
	// DHeap<Integer> h = new DHeap<>();
	// int i = 37;
	//
	// for (i = 37; i != 0; i = (i + 37) % numItems)
	// h.insert(i);
	// for (i = 1; i < numItems; i++)
	// if (h.deleteMin() != i)
	// System.out.println("Oops! " + i);
	// }

	public int parentIndex(int child) {
		if (child <= 1)
			throw new IllegalArgumentException();
		return (child - 2) / d + 1;
	}

	public int firstChildIndex(int parent) {
		if (parent <= 0)
			throw new IllegalArgumentException();
		return (parent - 1) * d + 2;
	}

	public int size() {
		return currentSize;
	}

	public Object get(int i) {
		return array[i];
	}
}