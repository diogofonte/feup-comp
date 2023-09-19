package pt.up.fe.comp2023.ollir.optimization;

import org.specs.comp.ollir.*;

import java.util.*;

public class ColoringGraph {
    private static class LocalVariableNode {
        int localVariable;
        String localVariableName;
        Set<LocalVariableNode> adjacentNodes;
        boolean isActive;

        public LocalVariableNode(String name, int localVariable) {
            this.localVariable = localVariable;
            localVariableName = name;
            adjacentNodes = new HashSet<>();
            isActive = true;
        }

        public List<String> getActiveAdjacentNodes() {
            List<String> variables = new ArrayList<>();
            for (LocalVariableNode adjacent : adjacentNodes) {
                if (adjacent.isActive) {
                    variables.add(adjacent.localVariableName);
                }
            }
            return variables;
        }
    }

    private final Map<Integer, LocalVariableNode> nodes;
    private final Map<String, Descriptor> varTable;
    private int localVariablesLowLimit;
    private final boolean isStaticMethod;

    public ColoringGraph(Map<Node, BitSet> inAlive, Map<Node, BitSet> outAlive, Map<Node, BitSet> define, Method method) {
        nodes = new HashMap<>();

        varTable = method.getVarTable();
        isStaticMethod = method.isStaticMethod();
        localVariablesLowLimit = isStaticMethod ? 0 : 1;

        addNodes();
        addEdges(inAlive);
        addEdges(outAlive);
        addEdges(define, outAlive);
    }

    private void addNodes() {
        for(String name: varTable.keySet()) {
            Descriptor descriptor = varTable.get(name);

            if (descriptor.getScope().equals(VarScope.PARAMETER)) {
                localVariablesLowLimit++;
            } else if (!descriptor.getVarType().getTypeOfElement().equals(ElementType.THIS) &&
                    !descriptor.getScope().equals(VarScope.FIELD)) {
                nodes.put(descriptor.getVirtualReg(), new LocalVariableNode(name, descriptor.getVirtualReg()));
            }
        }
    }

    // Connect each pair of variables that belong to the same IN or OUT set
    private void addEdges(Map<Node, BitSet> live) {
        for (Node instruction : live.keySet()) {
            BitSet bitset = live.get(instruction);
            List<Integer> varIndexes = new ArrayList<>();

            for (int i = 0; i < bitset.length(); i++) {
                if (bitset.get(i)) {
                    varIndexes.add(i);
                }
            }

            for (int i = 0; i < varIndexes.size() - 1; i++) {
                LocalVariableNode nodeOne = nodes.get(varIndexes.get(i));

                for (int j = i + 1; j < varIndexes.size(); j++) {
                    LocalVariableNode nodeTwo = nodes.get(varIndexes.get(j));

                    if (nodeOne != null && nodeTwo != null) {
                        nodeOne.adjacentNodes.add(nodeTwo);
                        nodeTwo.adjacentNodes.add(nodeOne);
                    }
                }
            }
        }
    }

    // Connect variables in KILL[i] with those in OUT[i]
    private void addEdges(Map<Node, BitSet> define, Map<Node, BitSet> outAlive) {
        for (Node instruction : define.keySet()) {
            BitSet defineBitset = define.get(instruction);
            BitSet outBitset = outAlive.get(instruction);

            List<Integer> defVarsIndexes = new ArrayList<>();
            List<Integer> outVarsIndexes = new ArrayList<>();

            for (int i = 0; i < defineBitset.length(); i++) {
                if (defineBitset.get(i)) {
                    defVarsIndexes.add(i);
                }
            }

            for (int i = 0; i < outBitset.length(); i++) {
                if (outBitset.get(i)) {
                    outVarsIndexes.add(i);
                }
            }

            for (Integer defVarIndex : defVarsIndexes) {
                LocalVariableNode nodeOne = nodes.get(defVarIndex);

                for (Integer outVarIndex : outVarsIndexes) {
                    LocalVariableNode nodeTwo = nodes.get(outVarIndex);

                    if (nodeOne != null && nodeTwo != null) {
                        nodeOne.adjacentNodes.add(nodeTwo);
                        nodeTwo.adjacentNodes.add(nodeOne);
                    }
                }
            }
        }
    }

    public LocalVariablesAllocator allocateLocalVariables(int localVariableNum) {
        if (localVariableNum < localVariablesLowLimit) {
            return allocateLocalVariables(localVariableNum + 1);
        }
        Stack<LocalVariableNode> stack = new Stack<>();

        while (!nodes.isEmpty()) {
            Iterator<Map.Entry<Integer, LocalVariableNode>> iterator = nodes.entrySet().iterator();
            while (iterator.hasNext()) {
                LocalVariableNode node = iterator.next().getValue();
                node.isActive = false;
                stack.push(node);
                iterator.remove();
            }
        }

        Map<Integer, List<String>> localVariables = new HashMap<>();
        for (int i = localVariablesLowLimit; i < localVariableNum; i++) {
            localVariables.put(i, new ArrayList<>());
        }

        Map<String, Descriptor> updatedVarTable = new HashMap<>();

        while (!stack.isEmpty()) {
            LocalVariableNode node = stack.pop();
            node.isActive = true;
            nodes.put(node.localVariable, node);

            if (!assignLocalVariable(localVariables, node, updatedVarTable)) {
                while (!stack.isEmpty()) {
                    LocalVariableNode otherNode = stack.pop();
                    otherNode.isActive = true;
                    nodes.put(otherNode.localVariable, otherNode);
                }
                return allocateLocalVariables(localVariableNum + 1);
            }
        }

        int local = isStaticMethod ? 0 : 1;

        for (String varName: varTable.keySet()) {
            Descriptor descriptor = varTable.get(varName);
            if (descriptor.getScope().equals(VarScope.PARAMETER)) {
                updatedVarTable.put(varName, new Descriptor(descriptor.getScope(), local, descriptor.getVarType()));
                local++;
            }
        }

        return new LocalVariablesAllocator(updatedVarTable, localVariableNum);
    }

    private boolean canAssignLocalVariable(Map<Integer, List<String>> localVariables, LocalVariableNode node, Integer local) {
        for (String adjacentLocalVariable : node.getActiveAdjacentNodes()) {
            if (localVariables.get(local).contains(adjacentLocalVariable)) {
                return false;
            }
        }

        return true;
    }

    private boolean assignLocalVariable(Map<Integer, List<String>> localVariables, LocalVariableNode node, Map<String, Descriptor> updatedVarTable) {
        for (Integer localVariable : localVariables.keySet()) {
            if (canAssignLocalVariable(localVariables, node, localVariable)) {
                localVariables.get(localVariable).add(node.localVariableName);
                Descriptor old = varTable.get(node.localVariableName);
                updatedVarTable.put(node.localVariableName, new Descriptor(old.getScope(), localVariable, old.getVarType()));
                return true;
            }
        }

        return false;
    }
}