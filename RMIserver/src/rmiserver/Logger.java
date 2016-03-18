/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rmiserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import static rmiserver.EncryptDecryptFile.encrypt;

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
            try {
                encryptfile(filepathLogin, "loginlog.txt");
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            System.out.println("Logger error:");
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logLogout(String username){
        try{
            FileWriter fw = new FileWriter(filepathLogin, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("[Logout] User \'"+username+"\' logged out.\n");
            bw.close();
            fw.close();
            try {
                encryptfile(filepathLogin, "loginlog.txt");
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            try {
                encryptfile(filepathOrder, "orderlog.txt");
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            try {
                encryptfile(filepathShip, "shippinglog.txt");
            } catch (Throwable ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            System.out.println("Logger error:");
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void encryptfile(File filepath, String outputfile) throws Throwable
    {
        FileInputStream fis = null;
        try {
            String key = "squirrel123"; // needs to be at least 8 characters for DES
            fis = new FileInputStream(filepath);
            FileOutputStream fos = new FileOutputStream(outputfile);
            encrypt(key, fis, fos);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
