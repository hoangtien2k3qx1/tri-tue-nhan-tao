package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.Data;

import java.util.*;

@Data
public class FarmerWolfCabbageSheep {
    private final String[] moves = {"F", "FW", "FS", "FC"};
    private final ArrayList<Node> queue = new ArrayList<>();
    private ArrayList<Node> solutions = new ArrayList<>();
    private Node root;

    // Breadth First Search
    public void startBreadthFirstSearch() {
        TreeSet<String> left = new TreeSet<>(Set.of("W", "S", "C", "F"));
        root = new Node(new State("left", left, new TreeSet<>()));

        queue.add(root);

        while (!queue.isEmpty()) {
            // Bước 1: Lấy nút đầu tiên từ hàng đợi

            Node node = queue.remove(0);

            System.out.println("\t* Tiến trình Level " + node.level + ": " + node.state);

            // Bước 2: Duyệt qua tất cả các bước di chuyển có thể
            for (String m : moves) {
                // Bước 3: Tạo trạng thái mới dựa trên bước di chuyển (nháp)
                State state = node.state.transits(m);

                // Bước 4: Kiểm tra tính hợp lệ của trạng thái mới (với yêu cầu bài toán)
                if (state != null && state.isAllow()) {
                    // Bước 5: Tạo một nút mới
                    Node child = new Node(state);
                    child.parent = node; // địa chỉ của node khởi tạo ban đầu
                    child.level = node.level + 1;
                    child.move = m + " moves " + child.state.bank();

                    // Bước 6: Kiểm tra xem nút mới có là node cha không
                    if (!child.isAncestor()) {
                        // Bước 7: Thêm nút mới vào danh sách các nút con của nút hiện tại
                        node.adjList.add(child); // đệ quy lui

                        // Bước 8: Kiểm tra xem nút mới có phải là giải pháp không (và có thể kết thức đươc bài toán hay chưa)
                        if (!child.state.isSolution()) {
                            // Bước 9: Thêm nút mới vào hàng đợi
                            queue.add(child);
                            System.out.println("\t+ Thêm trạng thái: " + child.state);
                        } else {
                            // Bước 10: Nếu là giải pháp, System.out.println("\t+ Thêm trạng thái: " + child.state); m vào danh sách giải pháp
                            solutions.add(child);
                            System.out.println("\t-> Tìm giải pháp: " + child.state);
                        }
                    }
                }
            }
        }
    }

    public void startDepthFirstSearch() {
        int dlimit = 1; // Giới hạn chiều sâu
        solutions = new ArrayList<>();

        while (solutions.isEmpty()) {
            TreeSet<String> left = new TreeSet<>(Set.of("W", "S", "C", "F"));
            root = new Node(new State("left", left, new TreeSet<>()));

            System.out.println("Bắt đầu DFS lặp với độ sâu: " + dlimit);
            startDFS(dlimit, root);
            dlimit++;
        }
    }

    public void startDFS(int depth, Node node) {
        if (depth == 0) {
            System.out.println("Giới hạn độ sâu tối đa");
            return;
        }

        System.out.println("Level " + node.level + ": " + node.state);

        for (String m : moves) {
            State s = node.state.transits(m);

            if (s != null && s.isAllow()) {
                Node child = new Node(s);
                child.parent = node;
                child.level = node.level + 1;
                child.move = m + " moves " + child.state.bank();

                if (!child.isAncestor()) {
                    node.adjList.add(child);
                    if (child.state.isSolution()) {// Found a solution
                        solutions.add(child);
                        System.out.println("giải pháp: " + child.state);
                        return;
                    } else {// Recursive call ;
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
            Node node = queue.remove(0);
            System.out.println("\tLevel " + node.level + ": " + node.state);
            ArrayList<Node> adjlist = node.adjList;
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
        StringBuilder builder = new StringBuilder();
        builder.append("Số lượt phải di chuyển: ").append(stack.size() - 1).append("\n");

        int checkCase = 0;
        for (int i = stack.size() - 1; i >= 0; i--) {
            Node node = stack.get(i);
            if (i != 0) {
                builder.append("Bước ").append(stack.size() - i - 1).append(": (").append(node.move).append(")\n");
            } else {
                builder.append("Bước ").append(stack.size() - i - 1).append(": (").append(node.move).append(")\n");
            }

            builder.append(drawState(node.state, checkCase)).append("\n");
            checkCase++;
        }

        System.out.println(builder);
        System.out.println("=".repeat(50));
    }

    private String drawState(State state, int checkCase) {
        StringBuilder drawing = new StringBuilder();
        String[] names = {"NÔNG DÂN", "CON SÓI", "BẮP CẢI", "CON CỪU"};
        String space = " ".repeat(10);
        String pattern = "|| %s |                | %s ||";

        drawing.append("=".repeat(46)).append("\n");
        drawing.append(String.format(pattern, space, space)).append("\n");

        for (String name : names) {
            String characterName = name + " ".repeat(10 - name.length());

            String result = switch (checkCase) {
                case 0 -> String.format(pattern, characterName, space);
                case 1 -> {
                    boolean isRightMove = name.equals("NÔNG DÂN") || name.equals("CON CỪU");
                    yield String.format(pattern, isRightMove ? space : characterName, isRightMove ? characterName : space);
                }
                case 2 -> {
                    boolean isRightMove = name.equals("NÔNG DÂN") || name.equals("CON SÓI") || name.equals("BẮP CẢI");
                    yield String.format(pattern, isRightMove ? characterName : space, isRightMove ? space : characterName);
                }
                case 3 -> {
                    boolean isRightMove = name.equals("NÔNG DÂN") || name.equals("CON SÓI") || name.equals("CON CỪU");
                    yield String.format(pattern, isRightMove ? space : characterName, isRightMove ? characterName : space);
                }
                case 4 -> {
                    boolean isLeftMove = name.equals("CON SÓI");
                    yield String.format(pattern, isLeftMove ? space : characterName, isLeftMove ? characterName : space);
                }
                case 5 -> {
                    boolean isRightMove = name.equals("NÔNG DÂN") || name.equals("BẮP CẢI") || name.equals("CON SÓI");
                    yield String.format(pattern, isRightMove ? space : characterName, isRightMove ? characterName : space);
                }
                case 6 -> {
                    boolean isLeftMove = name.equals("BẮP CẢI") || name.equals("CON SÓI");
                    yield String.format(pattern, isLeftMove ? space : characterName, isLeftMove ? characterName : space);
                }
                case 7 -> String.format(pattern, space, characterName);
                default -> throw new IllegalArgumentException("CHECK KEY KHÔNG HỢP LỆ: " + checkCase);
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

}
