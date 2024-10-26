package com.example.ruleengine.controller;

import com.example.ruleengine.model.Rule;
import com.example.ruleengine.presenter.RulePresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ruleengine.repository.RuleRepository;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "*")

public class RuleController {

	 private final RulePresenter rulePresenter;
	 private final RuleRepository ruleRepository;

	 @Autowired
	 public RuleController(RulePresenter rulePresenter, RuleRepository ruleRepository) {
	    this.rulePresenter = rulePresenter;
	    this.ruleRepository = ruleRepository;
    }

    @PostMapping("/create")
    public Rule createRule(@RequestBody String ruleString) {
        return rulePresenter.createRule(ruleString);
    }

    @PostMapping("/combine")
    public Rule combineRules(@RequestBody Map<String, List<String>> requestData) {
        List<String> ruleStrings = requestData.get("rules");
        return rulePresenter.combineRules(ruleStrings);
    }

    @PostMapping("/evaluate")
    public boolean evaluateRule(@RequestParam String ruleId, @RequestBody Map<String, Object> data) {
        return rulePresenter.evaluateRule(ruleId, data);
    }
    @GetMapping("/allrules")
    public List<Rule> getAllRules() {
        List<Rule> rules = ruleRepository.findAll();
        System.out.println("Fetched rules: " + rules);  // Log the rules for testing
        return rules;
    }
    
}
