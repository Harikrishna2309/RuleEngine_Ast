package com.example.ruleengine.service;

import com.example.ruleengine.ast.Node;
import java.util.List;

public class RuleCombiner {

    // Combine multiple ASTs into one using OR
    public static Node combineRules(List<Node> rules) {
        if (rules == null || rules.isEmpty()) return null;

        Node combinedAst = rules.get(0);

        for (int i = 1; i < rules.size(); i++) {
            combinedAst = new Node("operator", combinedAst, rules.get(i));
            combinedAst.setValue("OR");
        }
        return combinedAst;
    }
}
