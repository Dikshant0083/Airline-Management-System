package airline.util;

public class LinkedList {
    private class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
        }
    }

    private Node head;

    public void add(Object data) {
        Node newNode = new Node(data);
        if (head == null)
            head = newNode;
        else {
            Node current = head;
            while (current.next != null)
                current = current.next;
            current.next = newNode;
        }
    }

    public Object[] toArray() {
        int size = size();
        Object[] array = new Object[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public boolean remove(Object data) {
        if (head == null)
            return false;

        if (head.data.equals(data)) {
            head = head.next;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
