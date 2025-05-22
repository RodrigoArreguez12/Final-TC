package com.primerproyecto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.Trees;
import org.antlr.v4.runtime.tree.ErrorNode;
import com.primerproyecto.tp2Parser.InstruccionContext;
import com.primerproyecto.tp2Parser.InstruccionesContext;
import com.primerproyecto.tp2Parser.LlamadaFuncionContext;
import com.primerproyecto.tp2Parser.DeclaracionContext;
import com.primerproyecto.tp2Parser.DefinicionFuncionContext;
import com.primerproyecto.tp2Parser.ArgsContext;
import com.primerproyecto.tp2Parser.ArgumentosContext;
import com.primerproyecto.tp2Parser.AsignContext;
import com.primerproyecto.tp2Parser.OperacionContext;
import com.primerproyecto.tp2Parser.ParametrosContext;
import com.primerproyecto.tp2Parser.OpalContext;
import com.primerproyecto.tp2Parser.FactorContext;
import com.primerproyecto.tp2Parser.TContext;
import com.primerproyecto.tp2Parser.TermContext;
import com.primerproyecto.tp2Parser.ExpContext;
import com.primerproyecto.tp2Parser.ExpresionContext;
import com.primerproyecto.tp2Parser.OpcompContext;
import com.primerproyecto.tp2Parser.CompContext;
import com.primerproyecto.tp2Parser.ComparacionesContext;
import com.primerproyecto.tp2Parser.CondifContext;
import com.primerproyecto.tp2Parser.ConjContext;
import com.primerproyecto.tp2Parser.ConjuncionContext;
import com.primerproyecto.tp2Parser.DisyContext;
import com.primerproyecto.tp2Parser.BucleForContext;
import com.primerproyecto.tp2Parser.BucleWhileContext;
import com.primerproyecto.tp2Parser.ProgContext;
import com.primerproyecto.tp2Parser.RetornoContext;
import com.primerproyecto.tp2Parser.AsignacionContext;
import com.primerproyecto.tp2Parser.BloqueContext;

public class myVisitor extends tp2BaseVisitor<String> {
    String texto;
    Integer indent; 
    List<ErrorNode> errores;
    String codigoDeTresDirecciones;
    //Usa esta dos variables a la hora de crear nombres para vars y labels
    // "t1","t2" (vars) y "l1" , "l2" (labels)
    int indexVariablesTemporales;
    int indexLabelsTemporales;
    //pila de variables temporales: push/pop de variables intermedias 
    //para crear cod intermedio
    Stack<String> pilaVariablesTemporales;
    // pila de labels temporales: push/pop de labels necesarios 
    //para crear cod intermedio
    Stack<String> pilaLabelsTemporales;
    //pila de codigo: push/pop de variable temps, labels y caracteres (< , >, +, &&, etc)
    Stack<String> pilaCodigo; //aux = 2 ->(2 , = , aux) -> aux = 2;
    //mapa de funciones: le pasamos una funcion y nos devuelve los labels asociados
    Map<String,String> funciones;
    //Printers para escribir en los archivos standars
    PrintWriter out;
    PrintWriter outOptimizado;
    
    public myVisitor() throws FileNotFoundException {
        errores = new ArrayList<>();
        indexVariablesTemporales = 0;
        indexLabelsTemporales = 0;
        pilaVariablesTemporales = new Stack<String>();
        pilaLabelsTemporales = new Stack<String>();
        pilaCodigo = new Stack<String>();
        funciones = new HashMap<String,String>();
        initString();
        out = new PrintWriter("codigo-intermedio.txt");
        outOptimizado = new PrintWriter("codigo-intermedio-optimizado.txt");
    }
    
