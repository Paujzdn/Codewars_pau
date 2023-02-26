package kata.kyu4;

public class Lcs {
    public static void main(String[] args){
        System.out.println(lcs("abcdefghijklmnopq","apcdefghijklmnobq"));
    }

    static String lcs(String a, String b) {
        int a1 = a.length(); int b1 = b.length();
        return lcs(a, b, a1, b1);
    }
    static String lcs(String x, String y, int m, int n) {
        int[][] q = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    q[i][j] = 0;
                else if (x.charAt(i - 1) == y.charAt(j - 1))
                    q[i][j] = q[i - 1][j - 1] + 1;
                else
                    q[i][j] = Math.max(q[i - 1][j], q[i][j - 1]);
            }
        }
        int id = q[m][n];
        int d = id;
        char[] ret = new char[id + 1];
        ret[id] = '\0';
        int i = m;
        int j = n;
        while (i > 0 && j > 0) {
            if (x.charAt(i - 1) == y.charAt(j - 1)) {
                ret[id - 1] = x.charAt(i - 1);
                i--;
                j--;
                id--;
            } else if (q[i - 1][j] > q[i][j - 1])
                i--;
            else
                j--;
        }
        return getString(d, ret);
    }
    private static String getString(int temp, char[] lcs) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k <= temp; k++) {
            sb.append(lcs[k]);
        }
        return sb.deleteCharAt(temp).toString();
    }
}