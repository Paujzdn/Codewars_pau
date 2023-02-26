package kata.kyu2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static java.util.Collections.reverse;

public class CakeCutter_ {
    //String cake;
    //String[] map;
    int[][] cake_grid;
    List<List<int[][]>> solutions;
    int[] dim;
    Stack<int[]> rai;
    int yyyy;

    /*
    public static void main(String[] args){
        String cake_ = String.join("\n", Arrays.asList(
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
        //CakeCutter x = new CakeCutter(".o......\n......o.\n....o...\n..o.....");
        CakeCutter_ x = new CakeCutter_(cake_);
    }

     */

    public CakeCutter_(String cake) {
        //this.cake=cake;
        rai = new Stack<>();
        List<List<int[][]>> solutions=new ArrayList<>();
        specs(cake);
        cut();
        // Coding and coding
    }

    public void specs(String grid){
        //System.out.println("Specs starts");

        //{num de raisins, altura (y), amplada (x), area, area assignada a cada raisin
        dim = new int[]{0, 0, 0, 0, 0};

        //busco num de raisins
        for (int i=0; i<grid.length();i++){
            if(grid.charAt(i)=='o'){
                dim[2]++;
            }
        }
        String[] cake_s = grid.split("\n");
        dim[0]=cake_s.length;
        dim[1]=cake_s[0].length();
        cake_grid = new int[dim[0]][dim[1]];
        dim[3]=dim[0]*dim[1];
        dim[4]=dim[3]/dim[2];
        yyyy=0;
        //print_grid_i(str_to_int(grid));
        //System.out.println("\n");
        //print_grid_i(cake_grid);
        get_rai(str_to_int(grid));
        //System.out.println("Num de resins: "+String.valueOf(dim[2])+"\nfilas: " +String.valueOf(dim[0])+ " \ncol: "+String.valueOf(dim[1]));
    }


    public void print_grid_i(int[][] grid_i){
        //System.out.println("Print_ints starts");

        char[][] imapc_ = new char[dim[0]][dim[1]];

        for(int y=0;y<dim[0];y++){
            for (int x=0;x<dim[1];x++){
                imapc_[y][x]= (char)(grid_i[y][x]+'0');
            }
        }

        for(int y=0;y<grid_i.length;y++){
            System.out.println("" + new String((imapc_[y])));
        }
    }

    public int[][] str_to_int(String grid){

        String[] cake_s = grid.split("\n");
        int[][] grid_i = new int[cake_s.length][cake_s[0].length()];

        for(int y=0;y<grid_i.length;y++){

            for(int x=0;x<grid_i[0].length;x++){

                if(cake_s[y].charAt(x)=='o'){
                    grid_i[y][x]=1;

                }else if(cake_s[y].charAt(x)=='.'){
                    grid_i[y][x]=0;
                }
            }
        }

        //print_grid_i(grid_i);
        return grid_i;
    }

    public String int_to_str(int[][] grid_int){

        String cake_s="";

        for(int y=0;y<dim[0];y++){
            char[] row = new char[dim[1]];
            for (int x=0;x<dim[1];x++){
                row[x]= (char)(grid_int[y][x]+'0');
            }
            cake_s+=row.toString()+"\n";
        }

        return cake_s;
    }

    public void get_rai(int[][] grid){

        for (int y=0;y<dim[0];y++){

            for (int x=0;x<dim[1];x++){

                if (grid[y][x]==1){
                    rai.push(new int[]{y,x});
                }
            }
        }
        reverse(rai);
    }

    public void check_sols(List<int[][]> sols_reisin){

        //System.out.println("Check_sol starts");

        //List<String> de un reis, miro les List solucions (primera llista)
        //Per cada llista de solucions, miro si alguna de les sol de reis cuadra amb ella
        //Si cuadra la afegeizo, si cuadra més d'una, duplico i afageixo a solucions
        int s=dim[2]-rai.size();
        for(List<int[][]> sol_group: solutions){

            boolean repeat=false;

            for(int[][] sol_t: sols_reisin){


                if(check_strs(sol_group,sol_t)){
                    //System.out.println("OK");
                    if(!repeat){
                        sol_group.add(sol_t);
                        repeat=true;
                    }
                    else{
                        List<int[][]> temporary_sol = sol_group;
                        temporary_sol.add(sol_t);
                        solutions.add(solutions.size()+1,temporary_sol);
                    }



                }

            }
            /*
            for(int[][] k:sol_group){
                print_grid_i(k);
                System.out.println("\n\n");
            }

             */
        }

        for(List<int[][]> a:solutions){
            int[][] gr = new int[dim[0]][dim[1]];
            for(int[][] k:a){
                for (int y=0; y<dim[0];y++){
                    for (int x=0; x< dim[1]; x++){
                        gr[y][x]=gr[y][x] + k[y][x];
                    }
                }
            }
            print_grid_i(gr);
            System.out.println("\n\n");
        }
        for(int i=0;i<solutions.size();i++){
            if(solutions.get(i).size()!=yyyy){
                solutions.remove(i);
            }
        }
        System.out.println(yyyy);
    }

    public boolean check_strs(List<int[][]> str_list, int[][] sol_to_check){
        //System.out.println("Check starts");

        int[][] t = new int[dim[0]][dim[1]];

        for (int[][] a:str_list){

            for (int y=0; y<dim[0];y++){

                for (int x=0; x< dim[1]; x++){

                    t[y][x]=t[y][x]+a[y][x];
                }
            }
        }
        //print_grid_i(t);
        //System.out.println("\n\n\n");
        int[][] temp_grid = sol_to_check;

        for(int y=0;y<dim[0];y++){

            for (int x=0;x<dim[1];x++){

                t[y][x]=t[y][x] + temp_grid[y][x];
            }
        }



        for (int y=0; y<dim[0];y++){
            for (int x=0; x< dim[1]; x++){
                if(t[y][x]>1){
                    return false;
                }
            }
        }

        return true;
    }

