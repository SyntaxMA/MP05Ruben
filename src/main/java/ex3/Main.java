package ex3;

public class Main {
    public static void main(String[] args) {

        // Creamos una hashTable
        HashTable hashTable = new HashTable();

        // Le empezamos a poner diferentes valores hasta el numero que queramos, en mi caso hasta 26.
        for (int i = 0; i < 50; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Esto va a imprimir la estructura que va tener la Hash Table.
        HashTable.log("**** HashTable ****");
        HashTable.log(hashTable.toString());
        HashTable.log("\nValue for key(30) : " + hashTable.get("30"));
    }
}