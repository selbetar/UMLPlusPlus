package library.exceptions;

public class NameCheckException extends RuntimeException {

    public NameCheckException(String s) {
        System.out.println("Name Check Failed. You forgot to declare class: " + s);
    }
}
