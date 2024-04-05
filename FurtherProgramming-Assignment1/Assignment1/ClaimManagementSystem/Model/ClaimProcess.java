package ClaimManagementSystem.Model;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import java.util.Map;

public interface ClaimProcess {
    void add(Claim claim);
    void update(Claim oldClaim, Claim newClaim);
    void delete(Claim claim);
    Claim getOne(Claim claim);
    Map<String, Claim> getAll();
}
