import com.google.inject.Guice;
import com.google.inject.Injector;
import download.ResourceDownloaderManager;
import module.DownloaderModule;
import utils.URLUtils;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

public class DownloaderMain {

    private final static Logger logger = Logger.getLogger(DownloaderMain.class.getName());


    public static void main(String args[]) {
        if(args == null || args.length < 2) {
            logger.severe("Not enough input provided");
            return;
        }

        Injector injector = Guice.createInjector(new DownloaderModule(args[0]));
        ResourceDownloaderManager resourceDownloaderManager = injector.getInstance(ResourceDownloaderManager.class);

        String listOfURLs = args[1];
        List<URI> validUrlList = URLUtils.getValidURLListFromString(listOfURLs);

        resourceDownloaderManager.downloadResources(validUrlList);
    }
}
