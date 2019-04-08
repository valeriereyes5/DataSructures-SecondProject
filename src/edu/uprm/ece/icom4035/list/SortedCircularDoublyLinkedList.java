package edu.uprm.ece.icom4035.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;




public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {
	private DNode<E> head;
	private int currentSize; 
	int size;

	//constructor
	public SortedCircularDoublyLinkedList() {//complete
		head= new DNode<E>(null);
		this.head.setElement(null);
		this.head.setNext(head);head.setPrev(head);
		currentSize=-1;
		size=0;
	}

	//Returns a forward iterator ,
	//starting from the first element in the list.
	//method not in documentation
	@Override
	public Iterator<E> iterator() {//done
		return new FowardListIterator();
		// TODO Auto-generated method stub
	}



	//Adds a new element to the list in the right order 
	//The method traverses the list, looking for the right position for obj.
	//returns true when it adds obj
	@Override
	public boolean add(E obj) {//tested
		// TODO Auto-generated method stub
		DNode<E> curr = head.getNext();
		//node is the first
		if(this.isEmpty()) {
			DNode<E>  newNode = new DNode<E>(obj, head,head);
			head.setNext(newNode);
			head.setPrev(newNode);
		}

		else {
			while(curr.getNext()!= head) {
				if(((Comparable<E>) obj).compareTo(curr.getElement())<=0) {
					DNode<E>  newNode = new DNode<E>(obj, curr.getPrev(),curr);
					curr.getPrev().setNext(newNode);
					curr.setPrev(newNode);
					currentSize++;
					size++;
					return true;
				}

				curr = curr.getNext();
			}
			if(((Comparable<E>) obj).compareTo(curr.getElement())<=0) {
				DNode<E>  newNode = new DNode<E>(obj, curr.getPrev(),curr);
				curr.getPrev().setNext(newNode);
				curr.setPrev(newNode);
			}

			else if(((Comparable<E>) obj).compareTo(head.getNext().getElement())>0) {
				DNode<E>  newNode = new DNode<E>(obj, head.getPrev(),head);
				head.getPrev().setNext(newNode);
				head.setPrev(newNode);

			}
		}

		currentSize++;
		size++;
		return true;
	}

	// returns the number of elements in the list.
	@Override
	public int size() {//tested
		// TODO Auto-generated method stub
		return size;
	}


	//removes the first occurrence of obj from the list. 
	//Returns true if erased, or false otherwise.
	@Override
	public boolean remove(E obj) { //tested
		// TODO Auto-generated method stub
		DNode<E> cursor = head.getNext();
		for(int i=0; i<size; i++) {
			if(obj==cursor.getElement()) {
				DNode<E> before = cursor.getPrev();
				DNode<E> after = cursor.getNext();
				before.setNext(after);
				after.setPrev(before);
				currentSize--;
				size--;
				return true;


			}
			cursor= cursor.next;
		}

		return false;
	}


	//removes the elements at position index. 
	//Returns true if the element is erased, or an IndexOutBoundsException if index is illegal.
	@Override
	public boolean remove(int index) { //tested
		// TODO Auto-generated method stub
		if(index<0 || index>currentSize) {throw new IndexOutOfBoundsException();}

		int i=0;
		DNode<E> curr=new DNode<E>();
		curr =head.getNext();
		Iterator<E> iter = iterator();
		while(iter.hasNext()) {

			if(i==index) {
				break;
			}
			curr = curr.getNext();
			i++;
			iter.next();
		}
		DNode<E> before = curr.getPrev();
		DNode<E> after = curr.getNext();
		before.setNext(after);
		after.setPrev(before);
		currentSize--;
		size--;
		return true;

	}


	//removes all copies of element obj, and returns the number of copies erased.
	@Override
	public int removeAll(E obj) {//tested
		// TODO Auto-generated method stub
		if(!this.contains(obj)) {
			return 0;

		}

		int counter=0;

		Iterator<E> iter = this.iterator();

		while(iter.hasNext()) {
			if(obj==iter.next()) {
				this.remove(obj);
				counter++;
			}

		}
		return counter;
	}

	//returns the first (smallest) element in the list, or null if the list is empty.
	@Override
	public E first() {//tested
		// TODO Auto-generated method stub
		return head.getNext().getElement();
	}


	//returns the last (largest) element in the list, or null if the list is empty.
	@Override
	public E last() { //tested
		// TODO Auto-generated method stub
		return head.getPrev().getElement();
	}


	//returns the elements at position index, or an IndexOutBoundsException if index is illegal.
	@Override
	public E get(int index) { //tested
		if(index<0 || index>currentSize) {throw new IndexOutOfBoundsException();}

		int i=0;
		DNode<E> curr=new DNode<E>();
		curr =head.getNext();
		Iterator iter = iterator();
		while(iter.hasNext()) {

			if(i==index) {
				break;
			}
			curr = curr.getNext();
			i++;
			iter.next();
		}

		return curr.getElement();


		// TODO Auto-generated method stub
	}


	//removes all elements in the list.
	@Override
	public void clear() {//tetsed
		// TODO Auto-generated method stub
		if(this.isEmpty()) {
			return;
		}
		Iterator<E> iter = this.iterator();

		while(iter.hasNext()) {
			this.remove(iter.next());
		}

	}


	//returns true if the element e is in the list or false otherwise.
	@Override
	public boolean contains(E e) { //tested
		DNode<E> cursor = head.getNext();
		for(int i=0; i<size; i++) {
			if(e==cursor.getElement()) {
				return true;
			}
			cursor= cursor.next;
		}

		return false;
		// TODO Auto-generated method stub

	}


	//returns true if the list is empty, or false otherwise.
	@Override
	public boolean isEmpty() {//tested
		// TODO Auto-generated method stub
		return this.size==0;
	}


	//Returns a forward iterator from position index, or an IndexOutBoundsException if index is illegal.
	@Override
	public Iterator<E> iterator(int index) {//done
		// TODO Auto-generated method stub
		return new FowardListIteratorIndex(index);
	}



	//returns the index (position) of the first position of element e in the list or -1 if the element is not present.
	@Override
	public int firstIndex(E e) {//tested
		// TODO Auto-generated method stub
		if(!this.contains(e)) {
			return -1;
		}

		int i=0;

		Iterator iter = iterator();
		E curr= (E) iter.next();
		while(iter.hasNext()) {

			if(curr.equals(e)) {
				break;
			}
			i++;
			curr = (E) iter.next();
		}
		return i;
	}

	// returns the index (position) of the last position of element e in the list or -1 if the element is not present.
	@Override
	public int lastIndex(E e) {//tested
		// TODO Auto-generated method stub
		if(!this.contains(e)) {
			return -1;
		}

		int i=size;

		ReverseIterator<E> iter = reverseIterator();
		while(iter.hasPrevious()) {

			if(iter.previous().equals(e)) {
				i--;
				break;
			}
			i--;
		}
		return i;
	}

	
	//returns a reverse iterator, starting from the last element in the list.
	@Override
	public ReverseIterator<E> reverseIterator() {//done
		// TODO Auto-generated method stub
		return new ReverseListIterator();
	}

	//returns a reverse iterator, starting from position index in the list, or an IndexOutBoundsException if index is illegal.
	@Override
	public ReverseIterator<E> reverseIterator(int index) { //done
		// TODO Auto-generated method stub

		return new ReverseListIteratorIndex(index);
	}





	private static class DNode<E> implements Node<E> {
		private E element; 
		private DNode<E> prev, next; 

		// Constructors
		public DNode() {}

		public DNode(E e) { 
			element = e; 
		}

		public DNode(E e, DNode<E> p, DNode<E> n) { 
			element =e;
			prev = p; 
			next = n; 
		}

		// Methods
		public DNode<E> getPrev() {
			return prev;
		}
		public void setPrev(DNode<E> prev) {
			this.prev = prev;
		}
		public DNode<E> getNext() {
			return next;
		}
		public void setNext(DNode<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element; 
		}
		/**
		 * Just set references prev and next to null. Disconnect the node
		 * from the linked list.... 
		 */
		public void cleanLinks() { 
			prev = next = null; 
		}


		@Override
		public void setElement(E data) {
			// TODO Auto-generated method stub
			element =data;
		}

	}
	//Iterator classes
	private class FowardListIterator implements Iterator<E>{ //tested
		private DNode<E> currentNode;
		public FowardListIterator() {
			this.currentNode = head.getNext();
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return this.currentNode != head;
		}
		@Override
		public E next() {
			// TODO Auto-generated method stub
			if(!this.hasNext()) throw new NoSuchElementException();
			E result = this.currentNode.getElement();
			this.currentNode = this.currentNode.getNext();
			return result;
		}
		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

	public class ReverseListIteratorIndex implements ReverseIterator<E>{ //complete
		DNode<E> curr = null;
		int i=0;
		int index =0;
		public ReverseListIteratorIndex(int index) {
			this.index=index;
			curr = head.getPrev();
		}
		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return i<=index;
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			if(!hasPrevious()) throw new NoSuchElementException("No more elements");
			E temp = curr.getElement();
			i++;
			curr = curr.getPrev();
			return temp;
		}

	}

	private class ReverseListIterator implements ReverseIterator<E>{
		private DNode<E> currentNode;
		public ReverseListIterator() {
			this.currentNode = head.getPrev();
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return this.currentNode != head;
		}
		@Override
		public E previous() {
			// TODO Auto-generated method stub
			if(!this.hasPrevious()) {throw new NoSuchElementException();}
			E result = this.currentNode.getElement();
			this.currentNode = this.currentNode.getPrev();
			return result;
		}

	}


	public class FowardListIteratorIndex implements Iterator<E>{ //complete
		DNode<E> curr = null;
		int i=0;
		int index =0;
		public FowardListIteratorIndex(int index) {
			this.index=index;
			curr = head.getNext();
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return i<=index;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			if(!hasNext())
				throw new NoSuchElementException("No more elements");
			E temp = curr.getElement();
			i++;
			curr = curr.getNext();
			return temp;
		}
		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}


	}

}
