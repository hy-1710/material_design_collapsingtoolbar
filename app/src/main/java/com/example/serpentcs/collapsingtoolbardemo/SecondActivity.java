package com.example.serpentcs.collapsingtoolbardemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.serpentcs.collapsingtoolbardemo.databinding.ActivitySecondBinding;
import com.example.serpentcs.collapsingtoolbardemo.fragment.FakePageFragment;

public class SecondActivity extends AppCompatActivity implements Animation.AnimationListener {

    public static final String TAG = SecondActivity.class.getSimpleName();
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    ActivitySecondBinding binding;
    Animation fadeIn, fadeOut;
    private boolean mIsAvatarShown = true;
    private int mMaxScrollSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second);

        fadeIn = AnimationUtils.loadAnimation(this,
                R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        mMaxScrollSize = binding.appbar.getTotalScrollRange();

        binding.viewpager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);

            }
        });

        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e(TAG, "onOffsetChanged: " + verticalOffset);

                // here we can perform two type of animation

                //Animation type 1
                if (mMaxScrollSize == 0)
                    mMaxScrollSize = binding.appbar.getTotalScrollRange();

                int percentage = (Math.abs(verticalOffset) * 100) / mMaxScrollSize;


                if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
                    mIsAvatarShown = false;

                    binding.profileImage.animate()
                            .scaleY(0).scaleX(0)
                            .setDuration(200)
                            .start();
                }

                if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
                    mIsAvatarShown = true;

                    binding.profileImage.animate()
                            .scaleY(1).scaleX(1)
                            .start();
                }


         /*         //Animation Type 2
                    //here we can animate the image with fade in and fade out effects.
                    if (verticalOffset <= -90) {
                        if (binding.profileImage.getVisibility() != View.INVISIBLE) {
                            binding.profileImage.startAnimation(fadeOut);
                            binding.profileImage.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        if (binding.profileImage.getVisibility() != View.VISIBLE) {
                            binding.profileImage.setVisibility(View.VISIBLE);
                            binding.profileImage.startAnimation(fadeIn);
                        }
                    }*/
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    private static class TabsAdapter extends FragmentPagerAdapter {
        private static final int TAB_COUNT = 2;

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public Fragment getItem(int i) {
            return FakePageFragment.newInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + String.valueOf(position);
        }
    }
}
