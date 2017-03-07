package ipat.johanbayona.gca.ipat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ipat.johanbayona.gca.ipat.DataModels.Medida;
import ipat.johanbayona.gca.ipat.R;

/**
 * Created by JohanBayona on 20/10/2016.
 */

public class TableMedidas_Adapter extends ArrayAdapter<Medida> {

    Context mContext;
    int mLayoutResourceId;

    public TableMedidas_Adapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
    }

    public TableMedidas_Adapter(Context context, int resource, List<Medida> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final Medida currentItem = getItem(position);

        if (row == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            row = vi.inflate(R.layout.adapter_tablamedida_item, null);
        }

        Medida p = getItem(position);

        if (p != null) {
            final TextView txtIndex = (TextView) row.findViewById(R.id.TxtIndex);
            final TextView txtCoordX = (TextView) row.findViewById(R.id.TxtCoordX);
            final TextView txtCoordY = (TextView) row.findViewById(R.id.TxtCoordY);
            final TextView txtEvidencia = (TextView) row.findViewById(R.id.TxtEvidenciaId);
            final TextView txtDescription = (TextView) row.findViewById(R.id.TxtDescription);

            if (txtCoordX != null) txtIndex.setText("" + currentItem.getIndex());
            if (txtCoordX != null) txtCoordX.setText("" + currentItem.getX());
            if (txtCoordY != null) txtCoordY.setText("" + currentItem.getY());
            if (txtEvidencia != null) txtEvidencia.setText(currentItem.getEvidencia());
            if (txtDescription != null) txtDescription.setText(currentItem.getDescripcion());
        }

        return row;
    }
}
