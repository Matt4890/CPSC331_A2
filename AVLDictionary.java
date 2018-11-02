package cpsc331.assignment2;

import java.util.NoSuchElementException;
import cpsc331.collections.Dictionary;

/**
*
* Provides an implementation of a Dictionary using an AVL tree.
*
*/

// AVLDictionary Invariant:
//
// This AVL tree has nodes storing the ordered pairs in the
// Dictionary being represented. Thus it is a binary search
// tree such that all nodes have balance factors -1, 0, and 1.
// Furthermore, if the AVL tree is not empty, then
// - all the nodes in the left subtree include keys that are less
//   than the key stored at the root,
// - all the nodes in the right subtree include keys that are
//   greater than the nodes at the root, and
// - the left and right subtrees are also AVLDictionary's, so
//   they also satisfy this invariant.

public class AVLDictionary<K extends Comparable<K>, V> implements Dictionary<K, V> {
														
	// Provides a node in this AVL tree
	
	class AVLNode {
	
		// Data Fields
		
		private K key;          // The key stored at the this node
		private V value;        // The value stored at this node
		private int height;     // The height of the subtree with this node as root
		private AVLNode left;   // The left child of this node
		private AVLNode right;  // The right child of this node
		private AVLNode parent; // The parent of this node
		
		// Constructor; constructs an AVLNode with a given key and value
		// whose left and right node and parent are initially null
		
		AVLNode(K k, V v) {
		
			key = k;
			value = v;
			height = 0;
			left = null;
			right = null;
			parent = null;
		
		}
		
		// Returns the key stored at this node
		
		K key() {
			return this.key;
		}
		
		// Returns the value stored at this node
		
		V value() {
			return this.value;
		}
		
		// Returns the height of this node
		
		int height() {
			return this.height;
		}
		
		// Returns the left child of this node
		
		AVLNode left() {
			return this.left;
		}
		
		// Returns the right child of this node
		
		AVLNode right() {
			return this.right;
		}
		
		AVLNode parent() {
			return this.parent;
		}
		
		// Returns the balance factor of each node.
		
		int balanceFactor() {
		
			int leftHeight;  // Will be the height of the left child
			int rightHeight; // Will be the height of the right child
			
			if (this.left == null) {
				leftHeight = -1;
			} else {
				leftHeight = (this.left).height;
			};
			
			if (this.right == null) {
				rightHeight = -1;
			} else {
				rightHeight = (this.right).height;
			};
			
			return leftHeight - rightHeight;
		
		}
	
	}
	
	// Data Fields
	
	private AVLNode root;
	
	/**
	*
	* Constructs an empty AVLDictionary<br><br>
	*
	* Precondition: None<br>
	* Postcondition: An emptyAVLDictioary (satisfying the above
	*                AVLDictionary Invariant) has been created.
	*
	*/
	
	public AVLDictionary() {
		root = null;
	}
	
	// Returns a reference to the root of this AVLDictionary
	
	AVLNode root() {
		return this.root;
	}
	
	// Implements the "get" method provided by Dictionary
	
	public V get (K key) throws NoSuchElementException {
	
		return search(key, root);
		
	}
	
	// Implements the required "search" method; to be supplied by
	// students
	
	private V search (K key, AVLNode x) throws NoSuchElementException {
	
		if (x == null) {

			throw new NoSuchElementException();
		
		} else if (x.key().compareTo(key) == 0) {

			return x.value();

		} else if (x.key().compareTo(key) == -1) {

			return search(key, x.left());

		} else { // x.key().compareTo(key) == 1

			return search(key, x.right());

		}
	
	}
	
	// Implements a left rotation at an input node; to be supplied
	// by students
	
	private void rotateLeft (AVLNode x) {
	
		AVLNode p	= x.parent();
		AVLNode y	= x.right();
		AVLNode t1	= x.left();
		AVLNode t2	= y.left();
		AVLNode t3	= y.right();

		boolean xWasLeftChild	= x.key().compareTo(p.key()) == -1;

		// The following code is unoptimized and shouldn't work due to the lack of setter methods, but might anyway due to privacy leaks
		x.left()	= t1;
		x.right()	= t2;
		y.left()	= x;
		y.right		= t3;
		if (xWasLeftChild) {
			p.left()	= y;
		} else {
			p.right()	= y;
		}
	
	}
	
	// Implements a right rotation at an input node; to be supplied
	// by students
	
	private void rotateRight (AVLNode x) {
	
		AVLNode p	= x.parent();
		AVLNode y	= x.left();
		AVLNode t1	= y.left();
		AVLNode t2	= y.right();
		AVLNode t3	= x.right();

		boolean xWasLeftChild	= x.key().compareTo(p.key()) == -1;

		// The following code is unoptimized and shouldn't work due to the lack of setter methods, but might anyway due to privacy leaks
		x.left()	= t2;
		x.right()	= t3;
		y.left()	= t1;
		y.right		= t3;
		if (xWasLeftChild) {
			p.left()	= y;
		} else {
			p.right()	= y;
		}
	
	}
	
	// Implements the "set" method supplied by Dictionary
	
	public void set(K k, V v) {
	
		if (root == null) {
		
			root = new AVLNode(k, v);
		
		} else {
		
			change(k, v, root);
		
		}
	
	}
	
	// Implements the required "change" method; to be supplied
	// by students
	
	private void change (K k, V v, AVLNode x) {
	
	}
	
	// Implements the "remove" method supplied by Dictionary
	
	public V remove (K k) throws NoSuchElementException {
	
		return deleteFromSubtree(k, root);
		
	}
	
	// Implements the required "deleteFromSubtree" method; to be
	// supplied by students
	
	private V deleteFromSubtree(K k, AVLNode x)
		throws NoSuchElementException {
		
		return null;          // This line must be replaced.
		
	}
	
	// Implements the required "deleteNode" method; to be supplied
	// by students
	
	private void deleteNode (AVLNode x) {
	
	}
	
	// Implements the required "successor" method; to be supplied
	// by students
	
	private AVLNode successor (AVLNode x) {
	
		return null;      // This line must be replaced.
	
	}
														
}
