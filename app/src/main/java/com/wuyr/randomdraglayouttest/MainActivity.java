package com.wuyr.randomdraglayouttest;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyr.randomdraglayout.RandomDragLayout;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author wuyr
 * @github https://github.com/wuyr/RandomDragLayout
 * @since 2019-01-22 上午10:06
 */
public class MainActivity extends AppCompatActivity {

    private RandomDragLayout mRandomDragLayoutFish;
    private RandomDragLayout mRandomDragLayoutCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main_view);

        mRandomDragLayoutFish = findViewById(R.id.random_drag_layout_fish);
        mRandomDragLayoutCard = findViewById(R.id.random_drag_layout_card);
        //子View刷新频率为32 (毫秒/次)
        mRandomDragLayoutFish.setChildRefreshPeriod(32);
        //监听状态变更
        setOnStateChangeListener();
        //监听拖动信息变更
        setOnDragListener();

        initOtherViews();
    }

    private void initOtherViews() {
        TextView alphaAnimationDuration = findViewById(R.id.alpha_animation_duration_text);
        TextView flingDuration = findViewById(R.id.fling_duration_text);
        TextView scrollAvailabilityRatio = findViewById(R.id.scroll_availability_ratio_text);
        alphaAnimationDuration.setText(getString(R.string.alpha_animation_duration_text, 200));
        flingDuration.setText(getString(R.string.fling_duration_text, 800));
        scrollAvailabilityRatio.setText(getString(R.string.scroll_availability_ratio_text, 80));
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()) {
                    case R.id.alpha_animation_duration:
                        alphaAnimationDuration.setText(getString(R.string.alpha_animation_duration_text, progress * 10));
                        mRandomDragLayoutCard.setAlphaAnimationDuration(progress * 10);
                        mRandomDragLayoutFish.setAlphaAnimationDuration(progress * 10);
                        break;
                    case R.id.fling_duration:
                        flingDuration.setText(getString(R.string.fling_duration_text, progress * 10));
                        mRandomDragLayoutCard.setFlingDuration(progress * 10);
                        mRandomDragLayoutFish.setFlingDuration(progress * 10);
                        break;
                    case R.id.scroll_availability_ratio:
                        scrollAvailabilityRatio.setText(getString(R.string.scroll_availability_ratio_text, progress));
                        mRandomDragLayoutCard.setScrollAvailabilityRatio(progress / 100F);
                        mRandomDragLayoutFish.setScrollAvailabilityRatio(progress / 100F);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        ((SeekBar) findViewById(R.id.alpha_animation_duration)).setOnSeekBarChangeListener(listener);
        ((SeekBar) findViewById(R.id.fling_duration)).setOnSeekBarChangeListener(listener);
        ((SeekBar) findViewById(R.id.scroll_availability_ratio)).setOnSeekBarChangeListener(listener);
        ((Switch) findViewById(R.id.style)).setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (mRandomDragLayoutFish.reset() && mRandomDragLayoutCard.reset()) {
                    mRandomDragLayoutCard.setVisibility(View.INVISIBLE);
                    mRandomDragLayoutFish.setVisibility(View.VISIBLE);
                } else {
                    buttonView.setChecked(false);
                }
            } else {
                if (mRandomDragLayoutFish.reset() && mRandomDragLayoutCard.reset()) {
                    mRandomDragLayoutCard.setVisibility(View.VISIBLE);
                    mRandomDragLayoutFish.setVisibility(View.INVISIBLE);
                } else {
                    buttonView.setChecked(true);
                }
            }
        });
    }

    private void setOnStateChangeListener() {
        mRandomDragLayoutFish.setOnStateChangeListener(getOnStateChangeListener());
        mRandomDragLayoutCard.setOnStateChangeListener(getOnStateChangeListener());
    }

    private RandomDragLayout.OnStateChangeListener getOnStateChangeListener() {
        return newState -> {
            String content;
            switch (newState) {
                case RandomDragLayout.STATE_NORMAL:
                    content = "普通状态";
                    if (getRandomDragLayout() == mRandomDragLayoutFish) {
                        ((AnimationDrawable) ((ImageView) findViewById(R.id.fish)).getDrawable()).stop();
                    }
                    ((TextView) findViewById(R.id.drag_info)).setText("");
                    break;
                case RandomDragLayout.STATE_DRAGGING:
                    content = "正在拖动";
                    if (getRandomDragLayout() == mRandomDragLayoutFish) {
                        ((AnimationDrawable) ((ImageView) findViewById(R.id.fish)).getDrawable()).start();
                    }
                    break;
                case RandomDragLayout.STATE_FLINGING:
                    content = "正在惯性滑动";
                    break;
                case RandomDragLayout.STATE_FLEEING:
                    content = "正在向屏幕%s边逃离";
                    String tmp;
                    switch (getRandomDragLayout().getTargetOrientation()) {
                        case RandomDragLayout.ORIENTATION_LEFT:
                            tmp = "左";
                            break;
                        case RandomDragLayout.ORIENTATION_RIGHT:
                            tmp = "右";
                            break;
                        case RandomDragLayout.ORIENTATION_TOP:
                            tmp = "上";
                            break;
                        case RandomDragLayout.ORIENTATION_BOTTOM:
                            tmp = "下";
                            break;
                        default:
                            return;
                    }
                    content = String.format(content, tmp);
                    break;
                case RandomDragLayout.STATE_OUT_OF_SCREEN:
                    content = "超出屏幕";
                    break;
                case RandomDragLayout.STATE_GONE:
                    content = "消失";
                    break;
                default:
                    return;
            }
            content = "当前状态: " + content;
            ((TextView) findViewById(R.id.state)).setText(content);
        };
    }

    private RandomDragLayout getRandomDragLayout() {
        return mRandomDragLayoutCard.getVisibility() == View.INVISIBLE ? mRandomDragLayoutFish : mRandomDragLayoutCard;
    }

    private void setOnDragListener() {
        TextView dragInfo = findViewById(R.id.drag_info);
        String infoFormat = "X轴: %f\nY轴: %f\n绝对角度: %f";
        mRandomDragLayoutCard.setOnDragListener((x, y, degrees) ->
                dragInfo.setText(String.format(Locale.getDefault(), infoFormat, x, y, degrees)));
        mRandomDragLayoutFish.setOnDragListener((x, y, degrees) ->
                dragInfo.setText(String.format(Locale.getDefault(), infoFormat, x, y, degrees)));
    }

    public void onFishClick(View view) {
        Toast.makeText(this, "锦鲤被点击", Toast.LENGTH_SHORT).show();
    }

    public void onCardClick(View view) {
        Toast.makeText(this, "卡片被点击", Toast.LENGTH_SHORT).show();
    }

    public void reset(View view) {
        getRandomDragLayout().reset();
    }
}
