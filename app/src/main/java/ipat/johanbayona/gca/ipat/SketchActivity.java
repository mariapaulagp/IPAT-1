package ipat.johanbayona.gca.ipat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;

import ipat.johanbayona.gca.ipat.Adapters.MeasureTableAdapter;
import ipat.johanbayona.gca.ipat.Adapters.SketchCustViewPager;
import ipat.johanbayona.gca.ipat.Adapters.SketchFragmentPagerAdapter;
import ipat.johanbayona.gca.ipat.Sketch.SketchDrawActivity;
import ipat.johanbayona.gca.ipat.Sketch.SketchPagerAdapter;
import ipat.johanbayona.gca.ipat.Sketch.SketchTableActivity;

import static android.R.attr.fragment;

public class SketchActivity extends FragmentActivity implements MeasureTableAdapter.OnHeadlineSelectedListener {

    @Override
    public void onMeasureInDrawActiom(int position,  boolean delete) {
        SketchDrawActivity SketchDrawFragmenr = (SketchDrawActivity) viewPager.getAdapter().instantiateItem(viewPager, 0);
        SketchDrawFragmenr.onMeasureInDrawActiom(position, delete);
    }

    static SketchCustViewPager viewPager;
    SketchFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sketch);

        // Instantiate a ViewPager
        viewPager = (SketchCustViewPager) this.findViewById(R.id.viewpager);

        // Create an adapter with the fragments we show on the ViewPager
        SketchFragmentPagerAdapter pagerAdapter = new SketchFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(SketchDrawActivity.newInstance("SketchDrawActivity"));
        pagerAdapter.addFragment(SketchTableActivity.newInstance("SketchTableActivity"));
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {                    // Evento de cambio de pagina
            public void onPageScrollStateChanged(int state) {}

            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == 0) {
                    cerrarTeclado();
                }
//                if(viewPager.getCurrentItem() ==  1) menuContent.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);   // bloqueo la apertura del menu
//                else menuContent.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1 && positionOffset > 0.5) {
                    viewPager.setCurrentItem(1, true);
                }
            }
        });


//        SketchTableActivity selectorevidence = (SketchTableActivity)viewPager.getAdapter().instantiateItem(viewPager, 1);
//        selectorevidence.pruebann();

//        final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new SketchPagerAdapter(this));
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {                    // Evento de cambio de pagina
//            public void onPageScrollStateChanged(int state) {}
//
//            public void onPageSelected(int position) {
////                if(viewPager.getCurrentItem() ==  1) menuContent.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);   // bloqueo la apertura del menu
////                else menuContent.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (position == 1 && positionOffset > 0.5) {
//                    viewPager.setCurrentItem(1, true);
//                }
//            }
//        });

        DataIpat.adapterMeasureEvi.setCallback(this);

        Boolean tableInitialization = getIntent().getExtras().getBoolean("tableInicialization");
        viewPager.setCurrentItem(0);
        if(tableInitialization) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(1, false);
                }
            }, 300);
        }
    }

    private void cerrarTeclado(){
        InputMethodManager mgr = (InputMethodManager) viewPager.getContext().getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        // Return to previous page when we press back button
       if (viewPager.getCurrentItem() == 0)
            super.onBackPressed();
       else
           viewPager.setCurrentItem(this.viewPager.getCurrentItem() - 1);

    }

}
