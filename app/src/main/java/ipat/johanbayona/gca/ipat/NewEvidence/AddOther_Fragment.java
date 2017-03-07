package ipat.johanbayona.gca.ipat.NewEvidence;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ipat.johanbayona.gca.ipat.Adapters.SketchMenuSideAdapter;
import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.Evidence;
import ipat.johanbayona.gca.ipat.DataModels.EvidenceByCategory;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.R;

public class AddOther_Fragment extends Fragment {
    private GridView grdCategory, grdSubCategory;
    private TextView txtCategory, txtSubCategory;
    public Evidence OtherEviSelected = null;
    private String id = "id Otro";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.addother__fragment, container, false);

        grdCategory = (GridView)view.findViewById(R.id.GrdCategory);
        grdSubCategory  = (GridView)view.findViewById(R.id.GrdSubCategory);
        txtCategory  = (TextView)view.findViewById(R.id.TxtCategory);
        txtSubCategory  = (TextView)view.findViewById(R.id.TxtSubCategory);

        loadMennu();

        return view;
    }

    private void loadMennu(){
        grdCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.Otros), getResources().obtainTypedArray(R.array.OtrosIcon), 3));
        txtCategory.setText("OTRAS EVIDENCIAS");
        txtSubCategory.setText("");
        grdCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg40, View arg41, int categorySelected, long arg43) {
                switch(categorySelected) {
                    case 0:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.huellas), getResources().obtainTypedArray(R.array.huellasIcon), 3));
                        txtSubCategory.setText("Objeto Fijo");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch(subCategorySelected) {
                                    case 0:selectedEvidence(DataIpat.EvidenceCategory.HUELLA, DataIpat.EvidenceType.FRENADO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.camioneta); break;
                                    case 1:selectedEvidence(DataIpat.EvidenceCategory.HUELLA, DataIpat.EvidenceType.ARRASTRE, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculo_taxi);break;
                                    case 2:selectedEvidence(DataIpat.EvidenceCategory.HUELLA, DataIpat.EvidenceType.PINTURA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_boca_arriba06);break;
                                }
                            }
                        });
                        break;
                    case 1:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.Transito), getResources().obtainTypedArray(R.array.TransitoIcon), 3));
                        txtSubCategory.setText("Senales de Transito");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch(subCategorySelected) {
                                    case 0:selectedEvidence(DataIpat.EvidenceCategory.SEÑAL, DataIpat.EvidenceType.SEMAFORO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_senales_semaforo);break;
                                    case 1:selectedEvidence(DataIpat.EvidenceCategory.SEÑAL, DataIpat.EvidenceType.PREVENTIVA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_senales_preventiva);break;
                                    case 2:selectedEvidence(DataIpat.EvidenceCategory.SEÑAL, DataIpat.EvidenceType.REGLAMETARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_senales_reglamentacion);break;
                                    case 3:selectedEvidence(DataIpat.EvidenceCategory.SEÑAL, DataIpat.EvidenceType.INFORMATIVA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_senales_informacion);break;
                                    case 4:selectedEvidence(DataIpat.EvidenceCategory.SEÑAL, DataIpat.EvidenceType.TEMPORAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_senales_temporales);break;
                                    case 5:selectedEvidence(DataIpat.EvidenceCategory.SEÑAL, DataIpat.EvidenceType.REDUCTOR, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_senales_reductores);break;
                                }
                            }
                        });
                        break;
                    case 2:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.EviObjetos), getResources().obtainTypedArray(R.array.EviObjetosIcon), 3));
                        txtSubCategory.setText("Objetos");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch(subCategorySelected) {
//                                    case 0:selectedEvidence(DataIpat.EvidenceCategory.OBJETO, DataIpat.EvidenceType.FIJO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.camioneta); break;
//                                    case 1:selectedEvidence(DataIpat.EvidenceCategory.OBJETO, DataIpat.EvidenceType.MOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculo_taxi);break;
                                    case 2:selectedEvidence(DataIpat.EvidenceCategory.OBJETO, DataIpat.EvidenceType.VIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_fijos_arbol_lat);break;
//                                    case 3:selectedEvidence(DataIpat.EvidenceCategory.OBJETO, DataIpat.EvidenceType.DESCONOCIDO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_boca_abajo);break;
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    private void selectedEvidence(DataIpat.EvidenceCategory evidenceCategory, DataIpat.EvidenceType evidenceType, Drawable icon, int image){
        ImageView myImage = new ImageView(getActivity());
        myImage.setImageDrawable(getResources().getDrawable(image));
        OtherEviSelected = new Evidence(
                0,
                evidenceCategory,
                myImage,
                evidenceType,
                getContext()
        );
    }

    public boolean saveSelection(){
        if(OtherEviSelected == null) return false;

        OtherEviSelected.seteIndex(DataIpat.evidencetArray.size());
        OtherEviSelected.setcIndex(DataIpat.evidenceByOtherArray.size());
        OtherEviSelected.seteId(id);

        List<String> listMeasureType = DataIpat.getmDescriptionByeType(OtherEviSelected.geteType());
        for(String measurePoint: listMeasureType){         // Creo las medidas correspondientes a la evidencia
            MeasureEvi newMeasure = new MeasureEvi(
                    DataIpat.measureEviArray.size(),
                    OtherEviSelected.geteIndex(),
                    measurePoint,
                    getContext()
            );

            DataIpat.measureEviArray.add(newMeasure);
            OtherEviSelected.getmIndexArray().add(newMeasure.getmIndex());
        }

        DataIpat.evidencetArray.add(OtherEviSelected);
        DataIpat.evidenceByOtherArray.add(new EvidenceByCategory(OtherEviSelected.geteIndex(), OtherEviSelected.getcIndex()));
        return true;
    }
}