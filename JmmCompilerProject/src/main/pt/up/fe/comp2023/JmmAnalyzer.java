package pt.up.fe.comp2023;

import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.parser.JmmParserResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp2023.Semantic.SymbolTable;
import pt.up.fe.comp2023.Semantic.SemanticVisitor;

import java.util.ArrayList;
import java.util.List;

public class JmmAnalyzer implements JmmAnalysis {
    @Override
    public JmmSemanticsResult semanticAnalysis(JmmParserResult jmmParserResult) {


        List<Report> reports = new ArrayList<>();
        JmmNode node= jmmParserResult.getRootNode();
        SymbolTable symbolTable = SymbolTable.build(node);


        SemanticVisitor typeVerification = new SemanticVisitor(symbolTable, reports);
        typeVerification.visit(node);
        reports = typeVerification.getReports();


        for(int i = 0; i < reports.size(); i++){
            System.out.println(reports.get(i));
            //System.out.println("THIS IS A TEST");
        }





        //symbolTable.printTable();


        return new JmmSemanticsResult(jmmParserResult, symbolTable, reports);
    }
}
