package pt.up.fe.comp2023.Semantic;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class SymbolVisitor extends AJmmVisitor<SymbolTable, Boolean> {

    @Override
    protected void buildVisitor() {

        addVisit("Program", this::dealWithProgram);
        addVisit("ImportDeclaration", this::dealWithImport);
        addVisit("ClassDeclaration", this::dealWithClass);
        addVisit("MainMethodDeclaration", this::dealWithMain);
        addVisit("OtherMethodDeclaration", this::dealWithMethod);
        addVisit("Parameter", this::dealWithParameter);

    }


    public Boolean dealWithProgram(JmmNode jmmNode, SymbolTable symbolTable){
        for(JmmNode child: jmmNode.getChildren()){
            if (child.getKind().equals("ImportDeclaration"))
                visit(child,symbolTable);
            if (child.getKind().equals("ClassDeclaration"))
                visit(child,symbolTable);

        }
        return true;
    }

    public Boolean dealWithImport(JmmNode jmmNode, SymbolTable symbolTable) {

        StringBuilder importS = new StringBuilder(jmmNode.get("value"));

        for (JmmNode child : jmmNode.getChildren()){
            importS.append(".");
            importS.append(child.get("value1"));
       }

        symbolTable.setImport(importS.toString());
        return true;
    }

    private Boolean dealWithClass(JmmNode jmmNode, SymbolTable symbolTable) {

        String className=jmmNode.get("value");
        symbolTable.setClassName(className);


        for (JmmNode child : jmmNode.getChildren()){



            if(child.getKind().equals("ExtendClass")) {
                String classSuper = child.get("value");
                symbolTable.setSuper(classSuper);
            }

            if(child.getKind().equals("VarDeclaration")) {
                String varName = child.get("value");
                Type returnType= new Type("void",false);
                if (child.getJmmChild(0).getKind().equals("TypeArray"))
                    returnType = new Type("int",true);
                if (child.getJmmChild(0).getKind().equals("TypeInt"))
                    returnType = new Type("int", false);
                if (child.getJmmChild(0).getKind().equals("TypeChar"))
                    returnType = new Type("char", false);
                if (child.getJmmChild(0).getKind().equals("TypeBoolean"))
                    returnType = new Type("boolean", false);
                if (child.getJmmChild(0).getKind().equals("TypeObject"))
                    returnType = new Type(child.getJmmChild(0).get("value"), false);

                symbolTable.setField(returnType,varName);
            }

            if(child.getKind().equals("MainMethodDeclaration")){
                    visit(child,symbolTable);

            }

            if(child.getKind().equals("OtherMethodDeclaration")){
                visit(child,symbolTable);

            }
        }

        return true;
    }

    private Boolean dealWithMain(JmmNode jmmNode, SymbolTable symbolTable) {

        Type type = new Type("void",false);
        symbolTable.setMethod("main", type);
        List<Symbol> parameters = new ArrayList<>();
        List<Symbol> localVars = new ArrayList<>();

        Type returnType = new Type("String", true);
        parameters.add(new Symbol(returnType, "args"));
        symbolTable.setParameter("main",parameters);

        for (JmmNode child : jmmNode.getChildren()){
            if(child.getKind().equals("VarDeclaration")) {
                JmmNode typeNodeE = child.getJmmChild(0);
                String paramName2=child.get("value");
                Type typeE = new Type("void", false);
                if (typeNodeE.getKind().equals("TypeArray")){
                    typeE = new Type("int", true);}
                if (typeNodeE.getKind().equals("TypeInt")){
                    typeE = new Type("int", false);}
                if (typeNodeE.getKind().equals("TypeChar")){
                    typeE = new Type("char", false);}
                if (typeNodeE.getKind().equals("TypeString")){
                    typeE = new Type("String", true);}
                if (typeNodeE.getKind().equals("TypeBoolean")){
                    typeE = new Type("boolean", false);}
                if (typeNodeE.getKind().equals("TypeObject")){
                    typeE = new Type(typeNodeE.get("value"), false);}

                localVars.add(new Symbol(typeE, paramName2));
            }

        }
        symbolTable.setLocalVariable("main",localVars);

        return true;
    }

    private Boolean dealWithMethod(JmmNode jmmNode, SymbolTable symbolTable) {

        String methodName=jmmNode.get("name");
        symbolTable.setParameter(methodName,new ArrayList<>());
        List<Symbol> localVars = new ArrayList<>();

        /*Method*/
        Type returnType= new Type("void",false);
        if (jmmNode.getJmmChild(0).getKind().equals("TypeArray"))
            returnType = new Type("int",true);
        if (jmmNode.getJmmChild(0).getKind().equals("TypeInt"))
            returnType = new Type("int", false);
        if (jmmNode.getJmmChild(0).getKind().equals("TypeChar"))
            returnType = new Type("char", false);
        if (jmmNode.getJmmChild(0).getKind().equals("TypeBoolean"))
            returnType = new Type("boolean", false);
        if (jmmNode.getJmmChild(0).getKind().equals("TypeObject"))
            returnType = new Type(jmmNode.getJmmChild(0).get("value"), false);

        symbolTable.setMethod(methodName, returnType);
        /*Method*/

        for (JmmNode child : jmmNode.getChildren()){
            if(child.getKind().equals("Parameter")){
            visit(child,symbolTable);}

            /*LOCAL VARIABLES*/
            if(child.getKind().equals("VarDeclaration")) {
                JmmNode typeNodeE = child.getJmmChild(0);
                String paramName2=child.get("value");
                Type typeE = new Type("void", false);
                if (typeNodeE.getKind().equals("TypeArray")){
                    typeE = new Type("int", true);}
                if (typeNodeE.getKind().equals("TypeInt")){
                    typeE = new Type("int", false);}
                if (typeNodeE.getKind().equals("TypeChar")){
                    typeE = new Type("char", false);}
                if (typeNodeE.getKind().equals("TypeString")){
                    typeE = new Type("String", true);}
                if (typeNodeE.getKind().equals("TypeBoolean")){
                    typeE = new Type("boolean", false);}
                if (typeNodeE.getKind().equals("TypeObject")){
                    typeE = new Type(typeNodeE.get("value"), false);}

                localVars.add(new Symbol(typeE, paramName2));
                /*LOCAL VARIABLES*/
            }
        }
        symbolTable.setLocalVariable(methodName,localVars);

        return true;
    }

    private Boolean dealWithParameter(JmmNode jmmNode, SymbolTable symbolTable){
        JmmNode typeNode = jmmNode.getJmmChild(0);
        String paramName=jmmNode.get("name");
        String methodName=jmmNode.getJmmParent().get("name");
        List<Symbol> parameters = new ArrayList<>();


        Type typeR= new Type("void",false);
        if (typeNode.getKind().equals("TypeArray")){
            typeR = new Type("int",true);}
        if (typeNode.getKind().equals("TypeInt")){
            typeR = new Type("int", false);}
        if (typeNode.getKind().equals("TypeChar")){
            typeR = new Type("char", false);}
        if (typeNode.getKind().equals("TypeString")){
            typeR = new Type("String", true);}
        if (typeNode.getKind().equals("TypeBoolean")){
            typeR = new Type("boolean", false);}
        if (typeNode.getKind().equals("TypeObject")){
            typeR = new Type(typeNode.get("value"), false);}

        parameters.add(new Symbol(typeR, paramName));

        for (JmmNode child : jmmNode.getChildren()){
            if(child.getKind().equals("ParameterAppend")) {
                JmmNode typeNodeE = child.getJmmChild(0);
                String paramName2=child.get("name");
                Type typeE = new Type("void", false);
                if (typeNodeE.getKind().equals("TypeArray")){
                    typeE = new Type("int", true);}
                if (typeNodeE.getKind().equals("TypeInt")){
                    typeE = new Type("int", false);}
                if (typeNodeE.getKind().equals("TypeChar")){
                    typeE = new Type("char", false);}
                if (typeNodeE.getKind().equals("TypeString")){
                    typeE = new Type("String", true);}
                if (typeNodeE.getKind().equals("TypeBoolean")){
                    typeE = new Type("boolean", false);}
                if (typeNodeE.getKind().equals("TypeObject")){
                    typeE = new Type(typeNodeE.get("value"), false);}

                parameters.add(new Symbol(typeE, paramName2));
            }

        }

        symbolTable.setParameter(methodName,parameters);

        return true;
    }
}
