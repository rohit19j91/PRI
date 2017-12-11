package fr.epita.android.pri.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Stack;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class CustomViewpagerAdapter extends FragmentPagerAdapter {

    private static final int nb_pages = 8;

    public ChangeMail changeMail;
    ChangeMobile changeMobile;
    ChangePassword changePassword;
    Profil profile;
    public ComputersList computersList;
    Tamacharacter tamacharacter;
    public WebViewList webViewList ;
    public DisplayPageWeb displayPageWeb;

    public Stack<Integer> stack;



    public CustomViewpagerAdapter(FragmentManager fm) {
        super(fm);

        changeMail = new ChangeMail();
        changeMobile = new ChangeMobile();
        changePassword = new ChangePassword();
        profile = new Profil();
        computersList = new ComputersList();
        tamacharacter = new Tamacharacter();
        webViewList = new WebViewList();
        displayPageWeb = new DisplayPageWeb();
        stack = new Stack<>();

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
            case 6:
                return webViewList;
            case 7:
                return displayPageWeb;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nb_pages;
    }

}
