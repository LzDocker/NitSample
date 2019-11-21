package com.bfhd.evaluate.databinding;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemGenduCompareBindingImpl extends ItemGenduCompareBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.read_compare_score, 3);
        sViewsWithIds.put(R.id.read_compare_relative, 4);
        sViewsWithIds.put(R.id.read_compare_play, 5);
        sViewsWithIds.put(R.id.read_compare_pause, 6);
        sViewsWithIds.put(R.id.read_compare_recording, 7);
        sViewsWithIds.put(R.id.read_compare_playback, 8);
        sViewsWithIds.put(R.id.read_compare_cancel, 9);
        sViewsWithIds.put(R.id.read_compare_wave, 10);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemGenduCompareBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ItemGenduCompareBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (com.bfhd.evaluate.view.WaveLineView) bindings[10]
            );
        this.genduItemRelative.setTag(null);
        this.readCompareCh.setTag(null);
        this.readCompareEn.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.item == variableId) {
            setItem((com.bfhd.evaluate.vo.LessonDetailVo.SortContentBean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setItem(@Nullable com.bfhd.evaluate.vo.LessonDetailVo.SortContentBean Item) {
        this.mItem = Item;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.item);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.bfhd.evaluate.vo.LessonDetailVo.SortContentBean item = mItem;
        java.lang.String itemEn = null;
        java.lang.String itemText = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (item != null) {
                    // read item.en
                    itemEn = item.getEn();
                    // read item.text
                    itemText = item.getText();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.readCompareCh, itemText);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.readCompareEn, itemEn);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): item
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}