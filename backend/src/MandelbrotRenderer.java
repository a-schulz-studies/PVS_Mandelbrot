import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MandelbrotRenderer implements Runnable {

    private int width = 1920;
    private int height = 1080;
    private int maxIterations;


    private double radius = 2;
    private double divergence = radius * radius;

    private int xOffset = -(width - 1)/2;
    private int yOffset = (height - 1)/2;

    public Boolean exitThread;

    private int black = 0;

    private MbrotParameter[] mbrots;

    private ColorScheme color;

    public MandelbrotRenderer(MbrotParameter[] imgs, ColorScheme color, int maxIterations) {
        mbrots = imgs;
        this.color = color;
        this.maxIterations = maxIterations;
    }


    public void run() {
        int iteration, point;
        double a, b, aOld, x, y;
        int[] colorPalette = new int[maxIterations];
        for (int i = 0; i < maxIterations; i++) {
            colorPalette[i] = color.getColor(i);
        }

        for (MbrotParameter currMbrot : mbrots){
            BufferedImage img = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            File out = new File("backend/images/"+currMbrot.fName + ".png");
            for (int r = 0; r < height; r++)
            {
                for (int c = 0; c < width; c++)
                {
                    x = currMbrot.xCenter + (xOffset + c) / currMbrot.resolution;
                    y = currMbrot.yCenter + (yOffset - r) / currMbrot.resolution;

                    iteration = 0;
                    a = x;
                    b = y;
                    while (a*a + b*b <= divergence && iteration < maxIterations)
                    {
                        aOld = a;
                        a = a*a - b*b + x;

                        b = 2*aOld*b + y;
                        iteration ++;
                    }
                    if (iteration == maxIterations)
                        point = black;
                    else {
                        point = colorPalette[iteration];
                    }
                    img.setRGB(c, r, point);
                }
            }
            try {
                ImageIO.write(img, "png", out);
            }
            catch (Exception e) {
                System.out.println("Oops.");
            }
        }
    }
}
