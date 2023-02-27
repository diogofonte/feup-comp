package pt.up.fe.comp2023;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;

public class JavaCalcGenerator extends AJmmVisitor<String, String> {
    private String className;

    public JavaCalcGenerator(String className) {
        this.className = className;
    }

    protected void buildVisitor(){
        addVisit("Program", this::dealWithProgram);
        addVisit("Assignment", this::dealWithAssignment);
        addVisit("Integer", this::dealWithLiteral);
        addVisit("Identifier", this::dealWithLiteral);
        addVisit("ExprStmt", this::dealWithStatement);
        addVisit("BinaryOp", this::dealWithBinaryOp);
        addVisit("Parenthesis", this::dealWithParenthesis);
    }

    private String dealWithProgram(JmmNode jmmNode, String s){
        s = (s!=null?s:"");
        String ret = s+"public class "+this.className+" {\n";
        ret += s+"\tpublic static void main(String[] args) {\n";

        for(JmmNode child: jmmNode.getChildren()){
            ret += "\t\t"+visit(child,s);
            ret += "\n";
        }
        ret += s + "\t}\n";
        ret += s + "}\n";
        return ret;
    }

    private String dealWithAssignment(JmmNode jmmNode, String s) {
        return s+"int "+jmmNode.get("var")
                + " = "+visit(jmmNode.getJmmChild(0),s)
                +";";
    }

    private String dealWithLiteral(JmmNode jmmNode, String s) {
        return s+jmmNode.get("value");
    }

    private String dealWithStatement(JmmNode jmmNode, String s) {

        s = (s!=null?s:"");
        String ret = s+"System.out.println(";
        String s2 = s+" ";


        for(JmmNode child: jmmNode.getChildren()){
            ret += visit(child,s2);
        }
        ret += s2 + ")" + ";";
        return ret;

    }

    private String dealWithBinaryOp(JmmNode jmmNode, String s) {
        String ret = s;

        ret += visit(jmmNode.getJmmChild(0),s);
        ret += " " + jmmNode.get("op").charAt(1) + " ";
        ret += visit(jmmNode.getJmmChild(1),s);

        return ret;
    }

    private String dealWithParenthesis(JmmNode jmmNode, String s){
         return s + "(" + visit(jmmNode.getJmmChild(0),s) + " )";
    }
}