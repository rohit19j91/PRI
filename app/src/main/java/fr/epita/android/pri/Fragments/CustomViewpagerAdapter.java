package fr.epita.android.pri.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class CustomViewpagerAdapter extends FragmentPagerAdapter {

    private static final int nb_pages = 6;

    public ChangeMail changeMail;
    ChangeMobile changeMobile;
    ChangePassword changePassword;
    Profil profile;
    public ComputersList computersList;
    Tamacharacter tamacharacter;



    public CustomViewpagerAdapter(FragmentManager fm) {
        super(fm);

        changeMail = new ChangeMail();
        changeMobile = new ChangeMobile();
        changePassword = new ChangePassword();
        profile = new Profil();
        computersList = new ComputersList();
        tamacharacter = new Tamacharacter();


    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return profile;
            case 1:
                return changeMail;
            case 2:
                return changeMobile;
            case 3:
                return changePassword;
            case 4:
                return computersList;
            case 5:
                return tamacharacter;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nb_pages;
    }

}
