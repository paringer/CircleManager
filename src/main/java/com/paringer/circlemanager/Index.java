package com.paringer.circlemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class Index {

    ConcurrentSkipListMap<Integer, HashMap<Integer, Circle>> indexCircles = new ConcurrentSkipListMap<>();

    /**
     * Return the number of circles with members of size n passed
     * into the function. Try to optimize this to be as performant as possible.
     */

    synchronized public Integer query(Integer memberCount) {
        if (indexCircles.containsKey(memberCount))
            return indexCircles.get(memberCount).size();
        else
            return 0;
    }

    synchronized public void index(ConcurrentSkipListMap<Integer, Circle> myCircles) {
        indexCircles.clear();
        for (Map.Entry<Integer, Circle> it : myCircles.entrySet()) {
            try {
                updateIndex(it.getValue());
            } catch (NullPointerException ex) {
                continue;
            }
        }
    }

    synchronized public void updateIndex(Circle circle) {
        if (!indexCircles.containsKey(circle.members))
            indexCircles.put(circle.members, new HashMap<Integer, Circle>());
        HashMap<Integer, Circle> map = indexCircles.get(circle.members);
        map.put(circle.id, circle);
    }

    synchronized public void cleanIndexRecord(Circle circle) {
        if (indexCircles.containsKey(circle.members)) {
            indexCircles.get(circle.members).remove(circle.id);
        }
    }

    synchronized public boolean isEmpty() {
        return indexCircles.size() == 0;
    }

}
