package com.primerproyecto;

import org.antlr.v4.runtime.tree.Trees;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import com.primerproyecto.tp2Parser.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import com.primerproyecto.errors.CustomErrors;
import com.primerproyecto.tablaSimbolos.Funcion;
import com.primerproyecto.tablaSimbolos.Id;
import com.primerproyecto.tablaSimbolos.TablaSimbolos;
import com.primerproyecto.tablaSimbolos.Variable;


public class MyListener extends tp2BaseListener {

    private TablaSimbolos simbolos = new TablaSimbolos();
    private CustomErrors errors = new CustomErrors();
    private Boolean error = false;

    /*
     *  Devuelve la linea del token seguido del indice en la linea donde comienza el token
     */
    private String posicionDelToken(Token token){
        return "[" + token.getLine() + ":" + token.getCharPositionInLine() + "]";
    }

     /*
     *  Compara los tipos de datos recibidos por parametro, si son iguales retorna true sino false
     */
    private boolean coincideTipoDato(String tipoDato, String tipoDatoFactor) {
        if (tipoDato.equals(tipoDatoFactor))
            return true;
        else
            return false;
    }

    /*
     *  Switch que lee un token y lo mapea a su valor correspondiente
     */
    private String getTipoString(int type) {
        switch (type) {
            case tp2Lexer.INTEGER:
                return "int";
            case tp2Lexer.DECIMAL:
                return "double";
            case tp2Lexer.CHARACTER:
                return "char";
            default:
                return null;
        }
    }
    /*
     *  Busca un token en la tabla de simbolos
     */
    private Id getID(String id) { return simbolos.buscarIdGlobal(id); }

    /*
     *  Busca un token en el contexto actual
     */
    private Id getIDLocal(String id){ return simbolos.buscarIdLocal(id); }

     /*
     *  Se fija que el id recibido por parametro sea instancia de Funcion
     */
    private boolean esFuncion(Id id){ return id instanceof Funcion; }

    /*
        public static <T extends ParseTree> List<T> findAllRuleNodes(ParseTree tree, int ruleIndex)
        Donde tree es el árbol de análisis sintáctico en el que se desea buscar los nodos de reglas, y ruleIndex es el índice de la regla que se está buscando.
     *  factores       es una coleccion de reglas que contengan la regla RULE_factor
     *  llamadaFuncion es una coleccion de reglas que contengan la regla RULE_llamadaFuncion
     *  
     *  Devuelve una coleccion de factores que no son funciones  
     */
    private Collection<ParseTree> encontrarFactoresSinArgumentos(ParseTree ctx){
        // findAllRuleNodes: Encuentra todos los nodos de reglas en un árbol de análisis sintáctico
        Collection<ParseTree> factores = Trees.findAllRuleNodes(ctx, tp2Parser.RULE_factor);
        Collection<ParseTree> llamadaFuncion = Trees.findAllRuleNodes(ctx, tp2Parser.RULE_llamadaFuncion);
        Collection<ParseTree> factoresLlamadaFuncion;
        for (ParseTree parseTree : llamadaFuncion) {
            factoresLlamadaFuncion = Trees.findAllRuleNodes(parseTree, tp2Parser.RULE_factor);
            factores.removeAll(factoresLlamadaFuncion);
        }
        return factores;
    }
    

    /*
     * parametros es una coleccion de reglas de tipo declaración
     * Va iterando por cada declaración, si el ID de la declaración ya existe informa un error, sino
     * crea la variable y la agrega a la List<Variable> params
     * 
     * retorna una lista de tipo Variable
     */
/*      private List<Variable> getParametros(ParametrosContext ctx){
        List<Variable> params = new ArrayList<Variable>();
        Collection<ParseTree> parametros = Trees.findAllRuleNodes(ctx, tp2Parser.RULE_declaracion);
        for (ParseTree parseTree : parametros) {
            DeclaracionContext decl = (DeclaracionContext)parseTree;
            if(tieneParametros(params, decl.ID().getText())){
                errors.idRedefinido(posicionDelToken(ctx.getStart()), decl.ID().getText());
                return null;
            }
            Variable var = new Variable(true, false, decl.tipoDato().getText(), decl.ID().getText());
            params.add(var);
        }
        return params;
    } 
 */

/*  parametros : tipoDato ID param
                |
                ;

    param : COMA parametros
          | 
          ;
*/