    /**
     * Inicia el recorrido por el arbol desde el nodo indicado
     * 
     * @param tree La raiz desde donde comenzar, puede ser un subarbol
     */
    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public String visitProg(ProgContext ctx) {
        System.out.println("--> INIT visitProg");
        addTextoNodo(ctx, "prog");
        visitAllHijos(ctx);
        out.close();

        // texto += "} >- \n";
        return texto;
    }
    //instrucciones -> instruccion ->   
    //                              declaracion PYC --> int aux;              (*)
    //                              asignacion PYC --> aux = 2;               (*) arreglar x++;
    //                              bucleFor (...)                            (*)
    //                              bucleWhile (...)                          (*)
    //                              condif (...)                              (*)
    //                              declaracionFuncion PYC --> int sumarAux(); preguntar
    //                              definicionFuncion --> int sumarAux(){...} (*)
    //                              llamadaFuncion PYC --> sumarAux();        (*)
    //                              retorno PYC --> return opal;              (*)
    //                              bloque --> {codigo}                       (*)
    //                              falta optimizar el codigo intermedio
    //                              que ejercicios practicos van al final expresion regular - ASDescendente - ASAscendente
    @Override
    public String visitInstrucciones(InstruccionesContext ctx) {
        // texto += " -<(instrucciones) " + ctx.getChildCount() + " hijos -> ";
        addTextoNodo(ctx, "instrucciones");
        visitAllHijos(ctx.getRuleContext());
        // texto += "} >- \n";
        return texto;
    }
    
    @Override
    public String visitInstruccion(InstruccionContext ctx) {
        addTextoNodo(ctx, "instruccion");
        visitAllHijos(ctx);
        // texto += "} >- \n";
        return texto;
    }

    //Declaracion -> tipoDato ID;
    //            -> tipoDato ID asign --> IGUAL operacion -> opal
    @Override
    public String visitDeclaracion(DeclaracionContext ctx) {
        texto += " -<(declaracion) " + ctx.getChildCount() + " hijos -> ";
        System.out.println(texto);
        addTextoNodo(ctx, "declaracion");
        //si es solo declaracion sin asignacion no lo escribo directamente
        if(ctx.asign() == null){
        
        }
        else{ 
        //agrego el id de la declaracion a la pila codigo
        pilaCodigo.push(ctx.ID().getText());
        //agrego el id de la declaracion a la pila de var temps
        pilaVariablesTemporales.push(ctx.ID().getText());
        visitAllHijos(ctx);
        //asign -> igual operacion -> opal (recolecta datos para luego imprimir)
        imprimirCodigo();
        //saco la variable que habia guardado
        pilaCodigo.pop();
        }
    
        return texto;
    }

    //asign -> igual operacion -> opal
    @Override
    public String visitAsign(AsignContext ctx) {
        addTextoNodo(ctx, "asign");
        //agrego el signo IGUAL a la pila codigo 
        if(ctx.IGUAL() != null){
            pilaCodigo.push(ctx.IGUAL().getText()); 
            visitAllHijos(ctx);
            //imprimirCodigo();
        }
        else{
            pilaCodigo.push(ctx.SUM().get(0).getText()); 
            pilaCodigo.push(ctx.SUM().get(1).getText()); 
            imprimirCodigoBucles();
        }
        
        return texto;
    }
 
    //operacion -> opal 
    @Override
    public String visitOperacion(OperacionContext ctx) {
        addTextoNodo(ctx, "operacion");
        visitAllHijos(ctx);
        return texto;
    } 
    //
    @Override
    public String visitOpal(OpalContext ctx) {
        addTextoNodo(ctx, "opal");
        visitAllHijos(ctx);
        return texto;
    }
 
    //Asignacion -> ID asign
    @Override
    public String visitAsignacion(AsignacionContext ctx) {
        addTextoNodo(ctx, "asignacion");
        pilaCodigo.push(ctx.ID().getText());  //            i
        //pilaCodigo.push(ctx.IGUAL().getText());
        pilaVariablesTemporales.push(ctx.ID().getText());// i
        visitAllHijos(ctx);
        if (findRuleNodes(ctx.getChild(1), tp2Parser.RULE_llamadaFuncion).size() == 0)
        {
        imprimirCodigo();
        //saca la variable q ya proceso de la pila codigo
        pilaCodigo.pop();
        System.out.println("pilaCodigo: " + pilaCodigo);
        }else{
        out.println("pop " + ctx.ID().getText());
        pilaCodigo.pop();
        pilaVariablesTemporales.pop();
        }
      return texto;
    }

