package pt.up.fe.comp2023.ollir.optimization;

import org.specs.comp.ollir.Descriptor;

import java.util.Map;

public class LocalVariablesAllocator {
    private final Map<String, Descriptor> updateVarTable;
    private final int localVariableNum;

    public LocalVariablesAllocator(Map<String, Descriptor> updateVarTable, int localVariableNum) {
        this.updateVarTable = updateVarTable;
        this.localVariableNum = localVariableNum;
    }

    public int getLocalVariableNum() {
        return localVariableNum;
    }

    public Map<String, Descriptor> getUpdateVarTable() {
        return updateVarTable;
    }
}