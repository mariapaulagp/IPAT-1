package ipat.johanbayona.gca.ipat.Sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ipat.johanbayona.gca.ipat.Adapters.MeasureTableAdapter;
import ipat.johanbayona.gca.ipat.Adapters.SketchMenuSideAdapter;
import ipat.johanbayona.gca.ipat.Adapters.EvidenceTableAdapter;
import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.MyConvert;
import ipat.johanbayona.gca.ipat.R;

public class SketchTableActivity extends Fragment implements View.OnTouchListener{

    ImageView btnOrdenate;

    ImageView btnPR, btnPA, btnDeleteMeasure, btnEditMeasure, btnAddMeasure, btnUpMeasure, btnDownMeasure;
    Animation ani_BtnSelected, ani_CapaTitle;
    TextView txtPrId, txtPrDescription,
             txtPaId, txtPaDescription, txtPaCoordX, txtPaCoordY;
    ListView tblMedidas;
//    EvidenceTableAdapter adapterMedidas;
    RelativeLayout rlyContent;
    View view;

    int itemSelected = -1;
//    public OnHeadlineSelectedListener mCallback;
//
//    // La actividad contenedora debe implementar esta interfaz
//    public interface OnHeadlineSelectedListener {
//        public void onNewMeasureInDraw(MeasureTag measureTag, int state);
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // Nos aseguramos de que la actividad contenedora haya implementado la
//        // interfaz de retrollamada. Si no, lanzamos una excepción
//        try {
//            mCallback = (OnHeadlineSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " debe implementar OnHeadlineSelectedListener");
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.activity_sketchtable, container, false);

        rlyContent = (RelativeLayout) view.findViewById(R.id.RlyContent);
        rlyContent.setOnTouchListener(this); // touch down quita teclado

        btnOrdenate = (ImageView) view.findViewById(R.id.BtnOrdenate);
        btnOrdenate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdenaEvidencias();
            }
        });

        btnPR = (ImageView) view.findViewById(R.id.BtnPR);
        btnPR.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(0); } });
        btnPA = (ImageView) view.findViewById(R.id.BtnPA);
        btnPA.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(1); } });
        btnDeleteMeasure = (ImageView) view.findViewById(R.id.BtnDeleteMeasure);
        btnDeleteMeasure.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(2); } });
        btnEditMeasure = (ImageView) view.findViewById(R.id.BtnEditMeasure);
        btnEditMeasure.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(3); } });
        btnAddMeasure = (ImageView) view.findViewById(R.id.BtnAddMeasure);
        btnAddMeasure.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(4); } });
        btnUpMeasure = (ImageView) view.findViewById(R.id.BtnUpMeasure);
        btnUpMeasure.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(5); } });
        btnDownMeasure = (ImageView) view.findViewById(R.id.BtnDownMeasure);
        btnDownMeasure.setOnClickListener( new View.OnClickListener() {
            public void onClick( View v){ SelectedAction(6); } });

        showBtnTable(false);

        txtPaId = (TextView) view.findViewById(R.id.TxtPaId);
        txtPaDescription = (TextView) view.findViewById(R.id.TxtPaDescription);
        txtPaCoordX = (TextView) view.findViewById(R.id.TxtPaCoordX);
        txtPaCoordY = (TextView) view.findViewById(R.id.TxtPaCoordY);
        txtPrId = (TextView) view.findViewById(R.id.TxtPrId);
        txtPrDescription = (TextView) view.findViewById(R.id.TxtPrDescription);


        ani_BtnSelected = AnimationUtils.loadAnimation (view.getContext(), R.anim.ani_btnselected);
        ani_CapaTitle = AnimationUtils.loadAnimation (view.getContext(), R.anim.ani_capaselectedtitle);

        tblMedidas = (ListView) view.findViewById(R.id.TblMedidas);
        tblMedidas.setItemsCanFocus(true);
        tblMedidas.setAdapter(DataIpat.adapterMeasureEvi);
        tblMedidas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        tblMedidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
