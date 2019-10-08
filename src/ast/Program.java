package ast;

import library.exceptions.InvalidGrammarException;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public void nameCheck() {
        for (STATEMENT s : statements) {
            s.nameCheck();
        }
    }

    @Override
    public void typeCheck() {

    }
}
