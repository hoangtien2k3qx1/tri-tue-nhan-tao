package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.*;

import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class State {
    private final String bank;
    private final TreeSet<String> left;
    private final TreeSet<String> right;

    private boolean checkAllowBank(TreeSet<String> b) {
        if (b.contains("W") && b.contains("S") && (!b.contains("F")))
            return false;
        return !b.contains("S") || !b.contains("C") || (b.contains("F"));
    }

    public boolean isAllow() {
        if (!checkAllowBank(left)) return false;
        return checkAllowBank(right);
    }

    public boolean isSolution() {
        return left.isEmpty()
                && right.contains("W")
                && right.contains("S")
                && right.contains("C")
                && right.contains("F");
    }

    public State transits(String move) {
        String nBank;
        TreeSet<String> nLeft = new TreeSet<>();
        TreeSet<String> nRight = new TreeSet<>();

        if (bank.equalsIgnoreCase("left"))
            nBank = "right";
        else
            nBank = "left";

        copylist(right, nRight);
        copylist(left, nLeft);

        for (int i = 0; i < move.length(); i++) {
            String item = move.substring(i, i + 1);
            if (bank.equalsIgnoreCase("left")) {
                if (nLeft.remove(item))
                    nRight.add(item);
                else
                    return null;
            } else {
                if (nRight.remove(item))
                    nLeft.add(item);
                else
                    return null;
            }
        }

        return new State(nBank, nLeft, nRight);
    }

    private void copylist(TreeSet<String> src, TreeSet<String> dst) {
        dst.addAll(src);
    }

    public boolean compare(State s) {
        TreeSet<String> tmp;

        if (!s.getBank().equalsIgnoreCase(bank))
            return false;

        tmp = s.getLeft();
        for (String e : left) {
            if (!tmp.contains(e))
                return false;
        }

        tmp = s.getRight();
        for (String e : right) {
            if (!tmp.contains(e))
                return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("{L:");
        for (String e : left)
            ret.append(e);

        ret.append(" ");

        ret.append("R:");
        for (String e : right)
            ret.append(e);

        ret.append("}");
        return ret.toString();
    }
}