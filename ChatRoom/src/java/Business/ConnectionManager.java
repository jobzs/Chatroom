/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Job
 */
public class ConnectionManager {
    private Connection conn;
    
    
    private void conectar() throws ClassNotFoundException{ //esse erro ocorre quando não encontra o driver
        System.out.println("Conectando ao banco...");
        try{
            Class.forName("com.mysql.jdbc.Driver"); //verifica o driver via reflection
            System.out.println("conn:"+conn);
            //estabelece a conexao usando a string de conexao
            conn= (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/teste?autoReconnect=true&verifyServerCertificate=false&useSSL=false","novousuario","password");
            if(conn!=null){
                System.out.println("Conexão realizada!");
                System.out.println("conn:"+conn);
            }
        }catch(SQLException e){ //esse erro ocorre quando não conexta ao banco
            e.printStackTrace();
        }
    }
    
    public Connection getConexao() throws ClassNotFoundException{
       
        conectar();
        return conn;
    }
}
