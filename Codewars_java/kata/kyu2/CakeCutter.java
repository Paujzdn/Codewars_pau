package kata.kyu2;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CakeCutter {
    //hey
    final int COLUMNS,ROWS , AREA, REISINS;
    int[][] CAKEGRID, GRID;
    List<List<int[]>> solRectStack;
    List<int[]> rais;

    /*

    public static void main(String[] args){
        String cake = String.join("\n", Arrays.asList(
                "................",
                ".....o..........",
                "................",
                "...............o",
                "................",
                "................",
                "................",
                ".....o..o.....o.",
                "................",
                "................",
                "...o............",
                "................",
                "................",
                "...............o",
                "................",
                ".o.............."
        ));
        CakeCutter_gh x = new CakeCutter_gh(cake);
    }

     */

    public CakeCutter(String cake) {

        REISINS= (int) IntStream.range(0,cake.toCharArray().length).filter(i -> cake.toCharArray()[i]=='o').count();
        ROWS  = cake.split("\n").length;
        COLUMNS = cake.split("\n")[0].length();
        AREA = COLUMNS*ROWS;
        GRID = new int[ROWS][COLUMNS];
        String[] cakeS = cake.split("\n");

        for (int i=0;i<ROWS;i++){

            for (int j=0;j<COLUMNS;j++){

                if(cakeS[i].charAt(j)=='.'){
                    GRID[i][j]=0;
                }else {
                    GRID[i][j]=1;
                }
            }
        }
        /*
        List<String> a = cut();
        for(String o:a){
            System.out.println(o);
            System.out.println("\n");
        }

         */

        // Coding and coding
    }

    public List<String> cut() {

        final int AREAREISIN=(AREA/REISINS);

        if(AREAREISIN!=(AREA/REISINS)){
            return null;
        }

        List<String> out = new ArrayList<>();
        List<Point> possarea = possareas(AREAREISIN);
        List<Rect> sols = new ArrayList<>();
        CAKEGRID=gridCopy(GRID);
        int ramain = REISINS;
        recursiveSolution(ramain,possarea,out,sols);

        // Coding and coding... again!
        return out;
    }

    private List<Point> possareas(int areaP){

        List<Point> areas = new ArrayList<>();

        for(int i=1;i<=ROWS;i++){

            for(int j=1;j<=COLUMNS;j++){

                if(j*i==areaP){

                    areas.add(new Point(j,i));
                }
            }
        }
        return areas;
    }

    private class Rect {
        public Point position;
        public Point size;

        public Rect() {
            position = new Point(0,0);
            size = new Point(0,0);
        }

        public Rect(Point pos) {
            position = new Point(pos);
            size = new Point(0,0);
        }

        public Rect(Point pos,Point psize) {
            position = new Point(pos);
            size = new Point(psize);
        }

    }

    private void recursiveSolution(final int remainingR, final List<Point> possibleA, final List<String> output, List <Rect> solution){

        if(remainingR<=0){
            if (gFilled()==AREA){
                for (Rect t:solution){
                    output.add(cutArea(t.position, t.size));
                }
            }
            return;
        }

        Point currentcoordenate=topLeftAvaiblePoint();

        if((currentcoordenate==null)||(output.size()>0)){
            return;
        }

        int[][] gridClone = gridCopy(GRID);

        for (Point rect:possibleA){
            if (containsOneRaisin(currentcoordenate,rect)){

                fillArea(currentcoordenate,rect);
                solution.add(new Rect(currentcoordenate,rect));
                //println(stringify(grid));
                recursiveSolution(remainingR-1,possibleA,output,solution);
                if (output.size()>0){
                    return;
                }
                solution.remove(solution.size()-1);
                GRID = gridCopy(gridClone);

            }
        }

    }

    private int [][] gridCopy (int [][]grid) {
        int [][] ret = grid.clone();
        for (int i = 0;i<ret.length;++i){
            ret [i] = grid[i].clone();
        }
        return ret;
    }

    private int gFilled() {
        int count = 0;
        for (int i = 0;i<ROWS;++i){
            for (int j=0;j<COLUMNS;++j){
                if (GRID[i][j] == 8){
                    count++;
                }
            }
        }
        return count;
    }

    private String cutArea (Point coords,Point size) {
        StringBuilder sb = new StringBuilder(size.x*size.y + size.y);
        for (int i = coords.y,di=coords.y+size.y;i<di&&i<ROWS;++i){
            for (int j = coords.x,dj=coords.x+size.x;j<dj &&j<COLUMNS;++j){
                if (CAKEGRID[i][j] == 1){
                    sb.append("o");
                }else{
                    sb.append(".");
                }
            }
            sb.append(i<ROWS-1 && i<coords.y+size.y-1 ? "\n" : "");
        }
        return sb.toString();
    }

    private void fillArea (Point coords,Point size) {
        for (int i = coords.y,di=coords.y+size.y;i<di&&i<ROWS;++i){
            for (int j = coords.x,dj=coords.x+size.x;j<dj &&j<COLUMNS;++j){
                GRID[i][j] = 8;
            }
        }
    }

    private Point topLeftAvaiblePoint () {
        for (int i = 0;i<ROWS;++i){
            for (int j=0;j<COLUMNS;++j){

                if (GRID[i][j]!=8){
                    return new Point(j,i);
                }
            }
        }
        return null;
    }

    private boolean containsOneRaisin(Point coords,Point size) {
        int count = 0;
        for (int i = coords.y,di=coords.y+size.y;i<di;++i){
            if (i>=ROWS){
                return false;
            }
            for (int j = coords.x,dj=coords.x+size.x;j<dj ;++j){
                if (j>=COLUMNS){
                    return false;
                }
                if (GRID[i][j] == 1){
                    count++;
                }
            }
        }
        return count == 1;
    }


}