package kata.kyu2;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class CakeCutter__ {

    final int COLUMNS,ROWS , AREA, AREAREISIN, REISINS;
    int[][] CAKEGRID, GRID;
    List<List<int[]>> solRectStack;
    List<int[]> rais;

    public static void main(String[] args){
        String cake = String.join("\n", Arrays.asList(
                ".o....o.",
                ".o....o.",
                "........",
                "o..oo..o"
        ));
        CakeCutter__ x = new CakeCutter__(cake);
    }

    public CakeCutter__(String cake) {

        REISINS= (int) IntStream.range(0,cake.toCharArray().length).filter(i -> cake.toCharArray()[i]=='o').count();
        ROWS  = cake.split("\n").length;
        COLUMNS = cake.split("\n")[0].length();
        AREA = COLUMNS*ROWS;
        AREAREISIN=AREA/REISINS;
        GRID = new int[ROWS][COLUMNS];
        String[] cakeS = cake.split("\n");
        this.rais=new Stack<>();

        for(int y=0;y<ROWS;y++){

            for(int x=0;x<COLUMNS;x++){

                if(cakeS[y].charAt(x)=='o'){

                    GRID[y][x]=1;
                    System.out.println("y= "+ y+ " x= "+ x);
                }else{
                    GRID[y][x]=0;
                }
            }
        }

        System.out.println(" " + REISINS + " " + COLUMNS + " " + ROWS + " " + AREA + " " + AREAREISIN);
    }

    public List<String> cut() {

        if(REISINS*AREAREISIN!=AREA){
            return null;
        }

        final List<String> out=new ArrayList<>();
        final List<Point> dim= new ArrayList<>();

        for (int y=1;y<=ROWS;y++){

            for (int x=1;x<=COLUMNS;x++){

                if(x*y==AREAREISIN){

                    dim.add( new Point(x,y));
                }
            }
        }
        int remaining=REISINS;
        final List<Rect> solution = new ArrayList<>();
        CAKEGRID=GRID;

        solRectStack = new ArrayList<>();
        List<int[]> temp_ = possibleRectangles(rais.get(0));

        for(int i=0;i<temp_.size();i++){

            List<int[]> tempStk = new ArrayList<>();
            tempStk.add(0,temp_.get(i));
            solRectStack.add(i,tempStk);
        }
        /*
        if(!solRectStack.isEmpty()){
            for (List<int[]> g: solRectStack){

                printGridInt(sumList(g));
                System.out.println("\n");
            }
            //System.out.println("OK");
        }

         */

        //System.out.println("Is rais empty?");
        //System.out.println(rais.empty());

        for (int i=1;i<rais.size();i++){
            List<List<int[]>> raisList;
            //System.out.println("Reis : y= " + r[0] + " x= " + r[1]);
            raisList=checkSol(solRectStack, (possibleRectangles(rais.get(i))), i);
            solRectStack=raisList;
        }
        if(!solRectStack.isEmpty()){
            printGridInt(sumList(solRectStack.get(0)));
        }
        //System.out.println("Pieces are: ");
        return null;
    }
    private int[][] copyGRID(int[][] grid){
        int[][] ret= grid.clone();
        for(int i=0;i<ret.length;i++){
            ret[i]=grid[i].clone();
        }
        return ret;
    }

    private List<List<int[]>> checkSol(List<List<int[]>> solList_temp, List<int[]> vectToCheck, int ty){

        List<List<int[]>> tempSol = new ArrayList<>();
        System.out.println(solRectStack.size());

        for(List<int[]> j: solList_temp){

            for(int[] a:vectToCheck){

                List<int[]> tempL=j;
                tempL.add(a);
                int[][] total_SUM = sumList(tempL);

                if(checkGrid(total_SUM,ty)){
                    printGridInt(total_SUM);
                    System.out.println("\n");
                    tempSol.add(tempL);
                }

                /*
                printGridInt(tat);
                System.out.println("Checking:");
                printGridInt(ta);
                System.out.println("");
                 */
            }
            System.out.println("\n\n\n\n");
        }

        /*
        int[][] grid_to_print = CAKEGRID;
        if(!tempSol.isEmpty()){
            for (int[] g: tempSol.get(0)){

                grid_to_print=sumGrids(grid_to_print,fillGrid(g));
            }
            System.out.println("OK");
            printGridInt(grid_to_print);
        }

         */
        //System.out.println(tempSol.size());
        return tempSol;
    }

    public boolean checkGrid(int[][] gridToCheck, int ch){

        for(int y=0;y<ROWS;y++){

            for(int x=0;x<COLUMNS;x++){

                if(gridToCheck[y][x]>ch){

                    return false;
                }
            }
        }
        return true;
    }

    public int[][] sumList(List<int[]> gridlist){

        int[][] gridSum=new int[ROWS][COLUMNS];

        for(int i=0;i<gridlist.size();i++){

            for(int y=gridlist.get(i)[0];y<=(gridlist.get(i)[0]+gridlist.get(i)[2]);y++){

                for(int x=gridlist.get(i)[1];x<=(gridlist.get(i)[1]+gridlist.get(i)[3]);x++){

                    gridSum[y][x]=gridSum[y][x] + i + 1;
                }
            }
        }
        return gridSum;
    }

    public List<int[]> possibleRectangles(int[] position){

        List<int[]> alist=new ArrayList<int[]>();
        List<int[]> rlist=new ArrayList<int[]>();

        for (int y=1;y<=ROWS;y++){

            for (int x=1;x<=COLUMNS;x++){

                if(x*y==AREAREISIN){

                    alist.add(alist.size(),new int[]{y-1,x-1});
                }
            }
        }
        for(int[] vector: alist){

            for (int y=(position[0]-vector[0]);y<=position[0];y++){

                for (int x=(position[1]-vector[1]);x<=position[1];x++){

                    if((y>=0)&&(x>=0)&&((y+vector[0])<ROWS)&&((x+vector[1])<COLUMNS)){

                        rlist.add(new int[]{y,x,vector[0],vector[1]});
                        //System.out.println("Vector : y0= "+y+" x0= " + x + " vect y= " + (vector[0]-1) + " vector x= " + (vector[1]-1));
                    }
                }
            }
        }
        return rlist;
    }



    public void printGridInt(int[][] grid_i){

        char[][] imapc_ = new char[ROWS][COLUMNS];

        for(int y=0;y<ROWS;y++){

            for (int x=0;x<COLUMNS;x++){

                imapc_[y][x]= (char)(grid_i[y][x]+'0');
            }
            System.out.println("" + new String((imapc_[y])));
        }

    }

    public int[][] fillGrid(int[] vector,int u){//vector [y0,x0,vecy,vecx]

        int[][] grid= new int[ROWS][COLUMNS];

        for(int y=vector[0];y<=(vector[0]+vector[2]);y++){

            for(int x=vector[1];x<=(vector[1]+vector[3]);x++){

                grid[y][x]=u;
            }
        }

        return  grid;
    }


}
