# CPSC 331 - Assignment 2

## AVL Trees

* ***Height*** of a node --> Depth of the subtree with said node as root 
	* (Number of layers below the node)
* ***Balance Factor*** of a node --> Difference between the left and right subtree depths
	* Depth of left subtree - Depth of right subtree
	* Height of left child - Height of right child
	* Height of subtree is -1 if subtree is empty
* ***AVL Tree*** --> Binary Search Tree with all ***Balance Factors*** being either -1, 0, or 1
	* Search implementation identical to BST
	* *s_i* --> Minimum size of an AVL Tree of depth *i*
		* See assignment

## Tasks To Complete

* **Search**
	* Replacement of the BSTDictionary method
* **RotateLeft**
	* Perform a left rotation at a node
	* Private --> Can assume left rotations are always possible when this is called
* **RotateRight**
	* Perform a right rotation at a node
	* Private --> Can assume rightt rotations are always possible when this is called
* **Change**
	* Replacement of the BSTDictionary method
* **DeleteFromSubtree**
	* Replacement of the BSTDictionary method
* **DeleteNode**
	* Replacement of the BSTDictionary method
* **Successor**
	* Replacement of the BSTDictionary method
