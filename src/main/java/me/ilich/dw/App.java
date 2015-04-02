package me.ilich.dw;


public class App {

    public static void main(String[] params) {
        System.out.println("dw v0.1");
        Game game = new Game();
        while (game.isWorking()) {
            game.nextStep();
        }
        System.out.println("finish");
    }

}