    /*
     * la función tieneParametros retorna true si hay un elemento en la lista parametros cuyo nombre coincide con el valor de nameVar, y false en caso contrario.
     */
 /*    private boolean tieneParametros(List<Variable> parametros, String nameVar){
        return parametros.stream()
                         .filter(param -> param.getTokenNombre().equals(nameVar))
                         .findFirst()
                         .isPresent();
    } */


    private boolean analisisFactores(Id caller, String tipoDato, Collection<ParseTree> factores) {
        FactorContext fc;
        boolean error = true;
        for (ParseTree parseTree : factores) {
            fc = (FactorContext) parseTree;
            
            if (fc.ID() != null){
                String idFactor = fc.ID().getText();
                Id id = getID(idFactor); //busca el id del factor en todos los contextos
                /*chequea que
                  el token esté o no declarado
                  el token esté o no inicializado
                  el token esté o no usado
                */
                if (id == null){
                    setError(true);
                    errors.idNoDeclarado(posicionDelToken(fc.getStart()), idFactor);
                    error = false;
                }else if(!id.isInicializado()){
                    setError(true);
                    errors.idNoInicializado(posicionDelToken(fc.getStart()), idFactor);
                    error = false;
                }else{
                    /*
                     * Si esta declarado e inicializado, significa que el factor está usado
                     * y nos fijamos que esten bien los tipos de datos, y si es funcion que coincidan los tipos de datos de los parametros con los argumentos
                     */
                    id.setUsado(true);
                    if (tipoDato != null  && !coincideTipoDato(tipoDato, id.getTipoDato())){
                        if(esFuncion(caller)){
                            errors.idDiferenteTiposParamArgs(posicionDelToken(fc.getStart()), id.getTipoDato(), tipoDato, id.getTokenNombre());
                        } 
                        else{
                            errors.idDistintosTipos(posicionDelToken(fc.getStart()), id.getTipoDato(), tipoDato);
                        }
                    }
                }
            // si no es un ID, se fija que sea una llamada a función, si lo es la procesa
            } else if(fc.llamadaFuncion() != null){
                procesarLlamadaFuncion(tipoDato, fc.llamadaFuncion());
            //id es una variable, entonces chequeamos si es un numero o un caracter
            } else if (fc.INTEGER() != null || fc.DECIMAL() != null || fc.CHARACTER() != null) {
                if (tipoDato != null  && !coincideTipoDato(tipoDato, getTipoString(fc.getStop().getType()))){
                    if(esFuncion(caller)){
                        errors.idDiferenteTiposParamArgs(posicionDelToken(fc.getStart()), getTipoString(fc.getStop().getType()), tipoDato, fc.getText());
                    }
                    else{
                        errors.idDistintosTipos(posicionDelToken(fc.getStart()), getTipoString(fc.getStop().getType()), tipoDato);
                    }
                }
            }
        }
        return error;
    }

    /*
     *  procesa una asignación, se fija que sus factores no pertenezcan a una funcion y sean correctos
     */
    private void procesarInstruccionAsignacion(AsignacionContext ctx) {
        Id id = getID(ctx.ID().getText());
        if (id != null) {
            Collection<ParseTree> factores = encontrarFactoresSinArgumentos(ctx);
            if(analisisFactores(null, id.getTipoDato(), factores))
                id.setInicializado(true);
        } else{
            setError(true);
            errors.idNoDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        }
    }

    /*
     * se fija que si es una declaración, la agregue al contexto actual
     * si ya está declarada, la inicializa
     * si esta declarada e inicializada informa error 
     */
    private void procesarDeclaracion(DeclaracionContext ctx) {
        Collection<ParseTree> factores = encontrarFactoresSinArgumentos(ctx);
        if (getIDLocal(ctx.ID().getText()) == null) {
            Id id = new Variable(false, false, ctx.tipoDato().getText(), ctx.ID().getText());
            simbolos.agregarEnContextoActual(ctx.ID().getText(), id);
            if (ctx.asign() != null) {
                if(analisisFactores(null, ctx.tipoDato().getText(), factores))
                    id.setInicializado(true);
            }
        } else{
            analisisFactores(null, ctx.tipoDato().getText(), factores);
            setError(true);
            errors.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        }
    }

