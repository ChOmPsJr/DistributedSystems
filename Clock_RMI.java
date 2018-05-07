import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Clock_RMI extends Remote {
	
	void setSpeed_RMI(int sp) throws RemoteException;
	void setSeconds_RMI(int sc) throws RemoteException;
	int getSeconds_RMI() throws RemoteException;
	
}