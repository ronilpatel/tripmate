package grp16.tripmate.properties;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyProperties implements IProperties{

    private static MyProperties myProperties = null;
    private final ILogger logger = new MyLoggerAdapter(this);

    private static final String PROPERTY_CONFIGURATION_FILE = "./application.properties";
    private String databaseURL, databaseUserName, databasePassword;
    private String mailSender, host, port, password;
    Properties properties = new Properties();
    private MyProperties(){
        String activeProfile = getActiveProfile();
        setProfileProperties(activeProfile);
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseUserName() {
        return databaseUserName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getMailSender() {
        return mailSender;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public static MyProperties getInstance() throws Exception {
        if (myProperties == null) {
            myProperties = new MyProperties();
        }
        return myProperties;
    }

    public String getActiveProfile() {

        String activeProfile = loadPropertiesFromFile(PROPERTY_CONFIGURATION_FILE).getProperty("spring.profiles.active");

        if (activeProfile == null) {
            activeProfile = "devint";
        }
        return activeProfile;
    }

    private Properties loadPropertiesFromFile(String propertiesFileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(propertiesFileName)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(properties.toString());
        return properties;
    }

    public Properties loadProperties() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream resourceStream = loader.getResourceAsStream(PROPERTY_CONFIGURATION_FILE)) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(properties.toString());
        return properties;
    }

    private void setProfileProperties(String env) {
        String filename = "application-" + env + ".properties";
        properties = loadPropertiesFromFile(filename);
        logger.info(filename);
        this.databaseURL = properties.getProperty("databaseURL") + properties.getProperty("database");
        this.databaseUserName = properties.getProperty("username");
        this.databasePassword = properties.getProperty("password");
        this.mailSender = properties.getProperty("spring.mail.username");
        this.host = properties.getProperty("spring.mail.host");
        this.port = properties.getProperty("spring.mail.port");
        this.password = properties.getProperty("spring.mail.password");
        logger.info(databaseURL);
        logger.info(databaseUserName);
        logger.info(databasePassword);
        logger.info(mailSender);
    }
}
