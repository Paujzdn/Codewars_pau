package kata.kyu2.whitespace;

import kata.kyu2.whitespace.operations.IMP;
import kata.kyu2.whitespace.operations.InterpreterState;
import kata.kyu2.whitespace.tools.InterpreterFactory;


import java.io.InputStream;
import java.util.Optional;

public class WhitespaceInterpreter {

    public static String execute(String code, InputStream input) {
        if (code == null || code.isEmpty()) {
            throw new IllegalStateException("Code empty");
        }

        return interpretCommand(prepareCode(code), new InterpreterState(0), input);
    }

    static String interpretCommand(String code, InterpreterState state, InputStream input) {
        if (state.getCursor() >= code.length()) {
            return state.getResult().orElse("");
        }
        IMP imp = parseImpCommand(code, state);

        InterpreterFactory.getInterpreter(imp)
                .interpret(code, state, input);

        return interpretCommand(code, state, input);
    }

    static IMP parseImpCommand(String code, InterpreterState state) {
        int cursor = state.getCursor();
        Optional<IMP> impOptional = IMP.byCode(code.substring(cursor, cursor + 1));
        if (impOptional.isPresent()) {
            state.incrementCursor(1);
            return impOptional.get();
        }
        state.incrementCursor(2);
        return IMP.byCode(code.substring(cursor, cursor + 2))
                .orElseThrow(() -> new IllegalStateException("Cannot parse expression"));
    }

    static String prepareCode(String code) {
        return unbleach(removeComments(code));
    }

    // transforms space characters to ['s','t','n'] chars;
    static String unbleach(String code) {
        return code != null ? code.replace(' ', 's').replace('\t', 't').replace('\n', 'n') : null;
    }

    static String removeComments(String code) {
        return code != null ? code.replaceAll("[^\\t\\s\\n]", "") : null;
    }

}