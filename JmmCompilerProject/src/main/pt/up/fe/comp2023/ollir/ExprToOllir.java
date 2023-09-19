package pt.up.fe.comp2023.ollir;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.PreorderJmmVisitor;
import pt.up.fe.comp2023.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class ExprToOllir extends PreorderJmmVisitor<InferenceCalculator, ExprCodeResult> {
    public static boolean isAssign;
    private int counter;
    private String type;
    private final SymbolTable symbolTable;

    public ExprToOllir(SymbolTable symbolTable) {
        this.counter = 0;
        this.symbolTable = symbolTable;
    }

    public void setCounter(int n) {
        counter = n;
    }

    @Override
    protected void buildVisitor() {
        addVisit("ObjectCreation", this::visitObjectCreation);
        addVisit("MethodCall", this::visitMethodCall);
        addVisit("VarDecl", this::visitVarDecl);
        addVisit("BinaryOp", this::visitBinaryExpression);
        addVisit("Length", this::visitLength);
        addVisit("ArrayCreation", this::visitArrayCreation);
        addVisit("ArrayAccess", this::visitArrayAccess);
        addVisit("RelationalOp", this::visitRelationalOp);
        addVisit("LogicalAnd", this::visitLogicalAnd);
        addVisit("LogicalOr", this::visitLogicalOr);
        addVisit("True", this::visitBooleanTrue);
        addVisit("False", this::visitBooleanFalse);
        addVisit("Integer", this::visitInteger);
        addVisit("Identifier", this::visitID);
        setDefaultValue(() -> new ExprCodeResult("",""));
    }

    private String newTempVar() {
        var newVar = "t" + counter;
        counter++;
        return newVar;
    }

    public int getVarCounter(){
        return counter;
    }

    public void ChangeType(String type) {
        this.type = type;
    }


    public ExprCodeResult visitObjectCreation(JmmNode node, InferenceCalculator inference) {
        StringBuilder code = new StringBuilder();
        JmmNode parent = node.getJmmParent();

        String str = node.get("value");
        if (str != null && str.length() > 0) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        code.append("new(").append(str).append(")").append(".").append(str).append(";\n");
        code.append("\tinvokespecial(").append(parent.get("value")).append(".").append(str);
        code.append(",\"<init>\").V;\n");

        return new ExprCodeResult("", code.toString());
    }

    private ExprCodeResult visitMethodCall(JmmNode node, InferenceCalculator inference) {
        String firstChildKind = node.getJmmChild(0).getKind();
        String firstArg;

        if (firstChildKind.equals("This")) {
            firstArg = "this";

        } else if (firstChildKind.equals("ArrayCreation") || firstChildKind.equals("ObjectCreation")) {
            firstArg = visit(node.getJmmChild(0), inference).value();

        } else {
            firstArg = node.getJmmChild(0).get("value");
            var ollirType = OllirUtils.getOllirType(symbolTable.getVarRealTypeByID(firstArg, inference.getScope()));

            if (!ollirType.equals(".")){
                firstArg += ollirType;
            }
        }

        String invokeType = symbolTable.getInvokeType(firstArg);

        String method = node.get("value");
        String returnType;

        if (!symbolTable.checkObjectIsImported(firstArg)) {
            if (inference == null || inference.getInferredType() == null) {
                String type;

                if (firstArg.equals("this")) {
                    type = symbolTable.getClassName();

                } else if (symbolTable.isExternalClass(OllirGenerator.getMethodName(node), firstArg)) {
                    type = firstArg;

                } else {
                    type = OllirUtils.getOllirIdWithoutParamNum(firstArg);
                }

                if (type.equals(symbolTable.getClassName())) {
                    Type retType = symbolTable.getReturnType(method);

                    if (retType == null) {
                        returnType = ".V";
                    } else {
                        returnType = OllirUtils.getOllirType(retType);
                    }

                } else {
                    returnType = ".V";
                }

            } else {
                returnType = inference.getInferredType();
            }

        } else {
            returnType = ".V";
        }

        int iArg = 0;
        List<String> args = new ArrayList<>();
        for (JmmNode arg : node.getJmmChild(0).getChildren()) {
            if (method != null) {
                if (symbolTable.getParameters(method) != null) {
                    Symbol paramSymbol = symbolTable.getParameters(method).get(iArg);
                    if (paramSymbol != null) {
                        String inferredArgType = OllirUtils.getOllirType(paramSymbol.getType());
                        assert inference != null;
                        args.add(visit(arg.getJmmChild(0), new InferenceCalculator(inferredArgType, true, inference.getScope())).value());
                        iArg += 1;
                        continue;
                    }
                }
            }
            args.add(visit(arg.getJmmChild(0), new InferenceCalculator(true,inference.getScope())).value());
            iArg += 1;
        }

        StringBuilder operation = new StringBuilder();

        for (int j = 1 ; j < node.getNumChildren() ; j++) {
            JmmNode methodArg = node.getJmmChild(j);
            if (methodArg.getKind().equals("BinaryOp")) {
                var op = visit(methodArg, inference);
                operation.append(op.prefixCode());
                args.add(op.value());
            } else if (methodArg.getKind().equals("Length")) {
                var op = visit(methodArg, inference);
                operation.append(op.prefixCode());
                args.add(op.value());
            } else if (methodArg.getKind().equals("ArrayAccess")) {
                var op = visit(methodArg, inference);
                operation.append(op.value());
                args.add(op.prefixCode());
            } else {
                String op = visit(methodArg, inference).value();
                args.add(op);
            }
        }

        operation.append("\t").append(invokeType).append("(").append(firstArg).append(", \"").append(method).append("\"");
        for (String arg : args) {
            String argType = symbolTable.getVarTypeByID(arg,inference.getScope());
            if(!arg.equals("")) {
                if (arg.contains(".")) {
                    operation.append(", ").append(arg).append(argType);
                }else {
                    operation.append(", ").append(arg).append(".").append(argType);
                }
            }
        }
        operation.append(")");

        if(symbolTable.getReturnType(method) != null) {
            Type type = symbolTable.getReturnType(method);
            returnType = OllirUtils.getOllirType(type);
            System.out.println(returnType);
        }

        StringBuilder code = new StringBuilder();
        String result = operation.toString();

        if (inference == null || inference.getIsToAssignToTemp()) {
            code.append("\t");

            if (node.getJmmParent().getKind().equals(("Assignment"))) {
                code.append(node.getJmmParent().get("value")).append(returnType)
                        .append(" :=").append(returnType)
                        .append(" ");
            }
            code.append(operation).append(returnType).append(";\n");

            return new ExprCodeResult("", code.toString());
        } else if (returnType.equals("") || returnType.equals(".V")) {
            operation.append(".V").append(";\n");
            result = operation.toString();
        } else {
            operation.append(returnType).append(";\n");
            result = operation.toString();
        }

        return new ExprCodeResult("", result);
    }

   public ExprCodeResult visitArrayCreation(JmmNode node, InferenceCalculator inference) {
       JmmNode child = node.getJmmChild(0);
       ExprCodeResult lhsCode = visit(child, inference);

       StringBuilder code = new StringBuilder();
       String tempVarName = newTempVar() + ".i32";

       String tempVar = "\t" + tempVarName + " " + ":=.i32 " + lhsCode.value() + ";\n";

       code.append("new(array, " + tempVarName + ").array.i32");

       return new ExprCodeResult(tempVar, code.toString());
    }

    public ExprCodeResult visitArrayAccess(JmmNode node, InferenceCalculator inference) {
        StringBuilder code = new StringBuilder();

        JmmNode identifier = node.getJmmChild(0);
        JmmNode indexNode = node.getJmmChild(1);

        ExprCodeResult id = visit(identifier, inference);
        ExprCodeResult index = visit(indexNode, inference);

        String idStr = id.value();
        String indexStr = index.value();
        String prefixOp = index.prefixCode();

        String type = getVarType(identifier.get("value"), identifier).replace(".array", "");
        System.out.println(type);

        if (idStr.contains("\n")) {
            code.append(idStr.substring(0, idStr.lastIndexOf("\n") + 1));
            idStr = code.substring(idStr.lastIndexOf("\n") + 1);
            if (idStr.contains(".")) idStr = idStr.substring(0, idStr.indexOf("."));
        }


        if (indexNode.getKind().equals("Integer")) {
            code.append("t").append(++counter).append(".i32 :=.i32 ").append(indexStr).append(".i32;\n");
            indexStr = ("t" + counter);
        }

        if(indexNode.getKind().equals("ArrayAccess")) {
            code.append(indexStr);
        }

        if (indexNode.getKind().equals("Identifier")) {
            code.append("\tt").append(++counter).append(".i32 :=.i32 ").append(indexStr).append(";\n");
            indexStr = ("t" + counter);
        }

        if(indexNode.getKind().equals("MethodCall")) {
            code.append("t").append(++counter).append(".i32 :=.i32").append(indexStr).append(".i32;\n");
            indexStr = ("t" + counter);
        }


        if (indexStr.contains("$")){
            code.append("t").append(++counter).append(".i32 :=.i32 ").append(indexStr).append(type).append(";\n");
            indexStr = ("t" + counter);
        }

        if(indexNode.getKind().equals("BinaryOp") || indexNode.getKind().equals("RelationalOp")) {
            code.append(prefixOp);
        }

        if (indexNode.getKind().equals("LogicalAnd") || indexNode.getKind().equals("LogicalOr")){
            code.append("t").append(++counter).append(".i32 :=.i32 ").append(indexStr).append(";\n");
            indexStr = ("t" + counter);
        }

        if (indexStr.contains(".")) indexStr = indexStr.substring(0, indexStr.lastIndexOf("."));

        code.append("\t\tt").append(++counter).append(type);
        code.append(" :=").append(type).append(" ");

        if(!indexNode.getKind().equals("ArrayAccess")) {
            code.append(identifier.get("value")).append("[").append(indexStr).append(".i32").append("]").append(type);
            code.append(";\n");
        } else {
            code.append(identifier.get("value")).append("[").append(prefixOp).append("]").append(type);
            code.append(";\n");
        }


        String arg = "t" + counter + type;

        return new ExprCodeResult(arg, code.toString());
    }


   public ExprCodeResult visitVarDecl(JmmNode node, InferenceCalculator inference) {
        return new ExprCodeResult("", node.get("value"));
   }


   public ExprCodeResult visitBinaryExpression(JmmNode node, InferenceCalculator inference) {
        /* PENSO QUE ISTO AGORA VAI TER QUE PERGUNTAR PELO TYPE PARA DAR CHANGE*/
        ChangeType(".i32");
        var lhsCode = visit(node.getJmmChild(0), inference);
        var rhsCode = visit(node.getJmmChild(1), inference);

        var code = new StringBuilder();
        code.append(lhsCode.prefixCode());
        code.append(rhsCode.prefixCode());

        var value = newTempVar();
        value = value + type;

        if(node.getJmmParent().getKind().equals("OtherMethodDeclaration")) {
            code.append(value + " " + ":=" + type + " " + lhsCode.value() + " "
                    + node.get("op") + type +  " " + rhsCode.value());
        }
        else {
            code.append("\t" + value + " " + ":=" + type + " " + lhsCode.value() + " "
                    + node.get("op") + type +  " " + rhsCode.value()).append(";\n");
        }

        return new ExprCodeResult(code.toString(), value);
    }

    public ExprCodeResult visitLength(JmmNode node, InferenceCalculator inference) {
        JmmNode childL = node.getJmmChild(0);
        var lhsCode = visit(childL, inference);
        String name = lhsCode.value();

        var code = new StringBuilder();
        var value = newTempVar();
        value = value + ".i32";

        code.append(lhsCode.prefixCode());
        code.append("\t" + value + " " + ":=.i32 ").append("arraylength(" +name + ").i32;\n");

        return new ExprCodeResult(code.toString(), value);
    }

   private ExprCodeResult visitRelationalOp(JmmNode node, InferenceCalculator inference) {
        ChangeType(".bool");

        JmmNode childL = node.getJmmChild(0);
        JmmNode childR = node.getJmmChild(1);

        var lhsCode = visit(childL, inference);
        var rhsCode = visit(childR, inference);

        var code = new StringBuilder();
        code.append(lhsCode.prefixCode());
        code.append(rhsCode.prefixCode());

        var value = newTempVar();
        value = value + ".i32";

        if(node.getJmmParent().getKind().equals("OtherMethodDeclaration")) {
            code.append(value + " " + ":=" + type + " " + lhsCode.value() + " "
                    + node.get("op") + type +  " " + rhsCode.value());
        } else {
            code.append("\t" + value + " " + ":=" + type + " " + lhsCode.value() + " "
                    + node.get("op") + type +  " " + rhsCode.value()).append(";\n");
        }

        return new ExprCodeResult(code.toString(), value);
   }

    private ExprCodeResult visitLogicalAnd(JmmNode node, InferenceCalculator inference) {
        ChangeType(".bool");

        JmmNode childL = node.getJmmChild(0);
        JmmNode childR = node.getJmmChild(1);

        var lhsCode = visit(childL, inference);
        var rhsCode = visit(childR, inference);

        var code = new StringBuilder();
        code.append(lhsCode.prefixCode());
        code.append(rhsCode.prefixCode());

        String value;
        if((childL.getKind().equals("True") || childL.getKind().equals("False")) &&
                (childR.getKind().equals("True") || childR.getKind().equals("False"))) {

            value = lhsCode.value() + " " + "&&" + type +  " " + rhsCode.value();
        } else {
            value = newTempVar();
            value = value + type;

            if(node.getJmmParent().getKind().equals("OtherMethodDeclaration")) {
                code.append(value + " " + ":=" + type + " " + lhsCode.value() + " "
                        + "&&" + type +  " " + rhsCode.value());
            } else {
                code.append("\t" + value + " " + ":=" + type + " " + lhsCode.value() + " "
                        + "&&" + type +  " " + rhsCode.value()).append(";\n");
            }
        }


        return new ExprCodeResult(code.toString(), value);
    }

    private ExprCodeResult visitLogicalOr(JmmNode node, InferenceCalculator inference) {
        ChangeType(".bool");

        JmmNode childL = node.getJmmChild(0);
        JmmNode childR = node.getJmmChild(1);

        var lhsCode = visit(childL, inference);
        var rhsCode = visit(childR, inference);

        var code = new StringBuilder();
        code.append(lhsCode.prefixCode());
        code.append(rhsCode.prefixCode());

        String value;
        if((childL.getKind().equals("True") || childL.getKind().equals("False")) &&
                (childR.getKind().equals("True") || childR.getKind().equals("False"))) {
            value = lhsCode.value() + " " + "||" + type +  " " + rhsCode.value();
        } else {
            value = newTempVar();
            value = value + type;

            if(node.getJmmParent().getKind().equals("OtherMethodDeclaration")) {
                code.append(value + " " + ":=" + type + " " + lhsCode.value() + " "
                        + "||" + type +  " " + rhsCode.value());
            } else {
                code.append("\t" + value + " " + ":=" + type + " " + lhsCode.value() + " "
                        + "||" + type +  " " + rhsCode.value()).append(";\n");
            }
        }

        return new ExprCodeResult(code.toString(), value);
    }

   public ExprCodeResult visitBooleanTrue(JmmNode node, InferenceCalculator inference) {
        return new ExprCodeResult("", "1.bool");
   }


   public ExprCodeResult visitBooleanFalse(JmmNode node, InferenceCalculator inference) {
        return new ExprCodeResult("", "0.bool");
   }

   public ExprCodeResult visitInteger(JmmNode node, InferenceCalculator inference) {
        return new ExprCodeResult("", node.get("value") + ".i32");
   }

    private ExprCodeResult visitID(JmmNode id, InferenceCalculator inference) {
        String varType = symbolTable.getVarTypeByID(id.get("value"), inference.getScope());
        Type realVarType = symbolTable.getVarRealTypeByID(id.get("value"), inference.getScope());

        if (symbolTable.isField(inference.getScope(), varType)) {
            String operation = "getfield(this, " + id.get("value") + varType + ")" + varType;

            if (inference == null || inference.getIsToAssignToTemp()) {
                OllirGenerator.tempVarCounter++;
                String result = "\t" + "t" + OllirGenerator.tempVarCounter + varType + " :=" + varType + " " + operation + ";\n";
                return new ExprCodeResult("", result);
            }
        }

        StringBuilder code = new StringBuilder();
        code.append(id.get("value"));
        String paramType = "";

        switch (varType) {
            case "boolean" -> {
                code.append(".bool");
                paramType = ".bool";
            }
            case "int" -> {
                if (realVarType.isArray()) {
                    code.append(".array");
                    paramType = ".array";
                }
                code.append(".i32");
                paramType += ".i32";
            }
            default -> {
                code.append(OllirUtils.getOllirType(symbolTable.getReturnType(id.get("value"))));
                paramType = OllirUtils.getOllirType(symbolTable.getReturnType(id.get("value")));
            }
        }

        /*
        switch (varType) {
            case "boolean" -> {
                code.append(".bool");
                paramType = ".bool"
                return new ExprCodeResult("", code.toString());
            }
            case "int" -> {
                if (realVarType.isArray()) {
                    code.append(".array.i32");
                    return new ExprCodeResult("", code.toString());
                }
                code.append(".i32");
                return new ExprCodeResult("", code.toString());
            }
            default -> {
                code.append(OllirUtils.getOllirType(symbolTable.getReturnType(id.get("value"))));
            }
        }
         */

        int paramNum = symbolTable.isParam(inference.getScope(), id.get("value"));
        if(paramNum != -1){
            return new ExprCodeResult("", "$" + Integer.toString(paramNum)+ "." + id.get("value") + paramType);
        }

        return new ExprCodeResult("", code.toString());

        //code.append(varType);
        //return new ExprCodeResult("", code.toString());
    }

    public String methodName(JmmNode nodeId){
        while(!nodeId.getKind().equals("MethodDeclaration") && !nodeId.getKind().equals("Program")){
            nodeId = nodeId.getJmmParent();
        }

        if (nodeId.getKind().equals("Program")) return "";
        else {
            if (nodeId.getJmmChild(0).getKind().equals("MainMethodDeclaration")) return "main";
            else return nodeId.getJmmChild(0).getJmmChild(1).get("name");
        }
    }

    private String getVarType(String name, JmmNode id) {
        String methodName = methodName(id);

        for (Symbol symbol : symbolTable.getFields())
            if (name.equals(symbol.getName()))
                return OllirUtils.getOllirType(symbol.getType());

        for (String method : symbolTable.getMethods()) {
            if (methodName.equals(method) || methodName.equals("")){
                for (Symbol symbol : symbolTable.getParameters(method))
                    if (name.equals(symbol.getName()))
                        return OllirUtils.getOllirType(symbol.getType());

                for (Symbol symbol : symbolTable.getLocalVariables(method))
                    if (name.equals(symbol.getName()))
                        return OllirUtils.getOllirType(symbol.getType());
            }
        }
        return ".i32";
    }
}