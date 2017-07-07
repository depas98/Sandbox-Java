package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class CriminalRecordsEvaluator extends EvaluatorChain {
    public CriminalRecordsEvaluator(Evaluator nextEvaluator){
        super(nextEvaluator);
    }

    public boolean evaluate(Applicant applicant){
        if (!applicant.hasCriminalRecord()){
            return super.evaluate(applicant);
        }
        return false;
    }

}
