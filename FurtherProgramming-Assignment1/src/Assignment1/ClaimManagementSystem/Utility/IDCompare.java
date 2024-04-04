package Utility;

import java.util.Comparator;

public class IDCompare implements Comparator<String> {
    public int compare(String id1, String id2) {
        return id1.compareTo(id2);
    }
}
