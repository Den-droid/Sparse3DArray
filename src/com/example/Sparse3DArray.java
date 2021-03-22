package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Sparse3DArray {
    private class Node {
        public int index1;
        public int index2;
        public int index3;
        public int data;

        public Node(int index1, int index2, int index3, int data) {
            this.index1 = index1;
            this.index2 = index2;
            this.index3 = index3;
            this.data = data;
        }

        public int getIndex1() {
            return index1;
        }

        public int getIndex2() {
            return index2;
        }

        public int getIndex3() {
            return index3;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }

    public Sparse3DArray() {
        nonEmptyElements = new ArrayList<>();
    }

    private ArrayList<Node> nonEmptyElements;

    public ArrayList<Node> getNonEmptyElements() {
        return nonEmptyElements;
    }

    public void setNonEmptyElements(int[][][] allElements) {// Time: O(n^3), Size: O(k), k - count of none-0 elements
        for (int i = 0; i < allElements.length; i++) {
            for (int j = 0; j < allElements[i].length; j++) {
                for (int k = 0; k < allElements[i][j].length; k++) {
                    if (allElements[i][j][k] != 0) { // Time: O(1), Size: O(1)
                        Node node = new Node(i, j, k, allElements[i][j][k]); // Time: O(1), Size: O(1)
                        this.getNonEmptyElements().add(node); // Time: O(1), Size: O(1)
                    }
                }
            }
        }
    }

    public void insert(int index, Node node) { // Time: O(n), Size: O(n)
        ArrayList<Node> list = new ArrayList<>(); // Time: O(1), Size: O(1)
        for (int i = 0; i < index; i++) {
            list.add(this.getNonEmptyElements().get(i));
        }
        list.add(node); // Time: O(1), Size: O(1)
        for (int i = index; i < this.getNonEmptyElements().size(); i++) {
            list.add(this.getNonEmptyElements().get(i));
        }
        this.nonEmptyElements = list; // Time: O(1), Size: O(1)
    }

    public void addElement(int index1, int index2, int index3, int data) {
        if (data != 0) { // Time: O(1), Size: O(1)
            Node node;  // Time: O(1), Size: O(1)
            Node toInsert = new Node(index1, index2, index3, data);  // Time: O(1), Size: O(1)
            for (int i = 0; i < this.getNonEmptyElements().size(); i++) { // k iterations, Time: O(k*n), Size: O(n)
                node = this.getNonEmptyElements().get(i); // Time: O(1), Size: O(1)
                if (index1 == node.getIndex1()) { // Time: O(1), Size: O(1)
                    if (index2 < node.getIndex2()) { // Time: O(1), Size: O(1)
                        insert(i, toInsert); // Time: O(n), Size: O(n)
                        break;
                    } else if (index2 == node.getIndex2()) { // Time: O(1), Size: O(1)
                        if (index3 < node.getIndex3()) { // Time: O(1), Size: O(1)
                            insert(i, toInsert); // Time: O(n), Size: O(n)
                            break;
                        } else if (index3 == node.getIndex3()) { // Time: O(1), Size: O(1)
                            throw new IllegalArgumentException("Element with such indices has already existed");
                        }
                    }
                } else if (index1 < node.getIndex1()) { // Time: O(1), Size: O(1)
                    insert(i, toInsert); // Time: O(n), Size: O(n)
                    break;
                }

                if (i == this.getNonEmptyElements().size()) {  // Time: O(1), Size: O(1)
                    this.getNonEmptyElements().add(toInsert);  // Time: O(1), Size: O(1)
                }
            }
        }
    }

    public Integer getElementByIndices(int index1, int index2, int index3) {
        Integer value = null; // Time: O(1), Size: O(1)
        for (Node node : this.getNonEmptyElements()) { // n iterations, Time: ???, Size: O(1)
            if (node.getIndex1() == index1 && node.getIndex2() == index2 &&
                    node.getIndex3() == index3) { // Time: O(1), Size: O(1)
                value = node.getData(); // Time: O(1), Size: O(1)
                break;
            }
        }
        return value;
    }

    //Time:
    //worst case: go into if statement on last iteration. O(n)
    //best case: go into if statement on first iteration. O(1)
    //average: go into second if statement on k iteration. O(k)


    public void setElementByIndices(int index1, int index2, int index3, int data) {
        for (Node node : this.getNonEmptyElements()) { // n iterations, Time: ???, Size: ???
            if (node.getIndex1() == index1 && node.getIndex2() == index2 &&
                    node.getIndex3() == index3) { // Time: O(1), Size: O(1)
                if (node.getData() == 0 && data != 0) { // Time: O(1), Size: O(1)
                    this.addElement(index1, index2, index3, data); // inside this method
                } else if (data == 0) { // Time: O(1), Size: O(1)
                    this.getNonEmptyElements().remove(node); // Time: O(1), Size: O(1)
                } else {
                    node.setData(data); // Time: O(1), Size: O(1)
                }
                break;
            }
        }
    }

    // Time and size
    // If we are going into first if statement it is a worst case
    // If not: time=O(k), size=O(1), k - count of iterations

    public void sortNonempty() {
        int[] values = new int[this.getNonEmptyElements().size()]; // Time: O(1), Size: O(n)
        int i = 0; // Time: O(1), Size: O(1)
        for (Node elem : this.getNonEmptyElements()) { // Time: O(n), Size: O(n)
            values[i] = elem.getData(); // Time: O(1), Size: O(1)
            i++; // Time: O(1), Size: O(1)
        }
        Arrays.sort(values); // Time: O(n*log(n)), Size: O(n)
        for (int j = 0; j < this.getNonEmptyElements().size(); j++) { // Time: O(n), Size: O(1)
            this.getNonEmptyElements().get(j).setData(values[j]); // Time: O(1), Size: O(1)
        }
    }

    public void print() {
        for (Node node : this.getNonEmptyElements()) {
            System.out.println("Elem: " + node.getData() + ";index1: " + node.getIndex1() + ";index2:" +
                    node.getIndex2() + ";index3:" + node.getIndex3());
        }
    }
}