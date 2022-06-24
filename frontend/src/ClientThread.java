import javax.swing.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientThread extends JFrame implements Runnable{

    public boolean rendering = true;

    public ClientThread(){

    }
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public void onExit() throws RemoteException, MalformedURLException, NotBoundException {
        Thread threads = new Thread( new interrupt());
        threads.start();
        System.exit(0);
    }



    public void run()
    {

        Server server = null;
        try{

            server = (Server) Naming.lookup("rmi://localhost:2005/mandelbrot");
            JFrame frame = new JFrame("Image");
            WindowListener exitListener = new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        onExit();
                    } catch (RemoteException remoteException) {
                        remoteException.printStackTrace();
                    } catch (MalformedURLException malformedURLException) {
                        malformedURLException.printStackTrace();
                    } catch (NotBoundException notBoundException) {
                        notBoundException.printStackTrace();
                    }
                }
            };
            while ( true) {
                JLabel displayField;
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                frame.addWindowListener(exitListener);
                ImageIcon br = null;
                br = server.getMbrotIMG();
                displayField = new JLabel(br);
                frame.add(displayField);
                frame.setSize(1920, 1080);
                frame.setVisible(true);
                System.out.println(br);
                sleep(250);
                frame.remove(displayField);
            }


        }catch(NotBoundException e){
            e.printStackTrace();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (RemoteException e){
            e.printStackTrace();
        }

    }
}