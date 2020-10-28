import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

public class TestMap {
    public static void main(String[] args) {
        Comparator<Integer> c1 = Comparator.naturalOrder();
        Map<Integer, String> map1 = new Map<>(c1);

        Random r = new Random();
        for (int i = 0; i < 100; i++)
            map1.put(r.nextInt(), r.nextInt() + " ");

        Comparator<String> c2 = (str1, str2) -> str1.length() - str2.length();
        Map<String, Integer> map2 = new Map<>(c2);
        map2.put("a", 1);
        map2.put("ab", 2);
        map2.put("abc", 3);
        map2.put("abcd", 4);
        map2.put("abcde", 5);
        map2.put("abcdef", 6);
        map2.put("abcdefg", 7);
        map2.put("abcdefgh", 8);
        map2.put("abcdefghi", 9);
        map2.put("abcdefghij", 10);
        map2.put("ab", 2);
        map2.put("abcdefghijk", 11);



        Map<String, Integer> map2_5 = new Map<>(map2);

        map2.remove("11111111111");
        map2.remove("1010101010");
        map2.remove("666666");
        map2.remove("4444");

        try
        {
            System.out.print("Значение по ключу 'd' в map2: ");
            System.out.println(map2.get("d"));
            System.out.print("Значение по ключу 'dddddddddddddddddddddddddddddddddddddd' в map2: ");
            System.out.println(map2.get("dddddddddddddddddddddddddddddddddddddd"));
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Нет такого элемента");
        }

        System.out.println("Содержится ли ключ '4444' в map2_5? " + map2_5.searchByKey("4444"));

        System.out.println("Содержится ли ключ 'd' в map2? " + map2.searchByKey("d")
                + " Содержится ли ключ 'ddddddddddddddddddddddddd' в map2? " + map2.searchByKey("ddddddddddddddddddddddddd"));

        try
        {
            System.out.print("По ключу 'gf' в map2 содержится значение: ");
            System.out.println(map2.get("gf"));
            System.out.print("По ключу '23' в map1 содержится значение: ");
            System.out.println(map1.get(23));

        }
        catch (NoSuchElementException e)
        {
            System.out.println("Нет такого элемента");
        }
        System.out.println("map1 пустой? " + map1.isEmpty() + " map2 пустой? " + map2.isEmpty());
        map1.removeAll();
        map2.removeAll();
        System.out.println("map1 пустой? " + map1.isEmpty() + " map2 пустой? " + map2.isEmpty());

        Comparator<Integer> c3 = Comparator.naturalOrder();
        Map<Integer, Integer> map3 = new Map<>(c3);
        map3.put(7, r.nextInt());
        map3.put(2, r.nextInt());
        map3.put(9, r.nextInt());
        map3.put(1, r.nextInt());
        map3.put(5, r.nextInt());
        map3.put(8, r.nextInt());
        map3.put(10, r.nextInt());
        map3.put(3, r.nextInt());
        map3.put(6, r.nextInt());

        map3.remove(10);
        map3.remove(8);
        map3.remove(9);

        Map<Integer, Integer> map4 = new Map<>(c3);
        map4.put(7, r.nextInt());
        map4.put(2, r.nextInt());
        map4.put(10, r.nextInt());
        map4.put(1, r.nextInt());
        map4.put(5, r.nextInt());

        map4.remove(10);

        Map<Integer, Integer> map5 = new Map<>(c3);
        map5.put(17, r.nextInt());
        map5.put(9, r.nextInt());
        map5.put(25, r.nextInt());
        map5.put(1, r.nextInt());
        map5.put(12, r.nextInt());
        map5.put(20, r.nextInt());
        map5.put(31, r.nextInt());
        map5.put(19, r.nextInt());
        map5.put(23, r.nextInt());
        map5.put(27, r.nextInt());
        map5.put(45, r.nextInt());
        map5.put(22, r.nextInt());
        map5.put(24, r.nextInt());

        map5.remove(45);
        map5.remove(31);
        map5.remove(27);

        Map<Integer, Integer> map6 = new Map<>(c3);
        map6.put(1, 1);
        map6.remove(1);

        for (int i = 0; i < 13133; i++)
        {
            map6.put(Math.abs(r.nextInt()) % 50000, r.nextInt());
            if (!map6.check())
                System.out.println("Не выполняется на " + i + "-ом шаге");
        }

        for (int i = 0; i < 6666; i++)
        {
            map6.remove(r.nextInt());
            if (!map6.check())
                System.out.println("Не выполняется.");
        }

        System.out.println("Более умная проверка выполняется? " + map6.check());
    }
}