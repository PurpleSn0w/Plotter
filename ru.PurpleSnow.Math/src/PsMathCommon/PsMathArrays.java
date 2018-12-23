package PsMathCommon;

import java.util.Arrays;
import java.util.Random;

public class PsMathArrays {
    public static double max(double []array){
        double ret=array[0];
        for(double y:array){
            ret=Math.max(ret,y);
        }
        return ret;
    }
    public static double min(double []array){
        double ret=array[0];
        for(double y:array){
            ret=Math.min(ret,y);
        }
        return ret;
    }
    public static double max(double []array,int from,int to){
        double ret=array[from];
        for(int i=from;i<=to;i++){
            ret=Math.max(ret,array[i]);
        }
        return ret;
    }
    public static double min(double []array,int from,int to){
        double ret=array[from];
        for(int i=from;i<=to;i++){
            ret=Math.min(ret,array[i]);
        }
        return ret;
    }
    public static double[] maxmin(double []array){
        double[] ret = new double[2];
        ret[0]=array[0];
        ret[1]=array[0];
        for(double y:array){
            ret[0]=Math.min(ret[0],y);
            ret[1]=Math.max(ret[1],y);
        }
        return ret;
    }
    public static double[] maxmin(double []array,int from,int to){
        double[] ret = new double[2];
        ret[0]=array[from];
        ret[1]=array[from];
        for(int i=from;i<=to;i++){
            ret[0]=Math.min(ret[0],array[i]);
            ret[1]=Math.max(ret[1],array[i]);
        }
        return ret;
    }
    public static double[] maxmin(double[] ...arrays){
        double[] a0 = arrays[0];
        double[] ret = new double[2];
        ret[0] = a0[0];
        ret[1] = a0[0];
        for(double[] a:arrays){
            double[] m = maxmin(a);
            if(ret[0]>m[0])ret[0]=m[0];
            if(ret[1]<m[1])ret[1]=m[1];
        }
        return ret;
    }
    public static double[] toDouble(int []y){
        int size=y.length;
        double []ret=new double[size];
        for(int i=0;i<size;i++){
            ret[i]=(double)y[i];
        }
        return ret;
    }
    public static int[] genRandom(int size,int range){
        int[] ret=new int[size];
        Random r=new Random();
        for(int i=0;i<size;i++)ret[i]=r.nextInt(range);
        return ret;
    }
    public static double[] genRandom(int size,double rate){
        double[] ret=new double[size];
        Random r=new Random();
        for(int i=0;i<size;i++)ret[i]=r.nextDouble()*rate;
        return ret;
    }
    public static double[] genSin(int size,double amplitude,double phase,double step){
        /**
         * phase    in radians
         * step     in radians
         * */
        double[] ret = new double[size];
        for(int i=0;i<size;i++){
            ret[i]=amplitude*Math.sin(phase+step*i);
        }
        return ret;
    }
    public static double[] sum(double[] a,double[] b){
        int size = Math.min(a.length,b.length);
        double[] ret = new double[size];
        for(int i=0;i<size;i++){
            ret[i]=a[i]+b[i];
        }
        return ret;
    }
    public static double[] diff(double[] a,double[] b){
        /**
         * a - b
         */
        int size = Math.min(a.length,b.length);
        double[] ret = new double[size];
        for(int i=0;i<size;i++){
            ret[i]=a[i]-b[i];
        }
        return ret;
    }
    public static double[] mult(double[] a,double[] b){
        int size = Math.min(a.length,b.length);
        double[] ret = new double[size];
        for(int i=0;i<size;i++){
            ret[i]=a[i]*b[i];
        }
        return ret;
    }
    public static double[] devide(double[] a,double[] b){
        /**
         * a / b
         */
        int size = Math.min(a.length,b.length);
        double[] ret = new double[size];
        for(int i=0;i<size;i++){
            ret[i]=a[i]/b[i];
        }
        return ret;
    }
    public static double[] sum(double[] src,double increment){
        double[] ret = new double[src.length];
        for(int i=0;i<src.length;i++){
            ret[i]=src[i]+increment;
        }
        return ret;
    }
    public static double[] mult(double[] src,double rate){
        double[] ret = new double[src.length];
        for(int i=0;i<src.length;i++){
            ret[i]=src[i]*rate;
        }
        return ret;
    }
    public static double[] exclude(double[] src,int from,int count){
        /**
         * from - inclusive
         */
        if(from>=src.length)return Arrays.copyOf(src,src.length);
        int size=src.length;
        int sizeNew=Math.max(from,size-count);
        double[] ret = new double[sizeNew];
        int j=0;
        for(int i=0;i<size;i++){
            if(i<from || i>=from+count){
                ret[j]=src[i];
                j++;
            }
            else i+=count-1;
        }
        if(j!=sizeNew)System.err.println("PsMathArrays.exclude() - mismatch of result array size: "+
                "expected "+sizeNew+", returned "+j);
        return ret;
    }
    public static double[] alignLength(double[] src,double[] target,double blank){
        int lenSrc = src.length;
        int lenTarget = target.length;
        if(lenSrc>=lenTarget)return Arrays.copyOf(src,target.length);
        else {
            double[] ret = new double[lenTarget];
            for(int i=0;i<lenTarget;i++){
                ret[i] = (i<lenSrc) ? src[i] : blank;
            }
            return ret;
        }
    }
    public static double[] alignLength(double[] src,int targetLength,double blank){
        int lenSrc = src.length;
        if(lenSrc>=targetLength)return Arrays.copyOf(src,targetLength);
        else {
            double[] ret = new double[targetLength];
            for(int i=0;i<targetLength;i++){
                ret[i] = (i<lenSrc) ? src[i] : blank;
            }
            return ret;
        }
    }
}