    /* ********************************BUCLE FOR******************************** */
    //bucleFor : FOR PA inicializacion PYC operacion PYC ID asign PC instruccion
     @Override
    public String visitBucleFor(BucleForContext ctx) {
        addTextoNodo(ctx, "bucleFor");
        String labelIn = nuevoLabel();  //l1
        String labelOut = nuevoLabel(); //l2
        //Vamos a procesar la inicializacion -> asignacion | declaracion
        visit(ctx.inicializacion());                       //x = 0
        // System.out.println("label " + labelIn);            //l1
        out.println("label " + labelIn);
        visit(ctx.operacion());                            //t0 = x < 3
        String temp = pilaCodigo.pop();
        // System.out.println("ifnjmp " + temp + ", " + labelOut ); 
        out.println("ifnjmp " + temp + ", " + labelOut );  //ifnjmp t0, l2
        visit(ctx.instruccion());                          //instruccion o bloque de cod
        pilaVariablesTemporales.push(ctx.ID().getText());
        pilaCodigo.push(ctx.ID().getText());
        visit(ctx.asign());                                //x++
        // System.out.println("jmp " + labelIn);
        out.println("jmp " + labelIn);
        // System.out.println("label " + labelOut);
        out.println("label " + labelOut);
        texto += " -<(bucleFor) " + ctx.getChildCount() + " hijos -> ";
        System.out.println(texto);
        return texto;
    }
    /* ********************************BUCLE WHILE******************************** */
    //bucleWhile : WHILE PA operacion PC instruccion
    //problemas con el x++;
    @Override
    public String visitBucleWhile(BucleWhileContext ctx) {
        addTextoNodo(ctx, "BucleWhile");
        texto += " -<(bucleWhile) " + ctx.getChildCount() + " hijos -> ";
        System.out.println(texto);
        String labelIn = nuevoLabel();
        String labelOut = nuevoLabel();
        // System.out.println("label " + labelIn);
        out.println("label " + labelIn);
        visit(ctx.operacion());
        String temp = pilaCodigo.pop();
        // System.out.println("ifnjmp " + temp + ", " + labelOut );
        out.println("ifnjmp " + temp + ", " + labelOut );
        visit(ctx.getChild(4));
        // System.out.println("jmp " + labelIn);
        out.println("jmp " + labelIn);
        // System.out.println("label " + labelOut);
        out.println("label " + labelOut);
        
        return texto;
    }

    /* ********************************COND IF ******************************** */
    //condif : IF PA operacion PC bloque
    //       | IF PA operacion PC bloque ELSE bloque
   @Override
    public String visitCondif(CondifContext ctx) {
        addTextoNodo(ctx, "IF");
        visit(ctx.operacion());
        pilaLabelsTemporales.push(nuevoLabel());
        String temp = pilaCodigo.pop();
        // System.out.println("ifnjmp " + temp + ", " + pilaLabelsTemporales.lastElement());
        out.println("ifnjmp " + temp + ", " + pilaLabelsTemporales.lastElement());
        visit(ctx.getChild(4));
        if(ctx.ELSE() == null){
            String temporal = pilaLabelsTemporales.pop();
            // System.out.println("label " + temporal);
            out.println("label " + temporal);
        }else{
            String labelExit = nuevoLabel();
            // System.out.println("jmp " + labelExit);
            out.println("jmp " + labelExit);
            temp = pilaLabelsTemporales.pop();
            // System.out.println("label " + temp);
            out.println("label " + temp);
            visit(ctx.getChild(6));
            // System.out.println("label " + labelExit);
            out.println("label " + labelExit);
        }

        return texto;
    }

    @Override
    public String visitBloque(BloqueContext ctx) {
        addTextoNodo(ctx, "bloque");
        visitAllHijos(ctx);
        return texto;
    }

    //El codigo intermedio de un prototipo de funcion no se escribe por eso
    //sacamos esta funcion de visitDeclaracionFuncion

