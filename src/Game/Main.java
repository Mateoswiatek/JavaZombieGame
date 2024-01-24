package Game;

import CrossHair.CrossHair;
import spirites.SpiriteHolders;
import spirites.Sprite;
import factories.ZombieFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        ZombieFactory zombieFactory = ZombieFactory.getInstance("../resources/walkingdead.png");
        CrossHair crossHair = new CrossHair();

        // Przerobić na liste list, lub na jakiś słownik, gdzie mamy klay, tak aby np Game.DrawPanel mógł rysować wszystkie obiekty
        List<Sprite> spriteList = new ArrayList<>();
        SpiriteHolders spawnSpirite = new SpiriteHolders(zombieFactory, spriteList);


        JFrame frame = new JFrame("Zombie");
        DrawPanel panel = new DrawPanel(Main.class.getResource("/resources/tlo.jpg"), crossHair, 30, spriteList );

        //TODO zamienic wyswietlanie tez na zegarowe. tak samo jak robimy spawnowanie, to zroibc uaktualnianie
        // był problem, bo wołało się przed utworzeniem klasy.
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(spawnSpirite::spawnSpirite, 0, 2, TimeUnit.SECONDS);


        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }



}

/*
zastosowane wzorce:
- Factory - tworzenie obiektów
- Singleton  - przy tworzeniu fabryk.
- Observer Obserwator - Celownik



 */

/*
wzorzec factory

inwersja kontroli, spritefactory - cos ogolnego co możemy podstawić coś konkretnego.
Akcje usera,
przerysowywanie ekranu,

musi byc wzajemne wykluczanie, albo synchronize, albo mutexa - semafory.


celownik - wzorzec - poprzez zdarzenia. generacja zdarzeń. observer / lisener. - subkryer.
nasz panel zapisuje się jako subskryber - chce otrzymywać informację o czymś - o ruchach myszy i o naciśnięciu.

Mause press -> zbocze gdy naciska,
Mause click -> naciśnięcie i puszczenie. / zwolnienine przycisku


sztuczka z aktywacją -> timer zmieniający na chwilę -> np ogień czy coś podczas naciśnięciu.



lista nasłuchujących. metoda add / remove, notify lisseners - wysłanie do wszytkich elementów.
w tym przypadku będzie tylko inforamcja do Game.DrawPanel, ale to jest przykład bardziej ogólnego podejścia.


zamiast wszsytkich hitboxu, pośredni jest wewnątrz.
najpierw sprawdzamy se, ogólny / główny hitbox, i dopiero jeśli jest w tym dużym, no to dopiero w tedy sprawdzamy konkretne hitboxy.
dzięki temu oszczędzamy na obliczeniach. sprawdzamy najbardziej ogółny hitbox w którym zawierają się wszytkie inne.


Wątek nie tworzymy w Pant component XD,


wektor zamiast Array list, i wektor jest bezpieczny na wątki, ale to nie jest zbyt git.
Bo łatwiej np zrobić blok w którym ustawiamy wszystkie zombie niż dla kazdego get() wołamy synchronicznie.

nosql git przy dodawaniu,

 */











