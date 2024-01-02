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
    public Node parent; // F, W, S, C (Con trỏ) // child
    public State state;
    public ArrayList<Node> adjList;
    public int level;
    public String move;

    public Node(State data) {
        this.parent = null;
        this.state = data;
        this.adjList = new ArrayList<>();
        this.level = 0;
        this.move = "";
    }

    public boolean isAncestor() {
        Node node = parent; // Node child => Node node = note.parent
        boolean ret = false;
        while (node != null) {
            if (state.compare(node.state)) {
                ret = true;
                break;
            }

            node = node.parent; // note = note -> next
        }
        return ret;
    }
}