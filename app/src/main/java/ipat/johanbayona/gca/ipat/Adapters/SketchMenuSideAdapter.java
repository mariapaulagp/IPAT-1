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


public class SketchMenuSideAdapter extends ArrayAdapter<String> {
    private int selectedPosition = -50;
    private final Activity context;
    private String[] nameItem;
    private TypedArray iconsItem;
    private int list;

    public SketchMenuSideAdapter(Activity context, String[] nameItem, TypedArray iconsItem, int list) {
        super(context, R.layout.adapter_sketchmenuside, nameItem);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.nameItem = nameItem;
        this.iconsItem = iconsItem;
        this.list = list;
    }

    public View getView(int posicion, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_sketchmenuside,null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textoItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ImgItem);

        try {
            txtTitle.setText(nameItem[posicion]);
            imageView.setImageDrawable(iconsItem.getDrawable(posicion));
            if(list == 2) txtTitle.setTextColor(Color.parseColor("#ffffff"));
        }catch(Exception ex){}

        if (posicion == selectedPosition) {
            rowView.setBackground(context.getResources().getDrawable(R.drawable.fondoselected));
        } else {
            rowView.setBackground(context.getResources().getDrawable(R.drawable.shapetransparent));
        }

        return rowView;
    }

    public void setSelectedPosition(int position)
    {
        selectedPosition = position;
    }
}