package download;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import config.DownloaderConfiguration;
import utils.ProtocolIdentifierUtil;
import utils.ProtocolType;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Singleton
public class ResourceDownloaderManager {
    private final static Logger logger = Logger.getLogger(ResourceDownloaderManager.class.getName());

    private ExecutorService downloadExecutor;

    private DownloaderConfiguration downloaderConfiguration;

    @Inject
    public ResourceDownloaderManager(DownloaderConfiguration downloaderConfiguration) {
        this.downloaderConfiguration =  downloaderConfiguration;
        this.downloadExecutor = Executors.newFixedThreadPool(downloaderConfiguration.getNumThreads());

    }

    public void downloadResources(List<URI> uriList) {
        try {
            for(URI uri: uriList) {
                // Submit this to executor pool to do this in parallel
                try {
                    ResourceDownloader resourceDownloader = getDownloader(uri);
                    downloadExecutor.submit(resourceDownloader);
                } catch (NullPointerException npe) {
                    logger.warning("No protocol provided in " + uri);
                } catch (IllegalArgumentException ie) {
                    logger.warning("Unsupported protocol provided in " + uri);
                }
            }
            downloadExecutor.shutdown();
            downloadExecutor.awaitTermination(downloaderConfiguration.getMaxWaitTimeInSecs(), TimeUnit.SECONDS);
        } catch(InterruptedException ie) {
            logger.info("Executor was interrupted");
        } finally {
            downloadExecutor.shutdownNow();
        }
    }

    private ResourceDownloader getDownloader(URI uri) throws NullPointerException, IllegalArgumentException {
        ProtocolType protocolType = ProtocolIdentifierUtil.getProtocolTypeForURI(uri);
        switch(protocolType) {
            case FTP:
                return new FTPResourceDownloader(uri, downloaderConfiguration.getDownloadDestination(), downloaderConfiguration.getSocketTimeOut(), downloaderConfiguration.getConnectionTimeOut(),
                                                 downloaderConfiguration.getUsername(), downloaderConfiguration.getPassword());
            case HTTP:
            case HTTPS:
                return new HTTPResourceDownloader(uri, downloaderConfiguration.getDownloadDestination(), downloaderConfiguration.getSocketTimeOut(), downloaderConfiguration.getConnectionTimeOut());
            case SFTP:
                return new SFTPResourceDownloader(uri, downloaderConfiguration.getDownloadDestination(), downloaderConfiguration.getSocketTimeOut(), downloaderConfiguration.getConnectionTimeOut(), downloaderConfiguration.getKnownFTPHostsFilename(),
                                                  downloaderConfiguration.getUsername(), downloaderConfiguration.getPassword(), downloaderConfiguration.getKeyFileName(), downloaderConfiguration.getPassphrase());
            default:
                logger.warning("Unsupported protocol provided. Will use default apache FileUtils");
                return new HTTPResourceDownloader(uri, downloaderConfiguration.getDownloadDestination(), downloaderConfiguration.getSocketTimeOut(),
                                                  downloaderConfiguration.getConnectionTimeOut());
        }
    }

}
