// Generated from d:/UBP/TC/Final F- Tc/TPFinal-4/primerproyecto/src/main/java/com/primerproyecto/tp2.g4 by ANTLR 4.13.1

package com.primerproyecto;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link tp2Parser}.
 */
public interface tp2Listener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link tp2Parser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(tp2Parser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(tp2Parser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#instrucciones}.
	 * @param ctx the parse tree
	 */
	void enterInstrucciones(tp2Parser.InstruccionesContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#instrucciones}.
	 * @param ctx the parse tree
	 */
	void exitInstrucciones(tp2Parser.InstruccionesContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(tp2Parser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(tp2Parser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(tp2Parser.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(tp2Parser.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#retorno}.
	 * @param ctx the parse tree
	 */
	void enterRetorno(tp2Parser.RetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#retorno}.
	 * @param ctx the parse tree
	 */
	void exitRetorno(tp2Parser.RetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#inicializacion}.
	 * @param ctx the parse tree
	 */
	void enterInicializacion(tp2Parser.InicializacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#inicializacion}.
	 * @param ctx the parse tree
	 */
	void exitInicializacion(tp2Parser.InicializacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#declaracion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion(tp2Parser.DeclaracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#declaracion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion(tp2Parser.DeclaracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#asign}.
	 * @param ctx the parse tree
	 */
	void enterAsign(tp2Parser.AsignContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#asign}.
	 * @param ctx the parse tree
	 */
	void exitAsign(tp2Parser.AsignContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#tipoDato}.
	 * @param ctx the parse tree
	 */
	void enterTipoDato(tp2Parser.TipoDatoContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#tipoDato}.
	 * @param ctx the parse tree
	 */
	void exitTipoDato(tp2Parser.TipoDatoContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(tp2Parser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(tp2Parser.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#bucleFor}.
	 * @param ctx the parse tree
	 */
	void enterBucleFor(tp2Parser.BucleForContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#bucleFor}.
	 * @param ctx the parse tree
	 */
	void exitBucleFor(tp2Parser.BucleForContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#bucleWhile}.
	 * @param ctx the parse tree
	 */
	void enterBucleWhile(tp2Parser.BucleWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#bucleWhile}.
	 * @param ctx the parse tree
	 */
	void exitBucleWhile(tp2Parser.BucleWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#condif}.
	 * @param ctx the parse tree
	 */
	void enterCondif(tp2Parser.CondifContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#condif}.
	 * @param ctx the parse tree
	 */
	void exitCondif(tp2Parser.CondifContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionFuncion(tp2Parser.DeclaracionFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionFuncion(tp2Parser.DeclaracionFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#definicionFuncion}.
	 * @param ctx the parse tree
	 */
	void enterDefinicionFuncion(tp2Parser.DefinicionFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#definicionFuncion}.
	 * @param ctx the parse tree
	 */
	void exitDefinicionFuncion(tp2Parser.DefinicionFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(tp2Parser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(tp2Parser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(tp2Parser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(tp2Parser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#llamadaFuncion}.
	 * @param ctx the parse tree
	 */
	void enterLlamadaFuncion(tp2Parser.LlamadaFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#llamadaFuncion}.
	 * @param ctx the parse tree
	 */
	void exitLlamadaFuncion(tp2Parser.LlamadaFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#argumentos}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos(tp2Parser.ArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#argumentos}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos(tp2Parser.ArgumentosContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(tp2Parser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(tp2Parser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#operacion}.
	 * @param ctx the parse tree
	 */
	void enterOperacion(tp2Parser.OperacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#operacion}.
	 * @param ctx the parse tree
	 */
	void exitOperacion(tp2Parser.OperacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#opal}.
	 * @param ctx the parse tree
	 */
	void enterOpal(tp2Parser.OpalContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#opal}.
	 * @param ctx the parse tree
	 */
	void exitOpal(tp2Parser.OpalContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#disy}.
	 * @param ctx the parse tree
	 */
	void enterDisy(tp2Parser.DisyContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#disy}.
	 * @param ctx the parse tree
	 */
	void exitDisy(tp2Parser.DisyContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#conjuncion}.
	 * @param ctx the parse tree
	 */
	void enterConjuncion(tp2Parser.ConjuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#conjuncion}.
	 * @param ctx the parse tree
	 */
	void exitConjuncion(tp2Parser.ConjuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#conj}.
	 * @param ctx the parse tree
	 */
	void enterConj(tp2Parser.ConjContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#conj}.
	 * @param ctx the parse tree
	 */
	void exitConj(tp2Parser.ConjContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#comparaciones}.
	 * @param ctx the parse tree
	 */
	void enterComparaciones(tp2Parser.ComparacionesContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#comparaciones}.
	 * @param ctx the parse tree
	 */
	void exitComparaciones(tp2Parser.ComparacionesContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#comp}.
	 * @param ctx the parse tree
	 */
	void enterComp(tp2Parser.CompContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#comp}.
	 * @param ctx the parse tree
	 */
	void exitComp(tp2Parser.CompContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#opcomp}.
	 * @param ctx the parse tree
	 */
	void enterOpcomp(tp2Parser.OpcompContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#opcomp}.
	 * @param ctx the parse tree
	 */
	void exitOpcomp(tp2Parser.OpcompContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(tp2Parser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(tp2Parser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#exp}.
	 * @param ctx the parse tree
	 */
	void enterExp(tp2Parser.ExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#exp}.
	 * @param ctx the parse tree
	 */
	void exitExp(tp2Parser.ExpContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(tp2Parser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(tp2Parser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#t}.
	 * @param ctx the parse tree
	 */
	void enterT(tp2Parser.TContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#t}.
	 * @param ctx the parse tree
	 */
	void exitT(tp2Parser.TContext ctx);
	/**
	 * Enter a parse tree produced by {@link tp2Parser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(tp2Parser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link tp2Parser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(tp2Parser.FactorContext ctx);
}