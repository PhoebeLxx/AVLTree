package dsa.impl;


import dsa.iface.IBinarySearchTree;
import dsa.iface.INode;

public class BinarySearchTree<T extends Comparable<T>> extends ProperLinkedBinaryTree<T> implements IBinarySearchTree<T> {

   protected INode<T> find( INode<T> node, T value ) {
	   if(this.isExternal(node)) {
		   return node;
	   }else if(this.isInternal(node)) {
		   if(value.compareTo(node.element()) < 0) {
			   return find(this.left(node), value);
		   }else if(value.compareTo(node.element()) > 0) {
			   return find(this.right(node), value);
		   }else {
			   return node;
		   }
	   }
	   return null;
      
	   // 1. Return the node if it is external.
	   // 2. Compare the element of the node with 'value'.
	   // 3. If the value is less than the node's element, recursively call this method to search the left sub-tree.
	   // 4. If the value is greater than the node's element, recursively call this method to search the right sub-tree.
	   // 5. If the value is equal to the node's element, we have found it! Return this node.
      //return null; // <-- This is so that the class will compile. Remove it when writing your code.
   }

   public void insert( T value ) {
	   INode<T> node = find(this.root(), value);
	   super.expandExternal(node, value);
	   // 1. Use the 'find' 'method to find the external node where this should be inserted (beginning at the root)
	   // 2. Expand the external node that is found, to insert the element.
	   //       You can use the expandExternal(INode<T>, T e) method from ProperLinkedBinaryTree for this.
   }

   public void remove( T value ) {
	   INode<T> current = find(this.root(), value);
	   if(this.isExternal(this.left(current)) || this.isExternal(this.right(current))) {
		   super.remove(current);
	   }else{
		   INode<T> replace = biggestLeft(this.left(current));
		   T first = current.element();
		   T second = replace.element();
		   this.replace(current, second);
		   this.replace(replace, first);
		   super.remove(replace);
	   }
	   // 1. Use the 'find' method (starting at the root) to find the node that contains the value.
	   // 2. If an internal node with at least one external child was returned you can remove it using the remove(INode<T> n) method from ProperLinkedBinaryTree
	   // 3. If the node has two internal children, find the node with the next biggest value, swap the value and delete that node.
   }

   public boolean contains( T value ) {
	   if(find(this.root(), value) == null) return false;
	   else return true;
	   // 1. Use the 'find' method to find the node that contains the value (if it is in the tree).
	   // 2. If 'find' returned an internal node, return true. Otherwise return false.
      //return true; // <-- This is so that the class will compile. Remove it when writing your code.
   }
   
   public INode<T> biggestLeft(INode<T> node) {
	   if(this.isInternal(this.right(node))) {
		   return biggestLeft(this.right(node));
	   }
	   return node;
   }
   
   public void swap(T first, T second) {
	   T media = first;
	   first = second;
	   second = media;
   }
}