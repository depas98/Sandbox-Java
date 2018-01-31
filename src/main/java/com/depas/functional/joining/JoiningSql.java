package com.depas.functional.joining;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JoiningSql {

    public static void main(String[] args) {

        String lineSeparator = System.getProperty("line.separator");
        final AtomicInteger counter = new AtomicInteger(1);

        String sql = "select * from foo";
        List<String> sqls = Arrays.asList(sql, sql, sql, sql, sql);

        sqls.forEach(System.out::println);

        System.out.println("############################");

        String sqlsPrefix = sqls.stream()
                .map(s -> "1_rs" + counter.getAndIncrement() + " SYS_REFCURSOR; ")
                .collect(Collectors.joining(lineSeparator, "DECLARE" + lineSeparator, ""));

        counter.set(1);

        String sqlOut = sqls.stream()
                .map(s -> "OPEN 1_rs" + counter.getAndIncrement() + " FOR " + s + ";")
                .collect(Collectors.joining(lineSeparator, lineSeparator + "BEGIN " + lineSeparator, ""));

        counter.set(1);

        String sqlsSuffix = sqls.stream()
                .map(s -> "? := 1_rs" + counter.getAndIncrement() + "; ")
                .collect(Collectors.joining(lineSeparator, "" + lineSeparator, lineSeparator + "END;"));

        System.out.println(sqlsPrefix + sqlOut + sqlsSuffix);


        String noOracleSql = sqls.stream()
                .map(s -> s + ";")
                .collect(Collectors.joining(lineSeparator));


        final int numberOfSqlsPerHour = 2;
        final int numberOfHoursInDay = 4;
        final int dbId = 1;

        final String sqlForHour = "  select SQLHASH from "
                                + "(select SQLHASH, sum(TIMESECS) waittime"
                                + "from CON_STATS_SUM_" + dbId
                                + " where DATEHOUR = ? and SQLHASH > 0 "
        						+ "group by SQLHASH "
        						+ "having waittime > 0 "
        						+ "order by waittime desc "
        						+ "limit " + numberOfSqlsPerHour
                                + ") hr";

        String sqlAllHours = IntStream.range(0, numberOfHoursInDay)
                .mapToObj(i -> sqlForHour)
                .collect(Collectors.joining(lineSeparator + " union " + lineSeparator));

        System.out.println("------------------------------------------");
        System.out.println(sqlAllHours);

    }
}
