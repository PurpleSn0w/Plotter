package PsPainterGraph;

import PsMathCommon.PsMathArrays;

/**
 * zeroLevel, xstep, ystep - вычисляемые параметры
 * calcParams - вычисляет эти параметры, если autofillY=true; calcParams вызывать
 *  только при необходимости!
 *
 *  если в вызове ф-ии отрисовки нет каких-то параметров, будут использованы их текущие значения
 *  иначе соотв-ие параметры будут пересчитаны
 * */
public class PsPainterGraphCore {
    public double []y;
    public int begin, end;
    public double xstep,ystep,zeroLevel;
    public boolean autofillY = true;
    public int areaX,areaY,areaW,areaH;
    public boolean ready = false;

    DrawLine drawLine;

    public PsPainterGraphCore(double []y, int begin, int end, double xstep,
                              double ystep, double zeroLevel, boolean autofillY,
                              int areaX,int areaY,int areaW,int areaH,DrawLine drawLine){
        this.y=y;
        this.begin = begin;
        this.end = end;
        this.xstep=xstep;
        this.ystep=ystep;
        this.zeroLevel=zeroLevel;
        this.autofillY = autofillY;
        this.areaX=areaX;
        this.areaY=areaY;
        this.areaW=areaW;
        this.areaH=areaH;
        this.drawLine=drawLine;
        checkReady();
    }
    public PsPainterGraphCore(double []y,int areaX,int areaY,int areaW,int areaH,DrawLine drawLine){
        this.y=y;
        this.areaX=areaX;
        this.areaY=areaY;
        this.areaW=areaW;
        this.areaH=areaH;
        this.drawLine=drawLine;
        begin=0;
        end=y.length-1;
        autofillY = true;
        calcParams();
        checkReady();
    }
    public PsPainterGraphCore(){
        y = new double[3];
        y[0]=y[1]=y[2]=0;
        areaH=areaW=areaX=areaY=10;
        drawLine=(double x1,double y1,double x2,double y2) -> {};
        begin=0;end=2;
        autofillY = true;
        xstep=ystep=zeroLevel=1;
        checkReady();
    }
    public PsPainterGraphCore(double[] y,PsPainterGraphCore pattern){
        this(   y,pattern.begin,pattern.end,pattern.xstep,
                pattern.ystep,pattern.zeroLevel,pattern.autofillY,
                pattern.areaX,pattern.areaY,pattern.areaW,pattern.areaH,pattern.drawLine
        );
    }
    public PsPainterGraphCore(double[] y){
        this.y = y;
        this.drawLine = (double x1,double y1,double x2,double y2) -> {};
    }
    public PsPainterGraphCore createByPattern(double[] y){
        return new PsPainterGraphCore(y,this);
    }
    public void adjustToPattern(double[] y,PsPainterGraphCore pattern){
        copyParams(pattern);
        this.y = y;
    }
    public void copy(PsPainterGraphCore source){
        this.y = source.y;
        copyParams(source);
    }
    public void copyParams(PsPainterGraphCore pattern){
        this.begin = pattern.begin;
        this.end = pattern.end;
        this.xstep = pattern.xstep;
        this.ystep = pattern.ystep;
        this.zeroLevel = pattern.zeroLevel;
        this.autofillY = pattern.autofillY;
        this.areaX = pattern.areaX;
        this.areaY = pattern.areaY;
        this.areaW = pattern.areaW;
        this.areaH = pattern.areaH;
        this.drawLine = pattern.drawLine;
    }
    private void calcParams(){
        calcXstep();
        if(autofillY)fillYwhole();
    }
    private boolean checkReady(){
        if(y!=null && y.length>2 && drawLine!=null)ready=true;
        else ready=false;
        return ready;
    }
    public void refreshArea(int areaX,int areaY,int areaW,int areaH){
        this.areaX=areaX;
        this.areaY=areaY;
        this.areaW=areaW;
        this.areaH=areaH;
        calcParams();
    }
    public void setDrawLine(DrawLine drawLine){
        this.drawLine=drawLine;
        checkReady();
    }

