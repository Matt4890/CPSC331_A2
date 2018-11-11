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

			int leftHeight	= this.left == null ? -1 : this.left.height;
			int rightHeight	= this.right == null ? -1 : this.right.height;
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
	 * @param  k                    The key to search for in the tree.
	 * @param  x                      The node to start searching at.
	 * @return                        The value of the node with the specified key.
	 * @throws NoSuchElementException If the key is not in the tree,
	 */
	private V search (K k, AVLNode x) throws NoSuchElementException {

		if (x == null) {

			throw new NoSuchElementException();

		} else if (x.key.compareTo(k) == -1) {

			return search(k, x.left());

		} else if (x.key().compareTo(k) == 1) {

			return search(k, x.right());

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
		AVLNode y	= x.right;
		AVLNode z	= y.left;
		AVLNode p	= x.parent;

		// Handling Children
		x.right		= z;
		if (z != null){
			z.parent	= x;
		}
		y.left		= x;
		x.parent	= y;

		// Handling The Parent
		if (x == root) {

			root 		= y;
			y.parent	= null;

		} else {

			if (x.key.compareTo(p.key) == -1) { // If x was a left child
				p.left	= y;
			} else {
				p.right	= y;
			}

			y.parent = p;

		}

		x.updateHeight();
		y.updateHeight();

	}

	/**
	 * Performs a right rotation at a node.
	 * @param x The node at which to perform a right rotation.
	 */
	private void rotateRight (AVLNode x) {

		// Aliases
		AVLNode y	= x.left;
		AVLNode z	= y.right;
		AVLNode p	= x.parent;

		// Handling Children
		x.left		= z;
		if (z != null){
			z.parent	= x;
		}
		y.right		= x;
		x.parent	= y;

		// Handling The Parent
		if (x == root) {

			root 		= y;
			y.parent	= null;

		} else {

			if (x.key.compareTo(p.key) == -1) { // If x was a left child
				p.left	= y;
			} else {
				p.right	= y;
			}

			y.parent = p;

		}

		x.updateHeight();
		y.updateHeight();

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
		int result = k.compareTo(x.key);

		if (result == -1) {

			if (x.left == null){
				y			= new AVLNode(k, v);
				x.left		= y;
				y.parent	= x;
			} else {
				change(k, v, x.left);
			}

		} else if (result == 1) {

			if (x.right == null){
				y			= new AVLNode(k, v);
				x.right		= y;
				y.parent	= x;
			} else {
				change(k, v, x.right);
			}

		} else { // result == 0

			x.value	= v;

		}

		// Go up the tree, starting at the inserted node,
		// balancing any nodes that aren't balanced properly.
		while (y != null) {
			y.updateHeight();
			if (y.balanceFactor() == 2 || y.balanceFactor() == -2) {
				balanceNode(y);
			}
			y = y.parent;
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

		if (x == null) {
			throw new NoSuchElementException();
		}

		int result = k.compareTo(x.key);
		V v = x.value;

		if (result == -1) {
			deleteFromSubtree(k, x.left);
		} else if (result == 1) {
			deleteFromSubtree(k, x.right);
		} else { // result == 0
			deleteNode(x);
		}
		return v;

	}

	/**
	 * Deletes a node from the tree.
	 * @param x The node to delete from the tree.
	 */
	private void deleteNode (AVLNode x) {

		AVLNode p = null;

		if (x.left== null && x.right == null) { // x has no children

			if (x == root) {
				root = null;
			} else {
				p = x.parent;
				if (x.key.compareTo(p.key) == -1) {
					p.left = null;
				} else {
					p.right = null;
				}
				x.parent = null;
			}

		} else if (x.left == null || x.right== null) { // x has 1 child

			AVLNode c = null;
			if (x.left == null) {
				c = x.right;
				x.right = null;
			} else {
				c = x.left;
				x.left = null;
			}

			if (x == root) {
				c.parent = null;
				root = c;
			} else {
				p = x.parent;
				c.parent = p;
				if (x.key.compareTo(p.key) == -1) {
					p.left = c;
				} else {
					p.right = c;
				}
				x.parent = null;
			}

		} else { // x has 2 children

			AVLNode s = successor(x);
			x.key = s.key;
			x.value = s.value;
			deleteNode(s);

		}

		// Go up the tree, starting at the inserted node,
		// balancing any nodes that aren't balanced properly.
		while (p != null) {
			boolean equalCase = false;
			p.updateHeight();
			if (p.balanceFactor() == 2 || p.balanceFactor() == -2) {
				equalCase = balanceNode(p);
			}
			if (equalCase) {
				break;
			}
			p = p.parent;
		}

	}

	/**
	 * Gets the appropriate successor of a node.
	 * @param  x The node to get the successor of.
	 * @return   The appropriate successor node.
	 */
	private AVLNode successor (AVLNode x) {

		x = x.right;

		while (x.left != null) {
			x = x.left;
		}

		return x;

	}

	/**
	 * Balances a node after an insertion by performing the rotations
	 * described in the assignment to solve the left-left, left-right,
	 * right-left, and right-right cases.
	 * @param x The deepest node with a balance factor of -2 or 2.
	 * @return 	True if a *-Equal case was balanced. False otherwise.
	 */
	private boolean balanceNode(AVLNode x) {

		int result = -3; // To prevent false positives (unattainable value)

		if (x.balanceFactor() == 2) {

			result = x.left.balanceFactor();
			if (result == -1) {
				rotateLeft(x.left);
			}
			rotateRight(x);

		} else if (x.balanceFactor() == -2) {

			result = x.right.balanceFactor();
			if (result == 1) {
				rotateRight(x.right);
			}
			rotateLeft(x);

		}

		return result == 0;

	}

}
