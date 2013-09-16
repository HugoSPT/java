import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class BinarySearchTree {

	private Node root;

	/**
	 * Creates a new BST with a node
	 * @param value the value of root
	 * @param left the left child of root
	 * @param right the right child of root
	 */
	public BinarySearchTree(int value, Node left, Node right){
		this.root = new Node(value, left, right);
	}

	/**
	 * The list number of elements
	 * @param node the root to start the search
	 * @return number of nodes
	 */
	public int size(Node node){
		if(node == null)
			return 0;
		return 1 + size(node.getLeft()) + size(node.getRight());
	}

	/**
	 * Add a new node to the tree
	 * @param root the node to start to cross the tree
	 * @param value the value of node to add
	 */
	public void add(Node root, int value){
		if(value < root.getData()){
			if(root.getLeft() == null)
				root.left = new Node(value, null, null);
			else
				add(root.getLeft(), value);
		} else {
			if(root.getRight() == null)
				root.right = new Node(value, null, null);
			else
				add(root.getRight(), value);
		}
	}

	/**
	 * Find a value in the tree
	 * @param node the node to start the search
	 * @param value the value to be searched
	 * @return the node if it was found or null
	 */
	public Node findNode(Node node, int value){
		if(node == null) 
			return null;
		Node current = node;
		while(current != null){
			if(value < current.getData())
				return findNode(current.getLeft(), value);
			else if(value > current.getData())
				return findNode(current.getRight(), value);
			else
				return current;
		}
		return current;
	}

	/**
	 * Tree's depth
	 * @param node the node to start the search
	 * @return depth of tree
	 */
	public int height(Node node){
		if(node == null)
			return -1;
		return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
	}

	/**
	 * Check if node is leaf
	 * @param node the node to be checked
	 * @return if this node is leaf
	 */
	public boolean isLeaf(Node node){
		return node.getLeft() == null && node.getRight() == null;
	}

	public int numLeaves(Node node){
		if(node == null)
			return 0;
		if(node.getLeft() == null && node.getRight() == null)
			return 1;
		return numLeaves(node.getLeft()) + numLeaves(node.getRight());
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public int numNodesOneChild(Node node){
		if(node == null)
			return 0;
		if((node.getLeft() == null && node.getRight() != null) || (node.getLeft() != null && node.getRight() == null))
			return 1;
		return numNodesOneChild(node.getLeft()) + numNodesOneChild(node.getRight());
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public int numNodesTwoChilds(Node node){
		if(node == null)
			return 0;
		if(node.getLeft() != null && node.getRight() != null)
			return 1 + numNodesTwoChilds(node.getLeft()) + numNodesTwoChilds(node.getRight());
		return numNodesTwoChilds(node.getLeft()) + numNodesTwoChilds(node.getRight());
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public boolean isFull(Node node){
		if(node == null)
			return true;
		if(isLeaf(node))
			return true;
		if((node.getLeft() == null && node.getRight() != null) || (node.getLeft() != null && node.getRight() == null))
			return false;
		return isFull(node.getLeft()) && isFull(node.getRight());
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public boolean isComplete(Node node){
		if(node == null)
			return true;
		int leftDepth = height(node.getLeft());
		int rightDepth = height(node.getRight());

		return (leftDepth == rightDepth);
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public boolean isBalanced(Node node){
		return Math.abs(height(node.getLeft()) - height(node.getRight())) <= 1;

	}
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	public Node heapifyTree(Node node){
		int size = this.size(node);
		Node[] array = new Node[size];
		treeToArray(node, array, 0);
		Arrays.sort(array, new Comparator<Node>(){
			@Override public int compare(Node m, Node n){
				int mv = m.getData(), nv = n.getData();
				return (mv < nv ? -1 : (mv == nv ? 0 : 1));
			}
		});

		for(int i = 0; i < array.length; i++){
			int left = 2 * i + 1;
			int right = left + 1;
			array[i].setLeft(left >= size ? null : array[left]);
			array[i].setRight(right >= size ? null : array[right]);
		}
		toString(array[0]);

		return array[0];
	}

	/**
	 * 
	 * @param node
	 * @param array
	 * @param counter
	 * @return
	 */
	private int treeToArray(Node node, Node[] array, int counter){
		if(node == null)
			return counter;
		if(node != null){
			array[counter] = node;
		}
		counter++;
		counter = treeToArray(node.getLeft(), array, counter);
		counter = treeToArray(node.getRight(), array, counter);	
		return counter;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Node rotation(Node node){
		Node leftChild = node.getLeft();
		Node parent = node.getParent(node, getRoot(), null);
		try{
			node.setLeft(leftChild.getRight());
			leftChild.setRight(node);
			if(node.equals(root))
				root = leftChild;
			parent.setLeft(leftChild);
		} catch(NullPointerException e){

		}
		return leftChild;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public String toString(Node node){
		StringBuilder b = new StringBuilder();
		toString(node, b, 0);
		return b.toString();
	}

	/**
	 * 
	 * @param n
	 * @param sb
	 * @param depth
	 */
	private void toString(Node n, StringBuilder sb, int depth){
		indent(depth, sb);
		if(n == null){
			sb.append("");
		} else{
			sb.append(n.getData()).append("\n");
			toString(n.getLeft(), sb, depth+1);
			toString(n.getRight(), sb, depth+1);
		}

	}

	/**
	 * 
	 * @param depth
	 * @param sb
	 */
	private void indent(int depth, StringBuilder sb){
		for(int i = 5; i > depth; i--)
			sb.append("     ");
	}

	/**
	 * Tree's root
	 * @return the root
	 */
	public Node getRoot(){
		return this.root;
	}
	
	public static Node commonAntecessor(Node root, Node one, Node two){
		if(found(root.getLeft(), one) && found(root.getLeft(), two))
			commonAntecessor(root.getLeft(), one, two);
		if(found(root.getRight(), one) && found(root.getRight(), two))
			commonAntecessor(root.getRight(), one, two);
		return root;
	}
	
	private static boolean found(Node root, Node toSearch){
		if(root == null)
			return false;
		if(root == toSearch)
			return true;
		return found(root.getLeft(), toSearch) || found(root.getRight(), toSearch);
	}
	
	public static int betweenInterval(Node node, int a, int b){
		int counter = 0;
		if(node.getData() < a)
			return search(node.getRight(), a, b, counter);
		else if(node.getData() > b)
			return search(node.getLeft(),a , b, counter);
		return search(node, a, b, counter);
	}
	
	private static int search(Node node, int a, int b, int counter){
		if(node == null)
			return counter;
		if(node.getData() >= a && node.getData() <= b)
			counter++;
		counter = search(node.getLeft(), a, b, counter);
		counter = search(node.getRight(), a, b, counter);
		return counter;
		
	}


	/***
	 * 
	 * @author HUGO
	 *
	 */
	class Node{
		private Node left;
		private Node right;
		private int data;

		public Node(int data, Node left, Node right){
			this.left = left;
			this.right = right;
			this.data = data;
		}

		public Node(Node left, Node right, int data){
			this.left = left;
			this.right = right;
			this.data = data;
		}

		public Node getLeft(){
			return this.left;
		}

		public Node getRight(){
			return this.right;
		}

		public void setLeft(Node node){
			this.left = node;
		}

		public void setRight(Node node){
			this.right = node;
		}

		public int getData(){
			return this.data;
		}

		public Node getParent(Node find, Node node, Node parent)
		{
			if (node == null) {
				return null;
			} else if (!find.equals(node)) {
				parent = getParent(find, node.getLeft(), node);
				if (parent == null) {
					parent = getParent(find, node.getRight(), node);
				}
			}
			return parent;
		}

		public boolean isLeaf(){
			return (this.getLeft() == null && this.getRight() == null);
		}

		public boolean equals(Object obj){
			if(obj == null)
				return false;
			if(this == null)
				return false;
			if(!(obj instanceof Node))
				return false;
			Node temp = (Node) obj;
			return  new Integer(this.getData()).equals(new Integer(temp.getData())); 
		}

	}
}
