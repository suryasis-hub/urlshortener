package hashalgorithm;

import java.util.Base64;

public class Base64EncodingStrategy implements IHashStrategy {
    @Override
    public String getHashFromUrl(String websiteUrl) {
        String encodedString = Base64.getEncoder().encodeToString(websiteUrl.getBytes());
        return  encodedString;
    }
}
