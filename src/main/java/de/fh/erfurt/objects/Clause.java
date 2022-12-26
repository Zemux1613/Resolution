package de.fh.erfurt.objects;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Getter
public class Clause {
    private final List<Literal> literals;

    public Clause(Literal... literals) {
        this.literals = Arrays.stream(literals).toList();
    }

    public Clause(ArrayList<Literal> literals) {
        this.literals = literals;
    }

    public boolean isEmpty() {
        return this.literals.isEmpty();
    }

    public void printClause() {
        StringJoiner literalsString = new StringJoiner(", ");
        literals.forEach(literal -> {
            literalsString.add((literal.negativ() ? "¬" : "") + literal.name());
        });
        System.out.println("{" + literalsString.toString() + "}");
    }
}
