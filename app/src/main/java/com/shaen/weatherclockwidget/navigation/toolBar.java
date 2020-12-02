package com.shaen.weatherclockwidget.navigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.shaen.weatherclockwidget.R;


/**
 * Created by genius on 2018/3/7.
 */

@SuppressWarnings("DefaultFileTemplate")
public class toolBar extends RelativeLayout {

    public interface toolBarEvent
    {
        void showDrawLayout();
        void showInfo();

    }

    @SuppressWarnings("FieldCanBeLocal")
    private Context m_Context 			= null;
    private View m_View 				= null;
    private toolBarEvent mEvent         = null;


    private OnClickListener mClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.navigation:
                    if(mEvent != null) mEvent.showDrawLayout();
                    break;
                case R.id.close:
                    if(mEvent != null) mEvent.showInfo();
                    break;

            }
        }
    };

    @SuppressWarnings("ConstantConditions")
    public toolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        m_Context = context;
        LayoutInflater layoutInflater = (LayoutInflater) m_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_View = layoutInflater.inflate(R.layout.view_toolbar, this);
        initView();
    }

    public void setEventListener(toolBarEvent event)
    {
        mEvent = event;
    }

    private void initView()
    {
        m_View.findViewById(R.id.navigation).setOnClickListener(mClick);
        m_View.findViewById(R.id.close).setOnClickListener(mClick);

    }
}
