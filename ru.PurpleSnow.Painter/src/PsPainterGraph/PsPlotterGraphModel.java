package PsPainterGraph;

import java.util.ArrayList;

public class PsPlotterGraphModel<E extends PsPainterGraphCore> implements PsPainterGraphInterface<E>{
    public ArrayList<E> cores = new ArrayList();
    public boolean isEmpty(){
        if(cores!=null && cores.size()>0 && cores.get(0)!=null)return false;
        return true;
    }
    public int getCount(){
        return cores.size();
    }
    public boolean isReady(int index){
        return isReady(cores.get(index));
    }
    public boolean isReady(E core){
        if(core!=null && core.begin>=0 && core.end>=core.begin+2 && core.y!=null && core.y.length>=3
                && core.xstep>0 && core.ystep>0)return true;
        return false;
    }
    public boolean isReady(){
        for(E core:cores){
            if(!isReady(core))return false;
        }
        return true;
    }
    public int getBegin(){
        if(isReady(0))return cores.get(0).begin;
        return -1;
    }
    public int getEnd(){
        if(isReady(0))return cores.get(0).end;
        return -1;
    }
    public int getLength(){
        if(isReady(0))return cores.get(0).y.length;
        return -1;
    }
    public E get(int index){
        return cores.get(index);
    }
    public void add(E core){
        cores.add(core);
    }
    public void setAreaX(int x,int w){
        double step = (double)w/(getEnd()-getBegin());
        for(E core:cores){
            core.areaX = x;
            core.areaW = w;
            core.xstep = step;
        }
    }
    public void setAreaY(int y,int h){
    }
}
