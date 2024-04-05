package ClaimManagementSystem.Utilities;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import ClaimManagementSystem.Model.Customer;
import ClaimManagementSystem.Model.Dependant;
import ClaimManagementSystem.Model.PolicyHolder;

public class Comparator implements java.util.Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        if (c1 instanceof Dependant && c2 instanceof PolicyHolder) {
            return -1;
        } else if (c1 instanceof PolicyHolder && c2 instanceof Dependant) {
            return 1;
        } else {
            return c1.getId().compareTo(c2.getId());
        }
    }
}
