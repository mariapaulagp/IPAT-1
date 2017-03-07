package ipat.johanbayona.gca.ipat.Compass;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v4.app.Fragment;

import ipat.johanbayona.gca.ipat.R;
import ipat.johanbayona.gca.ipat.GpsLocation.GpsLocation;

/**
 * Created by JohanBayona on 2/11/2016.
 */

public class CompassActivity extends Fragment {
    View view;
    ImageView i;
    TextView txtGrados, txtLongitud, txtLatitud;

    private static SensorManager sensorService;                                                                            //sensor manager del dispositivo
    private Sensor sensor;
    public float currentDegree = 0f;

    private static GpsLocation gpsLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_compass, container, false);
        i = (ImageView) view.findViewById(R.id.brujula2);                                                               //inicializa la imagen de la brujula
        txtGrados = (TextView) view.findViewById(R.id.TxtDegrees);                                                                     //inicializa el textview para mostrar el grado de rotación
        txtLongitud = (TextView) view.findViewById(R.id.TxtLongitud);
        txtLatitud = (TextView) view.findViewById(R.id.TxtLatitud);

        sensorService = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        gpsLocation =  new GpsLocation(view.getContext());

        return view;
    }

    public void startCompass()
    {
        gpsLocation.startGPS();
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            int degree = Math.round(event.values[0]);
            int gradeShow = Math.abs(degree - 360);
            txtGrados.setText(  Integer.toString(gradeShow) + (char) 0x00B0 + " N");
            txtLongitud.setText(  "" + gpsLocation.lastLatitude);
            txtLatitud.setText(  "" + gpsLocation.lastLongitude);

            RotateAnimation ra = new RotateAnimation(currentDegree, -degree, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            ra.setDuration(1000);
            ra.setFillAfter(true);

            i.startAnimation(ra);
            currentDegree=-degree;
        }
    };

    public void stopCompass()
    {
        gpsLocation.stopGPS();
        if (sensor != null) {
            sensor = null;
            sensorService.unregisterListener(mySensorEventListener);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        gpsLocation.turnGPSOn();
        if(sensor!=null){                                                                                       //mediante la clase SensorEventListener se utiliza para el sensor de orientación del dispositivo a la
            sensorService.registerListener(mySensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onPause(){                                                                                   //método onPause(), elimina el registro del SensorEventListener cuando se detiene la actividad.
        super.onPause();
        if (sensor != null) {
            sensorService.unregisterListener(mySensorEventListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            sensor = null;
            sensorService.unregisterListener(mySensorEventListener);
        }
    }
    /*
        En el código anterior, la SensorEvent es el parámetro para el método onSensorChanged ().

        El grado de rotación se determina por el código float degree=Math.round(event.values[0]);y se muestra en la TextView usando el setText()método.

        El tamaño y los datos de la valuesmatriz depende del tipo de sensor.
        En caso del sensor de orientación, la valuesmatriz contiene los siguientes valores:

        valores [0]: ángulo entre la dirección norte magnético y el eje y alrededor del eje z (0 a 359), donde 0 = Norte, 90 = Medio, 180 = Sur, 270 = West.
        valores [1]: Rotación alrededor del eje x (-180 a 180), con valores positivos cuando El eje z se mueve hacia el eje y.
        valores [2]: Rotación alrededor del eje x, (-90 a 90), aumentando a medida que el dispositivo se mueve hacia la derecha.
        Después de que un objeto RotateAnimation se utiliza para girar la brújula como el dispositivo se hace girar. El constructor de la clase RotateAnimation toma los siguientes parámetros:

        fromDegrees: desplazamiento en el inicio de la animación de rotación.
        toDegrees: desplazamiento al final de la animación de rotación.
        pivotXType: Interpretación de pivotXValue. Puede ser Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF o Animation.RELATIVE_TO_PARENT.
        pivotXValue: coordenada X del punto sobre el que se está haciendo girar el objeto.
        pivotYType: Interpretación de pivotYValue. Puede ser Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF o Animation.RELATIVE_TO_PARENT.
        pivotYValue: coordenada Y del punto sobre el que se está haciendo girar el objeto.
        El setDuration()método de la clase de Animación se utiliza para especificar la duración en milisegundos para que la animación debe durar.

        El setFillAfter()método de la clase de Animación se utiliza para persistir la transformación producida por esta animación.

        El startAnimation()método de la clase de Animación se utiliza para iniciar la animación .

        Por último, el currentDegree se actualiza con el valor de -Grado de modo que la siguiente animación se iniciará desde la nueva posición.
     */

}
