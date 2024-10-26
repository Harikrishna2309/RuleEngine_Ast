package com.example.ruleengine.ast;

public class Node {
	private String type;      // "operator" or "operand"
    private Node left;        // Left child for binary operations (AND/OR)
    private Node right;       // Right child for binary operations (AND/OR)
    private String value;     // Value for operand nodes (e.g., "age > 25")
    
    
 // Constructor for operator nodes
    public Node(String type, Node left, Node right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }
    
 // No-arg constructor (required for MongoDB)
    public Node() {
    }
    
 // Constructor for operand nodes
    public Node(String type, String value) {
        this.type = type;
        this.value = value;
    }
 // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
 // Utility method to print the AST 
    public void printAST() {
        if ("operator".equals(type)) {
            System.out.println("(");
            if (left != null) left.printAST();
            System.out.print(" " + value + " ");
            if (right != null) right.printAST();
            System.out.println(")");
        } else {
            System.out.print(value);
        }
    }
}
