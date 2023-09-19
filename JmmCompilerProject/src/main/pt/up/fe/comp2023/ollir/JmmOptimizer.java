package pt.up.fe.comp2023.ollir;

import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ollir.JmmOptimization;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp2023.ollir.optimization.*;
import pt.up.fe.comp2023.ollir.optimization.visitors.*;

import java.util.Collections;

public class JmmOptimizer implements JmmOptimization {
    // JmmOptimizer
    public JmmSemanticsResult optimize(JmmSemanticsResult semanticsResult) {
        boolean optimize = semanticsResult.getConfig().get("optimize") != null
                && semanticsResult.getConfig().get("optimize").equals("true");

        if (!optimize) return semanticsResult;

        System.out.println("Performing optimizations...");

        // duplicate condition of while loops in order to check if they can be promoted to do-while loops at compile-time
        WhileDuplicatorVisitor whileDuplicatorVisitor = new WhileDuplicatorVisitor();
        whileDuplicatorVisitor.visit(semanticsResult.getRootNode());

        ConstFoldingVisitor foldingVisitor = new ConstFoldingVisitor();
        ConstPropagationVisitor propagationVisitor = new ConstPropagationVisitor(semanticsResult.getSymbolTable());
        do {
            foldingVisitor.resetUpdated();
            propagationVisitor.resetUpdated();
            foldingVisitor.visit(semanticsResult.getRootNode(), true);
            propagationVisitor.visit(semanticsResult.getRootNode(), true);
            System.out.println("Folding: " + foldingVisitor.getUpdated());
            System.out.println("Propagation: " + propagationVisitor.getUpdated());
        } while(foldingVisitor.getUpdated() || propagationVisitor.getUpdated() );

        // remove duplicated while condition and annotate with do-while true/false at compile-time
        DoWhileInsertVisitor doWhileInsertVisitor = new DoWhileInsertVisitor();
        doWhileInsertVisitor.visit(semanticsResult.getRootNode());
        System.out.println("Optimized AST : \n" + semanticsResult.getRootNode().toTree());

        return semanticsResult;
    }

    @Override
    public OllirResult toOllir(JmmSemanticsResult semanticsResult) {
        boolean optimize = semanticsResult.getConfig().get("optimize") != null
                && semanticsResult.getConfig().get("optimize").equals("true");

        SymbolTable symbolTable = semanticsResult.getSymbolTable();
        if(!(symbolTable instanceof pt.up.fe.comp2023.Semantic.SymbolTable st)) {
            throw new RuntimeException("Symbol Table type not the supported");
        }
        OllirGenerator ollirGenerator = new OllirGenerator(st, optimize);
        InferenceCalculator inference = new InferenceCalculator("",false,"");

        ollirGenerator.visit(semanticsResult.getRootNode(), inference);

        String ollirCode = ollirGenerator.getCode();

        ollirCode = ollirCode.replace(".i32;\n.i32;\n", ".i32;\n");
        ollirCode = ollirCode.replace(".i32.i32;\n", ".i32;\n");

        System.out.println(ollirCode);
        return new OllirResult(semanticsResult, ollirCode, Collections.emptyList());
    }

    @Override
    public OllirResult optimize(OllirResult ollirResult) {
        String localVariableAllocation = ollirResult.getConfig().get("registerAllocation");
        int localVariableNum;
        if (localVariableAllocation == null) {
            localVariableNum = -1;
        } else {
            localVariableNum = Integer.parseInt(localVariableAllocation);
        }

        if (localVariableNum > -1) {
            System.out.println("Register Allocation in progress...");
            LocalVariableOptimization optimization = new LocalVariableOptimization(ollirResult);
            optimization.optimize(localVariableNum);
        }

        return ollirResult;
    }
}