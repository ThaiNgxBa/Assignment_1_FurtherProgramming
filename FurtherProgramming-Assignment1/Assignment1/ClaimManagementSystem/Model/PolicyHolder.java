package ClaimManagementSystem.Model;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import java.util.ArrayList;
import java.util.List;

public class PolicyHolder extends Customer{
    private List<Dependant> dependantList;

    public PolicyHolder(String id, String name) {
        super(id, name);
        dependantList = new ArrayList<>();
    }

    public void addDependant(Dependant dependant) {
        this.dependantList.add(dependant);
    }

    public List<Dependant> getDependantList() {
        return dependantList;
    }

    public List<String> getDependantsIDS() {
        List<String> ids = new ArrayList<>();
        for (Dependant d : dependantList) {
            ids.add(d.getId());
        }
        return ids;
    }
    public boolean hasDependant(Dependant dependant) {
        return dependantList.contains(dependant);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if (!dependantList.isEmpty()) {
            stringBuilder.append("  - Dependents:\n");
            for (Dependant dependant : dependantList) {
                stringBuilder.append(String.format("      - %s\n", dependant.getId()));
            }
        } else {
            stringBuilder.append("  - No Dependents\n");
        }
        return stringBuilder.toString();
    }

}
