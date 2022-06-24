import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client extends JFrame{
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] argv)
    {
        final Boolean[] render = {false};
        try {
            Server server = (Server) Naming.lookup("rmi://localhost:2005/mandelbrot");
            JFrame frame = new JFrame("Image");
            final JLabel[] displayField = new JLabel[1];
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ImageIcon br = server.getWallpaper();
            displayField[0] = new JLabel(br);
            frame.add(displayField[0]);
            frame.setSize(1920, 1080);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setVisible(true);
            displayField[0].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent m) {
                    System.out.println(m.getX());
                    System.out.println(m.getY());
                    try {
                        server.sendMousepos(m.getX(),m.getY());
                        Thread threads = new Thread();
                        threads = new Thread( new ClientThread());
                        frame.dispose();
                        sleep(10000);
                        threads.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    frame.dispose();
                }
            });
        }catch(NotBoundException e){
            e.printStackTrace();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(RemoteException e){
            e.printStackTrace();
        }

    }
}