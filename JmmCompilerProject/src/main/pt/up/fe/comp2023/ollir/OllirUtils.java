package pt.up.fe.comp2023.ollir;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;

import java.util.Arrays;

public class OllirUtils {

    public static String getCode(Symbol symbol) {
        return symbol.getName() + getOllirType(symbol.getType());
    }

    public static String getOllirType(Type type) {
        StringBuilder code = new StringBuilder();
        code.append(".");
        
        if(type != null) {
            if (type.isArray())
                code.append("array.");

            String jmmType = type.getName();

            switch (jmmType) {
                case "int" -> code.append("i32");
                case "boolean" -> code.append("bool");
                case "void" -> code.append("V");
                default -> code.append(jmmType);
            }
        }

        return code.toString();
    }

    public static String getOllirTypeSymbol(Symbol symbol) {
        StringBuilder code = new StringBuilder();
        code.append(".");

        if (symbol.getType().isArray())
            code.append("array.");

        String jmmType = symbol.getType().getName();

        switch (jmmType) {
            case "int" -> code.append("i32");
            case "boolean" -> code.append("bool");
            case "void" -> code.append("V");
            default -> code.append(jmmType);
        }

        return code.toString();
    }

    public static String getOllirIdWithoutParamNum(String id) {
        String[] idSplit = id.split("\\.");
        int n = id.charAt(0) == '$' ? 2 : 1;

        return String.join(".", Arrays.copyOfRange(idSplit, n, idSplit.length));
    }
}
