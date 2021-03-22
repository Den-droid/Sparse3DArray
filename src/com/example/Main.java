package com.example;

public class Main {

    public static void main(String[] args) {
        int[][][] data = {
                {
                        {1, 0, 3, 6, 0, 0},
                        {5, 8, 7, 0, 0, 9}
                },
                {
                        {15, 19, 0, 6, 1, 0},
                        {25, 76, 12, 0, 11, 21}
                }
        };
        try{
            Sparse3DArray arr = new Sparse3DArray();
            arr.setNonEmptyElements(data);
            arr.addElement(0,1,4,79);
            arr.sortNonempty();
            arr.print();
            System.out.println("Done");
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }
}