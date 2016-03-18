/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.*;
import java.rmi.Remote;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author Faculudade2015-2016
 */
public interface Interface extends Remote
{
    public Boolean auth(String user, String password) throws RemoteException;
    public Connection connecttoDB(String dbname) throws RemoteException;
    public String ListInventoryTrees()throws RemoteException;

    public String ListInventorySeeds()throws RemoteException;
    public String ListInventoryShrubs()throws RemoteException;
    
    public Boolean ConnectToOrder()throws RemoteException;
    public Boolean InsertOrder(String dateTimeStamp, String firstName, String lastName, 
            String customerAddress, String phoneNumber, Float fCost, String orderTableName) throws RemoteException;
    public Boolean FinishOrder(String orderTableName, String productID, String description
                            ,Float perUnitCost, String dateTimeStamp) throws RemoteException;
    
    public void LogOut() throws RemoteException;
}
