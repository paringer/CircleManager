package com.paringer.circlemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * represents Index
 */
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

    /**
     * creates index and indexes all circles by members count
     * @param myCircles map of circles by circle id
     */
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

    /**
     * adds Circle to index by members count. can't remove old record with wrong number of members.
     * @param circle circle
     */
    synchronized public void updateIndex(Circle circle) {
        if (!indexCircles.containsKey(circle.members))
            indexCircles.put(circle.members, new HashMap<Integer, Circle>());
        HashMap<Integer, Circle> map = indexCircles.get(circle.members);
        map.put(circle.id, circle);
    }

    /**
     * removes circle from index by members count
     * @param circle circle
     */
    synchronized public void cleanIndexRecord(Circle circle) {
        if (indexCircles.containsKey(circle.members)) {
            indexCircles.get(circle.members).remove(circle.id);
        }
    }

    /**
     * method isEmpty
     * @return empty or not
     */
    synchronized public boolean isEmpty() {
        return indexCircles.size() == 0;
    }

}
