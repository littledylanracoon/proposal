import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {

  public static void main(String[] args) throws Exception {
    BufferedImage img = colorImage(ImageIO.read(new File("pianodisplay.png")));
    ImageIO.write(img, "png", new File("Test.png"));
  }

  private static BufferedImage colorImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    WritableRaster raster = image.getRaster();

    for (int xx = 0; xx < width; xx++) {
      for (int yy = 0; yy < height; yy++) {
        int[] pixels = raster.getPixel(xx, yy, (int[]) null);
        pixels[0] = 255;
        pixels[1] = 102;
        pixels[2] = 102;
        raster.setPixel(xx, yy, pixels);
      }
    }
    return image;
  }
}
