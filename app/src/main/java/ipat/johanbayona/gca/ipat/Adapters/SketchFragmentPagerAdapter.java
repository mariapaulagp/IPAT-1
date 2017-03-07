package ipat.johanbayona.gca.ipat.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbayo on 20/09/2016.
 */
public class SketchFragmentPagerAdapter extends FragmentPagerAdapter {
    // List of fragments which are going to set in the view pager widget
    List<Fragment> fragments;

    public SketchFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
        this.fragments = new ArrayList<Fragment>();
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}