package dsa.impl;

public class Main {
	public static void main(String[] args) {
		AVLTree<Integer> t = new AVLTree<Integer>();
	    
	    t.insert (2, null);
	    t.insert (1, null);
	    t.insert (4, null);
	    t.insert (5, null);
	    t.insert (9, null);
	    t.insert (3, null);
	    t.insert (6, null);
	    t.insert (7, null);
	    t.insert (8, null);
	    t.insert (11, null);
	    t.insert (10, null);
	    t.insert (100, null);
	    t.insert (19, null);
	    t.insert (13, null);
	    t.insert (16, null);
	    t.insert (17, null);
	    
	    t.insert (700, null);
	    t.insert (-5, null);
	    t.insert (99, null);
	    t.insert (-33, null);
	    t.insert (111, null);
	    t.insert (171, null);
	    
	    System.out.println("FIRST TREE-------------------------------------------------------");
		t.printTree(t.root);
		
		t.remove(t.find(t.root, 700));
		t.remove(t.find(t.root, 11));
		t.remove(t.find(t.root, 8));
		
		 System.out.println("SECOND TREE AFTER REMOVAL-------------------------------------------------------");
		t.printTree(t.root);
		

		/*
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		bst.insert(23);
		bst.insert(12);
		bst.insert(44);
		bst.insert(13);
		bst.insert(1);
		bst.insert(7);
		bst.insert(22);
		bst.insert(55);
		bst.insert(43);
		bst.insert(18);
		
		System.out.println("FIRST TREE------------------------------------------------------");
		TreePrinter.printTree( bst );
		bst.remove(1);
		
		System.out.println("SECOND TREE------------------------------------------------------");
		TreePrinter.printTree( bst );
		
		bst.remove(23);
		
		System.out.println("THIRD TREE-------------------------------------------------------");
		TreePrinter.printTree( bst );
		*/
	} 
}
