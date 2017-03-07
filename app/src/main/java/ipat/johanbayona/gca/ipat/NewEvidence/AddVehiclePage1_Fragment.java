package ipat.johanbayona.gca.ipat.NewEvidence;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ipat.johanbayona.gca.ipat.Adapters.PlatesAdapter;
import ipat.johanbayona.gca.ipat.Adapters.SketchMenuSideAdapter;
import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.Evidence;
import ipat.johanbayona.gca.ipat.DataModels.EvidenceByCategory;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.NewIPATTabs.StartSketch;
import ipat.johanbayona.gca.ipat.R;

public class AddVehiclePage1_Fragment extends Fragment {
    private GridView grdCategory, grdSubCategory, grdPlates;
    private TextView txtCategory, txtSubCategory;
    private EditText edt_Character1, edt_Character2, edt_Character3, edt_Character4, edt_Character5, edt_Character6, edt_CharacterTemp1, edt_CharacterTemp2;
    public Evidence VehicleEviSelected = null;
    private String id = "";
    private int plateSize;

    private LinearLayout llyCategories, llyIdentifider;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private TextView txtLabel, txtCiudad;
    private RelativeLayout rlyPlates;
    private List<String> myCharacterList = new ArrayList<>();
    private List<EditText> myEdt_CharacterList = new ArrayList<EditText>();
    private ImageView imgSelectedPlate, imgIconCarga, imgIconDiplomatico, imgIconVeihicle;
    boolean flagFromCode = false;

    private enum typePlate {
        PARTICULAR, MOTO, CLASICO, PUBLICO, DIPLOVIEJO, DIPLONUEVO, CARGA, REMOLQUE
    }

    public static boolean flagFinish = false;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addvehiclepage1__fragment, container, false);

        llyCategories = (LinearLayout) view.findViewById(R.id.LlyCategories);
        llyIdentifider = (LinearLayout) view.findViewById(R.id.LlyIdentifider);

        grdCategory = (GridView) view.findViewById(R.id.GrdCategory);
        grdSubCategory = (GridView) view.findViewById(R.id.GrdSubCategory);
        grdPlates = (GridView) view.findViewById(R.id.GrdPlates);

        txtCategory = (TextView) view.findViewById(R.id.TxtCategory);
        txtSubCategory = (TextView) view.findViewById(R.id.TxtSubCategory);
/////////////////////////////////////////////////////////////////////////////////////////////////
        imgSelectedPlate = (ImageView) view.findViewById(R.id.ImgSelectedPlate);
        imgIconCarga = (ImageView) view.findViewById(R.id.ImgIconCarga);
        imgIconDiplomatico = (ImageView) view.findViewById(R.id.ImgIconDiplomatico);
        imgIconVeihicle = (ImageView) view.findViewById(R.id.ImgIconVuehicles);
        txtCiudad = (TextView) view.findViewById(R.id.TxtCiudad);

        rlyPlates = (RelativeLayout) view.findViewById(R.id.RlyPlates);
        txtLabel = (TextView) view.findViewById(R.id.TxtLabel);

        myEdt_CharacterList.add((EditText) view.findViewById(R.id.Edt_Character0));
        myCharacterList.add("");
        myEdt_CharacterList.add((EditText) view.findViewById(R.id.Edt_Character1));
        myCharacterList.add("");
        myEdt_CharacterList.add((EditText) view.findViewById(R.id.Edt_Character2));
        myCharacterList.add("");
        myEdt_CharacterList.add((EditText) view.findViewById(R.id.Edt_Character3));
        myCharacterList.add("");
        myEdt_CharacterList.add((EditText) view.findViewById(R.id.Edt_Character4));
        myCharacterList.add("");
        myEdt_CharacterList.add((EditText) view.findViewById(R.id.Edt_Character5));
        myCharacterList.add("");
        myEdt_CharacterList.get(0).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return myTextChanged(src, 0);
            }
        }});
        myEdt_CharacterList.get(1).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return myTextChanged(src, 1);
            }
        }});
        myEdt_CharacterList.get(2).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return myTextChanged(src, 2);
            }
        }});
        myEdt_CharacterList.get(3).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return myTextChanged(src, 3);
            }
        }});
        myEdt_CharacterList.get(4).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return myTextChanged(src, 4);
            }
        }});
        myEdt_CharacterList.get(5).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                return myTextChanged(src, 5);
            }
        }});

        for (EditText myEditText : myEdt_CharacterList) {
            myEditText.setTypeface(StartSketch.myFont(view.getContext()));
        }
