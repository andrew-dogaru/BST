import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked list implementation which does not implement the List interface
 * since it only provides a limited subset of the methods.
 * 
 * It implements Iterable in order to be used in foreach statements.
 *
 * @param <E> list element type
 */
public class LinkedList<E> implements Iterable<E> {

	public Node<E> head;
	public Node<E> tail;
	private int size;

	public LinkedList() {
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		if (head == null || tail == null) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new PainterIterator();
	}

	public void add(E e) {
		if (head == null) {
			head = tail = new Node<E>(e, null, null);
			size++;
		} else {
			Node<E> n = new Node<E>(e, null, tail);
			tail.setNext(n);
			size++;
			tail = n;
		}
	}

	/**
	 * Removes element at index index.
	 * @param index
	 * @return the removed element or null if no element was removed
	 */
	public E remove(int index) {
		int curIndex = 0;
		Node<E> cur = head;
		if (head == null)
			return null;

		while (cur != null) {
			if (index == curIndex) {
				if (index == 0) {
					// remove element pointed by head
					head = head.next;
					if (head != null) {
						head.prev = null;
					} else {
						tail = null;
					}
				} else if (index == size - 1) {
					// remove last (tail) element					
					tail = tail.prev;
					tail.next = null;
				} else {
					cur.prev().setNext(cur.next());
					cur.next().setPrev(cur.prev());
					cur.setNext(null);
					cur.setPrev(null);
				}

				size--;
				break;
			}
			curIndex++;
			cur = cur.next();
		}
		if (cur == null)
			return null;
		else
			return cur.element();
	}

	private class PainterIterator implements Iterator<E> {

		/**
		 * Node whose element will be returned by subsequent call to next
		 */
		private Node<E> cur = head;

		public boolean hasNext() {

			if (cur == null) {
				return false;
			}
			return true;
		}

		@Override
		public E next() {
			if (hasNext() != false) {
				E tmp = cur.element();
				cur = cur.next();
				return tmp;
			}
			else
				// this is what the documentation of Iterator.next() states
			    throw new NoSuchElementException();
		}
	}

	private static class Node<T> {
		private T element;
		private Node<T> next;
		private Node<T> prev;

		public Node(T it, Node<T> nextVal, Node<T> prevVal) {
			element = it;
			next = nextVal;
			prev = prevVal;
		}

		public Node<T> next() {
			return next;
		}

		public Node<T> prev() {
			return prev;
		}

		public T element() {
			return element;
		}

		public Node<T> setNext(Node<T> nextval) {
			return next = nextval;
		}

		public Node<T> setPrev(Node<T> prevVal) {
			return prev = prevVal;
		}
	}
}
