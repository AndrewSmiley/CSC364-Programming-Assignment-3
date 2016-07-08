package csc364pa3;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyDoublyLinkedList<E> extends  MyAbstractSequentialList<E> {
    private Node head;
    private Node tail;


    private int size;

    public MyDoublyLinkedList() {
//        public DoublyLinkedList() {
            head = new Node(null, null, null);
            tail= new Node(null, null, null);
            head.next = tail;
            tail.prev = head;
//        }
    }
    /**
     * this class keeps track of each element information
     * @author java2novice
     *
     */
    private class Node {
        E element;
        Node next;
        Node prev;

        public Node(E element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    class DLListIterator implements ListIterator<E>{
//        Node currentNode = null;
//        Node previousNode = null;
//        int index;
        private Node currentNode = head.next;  // the node that is returned by next()
        private Node previousNode = null;      // the last node to be returned by prev() or next()
        // reset to null upon intervening remove() or add()
        private int index = 0;


//        @Override
//        public String toString(){
//            String str = "[";
//            currentNode =head;
//            while (hasNext()){
//                if(currentNode.prev == null){
//                    str = str+currentNode.element;
//                }else{
//                    str = str+", "+currentNode.element;
//                }
//                currentNode = currentNode.next;
//            }
//
//
//            return str+", "+currentNode.element+"]";
//        }
        @Override
        public boolean hasNext() {
            //i hate java so much...
//            resetNode(index);
//            currentNode= head;
            //just go grab the node
//            for(int i = 0; i < this.index; i++){
//                currentNode = currentNode.next;
//
//            }
//            currentNode = tmp;
//            return currentNode.next != null;
//            return currentNode.next != null;
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            previousNode = currentNode;
            E e = currentNode.element;
            currentNode = currentNode.next;
            index++;
            return e;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }
            currentNode = currentNode.prev;
            index--;
            previousNode= currentNode;
            return currentNode.element;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index-1;
        }

        @Override
        public void remove() {
            if (previousNode == null) throw new IllegalStateException();
            Node x = previousNode.prev;
            Node y = previousNode.next;
            x.next = y;
            y.prev = x;
            size--;
            if (currentNode == previousNode) {
                currentNode = y;
            }
            else {
                index--;
                previousNode = null;
            }
        }

        @Override
        public void set(E e) {
            if (previousNode == null){
                throw new IllegalStateException();
            }
            previousNode.element= e;
        }

        @Override
        public void add(E e) {
/*
            //handle the edge cases
            //if we're adding to the tail
            if(index == size) {
                Node tmp = tail;
                Node newTail = new Node(e, null, tmp);
                tmp.next = newTail;
                tail = newTail;
            }
            else if(index == 0) {
                Node newHead = new Node(e, head, null);
                head.prev = newHead;
                head = newHead;
            } else {
                //wires are getting crossed somewhere...
                //going to just reset index here, probably a java issue
                Node tmp = head;
                for(int i =0; i < index-1; i++){
                    tmp = tmp.next;

                }
                //ok so now that we have a good current index...
                Node newNode = new Node(e, tmp.next, tmp);
                //this should be getting caught in the edge case...
                if(tmp.next != null){
                    tmp.next.prev = newNode;
                }
                tmp.next = newNode;
            }


            size++;
        }*/

//            Node x = currentNode.prev;
            Node y = new Node(e, null, null);
//            Node z = currentNode;
            if(currentNode.prev != null){
                currentNode.prev.next = y;
            }
            y.prev =currentNode.prev;
            currentNode.prev = y;
            y.next = currentNode;

//            x.next = y;
//            y.next = currentNode;
//            z.prev = y;
//            y.prev = currentNode.prev;
            size++;
            index++;
            previousNode= null;

        }
    }

    /**
     * Our to string function
     * @return
     */
////    @Override
//    public String toString() {
//         return listIterator(0).toString();
//    }
    @Override
    public E getFirst() {
        return head.element;
    }

    @Override
    public E getLast() {
        return tail.element;
    }

    @Override
    public void addFirst(E e) {
        //this is so much easier in c/c++
        Node newHead = new Node(e, head, tail); //create the new head element
        this.head = newHead;

    }

    @Override
    public void addLast(E e) {
        Node newTail = new Node(e, tail.prev, head);
        this.tail = newTail;
    }

    @Override
    public E removeFirst() {
        if (this.head == null || size == 0) {
            throw new NoSuchElementException("The head is empty");
        }
        Node tmp = this.head.next;
        E returnValue=this.head.element;
        this.head = tmp;
        this.tail.next = tmp;
        this.head.prev = tail;
        this.size--;
        return returnValue;

    }

    @Override
    public E removeLast() {
        if(this.tail == null || size == 0)
            throw new NoSuchElementException("The tail is empty");
        Node tmp = this.tail.prev;
        E returnValue = this.tail.element;
        this.tail= tmp;
        //we have to point the head and tail back to one another
        this.tail.next = this.head;
        this.head.prev =this.tail;
        this.size--;
        return returnValue;
    }

//    public void add(E e){
//
//    }

    @Override
    public ListIterator<E> listIterator(int index) {
        ListIterator itr = new DLListIterator();
        for(int i = 0; i < index;i++){
            itr.next();
        }
        return itr;
    }

    @Override
    public void add(int index, E e) {
        if(index == 0){
            Node newNode = new Node(e, head, null);
            head.prev =head;

            head =
            return
        }
        listIterator(index).add(e);
//        Node last = tail.prev;
//        Node x = new Node(e, tail, last);
//        tail.prev = x;
//        last.next = x;
//        size++;
//        if(head == null){
//            //assuming we're working from a new linked list
//            head = new Node(e, null, null);
//            tail = head;
//            size++;
//
//        }else {
//            ListIterator<E> ltr = listIterator(index);
//            ltr.add(e);
//        }

    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) > -1;

    }

    @Override
    public E get(int index) {
        Node tmp = head;
        //just go grab the node
        for(int i = 0; i < index; i++){
            tmp = tmp.next;

        }
        return tmp.element;
    }

    @Override
    public int indexOf(E e) {
        Node tmp = head;
        for(int i = 0; i < size; i++){
            if(tmp.element.equals(e)){
                return i;
            }else{
                tmp = tmp.next;
            }
        }
        //should this be 0 indexed?
        return -1;

    }

    @Override
    public int lastIndexOf(E e) {
        return 0;
    }

    @Override
    public E remove(int index) {
        Node tmp = head;
        //just go grab the node
        for(int i = 0; i < index; i++){
                tmp = tmp.next;

        }
        Node lower = tmp.prev;
        Node upper = tmp.next;
        //obviously we have the head then
        if(lower==null){
            head = upper;
            head.prev = null;
            return  tmp.element;
        }
        else if(upper == null){
            tail = lower;
            tail.next = null;
            return tmp.element;
        }else{
            upper.prev = lower;
            lower.next = upper;
            return tmp.element;
        }
    }



    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        if (size > 0) {
            for (E item : this)
                s.append(item + ", ");
            s.replace(s.length()-2, s.length(), "");
        }
        s.append("]");
        return s.toString();
    }
    @Override
    public Object set(int index, E e) {
        //i have no idea what he's looking for here
        Node tmp = head;
        //just go grab the node
        for(int i = 0; i < size; i++){
            tmp = tmp.next;

        }
        E tmpElement = tmp.element;
        tmp.element = e;
        return tmpElement;

    }
}