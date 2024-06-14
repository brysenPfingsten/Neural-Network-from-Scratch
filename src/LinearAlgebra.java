

public class LinearAlgebra {
    public static double[] matrixMultiplication(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        if (vector.length != columns)
            throw new IllegalArgumentException("Invalid inputs!");

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            result[i] = 0;
            for (int j = 0; j < columns; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
    }

    public static double[] matrixMultiplication(double[][] matrix, int[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        if (vector.length != columns)
            throw new IllegalArgumentException("Invalid inputs!");

        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            result[i] = 0;
            for (int j = 0; j < columns; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
    }

    public static double[] vectorAddition(double[] vector, double[] bias) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] += bias[i];
        }

        return vector;
    }

    public static double[] reLU(double[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = Math.max(0, input[i]);
    }
    return input;
    }

    public static double[] sigmoid(double[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = 1 / (1 + Math.exp(-input[i]));
        }
        return input;
    }

}
