package pt.up.fe.comp2023.ollir.optimization.visitors;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.ollir.InferenceCalculator;

public class DoWhileInsertVisitor extends AJmmVisitor<InferenceCalculator, Boolean> {

    /*public DoWhileAnnotatorVisitor() {
        setDefaultVisit(this::defaultVisit);
        addVisit("WhileStatement", this::whileVisit);
    }*/

    @Override
    protected void buildVisitor() {
        setDefaultVisit(this::defaultVisit);
        addVisit("WhileStatement", this::whileVisit);
    }

    public Boolean defaultVisit(JmmNode jmmNode, InferenceCalculator inference) {
        boolean modified = false;

        for (JmmNode child : jmmNode.getChildren()) {
            modified = visit(child) || modified;
        }

        return modified;
    }

    private Boolean whileVisit(JmmNode node, InferenceCalculator inference) {
        if (node.getNumChildren() == 2){
            return false;
        }

        JmmNode child = node.getJmmChild(0);
        boolean isBool = child.getKind().equals("True") || child.getKind().equals("False");

        if (isBool && child.get("value").equals("True")) {
            node.put("do_while", "True");
        } else { //false
            node.put("do_while", "False");
        }

        child.delete();

        visit(node.getJmmChild(1)); // scope

        return true;
    }
}