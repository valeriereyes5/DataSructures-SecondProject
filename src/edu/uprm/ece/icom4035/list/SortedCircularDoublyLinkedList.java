package edu.uprm.ece.icom4035.list;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;




public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {
	private DNode<E> head, trailer; 
	private int currentSize; 

	public SortedCircularDoublyLinkedList() {//complete
		head= new DNode();
		trailer = new DNode();
		this.head.setElement(null);
		this.head.setNext(head);
		currentSize=0;
	}
	@Override
	public Iterator<E> iterator() {//done
		return new FowardListIterator();
		// TODO Auto-generated method stub
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



	@Override
	public boolean add(E obj) {
		// TODO Auto-generated method stub
		
		
		//node is the first
		if(this.isEmpty()) {
			DNode<E>  newNode = new DNode<E>(obj, head, head);
			head.setNext(newNode);
			head.getNext().setPrev(newNode);
			currentSize++;
			
			return true;
		}
		//node is header.prev aka the tail
		if(head.getNext().getElement().compareTo(obj)>0) {
			DNode<E>  newNode = new DNode<E>(obj, head, head.getNext());
			head.setNext(newNode);
			head.setPrev(newNode);
			currentSize++;
			return true;
		}
		//
		DNode<E> temp = head.getNext();
		while(temp.getNext()!=head) {
			if(temp.getElement().compareTo(obj)<0) {
				DNode<E>  newNode = new DNode<E>(obj, temp.getPrev(), temp.getNext());
				temp.setNext(newNode);
				temp.getNext().setPrev(newNode);
				currentSize++;
				return true;
			}
			temp = temp.getNext();
		}//temp-obj
		return false;
	}

	@Override
	public int size() {//done
		// TODO Auto-generated method stub
		return this.currentSize;
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
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
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
	public boolean contains(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {//done
		// TODO Auto-generated method stub
		return this.currentSize==0;
	}

	@Override
	public Iterator<E> iterator(int index) {
		// TODO Auto-generated method stub
		return null;
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
	public ReverseIterator<E> reverseIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		// TODO Auto-generated method stub
		
		return new ListReverseIteratorIndex (index);
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



	public class ListReverseIteratorIndex implements ReverseIterator<E>{ //complete
		DNode<E> curr = null;
		int i=0;
		int index =0;
		public ListReverseIteratorIndex(int index) {
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
			curr = curr.getNext();
			return temp;
		}

	}

}
