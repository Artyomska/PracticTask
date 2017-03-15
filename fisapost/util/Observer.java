package fisapost.util;

public interface Observer<E> {
    void update(Observable<E> observable);
}
