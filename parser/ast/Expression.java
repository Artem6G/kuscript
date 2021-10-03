package parser.ast;

import lib.Value;

public interface Expression extends Node, Cloneable {
    Value eval();
}
