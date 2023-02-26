package kata.kyu4;
public class Matrix {
    public static void main (String... args){
        int[][] x={{2,5,3}, {1,-2,-1}, {1, 3, 4}};
        System.out.println();
    }
    public static int determinant (int[][] matrix) {
        int temporary[][];
        int result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

        for (int i = 0; i < matrix[0].length; i++) {
            temporary = new int[matrix.length - 1][matrix[0].length - 1];
            for (int j = 1; j < matrix.length; j++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = matrix[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = matrix[j][k];
                    }
                }
            }

            result += matrix[0][i] * Math.pow (-1, (double) i) * determinant (temporary);
        }
        return (result);
    }
    private static int[][] removeCol(int [][] array, int colRemove) {
        int row = array.length;
        int col = array[0].length;
        int [][] newArray = new int[row][col-1]; //new Array will have one column less
        for(int i = 0; i < row; i++) {
            for(int j = 0,currColumn=0; j < col; j++) {
                if(j != colRemove) {
                    newArray[i][currColumn++] = array[i][j];
                }
            }
        }return newArray;
    }
    private static int[][] removeRow(int [][] array, int rowRemove) {
        int row = array.length;
        int col = array[0].length;
        int [][] newArray = new int[row-1][col]; //new Array will have one column less
        for(int i = 0, currRow=0; i < row; i++) {
            if(i != rowRemove) {
                newArray[currRow++] = array[i];
            }
        }return newArray;
    }
}