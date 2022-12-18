package de.fh.erfurt;

import de.fh.erfurt.objects.Clause;
import de.fh.erfurt.objects.Literal;

import java.util.ArrayList;
import java.util.List;

public class ResolutionCalculator {

    public static void main(String[] args) {
        final ArrayList<Clause> knf = new ArrayList<>();
        knf.add(new Clause(new Literal("A", false)));
        knf.add(new Clause(new Literal("A", true), new Literal("B", false)));
        knf.add(new Clause(new Literal("B", true)));

        if (knf.isEmpty()) {
            System.err.println("KNF without clauses are always false!");
            return;
        }

        ArrayList<Clause> resolvent = new ArrayList<>();
        boolean result = calculateResolution(knf, resolvent);

        // Print the result
        if (result) {
            System.out.println("The formula is satisfiable.");
        } else {
            System.out.println("The formula is not satisfiable.");
        }

    }

    private static boolean calculateResolution(ArrayList<Clause> knf, ArrayList<Clause> resolvent) {
        resolvent.addAll(knf);
        while (isContains(knf, resolvent) > 0) {
            // Select a pair of clauses to resolve
            for (int i = 0; i < resolvent.size(); i++) {
                for (int j = 1; j < resolvent.size(); j++) {
                    final Clause clause1 = resolvent.get(i);
                    final Clause clause2 = resolvent.get(j);
                    if (isResolvable(clause1.getLiterals(), clause2.getLiterals())) {
                        final Clause resolve = resolve(clause1, clause2);
                        resolvent.remove(clause1);
                        resolvent.remove(clause2);
                        resolvent.add(resolve);
                    }
                }
            }
        }

        return resolvent.stream().anyMatch(Clause::isEmpty);
    }

    private static int isContains(ArrayList<Clause> knf, ArrayList<Clause> resolvent) {
        int found = 0;
        for (Clause clause : resolvent) {
            if (knf.contains(clause)) {
                found++;
            }
        }
        return found;
    }

    public static Clause resolve(Clause clause1, Clause clause2) {
        // Create a new clause by removing the complementary literals from the input clauses
        final ArrayList<Literal> literals = new ArrayList<>();
        literals.addAll(clause1.getLiterals());
        literals.addAll(clause2.getLiterals());

        for (int i = 0; i < literals.size(); i++) {
            for (int j = 1; j < literals.size(); j++) {
                final Literal literal1 = literals.get(i);
                final Literal literal2 = literals.get(j);

                if (literal1.getName().equals(literal2.getName()) && literal1.isNegativ() == !literal2.isNegativ()) {
                    literals.remove(literal1);
                    literals.remove(literal2);
                    break;
                }

            }
        }

        return new Clause(literals);
    }

    public static boolean isResolvable(List<Literal> clause1, List<Literal> clause2) {
        // Check if the clauses have a complementary literal
        for (Literal literal1 : clause1) {
            for (Literal literal2 : clause2) {
                if (literal1.isNegativ() == !literal2.isNegativ()) {
                    return true;
                }
            }
        }
        return false;
    }
}
