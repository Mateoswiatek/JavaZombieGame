import factories.ZombieFactory;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        ZombieFactory zombieFactory = new ZombieFactory("../resources/walkingdead.png");

        JFrame frame = new JFrame("Zombie");
        DrawPanel panel = new DrawPanel(Main.class.getResource("/resources/tlo.jpg"), zombieFactory);

        //TODO zamienic wyswietlanie tez na zegarowe. tak samo jak robimy spawnowanie, to zroibc uaktualnianie
        // był problem, bo wołało się przed utworzeniem klasy.
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(panel::spawnSpirite, 0, 2, TimeUnit.SECONDS);


        frame.setContentPane(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }
}


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
w tym przypadku będzie tylko inforamcja do DrawPanel, ale to jest przykład bardziej ogólnego podejścia.


zamiast wszsytkich hitboxu, pośredni jest wewnątrz.
najpierw sprawdzamy se, ogólny / główny hitbox, i dopiero jeśli jest w tym dużym, no to dopiero w tedy sprawdzamy konkretne hitboxy.
dzięki temu oszczędzamy na obliczeniach. sprawdzamy najbardziej ogółny hitbox w którym zawierają się wszytkie inne.


Wątek nie tworzymy w Pant component XD,


wektor zamiast Array list, i wektor jest bezpieczny na wątki, ale to nie jest zbyt git.
Bo łatwiej np zrobić blok w którym ustawiamy wszystkie zombie niż dla kazdego get() wołamy synchronicznie.

nosql git przy dodawaniu,

 */











