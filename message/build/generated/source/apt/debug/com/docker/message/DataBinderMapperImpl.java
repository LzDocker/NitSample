package com.docker.message;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.docker.message.databinding.MessageActivityBindingImpl;
import com.docker.message.databinding.MessageFragmentCardStyleBindingImpl;
import com.docker.message.databinding.MessageFragmentListStyleBindingImpl;
import com.docker.message.databinding.MessageItemCollectionBindingImpl;
import com.docker.message.databinding.MessageItemCommentBindingImpl;
import com.docker.message.databinding.MessageItemFaviorBindingImpl;
import com.docker.message.databinding.MessageItemSystemBindingImpl;
import com.docker.message.databinding.MessageSampleActivityBindingImpl;
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
  private static final int LAYOUT_MESSAGEACTIVITY = 1;

  private static final int LAYOUT_MESSAGEFRAGMENTCARDSTYLE = 2;

  private static final int LAYOUT_MESSAGEFRAGMENTLISTSTYLE = 3;

  private static final int LAYOUT_MESSAGEITEMCOLLECTION = 4;

  private static final int LAYOUT_MESSAGEITEMCOMMENT = 5;

  private static final int LAYOUT_MESSAGEITEMFAVIOR = 6;

  private static final int LAYOUT_MESSAGEITEMSYSTEM = 7;

  private static final int LAYOUT_MESSAGESAMPLEACTIVITY = 8;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(8);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_activity, LAYOUT_MESSAGEACTIVITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_fragment_card_style, LAYOUT_MESSAGEFRAGMENTCARDSTYLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_fragment_list_style, LAYOUT_MESSAGEFRAGMENTLISTSTYLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_item_collection, LAYOUT_MESSAGEITEMCOLLECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_item_comment, LAYOUT_MESSAGEITEMCOMMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_item_favior, LAYOUT_MESSAGEITEMFAVIOR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_item_system, LAYOUT_MESSAGEITEMSYSTEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.docker.message.R.layout.message_sample_activity, LAYOUT_MESSAGESAMPLEACTIVITY);
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
        case  LAYOUT_MESSAGEACTIVITY: {
          if ("layout/message_activity_0".equals(tag)) {
            return new MessageActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_activity is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGEFRAGMENTCARDSTYLE: {
          if ("layout/message_fragment_card_style_0".equals(tag)) {
            return new MessageFragmentCardStyleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_fragment_card_style is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGEFRAGMENTLISTSTYLE: {
          if ("layout/message_fragment_list_style_0".equals(tag)) {
            return new MessageFragmentListStyleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_fragment_list_style is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGEITEMCOLLECTION: {
          if ("layout/message_item_collection_0".equals(tag)) {
            return new MessageItemCollectionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_item_collection is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGEITEMCOMMENT: {
          if ("layout/message_item_comment_0".equals(tag)) {
            return new MessageItemCommentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_item_comment is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGEITEMFAVIOR: {
          if ("layout/message_item_favior_0".equals(tag)) {
            return new MessageItemFaviorBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_item_favior is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGEITEMSYSTEM: {
          if ("layout/message_item_system_0".equals(tag)) {
            return new MessageItemSystemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_item_system is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGESAMPLEACTIVITY: {
          if ("layout/message_sample_activity_0".equals(tag)) {
            return new MessageSampleActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_sample_activity is invalid. Received: " + tag);
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
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(8);

    static {
      sKeys.put("layout/message_activity_0", com.docker.message.R.layout.message_activity);
      sKeys.put("layout/message_fragment_card_style_0", com.docker.message.R.layout.message_fragment_card_style);
      sKeys.put("layout/message_fragment_list_style_0", com.docker.message.R.layout.message_fragment_list_style);
      sKeys.put("layout/message_item_collection_0", com.docker.message.R.layout.message_item_collection);
      sKeys.put("layout/message_item_comment_0", com.docker.message.R.layout.message_item_comment);
      sKeys.put("layout/message_item_favior_0", com.docker.message.R.layout.message_item_favior);
      sKeys.put("layout/message_item_system_0", com.docker.message.R.layout.message_item_system);
      sKeys.put("layout/message_sample_activity_0", com.docker.message.R.layout.message_sample_activity);
    }
  }
}
