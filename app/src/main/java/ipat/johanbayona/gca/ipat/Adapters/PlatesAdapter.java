package ipat.johanbayona.gca.ipat.Adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ipat.johanbayona.gca.ipat.R;


public class PlatesAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private String[] nameItem;
    private TypedArray iconsItem;
    private int list;

    public PlatesAdapter(Activity context, String[] nameItem, TypedArray iconsItem, int list) {
        super(context, R.layout.adapter_plates, nameItem);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.nameItem = nameItem;
        this.iconsItem = iconsItem;
        this.list = list;
    }

    public View getView(int posicion, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_plates,null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textoItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ImgItem);

        try {
            txtTitle.setText(nameItem[posicion]);
            imageView.setImageDrawable(iconsItem.getDrawable(posicion));
        }catch(Exception ex){}
        return rowView;
    }
}