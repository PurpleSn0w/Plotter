package PsPainterGraph;

public interface PsPainterGraphInterface<E extends PsPainterGraphCore> {
    boolean isEmpty();
    boolean isReady(E core);
    boolean isReady(int index);
    boolean isReady();
    E get(int index);
    int getBegin();
    int getEnd();
    int getLength();
    int getCount();
    void setRange(int newBegin,int newEnd);
    void setParams(E core);
    void calcParams();
    void add(E core);
    void remove(int index);
    void setAreaX(int x,int w);
    void setAreaY(int y,int h);
}
