package com.example.ruleengine.presenter;

import com.example.ruleengine.ast.Node;
import com.example.ruleengine.model.Rule;
import com.example.ruleengine.repository.RuleRepository;
import com.example.ruleengine.service.RuleParser;
import com.example.ruleengine.service.RuleEvaluator;
import com.example.ruleengine.service.RuleCombiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RulePresenter {

    private final RuleRepository ruleRepository;

    @Autowired
    public RulePresenter(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    // Create and store a new rule
    public Rule createRule(String ruleString) {
        Node ast = RuleParser.parseRule(ruleString);
        Rule rule = new Rule(ruleString, ast);
        return ruleRepository.save(rule);
    }

 // Combine multiple rules into a single AST and save the combined rule
    public Rule combineRules(List<String> ruleStrings) {
        List<Node> ruleAsts = ruleStrings.stream()
                .map(RuleParser::parseRule)
                .collect(Collectors.toList());

        Node combinedAst = RuleCombiner.combineRules(ruleAsts);
        Rule combinedRule = new Rule("Combined Rule", combinedAst);

        // Save the combined rule to the database
        return ruleRepository.save(combinedRule);
    }

    // Evaluate a rule against input data
    public boolean evaluateRule(String ruleId, Map<String, Object> data) {
        Rule rule = ruleRepository.findById(ruleId).orElse(null);
        if (rule != null) {
            return RuleEvaluator.evaluateRule(rule.getAst(), data);
        }
        return false;
    }
}
