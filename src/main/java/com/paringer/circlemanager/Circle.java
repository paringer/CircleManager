package com.paringer.circlemanager;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Represents Circle of people called Members
 */
public class Circle {
    int id; // # of id in the com.paringer.circlemanager.Circle
    String name;
    int members; // # of members in the com.paringer.circlemanager.Circle
    int maxMemberId = 0;
    ConcurrentSkipListMap<Integer, Member> myMembers = new ConcurrentSkipListMap<>();

    /**
     * constructor, used by custom tests
     */
    public Circle(int id, String name, int members) {

        if (members < 0)
            throw new IllegalArgumentException("circle creation failed, members count should be greater then or equal to zero ");
        for (int i = 0; i < members; i++) {
            addMember();
        }
        this.id = id;
        this.name = name;
        this.members = members;
    }

    /**
     * constructor,
     * used by Gson serializer
     */
    public Circle(int id, String name, int members, int maxMemberId, ConcurrentSkipListMap<Integer, Member> myMembers) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.maxMemberId = maxMemberId;
        if (members < 0)
            throw new IllegalArgumentException("circle creation failed, members count should be greater then or equal to zero ");
        this.myMembers = myMembers;
    }

    /**
     * Adds a member to the circle with Member id passed in to
     * the signature. Should remove circles with no members
     */
    public void addMember() {
        Member member = new Member(++maxMemberId, Utilities.randomName(), Math.random() * 360 - 180, Math.random() * 180 - 90);
        myMembers.put(member.id, member);
        members++;
    }

    /**
     * Removes a Member from the circle.
     * Removes eldest Member.
     */
    public void removeMember() {
        if (members > 0) {
            myMembers.remove(myMembers.firstKey());
            members--;
        }
    }

    /**
     * Removes a Member from the circle with member id passed in to
     * the signature. Can't remove circle with no members
     */
    public void removeMember(Integer id) {
        if (myMembers.containsKey(id)) {
            myMembers.remove(id);
            members--;
        }
    }

    /**
     * queries members by member id
     */
    public Member query(Integer id){
        return myMembers.getOrDefault(id, null);
    }

    /**
     * queries members located near some point, within some range from that point
     */
    public List<Member> query(Double distanceInMeters, Double longitude, Double latitude){
        ArrayList<Member> list = new ArrayList<>();
        for (Member member:myMembers.values()) {
            if(Utilities.distanceInMeters(member.latitude, member.longitude, latitude, longitude) <= distanceInMeters){
                list.add(member);
            }
        }
        return list;
    }

    /**
     * method equals
     * @return equals or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;
        Circle circle = (Circle) o;
        return id == circle.id &&
                Objects.equals(name, circle.name) &&
                members == circle.members &&
                Objects.equals(myMembers, circle.myMembers);
    }

    /**
     * method hashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, members, myMembers);
    }

    /**
     * method toString
     * @return string representation
     */
    @Override
    public String toString() {
        return "com.paringer.circlemanager.Circle {" + "id=" + id + ", # = " + members + ", name = " + name + ", " + "maxMemberId = " + maxMemberId + ",\r\n" + myMembers.toString().replace("},", "},\r\n") + "}";
    }
}
