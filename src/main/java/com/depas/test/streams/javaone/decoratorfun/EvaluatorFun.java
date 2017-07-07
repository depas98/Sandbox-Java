package com.depas.test.streams.javaone.decoratorfun;

import com.depas.test.streams.javaone.decorator.Applicant;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
@FunctionalInterface
public interface EvaluatorFun {
    boolean evaluate(ApplicantFun applicant);
}
