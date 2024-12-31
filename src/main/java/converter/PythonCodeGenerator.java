package converter;

import ast.ASTNode;
import ast.FunctionNode;
import ast.TranslationUnitNode;

import java.util.List;

public class PythonCodeGenerator {


    public String convertProgram(ASTNode node) {

        return node.convert();
    }



}
