

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

    public static double[][] multiplyWeightsAndNodes(double[][] weights, double[] nodes) {
        int rows = weights.length;
        int cols = weights[0].length;
        double[][] result = new double[rows][cols];
        
        double sumOfNodeValues = 0;
        for (double nodeValue : nodes) {
            sumOfNodeValues += nodeValue;
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = weights[i][j] * sumOfNodeValues;
            }
        }
        
        return result;
    }

    public static double[][] addMatrices(double[][] matrix1, double[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        double[][] result = new double[rows][cols];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        
        return result;
    }

    public static double[] addVectors(double[] vector1, double[] vector2) {
        int rows = vector1.length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            result[i] = vector1[i] + vector2[i];
        }

        return result;
    }

    // public static double[] vectorAddition(double[] vector, double[] bias) {
    //     for (int i = 0; i < vector.length; i++) {
    //         vector[i] += bias[i];
    //     }

    //     return vector;
    // }

    public static double[][] scaleMatrix(double[][] matrix, double scalar) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[][] result = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }
        return result;
    }

    public static double[] scaleVector(double[] vector, double scalar) {
        int rows = vector.length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            result[i] = vector[i] * scalar;
        }

        return result;
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
