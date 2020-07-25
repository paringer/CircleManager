package com.paringer.circlemanager;

/*
 * com.paringer.circlemanager.Circle Manager
 *
 * A com.paringer.circlemanager.Circle manager that can add and remove members based on a circle ID.
 * Also able to query the number of circles with a given number of members
 */
public interface CircleManager {
    /**
     * Creates a new circle with 1 member and the provided ID
     */
    public void createCircle(Integer circleId);

    /**
     * Adds a member to a circle of the id passed in to the signature.
     * In the case that the circle does not exist they should create it.
     */
    public void addMember(Integer circleId);

    /**
     * Removes a member from the circle of the id passed in to
     * the signature. Should remove circles with no members
     */
    public void removeMember(Integer circleId);

    /**
     * Return the number of circles with members of size n passed
     * into the function. Try to optimize this to be as performant as possible.
     */
    public Integer query(Integer memberCount);
}
