import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        int tree[][];
        tree = enterTree();
        if(tree.length == 0)					//if the tree is empty 
        	System.out.println(""+0);
        else System.out.println(""+checkTree(tree));

    }
    
    public static int checkTree(int tree [][]) {
    	
        Stack<Integer> branchNodes=new Stack<Integer>();
        int numberOfBlackNodes = goLeftAndCountBlackNodes(tree,0,0);
        boolean isNumberOfBlackNodesEqualsInAllBranches = checkNumberOfBlackNodesInEachBranch(tree, 0, branchNodes, numberOfBlackNodes);
        boolean isNoTwoRedsNextToEach = checkIfTwoRedNodesNextToEachOther(tree,0);
        boolean isRBTreeBST = checkIfRBTreeIsBinarySearchTree(tree, 0);
        System.out.println("BST:"+isRBTreeBST+"\nTwoReds:"+isNoTwoRedsNextToEach+"\nNumberOfblackNodes:"+isNumberOfBlackNodesEqualsInAllBranches);
        
        if(tree[0][4]==0) {
            return 0;                //check if the root is black, if it is red return 0 ;
        }
        else if(isNumberOfBlackNodesEqualsInAllBranches && isNoTwoRedsNextToEach && isRBTreeBST) {
            
            return 1;
        }
        else {
            //System.out.println("hello");
            return 0;
        }
        
    }
    public static int goLeftAndCountBlackNodes(int tree[][],int indexRoot,int counter) {
        if(indexRoot == -1)
            return counter;
        if(tree[indexRoot][2]==-1 && tree[indexRoot][3]==-1 ) {
            if(tree[indexRoot][4]==1)
                return ++counter;
            else return counter;
        }
        if(tree[indexRoot][4]==1) {
            counter++;                    //if the node is black node  increment the counter
        }
        if(tree[indexRoot][2] != -1)                                                //if there is no left to the branch 
            counter = goLeftAndCountBlackNodes(tree, tree[indexRoot][2],counter);        
        else if(tree[indexRoot][2] == -1 && tree[indexRoot][3] != -1)                //then check the right of 
            counter = goLeftAndCountBlackNodes(tree, tree[indexRoot][3],counter);
        
        return counter;
    }
    
    
    public static boolean  checkNumberOfBlackNodesInEachBranch(int tree[][],int indexRoot,Stack<Integer> branchNodes,int numberOfBlackNodes) {
        if(indexRoot==-1) {
            branchNodes.push(-1);
            return true;
        }
        else if(tree[indexRoot][2]==-1 && tree[indexRoot][3]==-1) {
            branchNodes.push(tree[indexRoot][4]);
            int counter = 0;
            for(int i=0; i<branchNodes.size();i++) {
                if(branchNodes.get(i)==1) {
                    counter++;
                }
            }
            if(counter == numberOfBlackNodes)
                return true;
            
            else return false;
        }
        branchNodes.push(tree[indexRoot][4]); /// color of the node 1 for black and 0 for red

        boolean isBlack1 = checkNumberOfBlackNodesInEachBranch(tree, tree[indexRoot][2], branchNodes, numberOfBlackNodes);//go left
        branchNodes.pop();
        boolean isBlack2 = checkNumberOfBlackNodesInEachBranch(tree, tree[indexRoot][3], branchNodes, numberOfBlackNodes);//go right 
        branchNodes.pop();
 
        if(isBlack1 && isBlack2) {
            return true;
        }
        else return false; 
        
    }

    public static boolean  checkIfTwoRedNodesNextToEachOther(int tree[][],int indexRoot) {
        
    	if(indexRoot == -1) {
    		return true;
    	}
        if(tree[indexRoot][2]==-1 && tree[indexRoot][3]==-1) {
            return true;
        }
        boolean noTwoRedNodesNextToEachOther = true;
        
        boolean isBlack1 = checkIfTwoRedNodesNextToEachOther(tree, tree[indexRoot][2]);//go left
        if(tree[indexRoot][4]==0) {            //if the node is red 
            int left = tree[indexRoot][2];
            int right = tree[indexRoot][3];
            
            if((left != -1) && (right != -1)) {
                
                if((tree[left][4]==0 || tree[right][4]==0))
                    noTwoRedNodesNextToEachOther=false;
                
            }
            else if((left == -1)) {
                if(tree[right][4]==0)
                    noTwoRedNodesNextToEachOther=false;
            }
            else if((right == -1)) {
                if(tree[left][4]==0)
                    noTwoRedNodesNextToEachOther=false;
            }
                
            
        }
        else noTwoRedNodesNextToEachOther=true;
        boolean isBlack2 = checkIfTwoRedNodesNextToEachOther(tree, tree[indexRoot][3]);//go right 
   
        if(isBlack1 && isBlack2 && noTwoRedNodesNextToEachOther) {
            return true;
        }
        else return false; 
        
    }
    
    public static boolean checkIfRBTreeIsBinarySearchTree(int tree[][], int indexRoot) {
    	if(indexRoot == -1 ) {
    		return true;
    	}
        if(tree[indexRoot][2]==-1 && tree[indexRoot][3]==-1) {
            return true;
        }
        
        int right = tree[indexRoot][3];
        int left = tree[indexRoot][2];
        
        if(right != -1 ) {
            if(tree[right][1] < tree[indexRoot][1]) {	//right node should be greater than its parent , if not return false 

            	return false;
            }
        }

        if(left != -1) {
            if(tree[left][1] > tree[indexRoot][1]) {	//left node should be less than its parent, if not return false 
            	return false;
            }
        }

        
        boolean rightBranchIsBST = checkIfRBTreeIsBinarySearchTree(tree, right);		//if right or left = -1 the function will stop in the first condition 
        
        boolean leftBranchIsBST = checkIfRBTreeIsBinarySearchTree(tree, left);
        
        if (rightBranchIsBST && leftBranchIsBST) {
        	return true;
        }
        
        else {
        	return false;
        }
    }
    
    public static int [][] enterTree() {
            
        Scanner input = new Scanner(System.in);
        int [][] matrix;
        matrix = new int[input.nextInt()][5];
        for(int i=0; i<matrix.length; i++)
            for(int j=0; j<5;j++)
                matrix[i][j] = input.nextInt();
            
        input.close();
        return matrix;
    
    }
    
}

