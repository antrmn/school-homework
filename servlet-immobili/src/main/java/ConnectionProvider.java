
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
    
    private static final DataSource ds;
    
    static {
    	try {
            ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/immobili_db");
        } catch (NamingException e) {
        	e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }
    
    
    public static Connection getConnection() throws SQLException {  
        return ds.getConnection();             
    }
}