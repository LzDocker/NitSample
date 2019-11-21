package com.bfhd.evaluate;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.bfhd.evaluate.databinding.ActivityAudioLessonDetailBindingImpl;
import com.bfhd.evaluate.databinding.ActivityLessonAudioBindingImpl;
import com.bfhd.evaluate.databinding.ActivityRxdemoBindingImpl;
import com.bfhd.evaluate.databinding.FragmentLessonRecyclerBindingImpl;
import com.bfhd.evaluate.databinding.ItemAudioLessonDetailBindingImpl;
import com.bfhd.evaluate.databinding.ItemGenduCompareBindingImpl;
import com.bfhd.evaluate.databinding.ItemRadioLessonBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYAUDIOLESSONDETAIL = 1;

  private static final int LAYOUT_ACTIVITYLESSONAUDIO = 2;

  private static final int LAYOUT_ACTIVITYRXDEMO = 3;

  private static final int LAYOUT_FRAGMENTLESSONRECYCLER = 4;

  private static final int LAYOUT_ITEMAUDIOLESSONDETAIL = 5;

  private static final int LAYOUT_ITEMGENDUCOMPARE = 6;

  private static final int LAYOUT_ITEMRADIOLESSON = 7;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(7);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.activity_audio_lesson_detail, LAYOUT_ACTIVITYAUDIOLESSONDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.activity_lesson_audio, LAYOUT_ACTIVITYLESSONAUDIO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.activity_rxdemo, LAYOUT_ACTIVITYRXDEMO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.fragment_lesson_recycler, LAYOUT_FRAGMENTLESSONRECYCLER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.item_audio_lesson_detail, LAYOUT_ITEMAUDIOLESSONDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.item_gendu_compare, LAYOUT_ITEMGENDUCOMPARE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.bfhd.evaluate.R.layout.item_radio_lesson, LAYOUT_ITEMRADIOLESSON);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYAUDIOLESSONDETAIL: {
          if ("layout/activity_audio_lesson_detail_0".equals(tag)) {
            return new ActivityAudioLessonDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_audio_lesson_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLESSONAUDIO: {
          if ("layout/activity_lesson_audio_0".equals(tag)) {
            return new ActivityLessonAudioBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_lesson_audio is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYRXDEMO: {
          if ("layout/activity_rxdemo_0".equals(tag)) {
            return new ActivityRxdemoBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_rxdemo is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTLESSONRECYCLER: {
          if ("layout/fragment_lesson_recycler_0".equals(tag)) {
            return new FragmentLessonRecyclerBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_lesson_recycler is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMAUDIOLESSONDETAIL: {
          if ("layout/item_audio_lesson_detail_0".equals(tag)) {
            return new ItemAudioLessonDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_audio_lesson_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMGENDUCOMPARE: {
          if ("layout/item_gendu_compare_0".equals(tag)) {
            return new ItemGenduCompareBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_gendu_compare is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRADIOLESSON: {
          if ("layout/item_radio_lesson_0".equals(tag)) {
            return new ItemRadioLessonBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_radio_lesson is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(7);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.bfhd.circle.DataBinderMapperImpl());
    result.add(new com.dcbfhd.utilcode.DataBinderMapperImpl());
    result.add(new com.docker.common.DataBinderMapperImpl());
    result.add(new com.docker.core.DataBinderMapperImpl());
    result.add(new me.tatarka.bindingcollectionadapter2.DataBinderMapperImpl());
    result.add(new me.tatarka.bindingcollectionadapter2.recyclerview.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(23);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "favourNum");
      sKeys.put(2, "item");
      sKeys.put(3, "viewmodel");
      sKeys.put(4, "isFocus");
      sKeys.put(5, "isCollect");
      sKeys.put(6, "message");
      sKeys.put(7, "isFav");
      sKeys.put(8, "editFlag");
      sKeys.put(9, "img3");
      sKeys.put(10, "employeeNum");
      sKeys.put(11, "isDo");
      sKeys.put(12, "personVo");
      sKeys.put(13, "praiseNum");
      sKeys.put(14, "CreateVo");
      sKeys.put(15, "isJoin");
      sKeys.put(16, "imgurl");
      sKeys.put(17, "createVo");
      sKeys.put(18, "name");
      sKeys.put(19, "vo");
      sKeys.put(20, "img2");
      sKeys.put(21, "img1");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(7);

    static {
      sKeys.put("layout/activity_audio_lesson_detail_0", com.bfhd.evaluate.R.layout.activity_audio_lesson_detail);
      sKeys.put("layout/activity_lesson_audio_0", com.bfhd.evaluate.R.layout.activity_lesson_audio);
      sKeys.put("layout/activity_rxdemo_0", com.bfhd.evaluate.R.layout.activity_rxdemo);
      sKeys.put("layout/fragment_lesson_recycler_0", com.bfhd.evaluate.R.layout.fragment_lesson_recycler);
      sKeys.put("layout/item_audio_lesson_detail_0", com.bfhd.evaluate.R.layout.item_audio_lesson_detail);
      sKeys.put("layout/item_gendu_compare_0", com.bfhd.evaluate.R.layout.item_gendu_compare);
      sKeys.put("layout/item_radio_lesson_0", com.bfhd.evaluate.R.layout.item_radio_lesson);
    }
  }
}
