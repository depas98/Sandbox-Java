package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class QualifiedEvaluator implements Evaluator {
    public boolean evaluate(Applicant applicant){
        return applicant.isCredible();
    }
}
