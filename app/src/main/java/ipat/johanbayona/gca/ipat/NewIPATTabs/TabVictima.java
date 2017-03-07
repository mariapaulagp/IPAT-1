package ipat.johanbayona.gca.ipat.NewIPATTabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ipat.johanbayona.gca.ipat.R;

public class TabVictima extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tapvictima, container, false);
        return v;
    }

    public static TabVictima newInstance(String text) {

        TabVictima f = new TabVictima();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        return f;
    }
}
