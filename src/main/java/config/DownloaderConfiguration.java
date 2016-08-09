package config;

import com.google.inject.Singleton;
import org.apache.commons.io.FileUtils;

import java.io.File;

@Singleton
public class DownloaderConfiguration {

    private int maxWaitTimeInSecs;
    private String downloadDestination;
    private int numThreads;
    private int socketTimeOut;
    private int connectionTimeOut;
    private String knownFTPHostsFilename;
    private String username;
    private String password;
    private String keyFileName;
    private String passphrase;

    public DownloaderConfiguration() {
    }

    public DownloaderConfiguration(int maxWaitTimeInSecs, String downloadDestination, int numThreads,
                                   int socketTimeOut, int connectionTimeOut, String knownFTPHostsFilename,
                                   String keyFileName, String passphrase) {
        this.maxWaitTimeInSecs = maxWaitTimeInSecs;
        this.downloadDestination = downloadDestination;
        this.numThreads = numThreads;
        this.socketTimeOut = socketTimeOut;
        this.connectionTimeOut = connectionTimeOut;
        this.knownFTPHostsFilename = knownFTPHostsFilename;
        this.keyFileName = keyFileName;
        this.passphrase = passphrase;
    }

    private DownloaderConfiguration(Builder builder) {
        this(builder.maxWaitTime, builder.downloadDestination, builder.numThreads, builder.socketTimeOut,
             builder.connectionTimeOut, builder.knownFTPHostsFilename, builder.keyFileName, builder.passphrase);
    }

    public int getMaxWaitTimeInSecs() {
        return maxWaitTimeInSecs;
    }

    public void setMaxWaitTimeInSecs(final int maxWaitTimeInSecs) {
        this.maxWaitTimeInSecs = maxWaitTimeInSecs;
    }

    public File getDownloadDestination() {
        return new File(downloadDestination);
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(final int numThreads) {
        this.numThreads = numThreads;
    }

    public int getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(final int socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(final int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public String getKnownFTPHostsFilename() {
        return knownFTPHostsFilename;
    }

    public void setKnownFTPHostsFilename(final String knownFTPHostsFilename) {
        this.knownFTPHostsFilename = knownFTPHostsFilename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getKeyFileName() {
        return keyFileName;
    }

    public void setKeyFileName(final String keyFileName) {
        this.keyFileName = keyFileName;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(final String passphrase) {
        this.passphrase = passphrase;
    }

    public static class Builder {
        private int maxWaitTime;
        private String downloadDestination;
        private int numThreads;
        private int socketTimeOut;
        private int connectionTimeOut;
        private String knownFTPHostsFilename;
        private String keyFileName;
        private String passphrase;

        public Builder setMaxWaitTime(int maxWaitTime) {
            this.maxWaitTime = maxWaitTime;
            return this;
        }

        public Builder setPassphrase(String passphrase) {
            this.passphrase = passphrase;
            return this;
        }

        public Builder setKeyFileName(String keyFileName) {
            this.keyFileName = keyFileName;
            return this;
        }

        public Builder setKnownFTPHostsFilename(String knownFTPHostsFilename1) {
            this.knownFTPHostsFilename = knownFTPHostsFilename1;
            return this;
        }

        public Builder setSocketTimeOut(int socketTimeOut) {
            this.socketTimeOut = socketTimeOut;
            return this;
        }

        public Builder setConnectionTimeOut(int connectionTimeOut) {
            this.connectionTimeOut = connectionTimeOut;
            return this;
        }

        public Builder setNumThreads(int numThreads) {
            this.numThreads = numThreads;
            return this;
        }

        public DownloaderConfiguration buildWithDefaultConfiguration() {
            this.maxWaitTime = 120;
            this.downloadDestination = FileUtils.getTempDirectoryPath();
            this.numThreads = 5;
            this.socketTimeOut = 500;
            this.connectionTimeOut = 500;
            return build();
        }

        public DownloaderConfiguration build() {
            return new DownloaderConfiguration(this);
        }
    }
}
