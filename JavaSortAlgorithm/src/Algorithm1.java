import java.util.ArrayList;
import java.util.List;

/**
 * 50个人围坐一圈，当数到三或者三的倍数出圈，问剩下的人是谁，原来的位置是多少
 */
public class Algorithm1 {


    public static void main(String[] args) {

        List<Integer> a=new ArrayList<>();

        for (int i=0;i<50;i++){
            a.add(i);
        }

        int index=-1;
        while (a.size()>1){
            index=(index+3)%a.size();
            a.remove(index);
            index--;
        }
        System.out.println(a.get(0));
    }

}
