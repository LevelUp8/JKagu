package com.kagu.edit.jkagu.engine.query;

import com.kagu.edit.jkagu.engine.query.components.where.AND;
import com.kagu.edit.jkagu.engine.query.components.where.CompositeOperation;
import com.kagu.edit.jkagu.engine.query.components.where.OR;
import com.kagu.edit.jkagu.engine.query.components.where.Operation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BraketHandler {
    private static String removeAdditionalSpaces(String query) {
        String result = query.trim().replaceAll(" +", " ");
        return result;
    }


    public static Operation creator(String query) {
        String processedQuery = removeAdditionalSpaces(query);

        char[] chars = processedQuery.toCharArray();

        Deque<Integer> stack = new ArrayDeque<Integer>();

        AND andOperation = null;
        OR orOperation = null;
        CompositeOperation global = new AND(new ArrayList<>());


        boolean hasBrakets = false;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == '(') {
                stack.push(i);
                hasBrakets = true;
            }

            if (ch == ')') {
                /**
                 * Get the string and evaluate it.
                 */
                if (stack.size() == 0) {
                    throw new IllegalArgumentException("There is close brakert but there is no open braket for it!");
                }
                int startBraket = stack.pop();
                String inBraketString = query.substring((startBraket + 1), i);


                final Operation op = Parser.parse(inBraketString, false);

                final String restOfTheString = query.substring(i + 1);

                int nextAndIndex = restOfTheString.indexOf(" and ");
                int nextOrIndex = restOfTheString.indexOf(" or ");

                if (stack.size() > 0 && i == chars.length - 1) {
                    throw new IllegalArgumentException("There errors in open and close brakets!");
                }

                if (nextAndIndex == -1 && nextOrIndex == -1) {
                    if (null != andOperation) {
                        andOperation.addChildOperation(op);
                        global.addChildOperation(andOperation);
                        andOperation = null;
                    } else if (null != orOperation) {
                        orOperation.addChildOperation(op);
                        global.addChildOperation(orOperation);
                        orOperation = null;
                    } else {
                        global.addChildOperation(op);
                    }
                } else {
                    boolean nextWordIsAnd = false;
                    if (nextAndIndex == 0) {
                        nextWordIsAnd = true;
                    }

                    boolean nextWordIsOr = false;
                    if (nextOrIndex == 0) {
                        nextWordIsOr = true;
                    }

                    List<Operation> operations = new ArrayList<>();
                    operations.add(op);
                    if (nextWordIsAnd) {
                        if (null == andOperation) {
                            andOperation = new AND(operations);
                        } else {
                            andOperation.addChildOperation(op);
                        }

                    }

                    if (nextWordIsOr) {
                        if (null == orOperation) {
                            orOperation = new OR(operations);
                        } else {
                            orOperation.addChildOperation(op);
                        }
                    }
                }
            }
        }


        if (null != orOperation || null != andOperation) {
            throw new UnsupportedOperationException("There is mixing bettwen brakets and non brakets text. This case is still not supported");
        }

        if (!hasBrakets) {
            global.addChildOperation(Parser.parse(processedQuery, false));
        }

        return global;


    }
}
