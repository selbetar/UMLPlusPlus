package ast;

import library.Tokenizer;
import library.UmlBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public interface Node {
    Tokenizer tokenizer = Tokenizer.getTokenizer();
    UmlBuilder frame = UmlBuilder.getInstance();
    public static ArrayList<String> classes = new ArrayList<>();

    // Key = Superclass; Value = Subclass;
    public static HashMap<String, String> relationshipImplement = new HashMap<>();
    public static HashMap<String, String> relationshipExtend = new HashMap<>();

    void parse();

    void evaluate();

    void nameCheck();

    void typeCheck();
}
