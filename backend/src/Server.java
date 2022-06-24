import javax.swing.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote{

    public ImageIcon getMbrotIMG() throws IOException;
    public ImageIcon getWallpaper() throws IOException;
    public int sendMousepos(int x, int y) throws IOException;
    public void allertClose() throws IOException, InterruptedException;
    public Boolean getRenderedStart() throws RemoteException;
}
