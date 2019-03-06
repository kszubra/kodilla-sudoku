package com.kodilla.sudoku.backend.autosolving.dancing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DLinks {

    private Column header;
    private List<Node> answer;

    private void search(int k) {
        if (header.R == header) {
            handleSolution(answer);
        } else {
            Column c = selectColumnNodeHeuristic();
            c.cover();

            for (Node r = c.D; r != c; r = r.D) {
                answer.add(r);

                for (Node j = r.R; j != r; j = j.R) {
                    j.C.cover();
                }

                search(k + 1);

                r = answer.remove(answer.size() - 1);
                c = r.C;

                for (Node j = r.L; j != r; j = j.L) {
                    j.C.uncover();
                }
            }
            c.uncover();
        }
    }

    private Column selectColumnNodeHeuristic() {
        int min = Integer.MAX_VALUE;
        Column ret = null;
        for (Column c = (Column) header.R; c != header; c = (Column) c.R) {
            if (c.size < min) {
                min = c.size;
                ret = c;
            }
        }
        return ret;
    }

    private Column makeDLXBoard(boolean[][] grid) {
        final int COLS = grid[0].length;

        Column headerNode = new Column("header");
        List<Column> columnNodes = new ArrayList<>();

        for (int i = 0; i < COLS; i++) {
            Column n = new Column(Integer.toString(i));
            columnNodes.add(n);
            headerNode = (Column) headerNode.hookRight(n);
        }
        headerNode = headerNode.R.C;

        for (boolean[] aGrid : grid) {
            Node prev = null;
            for (int j = 0; j < COLS; j++) {
                if (aGrid[j]) {
                    Column col = columnNodes.get(j);
                    Node newNode = new Node(col);
                    if (prev == null)
                        prev = newNode;
                    col.U.hookDown(newNode);
                    prev = prev.hookRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = COLS;

        return headerNode;
    }

    DLinks(boolean[][] cover) {
        header = makeDLXBoard(cover);
    }

    public void runSolver() {
        answer = new LinkedList<>();
        search(0);
    }

    private void handleSolution(List<Node> answer) {
        int[][] result = parseBoard(answer);
        printSolution(result);
    }

    private int size = 9;

    private int[][] parseBoard(List<Node> answer) {
        int[][] result = new int[size][size];
        for (Node n : answer) {
            Node rcNode = n;
            int min = Integer.parseInt(rcNode.C.name);
            for (Node tmp = n.R; tmp != n; tmp = tmp.R) {
                int val = Integer.parseInt(tmp.C.name);
                if (val < min) {
                    min = val;
                    rcNode = tmp;
                }
            }
            int ans1 = Integer.parseInt(rcNode.C.name);
            int ans2 = Integer.parseInt(rcNode.R.C.name);
            int r = ans1 / size;
            int c = ans1 % size;
            int num = (ans2 % size) + 1;
            result[r][c] = num;
        }
        return result;
    }

    private static void printSolution(int[][] result) {
        int size = result.length;
        for (int[] aResult : result) {
            StringBuilder ret = new StringBuilder();
            for (int j = 0; j < size; j++) {
                ret.append(aResult[j]).append(" ");
            }
            System.out.println(ret);
        }
        System.out.println();
    }
}
