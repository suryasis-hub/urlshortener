package hashalgorithm;

import java.util.UUID;

public class UUIDStrategy implements IHashStrategy {
    @Override
    public String getHashFromUrl(String websiteUrl) {
        return UUID.randomUUID().toString();
    }
}
