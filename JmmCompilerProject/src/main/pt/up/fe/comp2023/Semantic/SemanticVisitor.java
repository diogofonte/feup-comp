package pt.up.fe.comp2023.Semantic;

import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;

import java.util.List;
import java.util.Objects;

public class SemanticVisitor extends AJmmVisitor<List<Report>, Boolean> {

    private SymbolTable symbolTable;
    private List<Report> reports;

    private String currentScope;

    public List<Report> getReports() {
        return this.reports;
    }

    @Override
    protected void buildVisitor() {

        addVisit("Program", this::dealWithProgram);
        addVisit("ClassDeclaration", this::dealWithClass);
        addVisit("MainMethodDeclaration", this::dealWithMainMethod);
        addVisit("OtherMethodDeclaration", this::dealWithMethod);
        addVisit("Statement", this::dealWithStatement);
        addVisit("BinaryOp", this::dealWithBinaryOp);
        addVisit("RelationalOp",this::dealWithRelationalOp);
        addVisit("Parentheses",this::dealWithParentheses);
        addVisit("ArrayAccess",this::dealWithArrayAccess);
        addVisit("Assignment",this::dealWithAssignment);
        addVisit("Expression",this::dealWithExpression);
        addVisit("ArrayAssignment",this::dealWithArrayAssignment);
        addVisit("Length",this::dealWithLength);
        addVisit("IfStatement",this::dealWithIfStatement);
        addVisit("WhileStatement",this::dealWithWhileStatement);
        addVisit("VarDeclaration",this::dealWithVarDeclaration);
        addVisit("ObjectCreation",this::dealWithObjectCreation);
        addVisit("ArrayCreation",this::dealWithArrayCreation);
        addVisit("MethodCall",this::dealWithMethodCall);
        addVisit("LogicalAnd",this::dealWithLogic);
        addVisit("LogicalOr",this::dealWithLogic);


    }

    private Boolean dealWithLength(JmmNode jmmNode, List<Report> reports) {
        JmmNode varNode = jmmNode.getJmmChild(0);

        if(varNode.getKind().equals("Parentheses")){

            return visit(varNode,reports);
        }

        if(checkVarIsDeclared(varNode,currentScope)){
            return checkTypeArray(varNode,currentScope);
        }

        this.reports.add(
                new Report(
                        ReportType.ERROR,
                        Stage.SEMANTIC,
                        Integer.parseInt(jmmNode.get("lineStart")),
                        Integer.parseInt(jmmNode.get("colStart")),
                        "Incorrect use of length."
                ));
        return false;

    }

    private Boolean dealWithLogic(JmmNode jmmNode, List<Report> reports){
        JmmNode left_child = jmmNode.getJmmChild(0);
        JmmNode right_child = jmmNode.getJmmChild(1);
        boolean RHS_ok=false;
        boolean LHS_ok=false;

        if(left_child.getKind().equals("Parentheses")){

            LHS_ok=visit(left_child,reports);
        }

        if(right_child.getKind().equals("Parentheses")){

            RHS_ok=visit(right_child,reports);
        }

        if((checkTypeBoolean(left_child))){
            LHS_ok=true;
        }
        if((checkTypeBoolean(right_child))){
            RHS_ok =true;
        }

        if((!(RHS_ok) || !(LHS_ok))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Expected both expressions to be boolean ."
                    ));

