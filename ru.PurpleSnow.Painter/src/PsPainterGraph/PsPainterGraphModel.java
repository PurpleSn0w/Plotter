package PsPainterGraph;

import PsMathCommon.PsMathArrays;

import java.util.ArrayList;

public class PsPainterGraphModel<E extends PsPainterGraphCore> implements PsPainterGraphInterface<E>{
    public ArrayList<E> cores = new ArrayList();
    public int begin=-1,end=-1,length=-1;
    public double xstep=-1,ystep=-1,zeroLevel=-1;
    public int areaX,areaY,areaW,areaH;
    double min,max;

    public PsPainterGraphModel(int length){
        this.length = length;
        begin = 0;
        end = length-1;
    }

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
                && core.xstep>0 && core.ystep>0 && core.y.length==length)return true;
        return false;
    }
    public boolean isReady(){
        for(E core:cores){
            if(!isReady(core))return false;
        }
        return true;
    }
    public int getBegin(){
        return begin;
    }
    public int getEnd(){
        return end;
    }
    public int getLength(){
        return length;
    }
    public E get(int index){
        return cores.get(index);
    }
    public void setRange(int newBegin,int newEnd){
        begin = newBegin;
        end = newEnd;
        setAreaX(areaX,areaW);
        for(E core:cores){
            core.calcXstep(newBegin,newEnd);
        }
        xstep = cores.get(0).xstep;
    }
    public void setParams(E core){
        if(core.y==null)core.y=new double[length];
        else if(core.y.length!=length && getCount()>1)
            core.y = PsMathArrays.alignLength(core.y,length,core.y[core.y.length-1]);
        core.begin = begin;
        core.end = end;
        core.xstep = xstep;
        core.ystep = ystep;
        core.zeroLevel = zeroLevel;
        core.areaX = areaX;
        core.areaY = areaY;
        core.areaW = areaW;
        core.areaH = areaH;
    }
    public void setParams(){
        for(E core:cores){
            setParams(core);
        }
    }
    public void copyParams(E core){
        if(core != null){
            begin       = core.begin;
            end         = core.end;
            xstep       = core.xstep;
            ystep       = core.ystep;
            zeroLevel   = core.zeroLevel;
            areaX       = core.areaX;
            areaY       = core.areaY;
            areaW       = core.areaW;
            areaH       = core.areaH;
        }
    }
    public void calcParams(){
        if(cores!=null && cores.size()>0) {
            min = cores.get(0).y[0];
            max = min;
            for (E core : cores) {
                double[] m = PsMathArrays.maxmin(core.y);
                min = Math.min(min, m[0]);
                max = Math.max(max, m[1]);
            }
            ystep = 0;
            if (max - min != 0) ystep = (double) areaH / (max - min);
            zeroLevel = areaY + areaH + ystep * min;
            setRange(begin, end);
            setParams();
        }
    }
    public void add(E core){
        if(!isReady(core))setParams(core);
        add(cores.size(),core);
    }
    public void add(int index,E core){
        if(!isReady(core)) setParams(core);
        cores.add(index,core);
        if(core.y.length!=length)PsMathArrays.alignLength(core.y,length,core.y[core.y.length-1]);
        double[] minmax = PsMathArrays.maxmin(core.y);
        if(getCount()==1){
            length = cores.get(0).y.length;
            setRange(0,length-1);
        }
        if(getCount()==1 || min>minmax[0] || max<minmax[1])calcParams();
    }
    public void remove(int index){
        cores.remove(index);
        calcParams();
    }
    public void removeAll(){
        cores.clear();
    }
    public void setAreaX(int x,int w){
        areaX = x;
        areaW = w;
        for(E core:cores){
            core.areaX = x;
            core.areaW = w;
            core.calcXstep();
        }
    }
    public void setAreaY(int y,int h){
        areaY = y;
        areaH = h;
        for(E core:cores){
            /*double rate = (double)core.areaH/core.ystep;
            double zero = (double)core.areaH/core.zeroLevel;
            core.areaY = y;
            core.areaH = h;
            core.ystep = (double)core.areaH/(double)rate;
            core.zeroLevel = (double)core.areaH/zero;*/

            core.areaY = y;
            core.areaH = h;
        }
        calcParams();
    }
    public void zoomX(double rate){
        for(E core:cores){
            core.zoomX(rate);
        }
        copyParams(get(0));
    }
    public void zoomX(double rate,int point){
        for(E core:cores){
            core.zoomX(rate,point);
        }
        copyParams(get(0));
    }
    public void move(int step){
        for(E core:cores){
            core.move(step);
        }
        copyParams(get(0));
    }
    public void moveTo(int point){
        for(E core:cores){
            core.moveTo(point);
        }
        copyParams(get(0));
    }
    public void fitY(boolean full){
        /*if(cores!=null && cores.size()>0) {
            min = cores.get(0).y[0];
            max = min;
            for (E core : cores) {
                double[] m = full?PsMathArrays.maxmin(core.y):PsMathArrays.maxmin(core.y,begin,end);
                min = Math.min(min, m[0]);
                max = Math.max(max, m[1]);
            }
            ystep = 0;
            if (max - min != 0) ystep = (double) areaH / (max - min);
            zeroLevel = areaY + areaH + ystep * min;
            setParams();
        }*/
        begin = 0;
        end = cores.get(0).y.length-1;
        calcParams();
    }
    public void info(){
        System.err.println("PsPainterGraphModel object info:");
        System.err.println("size = "+cores.get(0).y.length+"   ["+begin+":"+end+"]   xstep = "+xstep+
                "   ystep = "+ystep+"   zeroLevel = "+zeroLevel+"   rect = "+areaX+" "+areaY+" "+areaW+" "+areaH);
    }
}
