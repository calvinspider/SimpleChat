import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class WaitNotify {

    static List<Integer> queue=new ArrayList<>();


    public static void main(String[] args) {

        Producer producer=new Producer(queue);
        Consumer consumer=new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();

    }


    static class Producer implements Runnable{

        List<Integer> queue;

        public Producer(List<Integer> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            try {
                while (true){
                    synchronized (queue){
                        if(queue.size()==10){
                            System.out.println("queue full");
                        }else{
                            int a=new Random().nextInt(100);
                            queue.add(a);
                            System.out.println("provider :"+a);
                            queue.notifyAll();
                        }
                    }
                    Thread.sleep(1000);
                }
            }catch (Exception e){

            }

        }
    }

    static class Consumer implements Runnable{

        List<Integer> queue;

        public Consumer(List<Integer> queue){
            this.queue=queue;
        }


        @Override
        public void run() {

            try {

                while (true){

                    synchronized (queue){
                        if(queue.size()==0){
                            System.out.println("queue empty");
                            queue.wait();
                        }else{
                            System.out.println("comsumer:"+queue.remove(queue.size()-1));
                        }
                    }
                    Thread.sleep(1000);
                }
            }catch (Exception e){

            }

        }
    }
}
