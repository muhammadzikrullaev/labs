package mzik.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
@Getter
@Setter
public class ConnectionManager {


    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.user}")
    private String dbUser;

    @Value("${db.password}")
    private String dbPassword;

    static {
        loadDriver();
    }

    @SneakyThrows
    private static void loadDriver() {
        Class.forName("org.postgresql.Driver");
    }

    @SneakyThrows
    public Connection get() {
        return DriverManager
                .getConnection(dbUrl, dbUser, dbPassword);
    }

}
