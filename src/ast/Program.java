package ast;

import library.exceptions.InvalidGrammarException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Program implements Node {

    private List<Statement> statements = new ArrayList<>();

    @Override
    public void parse() {
        while (tokenizer.moreTokens()) {
            Statement statement;
            if (tokenizer.checkToken("Declare")) {
                statement = new ClassDec();
            } else if (tokenizer.checkToken("Implements") || tokenizer.checkToken("Extends")) {
                statement = new Relationship();
            } else if (tokenizer.checkToken("Add")) {
                statement = new AttributeDec();
            } else {
                throw new InvalidGrammarException();
            }
            statement.parse();
            statements.add(statement);
        }
    }

    @Override
    public void evaluate() {
        List<Statement> classDecs = statements.stream().filter((statement -> statement.getClass() == ClassDec.class))
                .collect(Collectors.toList());
        List<Statement> attributeDecs = statements.stream().filter((statement -> statement.getClass() == AttributeDec.class))
                .collect(Collectors.toList());
        List<Statement> relationshipDecs = statements.stream().filter((statement -> statement.getClass() == Relationship.class))
                .collect(Collectors.toList());

        for (Statement s : statements) {
            s.evaluate();
        }

    }

    @Override
    public void nameCheck() {

    }

    @Override
    public void typeCheck() {

    }
}
