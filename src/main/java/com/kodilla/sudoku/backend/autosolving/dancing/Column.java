package com.kodilla.sudoku.backend.autosolving.dancing;

public class Column extends Node {

    int size;
    String name;

    Column(String n) {
        super();
        size = 0;
        name = n;
        C = this;
    }

    void cover() {
        unlinkLR();
        for (Node i = this.D; i != this; i = i.D) {
            for (Node j = i.R; j != i; j = j.R) {
                j.unlinkUD();
                j.C.size--;
            }
        }
    }

    void uncover() {
        for (Node i = this.U; i != this; i = i.U) {
            for (Node j = i.L; j != i; j = j.L) {
                j.C.size++;
                j.relinkUD();
            }
        }
        relinkLR();
    }
}
