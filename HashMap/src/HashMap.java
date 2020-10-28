import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashMap<K, V> {
    private ArrayList<LinkedList<Pair>> hashTable;
    private int elementNum;
    private int listNum;
    private double C;

    public HashMap()
    {
        hashTable = new ArrayList<>(10);
        for (int i = 0; i < 10; i++)
            hashTable.add(new LinkedList<>());
        elementNum = 0;
        listNum = 10;
        C = 2.0;
    }

    public void removeAll()
    {
        for (int i = 0; i < listNum; i++)
            hashTable.set(i, new LinkedList<>());
        elementNum = 0;
    }

    public void put(K key, V value)
    {
        Pair put = new Pair(key, value);
        int hashCode = Math.abs(key.hashCode()) % listNum;

        if (hashTable.get(hashCode).isEmpty())
            hashTable.get(hashCode).add(put);
        else
        {
            for (Pair pair : hashTable.get(hashCode))
                if (pair.key.equals(key))
                {
                    pair.value = value;
                    return;
                }
            hashTable.get(hashCode).add(put);
        }
        elementNum++;

        if ((double)elementNum/(double)listNum >= C)
            reHash(2*listNum + 1);
    }

    public V get(K key) throws NoSuchElementException
    {
        int hashCode = Math.abs(key.hashCode()) % listNum;

        for (Pair pair : hashTable.get(hashCode))
            if (pair.key.equals(key))
                return pair.value;

        throw new NoSuchElementException();
    }

    public void remove(K key)
    {
        int hashCode = Math.abs(key.hashCode()) % listNum;

        for (Pair pair : hashTable.get(hashCode))
            if (pair.key.equals(key))
            {
                hashTable.get(hashCode).remove(pair);
                elementNum--;
                return;
            }
    }

    public void print()
    {
        for (int i = 0; i < hashTable.size(); i++)
        {
            System.out.print(i + ": ");
            for (Pair pair : hashTable.get(i))
                System.out.print("key = " + pair.key + ", value = " + pair.value + ";");
            System.out.println();
        }
    }

    public int getElementNum()
    {
        return elementNum;
    }

    public double getC()
    {
        return C;
    }

    public void setC(double c)
    {
        C = c;
        if ((double)elementNum/(double)listNum >= C)
            reHash(listNum);
    }

    public double getCurrentC()
    {
        return (double)elementNum/(double)listNum;
    }

    private void reHash(int newListNum)
    {
        if ((double)elementNum/(0.75*newListNum) >= C)
        {
            ArrayList<LinkedList<Pair>> expandedHashTable = new ArrayList<>(newListNum);
            for (int i = 0; i < newListNum; i++)
                expandedHashTable.add(new LinkedList<>());

            for (LinkedList<Pair> list : hashTable)
                for (Pair pair : list)
                    expandedHashTable.get(Math.abs(pair.key.hashCode()) % newListNum).add(pair);

            this.hashTable = expandedHashTable;
            this.listNum = newListNum;
        }
        else
            reHash((int)(0.75*newListNum));
    }

    private class Pair {
        K key;
        V value;

        Pair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }
}