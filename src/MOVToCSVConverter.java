import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MOVToCSVConverter {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load the OpenCV library
    }

//    public static void main(String[] args) {
//        String movFilePath = "C:\\Users\\ASUS\\Downloads\\vid1.mov"; // Path to the .mov file
//        String outputFolderPath = "E:\\CSV files"; // Path to save CSV files
//        int frameInterval = 1; // Save every frame
//
//        convertMOVToCSV(movFilePath, outputFolderPath, frameInterval);
//    }

    public void convertMOVToCSV(String movFilePath, String outputFolderPath, int frameInterval) {
        VideoCapture video = new VideoCapture(movFilePath);

        if (!video.isOpened()) {
            System.err.println("Error: Cannot open video file " + movFilePath);
            return;
        }

        // Create the output folder if it doesn't exist
        File folder = new File(outputFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Mat frame = new Mat();
        int frameCount = 0;
        int csvCount = 0;

        while (video.read(frame)) {
            // Process every 'frameInterval' frame
            if (frameCount % frameInterval == 0) {
                String csvFilePath = outputFolderPath + "/frame_" + csvCount + ".csv";
                saveFrameAsCSV(frame, csvFilePath);
                System.out.println("Saved frame " + csvCount + " to " + csvFilePath);
                csvCount++;
            }
            frameCount++;
        }

        video.release();
        System.out.println("Conversion complete. " + csvCount + " frames saved to CSV.");
    }

    private static void saveFrameAsCSV(Mat frame, String csvFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            // Convert the frame to grayscale if it's not already
            Mat grayFrame = new Mat();
            if (frame.channels() > 1) {
                Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
            } else {
                grayFrame = frame;
            }

            // Get the width, height, and pixel data
            int width = grayFrame.width();
            int height = grayFrame.height();
            byte[] data = new byte[(int) (grayFrame.total())];
            grayFrame.get(0, 0, data);

            // Write indexes in a single line with trailing commas
            for (int i = 1; i <= width * height; i++) {
                writer.write(i + ",");
            }
            writer.newLine();

            // Write grayscale pixel values row by row
            for (int y = 0; y < height; y++) {
                StringBuilder row = new StringBuilder();
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    int intensity = data[index] & 0xFF; // Grayscale intensity
                    row.append(intensity).append(",");
                }
                row.deleteCharAt(row.length() - 1); // Remove trailing comma
                writer.write(row.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving frame as CSV: " + e.getMessage());
        }
    }
}
