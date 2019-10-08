package ast;

import java.util.ArrayList;
import java.util.List;

public class ClassDec extends Statement {

    private List<Statement> statements = new ArrayList<>();

    @Override
    public void parse() {
        while (tokenizer.moreTokens()) {
            Statement statement;
            if (tokenizer.checkToken("Interface")) {
                statement = new Interface();
            } else if (tokenizer.checkToken("Class")) {
                statement = new Class();
            } else if (tokenizer.checkToken("Abstract")) {
                String next = tokenizer.getNext();
                if (next.equals("Class")) {
                    statement = new AbstractClass();
                }
            } else {
                // add the classname to statement
            }
            statements.add(statement);
        }
    }

    @Override
    public void evaluate() {

    }

    @Override
    public void nameCheck() {

    }

    @Override
    public void typeCheck() {

    }

    public enum classType {
        CLASS,
        INTERFACE,
        ABSTRACT_CLASS
    }
}
