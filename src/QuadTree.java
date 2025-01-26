import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class QuadTree {

    static int size;

    LinkedList root;

    public Pixel[][] image;

    List<Node> searchRange;

    public static Pixel[][] readCSV(String filePath, String delimiter) {

        Pixel[][] image = new Pixel[2048][2048];

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] pixelData = line.split(delimiter);
                if (pixelData[0].charAt(0) != '\"') {
                    size = (int) sqrt((double) pixelData.length);
                    for (int i = 0; i < pixelData.length; i ++) {
                        int r, g, b;
                        r = Integer.parseInt(pixelData[i]);
                        g = r;
                        b = r;
                        image[i / size][i % size] = new Pixel(i / size, i % size, r, g, b);
                    }
                }
                else {
                    size = (int) sqrt((double) pixelData.length / 3);
                    for (int i = 0; i + 2 < pixelData.length; i += 3) {
                        int r, g, b;
                        if (pixelData[i].charAt(0) != '\"') {
                            r = Integer.parseInt(pixelData[i]);
                            g = r;
                            b = r;
                        } else {
                            r = Integer.parseInt(pixelData[i].substring(1));
                            g = Integer.parseInt(pixelData[i + 1]);
                            b = Integer.parseInt(pixelData[i + 2].substring(0, pixelData[i + 2].length() - 1));
                        }
                        int j = i / 3;
                        image[j / size][j % size] = new Pixel(j / size, j % size, r, g, b);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing the RGB data: " + e.getMessage());
        }

        return image;
    }

    public QuadTree(Pixel[][] image) {
        this.image = image;
        Node root = BuildQuadTree(new Node(size, image[0][0]));
        this.root = new LinkedList(root);
//        print(root);
    }

    void print(Node root) {
        System.out.println(root.size + " " + root.start.x + " " + root.start.y + " " + root.isLeaf(image) + " " + root.start.r + " " + root.start.g + " " + root.start.b);

        if (root.isLeaf(image)) {
            return;
        }

        print(root.child0);
        print(root.child1);
        print(root.child2);
        print(root.child3);
    }

    public Node BuildQuadTree(Node root) {
        if (root.isLeaf(image) || root.size == 1)
            return root;


        root.child0 = BuildQuadTree(new Node(root.size / 2, root.start));
        root.child1 = BuildQuadTree(new Node(root.size / 2, image[root.start.x][root.start.y + root.size / 2]));
        root.child2 = BuildQuadTree(new Node(root.size / 2, image[root.start.x + root.size / 2][root.start.y]));
        root.child3 = BuildQuadTree(new Node(root.size / 2, image[root.start.x + root.size / 2][root.start.y + root.size / 2]));

        return root;
    }

    public int TreeDepth() {
        return treeDepth(root.head);
    }

    private int treeDepth(Node root) {
        if (root.isLeaf(image))
            return 0;

        return 1 + max(treeDepth(root.child0), max(treeDepth(root.child1), max(treeDepth(root.child2), treeDepth(root.child3))));
    }

    public int PixelDepth(int px, int py) {
        return pixelDepth(root.head, px, py, 0);
    }

    private int pixelDepth(Node root, int px, int py, int depth) {
        if (root.isLeaf(image)) {
            return depth;
        }

        if (root.start.x <= px && root.start.x + root.size / 2 > px)
            if (root.start.y <= py && root.start.y + root.size / 2 > py)
                return pixelDepth(root.child0, px, py, depth + 1);
            else
                return pixelDepth(root.child1, px, py, depth + 1);
        else
            if (root.start.y <= py && root.start.y + root.size / 2 > py)
                return pixelDepth(root.child2, px, py, depth + 1);
            else
                return pixelDepth(root.child3, px, py, depth + 1);
    }

    public Pixel[][] SearchSubspacesWithRange(int x1, int y1, int x2, int y2) {
        searchRange = new ArrayList<>();
        searchSubspacesWithRange(root.head, x1, y1, x2, y2);

        List<Pixel> searchRangeByPixel = new ArrayList<>();

        int xx1 = 2048, yy1 = 2048, xx2 = -1, yy2 = -1;

        for (Node u: searchRange)
            for (int i = 0; i < u.size; i++)
                for (int j = 0; j < u.size; j++) {
                    searchRangeByPixel.add(image[u.start.x + i][u.start.y + j]);
                    xx1 = min(xx1, u.start.x + i);
                    yy1 = min(yy1, u.start.y + j);
                    xx2 = max(xx2, u.start.x + i);
                    yy2 = max(yy2, u.start.y + j);
                }

        int sizeX = xx2 - xx1 + 1;
        int sizeY = yy2 - yy1 + 1;

        Pixel[][] result = new Pixel[2048][2048];

        for (Pixel p: searchRangeByPixel)
            result[p.x - xx1][p.y - yy1] = new Pixel(p.x - xx1, p.y - yy1, p.r, p.g, p.b);

        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                if (result[i][j] == null)
                    result[i][j] = new Pixel(i, j, 255, 255, 255);

        result[2047][2047] = new Pixel(sizeX, sizeY, 0, 0, 0);

        return result;
    }

    private void searchSubspacesWithRange(Node root, int x1, int y1, int x2, int y2) {
        if (root.isLeaf(image)) {
            searchRange.add(root);
            return;
        }

        if (! (root.start.x + root.size / 2 <= x1 || x2 < root.start.x || root.start.y + root.size / 2 <= y1 || y2 < root.start.y))
            searchSubspacesWithRange(root.child0, x1, y1, x2, y2);
        if (! (root.start.x + root.size / 2 <= x1 || x2 < root.start.x || root.start.y + root.size <= y1 || y2 < root.start.y + root.size / 2))
            searchSubspacesWithRange(root.child1, x1, y1, x2, y2);
        if (! (root.start.x + root.size <= x1 || x2 < root.start.x + root.size / 2 || root.start.y + root.size / 2 <= y1 || y2 < root.start.y))
            searchSubspacesWithRange(root.child2, x1, y1, x2, y2);
        if (! (root.start.x + root.size <= x1 || x2 < root.start.x + root.size / 2 || root.start.y + root.size <= y1 || y2 < root.start.y + root.size / 2))
            searchSubspacesWithRange(root.child3, x1, y1, x2, y2);

    }


    public Pixel[][] mask(int x1, int y1, int x2, int y2) {
        searchRange = new ArrayList<>();
        searchSubspacesWithRange(root.head, x1, y1, x2, y2);

        List<Pixel> searchRangeByPixel = new ArrayList<>();

        int xx1 = 2048, yy1 = 2048, xx2 = -1, yy2 = -1;

        for (Node u: searchRange)
            for (int i = 0; i <  u.size; i++)
                for (int j = 0; j <  u.size; j++) {
                    searchRangeByPixel.add(image[u.start.x + i][u.start.y + j]);
                   xx1 = min(xx1, u.start.x + i);
                   yy1 = min(yy1, u.start.y + j);
                   xx2 = max(xx2, u.start.x + i);
                   yy2 = max(yy2, u.start.y + j);
                }

        int sizeX = xx2 - xx1 + 1;
        int sizeY = yy2 - yy1 + 1;

        Pixel[][] result = new Pixel[2048][2048];

        for (Pixel p: searchRangeByPixel)
            result[p.x - xx1][p.y - yy1] = new Pixel(p.x - xx1, p.y -yy1, 255, 255, 255);

        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                if (result[i][j] == null)
                    result[i][j] = new Pixel(i, j, image[i + xx1][j + yy1].r, image[i + xx1][j + yy1].g, image[i + xx1][j + yy1].b);

        result[2047][2047] = new Pixel(sizeX, sizeY, 0, 0, 0);

        return result;
    }

    public Pixel[][] Compress(int newSize) {
        int scale = root.head.size / newSize;

        int maxDepth = -1;

        int tmp = newSize;
        while (tmp > 0) {
            maxDepth++;
            tmp /= 2;
        }

        compress(root.head, 0, maxDepth);

        Pixel[][] result = new Pixel[2048][2048];

        for (int i = 0; i < root.head.size; i += scale)
            for (int j = 0; j < root.head.size; j += scale)
                result[i / scale][j / scale] = new Pixel(i / scale, j / scale, image[i][j].r, image[i][j].g, image[i][j].b);

        image = result;

        image[2047][2047] = new Pixel(newSize, newSize, 0, 0, 0);

        return image;
    }

    private void compress(Node root, int depth, int maxDepth) {
        if (root.isLeaf(image)) {
            updateImage(root);
            return;
        }

        int newDepth = depth + 1;

        if (newDepth >= maxDepth) {
            int R = 0, G = 0, B = 0;
            for (int i = 0; i < root.size; i++)
                for (int j = 0; j < root.size; j++) {
                    R += image[root.start.x + i][root.start.y + j].r;
                    G += image[root.start.x + i][root.start.y + j].g;
                    B += image[root.start.x + i][root.start.y + j].b;
                }

            root.start.r = R / (root.size * root.size);
            root.start.g = G / (root.size * root.size);
            root.start.b = B / (root.size * root.size);

            updateImage(root);
            return;
        }

        compress(root.child0, newDepth, maxDepth);
        compress(root.child1, newDepth, maxDepth);
        compress(root.child2, newDepth, maxDepth);
        compress(root.child3, newDepth, maxDepth);
    }

    private void updateImage(Node root) {
        for (int i = 0; i < root.size; i++)
            for (int j = 0; j < root.size; j++) {
                image[root.start.x + i][root.start.y + j].r = image[root.start.x][root.start.y].r;
                image[root.start.x + i][root.start.y + j].g = image[root.start.x][root.start.y].g;
                image[root.start.x + i][root.start.y + j].b = image[root.start.x][root.start.y].b;
            }
    }
}
