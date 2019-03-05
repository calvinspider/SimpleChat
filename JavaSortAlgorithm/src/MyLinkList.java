public class MyLinkList {


    class LinkList{
        Node head=new Node(Integer.MAX_VALUE);
        Node rear=head;
        int size;

        public boolean add(int a){
            if(size==0){
                head.data=a;
                size++;
            }else{
                rear=new Node(a);
                head.next=rear;
                size++;
            }
            return true;
        }

        public boolean remove(int a){
            Node p=head;
            Node q=head;
            for (int i=0;i<a;i++){
                q=p;
                p=p.next;
            }
            q.next=p;
            size--;
            return true;
        }
    }


    class Node{
        int data;
        Node next;

        public Node(int i){
            data=i;
        }

        public void setNext(Node n){
            next=n;
        }
    }

}
