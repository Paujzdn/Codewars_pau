package kata.kyu3;

public class BattleField {
    public static void main(String[] args){
        int[][] f={{1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        boolean r = fieldValidator(f);
        System.out.println(r);
    }
    public static boolean aigua(int[][] p, int i, int j, int x, int y){
        if((p[i+1][j+1]==1)||(p[i-1][j-1]==1)||(p[i+1][j-1]==1)||(p[i-1][j+1]==1)){
            return false;
        }if(((x==1)&&(y==0))&&((p[i][j+1]==1)||(p[i][j-1]==1)||(p[i-1][j]==1))){
            return false;
        }if(((x==0)&&(y==1))&&((p[i+1][j]==1)||(p[i][j-1]==1)||(p[i-1][j]==1))){
            return false;
        }if(((x==0)&&(y==0))&&((p[i][j+1]!=0)||(p[i+1][j]!=0)||(p[i-1][j]!=0)||(p[i][j-1]!=0))){
            return false;
        }
        return true;

    }
    public static boolean fieldValidator(int[][] field) {
        int btsh=0;int cruis=0; int destr=0; int sub=0;
        boolean valid=true;
        int[][] pla = new int[field.length+2][field[0].length+2];
        for(int i=1;i<pla.length-1;i++){
            for(int j=1;j<pla[0].length-1;j++){
                pla[i][j]=field[i-1][j-1];
            }
        }
        System.out.println(aigua(pla,1,6,0,1));
        for(int i=0;i<pla.length;i++){
            for(int j=0;j<pla[0].length;j++){
                if(pla[i][j]==1){
                    int count=0; int x,y;
                    pla[i][j]=2;
                    if(pla[i+1][j]==1){
                        if(!aigua(pla,i,j,1,0)) valid=false;
                        x=1;y=0;count++;
                        while(pla[i+x][j]==1){
                            pla[i+x][j]=2;
                            if (!aigua(pla,i+x,j,1,0) ) valid=false;
                            count++;
                            x++;
                        }
                    }
                    else if(pla[i][j+1]==1){
                        if(!aigua(pla,i,j,0,1)) valid=false;
                        x=0;y=1;count++;
                        while(pla[i][j+y]==1){
                            pla[i][j+y]=2;
                            if(!aigua(pla,i,j+y,0,1)) valid=false;
                            count++;
                            y++;
                        }
                    }
                    else if(aigua(pla,i,j,0,0)){
                        count++;
                    }
                    //System.out.println(valid);
                    switch (count){
                        case 1:
                            sub++;
                            break;
                        case 2:
                            destr++;
                            break;
                        case 3:
                            cruis++;
                            break;
                        case 4:
                            btsh++;
                            break;
                        default:
                            valid=false;
                            break;
                    }
                }
            }
        }
        System.out.println(String.valueOf(cruis)+String.valueOf(btsh)+String.valueOf(destr)+String.valueOf(sub));
        if(!((cruis==2)&&(destr==3)&&(btsh==1)&&(sub==4))){
            valid=false;
        }
        for(int i=0;i<pla.length;i++){
            String p="";
            for(int j=0;j<pla[0].length;j++){
                p+=pla[i][j];
            }
            System.out.println(p);
        }
        return valid;
    }
}