package com.depas.test.streams.javaone;

import java.util.List;

public class EuclideanDistance {

    public static double getEuclideanDistance(List<Integer> pos1, List<Integer> pos2){
        double sum = 0;
        for (int i=0; i<pos1.size(); i++) {
            double d =  Math.pow(pos1.get(i) - pos2.get(2), 2);
            sum = sum + d;
        }

        return Math.sqrt(sum);
    }

    public static void main(String[] args) {

    }
}
