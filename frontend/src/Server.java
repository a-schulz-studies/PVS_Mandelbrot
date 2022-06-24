import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{
    public ImageIcon getMbrotIMG() throws RemoteException;
    public ImageIcon getWallpaper() throws RemoteException;
    public int sendMousepos(int x, int y) throws RemoteException;
    public void allertClose() throws RemoteException;
    public Boolean getRenderedStart() throws RemoteException;
}