/*  tree in slides ( Lec9-RB_Trees, slide 40), the result here is 1 
9
0 7 1 5 1 
1 2 2 3 0 
2 1 -1 -1 1 
3 5 4 -1 1 
4 4 -1 -1 0 
5 11 6 7 0 
6 8 -1 -1 1 
7 14 -1 8 1 
8 15 -1 -1 0
*/

/* test case in hacker rank (one node added to the left of tree), the result here is 0 
8
0 6 1 6 1
1 4 2 5 0
2 2 3 4 1
3 1 7 -1 0		//difference 
4 3 -1 -1 0
5 5 -1 -1 1
6 7 -1 -1 1
7 9 -1 -1 1
*/
/*uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu
8
0 6 1 6 1
1 4 2 5 0
2 2 3 4 1
3 1 7 -1 0		//difference
4 3 -1 -1 0
5 5 -1 -1 1
6 7 -1 -1 1
7 0 -1 -1 1
*/

/*		tree as linked list and have two red nodes next to each other
4
0 1 -1 1 1 
1 2 -1 2 0 
2 3 -1 3 0
3 4 -1 -1 1
*/

/*		this tree is not Binary search tree
7
0 6 1 6 1
1 4 2 5 0
2 2 3 4 1
3 8 -1 -1 0
4 3 -1 -1 0
5 5 -1 -1 1
6 7 -1 -1 1
*/

/*		this tree is binary search tree but does not achieve all conditions of RB-Tree.
9
0 6 1 6 1
1 4 2 5 0
2 2 3 4 1
3 1 7 -1 0		
4 3 -1 -1 0
5 5 -1 -1 1
6 7 -1 -1 1
7 0 8 -1 1 
8 -1 -1 -1 0
 */
