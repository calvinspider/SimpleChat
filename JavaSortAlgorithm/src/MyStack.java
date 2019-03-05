public class MyStack {

    class Stack{
        int capaty=10;
        int[] data=new int[capaty];
        int point;
        int size;

        public boolean add(int e){
            if(size==capaty){
                return false;
            }
            data[point++]=e;
            size++;
            return true;
        }

        public int pop(){
            if(point==0){
                return 0;
            }
            int i=data[point--];
            size--;
            return i;
        }
    }

}
