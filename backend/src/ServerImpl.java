import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;



public class ServerImpl  extends UnicastRemoteObject implements Server {
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static final long serialVersionUID = -9713246362486238L;
    StartRendering startRendering;
    long i;

    protected ServerImpl() throws RemoteException{
        super();
        File file = new File( "backend/images");
        if ( file.isDirectory() )
        {
            File[] listFiles = file.listFiles();

            for ( int i = 0; i < listFiles.length; i++ )
            {
                file=( listFiles[ i ] );
                file.delete();
            }
        }
        startRendering = new StartRendering();
        i = 1;
        startRendering.start();
    }

    @Override
    public Boolean getRenderedStart() throws RemoteException{
        if (Steps.getSteps_rendered()>=3){
            return Boolean.TRUE;
        }
        else{
            return  Boolean.FALSE;
        }
    }

    @Override
    public ImageIcon getWallpaper() throws IOException{
        return new ImageIcon("backend/Wallpaper/Wallpaper.png");
    }

    @Override
    public int sendMousepos(int x, int y)  throws IOException{
        if(x>960){
            Steps.setxCenter((x-960d)/960d*1.92d);
        }
        else{
            if(960==x){
                Steps.setxCenter(0);
            }
            else{
                Steps.setxCenter((-(960d-x)/960d)*1.92d);
            }
        }

        if(y>540){
            Steps.setyCenter(-(y-540d)/540d*1.07d);
        }
        else{
            if(540==y){
                Steps.setyCenter(0);
            }
            else{
                Steps.setyCenter((540.0-y)/540*1.07);
            }

        }
        System.out.println(Steps.getxCenter());
        System.out.println(Steps.getyCenter());
        return 1;
    }

    @Override
    public void allertClose() throws IOException, InterruptedException {
        sleep(100);
        System.out.println("close");
        Steps.setxCenter(-10);
        Steps.setyCenter(-10);
        Steps.setSteps_rendered(0);
        Steps.setSteps_displayed(0);
        this.i = 1;
        startRendering.stopThread();
        startRendering.join();
        startRendering = null;
        File file = new File( "backend/images");
        if ( file.isDirectory() )
        {
            File[] listFiles = file.listFiles();

            for ( int i = 0; i < listFiles.length; i++ )
            {
                file=( listFiles[ i ] );
                file.delete();
            }
        }
        startRendering = new StartRendering();
        startRendering.start();
    }



    @Override
    public ImageIcon getMbrotIMG() throws IOException {
        while(true) {
            if (Steps.getSteps_displayed()<=Steps.getSteps_rendered()-1) {
                break;
            } else {
                System.out.println("Waiting for Render.");
                continue;
            }
        }
        ImageIcon img  = new ImageIcon("backend/images/mbrot" + i + ".png");
        if(i % 12==0){
            Steps.setSteps_displayed(Steps.getSteps_displayed()+1);
        }
        if(i>=4) {
            Path path = Paths.get("backend/images/mbrot" + (i - 3) + ".png");
            Files.deleteIfExists(path);
        }
        i++;
        return img;
    }

    public static void main (String[] args){
        try{
            Server provider = new ServerImpl();
            LocateRegistry.createRegistry(2005);
            Naming.rebind("rmi://localhost:2005/mandelbrot", provider);
            System.out.println("Server erfolgreich gestartet");
        }catch(RemoteException | MalformedURLException e){
            e.printStackTrace();
        }
    }

}
