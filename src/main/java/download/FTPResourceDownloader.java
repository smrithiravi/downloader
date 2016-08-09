package download;

import exception.ResourceDownloadException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;

public class FTPResourceDownloader extends ResourceDownloader {
    protected String username;
    protected String password;

    public FTPResourceDownloader(final URI uri, final File downloadDestination, final int socketTimeOut, final int connectionTimeOut, final String username,
                                 final String password) {
        super(uri, downloadDestination, socketTimeOut, connectionTimeOut);
        this.username = username;
        this.password = password;
    }

    @Override
    public void downloadResource() throws ResourceDownloadException {
        try {
            Authenticator.setDefault(new CustomAuthenticator(username, password));
            FileUtils.copyURLToFile(resourceURI.toURL(), downloadDestination, socketTimeOut, connectionTimeOut);
        } catch (IOException e) {
            throw new ResourceDownloadException(e, resourceURI);
        }
    }

    public static class CustomAuthenticator extends Authenticator {
        protected String username = "";
        protected String password = "";

        public CustomAuthenticator(final String username, final String password) {
             this.username = username;
             this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }
}
