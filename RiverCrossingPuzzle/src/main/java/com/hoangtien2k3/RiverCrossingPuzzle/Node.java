package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@AllArgsConstructor
@RequiredArgsConstructor
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