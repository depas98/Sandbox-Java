package com.depas.functional.optional;

import java.util.Optional;
import java.util.Properties;
import static org.junit.Assert.assertEquals;

public class OptionalPropertiesExample {

    public static int readDurationImp(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int intValue = Integer.parseInt(value);
                if (intValue > 0) {
                    return intValue;
                }
            } catch (NumberFormatException nfe) {}
        }
        return 0;
    }

    public static int readDuration(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalUtility::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("prop1", "5");
        props.setProperty("prop2", "true");
        props.setProperty("prop3", "-3");
        props.setProperty("prop4", "1");

        assertEquals(5, readDurationImp(props, "prop1"));
        assertEquals(0, readDurationImp(props, "prop2"));
        assertEquals(0, readDurationImp(props, "prop3"));
        assertEquals(1, readDurationImp(props, "prop4"));

        System.out.println("prop1: " + readDurationImp(props, "prop1"));
        System.out.println("prop2: " + readDurationImp(props, "prop2"));
        System.out.println("prop3: " + readDurationImp(props, "prop3"));
        System.out.println("prop4: " + readDurationImp(props, "prop4"));
        System.out.println("prop5: " + readDurationImp(props, "prop5"));

        assertEquals(5, readDuration(props, "prop1"));
        assertEquals(0, readDuration(props, "prop2"));
        assertEquals(0, readDuration(props, "prop3"));
        assertEquals(1, readDuration(props, "prop4"));

        System.out.println("############# funtcional ####################");
        System.out.println("prop1: " + readDuration(props, "prop1"));
        System.out.println("prop2: " + readDuration(props, "prop2"));
        System.out.println("prop3: " + readDuration(props, "prop3"));
        System.out.println("prop4: " + readDuration(props, "prop4"));
        System.out.println("prop5: " + readDuration(props, "prop5"));

    }
}
