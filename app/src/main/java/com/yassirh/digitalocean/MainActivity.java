package com.yassirh.digitalocean;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yassirh.digitalocean.model.Account;
import com.yassirh.digitalocean.service.AccountService;
import com.yassirh.digitalocean.utils.ApiHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long lastBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AccountService accountService = new AccountService(this);
        if (!accountService.hasAccounts()) {
            FragmentManager fm = getSupportFragmentManager();
            SwitchAccountDialogFragment switchAccountDialogFragment = new SwitchAccountDialogFragment();
            switchAccountDialogFragment.show(fm, "switch_account");
        }

        View headerView = navigationView.getHeaderView(0);
        TextView accountNameTextView = headerView.findViewById(R.id.accountNameTextView);
        TextView accountEmailTextView = headerView.findViewById(R.id.accountEmailTextView);
        Account currentAccount = ApiHelper.getCurrentAccount(this);
        accountNameTextView.setText(currentAccount.getName());
        accountEmailTextView.setText(currentAccount.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (lastBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                View view = findViewById(android.R.id.content);
                Snackbar.make(view, R.string.message_press_again_to_exit, Snackbar.LENGTH_SHORT)
                        .show();
            }
            lastBackPressed = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_droplets) {

        } else if (id == R.id.nav_domains) {

        } else if (id == R.id.nav_images) {

        } else if (id == R.id.nav_sshkeys) {

        } else if (id == R.id.nav_about) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://yassirh.com/digitalocean_swimmer/"));
            startActivity(browserIntent);
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
