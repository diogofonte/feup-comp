package pt.up.fe.comp2023.ollir.optimization.visitors;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2023.ollir.InferenceCalculator;

public class WhileDuplicatorVisitor extends AJmmVisitor<InferenceCalculator, Boolean> {

    /*public WhileConditionDuplicatorVisitor() {
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
        if (node.getNumChildren() != 2)
            return false;

        JmmNode condition = node.getJmmChild(0);
        JmmNode copy = JmmNode.fromJson(condition.getJmmChild(0).toJson());
        node.add(copy, 0);

        visit(node.getJmmChild(2));

        return true;
    }
}