    //declaracionFuncion : tipoDato ID PA parametros PC PYC
    /* ********************************DECLARACION FUNCION******************************** */
/*     @Override
    public String visitDeclaracionFuncion(DeclaracionFuncionContext ctx) {
        addTextoNodo(ctx, "visitDeclaracionFuncion");

        String label = nuevoLabel();
        funciones.put(ctx.ID().getText(), label);

        generadorNombresTemporales();
        pilaCodigo.push("pop");

        // System.out.println("-----    VISIT DECLARACION FUNCION    -----");
        System.out.println("label " + label);
        out.println("label " + label);
        imprimirCodigo();
        visitAllHijos(ctx);
        String temp = pilaCodigo.pop();
        System.out.println("jmp " + temp);
        out.println("jmp " + temp);
        return texto;
    }  */
    //definicionFuncion : tipoDato ID PA parametros PC bloque
    /* ********************************DEFINICION FUNCION******************************** */
    @Override
    public String visitDefinicionFuncion(DefinicionFuncionContext ctx) {
        addTextoNodo(ctx, "visitDefinicionFuncion");

        //Genero label y lo guardo junto el id de la funcion en el map
        String label = nuevoLabel();
        funciones.put(ctx.ID().getText(), label); //funciones <sumar, l3>
        //Genero variable temporal y la guardo en la pila
        generadorNombresTemporales();  // t11
        
        // System.out.println("label " + label);
        out.println("label " + label); //label l3
        pilaVariablesTemporales.pop();
        pilaCodigo.pop();
        String temp = pilaCodigo.pop();
        out.println("pop " + temp); //temp
        //imprimirCodigo();
        visitAllHijos(ctx);
        // System.out.println("jmp " + temp);
        out.println("jmp " + temp);
        return texto;
    }
    //retorno : RETURN opal;
    @Override
    public String visitRetorno(RetornoContext ctx) {
        addTextoNodo(ctx, "visitRetorno");
        visitAllHijos(ctx);
       
        String temp = pilaCodigo.pop();
        // System.out.println("push " + temp);
        out.println("push " + temp);
        return texto;
    }
    /* ********************************LLAMADA FUNCION******************************** */
    //llamadaFuncion : ID PA argumentos PC
     @Override
    public String visitLlamadaFuncion(LlamadaFuncionContext ctx) {
        addTextoNodo(ctx, "llamadaFuncion");
        visitAllHijos(ctx);
        
        String label = nuevoLabel();
        // System.out.println("push " + label);
        out.println("push " + label);
        String funcion = ctx.ID().getText();
        // System.out.println("jmp " + funciones.get(funcion));
        out.println("jmp " + funciones.get(funcion));
        // System.out.println("label " + label);
        out.println("label " + label);
        pilaCodigo.push("pop");
        return texto;
    } 

    //parametros : tipoDato ID param
    @Override
    public String visitParametros(ParametrosContext ctx) {
        addTextoNodo(ctx, "visitParametros");
        if (ctx.ID() != null){

            out.println("pop " + ctx.ID().getText());
            visitAllHijos(ctx);
        }
        return texto;
    }

    //argumentos : operacion args
    @Override
    public String visitArgumentos(ArgumentosContext ctx) {
        addTextoNodo(ctx, "visitArgumentos");

        List<ParseTree> ruleFactors = findRuleNodes(ctx.operacion(), tp2Parser.RULE_factor);
        Boolean param = false;
        for(ParseTree ruleFactor : ruleFactors){
            FactorContext fc = ((FactorContext)ruleFactor);
            if(fc.getChildCount() > 0){
                param = true;
            }
        }
        if(param) {
            visitAllHijos(ctx);

            LinkedList<String> listaVariables = new LinkedList<String>();

            while(!pilaCodigo.lastElement().equals("="))
                listaVariables.add(pilaCodigo.pop());

            Iterator<String> iterator = listaVariables.descendingIterator();
            if(listaVariables.size() > 0){
                while (iterator.hasNext()){
                    out.println("push " + iterator.next());
                }
            } 
        }
        return texto;
    }
    //Args  : COMA operacion args
    //       |
    @Override
    public String visitArgs(ArgsContext ctx) {
        addTextoNodo(ctx, "Args");
        visitAllHijos(ctx);
        return texto;
    }

