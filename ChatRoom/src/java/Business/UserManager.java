/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import DAL.DAO_UserSelected;


/**
 *
 * @author Job
 */
public class UserManager {
    public boolean RealizarLogin(String name){
        return new DAO_UserSelected().RealizarLogin(name);
    }
    
    public boolean RetirarLogin(String name){
        return new DAO_UserSelected().SairdoChat(name);
    }

    public void TrocarNome(String oldName, String newName) {
        new DAO_UserSelected().TrocarNome(oldName, newName);
    }

    public String RealizarSorteio(){
        return new DAO_UserSelected().RealizarSorteio();
    }

}
