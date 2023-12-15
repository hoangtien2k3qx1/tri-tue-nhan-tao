package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@With
@Accessors(fluent = true)
public class Node {
    public Node parent;
    public State data;
    public ArrayList<Node> adjList;
    public int level;
    public String move;

    public Node(State data) {
        this.parent = null;
        this.data = data;
        this.adjList = new ArrayList<Node>();
        this.level = 0;
        this.move = "";
    }

    public boolean isAncestor() {
        Node node = parent;
        boolean ret = false;
        while (node != null) {
            if (data.compare(node.data)) {
                ret = true;
                break;
            }

            node = node.parent;
        }
        return ret;
    }
}