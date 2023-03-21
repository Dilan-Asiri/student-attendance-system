package lk.ijse.dep10.attendence.db;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection(){
        Properties configurations=new Properties();
        File configFile=new File("application.properties");
        try {
            FileReader fr=new FileReader(configFile);
            configurations.load(fr);

            String host=configurations.getProperty("mysql.host","127.0.0.1");
            String database=configurations.getProperty("mysql.database","dep10_sas");
            String port=configurations.getProperty("mysql.port","3306");
            String user=configurations.getProperty("mysql.user","root");
            String password=configurations.getProperty("mysql.password","");

            StringBuilder sb=new StringBuilder("jdbc:mysql://").append(host).append(":").append(port).append("/")
                    .append(database).append("?createDatabaseIfNotExist=true&allowMultiQueries=true");

            connection=DriverManager.getConnection(sb.toString(),user,password);
        } catch (FileNotFoundException e) {

            new Alert(Alert.AlertType.ERROR,"Configurations file do not exist").showAndWait();
            throw  new RuntimeException();
        }
        catch (IOException e) {

            new Alert(Alert.AlertType.ERROR,"Configurations file do not exist").showAndWait();
            throw  new RuntimeException();
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Failed to establish the database connection, try again.").showAndWait();
            throw  new RuntimeException(e);
        }


    }
    public static DBConnection getInstance(){
        return (dbConnection==null)?dbConnection=new DBConnection():dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
