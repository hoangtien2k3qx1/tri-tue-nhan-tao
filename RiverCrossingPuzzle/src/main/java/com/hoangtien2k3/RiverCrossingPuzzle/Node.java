package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class Node {
    public Node parent;
    public State data;
    public ArrayList<Node> adjList;
    public int level;
    public String move;

    public Node(State data) {
        parent = null;
        this.data = data;
        adjList = new ArrayList<>();
        level = 0;
        move = "";
    }

    public boolean isAncestor() {
        Node n = parent;
        boolean ret = false;
        while (n != null) {
            if (data.compare(n.data)) {
                ret = true;
                break;
            }
            n = n.parent;
        }

        return ret;
    }
}