package download;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import exception.ResourceDownloadException;

import java.io.File;
import java.net.URI;

public class SFTPResourceDownloader extends FTPResourceDownloader {
    private String knownFTPHostsFilename;
    private String keyFileName;
    private String passphrase;

    public SFTPResourceDownloader(final URI uri, final File downloadDestination, final int socketTimeOut, final int connectionTimeOut,
                                  final String ftpHostsFilename, final String username, final String password, String keyFileName, String passphrase) {
        super(uri, downloadDestination, socketTimeOut, connectionTimeOut, username, password);
        this.knownFTPHostsFilename = ftpHostsFilename;
        this.keyFileName = keyFileName;
        this.passphrase = passphrase;
    }

    @Override
    public void downloadResource() throws ResourceDownloadException {
        try {
            JSch jsch = new JSch();

            if(isStringIsNullOrEmpty(knownFTPHostsFilename)) {
                jsch.setKnownHosts(knownFTPHostsFilename);
            }

            if(isStringIsNullOrEmpty(passphrase)) {
                jsch.addIdentity(keyFileName, passphrase);
            } else {
                jsch.addIdentity(keyFileName);
            }

            Session session=jsch.getSession(username, resourceURI.getHost());

            if(isStringIsNullOrEmpty(password))  {
                session.setPassword(password);
            }

            session.connect();

            Channel channel = session.openChannel( "sftp" );
            channel.connect();

            ChannelSftp sftpChannel = (ChannelSftp) channel;

            sftpChannel.get(resourceURI.getPath(), downloadDestination.getAbsolutePath() );

            sftpChannel.exit();
            session.disconnect();
        } catch (SftpException e) {
            throw new ResourceDownloadException(e, resourceURI);
        } catch (JSchException e) {
            throw new ResourceDownloadException(e, resourceURI);
        }
    }

    private boolean isStringIsNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}

