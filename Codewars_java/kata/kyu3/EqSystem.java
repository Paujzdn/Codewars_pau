package kata.kyu3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EqSystem {

    HashMap<Character,List<binoM>> example;

    public static String simplify(String[] examples, String formula) {
        // Your code here!
        return null;
    }

    public EqSystem() {
        example=new HashMap<>();
    }

    public static void main(String[] args){

        EqSystem x =new EqSystem();
        x.fillformulas(new String[] {"a + a = b", "b - d = c", "a + b = d"});//+1a  +3g=k -70a = g

    }

    private String sumBinom(String sumA, String sumB){

        String A=sumA.substring(0,sumA.length()-1);
        String B=sumB.substring(0,sumB.length()-1);

        int iA = Integer.parseInt(sumA);
        int iB = Integer.parseInt(sumB);
        int iT = iB + iA;

        //String tot = String.valueOf(iT) + sumA.charAt(sumA.length()-1);
        return String.valueOf(iT);
    }

    private String sumBinList(List<String> exList){

        String res = new String();

        for(String u:exList){

            res = sumBinom(res,u);

        }

        return res;
    }

    public class binoM {

        int coef;
        char var;

        public binoM(){
            this.coef=0;
            this.var=' ';
        }

        public binoM(int coef, char var) {
            this.coef = coef;
            this.var = var;
        }


        public char getVar(){
            return var;
        }

        public int getCoef(){
            return coef;
        }

        public void setVar(char var){
            this.var=var;
        }

        public void setCoef(int coef){
            this.coef=coef;
        }

    }
    private List<binoM> subBIN(List<binoM> subbed, int subedcoef){

        List<binoM> provX = subbed;

        for(binoM x:provX){

            x.setCoef(x.getCoef()*subedcoef);
        }

        return provX;
    }

    private List<binoM> cleanformulas(List<binoM> ex){

        Stack<Character> used = new Stack<>();
        List<binoM> provEq=new ArrayList<>();
        int index=0;

        for (int i=0;i<ex.size();i++){

            if(!used.contains(ex.get(i).getVar())){

                used.push(ex.get(i).getVar());
                provEq.add(ex.get(i));
                index=i;

                for(int j=i+1;j<ex.size();j++){

                    if (ex.get(j).getVar()==used.peek()){

                        provEq.get(index).setCoef(provEq.get(index).getCoef() + ex.get(j).getCoef());
                        ex.remove(j);
                        j--;
                    }
                }
            }
        }
        return provEq;


    }

    private Character getsmallest(HashMap<Character,List<binoM>> formList){

        for (int i=0;i<formList.size();i++){

            for(char j:formList.keySet()){

                if(formList.get(j).size()==i){

                    return j;
                }
            }
        }
        return null;
    }

    private void fillformulas(String[] f){

        HashMap<Character,List<binoM>> example_t = new HashMap<Character, List<binoM>>();
        example = new HashMap<Character, List<binoM>>();
        String[][] exsystem = new String[f.length][2];

        for(int i=0;i<f.length;i++){

            exsystem[i] = f[i].replaceAll(" ","").split("=");
        }

        for(String[] o:exsystem){

            String var_P = o[0];
            List<binoM> var_F = new ArrayList<>();
            int y=0;

            for(int i=0;i<var_P.length();i++){

                binoM var__ = new binoM();

                if(var_P.charAt(i)>='a'&&var_P.charAt(i)<='z'){

                    if(y==0){

                        switch (i){

                            case 0:

                                var__=new binoM(1,var_P.charAt(0));
                                y=1;
                                //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                                var_F.add(var__);
                                break;

                            case 1:

                                if(var_P.charAt(0)>='0'||var_P.charAt(0)<='9'){

                                    var__ = new binoM(var_P.charAt(0), var_P.charAt(1));
                                    y=2;
                                    //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                                    var_F.add(var__);
                                }else {

                                    var__ = new binoM(Integer.parseInt(var_P.charAt(0) + "1"),var_P.charAt(1));
                                    y=i+1;
                                    //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                                    var_F.add(var__);
                                }
                                break;

                            default:

                                if(var_P.charAt(0)>='0'&&var_P.charAt(0)<='9'){

                                    var__ = new binoM(Integer.parseInt(var_P.substring(y,i)),var_P.charAt(i));
                                    y=i+1;
                                    //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                                    var_F.add(var__);
                                }else{

                                    var__ = new binoM(Integer.parseInt(var_P.substring(y,i)),var_P.charAt(i));
                                    y=i+1;
                                    //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                                    var_F.add(var__);
                                }

                                break;
                        }

                    }
                    else if(!(var_P.charAt(i-1)>='0'&&var_P.charAt(i-1)<='9')){

                        var__ = new binoM(Integer.parseInt(var_P.charAt(i-1) + "1"),var_P.charAt(i));
                        y=i+1;
                        //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                        var_F.add(var__);
                    }else {

                        var__ = new binoM(Integer.parseInt(var_P.substring(y,i)),var_P.charAt(i));
                        y=i+1;
                        //System.out.println(String.valueOf(var__.getCoef()) + var__.getVar());
                        var_F.add(var__);
                    }
                }
            }
            example.put(o[1].charAt(0),var_F);

        }




        for(char key_s:example.keySet()){

            List<binoM> provEq = cleanformulas(example.get(key_s));
            example_t.put(key_s,provEq);
        }
        example=example_t;



        char first_eq = getsmallest(example_t);

        if(example_t.get(first_eq).size()==1){

            while (example_t.values().size()>1){

                for(char i:example_t.keySet()){

                    if(first_eq!=i){

                        for (binoM h:example_t.get(i)){

                            if(h.getVar()==first_eq){

                                List<binoM> alfa = subBIN(example_t.get(i),example_t.get(first_eq).get(0).getCoef());
                                example_t.get(i).remove(h);
                                List<binoM> merged= Stream.concat(example_t.get(i).stream(),alfa.stream()).collect(Collectors.toList());
                                merged=cleanformulas(merged);
                                example_t.put(i,merged);
                            }
                        }
                    }
                }
            }
        }
        example=example_t;

        for(Character key:example.keySet()){

            System.out.println(key + " = ");

            for (binoM e:example.get(key)){

                System.out.println(String.valueOf(e.getCoef()) +  e.getVar());

            }
        }
    }
}