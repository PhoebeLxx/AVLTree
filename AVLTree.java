package dsa.impl;


class AVLTree<T extends Comparable<T>> {
	protected static class AVLNode<T>{
		protected int element;
		protected T elementExtra;
		protected AVLNode<T> left;
		protected AVLNode<T> right;
		protected AVLNode<T> parent;
		
		//create a node without child.
		public AVLNode(int element, T elementExtra, AVLNode<T> parent){
			this(element, elementExtra, null, null, parent);
			if(this.element != Integer.MIN_VALUE) 
				expandExteral(this);
		}

		//create a node with children.
		public AVLNode(int element, T elementExtra, AVLNode<T> left, AVLNode<T> right, AVLNode<T> parent) {
			this.element = element;
			this.elementExtra = elementExtra;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
		
		//change the value of elementExtra in an internal node
		public void setValue(int element, T elementExtra) {
			this.element = element;
			this.elementExtra = elementExtra;
		}
		
		//create two external children
		public void expandExteral(AVLNode<T> cell) {
			cell.left = new AVLNode<T>(Integer.MIN_VALUE, null, cell);
			cell.right = new AVLNode<T>(Integer.MIN_VALUE, null, cell);
		}
	}
	
	protected AVLNode<T> root;
	
	//initialize the tree
	public AVLTree() {
		root = new AVLNode<T>(Integer.MIN_VALUE, null, null);
	}
	
	//check whether the tree is empty
	public boolean isEmpty() {
		return (root == null);
	}
	
	//check external
	public boolean isExternal(AVLNode<T> cell) {
		if(cell != null)
			return ((cell.left != null) || (cell.right != null)) ? false : true;
		return false;
	}
	
	//check internal
	public boolean isInternal(AVLNode<T> cell) {
		if(cell != null)
			return ((cell.left != null) || (cell.right != null)) ? true : false;
		return false;
	}
	
	//find a node and return it if exists
	//return an empty node if not exist for insertion
	public AVLNode<T> find( AVLNode<T> cell, int value ) {
		if(cell == null) return null;
	   if(this.isExternal(cell)) {
		   return cell;
	   }else if(cell.element == value) {
		   return cell;
	   }else{
		   if(value < cell.element) return find(cell.left, value); 
		   else if(value > cell.element) return find(cell.right, value);
		   else return cell;
	   }
	 }
	 
	//find the higher child of cell
	public AVLNode<T> higherChild(AVLNode<T> cell){
		return getHeight(cell.left) > getHeight(cell.right) ? cell.left : cell.right;
	}
	
	//find the internal child (assume cell has only one internal node)
	public AVLNode<T> interalChild(AVLNode<T> cell){
		if(isInternal(cell.left)) return cell.left;
		if(isInternal(cell.right)) return cell.right;
		return null;
	}
	
	//height of the tree
	public int getHeight() {
		return getHeight(root);
	}
	
	//height of a particular node
	public int getHeight(AVLNode<T> cell) {
		if(cell == null) return -1;
		int rightHeight = -1;
		int leftHeight = -1;
		if(cell.left != null) 
			leftHeight = getHeight(cell.left);
		if(cell.right != null) 
			rightHeight = getHeight(cell.right);
		return Math.max(leftHeight, rightHeight) + 1;
	}
	
	//size of the tree
	public int size() {
		return size(root);
	}
	
	//size of a subtree
	public int size(AVLNode<T> cell) {
		if(cell == null) {
			return 0;
		}else {
			int leftSize = size(cell.left);
			int rightSize = size(cell.right);
			return leftSize + rightSize + 1;
		}
	}
	
	//level(depth) of a particular node
	public int getLevel(AVLNode<T> cell) {
		int countAnc = 0;
		while(cell != root) {
			cell = cell.parent;
			countAnc++;
			if(cell == root) break;
		}
		return countAnc;
	}
	
	//check whether a node is balanced
	public boolean checkUnbalanced(AVLNode<T> cell) {
		return (Math.abs(getHeight(cell.left) - getHeight(cell.right)) > 1);
	}
	
	//find the first unbalanced node start from cell
	public AVLNode<T> findUnbalanced(AVLNode<T> cell){
		while(cell.parent != null) {
			cell = cell.parent;
			if(checkUnbalanced(cell)) return cell;
		}
		return null;
	}
	
	//find the maximum value
	public int max(AVLNode<T> first, AVLNode<T> second, AVLNode<T> third) {
		if(first.element > second.element && first.element > third.element) 
			return first.element;
		else if(second.element > first.element && second.element > third.element) 
			return second.element;
		else if(third.element > first.element && third.element > second.element) 
			return third.element;
		else 
			return 0;
	}
	
	//find the minimum value
		public int min(AVLNode<T> first, AVLNode<T> second, AVLNode<T> third) {
			if(first.element < second.element && first.element < third.element) 
				return first.element;
			else if(second.element < first.element && second.element < third.element) 
				return second.element;
			else if(third.element < first.element && third.element < second.element) 
				return third.element;
			else 
				return 0;
		}
	
