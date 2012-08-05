
public class NumberTypeTesting {

    static final long ITERATIONS = 1000000000;

    public static void main(String[] args) {

        final double DOUBLE_PRIME = 73;
        final int INT_PRIME = 73;
        final long LONG_PRIME = 73;

        long start;
        long end;
        long count;

        double valueDoubleA = 1.00;
        double valueDoubleB = Math.PI; // pi = 3.142
        double valueDoubleC = Math.E; // e = 2.718

        int valueIntA = 1;
        int valueIntB = 10;
        int valueIntC = 31;

        long valueLongA = 1;
        long valueLongB = 31;
        long valueLongC = 33;

        // Integers
        count = ITERATIONS;
        start = System.nanoTime();
        while (count-- != 0) {
            valueIntA += valueIntC * valueIntB / INT_PRIME;
        }
        end = System.nanoTime();
        System.out.printf("Integer time = %d msec%n", (end - start) / 1000000);

        // Long
        count = ITERATIONS;
        start = System.nanoTime();
        while (count-- != 0) {
            valueLongA += valueLongC * valueLongB / LONG_PRIME;
        }
        end = System.nanoTime();
        System.out.printf("Long time = %d msec%n", (end - start) / 1000000);

        // Double
        count = ITERATIONS;
        start = System.nanoTime();
        while (count-- != 0) {
            valueDoubleA += valueDoubleC * valueDoubleB / DOUBLE_PRIME;
        }
        end = System.nanoTime();
        System.out.printf("Double time = %d msec%n", (end - start) / 1000000);

    }
}
