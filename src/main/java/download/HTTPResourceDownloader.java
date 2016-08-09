package download;

import exception.ResourceDownloadException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class HTTPResourceDownloader extends ResourceDownloader {

    public HTTPResourceDownloader(final URI uri, final File downloadDestination, final int socketTimeOut, final int connectionTimeOut) {
        super(uri, downloadDestination, socketTimeOut, connectionTimeOut);
    }

    @Override
    public void downloadResource() throws ResourceDownloadException {
        try {
            FileUtils.copyURLToFile(resourceURI.toURL(), downloadDestination, socketTimeOut, connectionTimeOut);
        } catch (IOException e) {
            throw new ResourceDownloadException(e, resourceURI);
        }
    }
}
