package ipat.johanbayona.gca.ipat.Sketch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ipat.johanbayona.gca.ipat.Adapters.MeasureTableAdapter;
import ipat.johanbayona.gca.ipat.DataModels.ObjectDraw;
import ipat.johanbayona.gca.ipat.MyConvert;
import ipat.johanbayona.gca.ipat.R;

/**
 * Created by Valentine on 10/27/2015.
 */
public class MeasureTool extends View {
    /////////////////////

    private boolean flagActive = false;
    private boolean flagDraw = false;

    public boolean isFlagActive() {
        return flagActive;
    }
    public void setFlagActive(boolean flagActive) {
        this.flagActive = flagActive;
        selectedObjectIndex = -1;
        invalidate();
    }
    public boolean isFlagDraw() {
        return flagDraw;
    }
    public void setFlagDraw(boolean flagDraw) {
        this.flagDraw = flagDraw;
    }

    private PointF startP = new PointF(0, 0);                                                        //variable para el punto inicial
    private PointF endP = new PointF(0, 0);                                                          //variable para el punto final

    private ArrayList<ObjectDraw> myObjects = new ArrayList<>();                                    // Arreglo que contiene todas las lineas

    //_______________________


    PointF distancia;

    //_______________

    //boolean moveObjectFlag = false;// bandera para determinar si la acion es de movimiento o de dibujo
    boolean startMoveGestureFlag = false;
    PointF lastPoint = new PointF(0, 0);                                                             // variable que almacena las cordenadas inmediatamente anteriores que se utiliza para calcular el desplazamiento
    PointF[] misPoint;
    int selectedObjectIndex = -1;                                                                   // variable para almacenar el index del objeto actualmente seleccionado
    int selectedPointIndex = -1; // 0 punto de inicio 1 pundo del medio y 2 punto final
    float margenContact = 20f;                                                                      // espacio adicional cerca a la linea para determinar si se selecciono, por que la linea es muy delgada y si no seria muy dificil
    int margenSelection = 20;
    int[] id_linea;// Variable de texto que sirve como testigo - esto es solo en desarrollo
    ////////////////////

    Object state;
    Object state2;
    private Paint drawPaint, drawPaint2, drawPaint3;//defines how to draw

    public MeasureTool.OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener { public void onSelectedMeasure(boolean state); }
    public void setCallback(MeasureTool.OnHeadlineSelectedListener mCallback){ this.mCallback = mCallback; }

