public class BinaryTree {

    public static void main(String[] args) {
        Node root=new Node(5);
        add(root,1);
        add(root,4);
        add(root,6);
        add(root,3);
        add(root,2);
        preViewTree(root);
        System.out.println();
        middleViewTree(root);
        System.out.println();
        lateViewTree(root);
    }


    public static Node add(Node node,int a){

        if(node==null){
            node=new Node(a);
            return node;
        }
        if(a>=node.data){
            node.right=add(node.right,a);
        }else{
            node.left=add(node.left,a);
        }
        return node;
    }

    public static void preViewTree(Node node){
        if(node==null){
            return;
        }
        System.out.printf(node.data+"");
        preViewTree(node.left);
        preViewTree(node.right);
    }


    public static void middleViewTree(Node node){
        if(node==null){
            return;
        }
        preViewTree(node.left);
        System.out.printf(node.data+"");
        preViewTree(node.right);
    }

    public static void lateViewTree(Node node){
        if(node==null){
            return;
        }
        preViewTree(node.left);
        preViewTree(node.right);
        System.out.printf(node.data+"");


    }

    static class Node{
        int data;
        Node left;
        Node right;

        public Node(int a){
            data=a;
        }
    }
}
