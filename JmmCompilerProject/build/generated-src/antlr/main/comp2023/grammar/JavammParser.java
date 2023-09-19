// Generated from comp2023/grammar/Javamm.g4 by ANTLR 4.12.0

    package pt.up.fe.comp2023;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class JavammParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		INT=46, ID=47, WS=48, COMMENT=49, LINECOMMENT=50;
	public static final int
		RULE_program = 0, RULE_importDeclaration = 1, RULE_importAppend = 2, RULE_classDeclaration = 3, 
		RULE_extendClass = 4, RULE_varDeclaration = 5, RULE_methodDeclaration = 6, 
		RULE_parameter = 7, RULE_parameterAppend = 8, RULE_type = 9, RULE_statement = 10, 
		RULE_expression = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importDeclaration", "importAppend", "classDeclaration", "extendClass", 
			"varDeclaration", "methodDeclaration", "parameter", "parameterAppend", 
			"type", "statement", "expression"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'import'", "';'", "'.'", "'class'", "'{'", "'}'", "'extends'", 
			"'public'", "'static'", "'void'", "'main'", "'('", "'String'", "'['", 
			"']'", "')'", "'return'", "','", "'int'", "'boolean'", "'char'", "'if'", 
			"'else'", "'while'", "'for'", "'='", "'+='", "'length'", "'new'", "'*'", 
			"'/'", "'+'", "'-'", "'<'", "'>'", "'<='", "'>='", "'&&'", "'||'", "'++'", 
			"'--'", "'!'", "'true'", "'false'", "'this'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "INT", "ID", 
			"WS", "COMMENT", "LINECOMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Javamm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavammParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(JavammParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(24);
				importDeclaration();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
			classDeclaration();
			setState(31);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportDeclarationContext extends ParserRuleContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public List<ImportAppendContext> importAppend() {
			return getRuleContexts(ImportAppendContext.class);
		}
		public ImportAppendContext importAppend(int i) {
			return getRuleContext(ImportAppendContext.class,i);
		}
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterImportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitImportDeclaration(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(T__0);
			setState(34);
			((ImportDeclarationContext)_localctx).value = match(ID);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(35);
				importAppend();
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(41);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportAppendContext extends ParserRuleContext {
		public Token value1;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ImportAppendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importAppend; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterImportAppend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitImportAppend(this);
		}
	}

	public final ImportAppendContext importAppend() throws RecognitionException {
		ImportAppendContext _localctx = new ImportAppendContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importAppend);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(T__2);
			setState(44);
			((ImportAppendContext)_localctx).value1 = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDeclarationContext extends ParserRuleContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ExtendClassContext extendClass() {
			return getRuleContext(ExtendClassContext.class,0);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<MethodDeclarationContext> methodDeclaration() {
			return getRuleContexts(MethodDeclarationContext.class);
		}
		public MethodDeclarationContext methodDeclaration(int i) {
			return getRuleContext(MethodDeclarationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitClassDeclaration(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(T__3);
			setState(47);
			((ClassDeclarationContext)_localctx).value = match(ID);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(48);
				extendClass();
				}
			}

			setState(51);
			match(T__4);
			setState(55);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(52);
					varDeclaration();
					}
					} 
				}
				setState(57);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140737492035328L) != 0)) {
				{
				{
				setState(58);
				methodDeclaration();
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExtendClassContext extends ParserRuleContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ExtendClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extendClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterExtendClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitExtendClass(this);
		}
	}

	public final ExtendClassContext extendClass() throws RecognitionException {
		ExtendClassContext _localctx = new ExtendClassContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_extendClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__6);
			setState(67);
			((ExtendClassContext)_localctx).value = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDeclarationContext extends ParserRuleContext {
		public Token value;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterVarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitVarDeclaration(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			type();
			setState(70);
			((VarDeclarationContext)_localctx).value = match(ID);
			setState(71);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MethodDeclarationContext extends ParserRuleContext {
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
	 
		public MethodDeclarationContext() { }
		public void copyFrom(MethodDeclarationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OtherMethodDeclarationContext extends MethodDeclarationContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public OtherMethodDeclarationContext(MethodDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterOtherMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitOtherMethodDeclaration(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MainMethodDeclarationContext extends MethodDeclarationContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MainMethodDeclarationContext(MethodDeclarationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterMainMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitMainMethodDeclaration(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_methodDeclaration);
		int _la;
		try {
			int _alt;
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new MainMethodDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(73);
					match(T__7);
					}
				}

				setState(76);
				match(T__8);
				setState(77);
				match(T__9);
				setState(78);
				match(T__10);
				setState(79);
				match(T__11);
				setState(80);
				match(T__12);
				setState(81);
				match(T__13);
				setState(82);
				match(T__14);
				setState(83);
				((MainMethodDeclarationContext)_localctx).value = match(ID);
				setState(84);
				match(T__15);
				setState(85);
				match(T__4);
				setState(89);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(86);
						varDeclaration();
						}
						} 
					}
					setState(91);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 277077521600544L) != 0)) {
					{
					{
					setState(92);
					statement();
					}
					}
					setState(97);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(98);
				match(T__5);
				}
				break;
			case 2:
				_localctx = new OtherMethodDeclarationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(99);
					match(T__7);
					}
				}

				setState(102);
				type();
				setState(103);
				((OtherMethodDeclarationContext)_localctx).name = match(ID);
				setState(104);
				match(T__11);
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140737492034560L) != 0)) {
					{
					setState(105);
					parameter();
					}
				}

				setState(108);
				match(T__15);
				setState(109);
				match(T__4);
				setState(113);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(110);
						varDeclaration();
						}
						} 
					}
					setState(115);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 277077521600544L) != 0)) {
					{
					{
					setState(116);
					statement();
					}
					}
					setState(121);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(122);
				match(T__16);
				setState(123);
				expression(0);
				setState(124);
				match(T__1);
				setState(125);
				match(T__5);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContext extends ParserRuleContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public List<ParameterAppendContext> parameterAppend() {
			return getRuleContexts(ParameterAppendContext.class);
		}
		public ParameterAppendContext parameterAppend(int i) {
			return getRuleContext(ParameterAppendContext.class,i);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParameter(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			type();
			setState(130);
			((ParameterContext)_localctx).name = match(ID);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(131);
				parameterAppend();
				}
				}
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterAppendContext extends ParserRuleContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ParameterAppendContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterAppend; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParameterAppend(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParameterAppend(this);
		}
	}

	public final ParameterAppendContext parameterAppend() throws RecognitionException {
		ParameterAppendContext _localctx = new ParameterAppendContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_parameterAppend);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(T__17);
			setState(138);
			type();
			setState(139);
			((ParameterAppendContext)_localctx).name = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeArrayContext extends TypeContext {
		public TypeArrayContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeArray(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeIntContext extends TypeContext {
		public TypeIntContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeInt(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeObjectContext extends TypeContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TypeObjectContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeObject(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeCharContext extends TypeContext {
		public TypeCharContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeChar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeChar(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeStringContext extends TypeContext {
		public TypeStringContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeString(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeBooleanContext extends TypeContext {
		public TypeBooleanContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeBoolean(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TypeVoidContext extends TypeContext {
		public TypeVoidContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTypeVoid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTypeVoid(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type);
		try {
			setState(150);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new TypeArrayContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(141);
				match(T__18);
				setState(142);
				match(T__13);
				setState(143);
				match(T__14);
				}
				break;
			case 2:
				_localctx = new TypeBooleanContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				match(T__19);
				}
				break;
			case 3:
				_localctx = new TypeIntContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				match(T__18);
				}
				break;
			case 4:
				_localctx = new TypeCharContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(146);
				match(T__20);
				}
				break;
			case 5:
				_localctx = new TypeStringContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(147);
				match(T__12);
				}
				break;
			case 6:
				_localctx = new TypeVoidContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(148);
				match(T__9);
				}
				break;
			case 7:
				_localctx = new TypeObjectContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(149);
				((TypeObjectContext)_localctx).value = match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitWhileStatement(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayAssignmentContext extends StatementContext {
		public Token value;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ArrayAssignmentContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArrayAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArrayAssignment(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends StatementContext {
		public Token value;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public AssignmentContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitAssignment(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public VarDeclarationContext varDeclaration() {
			return getRuleContext(VarDeclarationContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitForStatement(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionStatementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitExpressionStatement(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IncAssignmentContext extends StatementContext {
		public Token value;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public IncAssignmentContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIncAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIncAssignment(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BracketStatementContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BracketStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBracketStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBracketStatement(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIfStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		int _la;
		try {
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new BracketStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				match(T__4);
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 277077521600544L) != 0)) {
					{
					{
					setState(153);
					statement();
					}
					}
					setState(158);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(159);
				match(T__5);
				}
				break;
			case 2:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				match(T__21);
				setState(161);
				match(T__11);
				setState(162);
				expression(0);
				setState(163);
				match(T__15);
				setState(164);
				statement();
				setState(167);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(165);
					match(T__22);
					setState(166);
					statement();
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new WhileStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(169);
				match(T__23);
				setState(170);
				match(T__11);
				setState(171);
				expression(0);
				setState(172);
				match(T__15);
				setState(173);
				statement();
				}
				break;
			case 4:
				_localctx = new ForStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(175);
				match(T__24);
				setState(176);
				match(T__11);
				setState(180);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(177);
					varDeclaration();
					}
					break;
				case 2:
					{
					setState(178);
					statement();
					}
					break;
				case 3:
					{
					setState(179);
					expression(0);
					}
					break;
				}
				setState(182);
				match(T__1);
				setState(184);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 277077467074560L) != 0)) {
					{
					setState(183);
					expression(0);
					}
				}

				setState(186);
				match(T__1);
				setState(189);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(187);
					statement();
					}
					break;
				case 2:
					{
					setState(188);
					expression(0);
					}
					break;
				}
				setState(191);
				match(T__15);
				setState(192);
				statement();
				}
				break;
			case 5:
				_localctx = new ExpressionStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(193);
				expression(0);
				setState(194);
				match(T__1);
				}
				break;
			case 6:
				_localctx = new AssignmentContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(196);
				((AssignmentContext)_localctx).value = match(ID);
				setState(197);
				match(T__25);
				setState(198);
				expression(0);
				setState(199);
				match(T__1);
				}
				break;
			case 7:
				_localctx = new IncAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(201);
				((IncAssignmentContext)_localctx).value = match(ID);
				setState(202);
				match(T__26);
				setState(203);
				expression(0);
				setState(204);
				match(T__1);
				}
				break;
			case 8:
				_localctx = new ArrayAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(206);
				((ArrayAssignmentContext)_localctx).value = match(ID);
				setState(207);
				match(T__13);
				setState(208);
				expression(0);
				setState(209);
				match(T__14);
				setState(210);
				match(T__25);
				setState(211);
				expression(0);
				setState(212);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesesContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenthesesContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParentheses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParentheses(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdentifierContext extends ExpressionContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public IdentifierContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIdentifier(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryOpContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BinaryOpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBinaryOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBinaryOp(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseContext extends ExpressionContext {
		public FalseContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterFalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitFalse(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ThisContext extends ExpressionContext {
		public ThisContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterThis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitThis(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LengthContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LengthContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterLength(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitLength(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LogicalAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterLogicalAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitLogicalAnd(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntegerContext extends ExpressionContext {
		public Token value;
		public TerminalNode INT() { return getToken(JavammParser.INT, 0); }
		public IntegerContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitInteger(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelationalOpContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public RelationalOpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterRelationalOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitRelationalOp(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayCreationContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayCreationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArrayCreation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArrayCreation(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueContext extends ExpressionContext {
		public TrueContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterTrue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitTrue(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayAccessContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayAccessContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArrayAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArrayAccess(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryIncDecContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryIncDecContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterUnaryIncDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitUnaryIncDec(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalOrContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public LogicalOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterLogicalOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitLogicalOr(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ObjectCreationContext extends ExpressionContext {
		public Token value;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public ObjectCreationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterObjectCreation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitObjectCreation(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryNotContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryNotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterUnaryNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitUnaryNot(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MethodCallContext extends ExpressionContext {
		public Token value;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public MethodCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitMethodCall(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				_localctx = new ParenthesesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(217);
				match(T__11);
				setState(218);
				expression(0);
				setState(219);
				match(T__15);
				}
				break;
			case 2:
				{
				_localctx = new ArrayCreationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(221);
				match(T__28);
				setState(222);
				match(T__18);
				setState(223);
				match(T__13);
				setState(224);
				expression(0);
				setState(225);
				match(T__14);
				}
				break;
			case 3:
				{
				_localctx = new ObjectCreationContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(227);
				match(T__28);
				setState(228);
				((ObjectCreationContext)_localctx).value = match(ID);
				setState(229);
				match(T__11);
				setState(230);
				match(T__15);
				}
				break;
			case 4:
				{
				_localctx = new UnaryNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(231);
				match(T__41);
				setState(232);
				expression(6);
				}
				break;
			case 5:
				{
				_localctx = new IntegerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(233);
				((IntegerContext)_localctx).value = match(INT);
				}
				break;
			case 6:
				{
				_localctx = new TrueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(234);
				match(T__42);
				}
				break;
			case 7:
				{
				_localctx = new FalseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(235);
				match(T__43);
				}
				break;
			case 8:
				{
				_localctx = new IdentifierContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				((IdentifierContext)_localctx).value = match(ID);
				}
				break;
			case 9:
				{
				_localctx = new ThisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(237);
				match(T__44);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(282);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(280);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryOpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(240);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(241);
						((BinaryOpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__29 || _la==T__30) ) {
							((BinaryOpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(242);
						expression(13);
						}
						break;
					case 2:
						{
						_localctx = new BinaryOpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(243);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(244);
						((BinaryOpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__31 || _la==T__32) ) {
							((BinaryOpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(245);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new RelationalOpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(246);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(247);
						((RelationalOpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 257698037760L) != 0)) ) {
							((RelationalOpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(248);
						expression(11);
						}
						break;
					case 4:
						{
						_localctx = new LogicalAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(249);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(250);
						match(T__37);
						setState(251);
						expression(10);
						}
						break;
					case 5:
						{
						_localctx = new LogicalOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(252);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(253);
						match(T__38);
						setState(254);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new ArrayAccessContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(255);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(256);
						match(T__13);
						setState(257);
						expression(0);
						setState(258);
						match(T__14);
						}
						break;
					case 7:
						{
						_localctx = new MethodCallContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(260);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(261);
						match(T__2);
						setState(262);
						((MethodCallContext)_localctx).value = match(ID);
						setState(263);
						match(T__11);
						setState(272);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 277077467074560L) != 0)) {
							{
							setState(264);
							expression(0);
							setState(269);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==T__17) {
								{
								{
								setState(265);
								match(T__17);
								setState(266);
								expression(0);
								}
								}
								setState(271);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(274);
						match(T__15);
						}
						break;
					case 8:
						{
						_localctx = new LengthContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(275);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(276);
						match(T__2);
						setState(277);
						match(T__27);
						}
						break;
					case 9:
						{
						_localctx = new UnaryIncDecContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(278);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(279);
						((UnaryIncDecContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__39 || _la==T__40) ) {
							((UnaryIncDecContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(284);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 12);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 17);
		case 6:
			return precpred(_ctx, 16);
		case 7:
			return precpred(_ctx, 15);
		case 8:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00012\u011e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"%\b\u0001\n\u0001\f\u0001(\t\u0001\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"2\b\u0003\u0001\u0003\u0001\u0003\u0005\u00036\b\u0003\n\u0003\f\u0003"+
		"9\t\u0003\u0001\u0003\u0005\u0003<\b\u0003\n\u0003\f\u0003?\t\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0003\u0006K\b\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006X\b"+
		"\u0006\n\u0006\f\u0006[\t\u0006\u0001\u0006\u0005\u0006^\b\u0006\n\u0006"+
		"\f\u0006a\t\u0006\u0001\u0006\u0001\u0006\u0003\u0006e\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006k\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006p\b\u0006\n\u0006\f\u0006s\t\u0006"+
		"\u0001\u0006\u0005\u0006v\b\u0006\n\u0006\f\u0006y\t\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u0080\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u0085\b\u0007\n\u0007"+
		"\f\u0007\u0088\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u0097"+
		"\b\t\u0001\n\u0001\n\u0005\n\u009b\b\n\n\n\f\n\u009e\t\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00a8\b\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0003\n\u00b5\b\n\u0001\n\u0001\n\u0003\n\u00b9\b\n\u0001\n"+
		"\u0001\n\u0001\n\u0003\n\u00be\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0003\n\u00d7\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003"+
		"\u000b\u00ef\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u010c"+
		"\b\u000b\n\u000b\f\u000b\u010f\t\u000b\u0003\u000b\u0111\b\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005"+
		"\u000b\u0119\b\u000b\n\u000b\f\u000b\u011c\t\u000b\u0001\u000b\u0000\u0001"+
		"\u0016\f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000"+
		"\u0004\u0001\u0000\u001e\u001f\u0001\u0000 !\u0001\u0000\"%\u0001\u0000"+
		"()\u0147\u0000\u001b\u0001\u0000\u0000\u0000\u0002!\u0001\u0000\u0000"+
		"\u0000\u0004+\u0001\u0000\u0000\u0000\u0006.\u0001\u0000\u0000\u0000\b"+
		"B\u0001\u0000\u0000\u0000\nE\u0001\u0000\u0000\u0000\f\u007f\u0001\u0000"+
		"\u0000\u0000\u000e\u0081\u0001\u0000\u0000\u0000\u0010\u0089\u0001\u0000"+
		"\u0000\u0000\u0012\u0096\u0001\u0000\u0000\u0000\u0014\u00d6\u0001\u0000"+
		"\u0000\u0000\u0016\u00ee\u0001\u0000\u0000\u0000\u0018\u001a\u0003\u0002"+
		"\u0001\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000"+
		"\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000"+
		"\u0000\u0000\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000"+
		"\u0000\u0000\u001e\u001f\u0003\u0006\u0003\u0000\u001f \u0005\u0000\u0000"+
		"\u0001 \u0001\u0001\u0000\u0000\u0000!\"\u0005\u0001\u0000\u0000\"&\u0005"+
		"/\u0000\u0000#%\u0003\u0004\u0002\u0000$#\u0001\u0000\u0000\u0000%(\u0001"+
		"\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000\u0000"+
		"\')\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000\u0000)*\u0005\u0002\u0000"+
		"\u0000*\u0003\u0001\u0000\u0000\u0000+,\u0005\u0003\u0000\u0000,-\u0005"+
		"/\u0000\u0000-\u0005\u0001\u0000\u0000\u0000./\u0005\u0004\u0000\u0000"+
		"/1\u0005/\u0000\u000002\u0003\b\u0004\u000010\u0001\u0000\u0000\u0000"+
		"12\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u000037\u0005\u0005\u0000"+
		"\u000046\u0003\n\u0005\u000054\u0001\u0000\u0000\u000069\u0001\u0000\u0000"+
		"\u000075\u0001\u0000\u0000\u000078\u0001\u0000\u0000\u00008=\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u0000:<\u0003\f\u0006\u0000;:\u0001\u0000"+
		"\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001"+
		"\u0000\u0000\u0000>@\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000"+
		"@A\u0005\u0006\u0000\u0000A\u0007\u0001\u0000\u0000\u0000BC\u0005\u0007"+
		"\u0000\u0000CD\u0005/\u0000\u0000D\t\u0001\u0000\u0000\u0000EF\u0003\u0012"+
		"\t\u0000FG\u0005/\u0000\u0000GH\u0005\u0002\u0000\u0000H\u000b\u0001\u0000"+
		"\u0000\u0000IK\u0005\b\u0000\u0000JI\u0001\u0000\u0000\u0000JK\u0001\u0000"+
		"\u0000\u0000KL\u0001\u0000\u0000\u0000LM\u0005\t\u0000\u0000MN\u0005\n"+
		"\u0000\u0000NO\u0005\u000b\u0000\u0000OP\u0005\f\u0000\u0000PQ\u0005\r"+
		"\u0000\u0000QR\u0005\u000e\u0000\u0000RS\u0005\u000f\u0000\u0000ST\u0005"+
		"/\u0000\u0000TU\u0005\u0010\u0000\u0000UY\u0005\u0005\u0000\u0000VX\u0003"+
		"\n\u0005\u0000WV\u0001\u0000\u0000\u0000X[\u0001\u0000\u0000\u0000YW\u0001"+
		"\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000Z_\u0001\u0000\u0000\u0000"+
		"[Y\u0001\u0000\u0000\u0000\\^\u0003\u0014\n\u0000]\\\u0001\u0000\u0000"+
		"\u0000^a\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001\u0000"+
		"\u0000\u0000`b\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000b\u0080"+
		"\u0005\u0006\u0000\u0000ce\u0005\b\u0000\u0000dc\u0001\u0000\u0000\u0000"+
		"de\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0003\u0012\t\u0000"+
		"gh\u0005/\u0000\u0000hj\u0005\f\u0000\u0000ik\u0003\u000e\u0007\u0000"+
		"ji\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000kl\u0001\u0000\u0000"+
		"\u0000lm\u0005\u0010\u0000\u0000mq\u0005\u0005\u0000\u0000np\u0003\n\u0005"+
		"\u0000on\u0001\u0000\u0000\u0000ps\u0001\u0000\u0000\u0000qo\u0001\u0000"+
		"\u0000\u0000qr\u0001\u0000\u0000\u0000rw\u0001\u0000\u0000\u0000sq\u0001"+
		"\u0000\u0000\u0000tv\u0003\u0014\n\u0000ut\u0001\u0000\u0000\u0000vy\u0001"+
		"\u0000\u0000\u0000wu\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000"+
		"xz\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000z{\u0005\u0011\u0000"+
		"\u0000{|\u0003\u0016\u000b\u0000|}\u0005\u0002\u0000\u0000}~\u0005\u0006"+
		"\u0000\u0000~\u0080\u0001\u0000\u0000\u0000\u007fJ\u0001\u0000\u0000\u0000"+
		"\u007fd\u0001\u0000\u0000\u0000\u0080\r\u0001\u0000\u0000\u0000\u0081"+
		"\u0082\u0003\u0012\t\u0000\u0082\u0086\u0005/\u0000\u0000\u0083\u0085"+
		"\u0003\u0010\b\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0085\u0088\u0001"+
		"\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0086\u0087\u0001"+
		"\u0000\u0000\u0000\u0087\u000f\u0001\u0000\u0000\u0000\u0088\u0086\u0001"+
		"\u0000\u0000\u0000\u0089\u008a\u0005\u0012\u0000\u0000\u008a\u008b\u0003"+
		"\u0012\t\u0000\u008b\u008c\u0005/\u0000\u0000\u008c\u0011\u0001\u0000"+
		"\u0000\u0000\u008d\u008e\u0005\u0013\u0000\u0000\u008e\u008f\u0005\u000e"+
		"\u0000\u0000\u008f\u0097\u0005\u000f\u0000\u0000\u0090\u0097\u0005\u0014"+
		"\u0000\u0000\u0091\u0097\u0005\u0013\u0000\u0000\u0092\u0097\u0005\u0015"+
		"\u0000\u0000\u0093\u0097\u0005\r\u0000\u0000\u0094\u0097\u0005\n\u0000"+
		"\u0000\u0095\u0097\u0005/\u0000\u0000\u0096\u008d\u0001\u0000\u0000\u0000"+
		"\u0096\u0090\u0001\u0000\u0000\u0000\u0096\u0091\u0001\u0000\u0000\u0000"+
		"\u0096\u0092\u0001\u0000\u0000\u0000\u0096\u0093\u0001\u0000\u0000\u0000"+
		"\u0096\u0094\u0001\u0000\u0000\u0000\u0096\u0095\u0001\u0000\u0000\u0000"+
		"\u0097\u0013\u0001\u0000\u0000\u0000\u0098\u009c\u0005\u0005\u0000\u0000"+
		"\u0099\u009b\u0003\u0014\n\u0000\u009a\u0099\u0001\u0000\u0000\u0000\u009b"+
		"\u009e\u0001\u0000\u0000\u0000\u009c\u009a\u0001\u0000\u0000\u0000\u009c"+
		"\u009d\u0001\u0000\u0000\u0000\u009d\u009f\u0001\u0000\u0000\u0000\u009e"+
		"\u009c\u0001\u0000\u0000\u0000\u009f\u00d7\u0005\u0006\u0000\u0000\u00a0"+
		"\u00a1\u0005\u0016\u0000\u0000\u00a1\u00a2\u0005\f\u0000\u0000\u00a2\u00a3"+
		"\u0003\u0016\u000b\u0000\u00a3\u00a4\u0005\u0010\u0000\u0000\u00a4\u00a7"+
		"\u0003\u0014\n\u0000\u00a5\u00a6\u0005\u0017\u0000\u0000\u00a6\u00a8\u0003"+
		"\u0014\n\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000"+
		"\u0000\u0000\u00a8\u00d7\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005\u0018"+
		"\u0000\u0000\u00aa\u00ab\u0005\f\u0000\u0000\u00ab\u00ac\u0003\u0016\u000b"+
		"\u0000\u00ac\u00ad\u0005\u0010\u0000\u0000\u00ad\u00ae\u0003\u0014\n\u0000"+
		"\u00ae\u00d7\u0001\u0000\u0000\u0000\u00af\u00b0\u0005\u0019\u0000\u0000"+
		"\u00b0\u00b4\u0005\f\u0000\u0000\u00b1\u00b5\u0003\n\u0005\u0000\u00b2"+
		"\u00b5\u0003\u0014\n\u0000\u00b3\u00b5\u0003\u0016\u000b\u0000\u00b4\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b4\u00b3"+
		"\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000\u00b5\u00b6"+
		"\u0001\u0000\u0000\u0000\u00b6\u00b8\u0005\u0002\u0000\u0000\u00b7\u00b9"+
		"\u0003\u0016\u000b\u0000\u00b8\u00b7\u0001\u0000\u0000\u0000\u00b8\u00b9"+
		"\u0001\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00bd"+
		"\u0005\u0002\u0000\u0000\u00bb\u00be\u0003\u0014\n\u0000\u00bc\u00be\u0003"+
		"\u0016\u000b\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00bd\u00bc\u0001"+
		"\u0000\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u00bf\u0001"+
		"\u0000\u0000\u0000\u00bf\u00c0\u0005\u0010\u0000\u0000\u00c0\u00d7\u0003"+
		"\u0014\n\u0000\u00c1\u00c2\u0003\u0016\u000b\u0000\u00c2\u00c3\u0005\u0002"+
		"\u0000\u0000\u00c3\u00d7\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005/\u0000"+
		"\u0000\u00c5\u00c6\u0005\u001a\u0000\u0000\u00c6\u00c7\u0003\u0016\u000b"+
		"\u0000\u00c7\u00c8\u0005\u0002\u0000\u0000\u00c8\u00d7\u0001\u0000\u0000"+
		"\u0000\u00c9\u00ca\u0005/\u0000\u0000\u00ca\u00cb\u0005\u001b\u0000\u0000"+
		"\u00cb\u00cc\u0003\u0016\u000b\u0000\u00cc\u00cd\u0005\u0002\u0000\u0000"+
		"\u00cd\u00d7\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005/\u0000\u0000\u00cf"+
		"\u00d0\u0005\u000e\u0000\u0000\u00d0\u00d1\u0003\u0016\u000b\u0000\u00d1"+
		"\u00d2\u0005\u000f\u0000\u0000\u00d2\u00d3\u0005\u001a\u0000\u0000\u00d3"+
		"\u00d4\u0003\u0016\u000b\u0000\u00d4\u00d5\u0005\u0002\u0000\u0000\u00d5"+
		"\u00d7\u0001\u0000\u0000\u0000\u00d6\u0098\u0001\u0000\u0000\u0000\u00d6"+
		"\u00a0\u0001\u0000\u0000\u0000\u00d6\u00a9\u0001\u0000\u0000\u0000\u00d6"+
		"\u00af\u0001\u0000\u0000\u0000\u00d6\u00c1\u0001\u0000\u0000\u0000\u00d6"+
		"\u00c4\u0001\u0000\u0000\u0000\u00d6\u00c9\u0001\u0000\u0000\u0000\u00d6"+
		"\u00ce\u0001\u0000\u0000\u0000\u00d7\u0015\u0001\u0000\u0000\u0000\u00d8"+
		"\u00d9\u0006\u000b\uffff\uffff\u0000\u00d9\u00da\u0005\f\u0000\u0000\u00da"+
		"\u00db\u0003\u0016\u000b\u0000\u00db\u00dc\u0005\u0010\u0000\u0000\u00dc"+
		"\u00ef\u0001\u0000\u0000\u0000\u00dd\u00de\u0005\u001d\u0000\u0000\u00de"+
		"\u00df\u0005\u0013\u0000\u0000\u00df\u00e0\u0005\u000e\u0000\u0000\u00e0"+
		"\u00e1\u0003\u0016\u000b\u0000\u00e1\u00e2\u0005\u000f\u0000\u0000\u00e2"+
		"\u00ef\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005\u001d\u0000\u0000\u00e4"+
		"\u00e5\u0005/\u0000\u0000\u00e5\u00e6\u0005\f\u0000\u0000\u00e6\u00ef"+
		"\u0005\u0010\u0000\u0000\u00e7\u00e8\u0005*\u0000\u0000\u00e8\u00ef\u0003"+
		"\u0016\u000b\u0006\u00e9\u00ef\u0005.\u0000\u0000\u00ea\u00ef\u0005+\u0000"+
		"\u0000\u00eb\u00ef\u0005,\u0000\u0000\u00ec\u00ef\u0005/\u0000\u0000\u00ed"+
		"\u00ef\u0005-\u0000\u0000\u00ee\u00d8\u0001\u0000\u0000\u0000\u00ee\u00dd"+
		"\u0001\u0000\u0000\u0000\u00ee\u00e3\u0001\u0000\u0000\u0000\u00ee\u00e7"+
		"\u0001\u0000\u0000\u0000\u00ee\u00e9\u0001\u0000\u0000\u0000\u00ee\u00ea"+
		"\u0001\u0000\u0000\u0000\u00ee\u00eb\u0001\u0000\u0000\u0000\u00ee\u00ec"+
		"\u0001\u0000\u0000\u0000\u00ee\u00ed\u0001\u0000\u0000\u0000\u00ef\u011a"+
		"\u0001\u0000\u0000\u0000\u00f0\u00f1\n\f\u0000\u0000\u00f1\u00f2\u0007"+
		"\u0000\u0000\u0000\u00f2\u0119\u0003\u0016\u000b\r\u00f3\u00f4\n\u000b"+
		"\u0000\u0000\u00f4\u00f5\u0007\u0001\u0000\u0000\u00f5\u0119\u0003\u0016"+
		"\u000b\f\u00f6\u00f7\n\n\u0000\u0000\u00f7\u00f8\u0007\u0002\u0000\u0000"+
		"\u00f8\u0119\u0003\u0016\u000b\u000b\u00f9\u00fa\n\t\u0000\u0000\u00fa"+
		"\u00fb\u0005&\u0000\u0000\u00fb\u0119\u0003\u0016\u000b\n\u00fc\u00fd"+
		"\n\b\u0000\u0000\u00fd\u00fe\u0005\'\u0000\u0000\u00fe\u0119\u0003\u0016"+
		"\u000b\t\u00ff\u0100\n\u0011\u0000\u0000\u0100\u0101\u0005\u000e\u0000"+
		"\u0000\u0101\u0102\u0003\u0016\u000b\u0000\u0102\u0103\u0005\u000f\u0000"+
		"\u0000\u0103\u0119\u0001\u0000\u0000\u0000\u0104\u0105\n\u0010\u0000\u0000"+
		"\u0105\u0106\u0005\u0003\u0000\u0000\u0106\u0107\u0005/\u0000\u0000\u0107"+
		"\u0110\u0005\f\u0000\u0000\u0108\u010d\u0003\u0016\u000b\u0000\u0109\u010a"+
		"\u0005\u0012\u0000\u0000\u010a\u010c\u0003\u0016\u000b\u0000\u010b\u0109"+
		"\u0001\u0000\u0000\u0000\u010c\u010f\u0001\u0000\u0000\u0000\u010d\u010b"+
		"\u0001\u0000\u0000\u0000\u010d\u010e\u0001\u0000\u0000\u0000\u010e\u0111"+
		"\u0001\u0000\u0000\u0000\u010f\u010d\u0001\u0000\u0000\u0000\u0110\u0108"+
		"\u0001\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u0112"+
		"\u0001\u0000\u0000\u0000\u0112\u0119\u0005\u0010\u0000\u0000\u0113\u0114"+
		"\n\u000f\u0000\u0000\u0114\u0115\u0005\u0003\u0000\u0000\u0115\u0119\u0005"+
		"\u001c\u0000\u0000\u0116\u0117\n\u0007\u0000\u0000\u0117\u0119\u0007\u0003"+
		"\u0000\u0000\u0118\u00f0\u0001\u0000\u0000\u0000\u0118\u00f3\u0001\u0000"+
		"\u0000\u0000\u0118\u00f6\u0001\u0000\u0000\u0000\u0118\u00f9\u0001\u0000"+
		"\u0000\u0000\u0118\u00fc\u0001\u0000\u0000\u0000\u0118\u00ff\u0001\u0000"+
		"\u0000\u0000\u0118\u0104\u0001\u0000\u0000\u0000\u0118\u0113\u0001\u0000"+
		"\u0000\u0000\u0118\u0116\u0001\u0000\u0000\u0000\u0119\u011c\u0001\u0000"+
		"\u0000\u0000\u011a\u0118\u0001\u0000\u0000\u0000\u011a\u011b\u0001\u0000"+
		"\u0000\u0000\u011b\u0017\u0001\u0000\u0000\u0000\u011c\u011a\u0001\u0000"+
		"\u0000\u0000\u001a\u001b&17=JY_djqw\u007f\u0086\u0096\u009c\u00a7\u00b4"+
		"\u00b8\u00bd\u00d6\u00ee\u010d\u0110\u0118\u011a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}