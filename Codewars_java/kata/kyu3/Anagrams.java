package kata.kyu3;

import java.math.BigInteger;
import java.util.Arrays;

public class Anagrams {
    /*
    public static void main(String[] args){
        String word ="Question";
        System.out.println(listPosition(word));
    }

     */
    static final int MAX_CHAR = 26;
    public static void main(String[] args){
        Anagrams d = new Anagrams();
        String word ="QUESTION";
        long x = d.listPosition(word);
        //System.out.println(preparechar(word));
        System.out.println(x);

    }
    public static String sortWord(String word){
        char[] wordcharsorted = word.toCharArray();
        Arrays.sort(wordcharsorted);
        return new String(wordcharsorted);
    }
    public static String cleanword(String word){
        char[] wordcharsorted = word.toCharArray();
        char[] wordsortedcleaned={};

        for (char c:wordcharsorted){
            int ch=1;
            for (char x:wordsortedcleaned){
                if (x==c){
                    ch=0;
                }
            }
            if(ch==1){
                char[] newordsortedcleaned= new char[wordsortedcleaned.length+1];
                for (int i=0; i<wordsortedcleaned.length;i++){
                    newordsortedcleaned[i]=wordsortedcleaned[i];
                }
                newordsortedcleaned[newordsortedcleaned.length-1]=c;
                wordsortedcleaned = new char[newordsortedcleaned.length];
                for(int j=0;j<newordsortedcleaned.length;j++){
                    wordsortedcleaned[j]=newordsortedcleaned[j];
                }
            }
        }

        //
        System.out.println(wordcharsorted);
        System.out.println(wordsortedcleaned);
        return new String(wordsortedcleaned);
    }
    public long listPosition(String word) {
        //String a="";
        //BigInteger wpos = new BigInteger(a) ;
        long wpos=0;
        String sortedword = sortWord(word);
        String cleanword = cleanword(sortedword);
        String extra="";
        char[] lettersschar = sortedword.toCharArray();
        char[] wordchar = word.toCharArray();

        for(int i=0; i<sortedword.length();i++){
            for (int j=i+1; j<sortedword.length();j++){
                if(lettersschar[i]==lettersschar[j]){
                    extra=extra+sortedword.charAt(i);
                }
            }
        }

        System.out.println("Full word:"+word+"\n"+"Full sorted word: "+ sortedword+"\n"+"Full sorted cleaned word: "+cleanword+"\n"+"Extra letters: "+ extra);
        int lpos=wordchar.length;
        for (char c:wordchar){
            System.out.println("Sorted word: " + sortedword);
            System.out.println(extra);
            int pos=cleanword.indexOf(c);
            System.out.println("Pos en sorted: " + pos);
            //wpos=wpos.add(factofletter(pos));
            /*
            if(extra.contains(String.valueOf(c))){
                StringBuilder sbn = new StringBuilder(extra);
                sbn.deleteCharAt(extra.indexOf(c));
                extra=sbn.toString();
            }else{
                StringBuilder sb = new StringBuilder(cleanword);
                sb.deleteCharAt(pos);
                cleanword=sb.toString();
            }

             */
            StringBuilder sb = new StringBuilder(sortedword);
            sb.deleteCharAt(pos);
            sortedword=sb.toString();
            //wpos=wpos+(factofletter(lpos-1)*(pos));
            wpos=wpos+(countDistinctPermutations(word.substring(word.length()-(lpos-1))));
            System.out.println("num cmb: " + countDistinctPermutations(word.substring(word.length()-(lpos-1))));
            System.out.println("Position: " + wpos);
            System.out.println("Pos de lletra: "+ lpos);
            lpos--;

        }
        return wpos+1;
    }
    public long factofletter(int position){
        long fact=1;
        for(int i=2;i<=position;i++){
            fact=fact*i;
        }
        //return BigInteger.valueOf(fact);
        return fact;
    }
    public long countDistinctPermutations(String str)
    {
        int length = str.length();

        int[] freq = new int[MAX_CHAR];

        // finding frequency of all the lower case
        // alphabet and storing them in array of
        // integer
        for (int i = 0; i < length; i++)
            if (str.charAt(i) >= 'A')
                freq[str.charAt(i) - 'A']++;

        // finding factorial of number of appearances
        // and multiplying them since they are
        // repeating alphabets
        long fact = 1;
        for (int i = 0; i < MAX_CHAR; i++)
            fact = fact * factofletter(freq[i]);

        // finding factorial of size of string and
        // dividing it by factorial found after
        // multiplying
        return factofletter(length) / fact;
    }
}