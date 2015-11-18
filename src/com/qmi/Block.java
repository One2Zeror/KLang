package com.qmi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public class Block implements ASTNode {
    private List<ASTNode> statements = new ArrayList<>();

    public void addStatement(ASTNode statement) {
        statements.add(statement);
    }

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public Object parseValue(Visitor visitor) {
        return visitor.visitNode(this);
    }
}
