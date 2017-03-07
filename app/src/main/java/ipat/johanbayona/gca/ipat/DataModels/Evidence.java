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

import java.util.ArrayList;
import java.util.List;

import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.R;

/**
 * Created by JohanBayona on 20/10/2016.
 */

public class Evidence {

    private int eIndex;                                 //Index de la tabla a la que pertenece (Vehiculo, victima, evidencia genera;)
    private int cIndex;                                 // el indice da la tabla segun su categoria
    private DataIpat.EvidenceCategory eCategory;        // Categoria a la que pertenece vehiculo victima genertal
    private TextView eTag;                              // Etiqueta con numero para poner en pantalla
    private PointF eCoordenate = new PointF(0,0);                         // Coordenadas donde se debe poner la etiqueta
    private ImageView eImage;                           // Imagen de la evidencia
    private DataIpat.EvidenceType eType;                // Tipo de evidencia o subcategotya
    private String eId = "";                                 // Id placas cedula o identificador puesto a la evidencia
    private List<Integer> mIndexArray;              // array de index de Maasure que corresponden a la evidencia

    public Evidence(int eIndex, DataIpat.EvidenceCategory eCategory, ImageView eImage, DataIpat.EvidenceType eType, Context myContext) {
        this.eIndex = eIndex;
        this.eCategory = eCategory;
        this.eImage = eImage;
        this.eType = eType;
        this.eId = "";

        TextView newEvidenceTag = new TextView(myContext);
        newEvidenceTag.setLayoutParams(new DrawerLayout.LayoutParams(30, 30));
        newEvidenceTag.setTextSize(20);
        newEvidenceTag.setTextColor(Color.BLACK);
        newEvidenceTag.setTypeface(Typeface.DEFAULT_BOLD);
        newEvidenceTag.setBackgroundResource(R.drawable.evidencetag);
        newEvidenceTag.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        newEvidenceTag.setText("" + eIndex);

        this.eTag = newEvidenceTag;
        mIndexArray = new ArrayList<Integer>();
    }

    public int geteIndex() {
        return eIndex;
    }

    public void seteIndex(int eIndex) {
        this.eIndex = eIndex;
        eTag.setText("" + this.eIndex);
    }

    public int getcIndex() {
        return cIndex;
    }

    public void setcIndex(int cIndex) {
        this.cIndex = cIndex;
    }

    public DataIpat.EvidenceCategory geteCategory() {
        return eCategory;
    }

    public void seteCategory(DataIpat.EvidenceCategory eCategory) {
        this.eCategory = eCategory;
    }

    public TextView geteTag() {
        return eTag;
    }

    public PointF geteCoordenate() {
        return eCoordenate;
    }

    public void seteCoordenate(PointF eCoordenate) {
        this.eCoordenate = eCoordenate;
    }

    public ImageView geteImage() {
        return eImage;
    }

    public void seteImage(ImageView eImage) {
        this.eImage = eImage;
    }

    public DataIpat.EvidenceType geteType() {
        return eType;
    }

    public void seteType(DataIpat.EvidenceType eType) {
        this.eType = eType;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public List<Integer> getmIndexArray() {
        return mIndexArray;
    }

    public void setmIndexArray(List<Integer> mIndexArray) {
        this.mIndexArray = mIndexArray;
    }

    /////////////// Adicionales


}
