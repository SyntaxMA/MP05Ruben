package ex2;

import java.util.ArrayList;

public class HashTable {
    private int SIZE = 16;
    private int ITEMS = 0;
    private HashEntry[] entries = new HashEntry[SIZE];

    public int count() {
        return this.ITEMS;
    }

    public int size() {
        return this.SIZE;
    }

    public void put(String key, String value) {

        // Este es el código original

        /*
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) {
            entries[hash] = hashEntry;
        }
        else {
            HashEntry temp = entries[hash];
            while(temp.next != null)
                temp = temp.next;
            temp.next = hashEntry;
            hashEntry.prev = temp;
        } */

        // Este es el código nuevo con las modificaciones necesarias para que funcione.

        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        boolean actualizar = false;

        if (entries[hash] == null) {

            entries[hash] = hashEntry;

            // El primer paso que hacemos sera sumar un item para que despues podamos saber los items totales que hay.
            ITEMS++;

        } else {

            // Esta es la parte donde veremos si haremos un insert o un update, dependiendo del value.

            HashEntry temp = entries[hash];

            if (temp.key.equals(key)) {

                // Aqui creamos un primer if que comprobará si se quiere actualizar el primer elemento que este en el bucket.
                entries[hash].value = hashEntry.value;

            } else {

                while (temp.next != null){

                    temp = temp.next;

                // Faltara crear este segundo if que buscara hasta que sea el último de los elementos que colapsan en el mismo bucket y si lo encuentra la variable actualizar sera true
                    if (temp.key.equals(key)) {
                        temp.value = hashEntry.value;
                        actualizar = true;
                    }
                }

            // Si Actualizar no es true significa que el elemento no estaba en la tabla aun por tanto hay que añadir en el bucket un nuevo item.
                if (!actualizar) {
                    ITEMS++;
                    temp.next = hashEntry;
                    hashEntry.prev = temp;
                }
            }
        }
    }
    public String get(String key) {

        // Este es el código original
        /*
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];
            while( !temp.key.equals(key))
                temp = temp.next;
            return temp.value;
        }
        return null;
        */

        // Este es el código nuevo con las modificaciones necesarias para que funcione.

        int hash = getHash(key);
        if (entries[hash] != null) {

            HashEntry temp = entries[hash];

            while (!temp.key.equals(key)) {

                // Con esto evitas el tema del NullPointerException haciendo que cuando el puntero no encuentre nada con el .next devuelva un valor null y no pete con la excepcion
                if (temp.next == null) {
                    return null;
                }

                temp = temp.next;

            }
            return temp.value;
        }
        return null;
    }

    public void drop(String key) {

        // Este es el código original

        /*
        int hash = getHash(key);

        if(entries[hash] != null) {
            HashEntry temp = entries[hash];
            while( !temp.key.equals(key))
                temp = temp.next;
            if(temp.prev == null) entries[hash] = null;             //esborrar element únic (no col·lissió)
            else{
                if(temp.next != null) temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
                temp.prev.next = temp.next;                         //esborrem temp, per tant actualitzem el següent de l'anterior
            }
        }
        */

        // Este es el código nuevo con las modificaciones necesarias para que funcione.

        int hash = getHash(key);

        if (entries[hash] != null) {
            HashEntry temp = entries[hash];

            //Con el if que implementamos le decimos que recorra hasta pillar el key que quieres borrar.
            while (!temp.key.equals(key)) {
                if (temp.next != null) {
                    temp = temp.next;
                } else {
                    temp = null;
                    break;
                }
            }
            if (temp != null) {

                //Ambos if son para comprobar si temp no tiene colisiones.
                if (temp.prev == null) {
                    if (temp.next == null) {
                        entries[hash] = null;
                    }

                    //Con el else borraremos al primero de la lista.
                    else {
                        temp.next.prev = null;
                        entries[hash] = temp.next;
                    }
                } else {

                    //Este if es para borrar a un elemento que este situado en el medio.
                    if (temp.next != null) {
                        temp.next.prev = temp.prev;
                        temp.prev.next = temp.next;
                    }

                    //Este else es para borrar el ultimo elemento dentro del bucket.
                    else {
                        temp.prev.next = null;
                    }
                }
                // Hay que añadir el items-- para que el count se actualize y no cuente algo que ya no esta en la tabla.
                ITEMS--;
            }
        }
    }


    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % SIZE;
    }

    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if (entry == null) {
                bucket++;
                continue;
            }

            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while (temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    /**
     * Permet calcular quants elements col·lisionen (produeixen la mateixa posició dins la taula de hash) per a la clau donada.
     *
     * @param key La clau que es farà servir per calcular col·lisions.
     * @return Una clau que, de fer-se servir, provoca col·lisió amb la que s'ha donat.
     */
    public String getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1).get(0);
    }

    /**
     * Permet calcular quants elements col·lisionen (produeixen la mateixa posició dins la taula de hash) per a la clau donada.
     *
     * @param key      La clau que es farà servir per calcular col·lisions.
     * @param quantity La quantitat de col·lisions a calcular.
     * @return Un llistat de claus que, de fer-se servir, provoquen col·lisió.
     */
    public ArrayList<String> getCollisionsForKey(String key, int quantity) {
        /*
          Main idea:
          alphabet = {0, 1, 2}
          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"
          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() - 1;

        while (foundKeys.size() < quantity) {
            //building current key
            String currentKey = "";
            for (int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if (!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current) + 1);

            //overflow over the alphabet on current!
            if (newKey.get(current) == alphabet.length) {
                int previous = current;
                do {
                    //increasing the previous to current alphabet key
                    previous--;
                    if (previous >= 0) newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for (int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if (previous < 0) newKey.add(0);

                current = newKey.size() - 1;
            }
        }

        return foundKeys;
    }

    public static void main(String[] args) {

        // Creamos una hashTable
        HashTable hashTable = new HashTable();

        // Le empezamos a poner diferentes valores hasta el numero que queramos, en mi caso hasta 26.
        for (int i = 0; i < 26; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Esto va a imprimir la estructura que va tener la Hash Table.
        log("****   HashTable  ***");
        log(hashTable.toString());
        log("\nValue for key(12) : " + hashTable.get("12"));
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}