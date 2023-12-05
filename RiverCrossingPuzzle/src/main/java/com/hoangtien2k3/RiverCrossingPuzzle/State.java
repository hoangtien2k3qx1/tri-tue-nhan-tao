package com.hoangtien2k3.RiverCrossingPuzzle;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.TreeSet;

@AllArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class State {
    private String bank; // Ngân hàng đang hoạt động nơi người nông dân hiện đang ở bờ
    private TreeSet<String> left; // bờ trái
    private TreeSet<String> right; // bờ phải

    private boolean checkAllowBank(TreeSet<String> b) {
        // Sói và Cừu cùng nhau không có Người nông dân
        if (b.contains("W") && b.contains("S") && (!b.contains("F")))
            return false;
        // Cừu và bắp cải cùng nhau không có Nông dân
        return !b.contains("S") || !b.contains("C") || (b.contains("F"));
    }

    public boolean isAllow() {
        return checkAllowBank(left) && checkAllowBank(right);
    }

    public boolean isSolution() {
        return left.isEmpty() && right.contains("W") && right.contains("S") && right.contains("C")
                && right.contains("F");
    }

    public State transits(String move) {
        String nbank;
        TreeSet<String> nleft = new TreeSet<>();
        TreeSet<String> nright = new TreeSet<>();

        if (bank.equalsIgnoreCase("left"))
            nbank = "right";
        else
            nbank = "left";

        copylist(right, nright);
        copylist(left, nleft);

        for (int i = 0; i < move.length(); i++) {
            String item = move.substring(i, i + 1);
            if (bank.equalsIgnoreCase("left")) {
                if (nleft.remove(item))
                    nright.add(item);
                else
                    return null; // trả về null nếu di chuyển chứa
            } else {
                if (nright.remove(item))
                    nleft.add(item);
                else
                    return null; // trả về null nếu di chuyển chứa
            }
        }

        return new State(nbank, nleft, nright);
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
        ret.append("[L:(");

        for (String e : left)
            ret.append(e);

        ret.append(")  ");
        ret.append("R:(");

        for (String e : right)
            ret.append(e);

        ret.append(")]");
        return ret.toString();
    }

}
