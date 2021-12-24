package com.kagu.edit.jkagu.engine.actions;

import java.util.Stack;

public class CommandHistory {
    private static final Stack<Command> history = new Stack<>();

    public static void push(Command c) {
        history.push(c);
    }

    public static Command pop() {
        return history.pop();
    }

    public static boolean isEmpty() {
        return history.isEmpty();
    }
}
