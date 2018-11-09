package com.depas.test.passbyvalue;

import java.awt.Point;

public class Swap {

    public static void tricky(Point arg1, Point arg2) {
        arg1.x = 100;
        arg1.y = 100;
        Point temp = arg1;
        arg1 = arg2;
        arg2 = temp;
    }

    /*
        If you change the value of wrapper objects inside the method like this: x += 2, the change is not reflected
        outside the method, since wrapper objects are immutable. They create a new instance each time their state is
        modified. For more information about immutable classes, check out "How to create an immutable class in Java".
        String objects work similarly to wrappers, so the above rules apply also on strings.
     */
    private static void modifyWrappers(Integer x, Integer y) {
        x = new Integer(5);
        y = new Integer(10);

        // this is the same thing
//        x = 5;
//        y = 10;

        System.out.print("Values of x & y in modify wrapper method: ");
        System.out.println("x = " + x.intValue() + " ; y = " + y.intValue());
    }

    public static void main(String [] args) {
        Point pnt1 = new Point(0,0);
        Point pnt2 = new Point(0,0);
        System.out.println("X: " + pnt1.x + " Y: " +pnt1.y);
        System.out.println("X: " + pnt2.x + " Y: " +pnt2.y);
        System.out.println(" ");
        tricky(pnt1,pnt2);
        System.out.println("X: " + pnt1.x + " Y:" + pnt1.y);
        System.out.println("X: " + pnt2.x + " Y: " +pnt2.y);

        System.out.println("");
        System.out.println("***************** Next Test ******************************");
        System.out.println("");

        Integer obj1 = new Integer(1);
        Integer obj2 = new Integer(2);
        System.out.print("Values of obj1 & obj2 before wrapper modification: ");
        System.out.println("obj1 = " + obj1.intValue() + " ; obj2 = " + obj2.intValue());

        modifyWrappers(obj1, obj2);

        System.out.print("Values of obj1 & obj2 after wrapper modification: ");
        System.out.println("obj1 = " + obj1.intValue() + " ; obj2 = " + obj2.intValue());
    }

}
