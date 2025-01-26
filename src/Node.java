public class Node {
    int size;
    Pixel start;

    Node child0;
    Node child1;
    Node child2;
    Node child3;

    public Node(int size, Pixel start) {
        this.size = size;
        this.start = start;
        child0 = null;
        child1 = null;
        child2 = null;
        child3 = null;
    }

    public Boolean isLeaf(Pixel[][] image) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (! image[start.x + i][start.y + j].equals(start)) {
                    return false;
                }
            }
        }
        return true;
    }
}
