# downloader
Downloads given input URLs in parallel

Main: DownloaderMain

Program arguments
* Configuration  - config.json is provided under resources
* list of URLs - https://en.wikipedia.org/wiki/Main_Page,,ftp://ftp.singnet.com.sg/Testing/3MB.pdf,sftp://host_name//home/user/file

To build
mvn clean package

To Run the Downloader
java -cp target/downloader-1.0-SNAPSHOT.jar DownloaderMain /PATH/TO/DOWNLOADER_PROJECT/downloader/src/main/resources/config.json https://en.wikipedia.org/wiki/Main_Page,ftp://ftp.singnet.com.sg/Testing/3MB.pdf