package de.fh.erfurt.objects;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Clause {
    private final List<Literal> literals;

    public Clause(Literal... literals) {
        this.literals = Arrays.stream(literals).toList();
    }

    public Clause(ArrayList<Literal> literals){
        this.literals = literals;
    }

    public boolean isEmpty(){
        return this.literals.isEmpty();
    }
}
