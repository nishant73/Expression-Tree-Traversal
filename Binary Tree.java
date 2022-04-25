//package com.company;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class BinaryTree{
    Node root;
    int size;
    public static class Node{
        char data;
        Node left;
        Node right;
        Node(char data){
            this.data = data;
        }
        Node(char data, Node right,Node left){
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    public static int linearSearch(int[] arr, int key){
        for(int i=0;i<arr.length;i++){
            if(arr[i] == key){
                return i;
            }
        }
        return -1;
    }
    public static String toString(char[] a)
    {
        // Creating object of String class
        String string = new String(a);

        return string;
    }
    public static boolean isOperand(char element){

        int low = (int)'a';
        int high = (int)'z';
        if((int)element<=high&&(int)element>=low){
            return (true);
        }
        else return false;
    }
    public static boolean isOperator(char element){
        char [] operator = {'(',')','^','/','*','+','-'};
        int []operatorAscii = new int[operator.length];
        for(int i = 0;i<operator.length;i++){
            operatorAscii[i]=(int)operator[i];
        }
        if (linearSearch(operatorAscii,(int)element)!=-1){
            return true;
        }
        else return false;
    }
    public static boolean checkPrecedence(char input,char stkTop) {
        char[] operator = { '+', '-', '/', '*', '^','(', ')'};
        int [] precedence = {1,1,2,2,3,0,0};
        int[] operatorAscii = new int[operator.length];
        for (int i = 0; i < operator.length; i++) {
            operatorAscii[i] = (int) operator[i];
        }
        int indexInput = precedence[linearSearch(operatorAscii,(int)input)];
        int indexstkTop = precedence[linearSearch(operatorAscii,(int)stkTop)];
        if(input=='('){
            return false;
        }
        else if (indexstkTop > indexInput) {
            return true;
        } else if (indexInput > indexstkTop) {
            return false;
        }
        else return true;
    }
    public static ArrayList<Character> InfixToPostfix(char[] exp){
        // converting infix to postfix first using stack
        //precedence ()
        //^ R-L
        //  / * L-R
        // + - L-R
        // while scanning push the operators in stack if the precedence
        // of incoming operator is less than the top of the stack then pop,
        // keep poping until the precedence is lower than top of stack

        //if ( comes push it without checking, whatever comes after ( is pushed
        //when ) comes pop all the operators after (
        //once we reach end of the expression, pop out all the char in the stack until empty

        Stack<Character> stk = new Stack<>();
        ArrayList<Character> Postfix = new ArrayList<>();
        int i = 0;
        for(char element:exp){
            if(isOperand(element)){
                Postfix.add(element);
            }
            else {
                if((int)element==(int)'('){
                    stk.push(element);
                }
                else if((int)element==(int)')'){
                while (stk.peek()!='('){
                    Postfix.add(stk.pop());
                }
                stk.pop();
                }
                else {
                    while (!stk.empty()&&checkPrecedence(element,stk.peek())){
                        Postfix.add(stk.pop());
                    }
                    stk.push(element);
                }

            }
        }
        //emptying the stack
        while (!stk.empty()){
            Postfix.add(stk.pop());
        }
        //System.out.println("Postfix for the given expression\n"+Postfix.toString());
        return Postfix;
    }
    //               making binary tree with postfix expression
    //traverse the postfix expression
    //push nodes with operands as data
    //on finding operator as data,
                // pop->right node;
                // pop->left node;
                //push operator node.
    public static BinaryTree createBinaryTree(BinaryTree myTree,ArrayList<Character> postfix){
        Stack<Node>tempStack = new Stack<>();
        for (char element : postfix){
            if (isOperand(element)){
                tempStack.push(new Node(element));
            }
            else {
                tempStack.push(new Node(element,tempStack.pop(),tempStack.pop()));
            }
        }
        myTree.root = tempStack.pop();
        myTree.size = postfix.size();
        System.out.println("Binary Tree Created");
        return myTree;
    }
    public static void BinaryTreeInorder(Node node){
        if(node == null)
            return;
        if (isOperator(node.data))System.out.print("(");
        BinaryTreeInorder(node.left);
        System.out.print(node.data);
        BinaryTreeInorder(node.right);
        if (isOperator(node.data))System.out.print(")");
    }
    public static void BinaryTreePreorder(Node node){
        if(node == null)
            return;
        System.out.print(node.data);
        BinaryTreePreorder(node.left);
        BinaryTreePreorder(node.right);
    }
    public static void BinaryTreePostorder(Node node){
        if(node == null)
            return;
        BinaryTreePostorder(node.left);
        BinaryTreePostorder(node.right);
        System.out.print(node.data);

    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        char[] ch = new char[str.length()];

        // Copy character by character into array
        for (int i = 0; i < str.length(); i++) {
            ch[i] = str.charAt(i);
        }
        ArrayList<Character>postfix = InfixToPostfix(ch);

        BinaryTree myTree = new BinaryTree();
        myTree = createBinaryTree(myTree,postfix);

        System.out.println("Infix traversal");
        BinaryTreeInorder(myTree.root);
        System.out.println("");
        System.out.println("Prefix traversal");
        BinaryTreePreorder(myTree.root);
        System.out.println("");
        System.out.println("Postfix Traversal");
        BinaryTreePostorder(myTree.root);
    }
}
