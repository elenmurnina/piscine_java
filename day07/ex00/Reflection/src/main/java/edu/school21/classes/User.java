package edu.school21.classes;

import java.util.StringJoiner;

public class User {

    private String firstName;
    private String lastName;
    private double height;
    private boolean goodMood;

    public User() {
        this.firstName = "Default first name";
        this.lastName = "Default last name";
        this.height = 0;
        this.goodMood = true;
    }

    public User(String firstName, String lastName, double height, boolean goodMood) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.goodMood = goodMood;
    }

    public double grow(double value) {
        height += value;
        return height;
    }

    public boolean moodChange() {
        goodMood = !goodMood;
        return goodMood;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("height=" + height)
                .add("good mood=" + goodMood)
                .toString();
    }
}
