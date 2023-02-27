package fr.uparis.energy;

import fr.uparis.energy.model.InvalidSizeException;
import fr.uparis.energy.utils.InvalidLevelException;
import java.io.IOException;

public class Main {
    public static void Test() {}

    public static void main(String[] args) throws InvalidSizeException, IOException, InvalidLevelException {
        System.out.println("Hello world!");
        System.out.println(System.getProperty("user.dir"));
    }
}
