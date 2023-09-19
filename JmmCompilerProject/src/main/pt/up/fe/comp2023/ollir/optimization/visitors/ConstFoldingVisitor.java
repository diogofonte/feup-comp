package pt.up.fe.comp2023.ollir.optimization.visitors;

import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.JmmNodeImpl;

public class ConstFoldingVisitor extends AJmmVisitor<Boolean, String> {
    private boolean updated;

    public boolean getUpdated() {
        return this.updated;
    }

    public void resetUpdated() {
        this.updated = false;
    }

    public ConstFoldingVisitor() {
        this.updated = false;
    }

    @Override
    protected void buildVisitor() {
        addVisit("UnaryOp", this::visitUnaryOp);
        addVisit("BinaryOp", this::visitBinaryOp);
        addVisit("Parentheses", this::visitParentheses);
        setDefaultVisit(this::defaultVisit);
    }

    private String defaultVisit(JmmNode node, Boolean dummy) {
        for (var child : node.getChildren()) {
            visit(child);
        }
        return null;
    }

    public String visitParentheses(JmmNode node, Boolean dummy) {
        JmmNode child = node.getChildren().get(0);
        String childKind = child.getKind();
        boolean isInteger = childKind.equals("Integer");
        boolean isBool = childKind.equals("True") || childKind.equals("False");

        JmmNodeImpl newNode = new JmmNodeImpl(childKind);
        if (isInteger) {
            newNode.put("value", child.get("value"));
            node.replace(newNode);
            this.updated = true;
        } else if (isBool) {
            newNode.put("value", child.getKind());
            node.replace(newNode);
            this.updated = true;
        }

        visit(child);
        return null;
    }

    public String visitUnaryOp(JmmNode node, Boolean dummy) {
        JmmNode child = node.getChildren().get(0);
        String childKind = child.getKind();
        boolean isBool = childKind.equals("True") || childKind.equals("False");

        if (isBool) {
            JmmNodeImpl newNode;
            if(childKind.equals("True")) {
                newNode = new JmmNodeImpl("True");
            } else {
                newNode = new JmmNodeImpl("False");
            }
            node.replace(newNode);
            this.updated = true;
        }

        visit(child);
        return null;
    }
    private String visitBinaryOp(JmmNode node, Boolean dummy) {
        JmmNode left = node.getChildren().get(0);
        JmmNode right = node.getChildren().get(1);
        String lhsKind = left.getKind();
        String rhsKind = right.getKind();
        boolean isInteger = lhsKind.equals("Integer") && rhsKind.equals("Integer");
        boolean isBool = (lhsKind.equals("True") || lhsKind.equals("False")) && (rhsKind.equals("True") || rhsKind.equals("False"));

        String op = node.get("op");
        JmmNodeImpl newNode;

        if (isInteger || isBool) {
            switch (op) {
                case "*" -> {
                    newNode = new JmmNodeImpl("Integer");
                    newNode.put("value", String.valueOf(Integer.parseInt(left.get("value")) * Integer.parseInt(right.get("value"))));
                    node.replace(newNode);
                    this.updated = true;
                }
                case "/" -> {
                    newNode = new JmmNodeImpl("Integer");
                    newNode.put("value", String.valueOf(Integer.parseInt(left.get("value")) / Integer.parseInt(right.get("value"))));
                    node.replace(newNode);
                    this.updated = true;
                }
                case "+" -> {
                    newNode = new JmmNodeImpl("Integer");
                    newNode.put("value", String.valueOf(Integer.parseInt(left.get("value")) + Integer.parseInt(right.get("value"))));
                    node.replace(newNode);
                    this.updated = true;
                }
                case "-" -> {
                    newNode = new JmmNodeImpl("Integer");
                    newNode.put("value", String.valueOf(Integer.parseInt(left.get("value")) - Integer.parseInt(right.get("value"))));
                    node.replace(newNode);
                    this.updated = true;
                }
                case "<" -> {
                    if (Integer.parseInt(left.get("value")) < Integer.parseInt(right.get("value"))) {
                        newNode = new JmmNodeImpl("True");
                    } else {
                        newNode = new JmmNodeImpl("False");
                    }
                    node.replace(newNode);
                    this.updated = true;
                }
                case "&&" -> {
                    if (Boolean.parseBoolean(left.getKind()) && Boolean.parseBoolean(right.getKind())) {
                        newNode = new JmmNodeImpl("True");
                    } else {
                        newNode = new JmmNodeImpl("False");
                    }
                    node.replace(newNode);
                    this.updated = true;
                }
            }
        }

        visit(left);
        visit(right);
        return null;
    }
}