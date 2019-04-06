package edu.uprm.ece.icom4035.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;




public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {
	private DNode<E> head;
	private int currentSize; 
	int size;

	public SortedCircularDoublyLinkedList() {//complete
		head= new DNode();
		this.head.setElement(null);
		this.head.setNext(head);head.setPrev(head);
		currentSize=-1;
		size=0;
	}
	@Override
	public Iterator<E> iterator() {//done
		return new FowardListIterator();
		// TODO Auto-generated method stub
	}


	public void addbetween(E e, DNode<E> p, DNode<E> n) {
		DNode<E>  newNode = new DNode<E>(e, p, n);
		p.setNext(newNode);
		n.setPrev(newNode);
	}
	@Override
	public boolean add(E obj) {
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
				}

				curr = curr.getNext();
			}
			if(((Comparable<E>) obj).compareTo(head.getNext().getElement())>0) {
				DNode<E>  newNode = new DNode<E>(obj, head.getPrev(),head);
				head.getPrev().setNext(newNode);
				head.setPrev(newNode);

			}
			else if(((Comparable<E>) obj).compareTo(curr.getElement())<=0) {
				DNode<E>  newNode = new DNode<E>(obj, curr.getPrev(),curr);
				curr.getPrev().setNext(newNode);
				curr.setPrev(newNode);
			}
		}

		currentSize++;
		size++;
		return true;
	}


	@Override
	public int size() {//done
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean remove(E obj) { //complete
		// TODO Auto-generated method stub
		DNode<E> cursor = head.getNext();
		for(int i=0; i<currentSize; i++) {
			if(obj==cursor.getElement()) {
				DNode<E> before = cursor.getPrev();
				DNode<E> after = cursor.getNext();
				before.setNext(after);
				after.setPrev(before);
				currentSize--;
				return true;

			}
			cursor= cursor.next;
		}

		return false;
	}

	@Override
	public boolean remove(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int removeAll(E obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public E first() {//done
		// TODO Auto-generated method stub
		return head.getNext().getElement();
	}

	@Override
	public E last() { //done
		// TODO Auto-generated method stub
		return head.getPrev().getElement();
	}

	@Override
	public E get(int index) { //done //
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

	@Override
	public void clear() {//complete
		// TODO Auto-generated method stub
		if(this.isEmpty()) {
			return;
		}
		DNode<E> curr = this.head.getNext();
		while(curr != head) {
			DNode<E> temp= curr;
			curr = curr.getNext();
			temp.cleanLinks();
			currentSize--;
		}
	}

	@Override
	public boolean contains(E e) { //done
		DNode<E> cursor = head.getNext();
		for(int i=0; i<currentSize; i++) {
			if(e==cursor.getElement()) {
				return true;
			}
			cursor= cursor.next;
		}

		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpty() {//done
		// TODO Auto-generated method stub
		return this.size==0;
	}

	@Override
	public Iterator<E> iterator(int index) {//done
		// TODO Auto-generated method stub
		return new FowardListIteratorIndex(index);
	}

	@Override
	public int firstIndex(E e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndex(E e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReverseIterator<E> reverseIterator() {//done
		// TODO Auto-generated method stub
		return new ReverseListIterator();
	}

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

		}

	}

	private class FowardListIterator implements Iterator<E>{
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
			if(!this.hasPrevious())
				throw new NoSuchElementException();
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
			curr = head.getPrev();
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
			curr = curr.getPrev();
			return temp;
		}


	}

}
