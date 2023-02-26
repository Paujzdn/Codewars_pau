package kata.kyu3;

import java.util.*;

public class EqSystem_ {

    HashMap<String,List<String[]>> example;

    public static String simplify(String[] examples, String formula) {
        // Your code here!
        return null;
    }

    public EqSystem_() {
        example=new HashMap<>();
    }

    public static void main(String[] args){

        EqSystem_ x =new EqSystem_();
        x.fillformulas(new String[] {"a + a + 1f -12r +2s= b", "b - d = c", "a + b = d"});//+1a  +3g=k -70a = g

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

    private HashMap<String,List<String[]>> cleanformulas(HashMap<String,List<String[]>> ex){

        HashMap<String,List<String[]>> cleanex = new HashMap<>();

        for(String key_s:ex.keySet()){


            HashMap<String,List<String>> list_eq= new HashMap<>();
            Stack<String> used = new Stack<>();

            for (int i=0;i<ex.get(key_s).size();i++){

                if(!used.contains(ex.get(key_s).get(i)[1])){

                    used.push(ex.get(key_s).get(i)[1]);
                    List<String> temp = new ArrayList<>();
                    list_eq.put(used.peek(),temp);

                    for(int j=i+1;j<ex.get(key_s).size();j++){

                        if (ex.get(key_s).get(j)[1]==used.peek()){

                            list_eq.get(used.peek()).add(ex.get(key_s).get(j)[0]);
                        }
                    }
                }
            }
            List<String[]> list_vars = new ArrayList<>();
            for (String y:list_eq.keySet()){

                list_vars.add(new String[]{y,sumBinList(list_eq.get(y))});
            }
            cleanex.put(key_s, list_vars);
        }
        return cleanex;
    }

    private void fillformulas(String[] f){

        HashMap<String,List<String>> example_t = new HashMap<String, List<String>>();
        example = new HashMap<String, List<String[]>>();
        String[][] provF = new String[f.length][2];

        for(int i=0;i<f.length;i++){

            provF[i] = f[i].replaceAll(" ","").split("=");
        }

        for(String[] o:provF){

            String var_P = o[0];
            List<String[]> var_F = new ArrayList<>();
            int y=0;

            for(int i=0;i<var_P.length();i++){

                String[] var__ = new String[2];

                if(var_P.charAt(i)>='a'&&var_P.charAt(i)<='z'){

                    if(y==0){

                        switch (i){

                            case 0:

                                var__[0] = "+1";
                                var__[1] = String.valueOf(var_P.charAt(0));
                                y=1;
                                System.out.println(var__[0] + var__[1]);
                                var_F.add(var__);
                                break;

                            case 1:

                                if(var_P.charAt(0)>='0'||var_P.charAt(0)<='9'){

                                    var__[0] = "+" + var_P.charAt(0);
                                    var__[1] = String.valueOf(var_P.charAt(1));
                                    y=2;
                                    System.out.println(var__[0] + var__[1]);
                                    var_F.add(var__);
                                }else {

                                    var__[0]= var_P.charAt(0) + "1";
                                    var__[1]= String.valueOf(var_P.charAt(1));
                                    y=i+1;
                                    System.out.println(var__[0] + var__[1]);
                                    var_F.add(var__);
                                }
                                break;

                            default:

                                if(var_P.charAt(0)>='0'&&var_P.charAt(0)<='9'){

                                    var__[0] = "+" + var_P.substring(y,i);
                                    var__[1] = String.valueOf(var_P.charAt(i));
                                    y=i+1;
                                    System.out.println(var__[0] + var__[1]);
                                    var_F.add(var__);
                                }else{

                                    var__[0] = var_P.substring(y,i);
                                    var__[1] = String.valueOf(var_P.charAt(i));
                                    y=i+1;
                                    System.out.println(var__[0] + var__[1]);
                                    var_F.add(var__);
                                }

                                break;
                        }

                    }
                    else if(!(var_P.charAt(i-1)>='0'&&var_P.charAt(i-1)<='9')){

                        var__[0] = var_P.charAt(i-1) + "1";
                        var__[1] = String.valueOf(var_P.charAt(i));
                        y=i+1;
                        System.out.println(var__[0] + var__[1]);
                        var_F.add(var__);
                    }else {

                        var__[0] = var_P.substring(y,i);
                        var__[1] = String.valueOf(var_P.charAt(i));
                        y=i+1;
                        System.out.println(var__[0] + var__[1]);
                        var_F.add(var__);
                    }
                }
            }
            example.put(o[1],var_F);

        }
        example=cleanformulas(example);

        for(String key:example.keySet()){

            System.out.println(key + " = ");

            for (String[] l:example.get(key)){

                System.out.println(l[0] + l[1]);
            }


        }

    }

}