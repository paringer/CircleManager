package com.paringer.circlemanager;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class Circle {
    int id; // # of id in the com.paringer.circlemanager.Circle
    String name;
    int members; // # of members in the com.paringer.circlemanager.Circle
    int maxMemberId = 0;
    ConcurrentSkipListMap<Integer, Member> myMembers = new ConcurrentSkipListMap<>();

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

    public void addMember() {
        Member member = new Member(++maxMemberId, Utilities.randomName(), Math.random() * 360 - 180, Math.random() * 180 - 90);
        myMembers.put(member.id, member);
        members++;
    }

    public void removeMember() {
        if (members > 0) {
            myMembers.remove(myMembers.firstKey());
            members--;
        }
    }

    public void removeMember(Integer id) {
        if (myMembers.containsKey(id)) {
            myMembers.remove(id);
            members--;
        }
    }

    public Member query(Integer id){
        return myMembers.getOrDefault(id, null);
    }

    public List<Member> query(Double distanceInMeters, Double longitude, Double latitude){
        ArrayList<Member> list = new ArrayList<>();
        for (Member member:myMembers.values()) {
            if(Utilities.distanceInMeters(member.latitude, member.longitude, latitude, longitude) <= distanceInMeters){
                list.add(member);
            }
        }
        return list;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members, myMembers);
    }

    @Override
    public String toString() {
        return "com.paringer.circlemanager.Circle {" + "id=" + id + ", # = " + members + ", name = " + name + ", " + "maxMemberId = " + maxMemberId + ",\r\n" + myMembers.toString().replace("},", "},\r\n") + "}";
    }
}
