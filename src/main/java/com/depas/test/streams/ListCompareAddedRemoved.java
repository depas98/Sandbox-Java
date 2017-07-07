package com.test.streams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mike.depasquale on 4/27/2017.
 */
public class ListCompareAddedRemoved {

    public static List<String> listsCompareReturnMissingItems(final List<String> list1, final List<String> list2){

        return list1.stream()
                .filter(s -> !list2.contains(s))
                .collect(Collectors.toList());
    }

    public static Map<Lists_Tracked_Typed,List<String>> listTrackAddedAndRemovedItems(final List<String> list1,
                                                                                      final List<String> list2){
        final Map<Lists_Tracked_Typed,List<String>> addedAndRemovedItems = new HashMap<>();

        if (list1==null || list2==null){
            addedAndRemovedItems.put(Lists_Tracked_Typed.ADDED, new ArrayList<>());
            addedAndRemovedItems.put(Lists_Tracked_Typed.REMOVED, new ArrayList<>());
            return addedAndRemovedItems;
        }

        final List<String> removedItems = listsCompareReturnMissingItems(list1,list2);
        addedAndRemovedItems.put(Lists_Tracked_Typed.REMOVED, removedItems);

        final List<String> addedItems = listsCompareReturnMissingItems(list2,list1);
        addedAndRemovedItems.put(Lists_Tracked_Typed.ADDED, addedItems);

        return addedAndRemovedItems;
    }

    public static void main(String[] args) {

        List<String> ag_db1 = Arrays.asList("DB_A","DB_B","DB_C","DB_D");
        List<String> ag_db2 = Arrays.asList("DB_A","DB_B","DB_C","DB_D");

        Map<Lists_Tracked_Typed,List<String>> result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("same DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("same DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));

        ag_db2 = Arrays.asList();

        result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("all DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("all DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));

        ag_db2 = Arrays.asList("DB_A","DB_B","DB_C","DB_D");

        ag_db1 = Arrays.asList();

        result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("no DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("no DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));

        ag_db1 = Arrays.asList("DB_A","DB_B","DB_C","DB_D");
        ag_db2 = Arrays.asList("DB_C","DB_D");

        result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("some removed DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("some removed DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));

        ag_db2 = Arrays.asList("DB_E","DB_F");

        result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("all removed some added DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("all removed some added DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));

        ag_db2 = Arrays.asList("DB_A","DB_B","DB_C","DB_D","DB_E","DB_F");

        result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("some added DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("some added DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));

        ag_db2 = Arrays.asList("DB_A","DB_C","DB_E","DB_F");

        result = ListCompareAddedRemoved.listTrackAddedAndRemovedItems(ag_db1,ag_db2);
        System.out.println("some removed and added DBs removed result: " + result.get(Lists_Tracked_Typed.REMOVED));
        System.out.println("some removed and added DBs added result: " + result.get(Lists_Tracked_Typed.ADDED));
    }

    public enum Lists_Tracked_Typed{ADDED,REMOVED;}

}

