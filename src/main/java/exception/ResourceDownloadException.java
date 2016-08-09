package exception;

import java.net.URI;
import java.util.logging.Logger;

public class ResourceDownloadException extends Exception {
    private final static Logger logger = Logger.getLogger(ResourceDownloadException.class.getName());
    private URI uri;
    private Exception exception;

    public ResourceDownloadException(final Exception e, final URI uri) {
        super(e);
        this.uri = uri;
        this.exception = e;
    }

    public void print() {
        logger.severe("Exception occurred while downloading " + uri.toString() + " Exception: " + exception.getMessage());
        exception.printStackTrace();
    }
}
