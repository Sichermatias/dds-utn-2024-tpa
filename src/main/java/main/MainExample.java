package main;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class MainExample implements WithSimplePersistenceUnit {

    public static void main(String[] args) {
        MainExample instance = new MainExample();
        instance.inicializar();
    }

    private void inicializar() {
        withTransaction(() -> {
        });
    }
}
