package com.example.qicheng.suanming.utils;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.genview.WheelGeneralAdapter;
import com.codbking.widget.view.WheelView;
import com.example.qicheng.suanming.R;

import java.util.Date;

/**
 * Created by Sakura on 2020-04-18 09:56
 */
public class CustomDatePicker extends CustomWheelPick {

    private static final String TAG = "CustomWheelPick";

    private Integer[] yearArr, mothArr, dayArr, hourArr, minutArr;
    private String[] nlMotnArr, nlDayArr, nlHourArr;
    private PickerDateHelper datePicker;

    private WheelView yearView;
    private WheelView monthView;
    private WheelView dayView;
    private WheelView hourView;
    private WheelView minuteView;
    //开始时间
    private Date startDate = new Date();
    private int selectDay;

    //年分限制，默认上下5年
    private int yearLimt = 5;
    private OnChangeLisener onChangeLisener;

    // 公历
    private boolean flag = true;

    private Context context;

    //选择时间回调
    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    public CustomDatePicker(Context context) {
        super(context);
        this.context = context;
    }

    public CustomDatePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setYearLimt(int yearLimt) {
        this.yearLimt = yearLimt;
    }

    @Override
    protected int getLayout() {
        return R.layout.custom_datepicker_list;
    }

    //初始化值
    public void init() {
        this.minuteView = findViewById(R.id.minute);
        this.hourView = findViewById(R.id.hour);
        this.dayView = findViewById(R.id.day);
        this.monthView = findViewById(R.id.month);
        this.yearView = findViewById(R.id.year);

        this.minuteView.setVisibility(VISIBLE);
        this.hourView.setVisibility(VISIBLE);
        this.dayView.setVisibility(VISIBLE);
        this.monthView.setVisibility(VISIBLE);
        this.yearView.setVisibility(VISIBLE);

        datePicker = new PickerDateHelper();
        datePicker.setStartDate(startDate, yearLimt);

        dayArr = datePicker.genDay();
        yearArr = datePicker.genYear();
        mothArr = datePicker.genMonth();
        hourArr = datePicker.genHour();
        minutArr = datePicker.genMinut();
        //农历
        nlMotnArr = datePicker.getNlMonth();
        nlDayArr = datePicker.getNlDay();
        nlHourArr = datePicker.getNlHours();

        setWheelListener(yearView, yearArr, false);
        setWheelListener(monthView, mothArr, true);
        setWheelListener(dayView, dayArr, true);
        setWheelListener(hourView, hourArr, true);
        setWheelListener(minuteView, minutArr, true);

        yearView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.YEAR), yearArr));
        monthView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.MOTH), mothArr));
        dayView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.DAY), dayArr));
        hourView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.HOUR), hourArr));
        minuteView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.MINUTE), minutArr));

    }

    protected String[] convertData(WheelView wheelView, Integer[] data) {
        if (wheelView == yearView) {
            return datePicker.getDisplayValue(data, "年");
        } else if (wheelView == monthView) {
            return datePicker.getDisplayValue(data, "月");
        } else if (wheelView == dayView) {
            return datePicker.getDisplayValue(data, "日");
        } else if (wheelView == hourView) {
            return datePicker.getDisplayValue(data, "时");
        } else if (wheelView == minuteView) {
            return datePicker.getDisplayValue(data, "分");
        }
        return new String[0];
    }

    @Override
    protected int getItemHeight() {
        return dayView.getItemHeight();
    }


    @Override
    protected void setData(Object[] datas) {
    }

    private void setChangeDaySelect(int year, int moth) {
        dayArr = datePicker.genDay(year, moth);
        WheelGeneralAdapter adapter = (WheelGeneralAdapter) dayView.getViewAdapter();
        adapter.setData(convertData(dayView, dayArr));

        int indxt = datePicker.findIndextByValue(selectDay, dayArr);
        if (indxt == -1) {
            dayView.setCurrentItem(0);
        } else {
            dayView.setCurrentItem(indxt);
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        int year, moth, day, hour, minut;
        if (true) {
            year = yearArr[yearView.getCurrentItem()];
            moth = mothArr[monthView.getCurrentItem()];
            day = dayArr[dayView.getCurrentItem()];
            hour = hourArr[hourView.getCurrentItem()];
            minut = minutArr[minuteView.getCurrentItem()];
        }
//        else {
//            year = yearArr[yearView.getCurrentItem()];
//            moth = nlMotnArr[monthView.getCurrentItem()];
//            day = nlDayArr[dayView.getCurrentItem()];
//            hour = nlHourArr[hourView.getCurrentItem()];
//            minut = minutArr[minuteView.getCurrentItem()];
//        }


//        if (wheel == yearView || wheel == monthView) {
//            setChangeDaySelect(year, moth);
//        } else {
//            selectDay = day;
//        }

        if (onChangeLisener != null) {
            onChangeLisener.onChanged(DateUtils.getDate(year, moth, day, hour, minut));
        }
    }

    public void onClickNL() {
        flag = false;
        setWheelListener(yearView, yearArr, false);
        setWheelListener(monthView, nlMotnArr, false);
        setWheelListener(dayView, nlDayArr, false);
        setWheelListener(hourView, nlHourArr, false);
        setWheelListener(minuteView, minutArr, false);

//        yearView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.YEAR), yearArr));
//        monthView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.MOTH), nlMotnArr));
//        dayView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.DAY), nlDayArr));
//        hourView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.HOUR), nlHourArr));
//        minuteView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.MINUTE), minutArr));
    }

    public void onClickGL() {
        flag = true;
        setWheelListener(yearView, yearArr, false);
        setWheelListener(monthView, mothArr, true);
        setWheelListener(dayView, dayArr, true);
        setWheelListener(hourView, hourArr, true);
        setWheelListener(minuteView, minutArr, true);

//        yearView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.YEAR), yearArr));
//        monthView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.MOTH), mothArr));
//        dayView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.DAY), dayArr));
//        hourView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.HOUR), hourArr));
//        minuteView.setCurrentItem(datePicker.findIndextByValue(datePicker.getToady(PickerDateHelper.Type.MINUTE), minutArr));
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {
    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
    }


    //获取选中日期
    public Date getSelectDate() {
        int year = yearArr[yearView.getCurrentItem()];
        int moth = mothArr[monthView.getCurrentItem()];
        int day = dayArr[dayView.getCurrentItem()];
        int hour = hourArr[hourView.getCurrentItem()];
        int minut = minutArr[minuteView.getCurrentItem()];

        return DateUtils.getDate(year, moth, day, hour, minut);

    }
}
