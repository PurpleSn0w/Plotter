package PsMathDSP;

public class PsMathDSP {
    public static double[] convolution(double[] x,double[] h){
        int sizex=x.length,sizeh=h.length,sizey=x.length+h.length-1;
        double[] y=new double[sizey];
        for(int i=0;i<sizey;i++){
            for(int j=0;j<sizeh;j++){
                if(i-j>=0 && i-j<sizex){
                    y[i]+=h[j]*x[i-j];
                }
            }
        }
        return y;
    }
    public static int[] convolution(int[] x,int[] h){
        int sizex=x.length,sizeh=h.length,sizey=x.length+h.length-1;
        int[] y=new int[sizey];
        for(int i=0;i<sizey;i++){
            for(int j=0;j<sizeh;j++){
                if(i-j>=0 && i-j<sizex){
                    y[i]+=h[j]*x[i-j];
                }
            }
        }
        return y;
    }
}
