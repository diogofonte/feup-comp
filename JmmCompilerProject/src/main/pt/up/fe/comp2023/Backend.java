package pt.up.fe.comp2023;

import pt.up.fe.comp.jmm.jasmin.*;
import org.specs.comp.ollir.*;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.report.Report;
import java.util.*;
import static org.specs.comp.ollir.ElementType.*;
import static org.specs.comp.ollir.InstructionType.*;
import static org.specs.comp.ollir.OperationType.*;

public class Backend implements JasminBackend {
    private String className;
    private List<String> classImports = new ArrayList<>();
    private String superClass;
    private Method currentMethod; // method being generated
    private int currentStackSize = 0;
    private int maxStackSize = 0;
    private int conditionalNumber = 0;
    private final List<Report> reports = new ArrayList<>();

    @Override
    public JasminResult toJasmin(OllirResult ollirResult) {
        ClassUnit ollir = ollirResult.getOllirClass();
        StringBuilder jasmin = new StringBuilder();

        className = ollir.getClassName();
        classImports = ollir.getImports();
        superClass = ollir.getSuperClass();

        try {
            jasmin.append(this.createClass(ollir));
            System.out.println(jasmin);
            return new JasminResult(jasmin.toString());

        } catch(Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private String createClass(ClassUnit ollir) {
        return "\n.class public " + className + "\n" +
                ".super " + createSuper() + "\n" +
                createFields(ollir) +
                ".method public <init>()V\n" +
                "\taload_0\n" +
                "\tinvokespecial " + createSuper() + "/<init>()V\n" +
                "\treturn\n" +
                ".end method\n\n" +
                createMethods(ollir);
    }

    private String createSuper() {
        if (superClass == null) {
            return "java/lang/Object";
        }
        return superClass;
    }

    private String createFields(ClassUnit ollir) {
        StringBuilder code = new StringBuilder();

        for (Field field: ollir.getFields()) {
            code.append(".field ");
            String modifier = field.getFieldAccessModifier().toString();

            if (modifier.equals("DEFAULT")) {
                code.append("private ");
            } else {
                code.append(modifier.toLowerCase()).append(" ");
            }

            code.append(field.getFieldName()).append(" ")
                    .append(createFieldDescriptor(field.getFieldType())).append("\n");
        }

        code.append("\n");

        return code.toString();
    }

    // for register allocation purposes
    public static int returnLimitLocals(Method method) {
        Set<Integer> virtualRegs = new TreeSet<>();
        virtualRegs.add(0);

        for (Descriptor descriptor : method.getVarTable().values()) {
            virtualRegs.add(descriptor.getVirtualReg());
        }

        return virtualRegs.size();
    }

    private String createLocalLimits() {
        Set<Integer> virtualRegs = new TreeSet<>();
        virtualRegs.add(0);

        for (Descriptor descriptor : currentMethod.getVarTable().values()) {
            virtualRegs.add(descriptor.getVirtualReg());
        }

        return "\t.limit locals " + virtualRegs.size() + "\n";
    }

    private String createStackLimits() {
        return "\t.limit stack " + maxStackSize + "\n";
    }

    private void modifyStackSize(int variation) {
        currentStackSize += variation;
        maxStackSize = Math.max(maxStackSize, currentStackSize);
    }

    private String createMethods(ClassUnit ollir) {
        StringBuilder code = new StringBuilder();

        for (Method method: ollir.getMethods()) {
            currentMethod = method;

            if (method.isConstructMethod()) continue;

            if (method.getMethodName().equals("main")) {
                code.append(".method public static main([Ljava/lang/String;)V\n");

                String body = createMethodBody(method);
                code.append(createStackLimits()).append(createLocalLimits()).append(body);

            } else {
                code.append(".method ");

                String modifier = method.getMethodAccessModifier().toString().toLowerCase();
                code.append(modifier).append(" ");

                if (method.isConstructMethod()) {
                    code.append("<init>(");
                } else {
                    code.append(method.getMethodName()).append("(");
                }

                for (Element parameter:  method.getParams()) {
                    code.append(createFieldDescriptor(parameter.getType()));
                }

                code.append(")").append(createFieldDescriptor(method.getReturnType())).append("\n");

                String body = createMethodBody(method);
                code.append(createStackLimits()).append(createLocalLimits()).append(body);
            }

            code.append(".end method\n\n");
            currentStackSize = 0;
            maxStackSize = 0;
        }

        return code.toString();
    }
    private String createMethodBody(Method method){
        StringBuilder stringBody = new StringBuilder();

        List<Instruction> methodInstructions = method.getInstructions();
        for (Instruction instruction: methodInstructions) {
            for (Map.Entry<String, Instruction> label: currentMethod.getLabels().entrySet()) {
                if (label.getValue().equals(instruction)) {
                    stringBody.append(label.getKey()).append(":\n");
                }
            }
            stringBody.append(createInstruction(instruction));

            if (instruction.getInstType().equals(CALL)
                    && !((CallInstruction) instruction).getReturnType().getTypeOfElement().equals(VOID)) {

                stringBody.append("\tpop\n");
                modifyStackSize(-1);
            }
        }

        boolean hasReturnInstruction = methodInstructions.size() > 0
                && methodInstructions.get(methodInstructions.size() - 1).getInstType() == RETURN;

        if (!hasReturnInstruction && method.getReturnType().getTypeOfElement().equals(VOID)) {
            stringBody.append("\treturn\n");
        }

        return stringBody.toString();
    }

    private String createInstruction(Instruction instruction) {
        return switch (instruction.getInstType()) {
            case NOPER -> createSingleInstruction((SingleOpInstruction) instruction);
            case ASSIGN -> createAssignInstruction((AssignInstruction) instruction);
            case BINARYOPER -> createBinaryInstruction((BinaryOpInstruction) instruction);
            case UNARYOPER -> createUnaryInstruction((UnaryOpInstruction) instruction);
            case CALL -> createCallInstruction((CallInstruction) instruction);
            case GETFIELD -> createGetFieldInstruction((GetFieldInstruction) instruction);
            case PUTFIELD -> createPutFieldInstruction((PutFieldInstruction) instruction);
            case GOTO -> createGotoInstruction((GotoInstruction) instruction);
            case RETURN -> createReturnInstruction((ReturnInstruction) instruction);
            case BRANCH -> createBranchInstruction((CondBranchInstruction) instruction);
        };
    }

    private Descriptor getDescriptor(Element element) {
        if (element.isLiteral()) {
            return null;
        }

        ElementType elementType = element.getType().getTypeOfElement();
        if (elementType.equals(THIS)) {
            return currentMethod.getVarTable().get("this");
        }

        if (element instanceof Operand operand) {
            return currentMethod.getVarTable().get(operand.getName());
        }

        return null;
    }

    private String getCompleteClassName(String nameWithoutImports) {
        if (nameWithoutImports.equals("this")) {
            return className;
        }

        for (String importName : classImports) {
            if (importName.endsWith(nameWithoutImports)) {
                return importName.replaceAll("\\.", "/");
            }
        }

        return nameWithoutImports;
    }

    private String createFieldDescriptor(Type type) {
        StringBuilder code = new StringBuilder();
        ElementType elementType = type.getTypeOfElement();

        if (elementType.equals(ARRAYREF)) {
            code.append("[");
            elementType = ((ArrayType) type).getArrayType();  // getTypeOfElements() also deprecated
        }

        switch (elementType) {
            case INT32 -> code.append("I");
            case BOOLEAN -> code.append("Z");
            case OBJECTREF -> {
                String name = ((ClassType) type).getName();
                code.append("L").append(getCompleteClassName(name)).append(";");
            }
            case STRING -> code.append("Ljava/lang/String;");
            case VOID -> code.append("V");
            default -> code.append("\tERROR: descriptor type not supported\n");
        }

        return code.toString();
    }

    private String getVariableNumber(String name, Element element) {
        StringBuilder code = new StringBuilder();
        if (name.equals("this")) {
            return "_0";
        }

        Descriptor descriptor = getDescriptor(element);
        int virtualRegister = descriptor.getVirtualReg();

        if (virtualRegister < 4) { // virtual registers [0,3] have specific operations
            code.append("_");
        } else {
            code.append(" ");
        }

        code.append(virtualRegister);

        return code.toString();
    }

    private String createLoad(Element element) {
        StringBuilder code = new StringBuilder();

        if (element.isLiteral()) {
            String literal = ((LiteralElement) element).getLiteral();

            ElementType elementType = element.getType().getTypeOfElement();
            if (elementType.equals(INT32) || elementType.equals(BOOLEAN)) {
                int parsedInt = Integer.parseInt(literal);

                if (parsedInt >= -1 && parsedInt <= 5) { //[-1,5]
                    code.append("\ticonst_");
                } else if (parsedInt >= -128 && parsedInt <= 127) { //byte
                    code.append("\tbipush ");
                } else if (parsedInt >= -32768 && parsedInt <= 32767) { //short
                    code.append("\tsipush ");
                } else {
                    code.append("\tldc "); //int
                }

                if (parsedInt == -1) {
                    code.append("m1");
                } else {
                    code.append(parsedInt);
                }
            } else {
                code.append("\tldc ").append(literal);
            }

            modifyStackSize(1);
        } else if (element instanceof ArrayOperand) {
            ArrayOperand operand = (ArrayOperand) element;

            code.append("\taload").append(getVariableNumber(operand.getName(), element)).append("\n"); // load array (reference)
            modifyStackSize(1);

            code.append(createLoad(operand.getIndexOperands().get(0))); // load index
            code.append("\tiaload"); // load array[index]

            modifyStackSize(-1);
        } else if (element instanceof Operand) {
            Operand operand = (Operand) element;
            ElementType operantType = operand.getType().getTypeOfElement();
            switch (operantType) {
                case INT32, BOOLEAN -> {
                    code.append("\tiload").append(getVariableNumber(operand.getName(), element));
                }
                case OBJECTREF, STRING, ARRAYREF -> {
                    code.append("\taload").append(getVariableNumber(operand.getName(), element));
                }
                case THIS -> {
                    code.append("\taload_0");
                }
                default -> {
                    code.append("\tERROR: invalid operand when loading to the stack: ").append(operantType).append("\n");
                }
            }

            modifyStackSize(1);
        } else {
            code.append("\tERROR: invalid element when loading to the stack\n");
        }

        code.append("\n");
        return code.toString();
    }

    private String getStore(Operand destination) {
        StringBuilder stringBuilder = new StringBuilder();

        switch (destination.getType().getTypeOfElement()) {
            case INT32, BOOLEAN -> {
                Descriptor descriptor = getDescriptor(destination);
                if (descriptor.getVarType().getTypeOfElement().equals(ARRAYREF)) {
                    stringBuilder.append("\tiastore").append("\n");
                    modifyStackSize(-3);
                } else {
                    stringBuilder.append("\tistore").append(getVariableNumber(destination.getName(), destination)).append("\n");
                    modifyStackSize(-1);
                }
            }
            case OBJECTREF, THIS, STRING, ARRAYREF -> {
                stringBuilder.append("\tastore").append(getVariableNumber(destination.getName(), destination)).append("\n");
                modifyStackSize(-1);
            }
            default -> stringBuilder.append("\tERROR: store operation failed\n");
        }

        return stringBuilder.toString();
    }

    private String createSingleInstruction(SingleOpInstruction instruction) {
        Element operand = instruction.getSingleOperand();
        return createLoad(operand);
    }

    private String createAssignInstruction(AssignInstruction instruction) {
        StringBuilder code = new StringBuilder();
        Operand destination = (Operand) instruction.getDest();

        if (destination instanceof ArrayOperand) {
            ArrayOperand arrayOperand = (ArrayOperand) destination;
            modifyStackSize(1);
            // load array (reference)
            code.append("\taload").append(getVariableNumber(arrayOperand.getName(), destination)).append("\n");
            // load index
            code.append(createLoad(arrayOperand.getIndexOperands().get(0)));
        } else {
            // "iinc" instruction selection
            if (instruction.getRhs().getInstType().equals(BINARYOPER)) {
                BinaryOpInstruction binaryOpInstruction = (BinaryOpInstruction) instruction.getRhs();

                OperationType operationType = binaryOpInstruction.getOperation().getOpType();
                if (operationType.equals(ADD)) {
                    Element leftOperand = binaryOpInstruction.getLeftOperand();
                    Element rightOperand = binaryOpInstruction.getRightOperand();
                    LiteralElement literal = null; //constant to increment
                    Operand operand = null; //variable being incremented

                    if (leftOperand.isLiteral() && !rightOperand.isLiteral()) {
                        literal = (LiteralElement) binaryOpInstruction.getLeftOperand();
                        operand = (Operand) binaryOpInstruction.getRightOperand();
                    } else if (!leftOperand.isLiteral() && rightOperand.isLiteral()) {
                        literal = (LiteralElement) binaryOpInstruction.getRightOperand();
                        operand = (Operand) binaryOpInstruction.getLeftOperand();
                    }

                    if (literal != null && operand != null) {
                        if (operand.getName().equals(destination.getName())) {
                            int literalValue = Integer.parseInt((literal).getLiteral());

                            if (literalValue >= -128 && literalValue <= 127) {
                                Descriptor descriptor = getDescriptor(destination);
                                return "\tiinc " + descriptor.getVirtualReg() + " " + literalValue + "\n";
                            }
                        }
                    }
                }
            }
        }

        code.append(createInstruction(instruction.getRhs()));
        // store in array[index] => destination is ArrayOperand
        code.append(getStore(destination));
        return code.toString();
    }

    private String createOperation(Operation operation) {
        return switch (operation.getOpType()) {
            case GTE -> "if_icmpge";
            case LTH -> "if_icmplt";
            case ANDB -> "iand";
            case NOTB -> "ifeq";
            case ADD -> "iadd";
            case SUB -> "isub";
            case MUL -> "imul";
            case DIV -> "idiv";
            default -> "\tERROR: operation " + operation.getOpType() + " not implemented\n";
        };
    }

    private String createBooleanOpResultToStack() {
        return " LABEL_TRUE" + conditionalNumber + "\n"
                + "\ticonst_0\n"
                + "\tgoto LABEL_FALSE" + conditionalNumber + "\n"
                + "LABEL_TRUE" + conditionalNumber + ":\n"
                + "\ticonst_1\n"
                + "LABEL_FALSE" + conditionalNumber++ + ":";
    }

    private String createBinaryInstruction(BinaryOpInstruction instruction) {
        Element leftElement = instruction.getLeftOperand();
        Element rightElement = instruction.getRightOperand();
        Operation operation = instruction.getOperation();
        StringBuilder code = new StringBuilder();

        code.append(createLoad(leftElement))
                .append(createLoad(rightElement))
                .append("\t").append(createOperation(operation));

        List<OperationType> booleanList = new ArrayList<>();
        booleanList.add(EQ);
        booleanList.add(GTH);
        booleanList.add(GTE);
        booleanList.add(LTH);
        booleanList.add(LTE);
        booleanList.add(NEQ);

        if (booleanList.contains(operation.getOpType())) {
            code.append(createBooleanOpResultToStack());
        }

        code.append("\n");

        modifyStackSize(-1);
        return code.toString();
    }

    private String createUnaryInstruction(UnaryOpInstruction instruction) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(createLoad(instruction.getOperand()))
                .append("\t").append(createOperation(instruction.getOperation()));

        OperationType operationType = instruction.getOperation().getOpType();
        if (operationType.equals(NOTB)) {
            stringBuilder.append(createBooleanOpResultToStack());
        } else {
            stringBuilder.append("\tInvalid UnaryOper\n");
        }

        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private String createCallInstruction(CallInstruction instruction) {
        StringBuilder code = new StringBuilder();
        int pop = 0;
        ElementType returnElementType = instruction.getReturnType().getTypeOfElement();
        ArrayList<Element> operands = instruction.getListOfOperands();

        switch (instruction.getInvocationType()) {
            case NEW -> {
                pop = -1; //counting with the return of the NEW

                if (returnElementType.equals(OBJECTREF) || returnElementType.equals(CLASS)) {
                    for (Element element : operands) {
                        code.append(createLoad(element));
                        pop++;
                    }
                    code.append("\tnew ").append(getCompleteClassName(((Operand) instruction.getFirstArg()).getName())).append("\n");
                } else if (returnElementType.equals(ARRAYREF)) {
                    for (Element element : operands) {
                        code.append(createLoad(element));
                        pop++;
                    }

                    code.append("\tnewarray ");
                    ElementType operandZero = operands.get(0).getType().getTypeOfElement();
                    if (operandZero.equals(INT32)) {
                        code.append("int\n");
                    } else {
                        code.append("\tERROR: array type not supported\n");
                    }
                } else {
                    code.append("\tERROR: new call not supported\n");
                }
            }
            case arraylength -> {
                code.append(createLoad(instruction.getFirstArg()));
                code.append("\tarraylength\n");
            }
            case ldc -> {
                code.append(createLoad(instruction.getFirstArg()));
            }
            case invokevirtual -> {
                code.append(createLoad(instruction.getFirstArg()));
                pop = 1;

                for (Element element : operands) {
                    code.append(createLoad(element));
                    pop++;
                }

                code.append("\tinvokevirtual ").append(getCompleteClassName(((ClassType) instruction.getFirstArg().getType()).getName()))
                        .append("/").append(((LiteralElement) instruction.getSecondArg()).getLiteral().replace("\"", ""))
                        .append("(");

                for (Element operand : operands) {
                    code.append(createFieldDescriptor(operand.getType()));
                }

                code.append(")").append(createFieldDescriptor(instruction.getReturnType())).append("\n");

                if (!returnElementType.equals(VOID)) {
                    pop--;
                }
            }
            case invokespecial -> {
                code.append(createLoad(instruction.getFirstArg()));
                pop = 1;

                code.append("\tinvokespecial ");

                if (instruction.getFirstArg().getType().getTypeOfElement().equals(THIS)) {
                    code.append(superClass);
                } else {
                    String className = getCompleteClassName(((ClassType) instruction.getFirstArg().getType()).getName());
                    code.append(className);
                }

                code.append("/").append("<init>(");

                for (Element operand : operands) {
                    code.append(createFieldDescriptor(operand.getType()));
                }

                code.append(")").append(createFieldDescriptor(instruction.getReturnType())).append("\n");

                if (!returnElementType.equals(VOID)) {
                    pop--;
                }
            }
            case invokestatic -> {
                pop = 0;

                for (Element element : operands) {
                    code.append(createLoad(element));
                    pop++;
                }

                code.append("\tinvokestatic ").append(getCompleteClassName(((Operand) instruction.getFirstArg()).getName()))
                        .append("/").append(((LiteralElement) instruction.getSecondArg()).getLiteral().replace("\"", ""))
                        .append("(");

                for (Element operand : operands) {
                    code.append(createFieldDescriptor(operand.getType()));
                }

                code.append(")").append(createFieldDescriptor(instruction.getReturnType())).append("\n");

                if (!returnElementType.equals(VOID)) {
                    pop--;
                }
            }
            default -> code.append("\tERROR: call instruction not supported\n");
        }

        modifyStackSize(-pop);
        return code.toString();
    }

    private String createGetFieldInstruction(GetFieldInstruction instruction) {
        Element first = instruction.getFirstOperand();
        Element second = instruction.getSecondOperand();
        StringBuilder code = new StringBuilder();

        code.append(createLoad(first))
                .append("\tgetfield ").append(getCompleteClassName(((Operand) first).getName()))
                .append("/").append(((Operand) second).getName())
                .append(" ").append(createFieldDescriptor(second.getType())).append("\n");

        return code.toString();
    }

    private String createPutFieldInstruction(PutFieldInstruction instruction) {
        Element first = instruction.getFirstOperand();
        Element second = instruction.getSecondOperand();
        Element third = instruction.getThirdOperand();
        StringBuilder code = new StringBuilder();

        code.append(createLoad(first))
                .append(createLoad(third))
                .append("\tputfield ").append(getCompleteClassName(((Operand) first).getName()))
                .append("/").append(((Operand) second).getName())
                .append(" ").append(createFieldDescriptor(second.getType())).append("\n");

        modifyStackSize(-2);
        return code.toString();
    }

    private String createGotoInstruction(GotoInstruction instruction) {
        return "\tgoto " + instruction.getLabel() + "\n";
    }

    private String createReturnInstruction(ReturnInstruction instruction) {
        StringBuilder code = new StringBuilder();
        Element operand = instruction.getOperand();

        if (instruction.hasReturnValue()) {
            code.append(createLoad(operand));
        }

        code.append("\t");
        if (operand != null) {
            ElementType elementType = operand.getType().getTypeOfElement();

            if (elementType.equals(INT32) || elementType.equals(BOOLEAN)) {
                code.append("i");
            } else {
                code.append("a");
            }
        }

        code.append("return\n");
        return code.toString();
    }

    private String createBranchInstruction(CondBranchInstruction instruction) {
        StringBuilder code = new StringBuilder();

        Instruction condition;
        if (instruction instanceof SingleOpCondInstruction conditionInstruction) {
            condition = conditionInstruction.getCondition();
        } else if (instruction instanceof OpCondInstruction conditionInstruction) {
            condition = conditionInstruction.getCondition();
        } else {
            return "\tERROR: invalid condition branch instruction\n";
        }

        String operation;
        switch (condition.getInstType()) {
            case BINARYOPER -> {
                BinaryOpInstruction binaryOpInstruction = (BinaryOpInstruction) condition;
                switch (binaryOpInstruction.getOperation().getOpType()) {
                    case LTH -> {
                        Element leftElement = binaryOpInstruction.getLeftOperand();
                        Element rightElement = binaryOpInstruction.getRightOperand();
                        Integer parsedInt = null;
                        Element element = null; // x
                        operation = "if_icmplt";

                        if (leftElement.isLiteral()) { // instruction selection for 0 < x
                            String literal = ((LiteralElement) leftElement).getLiteral();
                            parsedInt = Integer.parseInt(literal);
                            element = rightElement;
                            operation = "ifgt";
                        } else if (rightElement.isLiteral()) { // instruction selection for x < 0
                            String literal = ((LiteralElement) rightElement).getLiteral();
                            parsedInt = Integer.parseInt(literal);
                            element = leftElement;
                            operation = "iflt";
                        }

                        if (parsedInt != null && parsedInt == 0) {
                            code.append(createLoad(element));
                        } else {
                            code.append(createLoad(leftElement))
                                    .append(createLoad(rightElement));
                            operation = "if_icmplt";
                        }
                    }
                    case GTE -> {
                        Element leftElement = binaryOpInstruction.getLeftOperand();
                        Element rightElement = binaryOpInstruction.getRightOperand();
                        Integer parsedInt = null;
                        Element element = null; // x
                        operation = "if_icmpge";

                        if (leftElement.isLiteral()) { // instruction selection for 0 > x
                            String literal = ((LiteralElement) leftElement).getLiteral();
                            parsedInt = Integer.parseInt(literal);
                            element = rightElement;
                            operation = "iflt";

                        } else if (rightElement.isLiteral()) { // instruction selection for x > 0
                            String literal = ((LiteralElement) rightElement).getLiteral();
                            parsedInt = Integer.parseInt(literal);
                            element = leftElement;
                            operation = "ifgt";
                        }

                        if (parsedInt != null && parsedInt == 0) {
                            code.append(createLoad(element));
                        } else {
                            code.append(createLoad(leftElement))
                                    .append(createLoad(rightElement));
                            operation = "if_icmpge";
                        }
                    }
                    case ANDB -> {
                        code.append(createInstruction(condition));
                        operation = "ifne";
                    }
                    default -> {
                        code.append("\tinvalid binary operation\n");
                        code.append(createInstruction(condition));
                        operation = "ifne";
                    }
                }
            }
            case UNARYOPER -> {
                UnaryOpInstruction unaryOpInstruction = (UnaryOpInstruction) condition;
                OperationType unaryOpType = unaryOpInstruction.getOperation().getOpType();
                if (unaryOpType.equals(NOTB)) {
                    code.append(createLoad(unaryOpInstruction.getOperand()));
                    operation = "ifeq";
                } else {
                    code.append("\tinvalid unary operation\n");
                    code.append(createInstruction(condition));
                    operation = "ifne";
                }
            }
            default -> {
                code.append(createInstruction(condition));
                operation = "ifne";
            }
        }

        code.append("\t").append(operation).append(" ").append(instruction.getLabel()).append("\n");

        if (operation.equals("if_icmplt")) {
            modifyStackSize(-2);
        } else {
            modifyStackSize(-1);
        }

        return code.toString();
    }
}