    private Id procesarFuncion(DefinicionFuncionContext ctx){
        Id id = getIDLocal(ctx.ID().getText());
        //Si no se encuentra el ID en el ctx actual lo agrego al mismo y retorno el ID 
        if(id == null){
            List<Variable> parametros = getParametros(ctx.parametros());
            if(parametros != null){
                Id idFunction = new Funcion(true, false, ctx.tipoDato().getText(), ctx.ID().getText(), parametros);
                simbolos.agregarEnContextoActual(ctx.ID().getText(), idFunction);
                return idFunction;
            }
        // Si se encuentra en el ctx actual y no es funcion entonces ya esta declarado    
        }else if(!esFuncion(id)){
            setError(true);
            errors.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        // Si se encuentra en el ctx actual y es una funcion y esta inicializada entonces ya esta declarada  
        }else if(esFuncion(id) && id.isInicializado()){
            setError(true);
            errors.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        // Si se encuentra en el ctx actual y es una funcion y no esta inicializada
        //chequeamos que los parametros sean los correctos e inicializamos y devolvemos el Id
        }else if(esFuncion(id) && !id.isInicializado()){
            if(!((Funcion)id).compararParametros(getParametros(ctx.parametros()))){
                setError(true);
                errors.ordenPrototipo(posicionDelToken(ctx.getStart()));
            }
            else
                id.setInicializado(true);
                return id;
        }
        //Si retorno NULL es que encontre el ID en el ctx actual y esta inicializado
        return null;
    }

    private void procesarLlamadaFuncion(String tipoDato, LlamadaFuncionContext ctx){
        // se trae el nombre de la funcion
        Id id = getID(ctx.ID().getText());
        //arma un arbol sintactico donde sus nodos son las reglas sintacticas de operacion
        Collection<ParseTree> operations = Trees.findAllRuleNodes(ctx, tp2Parser.RULE_operacion);
        Object[] oper = operations.toArray();
        //los meto en un array para obtener subarboles de cada factor de esa operacion
        Collection<ParseTree> factorCountFirstOperation = Trees.findAllRuleNodes((ParseTree)oper[0], tp2Parser.RULE_factor);
        //si el id es nulo, informo error
        if(id == null){
            setError(true);
            errors.idNoDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
        }
        /* si es una instancia de funcion y esta inicializada
        *  se trae los parametros 
        */
        else if(esFuncion(id) && id.isInicializado()){
            List<Variable> parametros = ((Funcion)id).getParametros();
            Collection<ParseTree> factores;
            //si es función de tipo void, nos fijamos que el tipo de dato sea nulo 
            if(tipoDato != null && id.getTipoDato().equals("void")){
                setError(true);
                errors.funcionVoid(posicionDelToken(ctx.getStart()), id.getTokenNombre());
            }
            //se fija que la cantidad de parametros coincida con la de los argumentos
            else if(factorCountFirstOperation.size() > 0 && ((Funcion)id).getParametros().size() != operations.size()){
                setError(true);
                errors.cantidadParamArgsDistinta(posicionDelToken(ctx.getStart()));
            }
            //se fija que la los tipos de datos de los parametros coincida con el de los argumentos
            else if(tipoDato != null && !coincideTipoDato(tipoDato, id.getTipoDato())){
                errors.idDistintosTipos(posicionDelToken(ctx.getStart()), id.getTipoDato(), tipoDato);
            }
            
            //analizamos cada parametro de la funcion 
            id.setUsado(true);
            for(int i=0; i < parametros.size();  i++){
                if(i > (operations.size() - 1)) { break; }
                factores = Trees.findAllRuleNodes((ParseTree)oper[i], tp2Parser.RULE_factor);
                analisisFactores(id, parametros.get(i).getTipoDato(), factores);
            }
            //es una instancia de funcion pero no esta declarada 
        }else if(esFuncion(id) && !id.isInicializado()){
            setError(true);
            errors.funcionNoDeclarada(posicionDelToken(ctx.getStart()), id.getTokenNombre());
            //no es instancia de funcion entonces es una variable 
        }else if(!esFuncion(id))
           setError(true);
            errors.declaradoComoVariable(posicionDelToken(ctx.getStart()), id.getTokenNombre());
    }

