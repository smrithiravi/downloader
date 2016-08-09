package download;

import exception.ResourceDownloadException;
import org.apache.commons.io.FileUtils;
import utils.DOWNLOAD_STATUS;

import java.io.File;
import java.net.URI;
import java.util.logging.Logger;

public abstract class ResourceDownloader implements Runnable {

    protected Logger logger;
    protected File downloadDestinationDirectory;
    protected File downloadDestination;
    protected DOWNLOAD_STATUS downloadStatus;
    protected URI resourceURI;
    protected long timeTakenToDownload;
    protected int socketTimeOut;
    protected int connectionTimeOut;

    public ResourceDownloader(URI uri, File downloadDestinationDirectory, int socketTimeOut, int connectionTimeOut)  {
        logger = Logger.getLogger(this.getClass().getName());
        this.resourceURI = uri;
        this.downloadDestinationDirectory = downloadDestinationDirectory;
        this.downloadStatus = DOWNLOAD_STATUS.NOT_STARTED;
        this.socketTimeOut = socketTimeOut;
        this.connectionTimeOut = connectionTimeOut;
        setDestinationPath();
    }

    public abstract void downloadResource() throws ResourceDownloadException;

    public DOWNLOAD_STATUS getDownloadStatus() {
        return downloadStatus;
    }

    public File getDownloadDestination() {
        return downloadDestination;
    }

    public void printDownloadStatus() {
         logger.info(resourceURI + " " + downloadStatus.name() + " Time to download " + timeTakenToDownload +
                     " Destination " + downloadDestination.getAbsolutePath());

    }

    public void printErrorStatus(ResourceDownloadException e) {
        if(e != null) {
            logger.info(resourceURI + " Status: " + downloadStatus.name());
            e.print();
        } else {
            logger.info(resourceURI + " Status: " + downloadStatus.name());
        }

    }

    @Override
    public void run() {
        performDownload();
    }

    public void performDownload() {
        long startTime = System.nanoTime();
        long endTime;
        downloadStatus = DOWNLOAD_STATUS.STARTED;
        ResourceDownloadException errorException = null;
        try {
            downloadResource();
            downloadStatus = DOWNLOAD_STATUS.SUCCESS;
        } catch (ResourceDownloadException e) {
            downloadStatus = DOWNLOAD_STATUS.FAILED;
            errorException = e;
        } finally {
            endTime = System.nanoTime();
            timeTakenToDownload = (endTime - startTime);
            if(downloadStatus == DOWNLOAD_STATUS.SUCCESS) {
                printDownloadStatus();
            } else {
                clearDestination();
                printErrorStatus(errorException);
            }
        }
    }

    protected void setDestinationPath() {
        downloadDestination = new File(downloadDestinationDirectory.getAbsolutePath() + "/" +
                                       resourceURI.toASCIIString().replaceAll("/", "_"));
    }

    protected void clearDestination() {
        FileUtils.deleteQuietly(downloadDestination);
    }
}
