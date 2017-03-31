package test;

import java.util.function.Supplier;

/**
 * Created by ZhangShaowei on 2017/2/22.
 */
public class Car {

    public static Car create(final Supplier<Car> supplier){
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("collide" + car.toString());
    }


    public void follow(final Car another) {
        System.out.println("Following the" + another.toString());
    }

    public void repair() {
        System.out.println( "Repaired " + this.toString() );
    }





}
