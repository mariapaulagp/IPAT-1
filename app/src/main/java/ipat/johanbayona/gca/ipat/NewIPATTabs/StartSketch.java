package ipat.johanbayona.gca.ipat.NewIPATTabs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import ipat.johanbayona.gca.ipat.R;

import static android.R.attr.fragment;

public class StartSketch extends AppCompatActivity {

    public static Typeface myFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/BEBAS.ttf");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_sketch);

        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        TabSketch myTabStart = new TabSketch();

        transition.replace(R.id.StartFragment, myTabStart);
        transition.commit();
    }
}
