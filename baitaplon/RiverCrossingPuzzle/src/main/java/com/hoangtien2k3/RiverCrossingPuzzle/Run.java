package com.hoangtien2k3.RiverCrossingPuzzle;

import java.util.ArrayList;

public class Run {
    public static void main(String[] args) {
        System.out.println("=> GIẢI BÀI TOÁN QUA SÔNG - (SÓI, CỪU, BẮP CẢI, NGƯỜI NÔNG DÂN) <=\n");
        FarmerWolfCabbageSheep obj = new FarmerWolfCabbageSheep();

        System.out.println("* TẠO BIỂU ĐỒ TRẠNG THÁI BẰNG CÁC SỬ DỤNG TÌM KIẾM THEO CHIỀU RỘNG:");
        obj.startBreadthFirstSearch();

        System.out.println("\n\n* TẠO BIỂU ĐỒ TRẠNG THÁI BẰNG CÁCH SỬ DỤNG TÌM KIẾM THEO CHIỀU RỘNG:");
        obj.printBFSGraph();

        System.out.println("\n\n* HÌNH ẢNH TRỰC QUAN LỜI GIẢI CHO BÀI TOÁN QUAN SÔNG - BFS:");
        obj.printSolution();

        System.out.println("\n\n* TẠO BIỂU ĐỒ TRẠNG THÁI BẰNG CÁCH SỬ DỤNG TÌM KIẾM THEO CHỀU SÂU:");
        obj.startDepthFirstSearch();
    }
}
