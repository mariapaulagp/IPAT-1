package ipat.johanbayona.gca.ipat.Adapters;

import android.app.Activity;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.Evidence;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.R;

/**
 * Created by JohanBayona on 20/10/2016.
 */

public class MeasureTableAdapter extends ArrayAdapter<MeasureEvi> {

    private final Activity context;
    ArrayList<MeasureEvi> myMeasureEvi;

    public OnHeadlineSelectedListener mCallback;
    public interface OnHeadlineSelectedListener { public void onMeasureInDrawActiom(int position, boolean delete); }
    public void setCallback(OnHeadlineSelectedListener mCallback){ this.mCallback = mCallback; }

    public MeasureTableAdapter(Activity context, ArrayList<MeasureEvi> myMeasureEvi) {
        super(context, R.layout.adapter_tablamedida_item, myMeasureEvi);

        this.context = context;
        this.myMeasureEvi = myMeasureEvi;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_tablamedida_item, null, true);

        MeasureEvi currentItem = getItem(position);

        final TextView txtIndex =       (TextView)  rowView.findViewById(R.id.TxtIndex);
        final EditText txtCoordX =      (EditText)  rowView.findViewById(R.id.TxtCoordX);
        final EditText txtCoordY =      (EditText)  rowView.findViewById(R.id.TxtCoordY);
        final ImageView imgEvidence =   (ImageView) rowView.findViewById(R.id.ImgIcon);
        final TextView txtEvidence =    (TextView)  rowView.findViewById(R.id.TxtEvidenciaId);
        final TextView txtName =        (TextView)  rowView.findViewById(R.id.TxtCategory);
        final TextView txtDescription = (TextView)  rowView.findViewById(R.id.TxtDescription);


        if (currentItem != null) {
            int suma = currentItem.getmOrderIndex()+1;
            Evidence myEvidence = DataIpat.evidencetArray.get(currentItem.geteIndex());

            if (txtIndex != null) txtIndex.setText("" + suma );
            if (imgEvidence != null) imgEvidence.setImageDrawable(myEvidence.geteImage().getDrawable());
            if (txtEvidence != null) txtEvidence.setText("" + myEvidence.geteId());
            if (txtName != null) txtName.setText(myEvidence.geteCategory() + " " + (myEvidence.getcIndex() + 1));

            if (txtDescription != null) txtDescription.setText(currentItem.getmDescription() + " - " + currentItem.getmObservation());

            if (txtCoordX != null) {
                txtCoordX.setSelectAllOnFocus(true);
                txtCoordX.setText("" + currentItem.getmCoordenate().x);
                txtCoordX.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus)
                            if(!(setPosition(txtCoordX.getText().toString(), txtCoordY.getText().toString(), position))){
                                PointF coord = DataIpat.measureEviArray.get(position).getmCoordenate();
                                txtCoordX.setText("" + coord.x);
                            }
                    }
                });
            }

            if (txtCoordY != null){
                txtCoordY.setSelectAllOnFocus(true);
                txtCoordY.setText("" + currentItem.getmCoordenate().y);
                txtCoordY.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus)
                            if(!(setPosition(txtCoordX.getText().toString(), txtCoordY.getText().toString(), position))){
                                PointF coord = DataIpat.measureEviArray.get(position).getmCoordenate();
                                txtCoordY.setText("" + coord.y);
                            }
                    }
                });
            }
        }
        return rowView;
    }

    private boolean setPosition(String coordX, String coordY, int position){
        try {
            float X = Float.parseFloat(coordX);
            float Y = Float.parseFloat(coordY);
            PointF coord = DataIpat.measureEviArray.get(position).getmCoordenate();

            if (X != 0 && Y != 0 ) { // Valido que los x y y sean diferentes de cero
                if(X != coord.x || Y != coord.y) { // valida que el dato alla cambiado
                    for(MeasureEvi myMeasure: DataIpat.measureEviArray){  //
                        if (myMeasure.geteIndex() == DataIpat.measureEviArray.get(position).geteIndex()
                        && myMeasure.getmIndex() != DataIpat.measureEviArray.get(position).getmIndex()){
                            if(X == myMeasure.getmCoordenate().x && Y == myMeasure.getmCoordenate().y) {
                                Toast.makeText(getContext(), "Error no se permiten coordenadas iguales.", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                        }
                    }
                    DataIpat.measureEviArray.get(position).setmCoordenate(new PointF(X, Y));
                    mCallback.onMeasureInDrawActiom(position, false); // true for delete
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
