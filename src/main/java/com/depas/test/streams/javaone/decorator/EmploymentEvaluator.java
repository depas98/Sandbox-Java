package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class EmploymentEvaluator extends EvaluatorChain  {
    public EmploymentEvaluator (Evaluator nextEvaluator){
        super(nextEvaluator);
    }

    public boolean evaluate(Applicant applicant){
        if (applicant.getEmploymentYear() > 2){
            return super.evaluate(applicant);
        }
        return false;
    }

}
