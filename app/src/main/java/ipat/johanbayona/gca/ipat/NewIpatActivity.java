package ipat.johanbayona.gca.ipat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

import ipat.johanbayona.gca.ipat.Adapters.SlidingTabLayout;
import ipat.johanbayona.gca.ipat.Adapters.NewIpatTabsPagerAdapter;
import ipat.johanbayona.gca.ipat.File.FileStorage;
import ipat.johanbayona.gca.ipat.NewIPATTabs.TabSketch;

public class NewIpatActivity extends FragmentActivity {
//    FragmentTabHost ipatTabs;
//    Animation animación;

    Toolbar toolbar;
    ViewPager pager;
    NewIpatTabsPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Croquis","Lugar","Vias","Vehículos","Victimas","Hipotesis"};
    int Numboftabs =6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);                             // Set portrait orientation
        setContentView(R.layout.activity_newipat);

        adapter =  new NewIpatTabsPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs); // Creating The NewIpatTabsPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.

        pager = (ViewPager) findViewById(R.id.pager);   // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);// Assiging the Sliding Tab Layout View
        tabs.setCustomTabView(R.layout.adapter_tabsnewipat);
        //Error no funciono y me toco cambiarlo direcgtamente
        //tabs.setSelectedIndicatorColors(getResources().getColor(R.color.tabsScrollColor));
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setViewPager(pager);   // Setting the ViewPager For the SlidingTabsLayout

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);     // Que no saque el teclado

        CreateIpat();

    }

    private void CreateIpat()
    {
        Random rnd = new Random();
        FileStorage.myFileStorage.createIpatFolder(this, "1234" + rnd.nextLong());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {

            AlertDialog deleteAlert = new AlertDialog.Builder(this).create();
            deleteAlert.setTitle("Salir");
            deleteAlert.setMessage("Esta seguro que quiere salir de Ipat, recuerde que se perdera la información.");
            //deleteAlert.setIcon(R.drawable.delete);
            deleteAlert.setButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FileStorage.myFileStorage.deleteIpatFolder(getBaseContext());
                    finish();
                    overridePendingTransition(R.anim.ani_activitypull_in_left, R.anim.ani_activitypush_out_right);
                }
            });
            deleteAlert.setButton2("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            deleteAlert.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.setReturnEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
