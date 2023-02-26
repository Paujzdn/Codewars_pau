package kata.kyu2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.*;
/*
class nodeEX
{
    String value;
    nodeEX right;
    nodeEX left;

    // Constructors
    public nodeEX()
    {
        this.value = "";
        this.right = this.left = null;
    }

    public nodeEX(String operation)
    {
        this.value = operation;
        this.right = this.left = null;
    }


    public nodeEX(String operation, nodeEX right, nodeEX left)
    {
        this.value = operation;
        this.right = right;
        this.left  = left;
    }

    // Method
    /*
    public void ajouteGauche(String caractere) // to add the left child
    {
        nodeEX gauche = new nodeEX(caractere);
        this.left = gauche;
    }

    public void ajouteDroite(String caractere) // to add the right child
    {
        nodeEX right = new nodeEX(caractere);
        this.right = right;
    }

    public boolean isLeaf()
    {
        return this.right == null && this.left == null;
    }


}
*/
public class PrefixDiff {
    /*
    nodeEX nodeTop;
    public void buildtree(String x){
        String[] y = fillN(x);
        this.nodeTop = new nodeEX(y[0]);
        this.nodeTop.left=subnode(y[1]);
        this.nodeTop.right=subnode(y[2]);
    }
    public nodeEX subnode(String y){
        String[] o = fillN(y);
        nodeEX d = new nodeEX(o[0]);
        d.left=subnode(o[1]);d.right=subnode(o[2]);
        return d;
    }

     */
    public static String[] fillN(String expr){
        String exprs=expr;
        String[] ret = new String[3];
        if(expr.charAt(0)=='('){
            exprs=expr.substring(1,expr.length()-1);
        }
        int index=0;
        while (exprs.charAt(index)!=' '){
            index++;
        }
        ret[0]=exprs.substring(0,index);
        exprs=exprs.substring(index+1,exprs.length()).trim();
        if(exprs.charAt(0)!='('){
            int index1=0;
            while (exprs.charAt(index1)!=' '){
                index1++;
            }
            ret[1]=exprs.substring(0,index1);
            exprs=exprs.substring(index1+1,exprs.length()).trim();
        }else {
            int par = 0;
            int parf = 0;
            int index2 = 0;
            while (parf == 0) {
                if (exprs.charAt(index2) == '(') {
                    par++;
                }
                if (exprs.charAt(index2) == ')') {
                    par--;
                    if (par == 0) {
                        parf = index2;
                    }
                }
                index2++;
            }
            ret[1] = exprs.substring(0, parf+1);
            exprs = exprs.substring(parf + 1, exprs.length());
        }
        ret[2]=exprs.trim();
        return ret;
    }
    public static void main(String[] args) {
        //this.nodeTop = new nodeEX("(* (^ (cos (* 2 x)) -2) 2 ");
        String[] r= new String[3];
        r=fillN("(+ (* 1 x) (* 2 (+ x 1)))");
        String[] t= new String[3];
        t=fillN("(sin (^ (cos (* 2 x)) -2) (^ (cos (* 2 x)) -2))");
        for(String x:r){
            System.out.println(x);
        }
        for(String x:t){
            System.out.println(x);
        }
    }
    /*
    public static List<String> byLevel(nodeEX rt){
        Queue<nodeEX> level  = new LinkedList<>();
        level.add(rt);
        List<String> llst = new ArrayList<String>();
        if(rt==null){
            return llst;
        }else{
            while(!level.isEmpty()){
                nodeEX node = level.poll();
                llst.add(node.value);
                if(node.left!= null)
                    level.add(node.left);
                if(node.right!= null)
                    level.add(node.right);
            }
            return llst;
        }
    }
     */
    public static void clalcopd(String opd){
        String resu="";
        String[] llst= fillN(opd);
        if((llst[1]!="x")&&(llst[0]!="/")&&(llst[0]!="-")&&(llst[0]!="/")){
            String hh = llst[1];
            llst[1]=llst[2];
            llst[2]=hh;
        }
        if(llst[1]=="x"&&llst[2]=="x"){
            switch (llst[0]){
                case "+":
                    resu = "2";
                    break;
                case "-":
                    resu = "0";
                    break;
                case "*":
                    resu = "(* 2 x)";
                    break;
                case "/":
                    resu = "0";
                    break;
                case "cos":
                    resu = "(* -1 (sin x))";
                    break;
                case "sin":
                    resu = "(cos x)";
                    break;
                case "tan":
                    resu = "(/ 1 (^ (cos x) 2))";
                    break;
                case "exp":
                    resu = opd;
                    break;
                case "ln":
                    resu = "(/ 1 x)";
                    break;

            }
        }else{
            Integer.parseInt(llst[2]);
            switch (llst[0]){
                case "+":
                    resu = "1";
                    break;
                case "-":
                    resu = "0";
                    break;
                case "*":
                    resu = "(* 2 x)";
                    break;
                case "/":
                    resu = "0";
                    break;
                case "cos":
                    resu = "(* -1 (sin x))";
                    break;
                case "sin":
                    resu = "(cos x)";
                    break;
                case "tan":
                    resu = "(/ 1 (^ (cos x) 2))";
                    break;
                case "exp":
                    resu = opd;
                    break;
                case "ln":
                    resu = "(/ 1 x)";
                    break;

            }
        }

    }
    public static void diff(String s){
        String resultat="";
        String[] expresioll = fillN(s);
        char[] op1 = expresioll[1].toCharArray();
        char[] op2 = expresioll[2].toCharArray();
        if(expresioll[1].contains("x")){
            if(expresioll[1].length()>12){
                String[] expresiollop1 = fillN(expresioll[1]);
                if(expresiollop1[1].contains("x")){

                }
            }
        }


    }
}
