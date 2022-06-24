
public class ColorScheme {

    private int maxIterations;
    public ColorScheme(int maxIterations){
        this.maxIterations = maxIterations;
    }

    public int getColor(int i) {
        int a = (int) (255 * ((double) i) / (maxIterations / 4));
        return
                ( (0) | (2*a<<16) | (a<<8) | ((a*2)<<0) );
    }

}
