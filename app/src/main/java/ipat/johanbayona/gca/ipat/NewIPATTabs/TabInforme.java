package ipat.johanbayona.gca.ipat.NewIPATTabs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ipat.johanbayona.gca.ipat.R;

public class TabInforme extends Fragment  {
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tabinforme, container, false);
        return v;
    }

    public static TabInforme newInstance(String text) {

        TabInforme f = new TabInforme();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);
        return f;
    }
}
