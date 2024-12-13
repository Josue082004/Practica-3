package com.Practica2.rest.controller.tda.list;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LinkedList<E> {
    private Node<E> head; 
    private Node<E> tail;
    private int size; 

    public LinkedList() {
        this.head = null;
        this.tail = null; 
        this.size = 0; 
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public void add(E data) {
        Node<E> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    public void update(E data, int index) {
        getNode(index).setInfo(data);
    }

    public E delete(int index) {
        if (isEmpty()) {
            throw new IllegalStateException("La lista está vacía");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
        Node<E> toDelete;
        if (index == 0) {
            toDelete = head;
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
        } else {
            Node<E> prev = getNode(index - 1);
            toDelete = prev.getNext();
            prev.setNext(toDelete.getNext());
            if (toDelete == tail) {
                tail = prev;
            }
        }
        size--;
        return toDelete.getInfo();
    }

    public E[] toArray(E[] a) {
        if (a.length < size) {
            a = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            a[i] = current.getInfo();
            current = current.getNext();
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    public void fromArray(E[] array) {
        reset();
        for (E data : array) {
            add(data);
        }
    }

    public void reset() {
        head = null;
        tail = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    private boolean compare(Object a, Object b, int type) {
        if (a == null || b == null) {
            return false;
        }
        if (a instanceof Comparable && b instanceof Comparable) {
            Comparable compA = (Comparable) a;
            Comparable compB = (Comparable) b;
            return type == 1 ? compA.compareTo(compB) > 0 : compA.compareTo(compB) < 0;
        }
        return false;
    }

    private Object getAttributeValue(E obj, String attribute) throws Exception {
        Method method = obj.getClass()
                .getMethod("get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1));
        return method.invoke(obj);
    }

    private boolean compareByAttribute(String attribute, E a, E b, int type) throws Exception {
        Object valueA = getAttributeValue(a, attribute);
        Object valueB = getAttributeValue(b, attribute);
        return compare(valueA, valueB, type);
    }

 // Quicksort    
    public void quickSort(String attribute, int type) throws Exception {
        if (!isEmpty()) {
            E[] array = toArray((E[]) java.lang.reflect.Array.newInstance(head.getInfo().getClass(), size));
            quickSortHelper(array, 0, array.length - 1, attribute, type);
            fromArray(array);
        }
    }

    private void quickSortHelper(E[] array, int low, int high, String attribute, int type) throws Exception {
        if (low < high) {
            int pi = partition(array, low, high, attribute, type);
            quickSortHelper(array, low, pi - 1, attribute, type);
            quickSortHelper(array, pi + 1, high, attribute, type);
        }
    }

    private int partition(E[] array, int low, int high, String attribute, int type) throws Exception {
        E pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compareByAttribute(attribute, array[j], pivot, type)) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        E temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

 //Mergesort
    public void mergeSort(String attribute, int type) throws Exception {
        if (!isEmpty()) {
            E[] array = toArray((E[]) java.lang.reflect.Array.newInstance(head.getInfo().getClass(), size));
            array = mergeSortHelper(array, attribute, type);
            fromArray(array);
        }
    }

    private E[] mergeSortHelper(E[] array, String attribute, int type) throws Exception {
        if (array.length <= 1) {
            return array;
        }
        int mid = array.length / 2;
        E[] left = Arrays.copyOfRange(array, 0, mid);
        E[] right = Arrays.copyOfRange(array, mid, array.length);
        left = mergeSortHelper(left, attribute, type);
        right = mergeSortHelper(right, attribute, type);
        return merge(left, right, attribute, type);
    }

    private E[] merge(E[] left, E[] right, String attribute, int type) throws Exception {
        E[] result = (E[]) java.lang.reflect.Array.newInstance(left.getClass().getComponentType(), left.length + right.length);
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (compareByAttribute(attribute, left[i], right[j], type)) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }

 //Shellsort
    public void shellSort(String attribute, int type) throws Exception {
        if (!isEmpty()) {
            E[] array = toArray((E[]) java.lang.reflect.Array.newInstance(head.getInfo().getClass(), size));
            int n = array.length;
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    E temp = array[i];
                    int j;
                    for (j = i; j >= gap && compareByAttribute(attribute, array[j - gap], temp, type); j -= gap) {
                        array[j] = array[j - gap];
                    }
                    array[j] = temp;
                }
            }
            fromArray(array);
        }
    }

 //Buusqueda binaria
    public E binarySearch(String attribute, Comparable value) throws Exception {
        if (isEmpty()) {
            return null;
        }
        E[] array = toArray((E[]) java.lang.reflect.Array.newInstance(head.getInfo().getClass(), size));
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            E midVal = array[mid];
            Comparable midAttr = (Comparable) getAttributeValue(midVal, attribute);
            int cmp = midAttr.compareTo(value);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return midVal;
            }
        }
        return null;
    }

 //busqueda lineal binaria
    public LinkedList<E> linearBinarySearch(String attribute, Comparable value) throws Exception {
        LinkedList<E> result = new LinkedList<>();
        if (!isEmpty()) {
            E[] array = toArray((E[]) java.lang.reflect.Array.newInstance(head.getInfo().getClass(), size));
            for (E element : array) {
                Comparable attrValue = (Comparable) getAttributeValue(element, attribute);
                if (attrValue.compareTo(value) == 0) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    public E get(int index) {
        return getNode(index).getInfo();
    }

    public void toList(E[] array) {
        Node<E> current = head;
        int index = 0;
        while (current != null && index < array.length) {
            array[index++] = current.getInfo();
            current = current.getNext();
        }
    }
}