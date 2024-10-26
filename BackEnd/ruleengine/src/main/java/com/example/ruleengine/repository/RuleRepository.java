package com.example.ruleengine.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ruleengine.model.Rule;

public interface RuleRepository extends MongoRepository<Rule, String> {

}
