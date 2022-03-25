//introduce the Scanner tool used for reading user input:
import java.util.Scanner;
//introduce Lists:
import java.util.ArrayList;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scan1 = new Scanner(System.in);
        printBase(4);
        printTreeDown(4);
        ArrayList<String> list =  new ArrayList<>();
        String text1 = "hello";
        list.add(text1);
        String text2 = "World";
        list.add(text2);
        printList(list);
        printTreeUp(4);
        printBase(4);
    }
    //personal method printTreeDown:
    public static void printTreeDown(int height) {
        int count = height+3;
        int rowCount = 1;
        while (rowCount<height+1) {
            printSpaces(rowCount-1);
            printStars(count);
            count-=2;
            rowCount++;
        }
    }
    //personal method printTreeUp:
    public static void printTreeUp(int height)  {
        int count = 1;
        int rowCount= 1;
        while (rowCount<height+1) {
            printSpaces(height-rowCount);
            printStars(count);
            count+=2;
            rowCount++;
        }
    }
    //personal method printStars:
    public static void printStars(int number1)  {
        int count = 0;
        while (count<number1) {
            System.out.print("*");
            count++;
        }
        System.out.println("");
    }
    //personal method printSpaces:
    public static void printSpaces(int number2)  {
        int count = 0;
        while (count<number2) {
            System.out.print(" ");
            count++;
        }
    }
    //personal method printBase:
    public static void printBase(int number3)  {
        int spaces = 0;
        if (number3>1) {
            spaces = number3 - 2;
        }
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.println("***");
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.println("***");
    }
    //personal method printList:
    public static void printList(ArrayList<String> theList) {
        for (String string: theList) {
            System.out.println(" "+string);
        }
    }
}
