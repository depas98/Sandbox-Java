package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class CreditEvaluator extends EvaluatorChain {
    public CreditEvaluator(Evaluator nextEvaluator) {
        super(nextEvaluator);
    }

    public boolean evaluate(Applicant applicant) {
        if (applicant.getCreditScore() > 600) {
            return super.evaluate(applicant);
        }
        return false;
    }
}
