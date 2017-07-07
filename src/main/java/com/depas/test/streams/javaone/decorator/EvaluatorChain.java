package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class EvaluatorChain implements Evaluator {
    private  Evaluator next;

    public EvaluatorChain (Evaluator nextEvaluator){
        this.next = nextEvaluator;
    }

    public boolean evaluate(Applicant applicant){
        return next.evaluate(applicant);
    }

}
