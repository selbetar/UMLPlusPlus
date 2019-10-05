import ast.Node;
import ast.Program;
import library.AttributeStorage;
import library.Tokenizer;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> literals = Arrays.asList("Declare", "As", "Add", "To", "[", "]", ",", ":",
                "Interface", "Class", "Abstract Class", "Implements", "Extends", ">", "Fields", "Methods",
                "+", "-", "#");
        Tokenizer.makeTokenizer("input.txt",literals);
        Node program = new Program();
        program.parse();
        program.nameCheck();
        program.typeCheck();
        program.evaluate();
    }
}
