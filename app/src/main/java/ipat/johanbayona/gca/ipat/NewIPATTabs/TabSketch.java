package ipat.johanbayona.gca.ipat.NewIPATTabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import ipat.johanbayona.gca.ipat.Adapters.EvidenceTableAdapter;
import ipat.johanbayona.gca.ipat.Adapters.MeasureTableAdapter;
import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.EvidenceByCategory;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.File.FileStorage;
import ipat.johanbayona.gca.ipat.MyConvert;
import ipat.johanbayona.gca.ipat.NewEvidence.AddOther_Fragment;
import ipat.johanbayona.gca.ipat.NewEvidence.AddVehiclePage1_Fragment;
import ipat.johanbayona.gca.ipat.NewEvidence.AddVictim_Fragment;
import ipat.johanbayona.gca.ipat.R;
import ipat.johanbayona.gca.ipat.SketchActivity;

public class TabSketch extends Fragment{

    private TextView txtText;
    private ImageView btnAddEvCancel, btnAddEvVOk;
    private DataIpat.EvidenceCategory categoryWork = null;

    FragmentManager fragmentManager;
    AddVehiclePage1_Fragment myVehicleFragment;
    AddVictim_Fragment myVictimFragment;
    AddOther_Fragment myOtherFragment;

    int vehicleSelectedIndex = -1, victimSelectedIndex = -1, otherSelectedIndex =-1;

    View view;

    ImageView btnAddSketch, btnDeleteSketch, btnEditSketch,     // Botones para crear editar y eliminar un Croquis
            btnAddMeausere, btnDeleteMeausere, btnEditMeausere,
            imgSketchResult,
            btnAddVehicle, btnDeleteVehicle, btnEditVehicle,    // Botones para crear editar y eliminar una evidencia
            btnAddVictim, btnDeleteVictim, btnEditVictim,
            btnAddOther, btnDeleteOther, btnEditOther;

    ScrollView sclTabSketch;
    RelativeLayout rlyNewEvidence;                              // Layaut para poner el fragmento de newEvidence

