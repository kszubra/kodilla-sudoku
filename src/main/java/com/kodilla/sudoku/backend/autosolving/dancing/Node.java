package com.kodilla.sudoku.backend.autosolving.dancing;

public class Node {
    Node L, R, U, D;
    Column C;

    Node hookDown(Node node) {
        assert (this.C == node.C);
        node.D = this.D;
        node.D.U = node;
        node.U = this;
        this.D = node;
        return node;
    }

    Node hookRight(Node node) {
        node.R = this.R;
        node.R.L = node;
        node.L = this;
        this.R = node;
        return node;
    }

    void unlinkLR() {
        this.L.R = this.R;
        this.R.L = this.L;
    }

    void relinkLR() {
        this.L.R = this.R.L = this;
    }

    void unlinkUD() {
        this.U.D = this.D;
        this.D.U = this.U;
    }

    void relinkUD() {
        this.U.D = this.D.U = this;
    }

    Node() {
        L = R = U = D = this;
    }

    Node(Column c) {
        this();
        C = c;
    }



}
