package ipat.johanbayona.gca.ipat.NewEvidence;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ipat.johanbayona.gca.ipat.Adapters.SketchMenuSideAdapter;
import ipat.johanbayona.gca.ipat.R;

import static android.widget.Toast.LENGTH_SHORT;

public class AddVehiclePage2_Fragment extends Fragment {
    private View view;

    private GridView placaLista;
    private TextView ciudad;
    private EditText texto_entrada;
    private EditText texto_entrada2;
    private EditText texto_entrada3;
    private EditText texto_entrada4;
    private EditText texto_entrada5;
    private EditText texto_entrada6;
    private EditText temporal;
    private EditText temporal2;
    private Button button;
    private Spinner spinner;

    ImageView area2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addvehiclepage1__fragment, container, false);
        //  rlyContent = (RelativeLayout) view.findViewById(R.id.Areaimagen);

        //__________________________  __________________________
        texto_entrada = (EditText) view.findViewById(R.id.edit_texto);

        placaLista = (GridView) view.findViewById(R.id.Lista_placas);
        texto_entrada2 = (EditText) view.findViewById(R.id.edit_texto2);
        texto_entrada3 = (EditText) view.findViewById(R.id.edit_texto3);
        texto_entrada4 = (EditText) view.findViewById(R.id.edit_texto4);
        texto_entrada5 = (EditText) view.findViewById(R.id.edit_texto5);
        texto_entrada6 = (EditText) view.findViewById(R.id.edit_texto6);
        temporal = (EditText) view.findViewById(R.id.edit_temp);
        temporal2 = (EditText) view.findViewById(R.id.edit_temp2);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<String> spinners = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ciudades));
        spinner.setAdapter(spinners);
        ciudad = (TextView) view.findViewById(R.id.ciudad);
        //__________________________________________________________________________________--
        placaLista = (GridView) view.findViewById(R.id.Lista_placas);
        //___________________________________________________________________________________
        button = (Button) view.findViewById(R.id.guardar);
        area2 = (ImageView) view.findViewById(R.id.area2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selecteditem = spinner.getSelectedItem().toString();
                ciudad.setText(selecteditem);
                int id_ciudad = spinner.getSelectedItemPosition();
                Toast.makeText(view.getContext(), "Usted selecciono : " + getResources().getStringArray(R.array.ciudades)[id_ciudad], LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String letters = String.valueOf(texto_entrada.getText() + "" + texto_entrada2.getText() + "" + texto_entrada3.getText() + "" + temporal2.getText());
                String number = String.valueOf(texto_entrada4.getText() + "" + texto_entrada5.getText() + "" + texto_entrada6.getText() + "" + temporal.getText());

                Bitmap imagen = area2.getDrawingCache();
                Intent intent2 = new Intent();

                intent2.putExtra("b", imagen);
                intent2.putExtra("text", letters);
                intent2.putExtra("numbers", number);
                //  intent2.setClass(getActivity(), Menu_Opciones.class);
                startActivity(intent2);
            }

        });

        /*placa_amarilla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area2.setImageResource(R.drawable.amarilla_editable);
                verificarTexto(texto_entrada, texto_entrada2);
                verificarTexto(texto_entrada2, texto_entrada3);
                verificarTexto(texto_entrada3, texto_entrada4);
                verificarNumero(texto_entrada4, texto_entrada5);
                verificarNumero(texto_entrada5, texto_entrada6);
                texto_entrada6.setVisibility(View.VISIBLE);
                verificarNumero(texto_entrada6, texto_entrada);
                temporal2.setVisibility(View.INVISIBLE);//EditText adicional no se muestra ,para cambio con EditText6
                temporal.setVisibility(view.INVISIBLE);//EditText adicional no se muestra,para cambio con EditText3
                texto_entrada2.setVisibility(View.VISIBLE);
                texto_entrada3.setVisibility(View.VISIBLE);
                area2.buildDrawingCache();
                borrar();
            }
        });


        placa_amarilla_moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area2.setImageResource(R.drawable.amarilla_editable);
                verificarTexto(texto_entrada, texto_entrada2);
                verificarTexto(texto_entrada2, texto_entrada3);
                verificarTexto(texto_entrada3, texto_entrada4);
                verificarNumero(texto_entrada4, texto_entrada5);
                verificarNumero(texto_entrada5, temporal);
                verificarTexto(temporal, texto_entrada);
                temporal.setVisibility(View.VISIBLE);//remplaza a EditText6
                temporal2.setVisibility(View.INVISIBLE);
                texto_entrada2.setVisibility(View.VISIBLE);
                texto_entrada3.setVisibility(View.VISIBLE);
                texto_entrada6.setVisibility(View.INVISIBLE);
                area2.buildDrawingCache();
                borrar();
            }
        });

        placa_blanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area2.setImageResource(R.drawable.placas_blanca_editable);
                verificarTexto(texto_entrada, texto_entrada2);
                verificarTexto(texto_entrada2, texto_entrada3);
                verificarTexto(texto_entrada3, texto_entrada4);
                verificarNumero(texto_entrada4, texto_entrada5);
                verificarNumero(texto_entrada5, texto_entrada6);
                verificarNumero(texto_entrada6, texto_entrada);
                temporal.setVisibility(view.INVISIBLE);
                temporal2.setVisibility(View.INVISIBLE);
                texto_entrada6.setVisibility(View.VISIBLE);
                texto_entrada2.setVisibility(View.VISIBLE);
                texto_entrada3.setVisibility(View.VISIBLE);
                area2.buildDrawingCache();
                borrar();
            }
        });

        placa_azul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area2.setImageResource(R.drawable.placas_azul_editable);
                verificarTexto(texto_entrada, texto_entrada2);
                texto_entrada2.setVisibility(View.VISIBLE);
                texto_entrada3.setVisibility(View.INVISIBLE);
                verificarTexto(texto_entrada2, temporal2);
                verificarNumero(temporal2, texto_entrada4);
                verificarNumero(texto_entrada4, texto_entrada5);
                verificarNumero(texto_entrada5, texto_entrada6);
                verificarNumero(texto_entrada6, texto_entrada);
                temporal2.setVisibility(View.VISIBLE);
                texto_entrada6.setVisibility(View.VISIBLE);
                temporal.setVisibility(view.INVISIBLE);
                area2.buildDrawingCache();
                borrar();

            }
        });

        placa_azul_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area2.setImageResource(R.drawable.diplomatica_nueva);
                verificarTexto(texto_entrada, texto_entrada2);
                verificarTexto(texto_entrada2, texto_entrada3);
                verificarTexto(texto_entrada3, texto_entrada4);
                verificarNumero(texto_entrada4, texto_entrada5);
                verificarNumero(texto_entrada5, texto_entrada6);
                texto_entrada6.setVisibility(View.VISIBLE);
                verificarNumero(texto_entrada6, texto_entrada);
                temporal2.setVisibility(View.INVISIBLE);
                temporal.setVisibility(View.INVISIBLE);
                texto_entrada3.setVisibility(View.VISIBLE);
                texto_entrada2.setVisibility(View.VISIBLE);
                area2.buildDrawingCache();
                borrar();

            }
        });

        placa_roja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area2.setImageResource(R.drawable.placa_roja_editable);
                verificarTexto(texto_entrada, temporal2);
                verificarNumero(temporal2, texto_entrada4);
                verificarNumero(texto_entrada4, texto_entrada5);
                verificarNumero(texto_entrada5, texto_entrada6);
                verificarNumero(texto_entrada6, texto_entrada);
                texto_entrada6.setVisibility(View.VISIBLE);
                temporal2.setVisibility(View.VISIBLE);
                texto_entrada3.setVisibility(View.INVISIBLE);
                texto_entrada2.setVisibility(View.INVISIBLE);
                temporal.setVisibility(view.INVISIBLE);
                area2.buildDrawingCache();
                borrar();
            }
        });*/

        Bundle b=new Bundle();
        int  index=b.getInt("num");
        return view;
    }
    public void recibe_datos(final int index) {
        switch (index) {
            case 1:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placasAuto), getResources().obtainTypedArray(R.array.placasAutoIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   /* area2.setImageResource(R.drawable.amarilla_editable);
                    verificarTexto(texto_entrada, texto_entrada2);
                    verificarTexto(texto_entrada2, texto_entrada3);
                    verificarTexto(texto_entrada3, texto_entrada4);
                    verificarNumero(texto_entrada4, texto_entrada5);
                    verificarNumero(texto_entrada5, texto_entrada6);
                    texto_entrada6.setVisibility(View.VISIBLE);
                    verificarNumero(texto_entrada6, texto_entrada);
                    temporal2.setVisibility(View.INVISIBLE);//EditText adicional no se muestra ,para cambio con EditText6
                    temporal.setVisibility(view.INVISIBLE);//EditText adicional no se muestra,para cambio con EditText3
                    texto_entrada2.setVisibility(View.VISIBLE);
                    texto_entrada3.setVisibility(View.VISIBLE);
                    area2.buildDrawingCache();
                    borrar();*/

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
//                            rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_particular_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            verificarTexto(texto_entrada2, texto_entrada3);
                            verificarTexto(texto_entrada3, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            verificarNumero(texto_entrada6, texto_entrada);
                            temporal2.setVisibility(View.INVISIBLE);//EditText adicional no se muestra ,para cambio con EditText6
                            temporal.setVisibility(View.INVISIBLE);//EditText adicional no se muestra,para cambio con EditText3
                            texto_entrada2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.VISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                        if (i == 1) {
                            area2.setImageResource(R.drawable.placa_especiales_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            texto_entrada2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.INVISIBLE);
                            verificarTexto(texto_entrada2, temporal2);
                            verificarNumero(temporal2, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            temporal2.setVisibility(View.VISIBLE);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            temporal.setVisibility(View.INVISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                        if (i == 2) {
                            area2.setImageResource(R.drawable.placa_extranjera_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            verificarTexto(texto_entrada2, texto_entrada3);
                            verificarTexto(texto_entrada3, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            verificarNumero(texto_entrada6, texto_entrada);
                            temporal2.setVisibility(View.INVISIBLE);
                            temporal.setVisibility(View.INVISIBLE);
                            texto_entrada3.setVisibility(View.VISIBLE);
                            texto_entrada2.setVisibility(View.VISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                        if (i == 3) {
                            area2.setImageResource(R.drawable.placa_publico_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            verificarTexto(texto_entrada2, texto_entrada3);
                            verificarTexto(texto_entrada3, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            temporal.setVisibility(view.INVISIBLE);
                            temporal2.setVisibility(View.INVISIBLE);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            texto_entrada2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.VISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                    }
                });
                break;

            case 2:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placasMoto), getResources().obtainTypedArray(R.array.placasMotoIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            //  rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_particular_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            verificarTexto(texto_entrada2, texto_entrada3);
                            verificarTexto(texto_entrada3, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, temporal);
                            verificarTexto(temporal, texto_entrada);
                            temporal.setVisibility(View.VISIBLE);//remplaza a EditText6
                            temporal2.setVisibility(View.INVISIBLE);
                            texto_entrada2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.VISIBLE);
                            texto_entrada6.setVisibility(View.INVISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                    }
                });
                break;


            case 3:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placasbus), getResources().obtainTypedArray(R.array.placasBusIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            // rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_particular_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            verificarTexto(texto_entrada2, texto_entrada3);
                            verificarTexto(texto_entrada3, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            verificarNumero(texto_entrada6, texto_entrada);
                            temporal2.setVisibility(View.INVISIBLE);//EditText adicional no se muestra ,para cambio con EditText6
                            temporal.setVisibility(View.INVISIBLE);//EditText adicional no se muestra,para cambio con EditText3
                            texto_entrada2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.VISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                        if (i == 1) {
                            area2.setImageResource(R.drawable.placa_publico_xl);
                            verificarTexto(texto_entrada, texto_entrada2);
                            verificarTexto(texto_entrada2, texto_entrada3);
                            verificarTexto(texto_entrada3, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            temporal.setVisibility(view.INVISIBLE);
                            temporal2.setVisibility(View.INVISIBLE);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            texto_entrada2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.VISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                    }
                });
                break;

            case 4:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placasCamion), getResources().obtainTypedArray(R.array.placasCamionIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            //  rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_carga_xl);
                            verificarTexto(texto_entrada, temporal2);
                            verificarNumero(temporal2, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            temporal2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.INVISIBLE);
                            texto_entrada2.setVisibility(View.INVISIBLE);
                            temporal.setVisibility(view.INVISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                        if (i == 1) {
                        }
                    }
                });
                break;
            case 5:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placaBici), getResources().obtainTypedArray(R.array.placasBiciIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            //rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_carga_xl);
                            verificarTexto(texto_entrada, temporal2);
                            verificarNumero(temporal2, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            temporal2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.INVISIBLE);
                            texto_entrada2.setVisibility(View.INVISIBLE);
                            temporal.setVisibility(view.INVISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                    }
                });
                break;

            case 6:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placasMaqui), getResources().obtainTypedArray(R.array.placasMaquiIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     /*   area2.setImageResource(R.drawable.placa_roja_editable);
                        verificarTexto(texto_entrada, temporal2);
                        verificarNumero(temporal2, texto_entrada4);
                        verificarNumero(texto_entrada4, texto_entrada5);
                        verificarNumero(texto_entrada5, texto_entrada6);
                        verificarNumero(texto_entrada6, texto_entrada);
                        texto_entrada6.setVisibility(View.VISIBLE);
                        temporal2.setVisibility(View.VISIBLE);
                        texto_entrada3.setVisibility(View.INVISIBLE);
                        texto_entrada2.setVisibility(View.INVISIBLE);
                        temporal.setVisibility(view.INVISIBLE);
                        area2.buildDrawingCache();
                        borrar();*/

                        if (i == 0) {
                            //   rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_carga_xl);
                            verificarTexto(texto_entrada, temporal2);
                            verificarNumero(temporal2, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            temporal2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.INVISIBLE);
                            texto_entrada2.setVisibility(View.INVISIBLE);
                            temporal.setVisibility(view.INVISIBLE);
                            area2.buildDrawingCache();
                            borrar();

                        }
                    }
                });
                break;
            case 7:
                placaLista.setAdapter(new SketchMenuSideAdapter((Activity) view.getContext(), getResources().getStringArray(R.array.placasTracc), getResources().obtainTypedArray(R.array.placasTracciIcon), 2));
                placaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            // rlyContent.setVisibility(View.VISIBLE);
                            area2.setImageResource(R.drawable.placa_carga_xl);
                            verificarTexto(texto_entrada, temporal2);
                            verificarNumero(temporal2, texto_entrada4);
                            verificarNumero(texto_entrada4, texto_entrada5);
                            verificarNumero(texto_entrada5, texto_entrada6);
                            verificarNumero(texto_entrada6, texto_entrada);
                            texto_entrada6.setVisibility(View.VISIBLE);
                            temporal2.setVisibility(View.VISIBLE);
                            texto_entrada3.setVisibility(View.INVISIBLE);
                            texto_entrada2.setVisibility(View.INVISIBLE);
                            temporal.setVisibility(view.INVISIBLE);
                            area2.buildDrawingCache();
                            borrar();
                        }
                    }
                });
                break;
        }
    }

    public void verificarTexto(EditText texto, final EditText focus) {
        texto.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1) {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.equals("")) {
                    return src;
                }
                if (src.toString().matches("[a-zA-Z ]")) {
                    focus.requestFocus();
                    return src;
                }
                return "";
            }
        }
        });
    }

    public void verificarNumero(final EditText texto2, final EditText focus) {
        texto2.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                if (src.equals("")) {
                    return src;
                }
                if (src.toString().matches("[0-9 ]")) {

                    focus.requestFocus();
                    return src;
                }
                return "";
            }
        }
        });
    }
    public void borrar() {
        texto_entrada.setText("");
        texto_entrada2.setText("");
        texto_entrada3.setText("");
        texto_entrada4.setText("");
        texto_entrada5.setText("");
        texto_entrada6.setText("");
        temporal.setText("");
        temporal2.setText("");

    }
}