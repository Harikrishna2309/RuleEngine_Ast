package com.example.ruleengine.service;

import com.example.ruleengine.ast.Node;

import java.util.Map;

public class RuleEvaluator {

    // Evaluate the rule AST against provided data
    public static boolean evaluateRule(Node ast, Map<String, Object> data) {
        if (ast == null) return false;

        if ("operator".equals(ast.getType())) {
            boolean leftResult = evaluateRule(ast.getLeft(), data);
            boolean rightResult = evaluateRule(ast.getRight(), data);

            if ("AND".equals(ast.getValue())) {
                return leftResult && rightResult;
            } else if ("OR".equals(ast.getValue())) {
                return leftResult || rightResult;
            }
        } else if ("operand".equals(ast.getType())) {
            return evaluateCondition(ast.getValue(), data);
        }

        return false;
    }

    private static boolean evaluateCondition(String condition, Map<String, Object> data) {
        String[] tokens = condition.split(" ");
        
        // Check if the tokens array has at least 3 elements
        if (tokens.length < 3) {
            throw new IllegalArgumentException("Invalid condition format: " + condition);
        }
        
        String key = tokens[0];
        String operator = tokens[1];
        String value = tokens[2];

        Object dataValue = data.get(key);

        // Evaluation logic...
        if (dataValue instanceof Number) {
            double numDataValue = ((Number) dataValue).doubleValue();
            double numConditionValue = Double.parseDouble(value);

            switch (operator) {
                case ">":
                    return numDataValue > numConditionValue;
                case "<":
                    return numDataValue < numConditionValue;
                case "=":
                    return numDataValue == numConditionValue;
                default:
                    return false;
            }
        } else if (dataValue instanceof String) {
            return dataValue.equals(value);
        }

        return false;
    }

}
