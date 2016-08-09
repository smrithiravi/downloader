import config.DownloaderConfiguration;
import download.ResourceDownloaderManager;
import org.junit.Test;
import utils.URLUtils;

import java.net.URI;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ResourceDownloaderTest {

    @Test
    public void testCase1() {
        ResourceDownloaderManager resourceDownloaderManager = new ResourceDownloaderManager(new DownloaderConfiguration.Builder().buildWithDefaultConfiguration());

        String listOfURLs = "https://en.wikipedia.org/wiki/Main_Page,file://,ftp://ftp.singnet.com.sg/Testing/3MB.pdf";
        List<URI> validUrlList = URLUtils.getValidURLListFromString(listOfURLs);
        assertEquals(validUrlList.size(), 2);
        resourceDownloaderManager.downloadResources(validUrlList);
    }
}
