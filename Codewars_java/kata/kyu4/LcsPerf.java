package kata.kyu4;

class LcsPerf {

    public static String lcs(String x, String y) {

        if (x.length() == 0 || y.length() == 0 || x == null || y == null)
            return "";

        String MaxResult = "";
        String result = "";
        for (int i = 0; i < x.length(); i++) {

            String currRes = x.substring(i, i+1);
            int posRes = y.indexOf(currRes);
            if (posRes >= 0) {
                result = currRes+lcs(x.substring(i+1),y.substring(posRes+1));
            }
            if (result.length() > MaxResult.length()) {MaxResult = result;}
        }

        return MaxResult;
    }



}