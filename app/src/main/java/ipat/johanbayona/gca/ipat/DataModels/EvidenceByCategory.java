package ipat.johanbayona.gca.ipat.DataModels;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
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

public class EvidenceByCategory {

    private int eIndex;                                 //Index de la tabla a la que pertenece (Vehiculo, victima, evidencia genera;)
    private int cIndex;                                 // el indice da la tabla segun su categoria

    public EvidenceByCategory(int eIndex, int cIndex) {
        this.eIndex = eIndex;
        this.cIndex = cIndex;
    }

    public int geteIndex() {
        return eIndex;
    }

    public void seteIndex(int eIndex) {
        this.eIndex = eIndex;
    }

    public int getcIndex() {
        return cIndex;
    }

    public void setcIndex(int cIndex) {
        this.cIndex = cIndex;
    }
}
