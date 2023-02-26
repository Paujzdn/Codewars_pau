package kata.kyu3;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.*;
import static java.math.BigInteger.valueOf;

public class Fibonacci {
    public static BigInteger fibonacci(BigInteger n) {
        if(n.intValue()>0){
            BigInteger f_n = BigInteger.ZERO;
            BigInteger f_n_plus_1 = BigInteger.ONE;
            BigInteger two = BigInteger.valueOf(2);
            BigInteger f_n_plus_1_squared, f_2n, f_n_squared, f_2n_plus_1;
            for (int i = Integer.SIZE - Integer.numberOfLeadingZeros(n.intValue()); i >= 0; i--) {
                f_n_squared = f_n.multiply(f_n);
                f_n_plus_1_squared = f_n_plus_1.multiply(f_n_plus_1);
                f_2n = f_n.multiply(f_n_plus_1).multiply(two).subtract(f_n_squared);
                f_2n_plus_1 = f_n_squared.add(f_n_plus_1_squared);
                if ((n.intValue() >> i & 1) == 1) {
                    f_n = f_2n_plus_1;
                    f_n_plus_1 = f_2n.add(f_2n_plus_1);
                } else {
                    f_n = f_2n;
                    f_n_plus_1 = f_2n_plus_1;
                }
            }
            return f_n;
        }else if (n.intValue()<0){
            n = valueOf(0).subtract(n);
            BigInteger f_n = BigInteger.ZERO;
            BigInteger f_n_plus_1 = BigInteger.ONE;
            BigInteger two = BigInteger.valueOf(2);
            BigInteger f_n_plus_1_squared, f_2n, f_n_squared, f_2n_plus_1;
            for (int i = Integer.SIZE - Integer.numberOfLeadingZeros(n.intValue()); i >= 0; i--) {
                f_n_squared = f_n.multiply(f_n);
                f_n_plus_1_squared = f_n_plus_1.multiply(f_n_plus_1);
                f_2n = f_n.multiply(f_n_plus_1).multiply(two).subtract(f_n_squared);
                f_2n_plus_1 = f_n_squared.add(f_n_plus_1_squared);
                if ((n.intValue() >> i & 1) == 1) {
                    f_n = f_2n_plus_1;
                    f_n_plus_1 = f_2n.add(f_2n_plus_1);
                } else {
                    f_n = f_2n;
                    f_n_plus_1 = f_2n_plus_1;
                }
            }
            return f_n.multiply(BigInteger.valueOf(-1).pow((n.intValue()+1)));
        }
        else{
            return valueOf(0);
        }
    }
    public static void main(String[] args) {
        int n = 1000000;
        long start = System.nanoTime();
        System.out.println(fibonacci(valueOf(2000000)).toString());
        long finish = System.nanoTime();
        System.out.println((finish - start) / 1000000 + " ms");
    }
}