    //Bitmap arrowImg;
    public MeasureTool(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        drawPaint = new Paint();
        drawPaint.setColor(Color.BLUE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(1);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        drawPaint2 = new Paint();
        drawPaint2.setColor(Color.GRAY);
        drawPaint2.setTextSize(15);
        drawPaint2.setAntiAlias(true);
        drawPaint2.setStrokeWidth(2);
        drawPaint2.setStyle(Paint.Style.FILL);
        drawPaint2.setStrokeJoin(Paint.Join.ROUND);
        drawPaint2.setStrokeCap(Paint.Cap.ROUND);

        drawPaint3 = new Paint();
        drawPaint3.setColor(Color.WHITE);
        drawPaint3.setStrokeWidth(20);
        drawPaint3.setDither(true);
        drawPaint3.setStyle(Paint.Style.FILL_AND_STROKE);
        drawPaint3.setStrokeJoin(Paint.Join.ROUND);
        drawPaint3.setStrokeCap(Paint.Cap.ROUND);
        //drawPaint3.setPathEffect(new CornerPathEffect(100));
        drawPaint3.setAntiAlias(true);

//        arrowImg = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow);
    }

    private void drawArrow(Canvas canvas, ObjectDraw myObject) {
        int h = 12; // Constante distancia de la flecha a la linea
        // -------------- Calculo Flecha Inicial
        float phi = (float) Math.atan2(myObject.getEndPoint().y - myObject.getStartPoint().y, myObject.getEndPoint().x - myObject.getStartPoint().x);

        float tip1angle = phi - (float) Math.PI / 17; // -45°
        float tip2angle = phi + (float) Math.PI / 17; // +45°

        float x3 = (float) (myObject.getEndPoint().x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
        float x4 = (float) (myObject.getEndPoint().x - h * Math.cos(tip2angle));
        float y3 = (float) (myObject.getEndPoint().y - h * Math.sin(tip1angle));
        float y4 = (float) (myObject.getEndPoint().y - h * Math.sin(tip2angle));

        //__________ Calculo flecha final ----
        float phi2 = (float) Math.atan2(myObject.getStartPoint().y - myObject.getEndPoint().y, myObject.getStartPoint().x - myObject.getEndPoint().x); // substitute x1, x2, y1, y2 as needed

        float tip1angle2 = phi2 - (float) Math.PI / 17; // -45°
        float tip2angle2 = phi2 + (float) Math.PI / 17; // +45°

        float x5 = (float) (myObject.getStartPoint().x - h * Math.cos(tip1angle2)); // substitute h here and for the following 3 places
        float x6 = (float) (myObject.getStartPoint().x - h * Math.cos(tip2angle2));
        float y5 = (float) (myObject.getStartPoint().y - h * Math.sin(tip1angle2));
        float y6 = (float) (myObject.getStartPoint().y - h * Math.sin(tip2angle2));

        //______ PINTA ___________

        canvas.drawLine(myObject.getEndPoint().x, myObject.getEndPoint().y, x4, y4, drawPaint2);//segmento de flecha Ini
        canvas.drawLine(myObject.getEndPoint().x, myObject.getEndPoint().y, x3, y3, drawPaint2);//segmento de flecha Ini
        //__________________________
        canvas.drawLine(myObject.getStartPoint().x, myObject.getStartPoint().y, x5, y5, drawPaint2);//segmento de flecha fin
        canvas.drawLine(myObject.getStartPoint().x, myObject.getStartPoint().y, x6, y6, drawPaint2);//segmento de flecha fin
    }

//    private void drawImgMoveIcon(Canvas canvas) {
//        float widthImg = (arrowImg.getWidth() / 2);
//        float heghtImg = (arrowImg.getHeight() / 2);
//        int h = 30; // Constante distancia de la flecha a la linea
//
//        // -------------- Calculo Flecha Inicial
//        float phi = (float) Math.atan2(myObjects.get(selectedObjectIndex).getHalfPoint().y - myObjects.get(selectedObjectIndex).getStartPoint().y, myObjects.get(selectedObjectIndex).getHalfPoint().x - myObjects.get(selectedObjectIndex).getStartPoint().x);
//
//        float tip1angle = phi - (float) Math.PI / 2; // -45°
//
//        float x3 = (float) (myObjects.get(selectedObjectIndex).getHalfPoint().x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
//        float y3 = (float) (myObjects.get(selectedObjectIndex).getHalfPoint().y - h * Math.sin(tip1angle));
//
//        //
//        canvas.drawBitmap(arrowImg, x3 - widthImg, y3 - heghtImg, null);
//
//    }

    private void drawTxtMeasure(Canvas canvas, ObjectDraw myObject) {
        int h = 25; // Constante distancia de la flecha a la linea
        /*float phi = (float) Math.atan2(myObject.getHalfPoint().y - myObject.getStartPoint().y, myObject.getHalfPoint().x - myObject.getStartPoint().x);
        float phi2 = (float) Math.atan2(myObject.getEndPoint().y - myObject.getHalfPoint().y, myObject.getEndPoint().x - myObject.getHalfPoint().x);*/
        float phi = (float) Math.atan2(myObject.getStartPoint().y - myObject.getHalfPoint().y, myObject.getStartPoint().x - myObject.getHalfPoint().x);
        float phi2 = (float) Math.atan2(myObject.getEndPoint().y - myObject.getHalfPoint().y, myObject.getEndPoint().x - myObject.getHalfPoint().x);

        float iniAngle = phi + (float) Math.PI; // 90°
        float finAngle = phi2 - (float) Math.PI; // 90°
        float Xini = (float) (myObject.getHalfPoint().x - h * Math.cos(iniAngle)); // substitute h here and for the following 3 places
        float Yini = (float) (myObject.getHalfPoint().y - h * Math.sin(iniAngle));
        float Xfin = (float) (myObject.getHalfPoint().x - h * Math.cos(finAngle)); // substitute h here and for the following 3 places
        float yfin = (float) (myObject.getHalfPoint().y - h * Math.sin(finAngle));

        Path myLineBackTxt = new Path();
        myLineBackTxt.moveTo(0, 0);
        myLineBackTxt.lineTo(300, 200);
        canvas.drawPath(myLineBackTxt, drawPaint2);

        Path myLine = new Path();
        myLine.moveTo(myObject.getStartPoint().x, myObject.getStartPoint().y);
        myLine.lineTo(myObject.getEndPoint().x, myObject.getEndPoint().y);

//        float distance = (float) (Math.sqrt(Math.pow(myObject.getEndPoint().x - myObject.getStartPoint().x, 2) + (Math.pow(myObject.getEndPoint().y - myObject.getStartPoint().y, 2))));
        canvas.drawLine(Xini, Yini, Xfin, yfin, drawPaint3);                                         //Pinta el fondo detras del texto
//        DisplayMetrics metrics = getResources().getDisplayMetrics();                                 // clase que permite obttenr DPI
//        int dpi = metrics.densityDpi*10;                                                               //obitien DPI del dispositivo en varible entera
//        double relacion = (2.54 / dpi) * 100;                                                        //relacion para pasar DPI a metros
//        double dimension = myObject.getMeasure();                                                     //distancia de la recta
//        double metros = Math.round(relacion * dimension) / 10.0;                                     //resultado de pixeles  a  metros
        DecimalFormat f = new DecimalFormat("#.##");
        float dimension =  (float) (Math.sqrt(Math.pow(myObject.getEndPoint().x - myObject.getStartPoint().x, 2) + (Math.pow(myObject.getEndPoint().y - myObject.getStartPoint().y, 2))));
        canvas.drawTextOnPath((f.format(MyConvert.PixsToMts(dimension)) + " m"), myLine, (dimension / 2) - 18, 5, drawPaint2);

        //canvas.drawTextOnPath(formattedmetro+"m", myLine, (distance / 2) - 18, 5, drawPaint2);
    }

    private void drawText2(Canvas canvas) {
        int h = 20;                                                                                         // Constante distancia de la flecha a la linea
        PointF halfPoint = new PointF(Math.abs(startP.x + endP.x) / 2, Math.abs(startP.y + endP.y) / 2);    // Calcula el punto de la mitad
        float phi = (float) Math.atan2(halfPoint.y - startP.y, halfPoint.x - startP.x);                     // calcula el angulo de la linea

        float tip1angle = phi + (float) Math.PI / 2; // -45°
        float x3 = (float) (halfPoint.x - h * Math.cos(tip1angle));                                         // la coordenada X del punto
        float y3 = (float) (halfPoint.y - h * Math.sin(tip1angle));                                         // la coordenada Y del punto

        DecimalFormat f = new DecimalFormat("#.##");
        float dimension = Math.round(Math.sqrt(Math.pow(endP.x - startP.x, 2) + (Math.pow(endP.y - startP.y, 2))));  //distancia en PIXELES
        canvas.drawText((f.format(MyConvert.PixsToMts(dimension)) + " m"), x3 - 15, y3, drawPaint2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);

        for (ObjectDraw l : myObjects) { // recorro el arreglo para pintar todas las lineas que esten en  myObjects
            if (l.getEndPoint() != null && l.getStartPoint() != null) { // valido que el punto inicial y el final cintengan datos
                canvas.drawLine(l.getStartPoint().x, l.getStartPoint().y, l.getEndPoint().x, l.getEndPoint().y, drawPaint2); // Pinto el objeto
                drawTxtMeasure(canvas, l);
                drawArrow(canvas, l);
            }
        }

        Path myLineBackTxt = new Path();
        myLineBackTxt.moveTo(300, 2000);
        // myLineBackTxt.lineTo(300, 200);
        canvas.drawPath(myLineBackTxt, drawPaint2);

        if (selectedObjectIndex > -1) {
            // canvas.drawCircle(myObjects.get(selectedObjectIndex).getStartPoint().x, myObjects.get(selectedObjectIndex).getStartPoint().y, 10, drawPaint3);
            // canvas.drawCircle(myObjects.get(selectedObjectIndex).getEndPoint().x, myObjects.get(selectedObjectIndex).getEndPoint().y, 10, drawPaint3);

            //drawImgMoveIcon(canvas);
            PointF myStartPoint = calStartPoint();
            canvas.drawCircle(myStartPoint.x, myStartPoint.y, 20, drawPaint);
            PointF myEndPoint = calEndtPoint();
            canvas.drawCircle(myEndPoint.x, myEndPoint.y, 20, drawPaint);
        } else if (startMoveGestureFlag) {
            canvas.drawLine(startP.x, startP.y, endP.x, endP.y, drawPaint);
            drawText2(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(flagActive) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // limpiamos bandera

                    lastPoint.x = startP.x = event.getX();                                              // actualizo las variables con las coordenadas de inicio
                    lastPoint.y = startP.y = event.getY();

                    if (selectedObjectIndex != -1) {
                        ObjectDraw obj = new ObjectDraw();
                        obj.setSeleccion(selectedObjectIndex);
                        myObjects.add(obj);

                        getSelectionPoint(new PointF(event.getX(), event.getY()));

                        selectedNotify(true);
                    }else selectedNotify(false);
                    break;
                case MotionEvent.ACTION_MOVE:
                    startMoveGestureFlag = true;                                                        // True para saber que se movio
                    endP.x = event.getX();                                                              // Actualizo las variable de punto final con el dato actual
                    endP.y = event.getY();

                    if (selectedObjectIndex != -1){
                        moveObject();
                        selectedNotify(true);
                    }else selectedNotify(false);

                    lastPoint.x = event.getX();                                                         // Actualizo la variable del punto inmediatamente aterior
                    lastPoint.y = event.getY();
                    invalidate();

                    break;
                case MotionEvent.ACTION_UP:
                    if (selectedObjectIndex > -1) {
                        if (startMoveGestureFlag) {
                        } else {
                            selectedObjectIndex = touchValidate(event);
                            if (selectedObjectIndex < 0) {
                                selectedPointIndex = -1;
                                selectedObjectIndex = -1;
                                selectedNotify(false);
                            } else selectedNotify(true);
                            invalidate();
                        }
                    } else {
                        if (startMoveGestureFlag) {
                            if (isLineDraw(new PointF(startP.x, startP.y), new PointF(endP.x, endP.y)))
                                addObject();
                        } else if (myObjects.size() > 0)
                            selectedObjectIndex = touchValidate(event);                    // valido que tenga por lo menos una liea en caso contrario es que es la primera que se agrega

                        if (selectedObjectIndex > -1) {
                            invalidate();
                            selectedNotify(true);
                        }else selectedNotify(false);
                    }
                    startMoveGestureFlag = false;

                    break;
                default:
                    return false;
            }
            return true;
        }else return false;
    }


    private void selectedNotify(boolean state){
        mCallback.onSelectedMeasure(state);
    }

    public void deleteMeasure(){
        if (selectedObjectIndex > -1) {
            myObjects.remove(selectedObjectIndex);
            selectedObjectIndex = -1;
            selectedNotify(false);
            invalidate();
        }
    }

    private void getMeasureObject(ObjectDraw myObject) {  // Metodo para Calcular la medida de la linea
        myObject.setMeasure(Math.round(Math.sqrt(Math.pow(myObject.getEndPoint().x - myObject.getStartPoint().x, 2) + (Math.pow(myObject.getEndPoint().y - myObject.getStartPoint().y, 2)))));
    }

    private PointF calcHalfPoint() {
//        float widthImg = (arrowImg.getWidth() / 2);
//        float heghtImg = (arrowImg.getHeight() / nhyuy 2);
        int h = 0; // Constante distancia de la flecha a la linea
        float phi = (float) Math.atan2(myObjects.get(selectedObjectIndex).getHalfPoint().y - myObjects.get(selectedObjectIndex).getStartPoint().y, myObjects.get(selectedObjectIndex).getHalfPoint().x - myObjects.get(selectedObjectIndex).getStartPoint().x);

        float tip1angle = phi - (float) Math.PI / 2; // -45°
        float x3 = (float) (myObjects.get(selectedObjectIndex).getHalfPoint().x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
        float y3 = (float) (myObjects.get(selectedObjectIndex).getHalfPoint().y - h * Math.sin(tip1angle));

        return new PointF(x3, y3);
    }

    private PointF calStartPoint() {
        int h = 30; // Constante distancia de la flecha a la linea
        float phi = (float) Math.atan2(myObjects.get(selectedObjectIndex).getStartPoint().y - myObjects.get(selectedObjectIndex).getEndPoint().y, myObjects.get(selectedObjectIndex).getStartPoint().x - myObjects.get(selectedObjectIndex).getEndPoint().x);

        float tip1angle = phi - (float) Math.PI; // -90
        float x3 = (float) (myObjects.get(selectedObjectIndex).getStartPoint().x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
        float y3 = (float) (myObjects.get(selectedObjectIndex).getStartPoint().y - h * Math.sin(tip1angle));

        return new PointF(x3, y3);
    }

    private PointF calEndtPoint() {
        int h = 30; // Constante distancia de la flecha a la linea
        float phi = (float) Math.atan2(myObjects.get(selectedObjectIndex).getEndPoint().y - myObjects.get(selectedObjectIndex).getStartPoint().y, myObjects.get(selectedObjectIndex).getEndPoint().x - myObjects.get(selectedObjectIndex).getStartPoint().x);

        float tip1angle = phi - (float) Math.PI; // -90
        float x3 = (float) (myObjects.get(selectedObjectIndex).getEndPoint().x - h * Math.cos(tip1angle)); // substitute h here and for the following 3 places
        float y3 = (float) (myObjects.get(selectedObjectIndex).getEndPoint().y - h * Math.sin(tip1angle));

        return new PointF(x3, y3);
    }

    private void getSelectionPoint(PointF touchPoint) {
        misPoint = new PointF[3];
        misPoint[0] = calStartPoint();
        misPoint[1] = calcHalfPoint();
        misPoint[2] = calEndtPoint();
        for (int i = 0; i < 3; i++) {
            if ((Math.sqrt(Math.pow(touchPoint.x - misPoint[i].x, 2) + (Math.pow(touchPoint.y - misPoint[i].y, 2)))) < 40) {
                selectedPointIndex = i;
                distancia = misPoint[1];
                return;
            }
        }
        selectedPointIndex = -1;
    }

    private void moveObject() {
        switch (selectedPointIndex) {
            case 0: // mueve y calcula el punto inicial
                myObjects.get(selectedObjectIndex).setStartPoint(
                        new PointF((myObjects.get(selectedObjectIndex).getStartPoint().x + endP.x - lastPoint.x),
                                (myObjects.get(selectedObjectIndex).getStartPoint().y += endP.y - lastPoint.y)));
                getMeasureObject(myObjects.get(selectedObjectIndex));  // Calcula la medida de la linea

                break;
            case 1: // Mueve Todof EL OBJETO
                myObjects.get(selectedObjectIndex).setStartPoint(
                        new PointF((myObjects.get(selectedObjectIndex).getStartPoint().x + endP.x - lastPoint.x),
                                (myObjects.get(selectedObjectIndex).getStartPoint().y += endP.y - lastPoint.y)));

                myObjects.get(selectedObjectIndex).setEndPoint(
                        new PointF((myObjects.get(selectedObjectIndex).getEndPoint().x + endP.x - lastPoint.x),
                                (myObjects.get(selectedObjectIndex).getEndPoint().y += endP.y - lastPoint.y)));
                break;
            case 2: // Mueve Y CALCULA el punto final
                myObjects.get(selectedObjectIndex).setEndPoint(
                        new PointF((myObjects.get(selectedObjectIndex).getEndPoint().x + endP.x - lastPoint.x),
                                (myObjects.get(selectedObjectIndex).getEndPoint().y += endP.y - lastPoint.y)));
                getMeasureObject(myObjects.get(selectedObjectIndex));  // Calcula la medida de la linea
                break;
        }
    }

    private void addObject() {
        ObjectDraw newLine = new ObjectDraw();                                                      // creo un nuevo objeto
        newLine.setStartPoint(new PointF(startP.x, startP.y));                                      // registro punto inicial a mi nuevo objeto
        newLine.setEndPoint(new PointF(endP.x, endP.y));                                            // registro punto final a mi uevo objeto
        getMeasureObject(newLine);
        myObjects.add(newLine);
        invalidate();
    }


    ///////////////////////////////////////////////  Validaciones  //////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isLineDraw(PointF startPoint, PointF EndPoint) {                 // Metodo retorna true si la intencion es pintar y false si la intencion es seleccionar
        if ((Math.sqrt(Math.pow((EndPoint.x - startPoint.x), 2) + Math.pow((EndPoint.y - startPoint.y), 2))) > margenSelection) // valido la distancia entre el punto oinicial y final y si es mayor al margen digo que la intencion es pintar
            return true;    // si la intencion es pintar retorno true
        return false;       // si la intencion es seleccionar retorno false
    }

    private int touchValidate(MotionEvent event) {
        // matodo para validar la seleccion de un objeto devielvo -1 si no se esta seleccionando ninguno en caSO CONTRARIO DEVUELVO EL INDEX CORRESPONDIENTE AL OBHETO seleccionado
        for (int index = myObjects.size() - 1; index >= 0; index--) { // recorro el arreglo de objetos de atras hacia adelante

            if (isTouchLine(myObjects.get(index).getStartPoint(), myObjects.get(index).getEndPoint(), new PointF(event.getX(), event.getY()))) // valido cada objeto si es el objeto seleccionado
                return index; // si es seleccionado retorno el index que corresponde al objeto

        }
        return -1; // si ningun objeto se selecciono retorno -1
    }

    public boolean isTouchLine(PointF startPoint, PointF endPoint, PointF point) { // // valida si el touch actual esta sobre o muy cerca del objeto
        PointF leftPoint = startPoint;
        PointF rightPoint = endPoint;

        // primera validacion
        if (startPoint.x > endPoint.x) {    // valido si el punto inicial esta a la derecha para compenzarlo
            leftPoint = endPoint;           // si estaba a la derecha lo paso a la izquierda
            rightPoint = startPoint;
        }

        // segunda validacion
        if (point.x + margenContact < leftPoint.x || rightPoint.x < point.x - margenContact)
            return false; // valido si esta por fuera de las coordenadas en x retono false NO SELECCION
        else if (point.y + margenContact < Math.min(leftPoint.y, rightPoint.y) || Math.max(leftPoint.y, rightPoint.y) < point.y - margenContact)
            return false; // valido si esta por fuera de coordenadas en y retorno false NO SELECCION

        double deltaX = rightPoint.x - leftPoint.x;
        double deltaY = rightPoint.y - leftPoint.y;

        // tercera validacion
        if (deltaX == 0 || deltaY == 0) {
            return true;
        } // valido si esta totalmente vertical o horizotal retorno true SI SELECION

        // cuarta validacion valido la distancia del punto al objeto
        double normalLength = Math.sqrt((rightPoint.x - leftPoint.x) * (rightPoint.x - leftPoint.x) + (rightPoint.y - leftPoint.y) * (rightPoint.y - leftPoint.y)); // calculo el punto de interseccion con el objeto a 90°
        double res = Math.abs((point.x - leftPoint.x) * (rightPoint.y - leftPoint.y) - (point.y - leftPoint.y) * (rightPoint.x - leftPoint.x)) / normalLength;          // calculo la distancia del touch al punto de interseccion
        if (res <= margenContact)
            return true; // si esta lo suficientemente cerca segun el margen de contacto retorno true SI SELECCIONO

        return false; // en caso de no entrar en ninguna validacion anterior retorno false NO SELECCION
    }


    ///////////////////////////////////////////////  Acciones  //////////////////////////////////////////////////////////////////////////////////////////////


    public void eraseAll() {        // borra todas las lineas y deja el camvas en blanco
        selectedObjectIndex = -1;
        selectedNotify(false);
        //moveObjectFlag = false;
        myObjects.clear();
        startP = new PointF(0, 0);
        endP = new PointF(0, 0);
        invalidate();
    }

    public void setErase(boolean isErase) { // NO se esta usando pero debe implementarse

    }

    public void onClickUndo() {
        if (selectedObjectIndex > -1) {
            myObjects.remove(selectedObjectIndex);
            selectedObjectIndex = -1;
            selectedNotify(false);
            invalidate();
        }
    }

    public void onClickRedo() {

    }

    public void setBrushSize(float newSize) {

    }

    public void setLastBrushSize(float lastSize) {

    }

    public float getLastBrushSize() {
        return 0;
    }
}
