/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author Pedro
 */
public class Logger {
    
    private File filepathLogin;
    private File filepathOrder;
    private File filepathShip;
    
    public Logger(String filepathShip){
        this.filepathShip = new File(filepathShip);
    }
    
    public Logger(String filepathLogin, String filepathOrder){
        this.filepathLogin = new File(filepathLogin);
        this.filepathOrder = new File(filepathOrder);
    }
    
    public Logger(String filepathLogin, String filepathOrder, String filepathShip){
        this.filepathLogin = new File(filepathLogin);
        this.filepathOrder = new File(filepathOrder);
        this.filepathShip = new File(filepathShip);
    }
    
    public void logLogin(String username){
        try{
            FileWriter fw = new FileWriter(filepathLogin, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("[Login] User \'"+username+"\' logged in.\n");
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Logger error:");
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logOrder(String username, String orderID, String timestamp){
        try{
            FileWriter fw = new FileWriter(filepathOrder, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("[Order "+orderID+"] "+timestamp+": User \'"+username+"\' added order.\n");
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Logger error:");
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logShip(String orderID, String timestamp){
        try{
            FileWriter fw = new FileWriter(filepathShip, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("[Shipment] "+timestamp+" Order "+orderID+" was shipped.\n");
            bw.close();
            fw.close();
        } catch (IOException ex) {
            System.out.println("Logger error:");
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
