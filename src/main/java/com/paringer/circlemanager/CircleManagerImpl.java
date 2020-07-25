package com.paringer.circlemanager;

import com.google.gson.GsonBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class CircleManagerImpl implements CircleManager {
    ConcurrentSkipListMap<Integer, Circle> myCircles = new ConcurrentSkipListMap<>();
    Index myIndex = new Index();

    /**
     * Creates a new circle with 1 member and the provided ID
     */
    @Override
    synchronized public void createCircle(Integer circleId) {
        if (!myCircles.containsKey(circleId)) {
            Circle circle = new Circle(circleId, Utilities.randomName(), 1);
            myCircles.put(circleId, circle);
            myIndex.updateIndex(circle);
        }
    }

    /**
     * Adds a member to a circle of the id passed in to the signature.
     * In the case that the circle does not exist they should create it.
     */
    @Override
    synchronized public void addMember(Integer circleId) {
        if (!myCircles.containsKey(circleId)) {
            createCircle(circleId);
        } else {
            myIndex.cleanIndexRecord(myCircles.get(circleId));
            myCircles.get(circleId).addMember();
            myIndex.updateIndex(myCircles.get(circleId));
        }
        myIndex.updateIndex(myCircles.get(circleId));
    }

    /**
     * Removes a member from the circle of the id passed in to
     * the signature. Should remove circles with no members
     */
    @Override
    synchronized public void removeMember(Integer circleId) {
        try {
            if (myCircles.containsKey(circleId)) {
                Circle c = myCircles.get(circleId);
                myIndex.cleanIndexRecord(c);
                c.removeMember();
                if (c.members <= 0) {
                    myCircles.remove(circleId);
                } else {
                    myIndex.updateIndex(c);
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Return the number of circles with members of size n passed
     * into the function. Try to optimize this to be as performant as possible.
     */
    @Override
    synchronized public Integer query(Integer memberCount) {
        if (myIndex.isEmpty()) myIndex.index(myCircles);
        return myIndex.query(memberCount);
    }

    synchronized public Integer findDuplicates(List<Circle> list) {
        int duplicates = 0;
        HashMap<Integer, Circle> map = new HashMap<>();
        for (Circle circle : list) {
            if (map.containsKey(circle.id)) {
                duplicates++;
            } else {
                map.put(circle.id, circle);
            }
        }
        return duplicates;
    }

    synchronized public List<Circle> mergeDuplicates(List<Circle> list) {
        int duplicates = 0;
        HashMap<Integer, Circle> map = new HashMap<>();
        for (Circle circle : list) {
            if (map.containsKey(circle.id)) {
                duplicates++;
                if(map.get(circle.id).members < circle.members){
                    map.put(circle.id, circle);
                }
            } else {
                map.put(circle.id, circle);
            }
        }
        return new ArrayList<>(map.values());
    }

    synchronized public void print() {
        System.out.println(this);
    }


    synchronized public void saveJSON(String fileName) {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this);
        try {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public CircleManagerImpl loadJSON(String fileName) {
        Map<Integer, Circle> myCircles = new HashMap<>();
        try {
            String text = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            CircleManagerImpl cm = new GsonBuilder().setPrettyPrinting().setLenient().create().fromJson(text, CircleManagerImpl.class);
            myCircles = cm.myCircles;
            return cm;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "com.paringer.circlemanager.CircleManagerImpl{" +
                "myCircles=\r\n\r\n" + myCircles.toString().replace("}, ", "},\r\n\r\n ") +
                "}\r\n==============";
    }
}
