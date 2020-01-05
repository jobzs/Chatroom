/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;


public class Model_User {
    
    private String name;
    private List<String> Message = new ArrayList<>();
    
    public Model_User(String name){
        this.name = name;
    }
    
    public void TrocarNome(String name){
        this.name = name;
    }

 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMessage() {
        return Message;
    }

    public void setMessage(List<String> Message) {
        this.Message = Message;
    }
    
    
    
}
