package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
public class FarmerWolfCabbageSheep {
    private final String[] moves = {"F", "FW", "FS", "FC"};
    private final ArrayList<Node> queue;
    private ArrayList<Node> solutions;
    private Node root;

    public FarmerWolfCabbageSheep() {
        queue = new ArrayList<>();
        solutions = new ArrayList<>();
    }

    public void startBreadthFirstSearch() {
        solutions = new ArrayList<>(); // Initialize solutions to zero
        TreeSet<String> left = new TreeSet<>(Set.of("W", "S", "C", "F"));

        root = new Node(State.builder()
                .bank("left")
                .left(left)
                .right(new TreeSet<>())
                .build());
        root.level = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            Node n = queue.remove(0);
            System.out.println("\t* Tiến trình Level " + n.level + ": " + n.data);
            for (String m : moves) {

                State s = n.data.transits(m);

                if (s != null && s.isAllow()) {

                    Node child = new Node(s);
                    child.parent = n;
                    child.level = n.level + 1;
                    child.move = m + " moves " + child.data.bank();

                    // Check that a node doesn't occur already as ancestor to
                    // prevent cycle in the graph
                    if (!child.isAncestor()) {
                        n.adjList.add(child);

                        if (!child.data.isSolution()) {
                            queue.add(child);
                            System.out.println("\t+ Thêm trạng thái: " + child.data);
                        } else {
                            solutions.add(child);
                            System.out.println("\t-> Tìm giải pháp: " + child.data);
                        }
                    }

                }

            }

        }
    }

    public void startDepthFirstSearch() {
        int dlimit = 1; // Giới hạn chiều sâu
        solutions = new ArrayList<>();

        while (solutions.isEmpty() && dlimit <= 10) {
            TreeSet<String> left = new TreeSet<>(Set.of("W", "S", "C", "F"));

            root = new Node(State.builder()
                    .bank("left")
                    .left(left)
                    .right(new TreeSet<>())
                    .build());
            root.level = 0;

            System.out.println("Bắt đầu DFS lặp với độ sâu: " + dlimit);
            startDFS(dlimit, root);
            dlimit++;
        }
    }

    public void startDFS(int depth, Node r) {
        if (depth == 0) {
            System.out.println("Giới hạn độ sâu tối đa");
            return;
        }

        System.out.println("===> Level " + r.level + " " + r.data);

        for (String m : moves) {
            State s = r.data.transits(m);

            if (s != null && s.isAllow()) {
                Node child = new Node(s);
                child.parent = r;
                child.level = r.level + 1;
                child.move = m + " moves " + child.data.bank();

                if (!child.isAncestor()) {
                    r.adjList.add(child);

                    if (child.data.isSolution()) {// Found a solution
                        solutions.add(child);
                        System.out.println("giải pháp: " + child.data);
                        return;
                    } else {// Recursive call
                        startDFS(depth - 1, child);
                    }
                }
            }
        }
    }

    public void printBFSGraph() {
        ArrayList<Node> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.remove(0);
            System.out.println("\tLevel " + n.level + ": " + n.data);

            ArrayList<Node> adjlist = n.adjList;
            queue.addAll(adjlist);
        }
    }

    public void printSolution() {
        System.out.println("- Số giải pháp:  " + solutions.size());
        ArrayList<Node> stack;
        Iterator<Node> iterator = solutions.iterator();

        int i = 1;
        while (iterator.hasNext()) {
            stack = new ArrayList<>();
            Node node = iterator.next();
            stack.add(node);
            node = node.parent;
            while (node != null) {
                stack.add(node);
                node = node.parent;
            }
            System.out.println("+ giải pháp " + i);

            printSequence(stack);
            i++;
        }
    }

    private void printSequence(ArrayList<Node> stack) {
        StringBuilder buf = new StringBuilder();
        buf.append("Số lượt phải di chuyển: ").append(stack.size() - 1).append("\n");

        int checkCase = 0;
        for (int i = stack.size() - 1; i >= 0; i--) {
            Node node = stack.get(i);
            if (i != 0) {
                buf.append("Bước ").append(stack.size() - i - 1).append(": (").append(node.move).append(")\n");
            } else {
                buf.append("Bước ").append(stack.size() - i - 1).append(": (").append(node.move).append(")\n");
            }

            buf.append(drawState(node.data, checkCase)).append("\n");
            checkCase++;
        }

        System.out.println(buf);
        System.out.println("=".repeat(50));
    }

    private String drawState(State state, int checkCase) {
        StringBuilder drawing = new StringBuilder();
        String[] names = {"FARMER", "WOLF", "CABBAGE", "SHEEP"};
        String space = " ".repeat(10);
        String pattern = "|| %s |                | %s ||";

        drawing.append("=".repeat(46)).append("\n");
        drawing.append(String.format(pattern, space, space)).append("\n");

        for (String name : names) {
            String characterName = name + " ".repeat(10 - name.length());

            String result = switch (checkCase) {
                case 0 -> String.format(pattern, characterName, space);
                case 1 -> {
                    boolean isRightMove = name.equals("FARMER") || name.equals("SHEEP");
                    yield String.format(pattern, isRightMove ? space : characterName, isRightMove ? characterName : space);
                }
                case 2 -> {
                    boolean isRightMove = name.equals("FARMER") || name.equals("WOLF") || name.equals("CABBAGE");
                    yield String.format(pattern, isRightMove ? characterName : space, isRightMove ? space : characterName);
                }
                case 3 -> {
                    boolean isRightMove = name.equals("FARMER") || name.equals("WOLF") || name.equals("SHEEP");
                    yield String.format(pattern, isRightMove ? space : characterName, isRightMove ? characterName : space);
                }
                case 4 -> {
                    boolean isLeftMove = name.equals("WOLF");
                    yield String.format(pattern, isLeftMove ? space : characterName, isLeftMove ? characterName : space);
                }
                case 5 -> {
                    boolean isRightMove = name.equals("FARMER") || name.equals("CABBAGE") || name.equals("WOLF");
                    yield String.format(pattern, isRightMove ? space : characterName, isRightMove ? characterName : space);
                }
                case 6 -> {
                    boolean isLeftMove = name.equals("CABBAGE") || name.equals("WOLF");
                    yield String.format(pattern, isLeftMove ? space : characterName, isLeftMove ? characterName : space);
                }
                case 7 -> String.format(pattern, space, characterName);
                default -> throw new IllegalArgumentException("CheckCase không hợp lệ: " + checkCase);
            };

            drawing.append(result).append("\n");
        }

        String boatLine = state.bank().equals("right") ?
                "||  " + space + "|           \\___/|" + space + "  ||" :
                "||  " + space + "|\\___/           |" + space + "  ||";
        drawing.append(boatLine).append("\n");

        drawing.append(String.format(pattern, space, space)).append("\n");
        drawing.append("=".repeat(46)).append("\n");

        return drawing.toString();
    }

    public static void main(String[] args) {
        System.out.println("=> GIẢI BÀI TOÁN QUA SÔNG - (SÓI, CỪU, BẮP CẢI, NGƯỜI NÔNG DÂN) <=\n");
        FarmerWolfCabbageSheep obj = new FarmerWolfCabbageSheep();

        System.out.println("* TẠO BIỂU ĐỒ TRẠNG THÁI BẰNG CÁC SỬ DỤNG TÌM KIẾM THEO CHIỀU RỘNG:");
        obj.startBreadthFirstSearch();

        System.out.println("\n\n* MÔ HÌNH - HÌNH ẢNH TRỰC QUAN VỀ TÌM KIẾM THEO CHIỀU RỘNG:");
        obj.printBFSGraph();

        System.out.println("\n\n* LỜI GIẢI CHO BÀI TOÁN QUAN SÔNG - BFS:");
        obj.printSolution();

        System.out.println("\n\n* TẠO BIỂU ĐỒ TRẠNG THÁI BẰNG CÁCH SỬ DỤNG TÌM KIẾM THEO CHỀU SÂU:");
        obj.startDepthFirstSearch();
    }

}
