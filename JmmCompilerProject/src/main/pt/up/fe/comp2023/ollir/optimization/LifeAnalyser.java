package pt.up.fe.comp2023.ollir.optimization;

import org.specs.comp.ollir.*;

import java.util.*;

public class LifeAnalyser {
    private final Map<Node, BitSet> defined;
    private final Map<Node, BitSet> used;
    private final Map<Node, BitSet> inAlive;
    private final Map<Node, BitSet> outAlive;
    private final List<Instruction> nodes;
    private final int varTableSize;

    public LifeAnalyser(Method method) {
        defined =  new HashMap<>();
        used = new HashMap<>();
        inAlive = new HashMap<>();
        outAlive = new HashMap<>();

        varTableSize = method.getVarTable().size();
        nodes = new ArrayList<>(method.getInstructions());

        for (Instruction instruction : nodes) {
            defined.put(instruction, getDefinedVars(instruction, method.getVarTable()));
            used.put(instruction, getUsedVars(instruction, method.getVarTable()));
            inAlive.put(instruction, new BitSet(varTableSize));
            outAlive.put(instruction, new BitSet(varTableSize));
        }

        Collections.reverse(nodes);
    }

    public Map<Node, BitSet> getInAlive() {
        return inAlive;
    }

    public Map<Node, BitSet> getOutAlive() {
        return outAlive;
    }

    public Map<Node, BitSet> getDefined() {
        return defined;
    }

    private BitSet getUsedVars(Instruction instruction, Map<String, Descriptor> varTable) {
        switch (instruction.getInstType()) {
            case UNARYOPER:
                return getUsedVarsUnary((UnaryOpInstruction) instruction, varTable);
            case BINARYOPER:
                return getUsedVarsBinary((BinaryOpInstruction) instruction, varTable);
            case NOPER:
                return getUsedVarsSingle((SingleOpInstruction) instruction, varTable);
            case ASSIGN:
                return getUsedVarsAssign((AssignInstruction) instruction, varTable);
            case CALL:
                return getUsedVarsCall((CallInstruction) instruction, varTable);
            case BRANCH:
                return getUsedVarsConditionBranch((CondBranchInstruction) instruction, varTable);
            case RETURN:
                return getUsedVarsReturn((ReturnInstruction) instruction, varTable);
            case GETFIELD:
                return getUsedVarsGetField((GetFieldInstruction) instruction, varTable);
            case PUTFIELD:
                return getUsedVarsPutField((PutFieldInstruction) instruction, varTable);
            default:
                break;
        }

        return new BitSet();
    }

    private BitSet getUsedVarsUnary(UnaryOpInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();
        setElement(vars, instruction.getOperand(), varTable);
        return vars;
    }

    private BitSet getUsedVarsBinary(BinaryOpInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();
        setElement(vars, instruction.getRightOperand(), varTable);
        setElement(vars, instruction.getLeftOperand(), varTable);
        return vars;
    }

    private BitSet getUsedVarsSingle(SingleOpInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();
        setElement(vars, instruction.getSingleOperand(), varTable);
        return vars;
    }

    private BitSet getUsedVarsAssign(AssignInstruction instruction, Map<String, Descriptor> varTable) {
        Operand dest = (Operand) instruction.getDest();
        Descriptor descriptor = varTable.get(dest.getName());
        BitSet vars = new BitSet();

        if (descriptor.getVarType().getTypeOfElement() == ElementType.ARRAYREF
                && dest.getType().getTypeOfElement() == ElementType.INT32) {
            for (Element index : ((ArrayOperand) dest).getIndexOperands()) {
                setElement(vars, index, varTable);
            }

            setElement(vars, dest, varTable);
        }

        vars.or(getUsedVars(instruction.getRhs(), varTable));
        return vars;
    }

    private BitSet getUsedVarsCall(CallInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();

        CallType callType = instruction.getInvocationType();
        if (callType.equals(CallType.invokevirtual) || callType.equals(CallType.invokespecial) || callType.equals(CallType.arraylength)) {
            setElement(vars, instruction.getFirstArg(), varTable);
        }

        if (instruction.getNumOperands() > 1) {
            if (!instruction.getInvocationType().equals(CallType.NEW)) {
                setElement(vars, instruction.getSecondArg(), varTable);
            }
            for (Element arg : instruction.getListOfOperands()) {
                setElement(vars, arg, varTable);
            }
        }

        return vars;
    }

    private BitSet getUsedVarsConditionBranch(CondBranchInstruction instruction, Map<String, Descriptor> varTable) {
        return getUsedVars(instruction.getCondition(), varTable);
    }

    private BitSet getUsedVarsReturn(ReturnInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();
        if (instruction.hasReturnValue()) {
            setElement(vars, instruction.getOperand(), varTable);
        }

        return vars;
    }

    private BitSet getUsedVarsGetField(GetFieldInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();
        setElement(vars, instruction.getFirstOperand(), varTable);
        return vars;
    }

    private BitSet getUsedVarsPutField(PutFieldInstruction instruction, Map<String, Descriptor> varTable) {
        BitSet vars = new BitSet();
        setElement(vars, instruction.getFirstOperand(), varTable);
        setElement(vars, instruction.getThirdOperand(), varTable);
        return vars;
    }

    private BitSet getDefinedVars(Instruction instruction, HashMap<String, Descriptor> varTable) {
        BitSet vars = new BitSet();

        if (instruction.getInstType().equals(InstructionType.ASSIGN)) {
            setElement(vars, ((AssignInstruction) instruction).getDest(), varTable, false);
        } else if (instruction.getInstType().equals(InstructionType.PUTFIELD)) {
            setElement(vars, ((PutFieldInstruction) instruction).getFirstOperand(), varTable, false);
        }

        return vars;
    }

    private void setElement(BitSet vars, Element element, Map<String, Descriptor> varTable) {
        setElement(vars, element, varTable, true);
    }

    private void setElement(BitSet vars, Element element, Map<String, Descriptor> varTable, boolean getUsed) {
        if (element.isLiteral()) {
            return;
        }

        if (element.getType().getTypeOfElement().equals(ElementType.THIS) ||
                (element.getType().getTypeOfElement().equals(ElementType.OBJECTREF) &&
                ((Operand) element).getName().equals("this"))) {
            vars.set(0);
            return;
        }

        Descriptor descriptor = varTable.get(((Operand) element).getName());

        if (getUsed) {
            // set index as used
            if (descriptor.getVarType().getTypeOfElement().equals(ElementType.ARRAYREF)
                    && element.getType().getTypeOfElement().equals(ElementType.INT32)) {
                for (Element index : ((ArrayOperand) element).getIndexOperands()) {
                    setElement(vars, index, varTable);
                }
            }
        }

        if (descriptor.getScope().equals(VarScope.PARAMETER) || descriptor.getScope().equals(VarScope.FIELD)) {
            return;
        }

        int reg = descriptor.getVirtualReg();
        vars.set(reg);
    }
}