package com.example.view;

import java.util.List;
import java.util.Scanner;

import com.example.model.Match;

public class View {

    private Scanner sc = new Scanner(System.in);

    public int menu() {
        System.out.println("1. Afegir partit");
        System.out.println("2. Eliminar partit");
        System.out.println("3. Modificar partit");
        System.out.println("4. Llistar tots");
        System.out.println("5. Llistar entre dates");
        System.out.println("6. Cercar per equip");
        System.out.println("0. Sortir");
        return sc.nextInt();
    }

    public Match readMatch() {
        sc.nextLine();
        System.out.print("Equip local: ");
        String el = sc.nextLine();
        System.out.print("Equip visitant: ");
        String ev = sc.nextLine();
        System.out.print("Gols local: ");
        int gl = sc.nextInt();
        System.out.print("Gols visitant: ");
        int gv = sc.nextInt();
        sc.nextLine();
        System.out.print("Competició: ");
        String c = sc.nextLine();
        System.out.print("Data (YYYY-MM-DD): ");
        String f = sc.nextLine();

        return new Match(el, ev, gl, gv, c, f);
    }

    public String readId() {
        sc.nextLine();
        System.out.print("ID: ");
        return sc.nextLine();
    }

    public String readText(String msg) {
        sc.nextLine();
        System.out.print(msg);
        return sc.nextLine();
    }

    public void showMatches(List<Match> matches) {
        for (Match m : matches) {
            System.out.println(m);
        }
    }
}
