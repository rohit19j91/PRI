package fr.epita.android.pri.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sadekseridj on 06/12/2017.
 */

public class CustomViewpagerAdapter extends FragmentPagerAdapter {

    private static final int nb_pages = 10;

    public ChangeMail changeMail;
    ChangeMobile changeMobile;
    ChangePassword changePassword;
    public ConfirmationCode confirmationCode;
    ForgotPassword forgotPassword;
    Profil profile;
    ResetPassword resetPassword;
    SignupActvity signupActvity;
    public ComputersList computersList;
    Tamacharacter tamacharacter;



    public CustomViewpagerAdapter(FragmentManager fm) {
        super(fm);

        changeMail = new ChangeMail();
        changeMobile = new ChangeMobile();
        changePassword = new ChangePassword();
        confirmationCode = new ConfirmationCode();
        forgotPassword = new ForgotPassword();
        profile = new Profil();
        resetPassword = new ResetPassword();
        signupActvity = new SignupActvity();
        computersList = new ComputersList();
        tamacharacter = new Tamacharacter();


    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return forgotPassword;
            case 1:
                return signupActvity;
            case 2:
                return profile;
            case 3:
                return changeMail;
            case 4:
                return changeMobile;
            case 5:
                return changePassword;
            case 6:
                return resetPassword;
            case 7:
                return confirmationCode;
            case 8:
                return computersList;
            case 9:
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
