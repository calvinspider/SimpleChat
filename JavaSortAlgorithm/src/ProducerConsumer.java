import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

    public static void main(String[] args) {

        BlockingQueue<Integer> queue=new ArrayBlockingQueue<>(100);

        Producer producer=new Producer(queue);
        Consumer consumer1=new Consumer(queue);
        Consumer consumer2=new Consumer(queue);
        Consumer consumer3=new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
        new Thread(consumer3).start();

    }

    static class Producer implements Runnable{

        private BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            while (true){
                int a=new Random().nextInt(1000);
                queue.add(a);
                System.out.println("producer:"+a);
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    static class Consumer implements Runnable{

        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue){
            this.queue=queue;
        }

        @Override
        public void run() {
            while (true){
                try {
                    System.out.println("consumer:"+queue.take());
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

}
