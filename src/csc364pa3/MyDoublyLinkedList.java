package csc364pa3;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyDoublyLinkedList<E> extends  MyAbstractSequentialList<E> {
    private Node head;
    private Node tail;
//    private int size;

    public MyDoublyLinkedList() {
        size = 0;
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
        Node currentNode;
        Node previousNode;
        int index;
        public DLListIterator(int index){
            if (index > size) {
                throw new NoSuchElementException("Index out of bounds");
            }

//            Node tmp = head;


//            previousNode = tmp==null? null: tmp.prev;
            this.index = index;
            currentNode = head;
            for(int i = 0; i < index; i++){
                currentNode= currentNode.next;
            }

            System.out.print("fuck");
        }

//        public void resetNode(int i){
//            Node tmp = head;
//            for(int j = 0; j < index; j++){
//                tmp = tmp.next;
//            }
//            currentNode = tmp;
//        }

        @Override
        public String toString(){
            String str = "[";
            currentNode =head;
            while (hasNext()){
                if(currentNode.prev == null){
                    str = str+currentNode.element;
                }else{
                    str = str+", "+currentNode.element;
                }
                currentNode = currentNode.next;
            }


            return str+", "+currentNode.element+"]";
        }
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
            return currentNode.next != null;
        }

        @Override
        public E next() {
            //i hate java so much...
            Node tmp = head;
            //just go grab the node
            for(int i = 0; i < this.index; i++){
                tmp = tmp.next;

            }
            currentNode = tmp;
            previousNode = currentNode;
            currentNode = currentNode.next;
            index++;
            return currentNode.element;
        }

        @Override
        public boolean hasPrevious() {
            //i hate java so much...
            Node tmp = head;
            //just go grab the node
            for(int i = 0; i < this.index; i++){
                tmp = tmp.next;

            }
            currentNode = tmp;

            return tmp.prev != null;
        }

        @Override
        public E previous() {
            //i hate java so much...
            Node tmp = head;
            //just go grab the node
            for(int i = 0; i < this.index; i++){
                tmp = tmp.next;

            }
            currentNode = tmp;
            previousNode = currentNode;
            currentNode = currentNode.prev;
            index--;
            return currentNode.element;
        }

        @Override
        public int nextIndex() {
            return index+1;
        }

        @Override
        public int previousIndex() {
            return index-1;
        }

        @Override
        public void remove() {
            //remove the current node
            if(index == size){
                Node tmpTail = currentNode.prev;
                tmpTail.next= null;
                tail = tmpTail;
                currentNode = tail;

            }
            if(index == 0){
                Node tmpHead = currentNode.next;
                tmpHead.prev = null;
                head = tmpHead;
                currentNode = head;
            }


        }

        @Override
        public void set(E e) {
            previousNode.element = e;
        }

        @Override
        public void add(E e) {

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
        }
    }

    /**
     * Our to string function
     * @return
     */
//    @Override
    public String toString() {
         return listIterator(0).toString();
    }
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
        if (this.head == null) {
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
        if(this.tail == null)
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

    @Override
    public ListIterator<E> listIterator(int index) {
        return new DLListIterator(index);
    }

    @Override
    public void add(int index, E e) {
        if(head == null){
            //assuming we're working from a new linked list
            head = new Node(e, null, null);
            tail = head;
            size++;

        }else {
            ListIterator<E> ltr = listIterator(index);
            ltr.add(e);
        }
//        Node tmp = head;
//        if(index > size){
//            throw new IllegalStateException("We cannot insert an element here");
//        }
//
//        if (size > 0) {
//            for(int i = 0; i  < index; i++){
//                tmp = tmp.next;
//            }
//            Node newNode = new Node(e, (tmp == null) ? null: tmp.next, tmp );
//            if (tmp == null || tmp.next == null) {
//                this.tail =newNode;
//            }else{
//                tmp.next.prev = newNode;
//            }
//            tmp.next = newNode;
//        }else{
//            this.head = new Node(e, null, null);
//        }
//        this.size++;
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