////////////////////////////////////////////////////////////////////////////////////////////////
        loadMennu();
        showIdentifider(false);

        return view;
    }

    private void loadMennu() {
        grdCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.vehiculos), getResources().obtainTypedArray(R.array.vehiculosIcon), 3));
        txtCategory.setText("VEICULOS");
        txtSubCategory.setText("");
        grdCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg40, View arg41, int categorySelected, long arg43) {
                switch (categorySelected) {
                    case 0:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.automoviles), getResources().obtainTypedArray(R.array.automovilesIcon), 3));
                        txtSubCategory.setText("Automoviles");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch (subCategorySelected) {
                                    case 0:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_sedan);
                                        break;
                                    case 1:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_golf);
                                        break;
                                    case 2:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_taxi_peque_sup);
                                        break;
                                    case 3:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_taxi_sedan_sup);
                                        break;
                                    case 4:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_cupe);
                                        break;
                                    case 5:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_camioneta2);
                                        break;
                                    case 6:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_camioneta);
                                        break;
                                    case 7:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_convert_sup);
                                        break;
                                    case 8:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_limo_sup);
                                        break;
                                    case 9:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_minivan_sup);
                                        break;
                                    case 10:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_van_sup);
                                        break;
                                    case 11:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_minivan_ventanas_sup);
                                        break;
                                    case 12:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_jeep);
                                        break;
                                    case 13:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_pickup_2_sup);
                                        break;
                                    case 14:
                                        selectedEvidence(DataIpat.EvidenceType.AUTOMOVIL, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_automoviles_pickup_sup);
                                        break;
                                }
                            }
                        });
                        break;
                    case 1:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.motos), getResources().obtainTypedArray(R.array.motosIcon), 3));
                        txtSubCategory.setText("Motos");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch (subCategorySelected) {
                                    case 0:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_moto_lat);
                                        break;
                                    case 1:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_moto_peque_lat);
                                        break;
                                    case 2:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_quatrimoto_sup);
                                        break;
                                    case 3:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_tricimoto_sup);
                                        break;
                                    case 4:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_chopper_lat);
                                        break;
                                    case 5:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_motocarro_sup);
                                        break;

                                    case 6:
                                        selectedEvidence(DataIpat.EvidenceType.MOTO, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_motos_motocross_lat);
                                        break;

                                }
                            }
                        });
                        break;
                    case 2:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.buses), getResources().obtainTypedArray(R.array.busesIcon), 3));
                        txtSubCategory.setText("Buses");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch (subCategorySelected) {
                                    case 0:
                                        selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_bus_bus_sup);
                                        break;
                                    case 1:
                                        selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_bus_transmi_sup);
                                        break;
                                    case 2:
                                        selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_bus_biarticulado_sup);
                                        break;
                                    case 3:
                                        selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_bus_sitp_sup);
                                        break;
                                    case 4:
                                        selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_bus_alimentador_sup);
                                        break;


                                }
                            }
                        });
                        break;
                    case 3:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.camiones), getResources().obtainTypedArray(R.array.camionesIcon), 3));
                        txtSubCategory.setText("Camiones");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch (subCategorySelected) {
                                    case 0:
                                        selectedEvidence(DataIpat.EvidenceType.CAMION, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculos_camion_up);
                                        break;
                                    case 1:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_13);break;
                                    case 2:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_estacas_completo_sup);break;
                                    case 3:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_ninera_sup);break;
                                    case 4:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_04);break;
                                    case 5:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_11);break;
                                    case 6:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_estacas_sup);break;
                                    case 7:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_15);break;
                                    case 8:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_cabezote_grande_sup);break;
                                    case 9:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_inferior); break;
                                    case 10:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_remolque_sup);break;
                                    case 11:selectedEvidence(DataIpat.EvidenceType.BUS, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_camiones_remolque_peque_sup);break;
                                }
                            }
                        });
                        break;
                    case 4:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.bicicletas), getResources().obtainTypedArray(R.array.bicicletasIcon), 3));
                        txtSubCategory.setText("Bicicletas");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch (subCategorySelected) {
                                    case 0:
                                        selectedEvidence(DataIpat.EvidenceType.BICICLETA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculos_bicicleta_side);
                                        break;

                                    case 1:
                                        selectedEvidence(DataIpat.EvidenceType.BICICLETA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_bici_bicitaxi_sup);
                                        break;
                                }
                            }
                        });
                        break;
                    case 5:
                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.maquinarias), getResources().obtainTypedArray(R.array.maquinariasIcon), 3));
                        txtSubCategory.setText("Maquinaria");
                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
                                switch (subCategorySelected) {
                                    case 0:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_aplanadora_sup); break;
                                    case 1:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_cargador_sup); break;
                                    case 2:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_retroexcabadora_sup); break;
                                    case 3:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_excabadora_sup); break;
                                    case 4:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_grua_sup); break;
                                    case 5:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_camion_descarga_sup); break;
                                    case 6:selectedEvidence(DataIpat.EvidenceType.MAQUINARIA, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.ml_vehiculos_maquinaria_montacarga_sup); break;
                                }
                            }
                        });
                        break;
