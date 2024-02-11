//Amogh Anoo ASA210011
public class DVD<D> implements Comparable<D> {
    public String title;
    public int available;
    public int rented;

    public DVD() {
    }

    public DVD(String t, int a, int r) {
        title = t;
        available = a;
        rented = r;
    }

    public int getAvailable() {
        return available;
    }

    public int getRented() {
        return rented;
    }

    public String getTitle() {
        return title;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setRented(int rented) {
        this.rented = rented;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return String.format("%-30s %d %d\n", title, available, rented);
    }

    @Override
    public int compareTo(D o) {
        //if dvd then execute
        if (o instanceof DVD){
            DVD<D> temp = (DVD<D>) o;
            return this.title.compareTo(((DVD<?>) o).title);
        }
        return -2004;
    }
}
