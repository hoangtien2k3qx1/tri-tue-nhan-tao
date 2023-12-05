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
    public Node parent; // Nút cha của nút
    public State data; // Trạng thái của nút
    public ArrayList<Node> adjList; // Nút con của nút
    public int level; // Độ sâu của nút
    public String move; // Sự di chuyển (chuyển tiếp) tạo ra dòng điện

//    public Node(State data) {
//        parent = null;
//        this.data = data;
//        adjList = new ArrayList<>();
//        level = 0;
//        move = "";
//    }

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