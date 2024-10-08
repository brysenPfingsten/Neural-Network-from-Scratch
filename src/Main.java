
import java.util.regex.Pattern;




public class Main {
    public static void main(String[] args) {
        String regex = "\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        boolean matches = pattern.matcher("12207645").matches();
         System.out.println(matches ? 1 : 0);

        int[] powersOfTwo = new int[25];
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

        for (int i = 0; i < 25; i++) {
            int num = (int)Math.pow(2, i);
            powersOfTwo[i] = num;
        }

        for (int i = 1; i < primes.length; i++) {
            System.out.println(primes[i]);
            int[] temp = new int[powersOfTwo.length];
            for (int j = 0; j < powersOfTwo.length; j++) {
                int floor = Math.floorDiv(powersOfTwo[j], primes[i]);
                temp[j] = powersOfTwo[j] - (floor * primes[i]);
            }
            for (int k : findSmallestRepeatingSubarray(temp)) {
                System.out.print(k + "  ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public static int[] findSmallestRepeatingSubarray(int[] arr) {
        int secondOneIndex = findSecondOne(arr);
        if (secondOneIndex == 0) {
            return new int[0];
        } else {
            int[] temp = new int[secondOneIndex];
            for (int i = 0; i < secondOneIndex; i++) {
                temp[i] = arr[i];
            }
            return temp;
        }
    }

    public static int findSecondOne(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == 1)
                return i;
        }
        return 0;
    }
}
