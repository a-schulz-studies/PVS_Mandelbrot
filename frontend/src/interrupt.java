import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class interrupt implements Runnable{
    public void run(){
        Server server = null;
        try {
            server = (Server) Naming.lookup("rmi://localhost:2005/mandelbrot");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            server.allertClose();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
