package net.degiere.soc.util;

import java.util.Random;

import net.degiere.soc.model.Employee;

public class Utils {

    /**
     * Generate a random string of text
     * 
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer result = new StringBuffer();
        Random rand = new Random();
        char[] letters = "abcdefghijklmnopqrstuvwxyz ".toCharArray();
        for (int i = 0; i < length; i++) {
            result.append(letters[rand.nextInt(26)]);
        }
        return result.toString();
    }

    /**
     * Generate a random long number
     * 
     * @return
     */
    public static Long getRandomLong() {
        Random rand = new Random();
        return Math.abs(rand.nextLong());
    }

    /**
     * Generate a random employee object
     * 
     * @return employee
     */
    public static Employee getRandomEmployee() {
        return new Employee(Utils.getRandomLong(), Utils.getRandomString(255));
    }

}
