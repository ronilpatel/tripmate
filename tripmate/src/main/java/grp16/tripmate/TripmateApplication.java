package grp16.tripmate;

import grp16.tripmate.persistence.connection.DatabaseConnection;
import grp16.tripmate.persistence.connection.IDatabaseConnection;
import grp16.tripmate.persistence.sql.LoadSQLProfile;
import grp16.tripmate.properties.MyProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.sql.Connection;

@SpringBootApplication
public class TripmateApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(TripmateApplication.class).profiles("devint", "production", "test").run(args);
    }
}