//                    case 6:
//                        grdSubCategory.setAdapter(new SketchMenuSideAdapter(getActivity(), getResources().getStringArray(R.array.traccionAnimal), getResources().obtainTypedArray(R.array.traccionAnimalIcon), 3));
//                        txtSubCategory.setText("Tracción");
//                        grdSubCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int subCategorySelected, long l) {
//                                switch(subCategorySelected) {
////                                    case 0:selectedEvidence(DataIpat.EvidenceType.TRACCION, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculos_bicicleta_side); break;
////                                    case 1:selectedEvidence(DataIpat.EvidenceType.TRACCION, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculos_bicicleta_side); break;
////                                    case 2:selectedEvidence(DataIpat.EvidenceType.TRACCION, ((ImageView) view.findViewById(R.id.ImgItem)).getDrawable(), R.drawable.lateral_vehiculos_bicicleta_side); break;
//                                }
//                            }
//                        });
//                        break;
                }
            }
        });
    }

    private void selectedEvidence(DataIpat.EvidenceType evidenceType, Drawable icon, int image) {
        ImageView myImage = new ImageView(getActivity());                                                   // se crea un ImagewView para contener la imagen que se desea agregar
        myImage.setScaleType(ImageView.ScaleType.CENTER);                                          // se configura el tipo de escalado de dicha imagen con repecto al ImageView
        myImage.setAdjustViewBounds(true);
        myImage.setDrawingCacheEnabled(true);
        myImage.setImageDrawable(getResources().getDrawable(image));

        VehicleEviSelected = new Evidence(
                0,
                DataIpat.EvidenceCategory.VEHICULO,
                myImage,
                evidenceType,
                getContext()
        );

        LoadPlate(evidenceType);
        showIdentifider(true);
    }

    public void LoadPlate(final DataIpat.EvidenceType evidenceType) {
        switch (evidenceType) {
            case AUTOMOVIL:
                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placasAuto), getResources().obtainTypedArray(R.array.placasAutoIcon), 2));
                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            selectedPlate(typePlate.PARTICULAR);
                        }
                        if (i == 1) {
                            selectedPlate(typePlate.DIPLOVIEJO);
                        }
                        if (i == 2) {
                            selectedPlate(typePlate.DIPLONUEVO);
                        }
                        if (i == 3) {
                            selectedPlate(typePlate.PUBLICO);
                        }
                    }
                });
                break;

            case MOTO:
                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placasMoto), getResources().obtainTypedArray(R.array.placasMotoIcon), 2));
                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            selectedPlate(typePlate.MOTO);
                        }
                        if (i == 1) {
                            selectedPlate(typePlate.DIPLOVIEJO);
                        }
                        if (i == 2) {
                            selectedPlate(typePlate.DIPLONUEVO);
                        }
                    }
                });
                break;
            case BUS:
                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placasbus), getResources().obtainTypedArray(R.array.placasBusIcon), 2));
                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            selectedPlate(typePlate.PARTICULAR);
                        }
                        if (i == 1) {
                            selectedPlate(typePlate.PUBLICO);
                        }
                    }
                });
                break;
            case CAMION:
                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placasCamion), getResources().obtainTypedArray(R.array.placasCamionIcon), 2));
                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            selectedPlate(typePlate.PARTICULAR);
                        }
                        if (i == 1) {
                            selectedPlate(typePlate.PUBLICO);
                        }
                        if (i == 2) {
                            selectedPlate(typePlate.REMOLQUE);
                        }
                        if (i == 2) {
                            selectedPlate(typePlate.CARGA);
                        }
                    }
                });
                break;
            case BICICLETA:
//                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placaBici), getResources().obtainTypedArray(R.array.placasBiciIcon), 2));
//                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        // Salta
//                    }
//                });
                break;
            case MAQUINARIA:
//                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placasMaqui), getResources().obtainTypedArray(R.array.placasMaquiIcon), 2));
//                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        selectedPlate(typePlate.CARGA);
//                    }
//                 });
                break;
            case TRACCION:
                grdPlates.setAdapter(new PlatesAdapter(getActivity(), getResources().getStringArray(R.array.placasTracc), getResources().obtainTypedArray(R.array.placasTracciIcon), 2));
                grdPlates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            selectedPlate(typePlate.PARTICULAR);
                        }
                        if (i == 1) {
                            selectedPlate(typePlate.PUBLICO);
                        }
                    }
                });
                break;
        }
    }

    private void showIdentifider(boolean state) {
        if (state) {
            llyCategories.setVisibility(View.GONE);
            llyIdentifider.setVisibility(View.VISIBLE);
            rlyPlates.setVisibility(View.GONE);
            txtLabel.setVisibility(View.VISIBLE);
        } else {
            llyCategories.setVisibility(View.VISIBLE);
            llyIdentifider.setVisibility(View.GONE);
        }
    }

    public boolean saveSelection() {
        if (VehicleEviSelected == null) return false;

        String Id = "";
        if (myEdt_CharacterList.size() < 1) return false;
        for (EditText myEditText : myEdt_CharacterList) {
            if (!myEditText.getText().toString().isEmpty()) {
                Id += myEditText.getText();
            }
        }
        if (Id.length() < plateSize) return false;

        VehicleEviSelected.seteIndex(DataIpat.evidencetArray.size());
        VehicleEviSelected.setcIndex(DataIpat.evidenceByVehicleArray.size());
        VehicleEviSelected.seteId(Id);

        List<String> listMeasureType = DataIpat.getmDescriptionByeType(VehicleEviSelected.geteType());
        for (String measurePoint : listMeasureType) {         // Creo las medidas correspondientes a la evidencia
            MeasureEvi newMeasure = new MeasureEvi(
                    DataIpat.measureEviArray.size(),
                    VehicleEviSelected.geteIndex(),
                    measurePoint,
                    getContext()
            );

            VehicleEviSelected.getmIndexArray().add(newMeasure.getmIndex());
            DataIpat.measureEviArray.add(newMeasure);  // Add Measure
        }

        DataIpat.evidencetArray.add(VehicleEviSelected);  // Add Evidence
        DataIpat.evidenceByVehicleArray.add(new EvidenceByCategory(VehicleEviSelected.geteIndex(), VehicleEviSelected.getcIndex()));
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    private void selectedPlate(typePlate typePlate) {                                                              // Dependiendo del tipo de placa acomodo los EditText y la imagen de fondo
        switch (typePlate) {
            case PARTICULAR: // Particular ABC 123
                plateSize = 6;
                clearAllCharacters();
                imgIconVeihicle.setVisibility(View.VISIBLE);
                imgIconVeihicle.setImageResource(R.drawable.icontransito_black);
                imgSelectedPlate.setImageResource(R.drawable.placa_particular_xl);
                imgSelectedPlate.invalidate();
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(1).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(0).requestFocus();
                break;
            case MOTO: // Moto ABC 12 - ABC 12A
                plateSize = 5;
                clearAllCharacters();
                imgIconVeihicle.setVisibility(View.VISIBLE);
                imgIconVeihicle.setImageResource(R.drawable.icontransito_black);
                imgSelectedPlate.setImageResource(R.drawable.placa_particular_xl);
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(1).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(0).requestFocus();
                break;
            case CLASICO: // Clasico ABC 123
                plateSize = 6;
                clearAllCharacters();
                imgIconVeihicle.setVisibility(View.VISIBLE);
                imgIconVeihicle.setImageResource(R.drawable.icontransito_black);
                imgSelectedPlate.setImageResource(R.drawable.placa_particular_xl);
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(1).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(0).requestFocus();
                break;
            case PUBLICO: // Publicos
                plateSize = 6;
                clearAllCharacters();
                imgIconVeihicle.setVisibility(View.VISIBLE);
                imgIconVeihicle.setImageResource(R.drawable.icontransito_black);
                imgSelectedPlate.setImageResource(R.drawable.placa_publico_xl);
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(1).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(0).requestFocus();
                break;
            case DIPLOVIEJO: // Diplomaticos Vieja
                plateSize = 6;
                clearAllCharacters();
                imgIconDiplomatico.setVisibility(View.VISIBLE);
                imgIconDiplomatico.setImageResource(R.drawable.icontransito_white);
                imgSelectedPlate.setImageResource(R.drawable.placa_especiales_xl);
                setWhiteColorText();
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(1).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(0).requestFocus();
                break;
            case DIPLONUEVO: // Diplomaticos Nueva
                plateSize = 6;
                clearAllCharacters();
                imgIconVeihicle.setVisibility(View.VISIBLE);
                imgIconVeihicle.setImageResource(R.drawable.icontransito_black);
                imgSelectedPlate.setImageResource(R.drawable.placa_extranjera_xl);
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(1).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(0).requestFocus();
                break;
            case CARGA: // Carga
                plateSize = 5;
                clearAllCharacters();
                imgIconCarga.setVisibility(View.VISIBLE);
                imgIconCarga.setImageResource(R.drawable.icontransito_white);
                imgSelectedPlate.setImageResource(R.drawable.placa_carga_xl);
                setWhiteColorText();
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(0).setEnabled(false);
                myEdt_CharacterList.get(0).setText("T");
                myEdt_CharacterList.get(1).setVisibility(View.GONE);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(2).requestFocus();
                break;
            case REMOLQUE: // Remolque
                plateSize = 5;
                clearAllCharacters();
                imgIconCarga.setVisibility(View.VISIBLE);
                imgIconCarga.setImageResource(R.drawable.icontransito_white);
                imgSelectedPlate.setImageResource(R.drawable.placa_carga_xl);
                setWhiteColorText();
                myEdt_CharacterList.get(0).setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                myEdt_CharacterList.get(0).setEnabled(false);
                myEdt_CharacterList.get(0).setText("R");
                myEdt_CharacterList.get(1).setVisibility(View.GONE);
                myEdt_CharacterList.get(2).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(3).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(4).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(5).setInputType(InputType.TYPE_CLASS_NUMBER);
                myEdt_CharacterList.get(2).requestFocus();
                break;
        }
    }

    private CharSequence myTextChanged(CharSequence s, int indexEditText) {                                  // Si no viene nada es por que es para borrar
        if (s.length() > 0 && s.length() < 2) {                                                            // Si accion de el dato corresponde a letra o numero segun sea
            if (validateDataType(s, indexEditText)) {
                if (!s.toString().equals(myCharacterList.get(indexEditText).toString())) {
                    flagFromCode = true;                                                                    // Bandera para evitar que escriba dos numeros iguales
                    myCharacterList.set(indexEditText, s.toString().toUpperCase());                      // almaceno el numero en memoria
                    myEdt_CharacterList.get(indexEditText).setText(myCharacterList.get(indexEditText));  // reescribo sobre el EditText
                } else if (flagFromCode) {                                                                  // Si el evento es generado por codigo
                    flagFromCode = false;
                    setFocus(indexEditText, true);
                    return myCharacterList.get(indexEditText);
                } else if (!flagFromCode) {                                                                  // Si el evento es fgenerado por el usuario
                    setFocus(indexEditText, true);
                    if (myEdt_CharacterList.get(indexEditText).getSelectionEnd() > 0)
                        return myCharacterList.get(indexEditText);
                    else return "";
                }
                setFocus(indexEditText, true);
                return myCharacterList.get(indexEditText);
            }
            myCharacterList.set(indexEditText, "");
            return "";
        } else {                                                                                            // si no trae nada es que quiero borra
            if (!myCharacterList.get(indexEditText).equals("")) {
                myCharacterList.set(indexEditText, "");
                myEdt_CharacterList.get(indexEditText).setText("");
            }
            setFocus(indexEditText, false);
            return "";
        }
    }

    private boolean validateDataType(CharSequence src, int indexEditText) {
        if (myEdt_CharacterList.get(indexEditText).getInputType() == InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS) {
            if (src.toString().matches("[a-zA-Z ]")) {
                return true;
            }
        } else if (myEdt_CharacterList.get(indexEditText).getInputType() == InputType.TYPE_CLASS_NUMBER) {
            if (src.toString().matches("[0-9 ]")) {
                return true;
            }
        }
        return false;
    }

    private void setFocus(int indexEditText, boolean dirRigth) {
        if (dirRigth) {                                                                                                         // Siguiente a la derecha
            if (indexEditText > -1 && indexEditText < 5)                                                                         // El ultimo campo
                if (myEdt_CharacterList.get(indexEditText + 1).getVisibility() == View.VISIBLE) {
                    myEdt_CharacterList.get(indexEditText + 1).requestFocus();
                } else setFocus((indexEditText + 1), dirRigth);
            if (indexEditText == 5) {
                myEdt_CharacterList.get(indexEditText).clearFocus();
                myEdt_CharacterList.get(indexEditText).requestFocus();
            }
        } else {                                                                                                                // Siguiente a la hizquierda
            if (indexEditText > 0 && indexEditText < 6) {
                if (myEdt_CharacterList.get(indexEditText - 1).getVisibility() == View.VISIBLE
                        && myEdt_CharacterList.get(indexEditText - 1).isEnabled()) {                                                     // Valida que este visible sino lo salta
                    myEdt_CharacterList.get(indexEditText - 1).requestFocus();
                } else if (myEdt_CharacterList.get(indexEditText - 1).isEnabled())
                    setFocus((indexEditText - 1), dirRigth);      // La validacion es para que no siga hacia atras sie el editext esta desabilitado
            } else if (indexEditText == 0) {                                                                                        // El primer campo
                myEdt_CharacterList.get(indexEditText).clearFocus();
                myEdt_CharacterList.get(indexEditText).requestFocus();
            }
        }
    }

    private void setWhiteColorText() {
        for (EditText myEditText : myEdt_CharacterList) {
            myEditText.setTextColor(Color.WHITE);
        }
        txtCiudad.setTextColor(Color.WHITE);
    }

    private void clearAllCharacters() {
        for (EditText myEditText : myEdt_CharacterList) {
            myEditText.setText("");
            myEditText.setEnabled(true);
            myEditText.setTextColor(Color.BLACK);
            myEditText.setVisibility(View.VISIBLE);
        }
        imgIconCarga.setImageResource(R.drawable.icontransito_black);
        imgIconVeihicle.setImageResource(R.drawable.icontransito_black);
        imgIconCarga.setImageResource(R.drawable.icontransito_black);

        imgIconCarga.setVisibility(View.GONE);
        imgIconDiplomatico.setVisibility(View.GONE);
        imgIconVeihicle.setVisibility(View.GONE);
        txtLabel.setVisibility(View.GONE);
        rlyPlates.setVisibility(View.VISIBLE);
        txtCiudad.setTextColor(Color.BLACK);
    }
//////////////////////////////////////////////////////////////////////////////////////////////

}