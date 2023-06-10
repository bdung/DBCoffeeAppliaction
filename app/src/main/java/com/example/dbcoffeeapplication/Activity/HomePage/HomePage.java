package com.example.dbcoffeeapplication.Activity.HomePage;

import static com.example.dbcoffeeapplication.R.id.menu_fav;
import static com.example.dbcoffeeapplication.R.id.menu_home;
import static com.example.dbcoffeeapplication.Utils.Utils._user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.dbcoffeeapplication.Activity.Account.ProfileActivity;
import com.example.dbcoffeeapplication.Activity.Cart.CartActivity;
import com.example.dbcoffeeapplication.Activity.Chat.ChatActivity;
import com.example.dbcoffeeapplication.Activity.LoginResgister.SignIn;
import com.example.dbcoffeeapplication.Activity.Product.ProductListActivity;
import com.example.dbcoffeeapplication.Activity.Voucher.VoucherUserActivity;
import com.example.dbcoffeeapplication.Activity.Wishlist.WishlistActivity;
import com.example.dbcoffeeapplication.Fragment.HomeFragment;
import com.example.dbcoffeeapplication.R;
import com.example.dbcoffeeapplication.databinding.ActivityHomePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity  {

    ActivityHomePageBinding binding;

    HomeFragment homeFragment = new HomeFragment();

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(new HomeFragment());
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        binding.bottomNavigation.setBackground(null);

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == menu_home){
                    replaceFragment(new HomeFragment());
                }
                else if(item.getItemId() == menu_fav){
                    replaceFragment(new HomeFragment());
                    Intent intent = new Intent(HomePage.this, SignIn.class);
                    startActivity(intent);
                }
                return true;
            }
        });


    }
    private  void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}