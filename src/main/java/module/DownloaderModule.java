package module;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import config.DownloaderConfiguration;
import download.ResourceDownloaderManager;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class DownloaderModule extends AbstractModule {
    private final static Logger logger = Logger.getLogger(DownloaderModule.class.getName());

    private String configPath;

    public DownloaderModule(String configPath) {
        this.configPath = configPath;
    }

    @Override
    protected void configure() {
        Yaml yaml = new Yaml();
        InputStream input = null;
        DownloaderConfiguration config = new DownloaderConfiguration.Builder().buildWithDefaultConfiguration();
        try {
            input = new FileInputStream(new File(configPath));
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(input, DownloaderConfiguration.class);
        } catch (FileNotFoundException e) {
            logger.info("Loading with default");
        } catch (JsonMappingException e) {
            logger.info("Loading with default");
        } catch (JsonParseException e) {
            logger.info("Loading with default");
        } catch (IOException e) {
            logger.info("Loading with default");
        }
        bind(DownloaderConfiguration.class).toInstance(config);
        bind(ResourceDownloaderManager.class).asEagerSingleton();
    }
}
