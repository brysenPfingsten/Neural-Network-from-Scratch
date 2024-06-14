public class MyMath {
    public static double OutputCost(int expected, double[] output) {
        double loss = 0;

        for (int i = 0; i < output.length; i++) {
            if (i == expected) {
                loss += Math.pow((1 - output[i]), 2);
            }
            else {
                loss += Math.pow((0 - output[i]), 2);
            }
        }

        return loss;
    }

    
}
