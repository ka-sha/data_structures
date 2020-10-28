public class QueueTest {
    public static void main (String[] args) {
        PriorityQueue<Integer> test1 = new PriorityQueue<>();
        test1.add(8);
        test1.add(5);
        test1.add(9);
        test1.add(12);
        test1.add(2);
        test1.add(1);
        test1.add(4);
        test1.add(3);
        test1.deleteElement();
        test1.deleteElement();
        test1.add(7);
        System.out.println(test1.isEmpty());
        System.out.println(test1.getNumberOfElements());
        System.out.println(test1.getMaxElement());
        while (test1.getNumberOfElements() != 0) {
            System.out.print(test1.getMaxElement() + " ");
            test1.deleteElement();
        }
        System.out.print("\n");
        test1.deleteElement();
        System.out.println(test1.isEmpty());
        PriorityQueue<String> test2 = new PriorityQueue<>();
        test2.deleteElement();
        test2.add("8");
        test2.add("10000000008");
        test2.add("-4");
        test2.add("asd");
        test2.add("test");
        System.out.println(test2.isEmpty());
        System.out.println(test2.getNumberOfElements());
        while (test2.getNumberOfElements() != 0) {
            System.out.print(test2.getMaxElement() + " ");
            test2.deleteElement();
        }
        System.out.print("\n");

        PriorityQueue<Integer> test3 = new PriorityQueue<>();
        for (int i = 0; i < 100; i = i + 7){
                test3.add(i);
        }
        while (!test3.isEmpty()){
            System.out.print(test3.getMaxElement() + " ");
            test3.deleteElement();
        }
    }
}