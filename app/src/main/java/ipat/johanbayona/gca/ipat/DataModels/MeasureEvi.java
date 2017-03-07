package ipat.johanbayona.gca.ipat.DataModels;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.R;

/**
 * Created by JohanBayona on 20/10/2016.
 */

public class MeasureEvi {

    private int mIndex;                             // Indeice de a tabla de medidas
    private int mOrderIndex;
    private int eIndex;                             // Indeice de la Evidencia a la que pertenece
    private TextView mTag;                          // Etiqueta con numero para poner en el pantalla
    private PointF mCoordenate = new PointF(0,0);                     // Coordenadas donde se debe poner la etiqueta
    private String mDescription = "";                    // Tipo de medida
    private String mObservation = "";                    // Detalle escrito sobre la medida

    public MeasureEvi(int mIndex, int eIndex, String mDescription, Context myContext) {
        this.mIndex = this.mOrderIndex = mIndex;
        this.eIndex = eIndex;
        this.mDescription = mDescription;

        TextView newMeasureTag = new TextView(myContext);
        newMeasureTag.setLayoutParams(new DrawerLayout.LayoutParams(25, 25));
        newMeasureTag.setTextSize(15);
        newMeasureTag.setTextColor(Color.WHITE);
        newMeasureTag.setTypeface(Typeface.DEFAULT_BOLD);
        newMeasureTag.setBackgroundResource(R.drawable.measuretag);
        newMeasureTag.setPadding(0, 1, 0, 0);
        newMeasureTag.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        newMeasureTag.setText("" + mIndex);

        this.mTag = newMeasureTag;
    }

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
        mTag.setText("" + this.mIndex);
    }

    public int geteIndex() {
        return eIndex;
    }

    public void seteIndex(int eIndex) {
        this.eIndex = eIndex;
    }

    public int getmOrderIndex() {
        return mOrderIndex;
    }

    public void setmOrderIndex(int mOrderIndex) {
        this.mOrderIndex = mOrderIndex;
    }

    public TextView getmTag() {
        return mTag;
    }

    public PointF getmCoordenate() {
        return mCoordenate;
    }

    public void setmCoordenate(PointF mCoordenate) {
        this.mCoordenate = mCoordenate;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmObservation() {
        return mObservation;
    }

    public void setmObservation(String mObservation) {
        this.mObservation = mObservation;
    }

    //////////// Aficionales

    public Evidence getEvidence(){
        return DataIpat.evidencetArray.get(eIndex);
    }
}
