package ch05.ch5_5.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

class IdGeneratorV3Impl implements IdGeneratorV3 {
    private static final Logger logger = LoggerFactory.getLogger(IdGeneratorV3Impl.class);

    @Override
    public String generate() {
        String id = "";
        String substrOfHostName = getLastFieldOfHostName();
        String randomChars = generateRandomAlphanumeric(8);
        id = String.format("%s-%d-%s", substrOfHostName, System.currentTimeMillis(), randomChars);
        return id;
    }

    protected String generateRandomAlphanumeric(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count++] = (char) (randomAscii);
            }
        }
        return new String(randomChars);
    }

    private String getLastFieldOfHostName() {
        String substrOfHostName = null;
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            substrOfHostName = getLastSubStrSplitByDot(hostName);
        } catch (UnknownHostException e) {
            logger.warn("Failed to get the hostname", e);
        }
        return substrOfHostName;
    }


    protected String getLastSubStrSplitByDot(String hostName) {
        String substrOfHostName;
        String[] tokens = hostName.split("\\.");
        substrOfHostName = hostName = tokens[tokens.length - 1];
        return substrOfHostName;
    }
}
