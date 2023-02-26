package kata.kyu2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class WhitespaceInterpreter_ {
    /*
    public static void main(String[] args) throws IOException {
        String in="   \t     \t\n\t\n  \n\n\n";
        System.out.println(in);
        InputStream inp = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        String o = execute(unbleach(in),inp);
        System.out.println(o);
    }

     */


    // transforms space characters to ['s','t','n'] chars;
    static String unbleach(String code) {
        return code != null ? code.replace(' ', 's').replace('\t', 't').replace('\n', 'n') : null;
    }
    static String remove_com(String code){//treu tot lo que no sigui t,n,s
        return code != null ? code.replaceAll("[^\\t\\s\\n]","") : null;
    }
    static String prepare_code(String code){
        return unbleach(remove_com(code));
    }
    // solution
    public static String execute(String code, InputStream input){
        Stack<Integer> stack = new Stack<>();
        Map<Integer,Integer> heap = new HashMap<>();
        String output = "";

        System.out.println(code);
        while (code.length()>0){
            if(code.startsWith("s")){
                code=code.substring(1);
                if(code.startsWith("s")){
                    code=code.substring(1);
                    int endnumb, j=0;
                    while (code.charAt(j)!='n'){
                        j++;
                    }
                    String numb = code.substring(0,j);//numero en whitespace, comença en s si es positiu, comença en t si es negatiu
                    char signe=numb.charAt(0);
                    numb=numb.substring(1).replace('s','0').replace('t','1');
                    int number=Integer.parseInt(numb,2);
                    if(signe=='t'){
                        number=0-number;
                    }
                    stack.push(number);
                    code=code.substring(j+1);//code sense el numero, començament del seguent comand
                }else if (code.startsWith("ts")){
                    code=code.substring(2);
                    int endnumb, j=0;
                    while (code.charAt(j)!='n'){
                        j++;
                    }
                    String numb = code.substring(0,j);//numero en whitespace, comença en s si es positiu, comença en t si es negatiu
                    char signe=numb.charAt(0);
                    numb=numb.substring(1).replace('s','0').replace('t','1');
                    int number=Integer.parseInt(numb,2);
                    stack.push(stack.elementAt(number));
                    code=code.substring(j+1);//code sense el numero, començament del seguent comand
                }else if (code.startsWith("tn")){
                    code=code.substring(2);
                    int endnumb, j=0;
                    while (code.charAt(j)!='n'){
                        j++;
                    }
                    String numb = code.substring(0,j);//numero en whitespace, comença en s si es positiu, comença en t si es negatiu
                    char signe=numb.charAt(0);
                    numb=numb.substring(1).replace('s','0').replace('t','1');
                    int number=Integer.parseInt(numb,2);
                    if((number<0)||(number>=stack.size())){
                        int num_top=stack.pop();
                        stack.removeAllElements();
                        stack.push(num_top);
                    }else{
                        int num_top =stack.pop();
                        while (number>0){//DEPEN POTSER POSAR EL >=
                            stack.pop();
                            number--;
                        }
                        stack.push(num_top);
                    }
                    code=code.substring(j+1);//code sense el numero, començament del seguent comand
                }else if (code.startsWith("ns")){
                    code=code.substring(2);
                    int a = stack.pop();
                    stack.push(a);
                    stack.push(a);
                }else if (code.startsWith("nt")){
                    code=code.substring(2);
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b);
                    stack.push(a);
                }else if (code.startsWith("nt")){
                    code=code.substring(2);
                    int a = stack.pop();
                }
            }
            else if(code.startsWith("ts")){
                code=code.substring(2);
                if (code.startsWith("ss")){
                    code=code.substring(2);
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a+b);
                }else if (code.startsWith("st")){
                    code=code.substring(2);
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a-b);
                }else if (code.startsWith("sn")){
                    code=code.substring(2);
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a*b);
                }else if (code.startsWith("ts")){
                    code=code.substring(2);
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a/b);
                }else if (code.startsWith("tt")){
                    code=code.substring(2);
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(a%b);
                }
            }
            else if(code.startsWith("tt")){
                code=code.substring(2);
                if (code.startsWith("s")){
                    code=code.substring(1);
                    int a = stack.pop();
                    int b = stack.pop();
                    heap.put(b,a);
                }else if (code.startsWith("t")){
                    code=code.substring(1);
                    int a = stack.pop();
                    heap.put(a,a);
                }
            }
            else if(code.startsWith("tn")){
                code=code.substring(2);
                if (code.startsWith("st")){
                    int a = stack.pop();
                    output = output + Integer.toString(a);
                }else if (code.startsWith("ss")){
                    int a = stack.pop();
                    output=output+((char) a);
                }
                if (code.startsWith("ts")){
                    char a = 0;
                    try {
                        a = (char) input.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int a_ = (int) a;
                    int b = stack.pop();
                    heap.put(b,a_);
                }else if (code.startsWith("tt")){
                    int a = 0;
                    try {
                        a = input.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int b = stack.pop();
                    heap.put(b,a);
                }
                code=code.substring(2);
            }
            else if(code.startsWith("n")){
                code=code.substring(1);
            }
        }
        /*
        if (outputStream!=null){
            try {
                outputStream.write(output.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         */
        return output;
    }
}
