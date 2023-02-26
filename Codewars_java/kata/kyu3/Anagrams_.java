package kata.kyu3;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

public class Anagrams_ {
    public static void main(String[] args){

        Anagrams_ d = new Anagrams_();
        System.out.println("Desired word: ");
        Scanner sc = new Scanner(System.in);
        String word =sc.nextLine();
        System.out.println(word + " is the " + d.listPosition(word) + "th position of its letter combination in alphabetical order.");

    }

    public BigInteger listPosition(String word){

        BigInteger total = BigInteger.ZERO;
        char[] word_ca = word.toCharArray();
        int num=0;

        for(int i=0; i<word_ca.length;i++){
            for (int j=i+1; j<word_ca.length;j++){
                if(word_ca[i]>word_ca[j]){
                    num++;
                }
            }
            char[] sword_ca = Arrays.copyOfRange(word_ca, i, word_ca.length);
            Arrays.sort(sword_ca);
            String sword_s = new String(sword_ca);
            int[] uletters = duplicates(sword_s);
            total = total.add(BigInteger.valueOf((long)num).multiply(countDistinctPermutations(sword_ca.length-1,uletters)));
            num=0;
        }
        return total.add(BigInteger.ONE);
    }

    public static String sortWord(String word){

        char[] sorted_ca = word.toCharArray();

        Arrays.sort(sorted_ca);

        return new String(sorted_ca);
    }

    public static String cleanword(String word){

        char[] word_ca = word.toCharArray();
        char[] clean_ca={};

        for (char c:word_ca){
            int ch=1;

            for (char x:clean_ca){
                if (x==c){
                    ch=0;}
            }

            if(ch==1){
                char[] nclean_ca= new char[clean_ca.length+1];
                System.arraycopy(clean_ca,0,nclean_ca,0,clean_ca.length);
                nclean_ca[nclean_ca.length-1]=c;
                clean_ca=nclean_ca;
            }
        }

        return new String(clean_ca);

    }
    public int[] duplicates(String word){
        char[] sword=word.toCharArray();
        char lastLetter = sword[0];
        int[] counts = new int[sword.length];
        int currentCount = 1;
        int numUniqueLetters = 0;
        for(int i = 1; i < sword.length; i++) {
            if(sword[i] == lastLetter) {
                currentCount++;
            }
            if(sword[i] != lastLetter) {
                counts[numUniqueLetters] = currentCount;
                currentCount = 1;
                lastLetter = sword[i];
                numUniqueLetters++;
            }
        }
        counts[numUniqueLetters] = currentCount;
        return Arrays.copyOf(counts,numUniqueLetters+1);
    }

    public BigInteger fact(int x){

        if(x == 0) {return BigInteger.ZERO;}
        BigInteger product = BigInteger.ONE;
        for(int i = x; i > 0; i--) {
            product = product.multiply(BigInteger.valueOf((long)i));
        }
        return product;

    }

    public BigInteger countDistinctPermutations(int x, int[] xn){
        if(x == 0) {return BigInteger.ZERO;}
        BigInteger product = BigInteger.ONE;
        for(int i = x; i > 0; i--) {
            product = product.multiply(BigInteger.valueOf((long)i));
        }
        for(int i =0; i < xn.length; i++) {
            product = product.divide(fact(xn[i]));
        }
        return product;
    }

}
