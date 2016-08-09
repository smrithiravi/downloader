import download.FTPResourceDownloader;
import download.HTTPResourceDownloader;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import utils.DOWNLOAD_STATUS;

import java.io.File;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class AllDownloadersTest {

    @Test
    public void testHttpDownloader_Success() throws Exception {
        HTTPResourceDownloader httpResourceDownloader = new HTTPResourceDownloader(new URI("https://en.wikipedia.org/wiki/Main_Page"), FileUtils.getTempDirectory(),
                                                                                   500, 500);
        httpResourceDownloader.performDownload();
        assertEquals(httpResourceDownloader.getDownloadStatus(), DOWNLOAD_STATUS.SUCCESS);

    }

    @Test
    public void testHttpDownloader_Failure() throws Exception {
        File tmpDir = FileUtils.getTempDirectory();
        HTTPResourceDownloader httpResourceDownloader = new HTTPResourceDownloader(new URI("http://www.anyurl.co.unavail"), tmpDir,
                                                                                   500, 500);
        httpResourceDownloader.performDownload();
        assertEquals(httpResourceDownloader.getDownloadStatus(), DOWNLOAD_STATUS.FAILED);
        File noDest = new File(httpResourceDownloader.getDownloadDestination().getAbsolutePath());
        assertEquals(false, noDest.exists());
    }

    @Test
    public void testFtppDownloader_Success() throws Exception {
        FTPResourceDownloader ftpResourceDownloader = new FTPResourceDownloader(new URI("ftp://ftp.singnet.com.sg/Testing/3MB.pdf"), FileUtils.getTempDirectory(),
                                                                                   500, 500, "", "");
        ftpResourceDownloader.performDownload();
        assertEquals(ftpResourceDownloader.getDownloadStatus(), DOWNLOAD_STATUS.SUCCESS);

    }

    @Test
    public void testFtpDownloader_Failure() throws Exception {
        // should timeout
        FTPResourceDownloader ftpResourceDownloader = new FTPResourceDownloader(new URI("ftp://ftp.singnet.com.sg/Testing/555MB.pdf"), FileUtils.getTempDirectory(),
                                                                                5, 5, "", "");
        ftpResourceDownloader.performDownload();
        assertEquals(DOWNLOAD_STATUS.FAILED, ftpResourceDownloader.getDownloadStatus());
        File noDest = new File(ftpResourceDownloader.getDownloadDestination().getAbsolutePath());
        assertEquals(false, noDest.exists());
    }
}
