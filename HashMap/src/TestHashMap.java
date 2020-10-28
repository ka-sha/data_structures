import java.util.NoSuchElementException;
import java.util.Random;

public class TestHashMap {
    public static void main(String[] args)
    {
        HashMap<Integer, Integer> map1 = new HashMap<>();
        for (int i = 0; i < 22; i++)
            map1.put(i, i);

        System.out.println("Элемент по ключу 14: " + map1.get(14));
        map1.remove(14);
        System.out.println("Количество элементов послеудаления 14: " + map1.getElementNum());

        try
        {
            System.out.println("Элемент по ключу 14: " + map1.get(14));
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Нет элемента по ключу 14");
        }

        System.out.println("Количество элементов: " + map1.getElementNum() + ", коэффициент загруженности: "
                + map1.getC() + ", текущий уровень загруженности: " + map1.getCurrentC());

        map1.print();
        map1.setC(0.75);
        map1.print();

        map1.removeAll();

        System.out.println();

        map1.put(1, 1);
        map1.put(1, 2);

        System.out.println("Количество элементов: " + map1.getElementNum() + ", коэффициент загруженности: "
                + map1.getC() + ", текущий уровень загруженности: " + map1.getCurrentC());

        HashMap<Integer, Integer> map2 = new HashMap<>();
        Random r = new Random();

        for(int i = 0; i < 1000000; i++)
            map2.put(r.nextInt(), i);

        map2.print();

        System.out.println("Количество элементов: " + map2.getElementNum() + ", коэффициент загруженности: "
                + map2.getC() + ", текущий уровень загруженности: " + map2.getCurrentC());

        for (int i = Integer.MIN_VALUE + 1; i < Integer.MAX_VALUE; i++)
            map2.remove(i);
        map2.removeAll();
        System.out.println("Количество элементов: " + map2.getElementNum() + ", коэффициент загруженности: "
                + map2.getC() + ", текущий уровень загруженности: " + map2.getCurrentC());

        map2.put(1,1);
    }
}