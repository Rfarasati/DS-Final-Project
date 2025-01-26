public class Pixel {
    int x;
    int y;
    int r, g, b;

    public Pixel(int x, int y, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    Boolean equals(Pixel p) {
        return p.r == r && p.g == g && p.b == b;
    }
}
