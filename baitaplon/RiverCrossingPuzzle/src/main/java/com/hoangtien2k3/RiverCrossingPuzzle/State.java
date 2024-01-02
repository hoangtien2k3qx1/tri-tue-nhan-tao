package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.TreeSet;

@Data
@Builder
@Accessors(fluent = true)
@AllArgsConstructor
public class State {
    private String bank; // left or right
    private TreeSet<String> left; // W, C, S
    private TreeSet<String> right;

    private boolean checkAllowBank(TreeSet<String> b) {
        if (b.contains("W") && b.contains("S") && !b.contains("F"))
            return false;
        return !b.contains("S") || !b.contains("C") || b.contains("F");
    }

    public boolean isAllow() {
        return checkAllowBank(left) && checkAllowBank(right);
    }

    public boolean isSolution() {
        return left.isEmpty()
                && right.contains("W")
                && right.contains("S")
                && right.contains("C")
                && right.contains("F");
    }

    // Kiểm tra xem khả năng nhân vật đó qua sông có hợp lệ hay không
    public State transits(String move) {
        String newBank;
        TreeSet<String> newLeft = new TreeSet<>();
        TreeSet<String> newRight = new TreeSet<>();

        if (bank.equalsIgnoreCase("left"))
            newBank = "right";
        else
            newBank = "left";

        copylist(right, newRight);
        copylist(left, newLeft);

        for (int i = 0; i < move.length(); i++) {
            String item = move.substring(i, i + 1); // F
            if (bank.equalsIgnoreCase("left")) {
                if (newLeft.remove(item))
                    newRight.add(item);
                else
                    return null; // trả về null nếu di chuyển chứa
            } else {
                if (newRight.remove(item))
                    newLeft.add(item);
                else
                    return null; // trả về null nếu di chuyển chứa
            }
        }

        return new State(newBank, newLeft, newRight);
    }

    private void copylist(TreeSet<String> src, TreeSet<String> dst) {
        dst.addAll(src);
    }

    public boolean compare(State s) {
        TreeSet<String> tmp;

        if (!s.bank.equalsIgnoreCase(bank))
            return false;

        tmp = s.left;
        for (String e : left) {
            if (!tmp.contains(e))
                return false;
        }

        tmp = s.right;
        for (String e : right) {
            if (!tmp.contains(e))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[Left:(");

        for (String e : left) ret.append(e);

        ret.append(")  ");
        ret.append("Right:(");

        for (String e : right) ret.append(e);

        ret.append(")]");
        return ret.toString();
    }

}
