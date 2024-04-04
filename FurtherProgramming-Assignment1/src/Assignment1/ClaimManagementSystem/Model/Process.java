package Model;

import Model.Claim;

import java.util.Map;

public interface Process {
    void add(Claim claim);
    void update(Claim oldClaim, Claim newClaim);
    void delete(Claim claim);
    Claim getOne(Claim claim);
    Map<String, Claim> getAll();
}
