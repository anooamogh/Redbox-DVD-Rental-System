//Amogh Anoo ASA210011
public class Node<N extends Comparable<N>> {
    public Node<N> left;
    public Node<N> right;
    public DVD<N> payload;

    public Node(DVD<N> dvd){
        this.setPayload(dvd);
    }

    public DVD<N> getPayload() {
        return payload;
    }

    public void setPayload(DVD<N> payload) {
        this.payload = payload;
    }
    public String toString(){
        return this.payload.toString();
    }
    public int compareTo(Object node){
        //if it is a node
        if (node instanceof Node){
            Node<N> n = (Node<N>) node;
            return this.payload.compareTo((N) n.getPayload());
        }
        return -1;
    }
}
