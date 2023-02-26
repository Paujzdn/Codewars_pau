package kata.kyu2.whitespace.tools;

import kata.kyu2.whitespace.operations.FlowControlOperation;
import kata.kyu2.whitespace.operations.InterpreterState;

import java.io.InputStream;

public class FlowControlInterpreter implements Interpreter {

    @Override
    public void interpret(String code, InterpreterState state, InputStream input) {
        FlowControlOperation operation = parseOperation(code, state);
        switch (operation) {
            case EXIT:
                return;
            default:
                throw new IllegalStateException(operation.toString());
        }
    }

    private FlowControlOperation parseOperation(String code, InterpreterState state) {
        int cursor = state.getCursor();
        state.incrementCursor(2);
        return FlowControlOperation.byCode(code.substring(cursor, cursor + 2))
                .orElseThrow(() -> new IllegalStateException("Cannot parse expression"));
    }
}