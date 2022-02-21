package ex4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void put() {
        ex4.HashTable hashTable = new ex4.HashTable();
        System.out.println("Colisiones " + hashTable.getCollisionsForKey("0", 4));
        System.out.println("Colisiones " + hashTable.getCollisionsForKey("1", 4));
        System.out.println("Colisiones " + hashTable.getCollisionsForKey("2", 4));
        System.out.println("Colisiones " + hashTable.getCollisionsForKey("9", 4));

        //2.1.1 Inserir no col·lisiona, una taula vuida
        hashTable.put("1", "Mafias");
        assertEquals("\n bucket[1] = [1, Mafias]", hashTable.toString());
        assertEquals(1, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.2 Inserir no col·lisiona, una taula no vuida
        hashTable.put("9", 12);
        assertEquals("\n" +
                " bucket[1] = [1, Mafias]\n" +
                " bucket[9] = [9, 12]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.3 Inserir col·lisiona, una taula no vuida, col·locarà en 2a posició
        hashTable.put("12", true);
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, true]\n" +
                " bucket[9] = [9, 12]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.4 Inserir col·lisiona, una taula no vuida, col·locarà en 3a posició
        hashTable.put("23", 4.9f);
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, true] -> [23, 4.9]\n" +
                " bucket[9] = [9, 12]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.5 Inserir ja existeix, no col·lisiona, una taula no vuida.
        hashTable.put("9", "Hoteles");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, true] -> [23, 4.9]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.6 Inserir ja existeix, si col·lisiona, 2a posició, una taula no vuida.
        hashTable.put("12", 'R');
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, R] -> [23, 4.9]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.7 Inserir ja existeix, si col·lisiona, 3a posició, una taula no vuida.
        hashTable.put("23", "Discotecas");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, R] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());
    }

    @Test
    void get() {
        ex4.HashTable hashTable = new ex4.HashTable();
        //2.1.8 Obtenir no col·lisiona, una taula vuida.
        assertNull(hashTable.get("0"));

        //2.1.9 Obtenir col·lisiona, una taula, 1a posició mateix bucket
        hashTable.put("1", "Mafias");
        assertEquals("Mafias", hashTable.get("1"));

        //2.1.10 Obtenir col·lisiona, una taula, 2a posició mateix bucket
        hashTable.put("12", 'R');
        assertEquals('R', hashTable.get("12"));

        //2.1.11 Obtenir col·lisiona, una taula, 3a posició mateix bucket
        hashTable.put("23", 12);
        assertEquals(12, hashTable.get("23"));

        //2.1.12 Obtenir no existe, posicion buida
        Assertions.assertNull(hashTable.get("9"));

        //2.1.13 Obtenir no existe, posicio ocupada, altre no col·lisiona.
        hashTable.put("2", 5.5f);
        assertNull(hashTable.get("13"));

        //2.1.14 Obtenir no existe, posicio ocupada, 3 altres col·lisiona.
        hashTable.put("13", true);
        hashTable.put("24", "Atracos");
        assertNull(hashTable.get("35"));
    }

    @Test
    void drop() {
        ex4.HashTable hashTable = new ex4.HashTable();
        hashTable.put("1", "Mafias");
        hashTable.put("12", 'R');
        hashTable.put("23", 12);
        hashTable.put("9", 4.9f);
        assertEquals(16, hashTable.size());

        //2.1.15 Esborrar no col·lisiona.
        hashTable.drop("9");
        assertNull(hashTable.get("9"));
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, R] -> [23, 12]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.16 Esborrar si col·lisiona, 1a posicio mateix bucket.
        hashTable.put("9", "Hoteles");
        hashTable.drop("1");
        assertNull(hashTable.get("1"));
        assertEquals("\n" +
                " bucket[1] = [12, R] -> [23, 12]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.17 Esborrar si col·lisiona, 2a posició mateix bucket.
        hashTable.put("1", "Mafias");
        hashTable.drop("12");
        assertNull(hashTable.get("12"));
        assertEquals("\n" +
                " bucket[1] = [23, 12] -> [1, Mafias]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.18 Esborrar si col·lisiona, 3a posició mateix bucket.
        hashTable.put("12", true);
        hashTable.drop("12");
        assertNull(hashTable.get("12"));
        assertEquals("\n" +
                " bucket[1] = [23, 12] -> [1, Mafias]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.19 Esborrar no existeix, posició buida.
        hashTable.put("12", "Casinos");
        hashTable.drop("3");
        assertNull(hashTable.get("3"));
        assertEquals("\n" +
                " bucket[1] = [23, 12] -> [1, Mafias] -> [12, Casinos]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.20 Esborrar no existeix, posicio ocupada, no col·lisiona.
        hashTable.drop("70");
        assertNull(hashTable.get("70"));
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.21 Esborrar no existeix, posicio ocupada, 3 col·lisiona.
        hashTable.drop("34");
        assertNull(hashTable.get("34"));
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());
    }

    @Test
    void count() {
    }

    @Test
    void size() {
    }

    @Test
    void testToString() {
    }
}