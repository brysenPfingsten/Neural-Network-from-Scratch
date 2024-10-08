public class Calculus {
    public static void main(String[] args) {
        Layer layer = new Layer(10, 10);
        layer.toString();
    }
    public static double SigmoidDerivative(double x) {
        return x * (1 - x);
    }

    public static double CostDerivative(double predictedOutput, int expectedOutput) {
        return 2 * (predictedOutput - expectedOutput);
    }

    public static double ReluDerivative(double x) {
        return x >= 0 ? 1 : 0;
    }

}
