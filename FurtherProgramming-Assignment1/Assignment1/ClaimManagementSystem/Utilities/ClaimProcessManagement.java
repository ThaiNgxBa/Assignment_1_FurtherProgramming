package ClaimManagementSystem.Utilities;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import ClaimManagementSystem.DataManager;
import ClaimManagementSystem.Model.Claim;
import ClaimManagementSystem.Model.ClaimProcess;

import java.util.Map;

public class ClaimProcessManagement implements ClaimProcess {

    @Override
    public void add(Claim claim) {
        // Add the claim to system and database
        DataManager.getClaims().put(claim.getId(), claim);
    }

    /**
     * <p>
     *     Search for the claim in database then overwrite it
     * </p>
     * */
    @Override
    public void update(Claim oldClaim, Claim newClaim) {
        delete(oldClaim);
        add(newClaim);
    }

    /**
     * <p>
     *     Before deleting a claim, we must delete the claim
     *     from the respective customer's list of claims.
     * </p>
     * */
    @Override
    public void delete(Claim claim) {
        // Delete claim from customer's list of claim
        claim.getInsuredPerson().removeClaim(claim);
        // Remove claim from the system
        DataManager.getClaims().remove(claim.getId());
        // Overwrite the data
        DataManager.overWriteCustomer();
        DataManager.overWriteClaim();
    }

    @Override
    public Claim getOne(Claim claim) {
        return null;
    }

    @Override
    public Map<String, Claim> getAll() {
        return null;
    }
}
