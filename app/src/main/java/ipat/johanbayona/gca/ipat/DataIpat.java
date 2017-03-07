package ipat.johanbayona.gca.ipat;

import java.util.ArrayList;
import java.util.List;

import ipat.johanbayona.gca.ipat.Adapters.EvidenceTableAdapter;
import ipat.johanbayona.gca.ipat.Adapters.MeasureTableAdapter;
import ipat.johanbayona.gca.ipat.DataModels.EvidenceByCategory;
import ipat.johanbayona.gca.ipat.DataModels.Evidence;
import ipat.johanbayona.gca.ipat.DataModels.MeasureEvi;

/**
 * Created by JohanBayona on 15/12/2016.
 */

public class DataIpat {

    public static ArrayList<Evidence> evidencetArray = new ArrayList<Evidence>();                                   // Arreglo de todas las evidencias creadas
    public static ArrayList<MeasureEvi> measureEviArray = new ArrayList<MeasureEvi>();                              // Arreglo de todas las medidas creadas
    public static MeasureTableAdapter adapterMeasureEvi;                                                              // Adaptador para la tabla de medidas

    public static ArrayList<EvidenceByCategory> evidenceByVehicleArray = new ArrayList<EvidenceByCategory>();       // arreglo de relacion de evidencias de Vehiculos
    public static ArrayList<EvidenceByCategory> evidenceByVictimArray = new ArrayList<EvidenceByCategory>();        // arreglo de relacion de evidencias de Victimas
//    public static ArrayList<EvidenceByCategory> evidenceByBraketraceArray = new ArrayList<EvidenceByCategory>();    // arreglo de relacion de evidencias de Huellas
//    public static ArrayList<EvidenceByCategory> evidenceByObjectArray = new ArrayList<EvidenceByCategory>();        // arreglo de relacion de evidencias de Objetos
//    public static ArrayList<EvidenceByCategory> evidenceByWayArray = new ArrayList<EvidenceByCategory>();           // arreglo de relacion de evidencias de Vias
//    public static ArrayList<EvidenceByCategory> evidenceBySignalsArray = new ArrayList<EvidenceByCategory>();       // arreglo de relacion de evidencias de Señales

    public static ArrayList<EvidenceByCategory> evidenceByOtherArray = new ArrayList<EvidenceByCategory>();                               // arreglo Para la tabla de otros que son Huellas, Vias y Objetos
    public static EvidenceTableAdapter adapterEvidenceByVehicle;                                                    // Adaptador para la tabla de Vehiculos
    public static EvidenceTableAdapter adapterEvidenceByVictim;                                                     // Adaptador para la tabla de Victimas
    public static EvidenceTableAdapter adapterEvidenceByOther;                                                      // Adaptador para la tabla de Otros


//
//    public Evidence getEvidenceBymIndex(int mIndex){                                                                         // Obtener obj de evidencia a partir de una medida
//        for(MeasureEvi myMeasure: this.measureEviArray){
//            if(myMeasure.getmIndex() == mIndex) return myMeasure;
//        }
//        return null;
//    }
//
//    public List<MeasureEvi> getMeasureByeIndex(int eIndex){                                                                       // Obtener lista de medida apartir de una evidencia
//
//    }


// CATALOGOS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static enum EvidenceCategory {
        VEHICULO, VICTIMA, HUELLA, SEÑAL, OBJETO,VIA
    }

    public static enum EvidenceType {
        AUTOMOVIL, MOTO, BUS, CAMION,
        BICICLETA, MAQUINARIA, TRACCION,

        PERSONA, ANIMAL,

        FRENADO, ARRASTRE, PINTURA,

        PREVENTIVA, INFORMATIVA, REGLAMETARIA,
        TEMPORAL, REDUCTOR, SEMAFORO ,

        FIJO, MOVIL, VIA,

        PARTE, DESCONOCIDO, GENERAL;
    }

    public static enum stateAction{
        Create, Edit, Delete,
    }

    public static List<String> getmDescriptionByeType(EvidenceType typeEvidence) {
        List myEvidences = new ArrayList<String>();
        switch (typeEvidence){
            case AUTOMOVIL:
                myEvidences.add("Vértice anterior");
                myEvidences.add("Vértice posterior");
                return myEvidences;
            case MOTO:
                myEvidences.add("Eje Tracero");
                myEvidences.add("Eje Delantero");

                return myEvidences;
            case BUS:
                myEvidences.add("Eje Tracero");
                myEvidences.add("Eje Delantero");

                return myEvidences;
            case CAMION:
                myEvidences.add("Eje Tracero");
                myEvidences.add("Eje Delantero");

                return myEvidences;
            case BICICLETA:
                myEvidences.add("Eje Tracero");
                myEvidences.add("Eje Delantero");

                return myEvidences;
            case MAQUINARIA:
                myEvidences.add("Eje Tracero");
                myEvidences.add("Eje Delantero");

                return myEvidences;
            case TRACCION:
                myEvidences.add("Eje Tracero");
                myEvidences.add("Eje Delantero");

                return myEvidences;
            case PERSONA:
                myEvidences.add("Cabeza");
                myEvidences.add("Pies");
                return myEvidences;
            case ANIMAL:
                myEvidences.add("Cabeza");
                myEvidences.add("Cola");
                return myEvidences;
            case FRENADO:
                myEvidences.add("Inicio");
                myEvidences.add("Fin");
                return myEvidences;
            case ARRASTRE:
                myEvidences.add("Inicio");
                myEvidences.add("Fin");
                return myEvidences;
            case PINTURA:
                myEvidences.add("Inicio");
                myEvidences.add("Fin");
                return myEvidences;
            case PREVENTIVA:
                myEvidences.add("No");
                return myEvidences;
            case INFORMATIVA:
                myEvidences.add("No");
                return myEvidences;
            case REGLAMETARIA:
                myEvidences.add("No");
                return myEvidences;
            case TEMPORAL:
                myEvidences.add("No");
                return myEvidences;
            case REDUCTOR:
                myEvidences.add("No");
                return myEvidences;
            case SEMAFORO:
                myEvidences.add("No");
                return myEvidences;
            case FIJO:
                myEvidences.add("No");
                return myEvidences;
            case MOVIL:
                myEvidences.add("No");
                return myEvidences;
            case VIA:
                myEvidences.add("No");
                return myEvidences;
            case GENERAL:
                myEvidences.add("No");
                return myEvidences;
            case DESCONOCIDO:
                myEvidences.add("No");
                return myEvidences;
            case PARTE:
                myEvidences.add("No");
                return myEvidences;
        }
        return myEvidences;
    }

}
