package com.paringer.circlemanager;

import java.util.ArrayList;

class Solution implements Constants {

    public static void main(String[] args) {
        // this method is here to allow the testing of this challenge from the commandline
        int maxMemberId = 0;
        System.out.println("Member and Circle construction");
        System.out.println(new Member(++maxMemberId, Utilities.randomName(), Math.random() * 360 - 180, Math.random() * 180 - 90));
        System.out.println(new Circle(1, Utilities.randomName(),1));
        System.out.println("==================================================");
        System.out.println("Random Circles, distance check");
        Circle circle = new Circle(1, Utilities.randomName(), 10);
        System.out.println(circle);
        circle.query(1);
        System.out.println("Distance 10 000 km :");
        System.out.println(circle.query(10_000_000.0, 53.6, 50.4).toString().replace("}, ", "},\r\n "));
        System.out.println("==================================================");
        System.out.println("CircleManager and Indexing check");
        CircleManagerImpl impl = new CircleManagerImpl();
        impl.createCircle(1);
        impl.addMember(1);
        impl.addMember(2);
        impl.createCircle(3);
        impl.addMember(4);
        impl.addMember(5);
        System.out.println(impl.query(2));
        System.out.println(impl.query(1));//4
        impl.removeMember(4);
        impl.removeMember(5);
        System.out.println(impl.query(1));//2
        System.out.println("==================================================");
        System.out.println("Query check");
        impl.saveJSON(CIRCLES_JSON);
        CircleManagerImpl impl2 = impl.loadJSON(CIRCLES_JSON);
        System.out.println(impl2);
        System.out.println(impl2.query(2));
        System.out.println(impl2.query(1));
        impl2.removeMember(2);
        impl2.removeMember(3);
        impl2.removeMember(4);
        System.out.println(impl2.query(2));
        System.out.println(impl2.query(1));
        System.out.println(impl2.query(0));
        System.out.println("==================================================");
        System.out.println("Methods to work with duplicates check");
        ArrayList<Circle> list = new ArrayList<>();
        list.add(new Circle(1, "1", 1));
        list.add(new Circle(1, "3", 3));
        list.add(new Circle(1, "2", 2));
        list.add(new Circle(2, "4", 1));
        list.add(new Circle(2, "5", 2));
        System.out.println("duplicates found:" + impl2.findDuplicates(list));
        System.out.println("unique id found:" + (list.size()-impl2.findDuplicates(list)));
        System.out.println(impl2.mergeDuplicates(list).toString().replace("}, ", "}\r\n "));
        System.out.println("==================================================");
    }
}