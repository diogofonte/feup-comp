package pt.up.fe.comp2023.ollir;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.Semantic.SymbolTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OllirGenerator extends AJmmVisitor<InferenceCalculator, String> {
    static int varCounter;
    private final ExprToOllir exprCode;
    static int markerCounter;
    private final StringBuilder codeBuilder;
    private final SymbolTable symbolTable;
    public static int tempVarCounter;
    private String scope;
    private Boolean optimize;
    private boolean isMain;

    @Override
    protected void buildVisitor() {
        tempVarCounter = 0;

        addVisit("Program", this::dealWithProgram);
        addVisit("ImportDeclaration", this::dealWithImport);
        addVisit("ClassDeclaration", this::dealWithClass);
        addVisit("VarDeclaration", this::dealWithVarDeclaration);
        addVisit("MainMethodDeclaration", this::dealWithMain);
        addVisit("OtherMethodDeclaration", this::dealWithOtherMethod);
        addVisit("ExpressionStatement", this::dealWithExpressionStatement);
        addVisit("IfStatement", this::dealWithIfStatement);
        addVisit("WhileStatement", this::dealWithWhileStatement);
        addVisit("BracketStatement", this::dealWithBracketStatement);
        addVisit("Assignment", this::dealWithAssignment);
        addVisit("ArrayAssignment", this::dealWithArrayAssignment);
    }

    public OllirGenerator(SymbolTable symbolTable, boolean optimize) {
        this.varCounter = 0;
        this.codeBuilder = new StringBuilder();
        this.symbolTable = symbolTable;
        this.exprCode = new ExprToOllir(symbolTable);
        this.optimize = optimize;

        buildVisitor();
    }

    public String getCode() {
        return codeBuilder.toString();
    }

    private String dealWithProgram(JmmNode program, InferenceCalculator inference) {
        InferenceCalculator inferenceCalculator = new InferenceCalculator("", false, "");
        for (var child : program.getChildren()) {
            codeBuilder.append(visit(child, inferenceCalculator));
        }

        return codeBuilder.toString();
    }

    private String dealWithImport(JmmNode importDeclar, InferenceCalculator inference) {
        StringBuilder importStr = new StringBuilder();

        importStr.append("import ");
        importStr.append(importDeclar.get("value"));

        for (var importId : importDeclar.getChildren()) {
            if (importId.getKind().equals("ImportAppend")) {
                importStr.append(".").append(importId.get("value1"));
            }
        }

        importStr.append(";\n");

        return importStr.toString();
    }

    private String dealWithClass(JmmNode classDeclar, InferenceCalculator inference) {
        StringBuilder classString = new StringBuilder();

        classString.append(symbolTable.getClassName());

        String superClass = symbolTable.getSuper();
        if (superClass != null)
            classString.append(" extends ").append(superClass);

        classString.append(" {\n");

        // Fields
        for (Symbol field : symbolTable.getFields()) {
            classString.append("\t")
                    .append(".field private ").append(field.getName())
                    .append(OllirUtils.getOllirType(field.getType()))
                    .append(";\n");
        }

        // Constructor
        classString.append(".construct ")
                .append(symbolTable.getClassName())
                .append("().V{\n").append("\tinvokespecial(this, \"<init>\").V;\n}\n");

        for (var child : classDeclar.getChildren()) {
            var childKind = child.getKind();
            if (childKind.equals("VarDeclaration") || childKind.equals("MainMethodDeclaration") ||
                    childKind.equals("OtherMethodDeclaration")) {
                classString.append(visit(child, inference));
            }
        }

        classString.append("}\n");

        return classString.toString();
    }

    private String dealWithVarDeclaration(JmmNode varDecl, InferenceCalculator inference) {
        StringBuilder varStr = new StringBuilder();
        JmmNode parent = varDecl.getJmmParent();

        if (parent.getKind().equals("MainMethodDeclaration")) {
            for (var localVar : symbolTable.getLocalVariables("main")) {
                varStr.append(OllirUtils.getOllirTypeSymbol(localVar));
            }
        }

        return varStr.toString();
    }

    public static String getMethodName(JmmNode nodeId){
        while(!nodeId.getKind().equals("OtherMethodDeclaration") && !nodeId.getKind().equals("MainMethodDeclaration")
                && !nodeId.getKind().equals("program")){
            nodeId = nodeId.getJmmParent();
        }

        if (nodeId.getKind().equals("program")) return "";
        else {
            if (nodeId.getKind().equals("MainMethodDeclaration")) return "main";
            else return nodeId.get("name");
        }
    }

    private String dealWithMain(JmmNode mainDeclar, InferenceCalculator inference) {
        StringBuilder mainMethodString = new StringBuilder();
        scope = "main";
        //InferenceCalculator inferenceCalculator = new InferenceCalculator("",false,scope);
        inference.setScope(scope);

        mainMethodString.append(".method public static main(");
        List<Symbol> params = symbolTable.getParameters(scope);

        String paramCode = params.stream().map(symbol -> OllirUtils.getCode(symbol)).collect(Collectors.joining(", "));

        for (int i = 1; i <= params.size() + 1; i++)
            paramCode.replace("$" + i + ".", "");
        mainMethodString.append(paramCode);

        mainMethodString.append(")");

        mainMethodString.append(OllirUtils.getOllirType(symbolTable.getReturnType("main")));
        mainMethodString.append(" {\n");

        isMain = true;
        for (var stmt : mainDeclar.getChildren()) {
            if (stmt.getHierarchy().contains("Statement")) {
                mainMethodString.append(visit(stmt, inference));
            }
        }

        if (!mainMethodString.toString().contains("ret."))
            mainMethodString.append("\t").append("ret");

        mainMethodString.append(OllirUtils.getOllirType(symbolTable.getReturnType("main"))).append(";");

        mainMethodString.append("\n");
        mainMethodString.append("}\n");

        return mainMethodString.toString();
    }

    private String dealWithOtherMethod(JmmNode methodDeclar, InferenceCalculator inference) {
        StringBuilder methodString = new StringBuilder();
        scope = methodDeclar.get("name");
        //InferenceCalculator inferenceCalculator = new InferenceCalculator("", false, scope);
        inference.setScope(scope);

        methodString.append(".method public ");
        methodString.append(this.scope);
        methodString.append("(");

        List<Symbol> parameters = symbolTable.getParameters(methodDeclar.get("name"));
        String paramCode = parameters.stream().map(OllirUtils::getCode).collect(Collectors.joining(", "));

        for (int i = 1; i <= parameters.size() + 1; i++) {
            paramCode.replace("$" + i + ".", "");
        }
        methodString.append(paramCode);
        methodString.append(")");

        methodString.append(OllirUtils.getOllirType(symbolTable.getReturnType(methodDeclar.get("name"))))
                .append(" {\n");

        this.isMain = false;
        for (var child : methodDeclar.getChildren()) {
            if (child.getHierarchy().contains("Statement")) {
                methodString.append(visit(child, inference));
            }
        }

        JmmNode lastChild = methodDeclar.getJmmChild(methodDeclar.getNumChildren() - 1);

        /*if(!lastChild.getKind().equals("BinaryOp")) {
            String op = evisit(lastChild, inference);
            methodString.append("\t").append("ret")
                    .append(OllirUtils.getOllirType(symbolTable.getReturnType(methodDeclar.get("name"))))
                    .append(" ").append(op);
        }*/
        var op = exprCode.visit(lastChild, inference);
        tempVarCounter = exprCode.getVarCounter();


        if(!op.prefixCode().equals("")) {
            methodString.append("\t").append(op.prefixCode()).append(";\n");
        }

        //methodString.append("\t").append(op.prefixCode());
        methodString.append("\tret")
                .append(OllirUtils.getOllirType(symbolTable.getReturnType(methodDeclar.get("name"))))
                .append(" ").append(op.value());

        if(symbolTable.isParam(methodDeclar.get("name"), op.value()) != -1) {
            methodString.append(OllirUtils.getOllirType(symbolTable.getReturnType(methodDeclar.get("name"))));
        }
        //.append(OllirUtils.getOllirType(symbolTable.getReturnType(methodDeclar.get("name"))));


        methodString.append(";\n");
        methodString.append("}\n");

        return methodString.toString();
    }

    private String dealWithExpressionStatement(JmmNode expressionstm, InferenceCalculator inference) {
        StringBuilder stringBuilder = new StringBuilder();
        for (JmmNode expression : expressionstm.getChildren()) {
            ExprCodeResult code =  exprCode.visit(expression, inference);
            tempVarCounter = exprCode.getVarCounter();
            stringBuilder.append(code.prefixCode());
            stringBuilder.append(code.value());
        }

        return stringBuilder.toString();
    }

    private String dealWithArrayAssignment(JmmNode node, InferenceCalculator inference) {
        var lhsCode = node.get("value");
        var type = ".i32";
        this.exprCode.ChangeType(type);

        JmmNode inarray = node.getJmmChild(0);
        String index = exprCode.visit(inarray).value();
        tempVarCounter = exprCode.getVarCounter();

        if (symbolTable.isField(scope, node.get("value"))) {
            String child = visit(inarray, new InferenceCalculator(type, true, scope));

            return "\t" + "putfield(this, " + node.get("value") + type + ", " + child + ").V;\n";
        }

        var code = new StringBuilder();

        JmmNode child = node.getJmmChild(1);
        if (child.getKind().equals("Identifier")) {
            if (symbolTable.isField(scope, child.get("value"))) {
                String operation = " getfield(this, " + child.get("value") + type + ")" + type;

                if (inference != null ) {
                    tempVarCounter++;
                    exprCode.setCounter(tempVarCounter);

                    return "\t" + "t" + tempVarCounter + type + " :=" + type + " " + operation + ";\n";
                }

                return "\t" + lhsCode + "[" + index + ".i32]" + type + " :=" + type + operation  + ";\n";
            }
        }

        if (child.getKind().equals("MethodCall")) {
            code.append(exprCode.visit(child));
            tempVarCounter = exprCode.getVarCounter();
        } else {
            var rhsCode2 = exprCode.visit(child, inference);
            tempVarCounter = exprCode.getVarCounter();
            String temp = "t" + tempVarCounter++ + ".i32";
            exprCode.setCounter(tempVarCounter);
            if(inarray.getKind().equals("Integer")) {
                String tempAss = temp + " :=.i32 " + index + ";\n";
                code.append("\t").append(tempAss);
                code.append(rhsCode2.prefixCode())
                        .append("\t").append(lhsCode).append("[").append(temp).append("]").append(type).append(" :=").append(type).append(" ")
                        .append(rhsCode2.value());
            } else {
                code.append(rhsCode2.prefixCode())
                        .append("\t").append(lhsCode).append("[").append(index).append("]").append(type).append(" :=").append(type).append(" ")
                        .append(rhsCode2.value());
            }

            if(!rhsCode2.value().contains("new")) {
                code.append(";");
            }

            code.append("\n");
        }

        return code.toString();
    }

    private String dealWithIfStatement(JmmNode node, InferenceCalculator inference) {
        StringBuilder code = new StringBuilder();

        ExprCodeResult child0 = exprCode.visit(node.getJmmChild(0), new InferenceCalculator("", true, scope));
        tempVarCounter = exprCode.getVarCounter();
        StringBuilder temp = new StringBuilder(child0.prefixCode());
        //temp.replace(4,7,"bool");
        code.append(temp);

        String condition = child0.value().replace(".i32", ".bool");
        String ifLabel = "if_then_" + markerCounter;
        String endLabel = "if_end_" + markerCounter;
        markerCounter++;

        String elseBlock = visit(node.getJmmChild(2), new InferenceCalculator("", false, scope));
        code.append("\tif (").append(condition).append(") goto ").append(ifLabel).append(";\n");
        code.append("\t").append(elseBlock);
        code.append("\t\tgoto ").append(endLabel).append(";\n");

        code.append(ifLabel).append(":\n");
        String ifBlock = visit(node.getJmmChild(1), new InferenceCalculator("", false, scope));
        code.append(ifBlock);
        code.append(endLabel).append(":\n");

        return code.toString();
    }

    private String dealWithWhileStatement(JmmNode node, InferenceCalculator inference) {
        StringBuilder code = new StringBuilder();

        ExprCodeResult child0 = exprCode.visit(node.getJmmChild(0), new InferenceCalculator("", false, scope));
        tempVarCounter = exprCode.getVarCounter();
        String condition = child0.value();
        String endLabel = "out_loop_" + markerCounter;
        String loopLabel = "loop_" + markerCounter;
        markerCounter++;

        code.append(loopLabel).append(":\n");
        code.append(child0.prefixCode());
        code.append("\tif (!.bool ").append(condition).append(") goto ").append(endLabel).append(";\n");

        ExprCodeResult child1 = exprCode.visit(node.getJmmChild(1), new InferenceCalculator("", false, scope));
        tempVarCounter = exprCode.getVarCounter();
        code.append(child1.prefixCode());
        code.append(child1.value());
        code.append("\tgoto ").append(loopLabel).append(";\n");
        code.append(endLabel).append(":\n");

        return code.toString();
    }

    private String dealWithBracketStatement(JmmNode node, InferenceCalculator inference) {
        StringBuilder code = new StringBuilder();
        String stat;

        for(JmmNode st : node.getChildren()) {
            if(st.getKind().equals("Statement") || st.getKind().equals("Assignment")) {
                stat = visit(st, new InferenceCalculator("", false, scope));
                code.append(stat);
            } else {
                ExprCodeResult visit = exprCode.visit(st, new InferenceCalculator("", false, scope));
                stat = visit.value();
                tempVarCounter = exprCode.getVarCounter();
                code.append(visit.prefixCode()); // alterado
                code.append(stat);
            }
        }

        return code.toString();
    }

    public String dealWithAssignment(JmmNode node, InferenceCalculator inference) {
        var lhsCode = node.get("value");
        var type = OllirUtils.getOllirType(symbolTable.getVarRealTypeByID(lhsCode, this.scope));
        this.exprCode.ChangeType(type);
        JmmNode child = node.getJmmChild(0); //expression

        if (symbolTable.isField(scope, lhsCode)) {
            String result = exprCode.visit(child, new InferenceCalculator(type, true, scope)).value();
            tempVarCounter = exprCode.getVarCounter();

            return "\t" + "putfield(this, " + node.get("value") + type + ", " + result + ").V;\n";
        }

        var code = new StringBuilder();

        if (child.getKind().equals("Identifier")) {
            if (symbolTable.isField(scope, child.get("value"))) {
                String operation = " getfield(this, " + child.get("value") + type + ")" + type;

                if (inference != null ) {
                    tempVarCounter++;
                    exprCode.setCounter(tempVarCounter);
                    return "\t" + "t" + tempVarCounter + type + " :=" + type + " " + operation + ";\n";
                }

                return "\t" + lhsCode + type + " :=" + type + operation  + ";\n";
            }
        }

        var rhsCode = exprCode.visit(child, inference);
        tempVarCounter = exprCode.getVarCounter();

        if (child.getKind().equals("MethodCall")) {
            code.append(rhsCode.prefixCode());
            code.append(rhsCode.value() + "\n");
        } else {
            code.append(rhsCode.prefixCode())
                    .append("\t").append(lhsCode).append(type).append(" :=").append(type).append(" ")
                    .append(rhsCode.value());

            if (!child.getKind().equals("ObjectCreation")) {
                code.append(";\n");
            }
        }

        return code.toString();
    }
}
