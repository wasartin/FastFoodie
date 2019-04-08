package edu.iastate.graysonc.fastfood.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.fragments.FavoritesFragment;
import edu.iastate.graysonc.fastfood.fragments.HomeFragment;
import edu.iastate.graysonc.fastfood.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "MainActivity";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private FragmentManager fragmentManager;
    private Fragment homeFragment;
    private Fragment favoritesFragment;
    private Fragment profileFragment;
    private Fragment currentFragment;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        // Instantiate all fragments
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        profileFragment = new ProfileFragment();
        /*fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_frame, profileFragment, "profile").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_frame, favoritesFragment, "favorites").hide(favoritesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_frame, homeFragment, "home").commit();
        currentFragment = homeFragment;*/

        // Start in home fragment
        if (savedInstanceState == null) {
            setFragment(homeFragment);
        }

        ((BottomNavigationView)findViewById(R.id.main_navigation)).setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    menuItem.setChecked(true);
                    setFragment(homeFragment);
                    break;
                case R.id.navigation_favorites:
                    menuItem.setChecked(true);
                    setFragment(favoritesFragment);
                    break;
                case R.id.navigation_profile:
                    menuItem.setChecked(true);
                    setFragment(profileFragment);
                    break;
            }
            return false;
        });
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
        //currentFragment = fragment;
    }
}
