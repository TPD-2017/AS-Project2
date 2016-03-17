/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro
 */
public class Logger {
    
    private String filepathLogin;
    private String filepathOps;
    private String filepathShip;
    
    
    public Logger(String filepath){
    }
    
    public Logger(String filepathLogin, String filepathOps, String filepathShip){
        this.filepathLogin = filepathLogin;
        this.filepathOps = filepathOps;
        this.filepathShip = filepathShip;
    }
}
