
public class SingleLinkedList<E> {

	private Node head;
	private int length;

	public SingleLinkedList(){
		this.head = null;
		this.length = 0;
	}

	public void addFirst(Object data){
		Node newNode = new Node(data);
		if(length() == 0)
			head = newNode;
		else{
			newNode.next = head;
			head = newNode;
		}
		length++;
	}

	public void addLast(Object data){
		Node newNode = new Node(data);
		if(length() == 0)
			head = newNode;
		else{
			Node current = head;
			while(current.next != null)
				current = current.next;
			current.next = newNode;
		}
		length++;
	}

	public void add(Object data, int index){
		Node newNode = new Node(data);
		if(length() == 0 && index == 1)
			head = newNode;
		else{
			Node current = head;
			try{
				for(int i = 1; i < index - 1 && current.next != null; i++)
					current = current.next;
				newNode.next = current.next;
				current.next = newNode;
			} catch(IndexOutOfBoundsException e){
				System.out.println("Invalid index");
			}
		}
		length++;
	}

	public int length(){
		return this.length;
	}

	public Node getFirst(){
		if(length() > 0)
			return this.head;
		return null;
	}

	public Node getLast(){
		if(length() > 0){
			Node current = head;
			while(current.next != null)
				current = current.next;
			return current;
		}
		return null;
	}

	public Node getIndex(int index){
		if(index <= 0 || index > length())
			return null;
		Node current = head;
		for(int i = 1; i < index; i++)
			current = current.next;
		return current;
	}

	public Node remove(int index){
		if(index <= 0 || index > length())
			System.out.println("Invalid index");
		else if(index == 1){
			Node removed = head;
			head = head.next;
			removed.next = null;
			length--;
			return removed;
		}
		else{
			Node current = head;
			for(int i = 2; i < index - 1; i++)
				current = current.next;
			Node removed = current.next;
			current = current.next.next;
			current.next = null;
			length--;
			return removed;
		}
		return null;
	}

	/**
	 * Special Problems
	 * @author HUGO
	 *
	 */

	/**
	 * Mth-to-last;
	 * @return 
	 */

	public Node MthToLast(int m){
		Node tempM = head;
		for(int i = 0; i < m; i++){
			if(tempM.next != null)
				tempM = tempM.next;
			else
				return new Node(null);
		}
		Node tempMBehind = head;
		while(tempM.next != null){
			tempM = tempM.next;
			tempMBehind = tempMBehind.next;
		}
		return tempMBehind;
	}

	public void reverse(){
		if(this.length == 1)
			return;
		Node first = head;
		Node current = head.next();
		Node last = null;
		if(this.length() > 2)
			last = current.next();
		while(current != null){
			current.setNext(first);
			first = current;
			current = last;
			if(last != null)
				last = last.next();
		}
		head = first;
	}

	public void concatReverseString(){
		Node aux = new Node();
		Node current = head;
		while(current.next() != null){
			Node letter = new Node(current.getData());
			if(aux.next() != null){
				letter.setNext(aux.next());
			}
			aux.setNext(letter);
			current = current.next();
		}
		current.setNext(aux.next());
	}
	/**
	 * This method creates Node type
	 * @author HUGO
	 *
	 */
	class Node{

		private Node next;
		private Object data;

		public Node(Object data){
			this.data = data;
			this.next = null;
		}
		
		public Node(){
			this.data = null;
			this.next = null;
		}

		public Node next(){
			return this.next;
		}

		public void setNext(Node next){
			this.next = next;
		}

		public Object getData(){
			return this.data;
		}

		public void setData(Object data){
			this.data = data;
		}
	}
}
