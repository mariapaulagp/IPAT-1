package ipat.johanbayona.gca.ipat.NewIPATTabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ipat.johanbayona.gca.ipat.R;

public class TabHipotesis extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tabhipotesis, container, false);
        return v;
    }

    public static TabHipotesis newInstance(String text) {

        TabHipotesis f = new TabHipotesis();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        return f;
    }
}
