import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import javax.swing.*;
import java.util.Arrays;

import ast.*;

public class example {

    public static void main(String[] args) {
        String input = """
                int x = 10;
                int y;
                std::cout << "Hello, C++!";
                """;

        CharStream charStream = CharStreams.fromString("#include <iostream>\n\nint main(char *argv, int argc){\nint x = 5+ getSize(list) -2;\nbool y = true;\n vector<int> ovo = new vector<int>(0,5);\n int *x = 5;\n cout << \"Hello, C++!\";\n}");

        CPP14Lexer lexer = new CPP14Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);
        //parser.setTrace(true);
        ParseTree tree = parser.translationUnit();

        ASTBuilder builder = new ASTBuilder();
        ASTNode ast = builder.visit(tree);
        System.out.println("------------------------");
        System.out.println(ast);
        System.out.println("------------------------");
        JFrame frame = new JFrame("ANTLR");
        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewer.setScale(1.0);

        frame.add(viewer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);


        System.out.println(tree.toStringTree(parser));
    }

}
