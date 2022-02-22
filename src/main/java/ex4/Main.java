package ex4;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Put some key values.
        for (int i = 0; i < 50; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Print the HashTable structure
        HashTable.log("**** HashTable ****");
        HashTable.log(hashTable.toString());
        HashTable.log("\nValue for key(30) : " + hashTable.get("30"));
    }
}