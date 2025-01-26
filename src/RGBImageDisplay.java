import javax.swing.*;
import java.awt.*;

public class RGBImageDisplay extends JPanel {
    private final int width;
    private final int height;
    private final Pixel[][] pixels;
    int k;

    public RGBImageDisplay(int width, int height, Pixel[][] pixels, int k) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.k = k;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = pixels[x][y].r;
                int g = pixels[x][y].g;
                int b = pixels[x][y].b;
                graphics.setColor(new Color(r, g, b)); // Set color using RGB
                graphics.fillRect(y * k, x * k, k, k); // Draw one pixel
            }
        }
    }
}
