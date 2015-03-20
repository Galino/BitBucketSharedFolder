/*
 * IJA 2014/2015
 * Ukol 1
 */
package ija.homework1;

import java.util.HashSet;
import java.util.Set;

import ija.homework1.treasure.*;

/**
 *
 * @author koci
 */
public class Homework1 {

    /**
     * Spusti testy.
     */
    public static void main(String[] args) {
        testTreasure();
        testCardPack();
        testShuffle();
        System.out.println("OK");
    }

    /**
     * Test vytvoreni sady pokladu.
     */
    public static void testTreasure() {
        Treasure.createSet();

        Treasure t1 = Treasure.getTreasure(0);
        Treasure t2 = Treasure.getTreasure(0);
        Treasure t3 = Treasure.getTreasure(1);
        Treasure t4 = Treasure.getTreasure(23);
        Treasure t5 = Treasure.getTreasure(24);

        assert t1.equals(t2)   : "Objekty t1 a t2 jsou shodne";
        assert t1 == t2        : "Objekty t1 a t2 jsou identicke";
        assert ! t1.equals(t3) : "Objekty t1 a t3 jsou ruzne";
        assert t4 != null      : "Objekt 23 existuje";
        assert t5 == null      : "Objekt 24 neexistuje";
    }

    /**
     * Test vytvoreni balicku karet a zakladni prace s balickem.
     */
    public static void testCardPack() {
        int size = 12;

        CardPack pack = new CardPack(size, size);
        assert size == pack.size() : "Pocet karet v balicku";

        for (int i = 0; i < size; i++) {
            TreasureCard c1 = pack.popCard();
            TreasureCard c2 = new TreasureCard(Treasure.getTreasure(i));
            assert c1.equals(c2) : "Karty jsou usporadane";
        }
        assert pack.size() == 0 : "Balicek je prazdny";
    }

    /**
     * Test vytvoreni balicku karet a zamichani karet v balicku.
     */
    public static void testShuffle() {
        int size = 12;
        int neq = 0;
        // pomocna mnozina pro testovani, zda po zamichani zustavaji v balicku stejne karty
        Set<TreasureCard> tmp = new HashSet<>();

        CardPack pack = new CardPack(size, size);
        assert size == pack.size() : "Pocet karet v balicku";

        pack.shuffle();
        assert size == pack.size() : "Pocet karet v balicku po zamichani";

        for (int i = 0; i < size; i++) {
            TreasureCard c = pack.popCard();
            tmp.add(c);
            // test, zda doslo k prohozeni karet
            if (! c.equals(new TreasureCard(Treasure.getTreasure(i)))) neq++;
        }

        assert pack.size() == 0   : "Balicek je prazdny";
        assert size == tmp.size() : "Pocet ruznych karet v balicku po zamichani je stejny jako pred zamichanim";
        assert neq > 0            : "Karty jsou zamichany";
    }
}
