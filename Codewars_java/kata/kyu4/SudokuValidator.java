package kata.kyu4;

import java.util.Arrays;

public class SudokuValidator {
    public static void main (String[] args) {
        int[][] fin = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}};
        System.out.println(check(fin));

    }
    public static boolean check(int[][] sudoku) {
        int[][] validacio = new int[27][9];
        int x=0;int y=0;
        boolean valid=true;
        for(int i=0;i< validacio.length;i++){
            if(i<9){
                validacio[i]=sudoku[i];
            }
            else if((i>=9)&&(i<18)){
                for(int j=0;j<9;j++){
                    validacio[i][j]=sudoku[j][i-9];
                }
            }
            else if((i>=18)&&(i<27)){
                x=i-(18+(i%3));y=(i%3)*3;int z=0;
                for(int xx=0;xx<3;xx++){
                    for(int yy=0;yy<3;yy++){
                        validacio[i][z]=sudoku[x+xx][y+yy];
                        z++;
                    }
                }
            }
        }
        for(int i =0; i< validacio.length;i++){
            Arrays.sort(validacio[i]);
        }
        for(int k=0;k<27;k++){
            for(int h=0;h<9;h++){
                if(validacio[k][h]==0){
                    valid = false;
                }
                if(validacio[k][h]!=h+1){
                    valid=false;
                }
            }
        }return valid;
    }
}