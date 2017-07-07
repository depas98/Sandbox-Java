package com.depas.test.streams.javaone.decoratorfun;

import java.util.function.Predicate;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class EvaluateCandidateFun {
    public static void evaluate(ApplicantFun applicant, Predicate<ApplicantFun> evaluator){
        String result = evaluator.test(applicant) ? "accepted" : "rejected";
        System.out.println("Result of evaluating applicant: " + result);

    }

    public static void main(String[] args) {
        ApplicantFun applicant = new ApplicantFun();

        Predicate<ApplicantFun> qualifiedEvaluator = ApplicantFun::isCredible;

        Predicate<ApplicantFun> creditEvaluator =
                a -> a.getCreditScore() > 600;

        Predicate<ApplicantFun> employmentEvaluator =
                a -> a.getEmploymentYear() > 2;

        Predicate<ApplicantFun> crimeCheck =
                a -> !a.hasCriminalRecord();

        evaluate(applicant, qualifiedEvaluator.and(creditEvaluator));

        evaluate(applicant, employmentEvaluator.and(creditEvaluator));

        evaluate(applicant, qualifiedEvaluator.and(employmentEvaluator).and(crimeCheck));

        evaluate(applicant, qualifiedEvaluator
                .and(employmentEvaluator)
                .and(creditEvaluator)
                .and(crimeCheck));
    }
}
