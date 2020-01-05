/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Model.Model_User;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Business.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Job
 */
public class DAO_UserSelected {
    
    private ConnectionManager conn;
    
    public void adicionar(String nome) throws SQLException, InstantiationException, IllegalAccessException{
        
        conn=new ConnectionManager();
        
        String sql="Insert into sorteio(nome) values(?)";
        
        PreparedStatement ps;
        ps = null;
        try {
            ps = conn.getConexao().prepareStatement(sql);
            ps.setString(0, nome);
            ps.execute();
        } catch (ClassNotFoundException ex) {
        }
        

        
    }
    
    public static List<Model_User> usuariosLogados = new ArrayList<Model_User>();
    public static List<Model_User> usuariosParaSortear = new ArrayList<Model_User>();
    public static List<Model_User> usuariosJaSorteados = new ArrayList<Model_User>();
    
    
    
    public boolean RealizarLogin(String name){
        Model_User newUser = new Model_User(name);
        usuariosLogados.add(newUser);
        usuariosParaSortear.add(newUser);
        return true;
    }
    
    

            
    public String RealizarSorteio(){
       
        if (!usuariosParaSortear.isEmpty()){
        int valorSorteado = new Random().nextInt(usuariosParaSortear.size());
        //Collections.shuffle(usuariosLogados);
                //new Random(0 , usuariosLogados.size());
                
        int count = 0;
        
            for(Model_User item : usuariosParaSortear){
                if(valorSorteado == count){
                    item.getName();
                    String nomeDoSorteado;
                    nomeDoSorteado = item.getName();
                    usuariosParaSortear.remove(item);
                    usuariosJaSorteados.add(item);
                    if(nomeDoSorteado != null){
                        try {
                            adicionar(nomeDoSorteado);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (InstantiationException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                        return nomeDoSorteado;
                        }else{
                         return RealizarSorteio();
            }
            
            }
                count++;
    } 
    }else{ 
                String listaVazia;
                listaVazia = "Não existe mais usuarios para sortear!";
                return listaVazia;
            }
        return RealizarSorteio();
    }
            
    public boolean SairdoChat(String name){
        boolean saiuDoChat = false;
        
        for(Model_User item : usuariosLogados){
            if(item.getName() == name){
                usuariosLogados.remove(item);
                usuariosParaSortear.remove(item);
                saiuDoChat = true;
            }
        }
        
        return saiuDoChat;
    }

    public void TrocarNome(String oldName, String newName) {
        for(Model_User item : usuariosLogados){
            if(item.getName() == oldName){
                item.TrocarNome(newName);
            }
        }
    }
}