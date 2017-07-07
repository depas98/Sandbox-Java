package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class EvaluateCandidate {
    public static void evaluate(Applicant applicant, Evaluator evaluator) {
        String result = evaluator.evaluate(applicant) ? "accepted" : "rejected";
        System.out.println("Result of evaluating applicant: " + result);
    }

    public static void main(String[] args) {
        Applicant applicant = new Applicant();
        evaluate(applicant, new CreditEvaluator(new QualifiedEvaluator()));

        evaluate(applicant,
                new CreditEvaluator(
                        new EmploymentEvaluator(new QualifiedEvaluator())));

        evaluate(applicant, new CriminalRecordsEvaluator(new EmploymentEvaluator(new QualifiedEvaluator())));

        evaluate(applicant,
                new CriminalRecordsEvaluator(
                        new CreditEvaluator(
                                new EmploymentEvaluator(new QualifiedEvaluator()))));

    }
}
