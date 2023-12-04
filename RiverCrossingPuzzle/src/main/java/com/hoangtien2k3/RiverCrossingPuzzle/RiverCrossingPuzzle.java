package com.hoangtien2k3.RiverCrossingPuzzle;

import java.util.*;


public class RiverCrossingPuzzle {
    private final String[] moves = {"F", "FW", "FS", "FC"};
    private final ArrayList<Node> queue;
    private ArrayList<Node> solutions;
    private Node root;

    public RiverCrossingPuzzle() {
        queue = new ArrayList<>();
        solutions = new ArrayList<>();
    }

    public void startBreadthFirstSearch() {
        solutions = new ArrayList<>();
        TreeSet<String> left = new TreeSet<>(Set.of("W", "S", "C", "F"));
        root = new Node(new State(StateType.left.name(), left, new TreeSet<>()));
        root.level = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.remove(0);
            System.out.println("Tiến độ Level: " + node.level + ": " + node.data);
            for (String m : moves) {
                State s = node.data.transits(m);

                if (s != null && s.isAllow()) {
                    Node child = new Node(s);
                    child.parent = node;
                    child.level = node.level + 1;
                    child.move = m + " moves " + child.data.getBank();

                    if (child.isAncestor()) {
                        node.adjList.add(child);

                        if (!child.data.isSolution()) {
                            queue.add(child);
                            System.out.println("Thêm Trạng Thái: " + child.data);
                        } else {
                            solutions.add(child);
                            System.out.println("Tìm Đường đi: " + child.data);
                        }
                    }

                }
            }
        }
    }

    public void startDepthFirstSearch() {
        int dlimit = 1;
        solutions = new ArrayList<>();
        while (solutions.isEmpty() && dlimit <= 10) {
            TreeSet<String> left = new TreeSet<>(Set.of("W", "S", "C", "F"));
            root = new Node(new State(StateType.left.name(), left, new TreeSet<>()));
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
        System.out.println("Tiến độ Level " + r.level + " " + r.data);
        for (String m : moves) {
            State s = r.data.transits(m);
            if (s != null && s.isAllow()) {
                Node child = new Node(s);
                child.parent = r;
                child.level = r.level + 1;
                child.move = m + " moves " + child.data.getBank();
                if (child.isAncestor()) {
                    r.adjList.add(child);
                    if (child.data.isSolution()) {
                        solutions.add(child);
                        System.out.println("Tìm đường đi: " + child.data);
                        return;
                    } else {
                        startDFS(depth - 1, child);
                    }
                }
            }
        }
    }

    // in ra các trường hợp có thể xảy ra khi {"FARMER", "WOLF", "CABBAGE", "SHEEP"} qua sông
    public void printBFSGraph() {
        ArrayList<Node> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.remove(0);
            System.out.println("Level " + node.level + ": " + node.data);
            ArrayList<Node> adjList = node.adjList;
            queue.addAll(adjList);
        }
    }

    public void printSolution() {
        System.out.println("No. of solutions:  " + solutions.size());
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
            System.out.println("Solution " + i);

            printSequence(stack);
            i++;
        }
    }

    private void printSequence(ArrayList<Node> stack) {
        StringBuilder buf = new StringBuilder();
        buf.append("No. of moves: ");
        buf.append(stack.size() - 1);
        buf.append("\n");

        for (int i = stack.size() - 1; i >= 0; i--) {
            Node node = stack.get(i);
            if (i != 0) {
                buf.append("Bước " + (stack.size() - i - 1) + ": (" + node.move + ")\n");
            } else {
                buf.append("Bước " + (stack.size() - i - 1) + ": (" + node.move + ")\n");
            }

            buf.append(drawState(node.data, node.move));
            buf.append("\n");
        }

        System.out.println(buf.toString());
        System.out.println("=".repeat(50));
    }


    private String drawState(State state, String move) {
        StringBuilder drawing = new StringBuilder();
        String[] names = {"FARMER", "WOLF", "CABBAGE", "SHEEP"};
        String space = " ".repeat(10);
        String pattern = "|| %s |                | %s ||";

        drawing.append("=".repeat(46)).append("\n");
        drawing.append(String.format(pattern, space, space)).append("\n");

        for (String name : names) {
            String characterName = name + " ".repeat(10 - name.length());

            // Step 0: ()
            if (move.isEmpty()) {
                drawing.append(String.format(pattern, characterName, space)).append("\n");
            }

            // Step 1: (FS moves right)
            else if (move.equals("FS moves right")) {
                if (name.equals("FARMER") || name.equals("SHEEP")) {
                    drawing.append(String.format(pattern, space, characterName)).append("\n");
                } else {
                    drawing.append(String.format(pattern, characterName, space)).append("\n");
                }
            }

            // Step 2: (F moves left)
            else if (move.equals("F moves left")) {
                if (name.equals("FARMER") || name.equals("WOLF") || name.equals("CABBAGE")) {
                    drawing.append(String.format(pattern, characterName, space)).append("\n");
                } else {
                    drawing.append(String.format(pattern, space, characterName)).append("\n");
                }
            }

            // Step 3: (FW moves right)
            else if (move.equals("FW moves right")) {
                if (name.equals("FARMER") || name.equals("WOLF") || name.equals("SHEEP")) {
                    drawing.append(String.format(pattern, space, characterName)).append("\n");
                } else {
                    drawing.append(String.format(pattern, characterName, space)).append("\n");
                }
            }

            // Step 4: (FS moves left)
            else if (move.equals("FS moves left")) {
                if (name.equals("WOLF")) {
                    drawing.append(String.format(pattern, space, characterName)).append("\n");
                } else {
                    drawing.append(String.format(pattern, characterName, space)).append("\n");
                }
            }

            // Step 5: (FC moves right)
            else if (move.equals("FC moves right")) {
                if (name.equals("FARMER") || name.equals("CABBAGE") || name.equals("WOLF")) {
                    drawing.append(String.format(pattern, space, characterName)).append("\n");
                } else {
                    drawing.append(String.format(pattern, characterName, space)).append("\n");
                }
            }

            // Step 6: (F moves left)
            else if (move.equals("F moves left")) {
                if (name.equals("CABBAGE") || name.equals("WOLF")) {
                    drawing.append(String.format(pattern, space, characterName)).append("\n");
                } else {
                    drawing.append(String.format(pattern, characterName, space)).append("\n");
                }
            }

            // Final State: (FS moves right)
            else {
                drawing.append(String.format(pattern, space, characterName)).append("\n");
            }

        }

        String boatLine = state.getBank().equals("right") ?
                "||  " + space + "|           \\___/|" + space + "  ||" :
                "||  " + space + "|\\___/           |" + space + "  ||";
        drawing.append(boatLine).append("\n");

        drawing.append(String.format(pattern, space, space)).append("\n");

        drawing.append("=".repeat(46)).append("\n");

        return drawing.toString();
    }

    private String centerText(State state, String character, String position) {
        int maxLength = 12;  // Adjust the maximum length as needed
        String formattedName = character + " ".repeat(maxLength - character.length());

        if (state.getBank().equalsIgnoreCase(position)) {
            return formattedName;
        } else {
            return " ".repeat(maxLength);
        }
    }

    public static void main(String[] args) {
        System.out.println("Solving Wolf, Sheep, Cabbage, Farmer, River Crossing Puzzle\n");
        RiverCrossingPuzzle obj = new RiverCrossingPuzzle();

        System.out.println("Creating State Graph using Breadth First Search");
        obj.startBreadthFirstSearch();

        System.out.println("\n\nState Graph in Breadth first order");
        obj.printBFSGraph();
        System.out.println("\n\n");

        System.out.println("Solutions to the River Crossing Puzzle BFS");
        obj.printSolution();

        System.out.println("\n\nCreating State Graph using Iterative Depth First Search");
        obj.startDepthFirstSearch();

    }
}
