package Model;

import java.util.ArrayList;
import java.util.List;

public class Policy extends Customer {
    private List<Dependant> dependantList;

    public Policy(String id, String name) {
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
}
