package kata.kyu4;

import static java.lang.Math.*;

public class Lcs1 {
    public static void main(String[] args){
        System.out.println(lcs("abcdefghijklmnopq","apcdefghijklmnobq"));
    }

    public static String lcs(String a, String b) {
        String subsequencia = "";
        if(b.length()>a.length()){
            String c = a;
            a=b;
            b=c;
        }if(a.length()==1&&a.charAt(0)==b.charAt(0))return String.valueOf(a.charAt(0));
        else if(a.length()==0)return subsequencia;
        int m=a.length();
        int n=b.length();
        int[][] quadre = new int[m+1][n+1];
        int x=0,y=0,c=0;
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                if(a.charAt(i-1)==b.charAt(j-1)){
                    quadre[i][j]=1+ quadre[i-1][j-1];
                    if(quadre[i][j]>c){
                        x=i;
                        y=j;
                        c=quadre[i][j];
                    }
                }
                else {
                    quadre[i][j]=Integer.max(quadre[i-1][j],quadre[i][j-1]);
                }
            }
        }

        int[] horitz = quadre[x];
        int count=0;
        int[][] s = new int[m][2];
        boolean w=true;


        while (count<c) {
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (quadre[i][j] == count&&w){
                        s[count][0] = i;
                        s[count][1] = j;
                        w=false;
                    }
                }
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if ((quadre[i][j] == count) && (max(((i-1)-(j-1)),((j-1)-(i-1)))<max((s[count][0] - s[count][1]),s[count][1] - s[count][0]))){
                        s[count][0] = i-1;
                        s[count][1] = j-1;
                    }
                }
                System.out.println(String.valueOf(s[count][0]) +String.valueOf(s[count][1]));
            }
            subsequencia=subsequencia+a.charAt((s[count][0]));
            count++;
            w=true;
        }

        System.out.println(subsequencia);
        String t="   ";
        String e= " " +a;
        for(int i=0;i<b.length();i++){
            t=t+b.charAt(i)+ " ";
        }
        System.out.println(t);
        for(int i=0;i<quadre.length;i++){
            String p="";
            for(int j=0;j<quadre[0].length;j++){
                if(quadre[i][j]>9){
                    p=p+quadre[i][j];
                }else{
                    p=p+" " + quadre[i][j];
                }
            }
            System.out.println(e.charAt(i) + p);
        }
        System.out.println("a= "+a.length());
        System.out.println("b= "+b.length());
        System.out.println("x="+x+" y="+y);
        return subsequencia;
    }
}
