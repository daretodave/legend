package com.legend.event;

public enum Button {

	/**
	 * A-Z
	 */
	A("a", "A", true), B("b", "B", true), C("c", "C", true), D("d", "D", true), E(
			"e", "E", true), F("f", "F", true), G("g", "G", true), H("h", "H",
			true), I("i", "I", true), J("j", "J", true), K("k", "K", true), L(
			"l", "L", true), M("m", "M", true), N("n", "N", true), O("o", "O",
			true), P("p", "P", true), Q("q", "Q", true), R("r", "R", true), S(
			"s", "S", true), T("t", "T", true), U("u", "U", true), V("v", "V",
			true), W("w", "W", true), X("x", "X", true), Y("y", "Y", true), Z(
			"z", "Z", true),

	/*
	 * 0-9 (Numpad)
	 */
	ZERO_NUMPAD("0", false), ONE_NUMPAD("1", false), TWO_NUMPAD("2", false), THREE_NUMPAD(
			"3", false), FOUR_NUMPAD("4", false), FIVE_NUMPAD("5", false), SIX_NUMPAD(
			"6", false), SEVEN_NUMPAD("7", false), EIGHT_NUMPAD("8", false), NINE_NUMPAD(
			"9", false),

	/*
	 * 0-9
	 */
	ZERO("0", ")", false), ONE("1", "!", false), TWO("2", "@", false), THREE(
			"3", "#", false), FOUR("4", "$", false), FIVE("5", "%", false), SIX(
			"6", "^", false), SEVEN("7", "&", false), EIGHT("8", "*", false), NINE(
			"9", "(", false),

	/**
	 * Various with no shift
	 */
	DIVIDE("/", false),
	MULTIPLY("*", false),
	MINUS2("-", false),
	PLUS2("+", false),
	
	/**
	 * Various with shift
	 */
	TILDA("`", "~", false),
	MINUS("-", "_", false),
	PLUS("+", "=", false),
	BRACKET_LEFT("[", "{", false),
	BRACKET_RIGHT("]", "}", false),
	BACKSLASH("\\", "|", false),
	SEMICOLON(";", ":", false),
	SINGLEQOUTE("'", "\"", false),
	COMMA(",", "<", false),
	PERIOD(".", ">", false),
	SLASH("/", "?", false),
		
	/**
	 * Action Keys
	 */
	BACKSPACE, TAB, SHIFT, CONTROL, ALT, INSERT, MENU, HOME, DELETE, END, PAGEUP, PAGEDOWN, ENTER, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, UNDEFINED;

	public boolean isActionKey() {
		return actionKey;
	}

	public String getResolve() {
		return resolve;
	}

	public String getAlternate() {
		return alternate;
	}

	public boolean isCapslockEffected() {
		return capslockEffected;
	}

	Button() {
		this.resolve = null;
		this.alternate = null;
		this.capslockEffected = false;
		this.actionKey = true;
	}

	Button(String resolve, boolean capslockEffected) {
		this.resolve = resolve;
		this.alternate = null;
		this.capslockEffected = capslockEffected;
		this.actionKey = false;
	}

	Button(String resolve, String alternate, boolean capslockEffected) {
		this.resolve = resolve;
		this.alternate = alternate;
		this.capslockEffected = capslockEffected;
		this.actionKey = false;
	}

	private final boolean actionKey;
	private final String resolve;
	private final String alternate;
	private final boolean capslockEffected;

}