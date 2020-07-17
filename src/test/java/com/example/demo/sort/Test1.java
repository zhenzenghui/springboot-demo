package com.example.demo.sort;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author zzh
 * @date 2020/7/14
 */
public class Test1 {

    public static void main(String[] args) throws Exception{
//        StackX stackX = new StackX(10);
////
////        stackX.push(10);
////        stackX.push(20);
////        stackX.push(30);
////        stackX.push(40);
////        stackX.push(50);
////        stackX.push(60);
////
////        while (!stackX.isEmpty()){
////            int pop = stackX.pop();
////            System.out.println(pop);
////        }
//        Scanner scanner = new Scanner(System.in);
//        String s1 = scanner.nextLine();
//        System.out.println("please enter string : ");
//        String string = getString();

        /*String string = "你好";
        Reverser reverser = new Reverser(string);
        String out = reverser.doRev();
        System.out.println(out);*/


        Queue queue = new Queue(5);
        queue.insert(10);
        queue.insert(20);
        queue.insert(30);
        queue.insert(40);
        queue.insert(50);

        queue.remove();
        queue.remove();

        queue.insert(90);
        queue.insert(80);
        queue.insert(100);
        queue.insert(110);
//        queue.insert(120);

        while (!queue.isEmpty()){
            int remove = queue.remove();
            System.out.println(remove);
        }


    }

    public static String getString() throws Exception{
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(inputStreamReader);
        String s = bf.readLine();
        return s;
    }

}

class Reverser{

    private String input;

    private String output;

    public Reverser(String input){
        this.input = input;
    }

    public String doRev(){

        int in = input.length();
        StackX stackX = new StackX(in);
        for (int i=0; i<in; i++){
            char c = input.charAt(i);
            stackX.push(c);
        }
        output = "";
        while (!stackX.isEmpty()){
            output = output + stackX.pop();
        }
        return output;
    }

}


class StackX {

    private int maxSize;

    //private int[] stackArr;
    private char[] stackArr;

    private int top;

    public StackX(int maxSize){
        this.maxSize = maxSize;
        //stackArr = new int[maxSize];
        stackArr = new char[maxSize];
        top = -1;
    }

//    public void push(int i){
//        stackArr[++top] = i;
//    }

    public void push(char i){
        stackArr[++top] = i;
    }

    public char pop(){
        return stackArr[top--];
    }

    public char peek(){
        return stackArr[top];
    }

    public boolean isFull(){
        return (top == maxSize);
    }

    public boolean isEmpty(){
        return (top == -1);
    }

}


class Queue{

    private int maxSize;

    private int[] queueArr;

    private int front;

    private int rear;

    private int nItems;

    public Queue (int maxSize){
        this.maxSize = maxSize;
        queueArr = new int[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }

    public void insert(int i){
        if (rear == queueArr.length-1){
            rear = -1;
        }
        queueArr[++rear] = i;
        nItems++;
    }

    public int remove(){
        int tem = queueArr[front++];
        if (front == queueArr.length){
            front = 0;
        }
        nItems--;
        return tem;
    }

    public int peekFront(){
        return queueArr[front];
    }

    public boolean isEmpty(){
        return (nItems == 0);
    }

    public boolean isFull(){
        return (nItems == queueArr.length);
    }

}

