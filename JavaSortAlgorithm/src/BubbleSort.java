public class BubbleSort {

    public static void main(String[] args) {

        int[] a={2,4,1,2,33,1,5,11,21};
        bubbleSort(a);
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }

    }

    public static void bubbleSort(int[] array){
        for (int i=0;i<array.length;i++){
            for (int j=i+1;j<array.length;j++){
                if(array[i]>array[j]){
                    int tmp=array[i];
                    array[i]=array[j];
                    array[j]=tmp;
                }
            }
        }
    }

}
