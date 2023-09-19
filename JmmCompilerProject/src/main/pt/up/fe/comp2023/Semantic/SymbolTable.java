package pt.up.fe.comp2023.Semantic;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable implements pt.up.fe.comp.jmm.analysis.table.SymbolTable {

    public static SymbolTable build(JmmNode node){
        SymbolTable table = new SymbolTable();
        SymbolVisitor visitor = new SymbolVisitor();
        visitor.visit(node, table);
        return table;
    }

    private final Map<String, Type> methods = new HashMap<>();
    private List<String> imports = new ArrayList<>();
    private String className;
    private String superClass;

    private final Map<String, List<Symbol>> parameters = new HashMap<>();
    private final List<Symbol> fields = new ArrayList<>();
    private final Map<String, List<Symbol>> localVariables = new HashMap<>();


    @Override
    public List<String> getImports() {
        return imports;
    }

    public void setImport(String importS){
        imports.add(importS);
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getSuper() {
        return superClass;
    }

    public void setSuper(String superClass) {
        this.superClass=superClass;
    }

    @Override
    public List<Symbol> getFields(){
        return  fields;
    }

    public void setField(Type returnType, String name) {
        fields.add(new Symbol(returnType,name));
    }

    @Override
    public List<String> getMethods() {
        return new ArrayList<>(methods.keySet());
    }

    //for testing purposes, remove later
    public void printTable(){

        System.out.println("Imports:");
        for(var i:getImports()){
            System.out.println("\t\t" + i);
        }

        System.out.println("Super Class:");
        System.out.println("\t" + getSuper());

        System.out.println("Class Name:");
        System.out.println("\t" + getClassName());

        System.out.println("Class Fields: ");
        for(var f:getFields()){
            System.out.println("\t" + f.getType().getName() + " " + f.getName());
        }

        System.out.println("Methods:");
        for(var m :getMethods()){
            System.out.println("\tName: "+ m);
            System.out.println("\t\tParameters: ");
            for(var p:getParameters(m)){
                System.out.println("\t\t\t" + p.getType().getName() + " " + p.getName());
            }
            System.out.println("\t\tLocal Variables: ");
            for(var localVar:getLocalVariables(m)){
                System.out.println("\t\t\t" + localVar.getType().getName() + " " + localVar.getName());
            }
        }


    }
    public Map<String, Type> maxMethods(){
        return methods;
    }

    public List<Symbol> maxFields(){
        return fields;
    }

    public Map<String, List<Symbol>> maxParameters(){
        return parameters;
    }

    public Map<String, List<Symbol>> maxLocalVars(){
        return localVariables;
    }

    public void setMethod(String method, Type returnType) {
        methods.put(method,returnType);
    }

    @Override
    public Type getReturnType(String s) {
        return methods.get(s);
    }

    @Override
    public List<Symbol> getParameters(String method) {
        return parameters.get(method);
    }

    public void setParameter(String methodName, List<Symbol> methodParameters) {
        parameters.put(methodName,methodParameters);
    }

    @Override
    public List<Symbol> getLocalVariables(String method) {
        return localVariables.get(method);
    }

    public void setLocalVariable(String methodName, List<Symbol> methodParameters) {
        localVariables.put(methodName,methodParameters);;
    }

    public String getVarTypeByID(String var_ID, String methodName){
        String default_s ="";

        if (getLocalVariables(methodName) != null) {
            for(var localVar : getLocalVariables(methodName)){
                if(localVar.getName().equals(var_ID)) {
                    return localVar.getType().getName();
                }
            }
        }

        if (getParameters(methodName) != null) {
            for(var p : getParameters(methodName)){
                if (p.getName().equals(var_ID)) {
                    return p.getType().getName();
                }
            }
        }

        if (getFields() != null) {
            for(var f : getFields()){
                if(f.getName().equals(var_ID)){
                    return f.getType().getName();
                }
            }
        }

        return default_s;
    }

    public Type getVarRealTypeByID(String var_ID, String methodName){
         Type varType = null;

         if (getLocalVariables(methodName) != null) {
             for(var localVar : getLocalVariables(methodName)){
                 if(localVar.getName().equals(var_ID)) {
                     return localVar.getType();
                 }
             }
         }

         if (getParameters(methodName) != null) {
             for (var p : getParameters(methodName)) {
                 if (p.getName().equals(var_ID)) {
                     return p.getType();
                 }
             }
         }

         if (getFields() != null) {
             for(var f : getFields()){
                 if(f.getName().equals(var_ID)){
                     return  f.getType();
                 }
             }
         }

         return varType;
    }

    public String getInvokeType(String name) {
        if (getImports() != null) {
            for (String importName: getImports()){
                if(importName.contains(name)){
                    return "invokestatic";
                }
            }
        }
        return "invokevirtual";
    }

    public boolean isExternalClass(String methodName, String var_ID) {
        if (getLocalVariables(methodName) != null) {
            for(var localVar : getLocalVariables(methodName)){
                if(localVar.getName().equals(var_ID)) {
                    return false;
                }
            }
        }

        if (getParameters(methodName) != null) {
            for(var p : getParameters(methodName)){
                if (p.getName().equals(var_ID)) {
                    return false;
                }
            }
        }

        if (getFields() != null) {
            for(var f : getFields()){
                if(f.getName().equals(var_ID)){
                    return false;
                }
            }
        }

        if (getImports() != null) {
            for (String importName : this.getImports()) {
                if (importName.endsWith(var_ID)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isField(String methodName, String var_ID) {
        if (getLocalVariables(methodName) != null) {
            for(var localVar : getLocalVariables(methodName)){
                if(localVar.getName().equals(var_ID)) {
                    return false;
                }
            }
        }

        if (getParameters(methodName) != null) {
            for(var p : getParameters(methodName)){
                if (p.getName().equals(var_ID)) {
                    return false;
                }
            }
        }

        Symbol field = null;
        if (getFields() != null) {
            for(var f : getFields()){
                if(f.getName().equals(var_ID)){
                    field = f;
                }
            }
        }

        return field != null;
    }

    public Boolean checkObjectIsImported(String objectName){
        if (getImports() != null) {
            for (String importName: getImports()){
                if(importName.contains(objectName)){
                    return true;
                }
            }
        }
        return false;
    }

    public int isParam(String methodName, String var_ID) {
        int n =-1;

        if (getLocalVariables(methodName) != null) {
            for(var localVar : getLocalVariables(methodName)){
                if(localVar.getName().equals(var_ID)) {
                    return n;
                }
            }
        }

        if (getParameters(methodName) != null) {
            for(var p : getParameters(methodName)){
                if (p.getName().equals(var_ID)) {
                    n= n+2;
                    break;
                }
                n++;
            }
        }

        return n;
    }
}