    private void procesarRetorno(DefinicionFuncionContext ctx){
        //Se queda con los nodos de reglas de retorno RETURN opal
        Collection<ParseTree> returns = Trees.findAllRuleNodes(ctx, tp2Parser.RULE_retorno);
        //Chequeamos que si es una funcion void no retorne nada
        if(ctx.tipoDato().getText().equals("void") && returns.size() != 0){
            setError(true);
            errors.retornoFuncionVoid(posicionDelToken(((RetornoContext)(returns.toArray()[0])).getStart()));
        }
        //Si no es funcion void ni funcion MAIN controlo que tenga un Return    
        else if(!ctx.tipoDato().getText().equals("void") && !ctx.ID().getText().equals("main") && returns.size() == 0){
        setError(true);    
        errors.noRetorno(posicionDelToken(ctx.getStart()));
        }
        //Chequeamos ,que si no es void y retorna algo, el tipo de dato coincida
        //El error se mostraria en la funcion de analisisFactores
        else if(!ctx.tipoDato().getText().equals("void") && returns.size() > 0){
            ParseTree ret = (ParseTree)(returns.toArray()[0]);
            Collection<ParseTree> factores = encontrarFactoresSinArgumentos(ret);
            analisisFactores(null, ctx.tipoDato().getText(), factores);
        }
    }

    @Override
    public void enterProg(tp2Parser.ProgContext ctx) {
        System.out.println("Comienza programa");
        simbolos.agregarContexto();
    }

    @Override
    public void exitProg(tp2Parser.ProgContext ctx) {
        //Retorna listado de variables sin usar
    
        errors.idNoUsado(simbolos.idSinUsar());
        //Quita el contexto general
        simbolos.quitarContexto();
        
        System.out.println("Finaliza programa");
    }

    @Override
    public void exitAsignacion(AsignacionContext ctx) {
        System.out.println("Exit de la asignacion del for " + ctx.getText());
        procesarInstruccionAsignacion(ctx);
    }
    
 ///agrego Kevin/////
     @Override
    public void enterInstruccion(InstruccionContext ctx) {
        System.out.println("Enter instruccion " + ctx.getText());
    }
    @Override
    public void enterBucleFor(BucleForContext ctx) {
        simbolos.agregarContexto();
        System.out.println("Entre bucleFor " + ctx.getText());
    }
    @Override
    public void exitBucleFor(BucleForContext ctx) {
        simbolos.quitarContexto();
        System.out.println("Salgo del bucleFor " + ctx.getText());
    }
    
     @Override
    public void enterDeclaracion(DeclaracionContext ctx) {
        if(ctx.getParent() instanceof BucleForContext){
            System.out.println("Enter de la declaracion de un for " + ctx.getText());
            //agrego la declaracion para usarla dentro del bucleFor 
        }  
    }
    @Override
    public void exitDeclaracion(DeclaracionContext ctx) {
        if(ctx.getParent() instanceof InicializacionContext){
            Id ide = getID(ctx.ID().getText());
            if(ide == null){
            System.out.println("Exit de la declaracion del for " + ctx.getText());
            //agrego la declaracion para usarla dentro del bucleFor
            Id id = new Variable(true, false, ctx.tipoDato().getText(), ctx.ID().getText());
            simbolos.agregarEnContextoActual(ctx.ID().getText(), id);
            }
            else{
                setError(true);
                errors.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
            } 
           
        }  
    }
    @Override
    public void enterOperacion(OperacionContext ctx) {
        if(ctx.getParent() instanceof BucleForContext){
            System.out.println("Enter operacion de un for " + ctx.getText());
        }
        
    }
    
    @Override 
    public void exitOperacion(OperacionContext ctx) { 
        if( !(ctx.getParent() instanceof AsignContext) &&
                !(ctx.getParent() instanceof ArgumentosContext) &&
                !(ctx.getParent() instanceof ArgsContext)){
            Collection<ParseTree> factores = encontrarFactoresSinArgumentos(ctx);
            //Chequeamos que los factores de la operacion sean correctos
            analisisFactores(null, null, factores);
        }
    }

