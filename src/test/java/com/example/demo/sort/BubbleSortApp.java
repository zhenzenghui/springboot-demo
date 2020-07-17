package com.example.demo.sort;

/**
 * @author zzh
 * @date 2020/7/10
 */
public class BubbleSortApp {

    public static void main(String[] args) {
        int[] arr = new int[6];
        arr[0] = 4;
        arr[1] = 23;
        arr[2] = 1;
        arr[3] = 45;
        arr[4] = 3;
        arr[5] = 4;

        display(arr);
        System.out.println();



//        //冒泡排序 O(N²)
//        for (int out=arr.length-1; out>1; out--){
//            for (int in=0; in<out; in++){
//                if (arr[in] > arr[in+1]){
//                    swap(arr, in, in+1);
//                }
//            }
//        }


//        //选择排序 O(N²)
//        for (int out=0; out<arr.length-1; out++){
//            int min = out;
//            for (int in=out+1; in<arr.length; in++){
//                if (arr[in] < arr[min]){
//                    min = in;
//                }
//            }
//            swap(arr, out, min);
//        }


//        //插入排序 O(N²)
//        for (int out=1; out<arr.length; out++) {
//            int in = out;
//            int tem = arr[out];
//            while ((in > 0) && (arr[in-1] >= tem) ){
//                arr[in] = arr[in-1];
//                in--;
//            }
//            arr[in] = tem;
//        }




        display(arr);

    }

    private static void display(int[] arr){
        for (int i=0; i<arr.length; i++){
            System.out.print(arr[i]);
            System.out.print("  ");

        }
    }

    private static void swap(int[] arr, int i1, int i2){
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }


}


