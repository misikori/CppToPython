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

        CharStream charStream = CharStreams.fromString("#include<iostream>\n\n int main(){\n" +
                "if(x>=y){ x = a || b || (c && d);} \n else{ x = 3;}\n" +
                "    switch (choice) {\n" +
                "        case 1:\n" +
                "            cout << \"You selected option 1.\" << endl;\n" +
                "            break;\n" +
                "        default:\n" +
                "            cout << \"Invalid choice. Please select a number between 1 and 3.\" << endl;\n" +
                "            break;\n" +
                "    } \n" +
                "std::cout << \"Hello, C++!\";\n}");

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
        viewer.setScale(1.0); // Adjust the scale as needed
        JScrollPane scrollPane = new JScrollPane(viewer);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);


        System.out.println(tree.toStringTree(parser));
    }

}