    @Override
    public String visitOpcomp(OpcompContext ctx) {
        addTextoNodo(ctx, "opcomp");
        visitAllHijos(ctx);
        return texto;
    }

  
    @Override
    public String visitConj(ConjContext ctx) {
        addTextoNodo(ctx, "conj");
        
        if (ctx.getChildCount() != 0){
            pilaCodigo.push(ctx.getChild(0).getText());

            Boolean hijos = true;
            for (int i = 1; i < ctx.getChildCount(); i++) {
                if (findRuleNodes(ctx.getChild(i), tp2Parser.RULE_factor).size() == 0)
                    hijos = false;
            }

            if (hijos) {
                generadorNombresTemporales();
                visitAllHijos(ctx);
                imprimirCodigo();
            }
            else
                visitAllHijos(ctx);
        }

        return texto;
    }

    @Override
    public String visitConjuncion(ConjuncionContext ctx) {
        addTextoNodo(ctx, "conjuncion");
        
        Boolean hijos = true;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (findRuleNodes(ctx.getChild(i), tp2Parser.RULE_factor).size() == 0)
                hijos = false;
        }

        if (hijos) {
            generadorNombresTemporales();
            visitAllHijos(ctx);
            imprimirCodigo();
        }
        else
            visitAllHijos(ctx);

