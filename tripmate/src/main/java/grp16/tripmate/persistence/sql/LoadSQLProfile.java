package grp16.tripmate.persistence.sql;

import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class LoadSQLProfile {
    private final ILogger logger = new MyLoggerAdapter(this);

    public void loadSQLforProfile(Connection conn, String env) {

        String filename = "schema-" + env + ".sql";

        logger.info("loading SQL from: " + filename);

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream resourceStream = loader.getResourceAsStream(filename);

            if (resourceStream != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
                String str;
                StringBuilder sb;
                while ((str = in.readLine()) != null) {
                    sb = new StringBuilder();
                    while ((str = in.readLine()) != null) {
                        sb.append(str).append("\n ");
                        if (sb.toString().contains(";")) {
                            break;
                        }
                    }
                    conn.createStatement().execute(sb.toString());
                }
                in.close();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}