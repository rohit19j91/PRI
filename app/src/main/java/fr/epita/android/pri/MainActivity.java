package fr.epita.android.pri;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.epita.android.pri.Fragments.CustomViewpagerAdapter;

//View.OnClickListener,
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imageHeader;
    public TextView txtLogin;

    private Toolbar toolbar;

    FragmentManager fragmentManager;
    ViewPager viewpagerAdapter;
    public CustomViewpagerAdapter customViewpagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.mydrawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navHeader = navigationView.getHeaderView(0);

        imageHeader = (ImageView) navHeader.findViewById(R.id.img_header);

        imageHeader.setImageResource(R.drawable.cybitlogo);
        txtLogin = (TextView) navHeader.findViewById(R.id.txtlogin);

        fragmentManager = getSupportFragmentManager();
        viewpagerAdapter = (ViewPager) findViewById(R.id.main_viewpager);
        viewpagerAdapter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        customViewpagerAdapter = new CustomViewpagerAdapter(fragmentManager);
        viewpagerAdapter.setAdapter(customViewpagerAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            int fragment = intent.getExtras().getInt("FRAGMENT");
            display_fragment(fragment);
        } else
            setTitle("CyBit");
    }



    public void showmessage(String message,String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else if (customViewpagerAdapter.stack.size() > 1) {
            customViewpagerAdapter.stack.pop();
            display_fragment(customViewpagerAdapter.stack.pop());
        }
        else if (customViewpagerAdapter != null) {
            if (customViewpagerAdapter.webViewList != null) {
                if (customViewpagerAdapter.webViewList.webView != null) {
                    if (customViewpagerAdapter.webViewList.webView.canGoBack())
                        customViewpagerAdapter.webViewList.webView.goBack();
                }
            }
        }
        else
            super.onBackPressed();
        }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.isChecked())
        {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

        switch (item.getItemId())
        {
            case R.id.nav_home:
                display_fragment(5);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_profile:
                display_fragment(0);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_computers:
                display_fragment(4);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_notifications:
                return true;
            case R.id.nav_news:
                display_fragment(6);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_resetpass:
                Bundle bundle = new Bundle();
                bundle.putString("LOGIN", LoginActivity.rl.getLogin());
                display_fragment(3);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_about_us:
                display_fragment(5);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_logout:
               Intent intent = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    public void display_fragment(int item) {
        switch (item)
        {
            case 0:
                setTitle("Profile");
                customViewpagerAdapter.stack.push(0);
                viewpagerAdapter.setCurrentItem(0, false);
                break;
            case 1:
                setTitle("Reset mail");
                customViewpagerAdapter.stack.push(1);
                viewpagerAdapter.setCurrentItem(1, false);
                break;
            case 2:
                setTitle("Reset mobile");
                customViewpagerAdapter.stack.push(2);
                viewpagerAdapter.setCurrentItem(2, false);
                break;
            case 3:
                setTitle("Reset password");
                customViewpagerAdapter.stack.push(3);
                viewpagerAdapter.setCurrentItem(3, false);
                break;
            case 4:
                setTitle("My computers");
                customViewpagerAdapter.stack.push(4);
                viewpagerAdapter.setCurrentItem(4, false);
                break;
            case 5:
                setTitle("My tamagotchi");
                customViewpagerAdapter.stack.push(5);
                viewpagerAdapter.setCurrentItem(5, false);
                break;
            case 6:
                setTitle("My news");
                customViewpagerAdapter.stack.push(6);
                viewpagerAdapter.setCurrentItem(6, false);
                break;
            case 7:
                String item_web = customViewpagerAdapter.displayPageWeb.getArguments().getString("URL");
                customViewpagerAdapter.displayPageWeb.webView.loadUrl(item_web);
                customViewpagerAdapter.stack.push(7);
                viewpagerAdapter.setCurrentItem(7, false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

}





