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

		/**
		 * Updates the local height variable with one more than the
		 * max of its children's heights.
		 */
		void updateHeight() {

			int leftHeight	= this.left == null ? -1 : this.left.height();
			int rightHeight	= this.right == null ? -1 : this.right.height();
			this.height		= leftHeight >= rightHeight ? leftHeight + 1 : rightHeight + 1;

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

	/**
	 * Searches for a specified key in the tree and returns its value,
	 * throwing an exception if the key is not in the tree.
	 * @param  key                    The key to search for in the tree.
	 * @param  x                      The node to start searching at.
	 * @return                        The value of the node with the specified key.
	 * @throws NoSuchElementException If the key is not in the tree,
	 */
	private V search (K key, AVLNode x) throws NoSuchElementException {

		if (x == null) {

			throw new NoSuchElementException();

		} else if (x.key().compareTo(key) == -1) {

			return search(key, x.left());

		} else if (x.key().compareTo(key) == 1) {

			return search(key, x.right());

		} else { // x.key().compareTo(key) == 0

			return x.value();

		}

	}

	/**
	 * Performs a left rotation at a node.
	 * @param x The node at which to perform a left rotation.
	 */
	private void rotateLeft (AVLNode x) {

		// Aliases
		AVLNode y	= x.right();
		AVLNode z	= y.left();
		AVLNode p	= x.parent();

		// Handling Children
		x.right		= z;
		z.parent	= x;
		y.left		= x;
		x.parent	= y;

		// Handling The Parent
		if (x == root) {

			root 		= y;
			y.parent	= null;

		} else {

			if (x.key().compareTo(p.key()) == -1) { // If x was a left child
				p.left	= y;
			} else {
				p.right	= y;
			}

			y.parent = p;

		}

	}

	/**
	 * Performs a right rotation at a node.
	 * @param x The node at which to perform a right rotation.
	 */
	private void rotateRight (AVLNode x) {

		// Aliases
		AVLNode y	= x.left();
		AVLNode z	= y.right();
		AVLNode p	= x.parent();

		// Handling Children
		x.left		= z;
		z.parent	= x;
		y.right		= x;
		x.parent	= y;

		// Handling The Parent
		if (x == root) {

			root 		= y;
			y.parent	= null;

		} else {

			if (x.key().compareTo(p.key()) == -1) { // If x was a left child
				p.left	= y;
			} else {
				p.right	= y;
			}

			y.parent = p;

		}

	}

	/**
	 * Sets the specified key in the tree to the specified value.
	 * @param k The key at which to set the value.
	 * @param v The value which should be stored with the key.
	 */
	public void set(K k, V v) {

		if (root == null) {

			root = new AVLNode(k, v);

		} else {

			change(k, v, root);

		}

	}

	/**
	 * Changes the specified key in the tree to the specified value,
	 * inserting a node with the key-value pair if none is found.
	 * @param k The key at which to set the value.
	 * @param v The value which should be stored with the key.
	 * @param x The node to start searching at.
	 */
	private void change (K k, V v, AVLNode x) {

		AVLNode y = null;

		if (x.key().compareTo(k) == -1) {

			if (x.left() == null){
				y			= new AVLNode(k, v);
				x.left		= y;
				y.parent	= x;
			} else {
				change(k, v, x.left());
			}

		} else if (x.key().compareTo(k) == 1) {

			if (x.right() == null){
				y			= new AVLNode(k, v);
				x.right		= y;
				y.parent	= x;
			} else {
				change(k, v, x.right());
			}

		} else { // x.key().compareTo(key) == 0

			x.value	= v;

		}

		if (y != null) { // If a node was inserted

			// Go up the tree, starting at the inserted node,
			// balancing any nodes that aren't balanced properly.
			while (y != root) {
				y.updateHeight();
				if (y.balanceFactor() == 2 || y.balanceFactor() == -2) {
					balanceNode(y);
				}
				y = y.parent();
			}

		}

	}

	/**
	 * Removes the specified key from the tree.
	 * @param  k                      The key to remove.
	 * @return                        The value stored at key k.
	 * @throws NoSuchElementException If the key was not in the tree.
	 */
	public V remove (K k) throws NoSuchElementException {

		return deleteFromSubtree(k, root);

	}

	/**
	 * Deletes a node with key k from a subtree.
	 * @param  k                      The key of the node to delete.
	 * @param  x                      The root of the subtree to delete the node from.
	 * @return                        The value stored at key k.
	 * @throws NoSuchElementException If the key was not in the tree.
	 */
	private V deleteFromSubtree(K k, AVLNode x) throws NoSuchElementException {

		return null;          // This line must be replaced.

	}

	/**
	 * Deletes a node from the tree.
	 * @param x The node to delete from the tree.
	 */
	private void deleteNode (AVLNode x) {

	}

	/**
	 * Gets the appropriate successor of a node.
	 * @param  x The node to get the successor of.
	 * @return   The appropriate successor node.
	 */
	private AVLNode successor (AVLNode x) {

		return null;      // This line must be replaced.

	}

	/**
	 * Balances a node after an insertion by performing the rotations
	 * described in the assignment to solve the left-left, left-right,
	 * right-left, and right-right cases.
	 * @param x The deepest node with a balance factor of -2 or 2.
	 */
	private void balanceNode(AVLNode x) {

		if (x.balanceFactor() == 2) {

			if (x.left().balanceFactor() == 1) {		// Left-Left Case
				rotateRight(x);
			} else { // x.left().balanceFactor() == -1	// Left-Right Case
				rotateLeft(x.left());
				rotateRight(x);
			}

		} else if (x.balanceFactor() == -2) {

			if (x.right().balanceFactor() == 1) {		// Right-Left Case
				rotateRight(x.right());
				rotateLeft(x);
			} else { // x.right().balanceFactor() == -1	// Right-Right Case
				rotateLeft(x);
			}

		}

	}

}