    //SEGUIR NETEJANT, USAR VAR GLOBALS, SEGUIR BUSCANT COM MILLORAR CODI, SEGUIR POSANT IMPRIMIRS
    public List<String> cut() {
        //System.out.println("Cut starts");

        //[baixa][dreta]======[y][x]

        //print_grid_i(cake_grid);
        //System.out.println(String.valueOf(cake_grid[1][2]));
        //System.out.println("\n");

        solutions = new ArrayList<>();
        List<int[][]> beta = sol_res(rai.pop());
        int y=0;

        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        for(int[][] alfa: beta){
            List<int[][]> tempL = new ArrayList<int[][]>();
            tempL.add(alfa);
            solutions.add(y,tempL);
            //print_grid_i(tempL.get(0));
            y++;
        }
        yyyy++;
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        while (!rai.empty()){
            //System.out.println("Anem a fer raisin: y=" + rai.peek()[0]+ " x= " + rai.peek()[1]);
            yyyy++;
            List<int[][]> sols_rai=sol_res(rai.pop());
            check_sols(sols_rai);

            //agafo cada un de les sols del nou rai, i faig check amb cada un
        }
        //System.out.println(dim[2]);
        List<List<int[][]>> solutions_= solutions.stream().filter(c -> c.size() == dim[2]).collect(Collectors.toList());


        System.out.println("Num de sol List<int[][]>" + solutions_.size());
        //System.out.println("Tamany de cada sol" + solutions_.get(0).size());


        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        for(int[][] a:solutions_.get(0)){
            //print_grid_i(a);
            //System.out.println("\n\n");

        }

        return null;
    }

    public List<int[][]> sol_res(int[] posr){//return string list, torna llista de sol per una resina, busca totes les combinacions de areas possibles
        //System.out.println("Sol_res starts");

        List<int[]> alist=new ArrayList<int[]>();//llista de areas posibles [y,x] del seu tamany
        List<int[][]> slist=new ArrayList<int[][]>();
        int p=0;

        for(int y=1;y<=dim[0];y++){//els +1 és per contar el pos de la resina, a el mxr i tal, ho treiem perque son maxims salts. i length de array sempre es de 1 a n, no de 0 a n

            for (int x=1;x<=dim[1];x++){

                if(x*y==dim[4]){

                    alist.add(p,new int[]{y, x});
                    //System.out.println("OK");
                    p++;
                    //System.out.println("Poss area : y " + String.valueOf(y) +  " x " + String.valueOf(x));
                }
            }
        }

        int s=0;

        for(int[] a:alist){

            List<int[]> ra = pos_r(a[0],a[1],posr);//
            //System.out.println("Pos_r de primera sol: \n");
            for (int[] f: ra){
                //System.out.println("RECTANGLE: y=" + a[0] + " x=" + a[1]);
                //System.out.println("ORIGENS: y=" + f[0] + " x=" + f[1]);
            }


            for (int[] b: ra){

                int[][] h = omplir_r(new int[]{a[0]-1, a[1]-1},b);
                slist.add(s,h);
                //System.out.println("Vectors== x+: " + (b[1]+a[1]-posr[1]-1) + ", x-: " + (posr[1]-b[1]) + ", y+: " + (b[0]+a[0]-posr[0]-1) + ", y-: " + (posr[0]-b[0]));
                s++;
            }
            //slist.add();
            //String a = omplir_r(new int[])
            //per cada rectangle, omplir el string pintat per cada iteració del rectangle desplaçat amunt i abaix
        }
        return slist;
    }

    public List<int[]> pos_r(int my, int mx, int[] pos){//torna llista dels rectangles possibles, amb argument com coordenada top left (y,x)
        //System.out.println("pos_r starts");

        List<int[]> alist_=new ArrayList<int[]>();
        //int gx0=0, gy0=0, gx_=grid[0].length, gy_=grid.length;
        //System.out.println("Grid: "+gy_+"x"+gx_);
        //System.out.println("Grid: "+dim[0]+"x"+dim[1]);
        int c=0;
        for(int y=pos[0]-my+1;y<=pos[0];y++){

            for(int x=pos[1]-mx+1;x<=pos[1];x++){

                if((y>=0)&&((y+my-1)<dim[0])){

                    if((x>=0)&&((x+mx-1)<dim[1])){
                        /*
                        System.out.println("j : " +  x);
                        System.out.println("i : " +  y);
                        System.out.println("my : " +  my);
                        System.out.println("mx : " +  mx);
                        System.out.println("posy : " +  pos[0]);
                        System.out.println("posx : " +  pos[1]);

                         */
                        alist_.add(c,new int[]{y,x});//down, right

                        //System.out.println("Top left: " + y + " , " + x);
                        c++;
                    }
                }
            }
        }
        return alist_;//tornant posicions
    }

    public int[][] omplir_r( int[] vec, int[] st){// omple int[][] amb especificacions
        //System.out.println("Omplir starts");
        //VEC down, up, left, right
        //mx i my son salts
        //pos [y,x]
        //mx [+,-]

        int[][] grid_= new int[dim[0]][dim[1]];
        //System.out.println("PROVA EN 0's");
        //print_grid_i(grid_);
        //System.out.println("down: " +  vec[0] + " right: " + vec[1]);
        for(int y=st[0];y<=(st[0]+vec[0]);y++){
            for(int x=st[1];x<=(st[1]+vec[1]);x++){
                grid_[y][x]=1;
            }
        }
        //print_grid_i(grid_);
        return  grid_;
    }

}