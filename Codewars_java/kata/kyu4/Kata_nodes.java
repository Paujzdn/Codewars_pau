package kata.kyu4;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Kata_nodes {
    public static void main (String[] args){
        List<Integer> finn = byLevel(new Node(new Node(null, new Node(null, null, 4), 2), new Node(new Node(null, null, 5), new Node(null, null, 6), 3), 1));
        for(int i =0;i<finn.size();i++){
            System.out.println(finn.get(i));
        }
    }
    public static List<Integer> byLevel(Node root){

        Queue<Node> level  = new LinkedList<>();
        level.add(root);
        List<Integer> llst = new ArrayList<Integer>();
        if(root==null){
            return llst;
        }else{
            while(!level.isEmpty()){
                Node node = level.poll();
                llst.add(node.value);
                if(node.left!= null)
                    level.add(node.left);
                if(node.right!= null)
                    level.add(node.right);
            }
            return llst;
        }
    }
}
class Node {
    public Node left;
    public Node right;
    public int value;

    public Node(Node l, Node r, int v) {
        this.left = l;
        this.right = r;
        this.value = v;
    }
}