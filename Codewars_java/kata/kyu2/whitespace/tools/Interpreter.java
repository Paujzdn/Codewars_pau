package kata.kyu2.whitespace.tools;

import kata.kyu2.whitespace.operations.InterpreterState;

import java.io.InputStream;

public interface Interpreter {

    void interpret(String code, InterpreterState state, InputStream input);

}