    public void info(){
        System.err.println("PsPainterGraphCore object info:");
        System.err.println("size = "+y.length+"   ["+begin+":"+end+"]   xstep = "+xstep+
                "   ystep = "+ystep+"   zeroLevel = "+zeroLevel+"   rect = "+areaX+" "+areaY+" "+areaW+" "+areaH);
    }

    //interface funcs
    public void draw(){
        for(int i=begin+1;i<=end;i++){
            drawLine.drawLine(areaX+xstep*(i-begin-1),zeroLevel-ystep*y[i-1],
                    areaX+xstep*(i-begin),zeroLevel-ystep*y[i]);
        }
    }
    public void fillYwhole(){
        double[] maxmin = PsMathArrays.maxmin(y);
        double max = maxmin[1];
        double min = maxmin[0];
        ystep=0;
        if(max-min!=0)ystep=(double)areaH/(max-min);
        zeroLevel=areaY+areaH+ystep*min;
    }
    public void fillY(){
        double[] maxmin = PsMathArrays.maxmin(y,begin,end);
        double max = maxmin[1];
        double min = maxmin[0];
        ystep=0;
        if(max-min!=0)ystep=(double)areaH/(max-min);
        zeroLevel=areaY+areaH+ystep*min;
    }
    public void calcXstep(int begin,int end){
        this.begin=begin;
        this.end=end;
        calcXstep();
    }
    public void calcXstep(){
        xstep=Math.abs(Math.min(areaW,(double)areaW/(end-begin)));
    }
    public void fitFull(){
        calcXstep(0, y.length - 1);
        fillYwhole();
    }
    public int getBeginX(double x){
        double xRelative = x-areaX;
        int ret = (int)(xRelative/xstep);
        if(xRelative>0)ret++;
        ret=Math.max(0,ret);
        return Math.max(ret+begin,0);
    }
    public static int getBeginX(double x,int areaX,double xstep,int begin){
        double xRelative = x-areaX;
        int ret = (int)(xRelative/xstep);
        if(xRelative>0)ret++;
        ret=Math.max(0,ret);
        return Math.max(ret+begin,0);
    }
    public int getEndX(double x){
        double xRelative = x-areaX;
        int ret = (int)(xRelative/xstep);
        ret = Math.min(y.length-1,ret);
        return Math.min(y.length-1,ret+begin);
    }
    public static int getEndX(double x,int arrayLen,int areaX,double xstep,int begin){
        double xRelative = x-areaX;
        int ret = (int)(xRelative/xstep);
        ret = Math.min(arrayLen-1,ret);
        return Math.min(arrayLen-1,ret+begin);
    }
    public void zoomX(double rate,int point){
        //int range=(int)((end-begin)*rate);  //количество точек
        int range=end-begin+1;
        int newBegin=begin;
        int newEnd=end;
        if(rate<1){
            range=(int)((end-begin)*rate);
            range=Math.max(3,range);
            int left=range/2;
            newBegin=point-left;
            newEnd=newBegin+range-1;
        }
        else if(rate>1){
            range=(int)((end-begin+1)*rate)+2;
            range=Math.min(range+1,y.length);
            if(range<y.length){
                int left=range/2;
                newBegin=point-left;
                newEnd=newBegin+range-1;
            }
            else {
                begin=0;
                newBegin=0;
                newEnd=y.length-1;
            }
        }
        if(newBegin<0){
            newEnd-=newBegin;
            newEnd=Math.min(newEnd,y.length-1);
            newBegin=0;
        }
        else if(newEnd>y.length-1){
            int delta=newEnd-y.length+1;
            newBegin-=delta;
            newBegin=Math.max(newBegin,0);
            newEnd=y.length-1;
        }
        begin=newBegin;
        end=newEnd;
        calcXstep(begin,end);
    }
    public void zoomX(double rate){
        zoomX(rate,begin+(end-begin)/2);
    }

    public interface DrawLine{
        void drawLine(double x1,double y1,double x2,double y2);
    }
}