import com.sun.org.apache.xpath.internal.operations.Bool;

public class MyQueue {

    public static void main(String[] args) {

    }



    class Queue{

        Integer[] data;
        Integer capatiy;
        Integer head;
        Integer tire;

        public Queue(){
            new Queue(10);
        }

        public Queue(Integer capatiy){

            data=new Integer[capatiy];
            head=0;
            tire=0;
        }

        public Integer size(){
            return capatiy;
        }

        public boolean isEmpty(){
            return head.equals(tire);
        }

        public boolean add(Integer e){
            if(head==(tire+1)%data.length){
                return false;
            }
            data[tire]=e;
            tire=(tire+1)%data.length;
            capatiy++;
            return true;
        }

        public Integer pop(){
            if(!isEmpty()){
                Integer i=data[head];
                head=(head+1)%data.length;
                capatiy--;
                return head;
            }
            return 0;
        }

    }


}