    GridView tblVehicle, tblVictimas, tblOther;
    Animation ani_BtnSelected, ani_NewEvidence_In, ani_NewEvidence_Out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_tabsketch, container, false);

        txtText = (TextView) view.findViewById(R.id.TxtText);
        btnAddEvCancel = (ImageView) view.findViewById(R.id.BtnAddEvCancel);
        btnAddEvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NewEvidenceAction(false);
            }
        });
        btnAddEvVOk = (ImageView) view.findViewById(R.id.BtnAddEvVOk);
        btnAddEvVOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { NewEvidenceAction(true);
            }
        });

        rlyNewEvidence = (RelativeLayout) view.findViewById(R.id.RlyNewEvidence);
        imgSketchResult = (ImageView) view.findViewById(R.id.ImgSketchResult);
        sclTabSketch = (ScrollView) view.findViewById(R.id.SclTabSketch);

        ani_BtnSelected = AnimationUtils.loadAnimation (view.getContext(), R.anim.ani_btnselected);
        ani_NewEvidence_In = AnimationUtils.loadAnimation (view.getContext(), R.anim.ani_popup_in);
        ani_NewEvidence_Out = AnimationUtils.loadAnimation (view.getContext(), R.anim.ani_popup_out);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        tblVehicle = (GridView) view.findViewById(R.id.TblVehicle);
        tblVehicle.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        DataIpat.adapterEvidenceByVehicle = new EvidenceTableAdapter(getActivity(), DataIpat.evidenceByVehicleArray);
        tblVehicle.setAdapter(DataIpat.adapterEvidenceByVehicle);
        tblVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> argTrh20, View tableView, int position, long arg2Trh3) {
                vehicleSelectedIndex = position;
                deselectedEvidence(DataIpat.EvidenceCategory.VEHICULO);
            }
        });
        tblVictimas = (GridView) view.findViewById(R.id.TblVictim);
        tblVictimas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        DataIpat.adapterEvidenceByVictim = new EvidenceTableAdapter(getActivity(), DataIpat.evidenceByVictimArray);
        tblVictimas.setAdapter(DataIpat.adapterEvidenceByVictim);
        tblVictimas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> argTrh20, View tableView, int position, long arg2Trh3) {
                victimSelectedIndex = position;
                deselectedEvidence(DataIpat.EvidenceCategory.VICTIMA);
            }
        });
        tblOther = (GridView) view.findViewById(R.id.TblOther);
        tblOther.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        DataIpat.adapterEvidenceByOther = new EvidenceTableAdapter(getActivity(), DataIpat.evidenceByOtherArray);
        tblOther.setAdapter(DataIpat.adapterEvidenceByOther);
        tblOther.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> argTrh20, View tableView, int position, long arg2Trh3) {
                otherSelectedIndex = position;
                deselectedEvidence(DataIpat.EvidenceCategory.OBJETO);
            }
        });
        DataIpat.adapterMeasureEvi = new MeasureTableAdapter(getActivity(), DataIpat.measureEviArray);  // Inicializo el adaptador de la Tabla de medidas
        btnAddSketch = (ImageView) view.findViewById(R.id.BtnAddSketch);
        btnAddSketch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnAddSketch.startAnimation(ani_BtnSelected);
                openNewSketch(false);
            }
        });
        btnDeleteSketch  = (ImageView) view.findViewById(R.id.BtnDeleteSketch);
        btnDeleteSketch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnDeleteSketch.startAnimation(ani_BtnSelected);
            }
        });
        btnEditSketch = (ImageView) view.findViewById(R.id.BtnEditSketch);
        btnEditSketch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnEditSketch.startAnimation(ani_BtnSelected);
            }
        });

        btnAddMeausere= (ImageView) view.findViewById(R.id.BtnAddMeausere);
        btnAddMeausere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnAddMeausere.startAnimation(ani_BtnSelected);
                openNewSketch(true);
            }
        });
        btnDeleteMeausere= (ImageView) view.findViewById(R.id.BtnDeleteMeausere);
        btnDeleteMeausere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnDeleteMeausere.startAnimation(ani_BtnSelected);
            }
        });
        btnEditMeausere= (ImageView) view.findViewById(R.id.BtnEditMeausere);
        btnEditMeausere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnEditMeausere.startAnimation(ani_BtnSelected);
            }
        });

        // Botones de para cada una de las tablas de evidencias
        btnAddVehicle = (ImageView) view.findViewById(R.id.BtnAddVehicle);
        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tblEvidenceAction(DataIpat.EvidenceCategory.VEHICULO, DataIpat.stateAction.Create);
            }
        });
        btnEditVehicle = (ImageView) view.findViewById(R.id.BtnEditVehicle);
        btnEditVehicle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tblEvidenceAction(DataIpat.EvidenceCategory.VEHICULO, DataIpat.stateAction.Edit);
            }
        });
        btnDeleteVehicle = (ImageView) view.findViewById(R.id.BtnDeleteVehicle);
        btnDeleteVehicle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deletePicture(v);
                //tblEvidenceAction(DataIpat.EvidenceCategory.VEHICULO, DataIpat.stateAction.Delete);
            }
        });

        btnAddVictim = (ImageView) view.findViewById(R.id.BtnAddVictim);
        btnAddVictim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tblEvidenceAction(DataIpat.EvidenceCategory.VICTIMA, DataIpat.stateAction.Create);
            }
        });
        btnEditVictim = (ImageView) view.findViewById(R.id.BtnEditVictim);
        btnEditVictim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tblEvidenceAction(DataIpat.EvidenceCategory.VICTIMA, DataIpat.stateAction.Edit);
            }
        });
        btnDeleteVictim = (ImageView) view.findViewById(R.id.BtnDeleteVictim);
        btnDeleteVictim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deletePicture(v);

                //tblEvidenceAction(DataIpat.EvidenceCategory.VICTIMA, DataIpat.stateAction.Delete);
            }
        });

        btnAddOther = (ImageView) view.findViewById(R.id.BtnAddOther);
        btnAddOther.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tblEvidenceAction(DataIpat.EvidenceCategory.OBJETO, DataIpat.stateAction.Create);
            }
        });
        btnEditOther = (ImageView) view.findViewById(R.id.BtnEditOther);
        btnEditOther.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tblEvidenceAction(DataIpat.EvidenceCategory.OBJETO, DataIpat.stateAction.Edit);
            }
        });
        btnDeleteOther = (ImageView) view.findViewById(R.id.BtnDeleteOther);
        btnDeleteOther.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deletePicture(v);
                // tblEvidenceAction(DataIpat.EvidenceCategory.OBJETO, DataIpat.stateAction.Delete);
            }
        });

        showBtnTblVehicle(false);
        showBtnTblOther(false);
        showBtnTblVictim(false);
        showBtnSketch(false);

        fragmentManager = getChildFragmentManager();

        FileStorage.myFileStorage.readNameIpat();
        return view;
    }

    private void deselectedEvidence(DataIpat.EvidenceCategory evidenceCategory){
        if(evidenceCategory != DataIpat.EvidenceCategory.VEHICULO){
            tblVehicle.setSelection(-1);
            showBtnTblVehicle(false);
        }else showBtnTblVehicle(true);
        if(evidenceCategory != DataIpat.EvidenceCategory.VICTIMA){
            tblVictimas.setSelection(-1);
            showBtnTblVictim(false);
        }else showBtnTblVictim(true);
        if(evidenceCategory != DataIpat.EvidenceCategory.OBJETO){
            tblOther.setSelection(-1);
            showBtnTblOther(false);
        }else showBtnTblOther(true);
    }

    public static TabSketch newInstance(String text) {
        TabSketch f = new TabSketch();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        return f;
    }

    private void deletePicture(View v)
    {
        switch (v.getId())
        {
            case R.id.BtnDeleteVehicle:
                DataIpat.adapterEvidenceByVehicle.deleteitem(vehicleSelectedIndex, DataIpat.EvidenceCategory.VEHICULO);
                //Refezca adaptador cambio desuda tos itnernos
                DataIpat.adapterEvidenceByVehicle.notifyDataSetChanged();

                break;
            case R.id.BtnDeleteVictim:
                DataIpat.adapterEvidenceByVictim.deleteitem(victimSelectedIndex, DataIpat.EvidenceCategory.VICTIMA);
                //Refezca adaptador cambio desuda tos itnernos
                DataIpat.adapterEvidenceByVictim.notifyDataSetChanged();
                break;
            case  R.id.BtnDeleteOther:
                DataIpat.adapterEvidenceByOther.deleteitem(otherSelectedIndex, DataIpat.EvidenceCategory.OBJETO);
                //Refezca adaptador cambio desuda tos itnernos
                DataIpat.adapterEvidenceByOther.notifyDataSetChanged();
                break;
        }
        //
    }

    private void openNewSketch(final boolean tableInitialization){
        if(DataIpat.evidenceByVehicleArray.size() > 0 || DataIpat.evidenceByVictimArray.size() > 0 || DataIpat.evidenceByOtherArray.size() > 0 ){
            //// Configuracioness necesarias para el dibujo ///////////////
            DisplayMetrics metrics = getResources().getDisplayMetrics();                                // clase que permite obttenr info del dispositivo
            MyConvert.setDpi(metrics.densityDpi);                                                       // Registro lod DPI del dispositivo
            MyConvert.setScreenResolution(new PointF(metrics.widthPixels, metrics.heightPixels));       // Registro la resolucion real de la pantalla del dispositivo
            MyConvert.setOffset(65, 100);

            LayoutInflater inflaterSK = getActivity().getLayoutInflater();

            View dialoglayoutSK = inflaterSK.inflate(R.layout.dialog_setting_draw, null);
            final ImageView btnAddSkOK = (ImageView) dialoglayoutSK.findViewById(R.id.BtnAddSkOK);
            final ImageView btnAddSkCancel = (ImageView) dialoglayoutSK.findViewById(R.id.BtnAddSkCancel);
            final EditText txtLargeArea = (EditText) dialoglayoutSK.findViewById(R.id.TxtLargeArea);

            final AlertDialog.Builder builderSK = new AlertDialog.Builder(getContext());
            builderSK.setView(dialoglayoutSK);
            //builder.show().getWindow().setLayout(950,350);
            final AlertDialog showSkPR = builderSK.show();
            txtLargeArea.selectAll();
            txtLargeArea.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    okNewSketchDialog(txtLargeArea, showSkPR, tableInitialization);                                         // al hacer click en el boton de accion del teclado en siguiente redirecciona el foco al siguiente campo
                    return true;
                }
            });
            btnAddSkOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    btnAddSkOK.startAnimation(ani_BtnSelected);
                    okNewSketchDialog(txtLargeArea, showSkPR, tableInitialization);
                }
            });
            btnAddSkCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    btnAddSkCancel.startAnimation(ani_BtnSelected);

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE); //Oculto el teclado
                    imm.hideSoftInputFromWindow(txtLargeArea.getWindowToken (), 0);
                    //imm.hideSoftInputFromWindow(txtTrackWidth.getWindowToken (), 0);

                    showSkPR.dismiss();
                }
            });
            ////////////////////////////////////////////////
        }else{
            LayoutInflater inflaterSK = getActivity().getLayoutInflater();

            View dialoglayoutSK = inflaterSK.inflate(R.layout.dialog_info_draw, null);
            final ImageView btnAddSkOK = (ImageView) dialoglayoutSK.findViewById(R.id.BtnAddSkOK);

            final AlertDialog.Builder builderSK = new AlertDialog.Builder(getContext());
            builderSK.setView(dialoglayoutSK);
            final AlertDialog showSkPR = builderSK.show();
            btnAddSkOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE); //Oculto el teclado
                    showSkPR.dismiss();
                }
            });
        }
    }

    //region - Button Action
    private void tblEvidenceAction(DataIpat.EvidenceCategory evidenceCategory, DataIpat.stateAction stateAction){
        switch(evidenceCategory){
            case VEHICULO: ;
                if(stateAction == DataIpat.stateAction.Create){
                    btnAddVehicle.startAnimation(ani_BtnSelected);                                                     // Animo el boton
                    myVehicleFragment = new AddVehiclePage1_Fragment();
                    myVehicleFragment.VehicleEviSelected = null;
                    fragmentManager.beginTransaction().replace(R.id.RlyFragment_content, myVehicleFragment).commit();
                    ani_NewEvidence_In.setAnimationListener(new Animation.AnimationListener() {                        // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                        public void onAnimationStart(Animation anim) {};
                        public void onAnimationRepeat(Animation anim) {};
                        public void onAnimationEnd(Animation anim) {
                            sclTabSketch.setVisibility(View.GONE);                                                   // desaparece el control donde estan todos los botones
                            ani_NewEvidence_In.setAnimationListener(null);
                        };
                    });
                    categoryWork = evidenceCategory;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rlyNewEvidence.setVisibility(View.VISIBLE);
                            rlyNewEvidence.startAnimation(ani_NewEvidence_In);
                        }
                    }, 350);
                }else if(stateAction == DataIpat.stateAction.Delete){
                    EvidenceByCategory evidenceIndex = DataIpat.evidenceByVehicleArray.get(vehicleSelectedIndex);
                    for(int measureIndex: DataIpat.evidencetArray.get(evidenceIndex.geteIndex()).getmIndexArray()){
                        DataIpat.measureEviArray.remove(measureIndex);
                    }

                    DataIpat.evidenceByVehicleArray.remove(evidenceIndex.getcIndex());
                    DataIpat.evidencetArray.remove(evidenceIndex.geteIndex());

                    DataIpat.adapterEvidenceByVehicle.notifyDataSetChanged();
                }
                break;
            case VICTIMA:
                myVictimFragment = new AddVictim_Fragment();
                myVictimFragment.VicitmEviSelected = null;
                fragmentManager.beginTransaction().replace(R.id.RlyFragment_content, myVictimFragment).commit();
                categoryWork = evidenceCategory;
                rlyNewEvidence.setVisibility(View.VISIBLE);
                rlyNewEvidence.startAnimation(ani_NewEvidence_In);
                break;
            case HUELLA:
            case SEÑAL:
            case OBJETO:
                myOtherFragment = new AddOther_Fragment();
                myOtherFragment.OtherEviSelected = null;
                fragmentManager.beginTransaction().replace(R.id.RlyFragment_content, myOtherFragment).commit();
                categoryWork = evidenceCategory;
                rlyNewEvidence.setVisibility(View.VISIBLE);
                rlyNewEvidence.startAnimation(ani_NewEvidence_In);
                break;
        }
    }

    private void deleteEvidence(){

    }
    //endregio

    private void okNewSketchDialog(EditText txtLargeArea, AlertDialog showSkPR, boolean tableInicialization){
        String areaX = txtLargeArea.getText().toString();
        //String viaX = txtTrackWidth.getText().toString();
        if(areaX.equals("")){
            if(areaX.equals("")) { txtLargeArea.setError("Agregar longitud del area del croquis."); txtLargeArea.requestFocus();}
            //else if(viaX.equals("")) { txtTrackWidth.setError("Agregar ancho de la vía."); txtTrackWidth.requestFocus();}
        }else{
            int area = Integer.parseInt(areaX);
            if(area > 14 && area < 101) {
                MyConvert.setAreaSelected(new PointF(Integer.parseInt(areaX), 15));                                              // Registro el area que deceo mostrar el y esta quemado en el momento mno lo estoy tomando en cuenta
                MyConvert.setScale(MyConvert.setSizeWorkAre());                                                                  // Calculo la escala adecuada para trabajar con el area deseada

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE); //Oculto el teclado
                imm.hideSoftInputFromWindow(txtLargeArea.getWindowToken (), 0);
                //imm.hideSoftInputFromWindow(txtTrackWidth.getWindowToken (), 0);

                for(MeasureEvi myMeasure: DataIpat.measureEviArray){
                    myMeasure.setmCoordenate(new PointF(0,0));
                }

                Intent intent = new Intent(getContext(), SketchActivity.class);
                intent.putExtra("tableInicialization", tableInicialization);                       /////////////////////////////////////////// Nuevo
                startActivity(intent);

                showSkPR.dismiss();
            } else {
                txtLargeArea.setError("La longitud debe estar entre 15 mtrs y 100 mtrs. "); txtLargeArea.requestFocus();
            }
        }
    }

    //region - Show Visual elements
    private void showBtnTblVehicle(boolean action){
        if(action){
            btnDeleteVehicle.setEnabled(true);
            btnDeleteVehicle.setAlpha(1f);
            btnEditVehicle.setEnabled(true);
            btnEditVehicle.setAlpha(1f);
        }else{
            btnDeleteVehicle.setEnabled(false);
            btnDeleteVehicle.setAlpha(0.2f);
            btnEditVehicle.setEnabled(false);
            btnEditVehicle.setAlpha(0.2f);
        }
    }

    private void showBtnTblVictim(boolean action){
        if(action){
            btnDeleteVictim.setEnabled(true);
            btnDeleteVictim.setAlpha(1f);
            btnEditVictim.setEnabled(true);
            btnEditVictim.setAlpha(1f);
        }else{
            btnDeleteVictim.setEnabled(false);
            btnDeleteVictim.setAlpha(0.2f);
            btnEditVictim.setEnabled(false);
            btnEditVictim.setAlpha(0.2f);
        }
    }

    private void showBtnTblOther(boolean action){
        if(action){
            btnDeleteOther.setEnabled(true);
            btnDeleteOther.setAlpha(1f);
            btnEditOther.setEnabled(true);
            btnEditOther.setAlpha(1f);
        }else{
            btnDeleteOther.setEnabled(false);
            btnDeleteOther.setAlpha(0.2f);
            btnEditOther.setEnabled(false);
            btnEditOther.setAlpha(0.2f);
        }
    }

    private void showBtnSketch(boolean action){
        if(action){
            btnDeleteSketch.setEnabled(true);
            btnDeleteSketch.setAlpha(1f);
            btnEditSketch.setEnabled(true);
            btnEditSketch.setAlpha(1f);
        }else{
            btnDeleteSketch.setEnabled(false);
            btnDeleteSketch.setAlpha(0.2f);
            btnEditSketch.setEnabled(false);
            btnEditSketch.setAlpha(0.2f);
        }
    }
    //endregion

    public void setReturnEvent(){
        int po = 0;
        Bitmap imgSketch = FileStorage.myFileStorage.getImageSketch(getContext());
        if(imgSketch != null){
            imgSketchResult.setImageBitmap(imgSketch);
        }
    }

    //region interface implemented
    public void NewEvidenceAction(boolean state) {
        if(state){
            btnAddEvVOk.startAnimation(ani_BtnSelected);
            switch(categoryWork){
                case VEHICULO:
                    if(myVehicleFragment.saveSelection()) {
                        List<EvidenceByCategory> lista = DataIpat.evidenceByVehicleArray;
                        DataIpat.adapterEvidenceByVehicle = new EvidenceTableAdapter(getActivity(),DataIpat.evidenceByVehicleArray);
                        tblVehicle.setAdapter( DataIpat.adapterEvidenceByVehicle);
                        DataIpat.adapterEvidenceByVehicle.notifyDataSetChanged();
                        closeNewEvidenceWindows();
                    }
                    break;
                case VICTIMA:
                    if(myVictimFragment.saveSelection()) {
                        List<EvidenceByCategory> lista = DataIpat.evidenceByVictimArray;
                        DataIpat.adapterEvidenceByVictim = new EvidenceTableAdapter(getActivity(),DataIpat.evidenceByVictimArray);
                        tblVictimas.setAdapter( DataIpat.adapterEvidenceByVictim);
                        DataIpat.adapterEvidenceByVictim.notifyDataSetChanged();
                        closeNewEvidenceWindows();
                    }
                    //else closeNewEvidenceWindows();
                    break;
                case HUELLA:
                case SEÑAL:
                case OBJETO: ;
                    if(myOtherFragment.saveSelection()) {
                        DataIpat.adapterEvidenceByOther = new EvidenceTableAdapter(getActivity(),DataIpat.evidenceByOtherArray);
                        tblOther.setAdapter( DataIpat.adapterEvidenceByOther);
                        closeNewEvidenceWindows();
                    }
                    break;
            }
        }else{
            btnAddEvCancel.startAnimation(ani_BtnSelected);
            closeNewEvidenceWindows();
        }
    }

    private void closeNewEvidenceWindows(){
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE); //Oculto el teclado
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }catch(Exception ex){}
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ani_NewEvidence_Out.setAnimationListener(new Animation.AnimationListener() {                        // a la ultima animacion le creo evento para saber cuando termino y poner desaparece los botones
                    public void onAnimationStart(Animation anim) {};
                    public void onAnimationRepeat(Animation anim) {};
                    public void onAnimationEnd(Animation anim) {
                        rlyNewEvidence.setVisibility(View.GONE);                                                   // desaparece el control donde estan todos los botones
                        ani_NewEvidence_Out.setAnimationListener(null);
                    };
                });
                sclTabSketch.setVisibility(View.VISIBLE);
                rlyNewEvidence.startAnimation(ani_NewEvidence_Out);
            }
        }, 350);
    }
    //endregion

    //
}