    @Override 
    public void exitDeclaracionFuncion(DeclaracionFuncionContext ctx) { 
        //me fijo que estemos en el context global y sino muestro error ya que el 
        //prototipo debe estar en ctx global
        if(simbolos.getTablaSimbolos().size() == 1){
            Id id = getID(ctx.ID().getText()); 
        //Si el id no existe en la tabla de simbolos lo agrego  sino muestro error de que ya fue declarado
            if(id == null){
                List<Variable> parametros = getParametros(ctx.parametros());
                if(parametros != null){
                    Id prototype = new Funcion(false, false, ctx.tipoDato().getText(), ctx.ID().getText(), parametros);
                    simbolos.agregarEnContextoActual(ctx.ID().getText(), prototype);
                }
            }else{
                setError(true);
                errors.idDeclarado(posicionDelToken(ctx.getStart()), ctx.ID().getText());
            }
        }else{
            setError(true);
            errors.prototipoContexto(posicionDelToken(ctx.getStart()));
        }
    }

    @Override 
    public void exitDefinicionFuncion(DefinicionFuncionContext ctx) { 
        //Id id = getID(ctx.ID().getText()); 
        //Si el id no existe en la tabla de simbolos lo agrego  sino muestro error de que ya fue declarado
        List<Variable> parametros = getParametros(ctx.parametros());
        if(parametros != null){
        Id prototype = new Funcion(true, false, ctx.tipoDato().getText(), ctx.ID().getText(), parametros);
        simbolos.agregarEnContextoActual(ctx.ID().getText(), prototype);
        System.out.println("Parametros de la funcion" + parametros.toString());   
        }
        }
    

    @Override 
    public void enterBloque(BloqueContext ctx) { 
        //Chequeamos que el padre sea un ctx de def de funcion sino agregamos un contexto
        //procesamos el bloque de la funcion
        if(ctx.getParent() instanceof DefinicionFuncionContext){
            DefinicionFuncionContext funcionContext = (DefinicionFuncionContext)ctx.getParent();
            
            Id id = procesarFuncion(funcionContext);
            simbolos.agregarContexto();
            //Dependiendo si existe o no en el ctx agrego los parametros a la tabla de simbolos del ctx actual
            if(id != null)
            {
                ((Funcion)id).getParametros().stream().forEach(param -> simbolos.agregarEnContextoActual(param.getTokenNombre(), param));
            }
            else{
                getParametros(funcionContext.parametros()).stream().forEach(param -> simbolos.agregarEnContextoActual(param.getTokenNombre(), param));
            }
            
        }else
            simbolos.agregarContexto();
    }

    //atributos de parametro q es una variable
    //private boolean inicializado;
    //private boolean usado;
    //private String tipoDato;
    //private String tokenNombre;
     private List<Variable> getParametros(ParametrosContext ctx){
            List<Variable> params = new ArrayList<Variable>();
            Variable var = new Variable(true, false, ctx.tipoDato().getText(), ctx.ID().getText());
            params.add(var);
            //busco todos los hijos de las reglas param
            Collection<ParseTree> parametros = Trees.findAllRuleNodes(ctx, tp2Parser.RULE_param);
            if(parametros != null)
            {
           // Collection<ParseTree> reglasParametros;
            for (ParseTree parseTree : parametros) {
                  if(parseTree.getChild(1) instanceof ParametrosContext){
                    Variable var1 = new Variable(true, false, ((ParametrosContext) parseTree.getChild(1)).tipoDato().getText(), ((ParametrosContext) parseTree.getChild(1)).ID().getText());
                    params.add(var1);
                  } 
                }
            } 
            System.out.println("Parametros encontrados> " + params.toString());
            return params;
         }
    @Override 
    public void exitBloque(BloqueContext ctx) {
    //Al salir del bloque chequeamos su return, mostramos los id sin usar y borramos el contexto    
        if(ctx.getParent() instanceof DefinicionFuncionContext)
            procesarRetorno((DefinicionFuncionContext)ctx.getParent());
        errors.idNoUsado(simbolos.idSinUsar());
        simbolos.quitarContexto();
    }

    @Override 
    public void exitLlamadaFuncion(LlamadaFuncionContext ctx) {
        if(ctx.getParent() instanceof InstruccionContext){
            Id id = getID(ctx.ID().getText());
            if(id != null){
                getID(ctx.ID().getText()).setUsado(true);
                procesarLlamadaFuncion(null, ctx);
            }
        }
    }

    @Override 
    public void exitInstruccion(InstruccionContext ctx) { 
        if(ctx.declaracion() != null)
            procesarDeclaracion(ctx.declaracion());
        System.out.println("Salgo de la instruccion");
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }


}