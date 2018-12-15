package PsPainterGraph;

import java.util.ArrayList;

public class PsPainterGraphCores<E extends PsPainterGraphCore> {
    ArrayList<E>cores = new ArrayList<>();
    public void clear(){
        cores.clear();
    }
    public void add(E core){
        cores.add(core);
    }
    public void remove(int i){
        cores.remove(i);
    }
    public void remove(E core){
        cores.remove(core);
    }
}
