package System.Model;

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
    public boolean loadDependant(Dependant dependant) {
        return dependantList.contains(dependant);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        if (!dependantList.isEmpty()) {
            sb.append("  - Dependents:\n");
            for (Dependant dependant : dependantList) {
                sb.append(String.format("      - %s\n", dependant.getId()));
            }
        } else {
            sb.append("  - No Dependents\n");
        }
        return sb.toString();
    }

    public List<String> getDependantsIDS() {
        List<String> ids = new ArrayList<>();
        for (Dependant d : dependantList) {
            ids.add(d.getId());
        }
        return ids;
    }
}