	//find the middle value
	public int middle(AVLNode<T> first, AVLNode<T> second, AVLNode<T> third) {
		if((max(first, second, third) != first.element) && (min(first, second, third) != first.element)) 
			return first.element;
		else if((max(first, second, third) != second.element) && (min(first, second, third) != second.element)) 
			return second.element;
		else if((max(first, second, third) != third.element) && (min(first, second, third) != third.element)) 
			return third.element;
		else 
			return 0;
	}
	
	//restructure the tree
	public AVLNode<T> resturcture(AVLNode<T> cell) {
		AVLNode<T> z = findUnbalanced(cell);
		if(z == null) return null;
		
		AVLNode<T> y = higherChild(z);
		AVLNode<T> x = higherChild(y);
		
		if(middle(z, y, x) == y.element) {
			rotation(z, y, x);
			return y;
		}else if(middle(z, y, x) == x.element) {
			rotation(y, x, z);
			rotation(z, x, y);
			return x;
		}
		return null;
	}
	
	//optimize the shape of the tree by continuing implementing resturcture
	public void optimize(AVLNode<T> cell) {
		if(getLevel(cell) > 1) {
			while(cell != root) {
				cell = resturcture(cell);
				if(cell == root) cell.parent = null;
				if(cell == null) break;
				if(cell.parent == null) root = cell;
			}
		}
	}
		
	//single rotation: parameter sequence follows (z, y, x)
	//double rotation: parameter sequence follows (y, x, z)
	public void rotation(AVLNode<T> first, AVLNode<T> second, AVLNode<T> third){
		AVLNode<T> parentFirst = first.parent;
		
		if(first.element > third.element) {  
			first.left = second.right;
			second.right.parent = first;
			if(parentFirst != null) {
				if(parentFirst.left == first) {
					parentFirst.left = second;
					second.parent = parentFirst;
				}
				if(parentFirst.right == first) {
					parentFirst.right = second;
					second.parent = parentFirst;
				}
			}
			second.right = first;
			first.parent = second;
		}else if(first.element < third.element) {
			first.right = second.left;
			second.left.parent = first;
			if(parentFirst != null) {
				if(parentFirst.left == first) {
					parentFirst.left = second;
					second.parent = parentFirst;
				}
				if(parentFirst.right == first) {
					parentFirst.right = second;
					second.parent = parentFirst;
				}
			}
			second.left = first;
			first.parent = second;
			
			if(parentFirst == null) root = second;
		}
	}
	
	//count internal children
	public int countInChild(AVLNode<T> cell) {
		int count = 0;
		if(isInternal(cell.left)) count++;
		if(isInternal(cell.right)) count++;
		return count;
	}
	
	//find the smallest value in the right subtree
	public AVLNode<T> smallestRight(AVLNode<T> cell){
		if(isInternal(cell.left))
			return smallestRight(cell.left);
		return cell;
	}
	
	//swap values between two nodes
	public AVLNode<T> swap(AVLNode<T> cell) {
		AVLNode<T> replace = smallestRight(cell.right);
		AVLNode<T> media = new AVLNode<T>(0, null, null);
		
		media.element  = cell.element;
		cell.element = replace.element;
		replace.element = media.element;
		
		media.elementExtra  = cell.elementExtra;
		cell.elementExtra = replace.elementExtra;
		replace.elementExtra = media.elementExtra;
		
		return replace;
	}
	
	
	//change reference among parent and child
	public AVLNode<T> checkLeftRight(AVLNode<T> cell) {
		AVLNode<T> check = cell.parent;
		if(check.left == cell) return check.left;
		if(check.right == cell) return check.right;
		return null;
	}
	
	//delete a node
	public AVLNode<T> delete(AVLNode<T> cell) {
		AVLNode<T> check = cell.parent;
		if(countInChild(cell) == 0) {
			 //changeRefer(check, new AVLNode(Integer.MIN_VALUE, null));
			 if(check.left == cell) {
				 check.left = new AVLNode<T>(Integer.MIN_VALUE, null, check);
				 return check.left;
			 }
			 if(check.right == cell) {
				 check.right = new AVLNode<T>(Integer.MIN_VALUE, null, check);
				 return check.right;
			 }
		 }else if(countInChild(cell) == 1) {
			 //changeRefer(check, interalChild(cell));
			 if(check.left == cell) {
				 check.left = interalChild(cell);
				 return check.left;
			 }
			 if(check.right == cell) {
				 check.right = interalChild(cell);
				 return check.right;
			 }
		 }else {
			 return delete(swap(cell));
		 }
		return null; 
	}
	
	//insert a new node
	public void insert(int element, T elementExtra) {
		AVLNode<T> cell = find(root, element);
		if(cell.element == Integer.MIN_VALUE) {
			cell.setValue(element, elementExtra);
			cell.expandExteral(cell);
		}
		optimize(cell);
	}

	//remove a node and restructure the tree
	public void remove(AVLNode<T> cell) {
		AVLNode<T> start = delete(cell);
		optimize(start);
	}
	
	//print the tree using preorder traversal
	public AVLNode<T> printTree(AVLNode<T> cell) {
		for(int i = 0; i < getLevel(cell); i++ ) 
			System.out.print("       ");
		
		if(isExternal(cell)) {
			System.out.println("[]");
			return null;
		}else {
			System.out.println(cell.element);
			if(printTree(cell.left) == null) return printTree(cell.right);
			else return printTree(cell.left);
		}
	}

}
