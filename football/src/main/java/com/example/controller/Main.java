package com.example.controller;

import com.example.model.MatchModel;
import com.example.view.View;

public class Main {

    public static void main(String[] args) {

        View view = new View();
        MatchModel model = new MatchModel();

        int option;

        do {
            option = view.menu();

            switch (option) {
                case 1:
                    model.insertMatch(view.readMatch());
                    break;

                case 2:
                    view.showMatches(model.getAllMatches());
                    model.deleteMatch(view.readId());
                    break;

                case 3:
                    view.showMatches(model.getAllMatches());
                    model.updateMatch(view.readId(), view.readMatch());
                    break;

                case 4:
                    view.showMatches(model.getAllMatches());
                    break;

                case 5:
                    String d1 = view.readText("Data inici: ");
                    String d2 = view.readText("Data fi: ");
                    view.showMatches(model.getMatchesByDate(d1, d2));
                    break;

                case 6:
                    String team = view.readText("Equip: ");
                    view.showMatches(model.getMatchesByTeam(team));
                    break;
            }

        } while (option != 0);
    }
}
