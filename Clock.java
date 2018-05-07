import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Clock implements Clock_RMI{

	static Server_RMI miVentana;
	static Thread window;
	
	public void setSpeed_RMI(int sp){
		miVentana.setSpeed_view(sp);
	}
	
	public void setSeconds_RMI(int sc){
		miVentana.setSeconds_view(sc);
	}
	
	public int getSeconds_RMI(){
		return miVentana.getSeconds_view();
	}
	
	Clock(){
	}
	
	public static void main (String args[]){
		
		miVentana = new Server_RMI(0);
		window = new Thread(miVentana);
		window.start();
		
		try {
			
			//System.setProperty("java.rmi.server.hostname","192.168.1.66");
			Clock obj = new Clock();
			Clock_RMI stub = (Clock_RMI) UnicastRemoteObject.exportObject(obj, 0);
			System.out.println("stop_wait_a_minute");
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Clock_RMI", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
		
	}

}