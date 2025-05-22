// Generated from d:/UBP/TC/Final F- Tc/TPFinal-4/primerproyecto/src/main/java/com/primerproyecto/tp2.g4 by ANTLR 4.13.1

package com.primerproyecto;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link tp2Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface tp2Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link tp2Parser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(tp2Parser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#instrucciones}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrucciones(tp2Parser.InstruccionesContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloque(tp2Parser.BloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(tp2Parser.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#retorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetorno(tp2Parser.RetornoContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#inicializacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializacion(tp2Parser.InicializacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#declaracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion(tp2Parser.DeclaracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#asign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsign(tp2Parser.AsignContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#tipoDato}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipoDato(tp2Parser.TipoDatoContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(tp2Parser.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#bucleFor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBucleFor(tp2Parser.BucleForContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#bucleWhile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBucleWhile(tp2Parser.BucleWhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#condif}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondif(tp2Parser.CondifContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#declaracionFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionFuncion(tp2Parser.DeclaracionFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#definicionFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicionFuncion(tp2Parser.DefinicionFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(tp2Parser.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(tp2Parser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#llamadaFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamadaFuncion(tp2Parser.LlamadaFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#argumentos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentos(tp2Parser.ArgumentosContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(tp2Parser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#operacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacion(tp2Parser.OperacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#opal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpal(tp2Parser.OpalContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#disy}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisy(tp2Parser.DisyContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#conjuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjuncion(tp2Parser.ConjuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#conj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConj(tp2Parser.ConjContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#comparaciones}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparaciones(tp2Parser.ComparacionesContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp(tp2Parser.CompContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#opcomp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpcomp(tp2Parser.OpcompContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(tp2Parser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(tp2Parser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(tp2Parser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#t}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitT(tp2Parser.TContext ctx);
	/**
	 * Visit a parse tree produced by {@link tp2Parser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(tp2Parser.FactorContext ctx);
}