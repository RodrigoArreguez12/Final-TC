package com.primerproyecto;

import org.antlr.v4.runtime.tree.ParseTree;

import com.primerproyecto.tablaSimbolos.ErrorsListener;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, Compilador!!!");
        System.out.println("TC Final - Arreguez");
        // create a CharStream that reads from file

        CharStream input = CharStreams
                .fromFileName("D:\\UBP\\TC\\Final F- Tc\\TPFinal-4\\primerproyecto\\input\\mate.txt");

        System.out.println(input.getSourceName());
        // create a lexer that feeds off of input CharStream

        tp2Lexer lexer = new tp2Lexer(input);

        // create a buffer of tokens pulled from the lexer
        // Entra texto -> Salen tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        // El parser es el analizador sintáctico
        // programaParser parser = new programaParser(tokens);
        tp2Parser parser = new tp2Parser(tokens);

        ErrorsListener errorsListener = new ErrorsListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorsListener);
        // create Listener -> activar el listener una vez creado
        // construyendo un objeto MiListener
        // ExpRegBaseListener escucha = new Escucha();
        tp2BaseListener escucha = new MyListener();
        // Conecto el objeto con Listeners al parser
        // parser.addParseListener(escucha);
        parser.addParseListener(escucha);
        System.out.println("\n");
        // Solicito al parser que comience indicando una regla gramatical
        // En este caso la regla es el simbolo inicial
        // parser.prog();
        // ParseTree tree = parser.s();
        ParseTree tree = parser.prog();
        ;
        if (!((MyListener) escucha).getError()) {
            // Conectamos el visitor
            myVisitor visitor = new myVisitor();
            visitor.visit(tree);
            optimizador Optimizado = new optimizador();
            Optimizado.optimizarCodigo();

        }

    }
}