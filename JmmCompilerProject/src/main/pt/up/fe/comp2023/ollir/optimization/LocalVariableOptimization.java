package pt.up.fe.comp2023.ollir.optimization;

import pt.up.fe.comp.jmm.ollir.OllirResult;
import org.specs.comp.ollir.ClassUnit;
import org.specs.comp.ollir.OllirErrorException;
import org.specs.comp.ollir.Method;

import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;

public class LocalVariableOptimization {
    private final OllirResult ollirResult;
    private final ClassUnit classUnit;

    public LocalVariableOptimization(OllirResult ollirResult) {
        this.ollirResult = ollirResult;
        this.classUnit = ollirResult.getOllirClass();
    }

    public void optimize(int localVariableNum) {
        try {
            classUnit.checkMethodLabels();
        } catch (OllirErrorException e) {
            e.printStackTrace();
            return;
        }

        classUnit.buildCFGs();
        classUnit.buildVarTables();

        int LocalsTopLimit = -1;

        for (Method method : classUnit.getMethods()) {
            LifeAnalyser analyser = new LifeAnalyser(method);
            ColoringGraph varGraph = new ColoringGraph(analyser.getInAlive(), analyser.getOutAlive(), analyser.getDefined(), method);

            LocalVariablesAllocator localVariablesAllocator = varGraph.allocateLocalVariables(localVariableNum);
            var updatedVarTable = localVariablesAllocator.getUpdateVarTable();
            int localVariableNumUsed = localVariablesAllocator.getLocalVariableNum();

            LocalsTopLimit = Math.max(LocalsTopLimit, localVariableNumUsed);

            for (String varName : updatedVarTable.keySet()) {
                method.getVarTable().put(varName, updatedVarTable.get(varName));
            }
        }

        // add report only if -r=n > 0 and n is insufficient
        if (localVariableNum > 0 && LocalsTopLimit > localVariableNum) {
            ollirResult.getReports().add(new Report(ReportType.ERROR, Stage.OPTIMIZATION, -1, -1,
                    "Insufficient -r=" + localVariableNum + ". Needs -r=" + LocalsTopLimit + "."));
        }
    }
}