package com.paringer.circlemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.StrictMath.*;

public class Utilities implements Constants{

    /**
     * calculates surface distance between two points in geo coordinates
     * @return distance in meters
     */
    public static Double distanceInMeters(Double lat1, Double lon1, Double lat2, Double lon2) {
        double theta = lon1 - lon2;
        double dist = sin(toRadians(lat1)) * sin(toRadians(lat2)) + cos(toRadians(lat1)) * cos(toRadians(lat2))
                * cos(toRadians(theta));
        dist = acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60.0 * 1.1515 * 1609.34;
    }

    /**
     * generates random name, almost readable or better say sometimes readable
     * @return random name
     */
    public static String randomName() {
        StringBuilder sb = new StringBuilder();
        sb.append(CONSONANTS.charAt(random(CONSONANTS.length())));
        sb.append(VOWELS.charAt(random(VOWELS.length())));
        sb.append(CONSONANTS.charAt(random(CONSONANTS.length())));
        sb.append(VOWELS.charAt(random(VOWELS.length())));
        sb.append(CONSONANTS.charAt(random(CONSONANTS.length())));
        sb.append(VOWELS.charAt(random(VOWELS.length())));
        sb.append(CONSONANTS.charAt(random(CONSONANTS.length())));
        return sb.toString();
    }

    /**
     * generates random number from zero to threshold - 1
     * @param threshold upper value threshold, non inclusive
     * @return random number
     */
    public static int random(int threshold) {
        return (int)Math.round(Math.floor(Math.random() * threshold));
    }

    /**
     * reads whole file from text file as String
     * @param fileName name of file
     * @return text of file
     * @throws IOException in case something wrong with file
     */
    public static String readFileAsString(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8");
        return str;
    }
}