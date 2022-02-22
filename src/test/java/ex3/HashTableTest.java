package ex3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void put() {

        ex3.HashTable hashTable = new ex3.HashTable();

        //2.1.1 Inserir un element que no col·lisiona dins una taula vuida.
        hashTable.put("1", "Mafias");
        assertEquals("\n bucket[1] = [1, Mafias]", hashTable.toString());
        assertEquals(1, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.2 Inserir un element que no col·lisiona dins una taula no vuida.
        hashTable.put("9", "Hoteles");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.3 Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 2a posició dins el mateix bucket.
        hashTable.put("12", "Casinos");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.4 Inserir un element que col·lisiona dins una taula no vuida, que es col·locarà en 3a posició dins el mateix bucket.
        hashTable.put("23", "Discotecas");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());


        //????? Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula vuida.


        //2.1.5 Inserir un elements que ja existeix (update) sobre un element que no col·lisiona dins una taula no vuida.
        hashTable.put("9", "Carreras");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Carreras]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.6 Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (2a posició) dins una taula no vuida.
        hashTable.put("12", "Gimnasios");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Gimnasios] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Carreras]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.7 Inserir un elements que ja existeix (update) sobre un element que si col·lisiona (3a posició) dins una taula no vuida.
        hashTable.put("23", "Playa");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Gimnasios] -> [23, Playa]\n" +
                " bucket[9] = [9, Carreras]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());
    }

    @Test
    void get() {
        ex2.HashTable hashTable = new ex2.HashTable();

        //2.2.1 Obtenir un element que no col·lisiona dins una taula vuida.
        assertNull(hashTable.get("0"));
        assertEquals(0, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.2.2 Obtenir un element que col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.put("0", "Mafias");
        assertEquals("Mafias", hashTable.get("0"));
        assertEquals(1, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.2.3 Obtenir un element que col·lisiona dins una taula (2a posició dins el mateix bucket).
        hashTable.put("11", "Casinos");
        assertEquals("Casinos", hashTable.get("11"));
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.2.4 Obtenir un element que col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.put("22", "Discotecas");
        assertEquals("Discotecas", hashTable.get("22"));
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.2.5 Obtenir un elements que no existeix perquè la seva posició està buida.
        Assertions.assertNull(hashTable.get("9"));
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.2.6 Obtenir un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
        hashTable.put("2", "Bandas");
        assertNull(hashTable.get("13"));
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.2.7 Obtenir un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
        hashTable.put("13", "Peleas");
        hashTable.put("24", "Atracos");
        assertNull(hashTable.get("35"));
        assertEquals(6, hashTable.count());
        assertEquals(16, hashTable.size());
    }

    @Test
    void drop() {
        ex2.HashTable hashTable = new ex2.HashTable();

        hashTable.put("0", "Mafias");
        hashTable.put("11", "Casinos");
        hashTable.put("22", "Discotecas");
        hashTable.put("9", "Hoteles");
        assertNotEquals(0, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.1 Esborrar un element que no col·lisiona dins una taula.
        hashTable.drop("9");
        assertNull(hashTable.get("9"));
        assertEquals("\n" +
                " bucket[0] = [0, Mafias] -> [11, Casinos] -> [22, Discotecas]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.2 Esborrar un element que si col·lisiona dins una taula (1a posició dins el mateix bucket).
        hashTable.drop("0");
        assertNull(hashTable.get("0"));
        assertEquals("\n" +
                " bucket[0] = [11, Casinos] -> [22, Discotecas]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.3 Esborrar un element que si col·lisiona dins una taula (2a posició dins el mateix bucket).
        hashTable.put("0", "Mafias");
        hashTable.drop("11");
        assertNull(hashTable.get("11"));
        assertEquals("\n" +
                " bucket[0] = [22, Discotecas] -> [0, Mafias]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.4 Esborrar un element que si col·lisiona dins una taula (3a posició dins el mateix bucket).
        hashTable.put("11", "Casinos");
        hashTable.drop("11");
        assertNull(hashTable.get("11"));
        assertEquals("\n" +
                " bucket[0] = [22, Discotecas] -> [0, Mafias]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.5 Eliminar un elements que no existeix perquè la seva posició està buida.
        hashTable.put("11", "Casinos");
        hashTable.drop("3");
        assertNull(hashTable.get("3"));
        assertEquals("\n" +
                " bucket[0] = [22, Discotecas] -> [0, Mafias] -> [11, Casinos]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.6 Eliminar un elements que no existeix, tot i que la seva posició està ocupada per un altre que no col·lisiona.
        hashTable.drop("26");
        assertNull(hashTable.get("26"));
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.3.7 Eliminar un elements que no existeix, tot i que la seva posició està ocupada per 3 elements col·lisionats.
        hashTable.drop("33");
        assertNull(hashTable.get("33"));
        assertEquals(3, hashTable.count());
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