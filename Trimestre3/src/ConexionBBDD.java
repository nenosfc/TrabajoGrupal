

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class ConexionBBDD
    {
        private Connection mconnection;

        public boolean success() {
            return this.mconnection!=null;
        }

        public ConexionBBDD(String host, String port, String user, String pass, String bd) {
            String thost = "sql7.freesqldatabase.com";
            String tport = "3306";
            String tuser = "sql7779140";
            String tpass = "UQp6YIRvGB";
            String tbd = "sql7779140";
            String turl = "jdbc:mysql://" + thost + ":" + tport + "/" + tbd + "?useSSL=false&serverTimezone=UTC";
            try {
                // Cargar el driver JDBC (opcional con JDBC 4.0+ pero recomendado)
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.mconnection =DriverManager.getConnection(turl, tuser, tpass);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e);
                this.mconnection = null;
            }
        }
        public Connection getConnection() {
            return this.mconnection;
        }

    }


