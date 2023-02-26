package kata.kyu3;

import java.util.Arrays;

public class Spiralizor {
    public static void main(String[] args) {
        // {1,2,3,6,9,8,7,4,5}
        int[][] r = spiralize(17);
        for (int i = 0; i < r.length; i++) {
            String p = "";
            for (int j = 0; j < r[0].length; j++) {
                if(r[i][j]==-1){
                    p += " 0";
                }else{
                    p += " 1";
                }
            }
            System.out.println(p);
        }
    }

    public static final int WALL = -1;

    public static int[][] spiralize(int size) {

        if (size < 5) {
            return null;
        }
        int[][] pla = new int[size][size];
        int n = size * size;
        //int n = array.length;
        //int[] result = new int[n * n];
        int x = 0, y = 0;
        int xStep = 1, yStep = 0;
        int i=0;
        while (i<n) {
            i++;
            //result[i] = array[y][x];
            //array[y][x] = WALL;
            if(pla[y][x]!=WALL) {
                System.out.println(i);
                if ((x > 0) && (y > 0) && (x < size - 1) && (y < size - 1)) {
                    if ((xStep != 0 && yStep == 0) && (pla[y][x + xStep] != WALL)) {
                        pla[y][x] = 1;
                        pla[y - 1][x] = WALL;
                        pla[y + 1][x] = WALL;
                    } else if ((yStep != 0 && xStep == 0) && (pla[y + yStep][x] != WALL)) {
                        pla[y][x] = 1;
                        pla[y][x - 1] = WALL;
                        pla[y][x + 1] = WALL;
                    } else if ((xStep != 0 && yStep == 0) && (pla[y][x + xStep] == WALL)) {
                        pla[y][x] = 1;
                        yStep = xStep;
                        xStep = 0;
                        if (yStep > 0) {
                            pla[y - 1][x] = WALL;
                        } else if (yStep < 0) {
                            pla[y + 1][x] = WALL;
                        }
                    } else if ((yStep != 0 && xStep == 0) && (pla[y + yStep][x] == WALL)) {
                        pla[y][x] = 1;
                        xStep = -yStep;
                        yStep = 0;
                        if (xStep > 0) {
                            pla[y][x - 1] = WALL;
                        } else if (xStep < 0) {
                            pla[y][x + 1] = WALL;
                        }
                    }
                } else {
                    if (y == 0 && x < size - 1) {
                        pla[y][x] = 1;
                        pla[y + 1][x] = WALL;
                    } else if ((y > 0 && y < size - 1) && x == size - 1) {
                        pla[y][x] = 1;
                        pla[y][x - 1] = WALL;
                    } else if (y == size - 1 && (x > 0 && x < size - 1)) {
                        pla[y][x] = 1;
                        pla[y - 1][x] = WALL;
                    } else if ((y > 0 && y < size - 1) && x == 0) {
                        if (pla[y - 1][x] != WALL) {
                            pla[y][x] = 1;
                            pla[y][x + 1] = WALL;
                        } else {
                            pla[y][x] = 1;
                            yStep = 0;
                            xStep = 1;
                        }
                    } else if ((y == 0) && (x == size - 1)) {
                        pla[y][x] = 1;
                        xStep = 0;
                        yStep = 1;
                    } else if ((y == size - 1) && (x == size - 1)) {
                        pla[y][x] = 1;
                        xStep = -1;
                        yStep = 0;
                    } else if ((y == size - 1) && (x == 0)) {
                        pla[y][x] = 1;
                        xStep = 0;
                        yStep = -1;
                    }
                }
                x += xStep;
                y += yStep;
            }
        }return pla;
    }
}

