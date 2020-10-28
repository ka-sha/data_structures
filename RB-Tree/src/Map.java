import java.util.Comparator;
import java.util.NoSuchElementException;

public class Map<K, V> {
    private Node root;
    private Comparator<K> comparator;
    private final Node NULL = new Node(null, null, null, null, null, Color.BLACK);
    private int globalBlackHeight;

    public Map(Comparator<K> comparator)
    {
        this.comparator = comparator;
        root = null;
        globalBlackHeight = 0;
    }

    public Map(Map<K, V> other)
    {
        this.comparator = other.comparator;
        this.root = new Node(other.root);
        copy(root, other.NULL);
        this.globalBlackHeight = other.globalBlackHeight;
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public void removeAll()
    {
        root = null;
    }

    public void put(K key, V value)
    {
        if (root == null)
            root = new Node(key, value, null, NULL, NULL, Color.BLACK);
        else
        {
            Node node = new Node(key, value, null, NULL, NULL, Color.RED);
            if (putRec(root, node))
                putBalancing(node);
        }
    }

    public V get(K key) throws NoSuchElementException
    {
        if (root == null)
            throw new NoSuchElementException();
        return search(root, key).value;
    }

    public boolean searchByKey(K key)
    {
        if (root == null)
            return false;
        try
        {
            search(root, key);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public void remove(K key)
    {
        if (!isEmpty())
        {
            try
            {
                Node node = search(root, key);
                if (node == root && node.left == NULL && node.right == NULL)
                    root = null;
                else
                {
                    if (node.left == NULL && node.right == NULL)
                        node = remove1(node);
                    else if (node.left == NULL || node.right == NULL)
                        node = remove2(node);
                    else
                        node = remove3(node);
                    if (node != NULL)
                    {
                        removeBalancing(node);
                        if (node.parent.left == node)
                            node.parent.left = NULL;
                        else
                            node.parent.right = NULL;
                    }
                }
            }
            catch (NoSuchElementException e) {}
        }
    }

    public boolean check()
    {
        globalBlackHeight = 0;
        Node node = root;
        while (node != NULL)
        {
            if (node.color == Color.BLACK)
                globalBlackHeight++;
            else if (node.left.color == Color.RED || node.right.color == Color.RED)
                return false;
            node = node.left;
        }

        return checkRec(root, 0);
    }

    private void copy(Node node, Node otherNULL)
    {
        if (node.left != otherNULL)
        {
            node.left = new Node(node.left);
            copy(node.left, otherNULL);
        }
        else if (node.right != otherNULL)
        {
            node.right = new Node(node.right);
            copy(node.right, otherNULL);
        }
        else
            node.left = node.right = NULL;
    }

    private boolean putRec(Node nodeOfTree, Node nodeAdd)
    {
        if (comparator.compare(nodeAdd.key, nodeOfTree.key) < 0)
        {
            if (nodeOfTree.left == NULL)
            {
                nodeOfTree.left = nodeAdd;
                nodeAdd.parent = nodeOfTree;
                return true;
            }
            else
                return putRec(nodeOfTree.left, nodeAdd);
        }
        else if (comparator.compare(nodeAdd.key, nodeOfTree.key) > 0)
        {
            if (nodeOfTree.right == NULL)
            {
                nodeOfTree.right = nodeAdd;
                nodeAdd.parent = nodeOfTree;
                return true;
            }
            else
                return putRec(nodeOfTree.right, nodeAdd);
        }
        else
        {
            nodeOfTree.value = nodeAdd.value;
            return false;
        }
    }

    private void putBalancing(Node node)
    {
        if (node == root)
            node.color = Color.BLACK;
        else if (node.parent.color == Color.RED)
        {
            if (isUncleRed(node))
            {
                node.parent.parent.color = Color.RED;
                node.parent.color = Color.BLACK;
                if (node.parent.parent.left == node.parent)
                    node.parent.parent.right.color = Color.BLACK;
                else
                    node.parent.parent.left.color = Color.BLACK;
                putBalancing(node.parent.parent);
            }
            else if (isInternalNode(node))
            {
                    if (node.parent.left == node)
                        turnRight(node);
                    else
                        turnLeft(node);
                    if (node.parent.left == node)
                    {
                        turnRight(node);
                        node.right.color = Color.RED;
                        node.color = Color.BLACK;
                    }
                    else
                    {
                        turnLeft(node);
                        node.left.color = Color.RED;
                        node.color = Color.BLACK;
                    }
            }
            else if (node.parent.parent.right.right != node)
            {
                turnRight(node.parent);
                node.parent.right.color = Color.RED;
                node.parent.color = Color.BLACK;
            }
            else
            {
                turnLeft(node.parent);
                node.parent.left.color = Color.RED;
                node.parent.color = Color.BLACK;
            }
        }
    }

    private boolean isUncleRed(Node node)
    {
        if (node.parent.parent.left == node.parent)
            return node.parent.parent.right.color == Color.RED;
        else
            return node.parent.parent.left.color == Color.RED;
    }

    private boolean isInternalNode(Node node)
    {
        if (node.parent.parent.left == node.parent)
            return node.parent.parent.left.right == node;
        else
            return node.parent.parent.right.left == node;
    }

    private void turnLeft(Node node) {
        if (node.left == NULL)
            node.parent.right = NULL;
        else
        {
            node.parent.right = node.left;
            node.parent.right.parent = node.parent;
        }
        node.left = node.parent;
        node.parent = node.parent.parent;
        node.left.parent = node;
        if (node.parent != null)
        {
            if (node.parent.left == node.left)
                node.parent.left = node;
            else
                node.parent.right = node;
        }
        else
            root = node;
    }

    private void turnRight(Node node)
    {
        if (node.right == NULL)
            node.parent.left = NULL;
        else
        {
            node.parent.left = node.right;
            node.parent.left.parent = node.parent;
        }
        node.right = node.parent;
        node.parent = node.parent.parent;
        node.right.parent = node;
        if (node.parent != null)
        {
            if (node.parent.left == node.right)
                node.parent.left = node;
            else
                node.parent.right = node;
        }
        else
            root = node;
    }

    private Node search(Node node, K key)
    {
        if (comparator.compare(key, node.key) == 0)
            return node;
        else if (comparator.compare(key, node.key) < 0)
        {
            if (node.left != NULL)
                return search(node.left, key);
            else
                throw new NoSuchElementException();
        }
        else
        {
            if (node.right != NULL)
                return search(node.right, key);
            else
                throw new NoSuchElementException();
        }
    }

    private Node remove1(Node node)
    {
        if (node.color == Color.RED)
        {
            if (node.parent.left == node)
            {
                node.parent.left = NULL;
                return NULL;
            }
            else
            {
                node.parent.right = NULL;
                return NULL;
            }
        }
        else
        {
            Node specialNULL = new Node(null, null, node.parent, null, null, Color.BLACK);
            if (node.parent.left == node)
            {
                node.parent.left = specialNULL;
                return specialNULL;
            }
            else
            {
                node.parent.right = specialNULL;
                return specialNULL;
            }
        }
    }

    private Node remove2(Node node)
    {
        if (node.parent.left == node)
        {
            if (node.left != NULL)
            {
                node.parent.left = node.left;
                node.left.parent = node.parent;
            }
            else
            {
                node.parent.left = node.right;
                node.right.parent = node.parent;
            }
            node.parent.left.color = Color.BLACK;
        }
        else
        {
            if (node.left != NULL)
            {
                node.parent.right = node.left;
                node.left.parent = node.parent;
            }
            else
            {
                node.parent.right = node.right;
                node.right.parent = node.parent;
            }
            node.parent.right.color = Color.BLACK;
        }
        return NULL;
    }

    private Node remove3(Node node)
    {
        Node minBig = searchMinBig(node.right);
        node.key = minBig.key;
        node.value = minBig.value;
        if (minBig.right != NULL)
            return remove2(minBig);
        else
            return remove1(minBig);
    }

    private Node searchMinBig(Node node)
    {
        if(node.left != NULL)
            return searchMinBig(node.left);
        else
            return node;
    }

    private void removeBalancing(Node node)
    {
        if (node != root)
        {
            Node brother = getBrother(node);
            if (brother.color == Color.RED)
            {
                brother.color = Color.BLACK;
                brother.parent.color = Color.RED;
                if (brother.parent.left == brother)
                    turnRight(brother);
                else
                    turnLeft(brother);
                brother = getBrother(node);
            }
            else if (brother.parent.color == Color.BLACK && brother.left.color == Color.BLACK && brother.right.color == Color.BLACK)
            {
                brother.color = Color.RED;
                removeBalancing(brother.parent);
            }
            if (brother.left.color == Color.BLACK && brother.right.color == Color.BLACK && brother.parent.color == Color.RED)
            {
                brother.color = Color.RED;
                brother.parent.color = Color.BLACK;
            }
            else
            {
                Node internalNephew = getInternalNephew(brother);
                Node externalNephew = getExternalNephew(brother);
                if (internalNephew.color == Color.RED && externalNephew.color == Color.BLACK)
                {
                    if (brother.left == internalNephew)
                        turnRight(internalNephew);
                    else
                        turnLeft(internalNephew);
                    internalNephew.color = Color.BLACK;
                    brother.color = Color.RED;
                    brother = internalNephew;
                    externalNephew = getExternalNephew(brother);
                }
                if (brother.parent.left == brother)
                    turnRight(brother);
                else
                    turnLeft(brother);
                if (node.parent.color == Color.BLACK)
                    brother.color = Color.BLACK;
                else
                    brother.color = Color.RED;
                node.parent.color = Color.BLACK;
                externalNephew.color = Color.BLACK;
            }
        }
    }

    private Node getBrother(Node node)
    {
        if (node.parent.left == node)
            return node.parent.right;
        else
            return node.parent.left;
    }

    private Node getInternalNephew(Node node)
    {
        if (node.left == NULL && node.right == NULL)
            return NULL;
        else if (node.left == NULL)
        {
            if (isInternalNode(node.right))
                return node.right;
            else
                return node.left;
        }
        else if (isInternalNode(node.left))
                return node.left;
            else
                return node.right;
    }

    private Node getExternalNephew(Node node)
    {
        if (node.left == NULL && node.right == NULL)
            return  NULL;
        else if (node.left == NULL)
        {
            if (!isInternalNode(node.right))
                return node.right;
            else
                return NULL;
        }
        else if (!isInternalNode(node.left))
            return node.left;
        else
            return node.right;
    }

    private boolean checkRec(Node node, int height)
    {
        if (node.color == Color.BLACK)
            height++;
        else if (node.left.color == Color.RED || node.right.color == Color.RED)
            return false;
        if (node.left != NULL)
            return checkRec(node.left, height);
        else if (node.right != NULL)
            return checkRec(node.right, height);
        else
            return height == globalBlackHeight;
    }

    private class Node {
        K key;
        V value;
        Node parent;
        Node left, right;
        Color color;

        Node(K key, V value, Node parent, Node left, Node right, Color color)
        {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        Node(Node other)
        {
            this.key = other.key;
            this.value = other.value;
            this.parent = other.parent;
            this.left = other.left;
            this.right = other.right;
            this.color = other.color;
        }
    }

    private enum Color {RED, BLACK}
}