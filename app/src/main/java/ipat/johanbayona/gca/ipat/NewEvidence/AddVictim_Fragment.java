package ipat.johanbayona.gca.ipat.NewEvidence;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class AddVictim_Fragment extends Fragment {
    private GridView grdCategory, grdSubCategory;
    private TextView txtCategory, txtSubCategory;
    public Evidence VicitmEviSelected = null;
    private String id = "Cédula";
    private EditText edtDocumentId;
    private RelativeLayout rlyDocumentId;
    private ImageView imgSelectedPerson;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.addvictim__fragment, container, false);

        grdCategory = (GridView)view.findViewById(R.id.GrdCategory);
        grdSubCategory  = (GridView)view.findViewById(R.id.GrdSubCategory);
        txtCategory  = (TextView)view.findViewById(R.id.TxtCategory);
        txtSubCategory  = (TextView)view.findViewById(R.id.TxtSubCategory);

        rlyDocumentId = (RelativeLayout)view.findViewById(R.id.RlyDocumentId);
        edtDocumentId = (EditText)view.findViewById(R.id.EdtDocumentId);
        imgSelectedPerson  = (ImageView)view.findViewById(R.id.ImgSelectedPerson);

        showIdDocument(false);
        loadMennu();
        return view;
    }

    private void showIdDocument(boolean state){
        if(state){
            rlyDocumentId.setVisibility(View.VISIBLE);
            grdSubCategory.setVisibility(View.GONE);
        }else{
            rlyDocumentId.setVisibility(View.GONE);
            grdSubCategory.setVisibility(View.VISIBLE);
        }
    }

    private void loadMennu()
    {
        final SketchMenuSideAdapter grdCategoryAdapter = new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.Victimas), getResources().obtainTypedArray(R.array.VictimasIcon), 3);
        grdCategory.setAdapter(grdCategoryAdapter);
        txtCategory.setText("VICTIMAS");
        txtSubCategory.setText("");
        grdCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg40, View arg41, int arg42, long arg43) {
                grdCategoryAdapter.setSelectedPosition(arg42);
                grdCategoryAdapter.notifyDataSetChanged();

                if(arg42 == 0){
                    grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.personas), getResources().obtainTypedArray(R.array.personasIcon), 3));
                    txtSubCategory.setText("Personas");

                    grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                            switch(subCategorySelected) {
                                case 0:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_medio_lado); break;
                                case 1:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_boca_arriba_06);break;
                                case 2:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_boca_arriba_02);break;
                                case 3:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_boca_abajo);break;
                                case 4:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_objetos_persona);break;
                                case 5:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_parado);break;
                                case 6:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_parado2);break;
                                case 7:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_sentado);break;
                                case 8:selectedEvidence(DataIpat.EvidenceType.PERSONA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_sentado_carro);break;
                            }
                        }
                    });
                }
                if(arg42 == 1){
                    grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.Animales), getResources().obtainTypedArray(R.array.AnimalesIcon), 3));
                    txtSubCategory.setText("Animales");

                    grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                            switch(subCategorySelected) {
                                case 0:selectedEvidence(DataIpat.EvidenceType.ANIMAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_perro); break;
                                case 1:selectedEvidence(DataIpat.EvidenceType.ANIMAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_animales_gato);break;
                                case 2:selectedEvidence(DataIpat.EvidenceType.ANIMAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_caballo);break;
                                case 3:selectedEvidence(DataIpat.EvidenceType.ANIMAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_animales_burro);break;
                                case 4:selectedEvidence(DataIpat.EvidenceType.ANIMAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_animales_vaca);break;
                                case 5:selectedEvidence(DataIpat.EvidenceType.ANIMAL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_ob_personas_animales_icono_animal);break;
                            }
                        }
                    });
                }
            }
        });
    }

    private void selectedEvidence(DataIpat.EvidenceType evidenceType, Drawable icon, int image){
        ImageView myImage = new ImageView(getActivity());
        myImage.setImageDrawable(getResources().getDrawable(image));
        VicitmEviSelected = new Evidence(
                0,
                DataIpat.EvidenceCategory.VICTIMA,
                myImage,
                evidenceType,
                getContext()
        );

        imgSelectedPerson.setImageDrawable(VicitmEviSelected.geteImage().getDrawable());
        if(VicitmEviSelected.geteType() == DataIpat.EvidenceType.PERSONA) showIdDocument(true);
    }

    public boolean saveSelection(){
        if(VicitmEviSelected == null) return false;
        if(VicitmEviSelected.geteType() == DataIpat.EvidenceType.PERSONA)
            if(!(edtDocumentId.getText().length() > 5 && edtDocumentId.getText().length() < 12)) return false;

        VicitmEviSelected.seteIndex(DataIpat.evidencetArray.size());
        VicitmEviSelected.setcIndex(DataIpat.evidenceByVictimArray.size());
        VicitmEviSelected.seteId(edtDocumentId.getText().toString());

        List<String> listMeasureType = DataIpat.getmDescriptionByeType(VicitmEviSelected.geteType());
        for(String measurePoint: listMeasureType){         // Creo las medidas correspondientes a la evidencia
            MeasureEvi newMeasure = new MeasureEvi(
                    DataIpat.measureEviArray.size(),
                    VicitmEviSelected.geteIndex(),
                    measurePoint,
                    getContext()
            );

            DataIpat.measureEviArray.add(newMeasure);
            VicitmEviSelected.getmIndexArray().add(newMeasure.getmIndex());
        }

        DataIpat.evidencetArray.add(VicitmEviSelected);
        DataIpat.evidenceByVictimArray.add(new EvidenceByCategory(VicitmEviSelected.geteIndex(), VicitmEviSelected.getcIndex()));
        return true;
    }
}
