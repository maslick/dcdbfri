package fri.helloworld.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maslick on 01/09/15.
 */
public class DbConfig {
    /* Reads environmental variables for persistence configuration */
    public static Map<String, Object> getPersistConfig() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();

        for (String envName : env.keySet()) {
            if (envName.contains("DB_HOSTURL")) {
                /* DB_URL = jdbc:mysql://localhost:3306/dcdbfri
                *  DB_HOSTURL = localhost
                * */

                configOverrides.put("javax.persistence.jdbc.url", "jdbc:mysql://" + env.get(envName) + ":3306/dcdbfri");
            }
            if (envName.contains("DB_USER")) {
                configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
            }
            if (envName.contains("DB_PWD")) {
                configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
            }
        }

        /* For Docker environment */
        String host = System.getenv("MYSQL_PORT_3306_TCP_ADDR");
        String port = System.getenv("MYSQL_PORT_3306_TCP_PORT");
        if (host != null && port != null) {
            configOverrides.put("javax.persistence.jdbc.url", "jdbc:mysql://" + host + ":" + port + "/dcdbfri");
        }

        String user = System.getenv("MYSQL_ENV_MYSQL_USER");
        String pwd = System.getenv("MYSQL_ENV_MYSQL_PASSWORD");
        if (user != null && pwd != null) {
            configOverrides.put("javax.persistence.jdbc.user", user);
            configOverrides.put("javax.persistence.jdbc.password", pwd);
        }
        return configOverrides;
    }
}
