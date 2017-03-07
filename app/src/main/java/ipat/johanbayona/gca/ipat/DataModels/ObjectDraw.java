package ipat.johanbayona.gca.ipat.DataModels;

import android.graphics.PointF;

/**
 * Created by GCATech on 26/10/2016.
 */

public class ObjectDraw {

    private PointF startPoint = new  PointF(0,0);;
    private PointF halfPoint = new PointF(0,0);
    private PointF endPoint = new PointF(0,0);;
    private double measure = 0;
    private  int seleccion=0;

    public int getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(int seleccion) {
        this.seleccion = seleccion;
    }

    public PointF getStartPoint() {
        return startPoint;
    }


    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }

    public void setStartPoint(PointF startPoint) {
        this.startPoint = startPoint;
        calculateHalfPoint();
    }

    public PointF getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(PointF endPoint) {
        this.endPoint = endPoint;
        calculateHalfPoint();
    }

    public PointF getHalfPoint(){
        return halfPoint;
    }

    private void calculateHalfPoint(){
        this.halfPoint.x = Math.abs(startPoint.x + endPoint.x)/2;
        this.halfPoint.y = Math.abs(startPoint.y + endPoint.y)/2;
    }


}
