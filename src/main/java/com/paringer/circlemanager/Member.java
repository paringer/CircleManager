package com.paringer.circlemanager;

import java.util.Objects;

/**
 * represents Member
 */
public class Member {
    Integer id;
    String name;
    Double longitude;
    Double latitude;

    /**
     * constructor used by Gson serializer and by Circles
     */
    public Member(Integer id, String name, Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * method equals
     * @return equals or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(name, member.name) &&
                Objects.equals(longitude, member.longitude) &&
                Objects.equals(latitude, member.latitude);
    }

    /**
     * method hashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, longitude, latitude);
    }

    /**
     * method toString
     * @return string representation
     */
    @Override
    public String toString() {
        return "com.paringer.circlemanager.Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
