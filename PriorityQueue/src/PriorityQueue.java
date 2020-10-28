import java.util.ArrayList;

public class PriorityQueue<T extends Comparable<T>> {

    private ArrayList<T> queue;

    public PriorityQueue() {
        queue = new ArrayList<>();
    }

    public void add (T forAdd) {
        queue.add(forAdd);
        if (queue.size() != 1)
            downUp(queue.size() - 1);
    }

    private void downUp (int index) {
        int parent = (index - 1)/2;
        int left = parent*2 + 1;
        int right = left + 1;
        if (right > queue.size() - 1 || queue.get(left).compareTo(queue.get(right)) > 0) {
            if (queue.get(left).compareTo(queue.get(parent)) > 0) {
                T t = queue.get(left);
                queue.set(left, queue.get(parent));
                queue.set(parent, t);
                downUp(parent);
            }
        } else if (queue.get(right).compareTo(queue.get(parent)) > 0) {
                T t = queue.get(right);
                queue.set(right, queue.get(parent));
                queue.set(parent, t);
                downUp(parent);
        }
    }

    public boolean isEmpty () {
        return (queue.size() == 0);
    }

    public int getNumberOfElements () {
        return queue.size();
    }

    public void deleteElement () {
        if (!isEmpty()) {
            queue.set(0, queue.get(queue.size() - 1));
            queue.remove(queue.size() - 1);
            upDown(0);
        }
    }

    private void upDown (int parent) {
        int left = parent*2 + 1;
        int right = left + 1;
        if (left <= queue.size() - 1) {
            if (right > queue.size() - 1 || queue.get(left).compareTo(queue.get(right)) > 0) {
                if (queue.get(left).compareTo(queue.get(parent)) > 0) {
                    T t = queue.get(parent);
                    queue.set(parent, queue.get(left));
                    queue.set(left, t);
                    upDown(left);
                }
            } else {
                if (queue.get(right).compareTo(queue.get(parent)) > 0) {
                    T t = queue.get(parent);
                    queue.set(parent, queue.get(right));
                    queue.set(right, t);
                    upDown(right);
                }
            }
        }
    }

    public T getMaxElement () {
        if (!isEmpty())
            return queue.get(0);
        else
            return null;
    }
}