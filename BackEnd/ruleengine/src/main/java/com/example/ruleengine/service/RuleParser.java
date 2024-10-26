package com.example.ruleengine.service;

import com.example.ruleengine.ast.Node;
import java.util.Stack;

public class RuleParser {

    // Parse a rule string into an AST
    public static Node parseRule(String ruleString) {
        Stack<Node> stack = new Stack<>();
        String[] tokens = ruleString.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (token.equals("AND") || token.equals("OR")) {
                Node right = stack.pop();
                Node left = stack.pop();
                Node operatorNode = new Node("operator", left, right);
                operatorNode.setValue(token);
                stack.push(operatorNode);
            } else {
                stack.push(new Node("operand", token));
            }
        }
        return stack.pop();
    }
}
