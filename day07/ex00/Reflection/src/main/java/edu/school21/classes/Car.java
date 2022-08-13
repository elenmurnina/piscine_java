package edu.school21.classes;

import java.util.StringJoiner;

import static java.lang.Math.max;

public class Car {

    private String make;
    private String model;
    private int modelYear;
    private long price;

    public Car() {
        this.make = "Default make";
        this.model = "Default model";
        this.modelYear = 0;
        this.price = 0;
    }

    public Car(String make, String model, int modelYear, long price) {
        this.make = make;
        this.model = model;
        this.modelYear = modelYear;
        this.price = price;
    }

    public long priceChange(long value) {
        price += value;
        price = max(price, 0);
        return price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("make='" + make + "'")
                .add("model='" + model + "'")
                .add("modelYear=" + modelYear)
                .add("price=" + price)
                .toString();
    }
}
