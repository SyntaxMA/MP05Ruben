package ex4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void put() {

        ex4.HashTable hashTable = new ex4.HashTable();

        //4.1.1 Inserir un element que no col·lisiona dins una taula vuida.
        hashTable.put("0", "Mafias");
        assertEquals("\n bucket[0] = [0, Mafias]", hashTable.toString());
        assertEquals(1, hashTable.count());
        assertEquals(16, hashTable.size());

        //4.1.2 Inserir un element que no col·lisiona dins una taula no vuida.
        hashTable.put("5", 12);
        assertEquals("\n" +
                " bucket[0] = [0, Mafias]\n" +
                " bucket[5] = [5, 12]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //4.1.3 Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
        hashTable.put("11", 4.9f);
        assertEquals("\n" +
                " bucket[0] = [0, Mafias] -> [11, 4.9]\n" +
                " bucket[5] = [5, 12]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //4.1.4 Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
        hashTable.put("22", true);
        assertEquals("\n" +
                " bucket[0] = [0, Mafias] -> [11, 4.9] -> [22, true]\n" +
                " bucket[5] = [5, 12]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());


        //????? Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula vuida.


        //4.1.5 Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
        hashTable.put("5", "Hoteles");
        assertEquals("\n" +
                " bucket[0] = [0, Mafias] -> [11, 4.9] -> [22, true]\n" +
                " bucket[5] = [5, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //4.1.6 Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
        hashTable.put("11", 'R');
        assertEquals("\n" +
                " bucket[0] = [0, Mafias] -> [11, R] -> [22, true]\n" +
                " bucket[5] = [5, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //4.1.7 Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
        hashTable.put("22", "Discotecas");
        assertEquals("\n" +
                " bucket[0] = [0, Mafias] -> [11, R] -> [22, Discotecas]\n" +
                " bucket[5] = [5, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());
    }

    @Test
    void get() {
        ex4.HashTable hashTable = new ex4.HashTable();

        //4.2.1 Obtenir un element que no col·lisiona dins una taula vuida.
        assertNull(hashTable.get("0"));

        //4.2.2 Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.put("0", "Mafias");
        assertEquals("Mafias", hashTable.get("0"));

        //4.2.3 Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
        hashTable.put("11", 'R');
        assertEquals('R', hashTable.get("11"));

        //4.2.4 Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.put("22", 12);
        assertEquals(12, hashTable.get("22"));

        //4.2.5 Obtenir un elements que no existeix perquè la seva posició està buida.
        Assertions.assertNull(hashTable.get("4"));

        //4.2.6 Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
        hashTable.put("5", 5.5f);
        assertNull(hashTable.get("13"));

        //4.2.7 Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
        hashTable.put("13", true);
        hashTable.put("24", "Atracos");
        assertNull(hashTable.get("33"));
    }

    @Test
    void drop() {
        ex4.HashTable hashTable = new ex4.HashTable();
        hashTable.put("1", "Mafias");
        hashTable.put("12", 'R');
        hashTable.put("23", 12);
        hashTable.put("9", 4.9f);
        assertEquals(16, hashTable.size());

        //2.3.1 Esborrar no col·lisiona.
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
        hashTable.drop("26");
        assertNull(hashTable.get("26"));
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.21 Esborrar no existeix, posicio ocupada, 3 col·lisiona.
        hashTable.drop("33");
        assertNull(hashTable.get("33"));
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