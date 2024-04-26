package Test;

import java.util.Random;
import java.util.random.*;

public class test {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = a.clone();

        a[0]= 5;

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i] + " ");
        }
    }
}
