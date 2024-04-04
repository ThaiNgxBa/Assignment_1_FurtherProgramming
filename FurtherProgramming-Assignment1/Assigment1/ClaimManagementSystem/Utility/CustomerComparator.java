package ClaimManagementSystem.Utility;

import ClaimManagementSystem.Model.Customer;
import ClaimManagementSystem.Model.Dependant;
import ClaimManagementSystem.Model.PolicyHolder;

import java.util.Comparator;

public class CustomerComparator implements Comparator<Customer> {
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
