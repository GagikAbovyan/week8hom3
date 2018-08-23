package vtc.room.a101.week8homework3.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import vtc.room.a101.week8homework3.R;
import vtc.room.a101.week8homework3.fragments.PeopleFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

    }


    public final class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(final FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            switch (position){
                case 0:
                    final PeopleFragment peopleFragmentXML = new PeopleFragment();
                    peopleFragmentXML.setXML(true);
                    return peopleFragmentXML;
                case 1:
                    final PeopleFragment peopleFragmentJson = new PeopleFragment();
                    peopleFragmentJson.setXML(false);
                    return peopleFragmentJson;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
