package com.self_project.task_management_api.utils;

import java.util.Scanner;

public class codeStar {
    static void segitiga(int n){
        for(int i=1;i<=n;i++){
            for(int j= 0;j<i;j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }

    static void segitigaKanan(int n){
        for(int i=1; i<=n; i++){
            for (int sp = 0; sp < n - i; sp++) {
                System.out.print(" ");
            };
            for (int st = 0; st < i; st++) {
                System.out.print("*");
            };
            System.out.println();
        }
    }

    static void piramida(int n){
        for(int i=1; i<n; i++){
            for(int j=n-i; j>1; j--){
                System.out.print(" ");
            }
            for(int k=0; k<(2*i-1); k++){
                System.out.print("*");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Pilih Metode Membuat Bintang: ");
        System.out.println("1. Segitiga");
        System.out.println("2. Piramida");
        System.out.println("3. Segitiga Kanan");
        System.out.println("=====================");

        System.out.print("Pilih Metode: ");
        int metode = sc.nextInt();
        System.out.print("input jumlah bintang: ");
        int jumlah = sc.nextInt();

        switch (metode) {
            case(1):
                segitiga(jumlah);
                break;
            case(2):
                piramida(jumlah);
                break;
            case(3):
                segitigaKanan(jumlah);
                break;
        }

        sc.close();

    }
}