        return texto;
    }

    @Override
    public String visitComp(CompContext ctx) {
        addTextoNodo(ctx, "comp");
        if (ctx.getChildCount() != 0){
            pilaCodigo.push(ctx.getChild(0).getText());
            visitAllHijos(ctx);
        }
        return texto;
    }

    @Override
    public String visitComparaciones(ComparacionesContext ctx) {
        addTextoNodo(ctx, "comparaciones");

        Boolean hijos = true;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (findRuleNodes(ctx.getChild(i), tp2Parser.RULE_factor).size() == 0)
                hijos = false;
        }

        if (hijos) {
            generadorNombresTemporales();
            visitAllHijos(ctx);
            imprimirCodigo();
        }
        else
            visitAllHijos(ctx);

        return texto;
    }

    @Override
    public String visitExp(ExpContext ctx) {
        addTextoNodo(ctx, "exp");

        if (ctx.getChildCount() != 0){
            pilaCodigo.push(ctx.getChild(0).getText());

            Boolean hijos = true;
            for (int i = 1; i < ctx.getChildCount(); i++) {
                if (findRuleNodes(ctx.getChild(i), tp2Parser.RULE_factor).size() == 0)
                    hijos = false;
            }

            if (hijos) {
                generadorNombresTemporales();
                visitAllHijos(ctx);
                imprimirCodigo();
            }
            else
                visitAllHijos(ctx);
        }

        return texto;
    }

    @Override
    public String visitExpresion(ExpresionContext ctx) {
        addTextoNodo(ctx, "expresion");
        texto += " -<(expresion) " + ctx.getChildCount() + " hijos -> ";
        System.out.println(texto);
        Boolean hijos = true;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (findRuleNodes(ctx.getChild(i), tp2Parser.RULE_factor).size() == 0)
                hijos = false;
        }
        //al tener 2 hijos creo una variable temporal 
        if (hijos) {
            generadorNombresTemporales();
            visitAllHijos(ctx);
            imprimirCodigo();
        }
        else
            visitAllHijos(ctx);

        return texto;
    }


    @Override
    public String visitFactor(FactorContext ctx) {
        addTextoNodo(ctx, "factor");

        if(ctx.getChildCount() == 1 && ctx.llamadaFuncion() == null)
            pilaCodigo.push(ctx.getChild(0).getText());

        visitAllHijos(ctx);

        return texto;
    }

    @Override
    public String visitDisy(DisyContext ctx) {
        addTextoNodo(ctx, "disy");
    
        if (ctx.getChildCount() != 0){
            pilaCodigo.push(ctx.getChild(0).getText());

            Boolean hijos = true;
            for (int i = 1; i < ctx.getChildCount(); i++) {
                if (findRuleNodes(ctx.getChild(i), tp2Parser.RULE_factor).size() == 0)
                    hijos = false;
            }

            if (hijos) {
                generadorNombresTemporales();
                visitAllHijos(ctx);
                imprimirCodigo();
            }
            else
                visitAllHijos(ctx);
        }

        return texto;
    }


    @Override
    public String visitTerm(TermContext ctx) {
        addTextoNodo(ctx, "term");

        if (ctx.t().getChildCount() > 0) {
            generadorNombresTemporales();
            visitAllHijos(ctx);
            imprimirCodigo();
        }
        else
            visitAllHijos(ctx);

        return texto;
    }

    @Override
    public String visitT(TContext ctx) {
        addTextoNodo(ctx, "t");

        if (ctx.getChildCount() != 0){
            pilaCodigo.push(ctx.getChild(0).getText());

            if (ctx.t().getChildCount() > 0) {
                generadorNombresTemporales();
                visitAllHijos(ctx);
                imprimirCodigo();
            }
            else
                visitAllHijos(ctx);
        }

        return texto;
    }


     /* 
     * @param ctx Contexto del nodo donde estamos parados
     */
    public String visitAllHijos(RuleContext ctx) {
        incrementarIndentacion();
        for (int hijo = 0; hijo < ctx.getChildCount(); hijo++) {
            addTextoNuevoNodo();
            visit(ctx.getChild(hijo));
        }
        decrementarIndentacion();
        return texto;
    }

    private void initString() {
        //texto = "**Caminante**\n  |\n  +--> ";
        //indent = -1;
    }
    
    private void incrementarIndentacion() {
        //indent += 1;
    }

    private void decrementarIndentacion() {
        //indent -= 1;
    }

    private void addTextoNodo(RuleContext ctx, String nombre) {
        //texto += "(" + nombre + ") " + ctx.getChildCount() + " Hijos\n";

    }
    private void addTextoNuevoNodo() {
        //texto += "     " + "  |  ".repeat(indent) + "  +--> ";
    }

    @Override
    public String toString() {
        return texto;
    }

    
    // Obtengo un subarbol en formato ArrayList segun la regla que especifiquemos
    
    private List<ParseTree> findRuleNodes(ParseTree ctx, int ruleIndex) {
        return new ArrayList<ParseTree>(Trees.findAllRuleNodes(ctx, ruleIndex));
    }
    
    //esta funcion genera nombres para variables temporales "t1","t2","t3",etc.
    // guarda la variable en la pila de vars, codigo quedando pilas(= , t1)
    private void generadorNombresTemporales() {
        String temporal = "t" + indexVariablesTemporales;
        indexVariablesTemporales++;
        pilaVariablesTemporales.push(temporal);
        pilaCodigo.push(temporal);
        pilaCodigo.push("=");
    }

    //Genera el nombre para un label temporal de la forma "l1","l2"...
    private String nuevoLabel() {
        return "l" + indexLabelsTemporales++;
    }

   
    //Esta funcion agarra la ultima variable temporal y va recorriendo la pila codigo hasta
    //matchear, pero mientras va buscando va guardando los elementos en una lista para una vez matchee
    //poder imprimir el codigo en la salida standar y en consola
    //ejemplo: pilaVariablesTemporales(aux), pilaCodigo(2,=,aux)
    //guarda en la lista codigo (= , 2) y la salida armada es aux = 2

    private void imprimirCodigo() {
     
        String varTemp = pilaVariablesTemporales.pop();
        List<String> codigo = new LinkedList<String>();

        while(!varTemp.equals(pilaCodigo.lastElement())){
            codigo.add(0, pilaCodigo.pop());
        }

        // System.out.print(varTemp + " ");
        out.print(varTemp + " ");
        for(int i = 0; i < codigo.size(); i++){
            // System.out.print(codigo.get(i) + " ");
            out.print(codigo.get(i) + " ");
        }
        // System.out.println("");
        out.println("");
    }

    private void imprimirCodigoBucles() {
        /* System.out.println("pilaCodigo: " + pilaCodigo);
        System.out.println("pilaVariablesTemporales: " + pilaVariablesTemporales); */
        String varTemp = pilaVariablesTemporales.pop();
        /* System.out.println("varTemp: " + varTemp); */
        List<String> codigo = new LinkedList<String>();

        while(!varTemp.equals(pilaCodigo.lastElement())){
            codigo.add(0, pilaCodigo.pop());
        }

        out.print(varTemp + " " + "= " + varTemp + codigo.get(0) + "1\n");
        // System.out.print(varTemp + " " + "= " + varTemp + codigo.get(0) + "1");

    }
  
    
}
