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
    void setParams();
    void copyParams(E core);
    void calcParams();
    void add(E core);
    void add(int index,E core);
    void remove(int index);
    void removeAll();
    void setAreaX(int x,int w);
    void setAreaY(int y,int h);
    void zoomX(double rate);
    void zoomX(double rate,int point);
    void move(int step);
    void moveTo(int point);
    void fitY(boolean full);
    void info();
}
