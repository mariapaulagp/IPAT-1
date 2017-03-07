package ipat.johanbayona.gca.ipat.Sketch;

import android.graphics.PointF;

/**
 * Created by JohanBayona on 19/12/2016.
 */

public class MyTrigonometry {

    public static PointF halfTwoVectors(PointF startPoint, PointF endPoint){
        return new PointF((startPoint.x + endPoint.x) / 2, (startPoint.y + endPoint.y) / 2);
    }

    public static double destanceTwoVectors(PointF startPoint, PointF endPoint){
        return (Math.sqrt(Math.pow(endPoint.x - startPoint.x, 2) + (Math.pow(endPoint.y - startPoint.y, 2))));
    }

    public static double angleTwoVectors(PointF startPoint, PointF endPoint){
        if(endPoint == null) return 0;
        double angleRadians = Math.atan2(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
        double angleDegrees = Math.toDegrees(angleRadians);
        return angleDegrees - 90;
    }

//    public static PointF doubleCenterPoint(PointF startPoint, PointF endPoint, double h, boolean type){
//        double distance;
//        if (type) distance = 1.69; //(h / 100) / 2; // paso de cm a mtrs
//        else distance = 1.69 / 2;
//
//        PointF halfPoint = new PointF(Math.abs(startPoint.x + endPoint.x) / 2, Math.abs(startPoint.y + endPoint.y) / 2);
//        float angleLine = (float) Math.atan2(halfPoint.y - startPoint.y, halfPoint.x - startPoint.x);
//
//        float anglePoint = angleLine - (float) Math.PI / 2; // 90°
//        PointF result = new PointF((float) (halfPoint.x - distance * Math.cos(anglePoint)),(float) (halfPoint.y - distance * Math.sin(anglePoint)));
//        return result;
//    }

    public static PointF getTwoCenterPoint(PointF startPoint, PointF endPoint, double h){
        if(startPoint == null) startPoint = endPoint;
        PointF halfPoint = new PointF(Math.abs(startPoint.x + endPoint.x) / 2, Math.abs(startPoint.y + endPoint.y) / 2);
        float phi = (float) Math.atan2(halfPoint.y - endPoint.y, halfPoint.x - endPoint.x); // substitute x1, x2, y1, y2 as needed

        float tip1angle = phi - (float) (3 * Math.PI / 2); ; // -90°
        float x5 = (float) (halfPoint.x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
        float y5 = (float) (halfPoint.y - h * Math.sin(tip1angle));

        return new PointF(x5, y5);
    }

    public static PointF getCornerrPoint(PointF startPoint, PointF endPoint, double h){
        float phi = (float) Math.atan2(startPoint.y - endPoint.y, startPoint.x - endPoint.x); // substitute x1, x2, y1, y2 as needed

        float tip1angle = phi - (float) (3 * Math.PI / 2); ; // -90°
        float x5 = (float) (startPoint.x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
        float y5 = (float) (startPoint.y - h * Math.sin(tip1angle));

        return new PointF(x5, y5);
    }
}
