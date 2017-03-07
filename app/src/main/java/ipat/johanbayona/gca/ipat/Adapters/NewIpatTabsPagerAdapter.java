package ipat.johanbayona.gca.ipat.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ipat.johanbayona.gca.ipat.NewIPATTabs.TabHipotesis;
import ipat.johanbayona.gca.ipat.NewIPATTabs.TabInforme;
import ipat.johanbayona.gca.ipat.NewIPATTabs.TabLugar;
import ipat.johanbayona.gca.ipat.NewIPATTabs.TabSketch;
import ipat.johanbayona.gca.ipat.NewIPATTabs.TabVehiculos;
import ipat.johanbayona.gca.ipat.NewIPATTabs.TabVictima;
import ipat.johanbayona.gca.ipat.R;

public class NewIpatTabsPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when NewIpatTabsPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the NewIpatTabsPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public NewIpatTabsPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }
    TabSketch sketchInstance = TabSketch.newInstance("FirstFragment, Instance 1");
    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return sketchInstance;
            case 1:
                return TabInforme.newInstance("SecondFragment, Instance 1");
            case 2:
                return TabLugar.newInstance("SecondFragment, Instance 2");
            case 3:
                return TabVehiculos.newInstance("FirstFragment, Instance 1");
            case 4:
                return TabVictima.newInstance("SecondFragment, Instance 1");
            case 5:
                return TabHipotesis.newInstance("SecondFragment, Instance 2");
        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public void setReturnEvent()
    {
        sketchInstance.setReturnEvent();
    }

}