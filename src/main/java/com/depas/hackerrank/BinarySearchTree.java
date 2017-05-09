package com.depas.hackerrank;

import java.util.Scanner;

class TreeNode{
    TreeNode left,right;
    int data;
    TreeNode(int data){
        this.data=data;
        left=right=null;
    }
}

public class BinarySearchTree {

	public static int getHeight(TreeNode root){
		int totalLeft = 0;
		int totalRight = 0;
	    //Write your code here
		if (root.left==null && root.right==null){
			return 0;
		}
		if (root.left!=null){
			totalLeft = 1 + getHeight(root.left);
		}
		if (root.right!=null){
			totalRight =  1 + getHeight(root.right);
		}
		
		return totalLeft > totalRight ? totalLeft : totalRight;
	}
	
    public static TreeNode insert(TreeNode root,int data){
        if(root==null){
            return new TreeNode(data);
        }
        else{
            TreeNode cur;
            if(data<=root.data){
                cur=insert(root.left,data);
                root.left=cur;
            }
            else{
                cur=insert(root.right,data);
                root.right=cur;
            }
            return root;
        }
    }
	 public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int T=sc.nextInt();
        TreeNode root=null;
        while(T-->0){
            int data=sc.nextInt();
            root=insert(root,data);
        }
        int height=getHeight(root);
        System.out.println(height);
    }
}
