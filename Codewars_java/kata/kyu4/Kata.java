package kata.kyu4;

import java.util.ArrayList;
import java.util.List;

public class Kata {
    public static void main(String[] args){
        String textcomplet = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sa"
                + "gittis dolor mauris, at elementum ligula tempor eget. In quis rhoncus nunc, at aliquet orci. Fusc"
                + "e at dolor sit amet felis suscipit tristique. Nam a imperdiet tellus. Nulla eu vestibulum urna. V"
                + "ivamus tincidunt suscipit enim, nec ultrices nisi volutpat ac. Maecenas sit amet lacinia arcu, no"
                + "n dictum justo. Donec sed quam vel risus faucibus euismod. Suspendisse rhoncus rhoncus felis at f"
                + "ermentum. Donec lorem magna, ultricies a nunc sit amet, blandit fringilla nunc. In vestibulum vel"
                + "it ac felis rhoncus pellentesque. Mauris at tellus enim. Aliquam eleifend tempus dapibus. Pellent"
                + "esque commodo, nisi sit amet hendrerit fringilla, ante odio porta lacus, ut elementum justo nulla"
                + " et dolor.";
        System.out.println(justify(textcomplet,90));
    }
    public static String justify(String text, int width) {

        String[] txt = text.split("\s");
        List<String> out = new ArrayList<String>();
        out.add("");
        int x=width; int idx=0;
        x =width-out.get(idx).length();
        for(int i=0;i<txt.length;i++){
            if(x>txt[i].length()){
                if(out.get(idx)==""){
                    out.set(idx,out.get(idx)+txt[i]);
                    x =width-out.get(idx).length();
                }else{
                    out.set(idx,out.get(idx)+ "\s" + txt[i]);
                    x =width-out.get(idx).length();
                }
            } else{
                int caracterssobrants=width-out.get(idx).length();
                String espaigran = "  ";String espaipetit = " ";
                if(caracterssobrants>0){
                    String[] lnl = out.get(idx).split("   ");
                    int espais=lnl.length-1;
                    int espaissobrants; int espaisresidu;
                    if(espais>0){
                        espaisresidu=caracterssobrants%espais;
                        espaissobrants=caracterssobrants/espais;
                        out.set(idx,"");
                        for(int o=0;o<espaissobrants;o++){
                            espaigran+= " ";
                            espaipetit+= " ";
                        }
                        for(String y:lnl){
                            if(espaisresidu>0){
                                out.set(idx,out.get(idx)+y+espaigran);
                                espaisresidu--;
                            }
                            else{
                                out.set(idx,out.get(idx)+y+espaipetit);
                                espaisresidu--;
                            }
                        }
                        out.set(idx,out.get(idx).substring(0,width));
                        espais=0;
                    }else{
                        espais=0;
                        espaisresidu=0;
                        espaissobrants=0;
                        out.set(idx,lnl[0]);
                    }
                }
                idx++;
                out.add(txt[i]);
                x=width-out.get(idx).length();
            }
        }
        String Output=new String();
        for(int i=0;i< out.size();i++){
            Output+=out.get(i)+"\n";
        }
        Output= Output.substring(0,Output.length()-1);
        return Output;

    }
}