import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MOVToCSVConverter converter = new MOVToCSVConverter();

//        String movFilePath = "C:\\Users\\ASUS\\Downloads\\vid1.mov"; // Path to the .mov file
//        String outputFolderPath = "E:\\CSV files"; // Path to save CSV files
//        int frameInterval = 1; // Save every frame

//        converter.convertMOVToCSV(movFilePath, outputFolderPath, frameInterval);

        //"C:\Users\ASUS\Downloads\Dataset\image1_gray.csv"
        //"C:\\Users\\ASUS\\Downloads\\Telegram Desktop\\tableConvert.com_dqr06j (2).csv"
        //"C:\\Users\\ASUS\\Downloads\\Dataset\\image4_RGB.csv"
       /* QuadTree quadTree = new QuadTree(QuadTree.readCSV("C:\\Users\\ASUS\\Downloads\\Telegram Desktop\\Testcase\\T1.csv", ","));
        System.out.println("depth : " + quadTree.TreeDepth());
        System.out.println("picture size is " + QuadTree.size + " * " + QuadTree.size);
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 0; i++) {
            System.out.println("enter pixel to get its depth");
            int x = input.nextInt();
            int y = input.nextInt();
            System.out.println("pixel depth : " + quadTree.PixelDepth(x, y));
        }
        RGBImageDisplay display;
        JFrame frame = new JFrame("RGB Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true); // Remove borders and title bar

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        for (int i = 0; i < 0; i++) {
            System.out.println("enter the rectangle to get search ...");
            int x1 = input.nextInt();
            int y1 = input.nextInt();
            int x2 = input.nextInt();
            int y2 = input.nextInt();
            Pixel[][] b = quadTree.mask(x1, y1, x2, y2);
            int w = b[2047][2047].x;
            int h = b[2047][2047].y;
            for (int ii = 0; ii < w; ii++)
                for (int j = 0; j < h; j++) {
                    System.out.println(b[ii][j].x + " " + b[ii][j].y + " " + b[ii][j].r + " " + b[ii][j].g + " " + b[ii][j].b);
                }
            System.out.println("width : " + b[2047][2047].x + ", height : " + b[2047][2047].y);
            int k = max(w, h);
            k = max(100 / k, 1);
            display = new RGBImageDisplay(w, h, b, k);
            frame.getContentPane().removeAll();
            frame.add(display);

            gd.setFullScreenWindow(frame);
            frame.revalidate();
            frame.repaint();

            System.out.println(QuadTree.size);

            // Delay for a smooth transition (e.g., 100ms per frame)
            Thread.sleep(3000);
            gd.setFullScreenWindow(null);
            frame.dispose();
        }


        for (int i = 0; i < 0; i++) {
            System.out.println("enter the rectangle to get search ...");
            int x1 = input.nextInt();
            int y1 = input.nextInt();
            int x2 = input.nextInt();
            int y2 = input.nextInt();
            Pixel[][] b = quadTree.SearchSubspacesWithRange(x1, y1, x2, y2);
            int w = b[2047][2047].x;
            int h = b[2047][2047].y;
            for (int ii = 0; ii < w; ii++)
                for (int j = 0; j < h; j++) {
                    System.out.println(b[ii][j].x + " " + b[ii][j].y + " " + b[ii][j].r + " " + b[ii][j].g + " " + b[ii][j].b);
                }
            System.out.println("width : " + b[2047][2047].x + ", height : " + b[2047][2047].y);
            int k = max(w, h);
            k = max(100 / k, 1);
            display = new RGBImageDisplay(w, h, b, k);
            frame.getContentPane().removeAll();
            frame.add(display);

            gd.setFullScreenWindow(frame);
            frame.revalidate();
            frame.repaint();

            System.out.println(QuadTree.size);

            // Delay for a smooth transition (e.g., 100ms per frame)
            Thread.sleep(3000);
            gd.setFullScreenWindow(null);
            frame.dispose();
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("enter the new size to compress ...");
            int newSize = input.nextInt();
            Pixel[][] b = quadTree.Compress(newSize);
            int w = b[2047][2047].x;
            int h = b[2047][2047].y;
            int k = max(w, h);
            k = 100 / k;
            display = new RGBImageDisplay(w, h, b, 10);
            frame.getContentPane().removeAll();
            frame.add(display);

            gd.setFullScreenWindow(frame);
            frame.revalidate();
            frame.repaint();

            System.out.println(QuadTree.size);

            // Delay for a smooth transition (e.g., 100ms per frame)
            Thread.sleep(10000);
            gd.setFullScreenWindow(null);
            frame.dispose();
        }
        for (int i = 2; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                System.out.println(quadTree.image[i][j].r + " " + quadTree.image[i][j].g + " " + quadTree.image[i][j].b);
            }
        }
        */
        Scanner input = new Scanner(System.in);
        RGBImageDisplay display;
        JFrame frame = new JFrame("RGB Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setUndecorated(true); // Remove borders and title bar

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        System.out.println("enter the newSize to start");
        Pixel[][] b = QuadTree.readCSV("C:\\Users\\ASUS\\Downloads\\Dataset\\image4_RGB.csv", ",");
        display = new RGBImageDisplay(256, 256, b, 1);
        frame.getContentPane().removeAll();
        frame.add(display);

        gd.setFullScreenWindow(frame);
        frame.revalidate();
        frame.repaint();


//
//
//        int newSize = input.nextInt();
//        for (int i = 0; i < 60; i++) {
//            QuadTree quadT = new QuadTree(QuadTree.readCSV("C:\\Users\\ASUS\\Downloads\\Telegram Desktop\\frames_csv\\frame_" + i + ".csv", ","));
//            Pixel[][] b = quadT.Compress(newSize);
//            display = new RGBImageDisplay(newSize, newSize, b, 1);
//            int w = b[2047][2047].x;
//            int h = b[2047][2047].y;
//            int k = max(w, h);
//            k = 1024 / k;
//            display = new RGBImageDisplay(w, h, b, k);
//            frame.getContentPane().removeAll();
//            frame.add(display);
//
//            gd.setFullScreenWindow(frame);
//            frame.revalidate();
//            frame.repaint();
//
//            System.out.println(QuadTree.size);
//
//            // Delay for a smooth transition (e.g., 100ms per frame)
//            Thread.sleep(5000);
//        }

        gd.setFullScreenWindow(null);
        frame.dispose();

    }
}