//                tblMedidas.setItemChecked(position, true);
//                adapterMedidas.notifyDataSetChanged();
//
//                if(itemSelected > -1) {
//                    tblMedidas.getChildAt(itemSelected).setBackgroundResource(0);
//                    //tblMedidas.getChildAt(itemSelected).setFocusable(false);
//                    showBtnTable(false);
//                }
//                if(itemSelected == position) {
//                    view.setBackgroundResource(0);
//                    showBtnTable(false);
//                    itemSelected = -1;
//                }
//                else {
//                    view.setBackgroundResource(R.drawable.fondoazul);
//                    //view.setFocusable(true);
//                    showBtnTable(true);
//                    itemSelected = position;
//                }
//            }
//        });

        //tblMedidas.setOnTouchListener(this);  // touch down quita teclado
        return view;
    }

    private void showBtnTable(boolean action){
        if(action)
        {
            btnUpMeasure.setEnabled(true);
            btnUpMeasure.setAlpha(1f);
            btnDownMeasure.setEnabled(true);
            btnDownMeasure.setAlpha(1f);
            btnDeleteMeasure.setEnabled(true);
            btnDeleteMeasure.setAlpha(1f);
            btnEditMeasure.setEnabled(true);
            btnEditMeasure.setAlpha(1f);
        }else{
            btnUpMeasure.setEnabled(false);
            btnUpMeasure.setAlpha(0.2f);
            btnDownMeasure.setEnabled(false);
            btnDownMeasure.setAlpha(0.2f);
            btnDeleteMeasure.setEnabled(false);
            btnDeleteMeasure.setAlpha(0.2f);
            btnEditMeasure.setEnabled(false);
            btnEditMeasure.setAlpha(0.2f);
        }
    }

    private void SelectedAction(final int selectedIndex){
        switch (selectedIndex) {
            case 0:
                btnPR.startAnimation(ani_BtnSelected);
                addReferencePoint();
                break;
            case 1:
                btnPA.startAnimation(ani_BtnSelected);
                addAuxiliarPoint();
                break;
            case 2:
                btnDeleteMeasure.startAnimation(ani_BtnSelected);
                deleteMeasure();
                break;
            case 3:
                btnEditMeasure.startAnimation(ani_BtnSelected);
                addNewMeasure(true);
                break;
            case 4:
                btnAddMeasure.startAnimation(ani_BtnSelected);
                if(itemSelected > -1) {
                    tblMedidas.getChildAt(itemSelected).setBackgroundResource(0);
                    showBtnTable(false);
                    itemSelected = -1;
                }
                addNewMeasure(false);
                break;
            case 5:
                btnUpMeasure.startAnimation(ani_BtnSelected);
                break;
            case 6:
                btnDownMeasure.startAnimation(ani_BtnSelected);
                break;
        }
    }

    public static SketchTableActivity newInstance(String text) {
        SketchTableActivity f = new SketchTableActivity();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        return f;
    }

    private void addReferencePoint(){
        LayoutInflater inflaterPR = getActivity().getLayoutInflater();

        View dialoglayoutPR = inflaterPR.inflate(R.layout.dialog_reference_point, null);
        final ImageView btnPuntoRefOK = (ImageView) dialoglayoutPR.findViewById(R.id.BtnReOK);
        final ImageView btnPuntoRefCancel = (ImageView) dialoglayoutPR.findViewById(R.id.BtnReCancel);
        final EditText txtReId = (EditText) dialoglayoutPR.findViewById(R.id.TxtReId);
        final EditText txtReDescripcion = (EditText) dialoglayoutPR.findViewById(R.id.TxtReDescripcion);
        final TextView txtReTitle = (TextView) dialoglayoutPR.findViewById(R.id.TxtReTitle);

        txtReId.setText(txtPrId.getText());
        txtReDescripcion.setText(txtPrDescription.getText());

        final AlertDialog.Builder builderPR = new AlertDialog.Builder(getContext());
        builderPR.setView(dialoglayoutPR);
        //builder.show().getWindow().setLayout(950,350);
        final AlertDialog showPR = builderPR.show();

        btnPuntoRefOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnPuntoRefOK.startAnimation(ani_BtnSelected);
                String id = txtReId.getText().toString();
                String descrip = txtReDescripcion.getText().toString();
                if(id.equals("") || descrip.equals(""))
                {
                    txtReTitle.setText("Porfavor complete los campos");
                    txtReTitle.startAnimation(ani_CapaTitle);
                    if(descrip.equals("")) { txtReDescripcion.setError("Agergar Descripción"); txtReDescripcion.requestFocus();}
                    if(id.equals("")) { txtReId.setError("Agergar Id"); txtReId.requestFocus();}
                }else
                {
                    txtPrId.setText(txtReId.getText());
                    txtPrDescription.setText(txtReDescripcion.getText());
                    showPR.dismiss();
                }
            }
        });
        btnPuntoRefCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnPuntoRefCancel.startAnimation(ani_BtnSelected);
                showPR.dismiss();
            }
        });
    }

    private void addAuxiliarPoint(){
        LayoutInflater inflaterPA  = getActivity().getLayoutInflater();

        View dialoglayoutPA = inflaterPA.inflate(R.layout.dialog_auxiliar_point, null);
        final ImageView btnPuntoAufOK = (ImageView) dialoglayoutPA.findViewById(R.id.BtnAuOK);
        final ImageView btnPuntoAufCancel = (ImageView) dialoglayoutPA.findViewById(R.id.BtnAuCancel);
        final EditText txtAuId = (EditText) dialoglayoutPA.findViewById(R.id.TxtAuId);
        final EditText txtAuDescripcion = (EditText) dialoglayoutPA.findViewById(R.id.TxtAuDescripcion);
        final EditText txtAuCoordX = (EditText) dialoglayoutPA.findViewById(R.id.TxtAuCoordX);
        final EditText txtAuCoordY = (EditText) dialoglayoutPA.findViewById(R.id.TxtAuCoordY);
        final TextView txtAuTitle = (TextView) dialoglayoutPA.findViewById(R.id.TxtAuTitle);

        txtAuId.setText(txtPaId.getText());
        txtAuDescripcion.setText(txtPaDescription.getText());
        txtAuCoordX.setText(txtPaCoordX.getText());
        txtAuCoordY.setText(txtPaCoordY.getText());

        final AlertDialog.Builder builderPA = new AlertDialog.Builder(getContext());
        builderPA.setView(dialoglayoutPA);
        //builder.show().getWindow().setLayout(950,350);
        final AlertDialog showPA = builderPA.show();

        btnPuntoAufOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnPuntoAufOK.startAnimation(ani_BtnSelected);
                String id = txtAuId.getText().toString();
                String descrip = txtAuDescripcion.getText().toString();
                String x = txtAuCoordX.getText().toString();
                String y = txtAuCoordY.getText().toString();
                if(id.equals("") || descrip.equals("") || x.equals("") || y.equals(""))
                {
                    txtAuTitle.setText("Porfavor complete los campos");
                    txtAuTitle.startAnimation(ani_CapaTitle);
                    if(descrip.equals("")) { txtAuDescripcion.setError("Agergar Descripción"); txtAuDescripcion.requestFocus();}
                    if(y.equals("")) { txtAuCoordY.setError("Agergar Coordenadas en Y"); txtAuCoordY.requestFocus();}
                    if(x.equals("")) { txtAuCoordX.setError("Agergar Cooredenadas en X "); txtAuCoordX.requestFocus();}
                    if(id.equals("")) { txtAuId.setError("Agergar Id"); txtAuId.requestFocus();}
                }else
                {
                    txtPaId.setText(txtAuId.getText());
                    txtPaDescription.setText(txtAuDescripcion.getText());
                    txtPaCoordX.setText(txtAuCoordX.getText());
                    txtPaCoordY.setText(txtAuCoordY.getText());
                    showPA.dismiss();
                }
            }
        });
        btnPuntoAufCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                btnPuntoAufCancel.startAnimation(ani_BtnSelected);
                showPA.dismiss();
            }
        });
    }

    private void deleteMeasure(){
//        if(itemSelected > -1) {
//            AlertDialog deleteAlert = new AlertDialog.Builder(getContext()).create();                   // Pregunto si esta seguro de Eliminar la imagen
//            deleteAlert.setTitle("Eliminar");
//            deleteAlert.setMessage("Seguro que decea eliminar la medida seleccionada?");
//            //deleteAlert.setIcon(R.drawable.delete);
//            deleteAlert.setButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which)
//                {
//                    tblMedidas.getChildAt(itemSelected).setBackgroundResource(0);
//                    showBtnTable(false);
//                    adapterMedidas.remove((MeasureEvi) tblMedidas.getItemAtPosition(itemSelected));
//                    itemSelected = -1;                                                      // Acomodo el entorno de la pantalla principal, ocultando el panel de Edicion y mostrando el panel Principal
//                }
//            });
//            deleteAlert.setButton2("Cancelar", new DialogInterface.OnClickListener() {          // Cancela la eliminacion
//                @Override
//                public void onClick(DialogInterface dialog, int which) {           // vacio no hay que hacer nada solo regresar al la pantalla en que estaba
//
//                }
//            });
//            deleteAlert.show();
//        }
    }

    private void addNewMeasure(final boolean fromEdit) { // Edit and Add
//        LayoutInflater inflaterAdd  = getActivity().getLayoutInflater();
//
//        View dialoglayoutAdd = inflaterAdd.inflate(R.layout.dialog_new_measure, null);
//        final ImageView btnAddOK = (ImageView) dialoglayoutAdd.findViewById(R.id.BtnAddOK);
//        final ImageView btnAddfCancel = (ImageView) dialoglayoutAdd.findViewById(R.id.BtnAddCancel);
//        //final EditText txtAddId = (EditText) dialoglayoutAdd.findViewById(R.id.TxtAddId);
//        final Spinner spnEvidence = (Spinner) dialoglayoutAdd.findViewById(R.id.SpnEvidence);
//        spnEvidence.setAdapter(DataIpat.adapterEvidence);
//        spnEvidence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> spinner, View v, int arg2, long arg3) {
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//        final EditText txtAddDescripcion = (EditText) dialoglayoutAdd.findViewById(R.id.TxtAddDescription);
//        final EditText txtAddCoordX = (EditText) dialoglayoutAdd.findViewById(R.id.TxtAddCoordX);
//        final EditText txtAddCoordY = (EditText) dialoglayoutAdd.findViewById(R.id.TxtAddCoordY);
//        final TextView txtAddTitle = (TextView) dialoglayoutAdd.findViewById(R.id.TxtAddTitle);
//        final TextView txtAddNumItem = (TextView) dialoglayoutAdd.findViewById(R.id.TxtAddNumItem);
//
//        if(fromEdit) {
//            MeasureTag data = (MeasureTag) tblMedidas.getItemAtPosition(itemSelected);
//            txtAddNumItem.setText("" + data.getmIndex());
//            txtAddCoordX.setText("" + data.getmCoordenate().x);
//            txtAddCoordY.setText("" + data.getmCoordenate().x);
//            //txtAddId.setText("" + data.getEvidenceIndex());
//            spnEvidence.setSelection(0);
////            txtAddDescripcion.setText(data.getmMeasureType());
//        }
//
//        final AlertDialog.Builder builderAdd = new AlertDialog.Builder(getContext());
//        builderAdd.setView(dialoglayoutAdd);
//        //builder.show().getWindow().setLayout(950,350);
//        final AlertDialog showAdd = builderAdd.show();
//
//        btnAddOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                btnAddOK.startAnimation(ani_BtnSelected);
//                try {
//                    //int id = Integer.parseInt(txtAddId.getText().toString());
//                    int id = spnEvidence.getSelectedItemPosition();
//                    EvidenceItem mySelection = (EvidenceItem) spnEvidence.getSelectedItem();
//                    String descrip = txtAddDescripcion.getText().toString();
//                    PointF newCoordenate = new PointF(Float.parseFloat(txtAddCoordX.getText().toString()),  Float.parseFloat(txtAddCoordY.getText().toString()));
//
//                    if (id < 0 || descrip.equals("") || newCoordenate.x == 0 || newCoordenate.y == 0) {
//                        txtAddTitle.setText("Porfavor complete los campos");
//                        txtAddTitle.startAnimation(ani_CapaTitle);
//                        //if(descrip.equals("")) { txtAuDescripcion.setError("Agergar Descripción"); txtAuDescripcion.requestFocus();}
//                        if (newCoordenate.y == 0) {
//                            txtAddCoordY.setError("Agergar Coordenadas en Y");
//                            txtAddCoordY.requestFocus();
//                        }
//                        if (newCoordenate.x == 0) {
//                            txtAddCoordX.setError("Agergar Cooredenadas en X ");
//                            txtAddCoordX.requestFocus();
//                        }
//                        if (id < 0 ) {
//                            //txtAddId.setError("Agergar Id");
//                            //txtAddId.requestFocus();
//                        }
//                    } else {
////                        if(fromEdit) {
////                            MeasureTag updateMeasure = (MeasureTag) tblMedidas.getItemAtPosition(itemSelected);
////                            updateMeasure.setIndex(adapterMedidas.getCount());
////                            updateMeasure.setCoordenate(newCoordenate);
////                            updateMeasure.setEvidenceId(mySelection.getEvidenceId());
////                            updateMeasure.setDescription(descrip);
////                            //mCallback.onNewMeasureInDraw(updateMeasure, 1);
////                            adapterMedidas.notifyDataSetChanged();
////                        }else{
////                            adapterMedidas.getCount();
////                            MeasureTag newMeasure = new MeasureTag();
////                            newMeasure.setIndex(adapterMedidas.getCount());
////                            newMeasure.setCoordenate(newCoordenate);
////                            newMeasure.setEvidenceId(mySelection.getEvidenceId());
////                            newMeasure.setDescription(descrip);
////                            adapterMedidas.add(newMeasure);
////
////                            //mCallback.onNewMeasureInDraw(newMeasure, 0);
////                        }
//                        showAdd.dismiss();
//                    }
//                }catch (Exception ex) {
//                    txtAddTitle.setText("Error de tipo en los datos ingresados.");
//                    txtAddTitle.startAnimation(ani_CapaTitle);
//                }
//            }
//        });
//        btnAddfCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                btnAddfCancel.startAnimation(ani_BtnSelected);
//                showAdd.dismiss();
//            }
//        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                InputMethodManager mi = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                mi.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;

            case MotionEvent.ACTION_MOVE:
                // touch move code
                break;

            case MotionEvent.ACTION_UP:
                // touch up code
                break;
        }
        return true;
    }

    /////////////////////////////////////

    private void OrdenaEvidencias(){
        ArrayList<MeasureEvi> measureEviArray = (ArrayList<MeasureEvi>)DataIpat.measureEviArray.clone();
        ArrayList<MeasureEvi> NuevaLista = new ArrayList<MeasureEvi>();
        ///Get Item Menor Position
        float menorPositionX = 1000;
        float MayoritemX = 0;
        float CordenateX=0;
        int IndexMenorPosition = -20;
        int IndexMayorPosition = 0;
        MeasureEvi newITemMenor = null;
        //Aqui captura la lista el menor y el mayor
        for(int i = 0; i< DataIpat.measureEviArray.size(); i++)
        {
            for (MeasureEvi item :measureEviArray) {
                CordenateX = item.getmCoordenate().x;
                if(CordenateX < menorPositionX) {
                    menorPositionX = CordenateX;
                    IndexMenorPosition = measureEviArray.indexOf(item);
                }
            }
            newITemMenor = measureEviArray.get(IndexMenorPosition);
            measureEviArray.remove(IndexMenorPosition);
            newITemMenor.setmOrderIndex(i+1);
            NuevaLista.add(newITemMenor);
            menorPositionX = 1000;
        }

        DataIpat.measureEviArray =  (ArrayList<MeasureEvi>)NuevaLista.clone();
        DataIpat.adapterMeasureEvi  = new MeasureTableAdapter(getActivity(), DataIpat.measureEviArray );
        tblMedidas.setAdapter(DataIpat.adapterMeasureEvi);
        //ahora debe comenzar optiene
    }
}