import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Mnist {
    
    public static int[] readLabels(String filename) throws IOException {
        int[] labels;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            dis.readInt(); // Magic number
            int numLabels = dis.readInt();
            labels = new int[numLabels];
            for (int i = 0; i < numLabels; i++) {
                labels[i] = dis.readUnsignedByte();
            }
        } // Magic number
        return labels;
    }

    public static int[][][] readImages(String filename) throws IOException {
        int[][][] images;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            dis.readInt(); // Magic number
            int numImages = dis.readInt();
            int numRows = dis.readInt();
            int numCols = dis.readInt();
            images = new int[numImages][numRows][numCols];
            for (int i = 0; i < numImages; i++) { //numImages
                for (int r = 0; r < numRows; r++) {
                    for (int c = 0; c < numCols; c++) {
                        images[i][r][c] = dis.readUnsignedByte();
                    }
                }
            }
        } // Magic number
        return images;
    }

    public static void main(String[] args) {
        try {
            String trainingImagesPath = "src\\train-images.idx3-ubyte";
            String trainingLabelsPath = "src\\train-labels.idx1-ubyte";
            int[][][] trainingImages = readImages(trainingImagesPath);
            int[] trainingLabels = readLabels(trainingLabelsPath);
            
            // Example: Print the first image and label
            System.out.println("First label: " + trainingLabels[0]);
            int[][] imageData = trainingImages[0];
            int[] columnImageData = new int[784];
            for (int i = 0; i < imageData.length; i++) {
                for (int j = 0; j < imageData.length; j++) {
                    columnImageData[i*28+j] = imageData[i][j];
                }
            }
            for (int i = 0; i < columnImageData.length; i++) {
                System.out.println(columnImageData[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

