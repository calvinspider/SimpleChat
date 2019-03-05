import java.util.Random;

/**
 * 随机产生20个不能重复的字符并排序
 */
public class Algorithm2 {

    public static void main(String[] args) {

        for (int i=0;i<4;i++){
            char a=(char) new Random().nextInt(20);
            System.out.println(a);
        }

    }

}
