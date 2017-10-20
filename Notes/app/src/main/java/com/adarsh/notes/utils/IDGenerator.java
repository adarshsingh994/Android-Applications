package com.adarsh.notes.utils;

import java.util.Random;

/**
 * Created by adars on 10/19/2017.
 */

public class IDGenerator {

    public static long generateRandomId(){
        long LOWER_RANGE = 1;
        long UPPER_RANGE = 1000000000;
        Random random = new Random();


        long randomValue = LOWER_RANGE + (long)(random.nextDouble()*(UPPER_RANGE - LOWER_RANGE));
        return randomValue;
    }
}
