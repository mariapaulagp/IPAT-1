package ipat.johanbayona.gca.ipat;

import android.content.res.Resources;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by JohanBayona on 1/12/2016.
 */

public class MyConvert  {

    private static int dpi;
    private static float scale = 1;                                                                 // escala 1:200
    private static PointF screenResolution;
    private static PointF areaSelected;
    private static PointF offset;

    public static int dpToPx(double dp) {
        return (int) Math.round(dp * (dpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(double px) {
        return (int) Math.round(px / (dpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static PointF PixsToMts(PointF coordenate){
        return new PointF((float) ((((coordenate.x * 2.54) / dpi) * scale) / 100),
                          (float) ((((coordenate.y * 2.54) / dpi) * scale) / 100));
    }

    public static double PixsToMts(double pixel){
        return ((((pixel * 2.54) / dpi) * scale) / 100);
    }

    public static PointF MtsToPixs(PointF coordenate){
        return new PointF((float)((((coordenate.x * 100) / scale) * dpi) / 2.54),                   //resultado de pixeles  a metros
                          (float)((((coordenate.y * 100) / scale) * dpi) / 2.54));
    }

    public static double MtsToPixs(double meters){
        return (((meters * 100) / scale) * dpi) / 2.54;
    }

    public static float setSizeWorkAre(){
        PointF offsetMtrs = PixsToMts(offset);
        scale = 1;
        PointF screenInMts = PixsToMts(screenResolution);
//        float width = screenInMts.x * screenResolution.x;
//        float height = screenInMts.y * screenResolution.y;
        return ((areaSelected.x + offsetMtrs.x) / screenInMts.x );
    }

    public static float scaleValue(float value){
        return value / (getScale() / 122);
    }

//    double relacion = (2.54 / dpi) * 100;                                                         // relacion para pasar DPI a metros
//    float dimension = Math.round(Math.sqrt(Math.pow(endP.x - startP.x, 2) + (Math.pow(endP.y - startP.y, 2))));//distancia en PIXELES
//    coordenate.x = (float)(Math.round(relacion * coordenate.x) / 10.0); //resultado de pixeles  a metros
//    coordenate.y = (float)(Math.round(relacion * coordenate.y) / 10.0);
//    DecimalFormat f = new DecimalFormat("#.##");
//    String formattedmetro = f.format(metros);

//    (float)(Math.rint(((coordenate.x * dpi) / 2.54) * 100) / 100);

    public static int getDpi() {
        return dpi;
    }

    public static void setDpi(int dpi) {
        MyConvert.dpi = dpi;
    }

    public static float getScale() {
        return MyConvert.scale;
    }

    public static void setScale(float scale) {
        MyConvert.scale = scale;
    }

    public static PointF getScreenResolution() {
        return MyConvert.screenResolution;
    }

    public static void setScreenResolution(PointF screenResolution) {
        MyConvert.screenResolution = screenResolution;
    }

    public static PointF getAreaSelected() {
        return MyConvert.areaSelected;
    }

    public static void setAreaSelected(PointF areaSelected) {
        MyConvert.areaSelected = areaSelected;
    }

    public static PointF getOffset() {
        return MyConvert.offset;
    }

    public static void setOffset(int x, int y) {
        MyConvert.offset = new PointF(dpToPx(x), dpToPx(y));
    }

}
