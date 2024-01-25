package game;

import crosshair.CrossHair;
import spirites.SpiriteHolders;
import spirites.Sprite;
import factories.ZombieFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TODO dodać po lewej stornie jakas postac, jakiegos bohatera czy cos. aby on strzel, aby mial jakas bron (skorke broni) i aby ona sie obracala tam gdzie jest celownik.
// I w tedy np zrobic jakas bariere po lewej stronie, jakis domek, i jesli zoombie wejdzie w domek to gracz obrywa.
public class Main {
    public final static int backgroundWIDTH = 1000;
    public final static int backgroundHEIGHT = 700;
    public static int MAX_SPIRITES = 5;
    public static void main(String[] args) throws InterruptedException {
        //TODO dodać Zombie Factory do SpiritHoldera, o albo SpiritHolder ma funkcje dodajaca kolejna fabrykę, przyjmuje nazwę klasy i dodaje instancję do jakiejś listy czy coś
        // i w tedy do schedduledExecutor można dawać kolejne funkcje ktore maja sie wolac.
        ZombieFactory zombieFactory = ZombieFactory.getInstance("../resources/walkingdead.png");
        CrossHair crossHair = new CrossHair();

        // Przerobić na liste list, lub na jakiś słownik, gdzie mamy klay, tak aby np Game.DrawPanel mógł rysować wszystkie obiekty
        SpiriteHolders.MAX_SPIRITES = MAX_SPIRITES;
        List<Sprite> spriteList = new ArrayList<>();
        SpiriteHolders spawnSpirite = new SpiriteHolders(zombieFactory, spriteList);


        JFrame frame = new JFrame("Zombie");
        DrawPanel panel = new DrawPanel(Main.class.getResource("/resources/tlo.jpg"), crossHair, 30, spriteList);

        //TODO zamienic wyswietlanie tez na zegarowe. tak samo jak robimy spawnowanie, to zroibc uaktualnianie
        // był problem, bo wołało się przed utworzeniem klasy.
        // Przeniesc do zombie factory ??? ewentualnie do Spirit Holders?
        // albo wgl zrobić listę
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(spawnSpirite::spawnSpirite, 0, 2, TimeUnit.SECONDS);


        frame.setContentPane(panel);
        frame.setSize(backgroundWIDTH, backgroundHEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        // czy na pewno jest przekazywana wszedzie referencja do listy
        /*
        while(true){
            Thread.sleep(100);
            System.out.println(spriteList.size());
        }
        */
    }



}

/*
zastosowane wzorce:
- Factory - tworzenie obiektów
- Singleton - przy tworzeniu fabryk.
- Observer - Celownik



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











