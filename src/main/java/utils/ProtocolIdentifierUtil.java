package utils;

import java.net.URI;
import java.util.logging.Logger;

public class ProtocolIdentifierUtil {
    private final static Logger logger = Logger.getLogger(ProtocolIdentifierUtil.class.getName());


    public static utils.ProtocolType getProtocolTypeForURI(URI uri) throws NullPointerException, IllegalArgumentException {
            return ProtocolType.valueOf(uri.getScheme().toUpperCase());
    }
}
