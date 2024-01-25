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

//TODO zamieic odpowiedzialnosc usuwania z listy z DrawPanel na jakas inna klase, np spiritesHolders? w tedy tam momzna dac zazeznosci usuwania np od broni
// Jakas snajperka ktora usuwa wszystkie ktore obejmuje strza (jesli jest jeden za drugim)

//TODO Dodać klase Weapon ktora ustaiwa / ma w soie ?? celownik, odglosy, amunicje? wyswietlany element
// I w Menu jest przelaczanie broni, przelaczenie generuje event, do lissenerow dodajemy klay odpowiedzialne za celownik, odglosy, amunicje, wyswietlanie
// w komunikacie przekazujemy klase Gun, ktora ma w sobie informacje o skorkach, glosie. Dostpene bronie sa na liscie / tablicy (bo ich malo bedzie, a dostep prosty)
// Mozna nawet dac ze nie jet mozliwa zmiana kolejnosci i ustawic na sztywno ktory jest na ktorym miejscu.
// W lisenerach, kazdy lisener sobie bierze np gun.getsound() co zwraca dzwiek, i w tedy klasa za dzwiek u siebie podmienia aktualny dzwiek broni na nowy.
// Podobnie celownik, w metodzie powiadomienia na cos w stylu gun.getImgNormal() gun.getImgFShot() i podmienia aktualne ustawienia + odswieza myszke.
// te klasy (rekordy) będą miały tylko pola, bez metod, bo wszystkie zaleznosci beda wstrzykiwane do ogolnej klasy gun, w ktorej beda na stale wartosci i
// Przy powiadomieniu, ze bron zostala zmieniona, to kazda klasa se zmienia wartosci.


//TODO zrobić listę sortowaną, w tedy usuwamy pierwszy element, ten ktory jest najbliższy, trafiony, w tedy szukamy dla którego jest spełnione isHit i tego usuwamy, i dalej ie musimy szukać

//TODO dodać po lewej stornie jakas postac, jakiegos bohatera czy cos. aby on strzel, aby mial jakas bron (skorke broni) i aby ona sie obracala tam gdzie jest celownik.
// I w tedy np zrobic jakas bariere po lewej stronie, jakis domek, i jesli zoombie wejdzie w domek to gracz obrywa.
public class Main {
    public final static int backgroundWIDTH = 1000;
    public final static int backgroundHEIGHT = 700;
    public static int MAX_SPIRITES = 5;
    public static void main(String[] args) {

        //TODO dodać Zombie Factory do SpiritHoldera, o albo SpiritHolder ma funkcje dodajaca kolejna fabrykę, przyjmuje nazwę klasy i dodaje instancję do jakiejś listy czy coś
        // i w tedy do schedduledExecutor można dawać kolejne funkcje ktore maja sie wolac.
        ZombieFactory zombieFactory = ZombieFactory.getInstance("../resources/walkingdead.png");
        CrossHair crossHair = new CrossHair();

        //TODO przeniesc do nowej klasy gun
        GunSounds gunSounds = new GunSounds("src/resources/gunshot.wav");
        Thread gunSoundsThread = new Thread(gunSounds);
        gunSoundsThread.start();
        //TODO dodać to do klasy Gun? jako obastracyjny poziom.
        crossHair.addCrossHairListener(gunSounds);


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

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                scheduledExecutorService.close();
                panel.animationThread.interrupt();
                gunSoundsThread.interrupt();
                // tu zatrzymaj watek
                // ale nie za pomocą metody stop() !!!

                //zatrzymaj też timer celownika za pomocą cancel(). Timer to także wątek...
            }
        });
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











