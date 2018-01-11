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

import com.bumptech.glide.Glide;

import fr.epita.android.pri.Fragments.CustomViewpagerAdapter;

//View.OnClickListener,
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected NavigationView navigationView;
    public DrawerLayout drawer;
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
        if (LoginActivity.rl.getUri() != null)
            Glide.with(this).load(LoginActivity.rl.getUri()).into(imageHeader);
        else
            imageHeader.setImageResource(R.drawable.cybitlogo);

        txtLogin = (TextView) navHeader.findViewById(R.id.txtlogin);
        txtLogin.setText(LoginActivity.rl.getLogin());
        txtLogin.setText(txtLogin.getText().toString().substring(0,1).toUpperCase() + txtLogin.getText().toString().substring(1));

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
        else if (customViewpagerAdapter != null &&
                customViewpagerAdapter.displayPageWeb.webView != null &&
                customViewpagerAdapter.displayPageWeb.webView.canGoBack())
            customViewpagerAdapter.displayPageWeb.webView.goBack();
        else if (customViewpagerAdapter != null &&
                customViewpagerAdapter.webViewList.webView != null &&
                customViewpagerAdapter.webViewList.webView.canGoBack())
            customViewpagerAdapter.webViewList.webView.goBack();
        else if (customViewpagerAdapter.stack.size() > 1) {
            customViewpagerAdapter.stack.pop();
            display_fragment(customViewpagerAdapter.stack.pop());
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
            case R.id.nav_game:
                Intent intent = new Intent(MainActivity.this, ChooseTopic.class);
                startActivity(intent);
                return true;
            case R.id.nav_computers:
                display_fragment(4);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_notifications:
                return true;
            case R.id.nav_mynews:
               display_fragment(8);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_signnews:
                display_fragment(9);
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
               Intent intent_nav = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(intent_nav);
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
                navigationView.getMenu().getItem(1).setChecked(true);
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
                navigationView.getMenu().getItem(5).setChecked(true);
                viewpagerAdapter.setCurrentItem(3, false);
                break;
            case 4:
                setTitle("My computers");
                customViewpagerAdapter.stack.push(4);
                navigationView.getMenu().getItem(2).setChecked(true);
                viewpagerAdapter.setCurrentItem(4, false);
                break;
            case 5:
                setTitle("My tamagotchi");
                customViewpagerAdapter.stack.push(5);
                navigationView.getMenu().getItem(0).setChecked(true);
                viewpagerAdapter.setCurrentItem(5, false);
                break;
            case 6:
                setTitle("My news");
                customViewpagerAdapter.stack.push(6);
                navigationView.getMenu().getItem(4).setChecked(true);
                viewpagerAdapter.setCurrentItem(6, false);
                break;
            case 7:
                String item_web = customViewpagerAdapter.displayPageWeb.getArguments().getString("URL");
                customViewpagerAdapter.displayPageWeb.webView.loadUrl(item_web);
                customViewpagerAdapter.stack.push(7);
                viewpagerAdapter.setCurrentItem(7, false);
                break;
            case 8:
                setTitle("My News");
                customViewpagerAdapter.stack.push(8);
                navigationView.getMenu().getItem(4).setChecked(true);
                viewpagerAdapter.setCurrentItem(8, false);
                break;
            case 9:
                setTitle("Sign for news");
                customViewpagerAdapter.stack.push(9);
                //navigationView.getMenu().getItem(4).setChecked(true); mettre à jour une fois intégré dans le menu
                viewpagerAdapter.setCurrentItem(9, false);
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





