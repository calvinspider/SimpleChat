public class BinarySearch {

    public static void main(String[] args) {
        int[] a={1,2,3,4,5,6,7,8};
        System.out.println(binarySearch(a,6));
    }


    public static int binarySearch(int[] a,int key){
        int low=0;
        int high=a.length-1;

        if(key>a[high]||key<a[low]){
            return -1;
        }

        while (low<=high){
            int middle=(low+high)/2;
            if(a[middle]>key){
                high=middle-1;
            }else if(a[middle]<key){
                low=middle+1;
            }else{
                return middle;
            }
        }
        return -1;
    }

}
