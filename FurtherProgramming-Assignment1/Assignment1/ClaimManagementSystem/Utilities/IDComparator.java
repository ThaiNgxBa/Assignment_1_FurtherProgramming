package ClaimManagementSystem.Utilities;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import java.util.Comparator;

public class IDComparator implements Comparator<String> {
    @Override
    public int compare(String id1, String id2) {
        return id1.compareTo(id2);
    }
}
