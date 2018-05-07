import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Coordinador_RMI{

	static Coordinador_Hilo coord;
	static Thread coord_Hilo;

    private Coordinador_RMI() {}

    public static void main(String[] args) {

        //String host = (args.length < 1) ? null : args[0];
        try {
            //Registry registry = LocateRegistry.getRegistry("192.168.1.21", 1099);
            Registry registry1 = LocateRegistry.getRegistry("localhost", 1099);
            Clock_RMI stub1 = (Clock_RMI) registry1.lookup("Clock_RMI");
            //int hour1 = stub.getSeconds_RMI();
            //System.out.println("response: " + response);
			
			Registry registry2 = LocateRegistry.getRegistry("localhost", 1099);
            Clock_RMI stub2 = (Clock_RMI) registry2.lookup("Clock_RMI");
            //int hour2 = stub.getSeconds_RMI();
            //System.out.println("response: " + response);
			
			Registry registry3 = LocateRegistry.getRegistry("localhost", 1099);
            Clock_RMI stub3 = (Clock_RMI) registry3.lookup("Clock_RMI");
            //int hour3 = stub.getSeconds_RMI();
            //System.out.println("response: " + response);
			
			//Algoritmo de Berkeley
			coord = new Coordinador_Hilo(stub1, stub2, stub3);
			coord_Hilo = new Thread(coord);
			coord_Hilo.start();
			
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}