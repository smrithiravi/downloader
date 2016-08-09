package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class URLUtils {

    private final static Logger logger = Logger.getLogger(URLUtils.class.getName());

    public static List getValidURLListFromString(String listOfURLs) {
        List<URI> urlList = new ArrayList<URI>();
        StringTokenizer token = new StringTokenizer(listOfURLs, ",");

        while (token.hasMoreTokens()) {
            String url = token.nextToken();
            try {
                urlList.add(new URI(url));
            } catch (URISyntaxException ue) {
                logger.warning("Malformed URL was provided as input. Ignoring it for download " + url +
                               " Exception " + ue.getMessage());
            }
        }
        return urlList;
    }
}
