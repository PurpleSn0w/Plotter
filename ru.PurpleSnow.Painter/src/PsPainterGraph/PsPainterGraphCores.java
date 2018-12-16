package PsPainterGraph;

import PsMathCommon.PsMathArrays;

import java.util.ArrayList;

public class PsPainterGraphCores<E extends PsPainterGraphCore> {
    public ArrayList<E>cores = new ArrayList();
    public boolean ready=false;
    private void setReady(boolean b){
        ready = b;
    }
    public void clear(){
        cores.clear();
    }
    public E get(int index){
        return cores.get(index);
    }
    public void add(E core){
        cores.add(core);
    }
    public void add(int index, E core){cores.add(index,core);}
    public void add(double[] y,E blank){
        if(cores.size()>0 && cores.get(0)!=null){
            add(blank);
            cores.get(cores.size()-1).copy(get(0));
            cores.get(cores.size()-1).y = y;
        }
    }
    public void add(int index,double[] y,E blank){
        if(cores.size()>0 && cores.get(0)!=null){
            add(index,blank);
            cores.get(cores.size()-1).copy(get(0));
            cores.get(cores.size()-1).y = y;
        }
    }
    public void remove(int i){
        cores.remove(i);
    }
    public void remove(E core){
        cores.remove(core);
    }
    public boolean ready(){
        for(E core:cores){
            if(!core.ready)return false;
        }
        return true;
    }
    public void setXstep(double xstep,int begin,int end){
        for(E core:cores){
            core.xstep=xstep;
            core.begin=begin;
            core.end=end;
        }
    }
    public void calcXstep(int begin,int end){
        cores.get(0).calcXstep(begin,end);
        setXstep(cores.get(0).xstep,begin,end);
    }
    public void calcXstep(){
        cores.get(0).calcXstep();
        setXstep(cores.get(0).xstep,cores.get(0).begin,cores.get(0).end);
    }
    public void setYparams(double ystep,double zeroLevel){
        for(E core:cores){
            core.ystep = ystep;
            core.zeroLevel = zeroLevel;
        }
    }
    public void fillYwhole(){
        double max = cores.get(0).y[0];
        double min = max;
        for(E core:cores){
            double[] maxmin = PsMathArrays.maxmin(core.y);
            max = Math.max(max,maxmin[1]);
            min = Math.min(min,maxmin[0]);
        }
        double ystep = 0;
        if(max-min!=0)ystep=(double)cores.get(0).areaH/(max-min);
        double zeroLevel=cores.get(0).areaY+cores.get(0).areaH+ystep*min;
        setYparams(ystep,zeroLevel);
    }
    public void fillY(){
        double max = cores.get(0).y[0];
        double min = max;
        for(E core:cores){
            double[] maxmin = PsMathArrays.maxmin(core.y,cores.get(0).begin,cores.get(0).end);
            max = Math.max(max,maxmin[1]);
            min = Math.min(min,maxmin[0]);
        }
        double ystep = 0;
        if(max-min!=0)ystep=(double)cores.get(0).areaH/(max-min);
        double zeroLevel=cores.get(0).areaY+cores.get(0).areaH+ystep*min;
        setYparams(ystep,zeroLevel);
    }
    public void fitFull(){
        calcXstep(0,cores.get(0).y.length-1);
        fillYwhole();
    }
    public void refreshAreaX(int areaX,int areaW){
        cores.get(0).areaX = areaX;
        cores.get(0).areaW = areaW;
        calcXstep();
    }
    public void refreshAreaY(int areaY,int areaH){
        cores.get(0).areaY = areaY;
        cores.get(0).areaH = areaH;
        fillYwhole();
    }
    public int getBeginX(double x){
        return cores.get(0).getBeginX(x);
    }
    public int getEndX(double x){
        return cores.get(0).getEndX(x);
    }
    public void setDrawLine(PsPainterGraphCore.DrawLine drawLine){
        for(E core:cores){
            core.setDrawLine(drawLine);
        }
    }
    public void zoomX(double rate,int point){
        cores.get(0).zoomX(rate,point);
        setXstep(cores.get(0).xstep,cores.get(0).begin,cores.get(0).end);
    }
    public void zoomX(double rate){
        cores.get(0).zoomX(rate);
        setXstep(cores.get(0).xstep,cores.get(0).begin,cores.get(0).end);
    }
}
