/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Faculudade2015-2016
 */
public class ArrancarServer {
    public static void main(String[] args) throws RemoteException
	{
		Registry reg = LocateRegistry.createRegistry(1099);
		reg.rebind("Interface", new InterfaceServer());
                
                System.out.println("DONE");
	}
}