            return false;
        }
        return true;
    }

    private Boolean dealWithExpression(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {
            if (child.getKind().equals("Parentheses")) {
                visit(child, reports);
            }

            if (child.getKind().equals("ArrayAccess")) {
                visit(child, reports);
            }

            if (child.getKind().equals("MethodCall")) {
                visit(child, reports);
            }

            if (child.getKind().equals("Length")) {
                visit(child, reports);
            }

            if (child.getKind().equals("UnaryNot")) {
                visit(child, reports);
            }

            if (child.getKind().equals("Length")) {
                visit(child, reports);
            }

            if (child.getKind().equals("BinaryOp")) {
                visit(child, reports);
            }

            if (child.getKind().equals("RelationalOp")) {
                visit(child, reports);
            }

            if (child.getKind().equals("ObjectCreation")) {
                visit(child, reports);
            }

        }
        return true;
    }


    public SemanticVisitor(SymbolTable symbolTable, List<Report> reports) {
        this.symbolTable = symbolTable;
        this.reports = reports;

        buildVisitor();
    }

    public Boolean dealWithProgram(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {
            if (child.getKind().equals("ClassDeclaration")) {
                visit(child, reports);
            }

        }
        return true;
    }

    private Boolean dealWithVarDeclaration(JmmNode jmmNode, List<Report> reports) {
        String varType= jmmNode.getJmmChild(0).getKind();
        String varID= jmmNode.get("value");

        switch (varType){
            case "TypeInt":
                break;
            case "TypeBoolean":
                break;
            case "TypeArray":
                break;
            case "TypeObject":
                if(!checkObjectDeclared(jmmNode.getJmmChild(0).get("value"))){
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Object must correspond to class name or import"
                            ));
                }
        }


        return true;
    }

    //visit will check if method call is ok
    private Boolean dealWithMethodCall(JmmNode jmmNode, List<Report> reports) {
        JmmNode object = jmmNode.getJmmChild(0);
        String methodName = jmmNode.get("value");

        String objectName;
        String objectType;


        if(object.getKind().equals("Parentheses")){
            return true;
        }
        if(object.getKind().equals("This")){
            objectType= symbolTable.getClassName();
        }
        else{
            objectName= object.get("value");
            objectType = symbolTable.getVarTypeByID(objectName,currentScope);

            //If the class is being imported, assume the types of the expression where it's used are correct.
            if(checkObjectIsImportedSolo(objectName)){
                return true;
            }
            if(checkObjectIsImportedSolo(objectType)){
                return true;
            }
        }


        //assume the method exists in one of the super classes, and that is being correctly called
        if((symbolTable.getSuper()!=null)) {

            if (checkObjectIsExtendSolo(objectType)) {

                return true;
            }
        }

        //assume the method exists in one of the super classes, and that is being correctly called
        if((symbolTable.getSuper()!=null)) {

            if (checkObjectIsExtendSolo(objectType)) {

                return true;
            }
        }

        //check method exists
        if(!(checkMethodExists(methodName))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Method " + methodName  + " doesn't exist"
                    ));
            return false;
        }

        //if method is expecting no parameters and none were given, end
        if (symbolTable.getParameters(methodName) == null) {
            if(jmmNode.getNumChildren()>1){
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Method call was not expecting parameters."
                        ));
                return false;
            }
            return true;
        }

        //checks if parameters are the right number and type

        if((jmmNode.getNumChildren() - 1)!=(symbolTable.getParameters(methodName).size())){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Mismatched number of variables given for this method signature "
                    ));
            return false;
        }

        //deal with parameters one by one
        for (int i = 0; i != symbolTable.getParameters(methodName).size(); i++) {

            Type expectedTypeReal= symbolTable.getParameters(methodName).get(i).getType();
            String expectedType = expectedTypeReal.getName();
            JmmNode givenType = jmmNode.getJmmChild(i + 1);
            String givenTypeS = "";

            if(givenType.getKind().equals("True") || givenType.getKind().equals("False")){
                if (expectedType.equals("boolean")){
                    continue;
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + "to be " + expectedType + "but received boolean"
                        ));
                return false;
            }

            if(givenType.getKind().equals("This")){
                if (expectedType.equals(symbolTable.getClassName())){
                    continue;
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + " to be " + expectedType + "but received " + symbolTable.getClassName()
                        ));
                return false;
            }

            if(givenType.getKind().equals("MethodCall")){
                if (!(visit(givenType, reports))){
                    return false;
                }
                String methodCallName = givenType.get("value");
                String objectCallName;

                if(!givenType.getJmmChild(0).getKind().equals("This")){
                    objectCallName = givenType.getJmmChild(0).get("value");
                    String objectCallType = symbolTable.getVarTypeByID(objectCallName,currentScope);
                    if((checkObjectIsImportedSolo(objectCallName)) || (checkObjectIsImportedSolo(objectCallType))
                            ||checkObjectIsExtendSolo(objectCallName)||checkObjectIsExtendSolo(objectCallType)){
                        continue;
                    }
                }

                if(!(symbolTable.getReturnType(methodCallName).equals(expectedTypeReal))) {
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Method being called does not return  " + expectedType
                            ));
                    return false;

                }
                continue;

            }

            if((givenType.getKind().equals("LogicalAnd"))||(givenType.getKind().equals("LogicalOr"))){
                if (expectedType.equals("boolean")){
                    if(visit(givenType,reports)){
                        continue;
                    }
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + "to be " + expectedType + "but received boolean"
                        ));
                return false;
            }

            Type typeArray = new Type("int", true);
            if(givenType.getKind().equals("ArrayCreation")){
                if (expectedTypeReal.equals(typeArray)){
                    if(visit(givenType,reports)){
                        continue;
                    }
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + "to be " + expectedType
                        ));
                return false;
            }

            if(givenType.getKind().equals("ObjectCreation")){
                if (expectedType.equals(givenType.get("value"))){
                    if(visit(givenType,reports)){
                        continue;
                    }
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + "to be " + expectedType
                        ));
                return false;
            }

            Type typeInt = new Type("int", false);

            if(givenType.getKind().equals("BinaryOp")){
                if (expectedTypeReal.equals(typeInt)){
                    if(visit(givenType,reports)){
                        continue;
                    }
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + "to be " + expectedType + "but integer"
                        ));
                return false;
            }

            givenTypeS = symbolTable.getVarTypeByID(givenType.get("value"),currentScope);

            if (givenType.getKind().equals("Integer")) {
                if (expectedTypeReal.equals(typeInt)) {
                    continue;
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible arguments with method call. Expected argument " + i + "to be " + expectedType + "but received integer"
                        ));
                return false;

            }

            if(givenType.getKind().equals("Identifier")){

                checkVarIsDeclared(givenType,currentScope);
                if(expectedType.equals("int")&& !(symbolTable.getParameters(methodName).get(i).getType().isArray())){
                    if(checkTypeInt(givenType,currentScope)){
                        continue;
                    }
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Incompatible arguments with method call. Expected argument " + i + " to be  int but received " + givenTypeS
                            ));
                    return false;

                }

                if(expectedType.equals("int") && symbolTable.getParameters(methodName).get(i).getType().isArray()){
                    if(checkTypeArray(givenType,currentScope)){
                        continue;
                    }
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Incompatible arguments with method call. Expected argument " + i + "to be Array but received " + givenTypeS + ""
                            ));
                    return false;


                }
                if(!(givenTypeS.equals(expectedType))){
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Incompatible arguments with method call. Expected " + expectedType + " but received " + givenTypeS
                            ));
                    return false;
                }
            }
        }
        return true;
    }


    private Boolean checkObjectDeclared(String objectName) {

        for (String importName: symbolTable.getImports()){
            if (importName.equals(objectName)){
                return true;
            }

        }

        if(objectName.equals(symbolTable.getClassName())) {
            return true;
        }

        if(objectName.equals(symbolTable.getSuper())) {
            return true;
        }
        return false;

    }

    private Boolean checkObjectIsImported(String objectName, String assignedType){
        for (String importName: symbolTable.getImports()){
            if((importName.contains(objectName))){
                return true;
            }
            if((importName.contains(assignedType))){
                return true;
            }

        }
        return false;
    }

    private Boolean checkObjectIsImportedSolo(String objectName){
        for (String importName: symbolTable.getImports()){
            if((importName.contains(objectName))){
                return true;
            }
        }
        return false;
    }


    private Boolean dealWithClass(JmmNode jmmNode, List<Report> reports) {

        String className = jmmNode.get("value");
        symbolTable.setClassName(className);


        for (JmmNode child : jmmNode.getChildren()) {

            if (child.getKind().equals("MainMethodDeclaration")) {
                currentScope = "main";
                visit(child, reports);

            }

            if (child.getKind().equals("OtherMethodDeclaration")) {
                currentScope = child.get("name");
                visit(child, reports);

            }

            if (child.getKind().equals("VarDeclaration")) {
                visit(child, reports);

            }

        }

        return true;
    }

    private Boolean dealWithMainMethod(JmmNode jmmNode, List<Report> reports) {

        checkThisInMain(jmmNode); //if This exists inside static main, error report is created
        for (JmmNode child : jmmNode.getChildren()) {

            if (child.getKind().equals("VarDeclaration")) {
                visit(child, reports);

            }

            if (child.getKind().equals("Statement")){
                visit(child, reports);
            }
            if (child.getKind().equals("Assignment")){
                visit(child, reports);
            }

            if (child.getKind().equals("ArrayAssignment")){
                visit(child, reports);
            }

            if (child.getKind().equals("IfStatement")){
                visit(child,reports);
            }

            if (child.getKind().equals("WhileStatement")){
                visit(child,reports);
            }

            if (child.getKind().equals("ExpressionStatement")){
                if(child.getJmmChild(0).getKind().equals("MethodCall")) {
                    visit(child.getJmmChild(0), reports);
                }
            }
        }
        return true;

    }

    private Boolean checkThisInMain(JmmNode jmmNode){

        for (JmmNode child : jmmNode.getChildren()) {

            if (child.getKind().equals("This")){
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "This expression can't be used in a static method."
                        ));
                return false;
            }
            checkThisInMain(child);
        }

        return true;
    }

    private Boolean dealWithMethod(JmmNode jmmNode, List<Report> reports) {

        for (JmmNode child : jmmNode.getChildren()) {

            if (child.getKind().equals("VarDeclaration")) {
                visit(child, reports);
            }

            if (child.getKind().equals("Statement")){
                visit(child, reports);
            }
            if (child.getKind().equals("Assignment")){
                visit(child, reports);
            }

            if (child.getKind().equals("ArrayAssignment")){
                visit(child, reports);
            }

            if (child.getKind().equals("IfStatement")){
                visit(child,reports);
            }

            if (child.getKind().equals("WhileStatement")){
                visit(child,reports);
            }

            if (child.getKind().equals("ExpressionStatement")){
                if(child.getJmmChild(0).getKind().equals("MethodCall")) {
                    visit(child.getJmmChild(0), reports);
                }
            }
        }

        checkMethodReturnType(jmmNode, reports);
        return true;
    }

    private Boolean dealWithAssignment(JmmNode jmmNode, List<Report> reports) {


        Type typeArray = new Type("int", true);
        Type typeInt = new Type("int", false);


        if(checkVarIsDeclared(jmmNode,currentScope)){ //start by checking if var to be assigned is declared

            String assigned_type= symbolTable.getVarTypeByID(jmmNode.get("value"),currentScope);
            Type assigned_RealType = symbolTable.getVarRealTypeByID(jmmNode.get("value"),currentScope);
            JmmNode assignee = jmmNode.getJmmChild(0);

            if(assignee.getKind().equals("Length")){

                if(assigned_RealType.equals(typeInt)){
                    return visit(assignee, reports);
                }
            }

            if(assignee.getKind().equals("RelationalOp")||assignee.getKind().equals("LogicalAnd")||assignee.getKind().equals("LogicalOr")){

                if(assigned_type.equals("boolean")){
                    return visit(assignee, reports);
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Tried to assign boolean expression  to "  + assigned_type
                        ));

                return false;

            }


            if(assignee.getKind().equals("ArrayCreation")){

                if (!(visit(assignee, reports))){
                   return false;
                }
                if(!(checkTypeArray(jmmNode,currentScope))){
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Tried to assign array creation  to "  + assigned_type
                            ));

                    return false;
                }
                return true;
            }

            if(assignee.getKind().equals("MethodCall")){

                if (!(visit(assignee, reports))){
                    return false;
                }
                String methodName = assignee.get("value");
                String objectName;

                if(!assignee.getJmmChild(0).getKind().equals("This")){
                    objectName = assignee.getJmmChild(0).get("value");
                    String objectType = symbolTable.getVarTypeByID(objectName,currentScope);
                    if((checkObjectIsImportedSolo(objectName)) || (checkObjectIsImportedSolo(objectType)) ||checkObjectIsExtendSolo(objectName)||checkObjectIsExtendSolo(objectType)){
                        return true;
                    }
                }

                if(!(symbolTable.getReturnType(methodName).equals(assigned_RealType))) {
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Tried to assign method return " + symbolTable.getReturnType(methodName).getName() + " to " + assigned_type
                            ));
                    return false;

                }
                return true;
            }

            if(assignee.getKind().equals("ObjectCreation")){

                if (!(visit(assignee, reports))){
                    return false;
                }
                return true;
            }


                if(assignee.getKind().equals("This")){
                    String varType = symbolTable.getClassName();
                    if(!(Objects.equals(assigned_type, varType))){
                        this.reports.add(
                                new Report(
                                        ReportType.ERROR,
                                        Stage.SEMANTIC,
                                        Integer.parseInt(jmmNode.get("lineStart")),
                                        Integer.parseInt(jmmNode.get("colStart")),
                                        "Tried to assign " + varType + " to " + assigned_type
                                ));
                        return false;

                    }
                return true;

                }

                if(assignee.getKind().equals("True")||assignee.getKind().equals("False")){
                    if(assigned_type.equals("boolean")){
                        return true;
                    }
                    else{
                        this.reports.add(
                                new Report(
                                        ReportType.ERROR,
                                        Stage.SEMANTIC,
                                        Integer.parseInt(jmmNode.get("lineStart")),
                                        Integer.parseInt(jmmNode.get("colStart")),
                                        "Tried to assign boolean to " + assigned_type + " variable."
                                ));
                        return false;
                    }
                }

                if (assignee.getKind().equals("BinaryOp")) {
                    return visit(assignee, reports);
                }

                if(assignee.getKind().equals("ArrayAccess")){
                    return visit(assignee, reports);
                }
                if (assignee.getKind().equals("Identifier")) {
                    if(checkVarIsDeclared(jmmNode, currentScope)){

                        String assigneeID = assignee.get("value");
                        String varType = symbolTable.getVarTypeByID(assignee.get("value"),currentScope);

                        if (!assigned_type.equals(varType)){

                            if((varType.equals(symbolTable.getClassName()) || (assigned_type.equals(symbolTable.getClassName())))){
                                if(symbolTable.getSuper()!=null) {
                                    if ((checkObjectIsExtend(varType, assigned_type))) {
                                        return true;
                                    }
                                }
                                this.reports.add(
                                        new Report(
                                                ReportType.ERROR,
                                                Stage.SEMANTIC,
                                                Integer.parseInt(jmmNode.get("lineStart")),
                                                Integer.parseInt(jmmNode.get("colStart")),
                                                "Tried to assign " + varType + " to "  + assigned_type + " variable"
                                        ));
                                return false;
                            }
                            if((checkObjectIsImported(varType,assigned_type))){
                                return true;

                            }


                            this.reports.add(
                                    new Report(
                                            ReportType.ERROR,
                                            Stage.SEMANTIC,
                                            Integer.parseInt(jmmNode.get("lineStart")),
                                            Integer.parseInt(jmmNode.get("colStart")),
                                            "Tried to assign " + varType + " to "  + assigned_type + " variable"
                                    ));
                            return false;

                        }

                        if((Objects.equals(assigned_type, varType)) && (Objects.equals(assigned_type, "int"))){
                            if(((checkTypeArray(jmmNode,currentScope)) && !(checkTypeArray(assignee,currentScope)))
                            || ((!(checkTypeArray(jmmNode,currentScope)) && (checkTypeArray(assignee,currentScope))))){
                                this.reports.add(
                                        new Report(
                                                ReportType.ERROR,
                                                Stage.SEMANTIC,
                                                Integer.parseInt(jmmNode.get("lineStart")),
                                                Integer.parseInt(jmmNode.get("colStart")),
                                                "Tried to assign array to wrong type"
                                        ));
                                return false;

                            }
                            return true;

                        }
                    } else {
                        this.reports.add(
                                new Report(
                                        ReportType.ERROR,
                                        Stage.SEMANTIC,
                                        Integer.parseInt(jmmNode.get("lineStart")),
                                        Integer.parseInt(jmmNode.get("colStart")),
                                        "Tried to assign with a variable that was not declared"
                                ));
                        return false;
                    }
                    return true;
                }
                if (assignee.getKind().equals("Integer")) {
                    if (!assigned_type.equals("int")){
                        this.reports.add(
                                new Report(
                                        ReportType.ERROR,
                                        Stage.SEMANTIC,
                                        Integer.parseInt(jmmNode.get("lineStart")),
                                        Integer.parseInt(jmmNode.get("colStart")),
                                        "Tried to assign integer to "  + assigned_type + " variable"
                                ));
                        return false;

                    }
                    return true;
                }
            }

        this.reports.add(
                new Report(
                        ReportType.ERROR,
                        Stage.SEMANTIC,
                        Integer.parseInt(jmmNode.get("lineStart")),
                        Integer.parseInt(jmmNode.get("colStart")),
                        "Incompatible assignment"
                ));
        return false;
    }

    private boolean checkObjectIsExtend(String varType, String assignedType) {

        if((symbolTable.getClassName().equals(varType))&&(symbolTable.getSuper().equals(assignedType))){
                return true;
        }

        if((symbolTable.getClassName().equals(assignedType))&&(symbolTable.getSuper().equals(varType))){

            return true;
        }

        return false;

    }

    private boolean checkObjectIsExtendSolo(String objectType) {
        if(symbolTable.getClassName().equals(objectType)){
            return true;
        }

        return false;

    }

    private Boolean dealWithObjectCreation(JmmNode jmmNode, List<Report> reports) {

        String objectName = jmmNode.get("value");
        checkObjectIsDeclared(objectName,currentScope);
        return true;
    }

    private Boolean dealWithArrayCreation(JmmNode jmmNode, List<Report> reports) {
        JmmNode index =jmmNode.getJmmChild(0);
        if((!checkTypeInt(index,currentScope))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Expected index on new array to be integer"
                    ));
            return false;
        }
        return true;
    }

    private boolean checkObjectIsDeclared(String objectName, String currentScope) {

        for (var parameter:symbolTable.getParameters(currentScope)) { //checks method parameters
            if (Objects.equals(parameter.getName(), objectName)) {
                return true;
            }
        }

        for (var method:symbolTable.getMethods()) { //checks method parameters
            if (Objects.equals(method, objectName)) {
                return true;
            }
        }


        for (var field:symbolTable.getFields()){ //checks class fields
            if (Objects.equals(field.getName(), objectName)) {
                return true;
            }
        }


        for(var localVar:symbolTable.getLocalVariables(currentScope)){ //checks if any declared local variable corresponds to identifier
            if (Objects.equals(localVar.getName(), objectName)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkMethodExists(String methodName) {

        for (var method:symbolTable.getMethods()) { //checks class Methods
            if (Objects.equals(method, methodName)) {
                return true;
            }
        }

        return false;
    }

    private Boolean dealWithArrayAssignment(JmmNode jmmNode, List<Report> reports) {

        JmmNode index = jmmNode.getJmmChild(0);
        JmmNode assignee =jmmNode.getJmmChild(1);

        boolean RHS_ok=false;
        boolean LHS_ok=false;


        if(checkVarIsDeclared(jmmNode,currentScope)){ //start by checking if var to be assigned is declared

            String assigned_type= symbolTable.getVarTypeByID(jmmNode.get("value"),currentScope);
            if(!(checkTypeArrayByName(jmmNode.get("value"),jmmNode,currentScope))){
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Tried to access array over "  + assigned_type + " variable. Must be array."
                        ));
                return false;

            }

            if(index.getKind().equals("Parentheses")){
                return visit(index,reports);
            }

            if(!checkTypeInt(index,currentScope)){
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Array index must of type Integer"
                        ));
                return false;
            }


                if(assignee.getKind().equals("Length")){

                    return visit(assignee, reports);
                }


                if (assignee.getKind().equals("BinaryOp")) {
                    return visit(assignee, reports);
                }
                if (assignee.getKind().equals("Identifier")) {
                    if(checkVarIsDeclared(jmmNode, currentScope)){

                        String assigneeID = assignee.get("value");
                        String varType = symbolTable.getVarTypeByID(assignee.get("value"),currentScope);

                        if (!assigned_type.equals(varType)){
                            this.reports.add(
                                    new Report(
                                            ReportType.ERROR,
                                            Stage.SEMANTIC,
                                            Integer.parseInt(jmmNode.get("lineStart")),
                                            Integer.parseInt(jmmNode.get("colStart")),
                                            "Tried to assign " + varType + " to "  + assigned_type + " variable"
                                    ));
                            return false;

                        }

                        if((varType.equals("int"))){
                            if(!(checkTypeInt(assignee,currentScope))){
                                this.reports.add(
                                        new Report(
                                                ReportType.ERROR,
                                                Stage.SEMANTIC,
                                                Integer.parseInt(jmmNode.get("lineStart")),
                                                Integer.parseInt(jmmNode.get("colStart")),
                                                "Tried to assign array to wrong type"
                                        ));
                                return false;

                            }
                            return true;
                        }

                    } else {
                        this.reports.add(
                                new Report(
                                        ReportType.ERROR,
                                        Stage.SEMANTIC,
                                        Integer.parseInt(jmmNode.get("lineStart")),
                                        Integer.parseInt(jmmNode.get("colStart")),
                                        "Tried to assign with a variable that was not declared"
                                ));
                        return false;
                    }
                }
                if (assignee.getKind().equals("Integer")) {
                    return true;

                }
            }

        this.reports.add(
                new Report(
                        ReportType.ERROR,
                        Stage.SEMANTIC,
                        Integer.parseInt(jmmNode.get("lineStart")),
                        Integer.parseInt(jmmNode.get("colStart")),
                        "Incompatible assignment"
                ));
        return false;

    }

    private Boolean dealWithRelationalOp(JmmNode jmmNode, List<Report> reports) {
        JmmNode left_child = jmmNode.getJmmChild(0);
        JmmNode right_child = jmmNode.getJmmChild(1);

        boolean RHS_ok=false;
        boolean LHS_ok=false;

        if(left_child.getKind().equals("Parentheses")){

            LHS_ok=visit(left_child,reports);
        }

        if(right_child.getKind().equals("Parentheses")){

            RHS_ok=visit(right_child,reports);
        }

        if(left_child.getKind().equals("ArrayAccess")){

            LHS_ok= visit(left_child, reports);
        }

        if(right_child.getKind().equals("ArrayAccess")){
            RHS_ok=visit(right_child, reports);
        }

        if((checkTypeInt(left_child, currentScope))){
            LHS_ok=true;
        }
        if((checkTypeInt(right_child, currentScope))){
            RHS_ok =true;
        }

        if((!(RHS_ok) || !(LHS_ok))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Expected both operands to be integers."
                    ));

            return false;
        }

        return true;

    }


    private Boolean dealWithBinaryOp(JmmNode jmmNode, List<Report> reports){

        JmmNode left_child = jmmNode.getJmmChild(0);
        JmmNode right_child = jmmNode.getJmmChild(1);
        boolean RHS_ok=false;
        boolean LHS_ok=false;

        if(left_child.getKind().equals("Parentheses")){

            LHS_ok=visit(left_child,reports);
        }

        if(right_child.getKind().equals("Parentheses")){

            RHS_ok=visit(right_child,reports);
        }

        if(left_child.getKind().equals("BinaryOp")){

            LHS_ok=visit(left_child,reports);
        }

        if(right_child.getKind().equals("BinaryOp")){

            RHS_ok=visit(right_child,reports);
        }

        if(left_child.getKind().equals("ArrayAccess")){

            LHS_ok= visit(left_child, reports);
        }

        if(right_child.getKind().equals("ArrayAccess")){
            RHS_ok=visit(right_child, reports);
        }

        if((checkTypeInt(left_child, currentScope))){
            LHS_ok=true;
        }
        if((checkTypeInt(right_child, currentScope))){
            RHS_ok =true;
        }

        if((!(RHS_ok) || !(LHS_ok))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Expected both operands to be integers."
                    ));

            return false;
        }
        return true;
    }

    private Boolean dealWithParentheses(JmmNode jmmNode, List<Report> reports) {
        for (JmmNode child : jmmNode.getChildren()) {

            if(!visit(child,reports)){
                return false;
            }

            /*
            if(child.getKind().equals("BinaryOp")){
                return visit(child,reports);
            }

            if(child.getKind().equals("Parentheses")){
                return visit(child,reports);
            }

             */
    }
        return true;
    }

    private Boolean dealWithIfStatement(JmmNode jmmNode, List<Report> reports){

        JmmNode expression = jmmNode.getJmmChild(0);

        if (!(checkTypeBoolean(expression))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Expressions in conditions must return a boolean"
                    ));
            return false;
        }

        for (JmmNode child : jmmNode.getChildren()) {
            if (child.getKind().equals("BracketStatement")){
                for(JmmNode grandchild: child.getChildren()) {
                    visit(grandchild,reports);
                }
            }
            if(child.getKind().equals("Statement")){
                visit(child,reports);
            }
        }

        return true;

    }

    private Boolean dealWithWhileStatement(JmmNode jmmNode, List<Report> reports){

        JmmNode expression = jmmNode.getJmmChild(0);
        JmmNode statement = jmmNode.getJmmChild(1);

        if (!(checkTypeBoolean(expression))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Expressions in conditions must return a boolean"
                    ));
            return false;
        }

        return visit(statement,reports);
    }

    private Boolean checkTypeBoolean(JmmNode jmmNode){

        if(jmmNode.getKind().equals("RelationalOp")){
            return visit(jmmNode,reports);
        }

        if(jmmNode.getKind().equals("LogicalAnd")){
            return visit(jmmNode,reports);
        }

        if(jmmNode.getKind().equals("LogicalOr")){
            return visit(jmmNode,reports);
        }

        if(jmmNode.getKind().equals("Identifier")){
            String varName = jmmNode.get("value");

            if (symbolTable.getVarTypeByID(varName,currentScope).equals("boolean")){

                return true;

            }
        }

        if(((jmmNode.getKind().equals("True")) || jmmNode.getKind().equals("False"))){
            return true;
        }

        return false;

    }

    private Boolean dealWithStatement(JmmNode jmmNode, List<Report> reports){
        for (JmmNode child : jmmNode.getChildren()) {

            if (child.getKind().equals("Statement")){
                visit(child, reports);
            }

            if (child.getKind().equals("IfStatement")){
                visit(child,reports);
            }

            if (child.getKind().equals("WhileStatement")){
                visit(child,reports);
            }

            if (child.getKind().equals("BracketStatement")){
                for(JmmNode grandchild: child.getChildren()) {
                    visit(grandchild,reports);
                }
            }

        }
        return true;
    }

    private Boolean dealWithArrayAccess(JmmNode jmmNode, List<Report> reports){
        JmmNode array_ID = jmmNode.getJmmChild(0);
        JmmNode array_index = jmmNode.getJmmChild(1);

        if(!(checkVarIsDeclared(array_ID,currentScope))){
            return false;
        }
        if(!(checkTypeArray(array_ID,currentScope))){
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Array access must be done over an array"
                    ));
            return false;
        }

        if(checkTypeInt(array_index,currentScope)){
            return true;
        } else {
            this.reports.add(
                    new Report(
                            ReportType.ERROR,
                            Stage.SEMANTIC,
                            Integer.parseInt(jmmNode.get("lineStart")),
                            Integer.parseInt(jmmNode.get("colStart")),
                            "Array access index must be of type Integer"
                    ));
            return false;
        }
    }


    private Boolean checkMethodReturnType(JmmNode jmmNode, List<Report> reports) {
        String method = jmmNode.get("name");
        Type returnType= symbolTable.getReturnType(method);
        String returnTypeS = returnType.getName();

        for (JmmNode child : jmmNode.getChildren()) {

            if(child.getKind().equals("MethodCall")){
                if(visit(child,reports)){
                    /*Check if import or extend, assume return is correct*/
                    JmmNode object = child.getJmmChild(0);

                    String objectName;
                    String objectType;

                    if(object.getKind().equals("This")){
                        objectType= symbolTable.getClassName();
                    }
                    else{
                        objectName= object.get("value");
                        objectType = symbolTable.getVarTypeByID(objectName,currentScope);

                        //If the class is being imported, assume the types of the expression where it's used are correct.
                        if(checkObjectIsImportedSolo(objectName)){
                            return true;
                        }
                        if(checkObjectIsImportedSolo(objectType)){
                            return true;
                        }
                    }

                    //assume the method exists in one of the super classes, and that is being correctly called
                    if((symbolTable.getSuper()!=null)) {

                        if (checkObjectIsExtendSolo(objectType)) {

                            return true;
                        }
                    }
                    //assume the method exists in one of the super classes, and that it is being correctly called
                    if((symbolTable.getSuper()!=null)) {

                        if (checkObjectIsExtendSolo(objectType)) {

                            return true;
                        }
                    }
                    /*Check if import or extend, assume return is correct*/

                    /*Confirm Return Type*/
                    String methodName = child.get("value");
                    Type returnTypeMethodCall = symbolTable.getReturnType(methodName);
                    String returnTypeMethodCallS = returnTypeMethodCall.getName();

                    if(!returnTypeS.equals(returnTypeMethodCallS)){
                        this.reports.add(
                                new Report(
                                        ReportType.ERROR,
                                        Stage.SEMANTIC,
                                        Integer.parseInt(jmmNode.get("lineStart")),
                                        Integer.parseInt(jmmNode.get("colStart")),
                                        "Return type mismatch. Expected " + returnTypeS  + " but received " + returnTypeMethodCallS +""
                                ));
                        return false;
                    }

                    return true;
                }

                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Incompatible assignment with MethodCall"
                        ));
                return false;
            }


            if((child.getKind().equals("True")) || ((child.getKind().equals("False")))){
                if(returnTypeS.equals("boolean")) {
                    return true;
                }

            }

            Type typeArray = new Type("int", true);
            if(child.getKind().equals("ArrayCreation")){
                if (returnType.equals(typeArray)){
                    if(visit(child,reports)){
                        continue;
                    }
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Wrong return type. Method expected " + returnTypeS
                        ));
                return false;
            }

            if(child.getKind().equals("ObjectCreation")){
                if (returnTypeS.equals(child.get("value"))){
                    if(visit(child,reports)){
                        continue;
                    }
                }
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Wrong return type, expected " + returnTypeS
                        ));
                return false;
            }

            if (child.getKind().equals("ArrayAccess")){
                if(returnTypeS.equals("int")) {
                    visit(child, reports);
                    return true;
                }
                else{
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Return type for method is incorrect. Expected " + returnTypeS + " but received Array Access"
                            ));
                    return false;
                }
            }

            /*CHECKS IF RETURN IS RELATIONALOP*/
            if (child.getKind().equals("RelationalOp")){
                if(returnTypeS.equals("boolean")) {
                    visit(child, reports);
                    return true;
                }
                else{
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Return type for method is incorrect. Expected " + returnTypeS + " but received boolean operation"
                            ));
                    return false;
                }
            }

             /*CHECKS IF RETURN IS BINARY OPERATION*/
            if (child.getKind().equals("BinaryOp")){
                if(returnTypeS.equals("int")) {
                    visit(child, reports);
                    return true;
                }
                else{
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Return type for method is incorrect. Expected " + returnTypeS + " but received Binary Operation"
                            ));
                    return false;
                }
            }

            /*CHECKS IF RETURN TYPE IS INTEGER*/
            if (child.getKind().equals("Integer")) { //checks if return type is int
                if ((returnType.getName().equals("int"))&&!(returnType.isArray())){
                    return true;
                }
                else{
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Return type for method is incorrect. Expected " + returnType + " but received int"
                            ));
                    return false;
                }
            }
        /*CHECKS IF RETURN TYPE IS INTEGER*/

        /*CASE RETURN TYPE IS IDENTIFIER - CHECK LOCAL VARS, PARAMETERS, METHOD PARAMETER OR FIELDS*/
            if (child.getKind().equals("Identifier")) { //checks if return type for method is correct

                String childID = child.get("value");
                String className = symbolTable.getClassName();

                for(var localVar:symbolTable.getLocalVariables(method)){ //checks if any local variable corresponds to identifier for return
                    if (Objects.equals(localVar.getName(), childID)) {
                        if (Objects.equals(localVar.getType(), returnType)) {
                            return true;
                        } else {
                            this.reports.add(
                                    new Report(
                                            ReportType.ERROR,
                                            Stage.SEMANTIC,
                                            Integer.parseInt(jmmNode.get("lineStart")),
                                            Integer.parseInt(jmmNode.get("colStart")),
                                            "Return type for method is incorrect. Expected " + returnTypeS + " but received " + localVar.getType().getName() + ""
                                    ));
                            return false;
                        }
                    }

                }

                for (var parameter:symbolTable.getParameters(method)) { //checks method parameters
                 if (Objects.equals(parameter.getName(), childID)) {
                        if (Objects.equals(parameter.getType(), returnType)) {
                            return true;
                        }else {
                            this.reports.add(
                                    new Report(
                                            ReportType.ERROR,
                                            Stage.SEMANTIC,
                                            Integer.parseInt(jmmNode.get("lineStart")),
                                            Integer.parseInt(jmmNode.get("colStart")),
                                            "Return type for method is incorrect. Expected " + returnTypeS + " but received " + parameter.getType().getName() + ""
                                    ));
                            return false;
                        }
                  }
             }

                    for (var field:symbolTable.getFields()){ //checks class fields
                        if (Objects.equals(field.getName(), childID)) {
                            if (Objects.equals(field.getType(), returnType)) {
                                return true;
                            }else {
                                this.reports.add(
                                        new Report(
                                                ReportType.ERROR,
                                                Stage.SEMANTIC,
                                                Integer.parseInt(jmmNode.get("lineStart")),
                                                Integer.parseInt(jmmNode.get("colStart")),
                                                "Return type for method is incorrect. Expected " + returnTypeS + " but received " + field.getType().getName() + ""
                                        ));
                                return false;
                            }
                        }
                 }
              //identifier was not declared
                this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Return type for method is incorrect. Expected " + returnTypeS + " but received undeclared variable"
                        ));
             return false;
            }
        }
        this.reports.add(
                new Report(
                        ReportType.ERROR,
                        Stage.SEMANTIC,
                        Integer.parseInt(jmmNode.get("lineStart")),
                        Integer.parseInt(jmmNode.get("colStart")),
                        "Return type for method is incorrect. Expected " + returnTypeS + " but received incompatible expression"
                ));
        return false;
    }


    private Boolean checkTypeInt(JmmNode jmmNode, String methodName) {

        Type typeInt = new Type("int", false);
            /*CHECKS IF Length*/
            if (jmmNode.getKind().equals("Length")) {
                return visit(jmmNode);
            }


            /**/

            /*CHECKS IF RETURN TYPE IS INTEGER*/
            if (jmmNode.getKind().equals("Integer")) { //checks if return type is int
                return true;
                }
            /*CHECKS IF RETURN TYPE IS INTEGER*/

        /*CHECKS IF RETURN TYPE IS INTEGER For binary Op*/
        if (jmmNode.getKind().equals("BinaryOp")) { //checks if return type is int
            if (!(visit(jmmNode, reports))){
                return false;
            }
            return true;
        }
        /*CHECKS IF RETURN TYPE IS INTEGER For binaryOp*/


            /*CHECK IF METHOD BEING CALLED IS VALID AND RETURNS INT*/
            if(jmmNode.getKind().equals("MethodCall")){
                if (!(visit(jmmNode, reports))){
                    return false;
                }

                String methodCallName = jmmNode.get("value");
                String objectName;

                if(!jmmNode.getJmmChild(0).getKind().equals("This")){
                    objectName = jmmNode.getJmmChild(0).get("value");
                    String objectType = symbolTable.getVarTypeByID(objectName,currentScope);
                    if((checkObjectIsImportedSolo(objectName)) || (checkObjectIsImportedSolo(objectType))
                            ||checkObjectIsExtendSolo(objectName)||checkObjectIsExtendSolo(objectType)){
                        return true;
                    }
                }

                if(!(symbolTable.getReturnType(methodCallName).equals(typeInt))) {
                    this.reports.add(
                            new Report(
                                    ReportType.ERROR,
                                    Stage.SEMANTIC,
                                    Integer.parseInt(jmmNode.get("lineStart")),
                                    Integer.parseInt(jmmNode.get("colStart")),
                                    "Method being called does not return int."
                            ));
                    return false;

                }
                return true;
            }
            /**/

            /*CASE RETURN TYPE IS IDENTIFIER - CHECK LOCAL VARS, PARAMETERS, METHOD PARAMETER OR FIELDS*/
            if (jmmNode.getKind().equals("Identifier")) { //checks if return type for method is correct

                checkVarIsDeclared(jmmNode,currentScope);

                String childID = jmmNode.get("value");

                for(var localVar:symbolTable.getLocalVariables(methodName)){ //checks if any local variable corresponds to identifier for return
                    if (Objects.equals(localVar.getName(), childID)) {
                        if ((Objects.equals(localVar.getType().getName(), "int")) && !(localVar.getType().isArray())) {
                            return true;
                        }
                    }

                }

                for (var parameter:symbolTable.getParameters(methodName)) { //checks method parameters
                    if (Objects.equals(parameter.getName(), childID)) {
                        if ((Objects.equals(parameter.getType().getName(), "int")) && !(parameter.getType().isArray())) {
                            return true;
                        }
                    }
                }

                for (var field:symbolTable.getFields()){ //checks class fields
                    if (Objects.equals(field.getName(), childID)) {
                        if ((Objects.equals(field.getType().getName(), "int")) && !(field.getType().isArray())) {
                            return true;
                        }
                    }
                }

                //identifier does not correspond to int
        }
        return false;
    }

    private Boolean checkTypeArray(JmmNode jmmNode, String methodName) {

        /*CHECKS IF IDENTIFIER CORRESPONDS TO AN ARRAY*/

            String childID = jmmNode.get("value");

        for(var localVar:symbolTable.getLocalVariables(methodName)){ //checks if any local variable corresponds to identifier for return
            if (Objects.equals(localVar.getName(), childID)) {
                if ((Objects.equals(localVar.getType().getName(), "int")) && (localVar.getType().isArray())) {
                    return true;
                }
            }

        }

            for (var parameter:symbolTable.getParameters(methodName)) { //checks method parameters
                if (Objects.equals(parameter.getName(), childID)) {
                    if ((Objects.equals(parameter.getType().getName(), "int")) && (parameter.getType().isArray())) {
                        return true;
                    }
                }
            }

            for (var field:symbolTable.getFields()){ //checks class fields
                if (Objects.equals(field.getName(), childID)) {
                    if ((Objects.equals(field.getType().getName(), "int")) && (field.getType().isArray())) {
                        return true;
                    }
                }
            }

             //identifier does not correspond to array
        return false;
    }

    private Boolean checkTypeArrayByName(String arrayName, JmmNode jmmNode, String methodName) {

        /*CHECKS IF IDENTIFIER CORRESPONDS TO AN ARRAY*/

        String childID = arrayName;

        for(var localVar:symbolTable.getLocalVariables(methodName)){ //checks if any local variable corresponds to identifier for return
            if (Objects.equals(localVar.getName(), childID)) {
                if ((Objects.equals(localVar.getType().getName(), "int")) && (localVar.getType().isArray())) {
                    return true;
                }
            }

        }

        for (var parameter:symbolTable.getParameters(methodName)) { //checks method parameters
            if (Objects.equals(parameter.getName(), childID)) {
                if ((Objects.equals(parameter.getType().getName(), "int")) && (parameter.getType().isArray())) {
                    return true;
                }
            }
        }

        for (var field:symbolTable.getFields()){ //checks class fields
            if (Objects.equals(field.getName(), childID)) {
                if ((Objects.equals(field.getType().getName(), "int")) && (field.getType().isArray())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean checkVarIsDeclared(JmmNode jmmNode, String methodName) {

        String childID = jmmNode.get("value");

        for(var localVar:symbolTable.getLocalVariables(methodName)){ //checks if any declared local variable corresponds to identifier
            if (Objects.equals(localVar.getName(), childID)) {
                return true;
            }
        }

            for (var parameter:symbolTable.getParameters(methodName)) { //checks method parameters
                if (Objects.equals(parameter.getName(), childID)) {
                        return true;
                }
            }

            for (var field:symbolTable.getFields()){ //checks class fields
                if (Objects.equals(field.getName(), childID)) {
                    //if field is used in main, error report is created
                    if(currentScope.equals("main")){
                        this.reports.add(
                        new Report(
                                ReportType.ERROR,
                                Stage.SEMANTIC,
                                Integer.parseInt(jmmNode.get("lineStart")),
                                Integer.parseInt(jmmNode.get("colStart")),
                                "Tried to access " + childID + " field in static method."
                        ));
                        return false;

                    }
                        return true;
                }
            }

        this.reports.add(
                new Report(
                        ReportType.ERROR,
                        Stage.SEMANTIC,
                        Integer.parseInt(jmmNode.get("lineStart")),
                        Integer.parseInt(jmmNode.get("colStart")),
                        "Variable " + childID + " was not declared."
                ));
        return false;
    }
}
