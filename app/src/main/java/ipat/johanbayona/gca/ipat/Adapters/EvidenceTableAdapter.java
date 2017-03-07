package ipat.johanbayona.gca.ipat.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ipat.johanbayona.gca.ipat.DataIpat;
import ipat.johanbayona.gca.ipat.DataModels.Evidence;
import ipat.johanbayona.gca.ipat.DataModels.EvidenceByCategory;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;
import ipat.johanbayona.gca.ipat.R;

import static ipat.johanbayona.gca.ipat.DataIpat.EvidenceCategory.VEHICULO;
import static ipat.johanbayona.gca.ipat.DataIpat.measureEviArray;


public class EvidenceTableAdapter extends ArrayAdapter<EvidenceByCategory> {

    private final Activity context;
    ArrayList<EvidenceByCategory> myEvidenceByCategory;

    public EvidenceTableAdapter(Activity context, ArrayList<EvidenceByCategory> myEvidenceByCategory) {
        super(context, R.layout.adapter_sketchmenuside, myEvidenceByCategory);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.myEvidenceByCategory = myEvidenceByCategory;
    }

    public  void  deleteitem(int indexdelete,DataIpat.EvidenceCategory typeitem)
    {
        try
        {
            //busca item de categoria
            EvidenceByCategory itemCategory = myEvidenceByCategory.get(indexdelete);
            //busca item de evidencia
            Evidence itemEvidence = null;
            for(int pos = 0;pos< DataIpat.evidencetArray.size();pos++)
            {
                if(DataIpat.evidencetArray.get(pos).geteIndex() ==itemCategory.geteIndex() )
                {
                    itemEvidence = DataIpat.evidencetArray.get(pos);
                    break;
                }
            }
            //delete items de mesure
            int ContenoCompleto = itemEvidence.getmIndexArray().size();
            int ConteoCorreto=0;
            List<MeasureEvi> ListaIndexRemove = new ArrayList<MeasureEvi>();
            if(itemEvidence!= null) {
                for (int mIndexDelete:itemEvidence.getmIndexArray())
                {
                    for (MeasureEvi item: measureEviArray)
                    {
                        if(mIndexDelete == item.getmIndex() && itemEvidence.geteIndex() == item.geteIndex())
                        {
                            ListaIndexRemove.add(measureEviArray.get(mIndexDelete));
                            ConteoCorreto++;
                            if(ContenoCompleto == ConteoCorreto)
                            {
                                break;
                            }
                        }
                    }
                }

                for(MeasureEvi Indexelminar : ListaIndexRemove)
                {
                    measureEviArray.remove(Indexelminar);
                }


                //Elmina de categoria
                myEvidenceByCategory.remove(itemCategory);
                //Elimina de Evidencia
                DataIpat.evidencetArray.remove(itemEvidence);

                //hay que indexar
                //1 Local tres globales
                int indexNew = 0;
                int indexActual = 0;
                for(int pos=0; pos<  DataIpat.evidencetArray.size(); pos++)
                {
                    indexActual =  DataIpat.evidencetArray.get(pos).geteIndex();//obtiene el actuali eindex en evidencia
                    DataIpat.evidencetArray.get(pos).seteIndex(indexNew); //Graba el nuevo eindex en evidencias
                    for (int pis=0;pis<myEvidenceByCategory.size();pis++) {
                        if (myEvidenceByCategory.get(pis).geteIndex() == indexActual) {

                            myEvidenceByCategory.get(pis).seteIndex(indexNew);//Envio eindex a categoria
                            DataIpat.evidencetArray.get(pos).setcIndex(pis);//recibo posicion cIndex de categoria
                            myEvidenceByCategory.get(pis).setcIndex(pis);//Actualizo cindex Categoria
                            List<Integer> medidas = new ArrayList<>();
                            for(int pus = 0; pus< measureEviArray.size(); pus++)
                            {
                                if(measureEviArray.get(pus).geteIndex() == indexActual)
                                {
                                    measureEviArray.get(pus).seteIndex(indexNew);//Envio eidnex a medidas
                                    medidas.add(pus); //Recibo uno a uno las medidas en un arreglo de enteros para asiganarlas
                                    measureEviArray.get(pus).setmIndex(pus);//Actualiza setMidnex en medidas
                                }
                            }
                            DataIpat.evidencetArray.get(pos).setmIndexArray(medidas);//Asigno la lista con las nuevas posicioens
                        }
                    }
                    indexNew++;
                }

               /* indexNew = 0;
                for(int pos=0; pos< myEvidenceByCategory.size(); pos++)
                {
                    indexActual = myEvidenceByCategory.get(pos).getcIndex();
                    for (int pus = 0;pus< DataIpat.evidencetArray.size(); pus++) {
                        if(DataIpat.evidencetArray.get(pus).getcIndex() ==indexActual)
                        {
                            DataIpat.evidencetArray.get(pus).setcIndex(indexNew);
                        }
                    }
                    indexNew++;
                }


              //  indexNew = 0;
                List<Integer> misure  = new ArrayList<Integer>();
                int eid = 0;
                for(int pos=0; pos<  DataIpat.measureEviArray.size(); pos++)
                {
                    indexActual = DataIpat.measureEviArray.get(pos).getmIndex();
                    eid = DataIpat.measureEviArray.get(pos).geteIndex();
                    for(int pus=0; pus<DataIpat.evidencetArray.size(); pus++)
                    {
                        if(DataIpat.evidencetArray.get(pus).geteIndex() ==  DataIpat.measureEviArray.get(pos).geteIndex())
                        {
                            for (int mesureID: DataIpat.evidencetArray.get(pus).getmIndexArray())
                            {
                                if(mesureID == indexActual)
                                {
                                    misure.add(indexNew);
                                    DataIpat.measureEviArray.get(pos).setmIndex(indexNew);
                                }
                            }
                            DataIpat.evidencetArray.get(pus).setmIndexArray(misure);
                        }
                    }
                    indexNew++;
                }*//*
*/

                switch(typeitem){
                    case VEHICULO:
                        DataIpat.evidenceByVehicleArray = (ArrayList<EvidenceByCategory>)myEvidenceByCategory.clone();
                        break;
                    case VICTIMA:
                        DataIpat.evidenceByVictimArray= (ArrayList<EvidenceByCategory>)myEvidenceByCategory.clone();
                        break;
                    case OBJETO:
                        DataIpat.evidenceByOtherArray = (ArrayList<EvidenceByCategory>)myEvidenceByCategory.clone();
                        break;
                }

            }
        }
        catch (Exception ex)
        {
            String es = ex.getMessage();
        }


    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_newevidence, null, true);

        EvidenceByCategory currentItem = getItem(position);

        ImageView imgEvidence =     (ImageView) rowView.findViewById(R.id.ImgIcon);
        TextView txtName =          (TextView)  rowView.findViewById(R.id.TxtName);
        TextView txtEvidence =      (TextView)  rowView.findViewById(R.id.TxtEvidence);

        if (currentItem != null) {
           Evidence myEvidence = DataIpat.evidencetArray.get(currentItem.geteIndex());

            if (imgEvidence != null) imgEvidence.setImageDrawable(myEvidence.geteImage().getDrawable());
            if (txtEvidence != null) txtEvidence.setText("" + myEvidence.geteId());
            if (txtName != null) txtName.setText(myEvidence.geteCategory() + " " + (myEvidence.getcIndex() + 1));
        }
        return rowView;
    }
}


