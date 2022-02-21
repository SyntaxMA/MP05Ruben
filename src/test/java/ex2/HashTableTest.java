package ex2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void put() {
        ex3.HashTable hashTable = new ex3.HashTable();
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
        hashTable.put("9", "Hoteles");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(2, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.3 Inserir col·lisiona, una taula no vuida, col·locarà en 2a posició
        hashTable.put("12", "Casinos");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.4 Inserir col·lisiona, una taula no vuida, col·locarà en 3a posició
        hashTable.put("23", "Discotecas");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.5 Inserir ja existeix, no col·lisiona, una taula no vuida.
        hashTable.put("9", "Carreras");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Carreras]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.6 Inserir ja existeix, si col·lisiona, 2a posició, una taula no vuida.
        hashTable.put("12", "Gimnasios");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Gimnasios] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Carreras]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.7 Inserir ja existeix, si col·lisiona, 3a posició, una taula no vuida.
        hashTable.put("23", "Playa");
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Gimnasios] -> [23, Playa]\n" +
                " bucket[9] = [9, Carreras]", hashTable.toString());
        assertEquals(4, hashTable.count());
        assertEquals(16, hashTable.size());
    }

    @Test
    void get() {
        ex3.HashTable hashTable = new ex3.HashTable();
        //2.1.8 Obtenir no col·lisiona, una taula vuida.
        assertNull(hashTable.get("0"));

        //2.1.9 Obtenir col·lisiona, una taula, 1a posició mateix bucket
        hashTable.put("1", "Mafias");
        assertEquals("Mafias", hashTable.get("1"));

        //2.1.10 Obtenir col·lisiona, una taula, 2a posició mateix bucket
        hashTable.put("12", "Casinos");
        assertEquals("Casinos", hashTable.get("12"));

        //2.1.11 Obtenir col·lisiona, una taula, 3a posició mateix bucket
        hashTable.put("23", "Discotecas");
        assertEquals("Discotecas", hashTable.get("23"));

        //2.1.12 Obtenir no existe, posicion buida
        Assertions.assertNull(hashTable.get("9"));

        //2.1.13 Obtenir no existe, posicio ocupada, altre no col·lisiona.
        hashTable.put("2", "Bandas");
        assertNull(hashTable.get("13"));

        //2.1.14 Obtenir no existe, posicio ocupada, 3 altres col·lisiona.
        hashTable.put("13", "Peleas");
        hashTable.put("24", "Atracos");
        assertNull(hashTable.get("35"));
    }

    @Test
    void drop() {
        ex3.HashTable hashTable = new ex3.HashTable();
        hashTable.put("1", "Mafias");
        hashTable.put("12", "Casinos");
        hashTable.put("23", "Discotecas");
        hashTable.put("9", "Hoteles");
        assertEquals(16, hashTable.size());

        //2.1.15 Esborrar no col·lisiona.
        hashTable.drop("9");
        assertNull(hashTable.get("9"));
        assertEquals("\n" +
                " bucket[1] = [1, Mafias] -> [12, Casinos] -> [23, Discotecas]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.16 Esborrar si col·lisiona, 1a posicio mateix bucket.
        hashTable.put("9", "Hoteles");
        hashTable.drop("1");
        assertNull(hashTable.get("1"));
        assertEquals("\n" +
                " bucket[1] = [12, Casinos] -> [23, Discotecas]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.17 Esborrar si col·lisiona, 2a posició mateix bucket.
        hashTable.put("1", "Mafias");
        hashTable.drop("12");
        assertNull(hashTable.get("12"));
        assertEquals("\n" +
                " bucket[1] = [23, Discotecas] -> [1, Mafias]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.18 Esborrar si col·lisiona, 3a posició mateix bucket.
        hashTable.put("12", "Casinos");
        hashTable.drop("12");
        assertNull(hashTable.get("12"));
        assertEquals("\n" +
                " bucket[1] = [23, Discotecas] -> [1, Mafias]\n" +
                " bucket[9] = [9, Hoteles]", hashTable.toString());
        assertEquals(3, hashTable.count());
        assertEquals(16, hashTable.size());

        //2.1.19 Esborrar no existeix, posició buida.
        hashTable.put("12", "Casinos");
        hashTable.drop("3");
        assertNull(hashTable.get("3"));
        assertEquals("\n" +
                " bucket[1] = [23, Discotecas] -> [1, Mafias] -> [12, Casinos]\n" +
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