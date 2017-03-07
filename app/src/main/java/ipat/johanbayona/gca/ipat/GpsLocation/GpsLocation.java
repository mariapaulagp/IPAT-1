package ipat.johanbayona.gca.ipat.GpsLocation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ipat.johanbayona.gca.ipat.R;

/**
 * Created by junio on 11/05/2016.
 */
public class GpsLocation implements LocationListener {

    Animation ani_BtnSelected;

    public static double lastLongitude, lastLatitude;
    private Context ctx;
    LocationManager locationManager;
    String proveedor;
    private boolean networkOn;

    public GpsLocation(Context ctx) {
        this.ctx = ctx;

        ani_BtnSelected = AnimationUtils.loadAnimation (ctx, R.anim.ani_btnselected);
        //turnGPSOn();
    }

    public void turnGPSOn(){

//        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            Intent intent = new Intent();
//            intent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            intent.setData(Uri.parse("3"));
//            ctx.sendBroadcast(intent);
//        }

        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ctx, "Error de GPS", Toast.LENGTH_SHORT).show();
        }else {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                LayoutInflater inflaterPR = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                View dialoglayoutPR = inflaterPR.inflate(R.layout.dialog_gps_enable, null);
                final ImageView btnPuntoEnebleOK = (ImageView) dialoglayoutPR.findViewById(R.id.BtnReOK);

                final AlertDialog.Builder builderPR = new AlertDialog.Builder(ctx);
                builderPR.setCancelable(false);
                builderPR.setView(dialoglayoutPR);
                //builder.show().getWindow().setLayout(950,350);
                final AlertDialog showPR = builderPR.show();

                btnPuntoEnebleOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        btnPuntoEnebleOK.startAnimation(ani_BtnSelected);

                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        ctx.startActivity(intent);
                        showPR.dismiss();
                    }
                });
            }else Toast.makeText(ctx, "GPS Activado", Toast.LENGTH_LONG).show();
        }
    }


    public void startGPS()
    {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ctx, "Error Iniciando GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        proveedor = LocationManager.GPS_PROVIDER;
        networkOn = locationManager.isProviderEnabled(proveedor);
        locationManager.requestLocationUpdates(proveedor, 1000, 0, this);
    }

    public void stopGPS()
    {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ctx, "Error Iniciando GPS", Toast.LENGTH_SHORT).show();
            return;
        }
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            locationManager = null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (networkOn) {
            if(location != null)
            {
                lastLongitude = location.getLongitude();
                lastLatitude = location.getLatitude();
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(ctx, "GPS Activado", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(ctx, "GPS Desactivado", Toast.LENGTH_LONG).show();
    }
}
