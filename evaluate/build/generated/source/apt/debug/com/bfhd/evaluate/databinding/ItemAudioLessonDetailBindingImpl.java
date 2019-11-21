package com.bfhd.evaluate.databinding;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemAudioLessonDetailBindingImpl extends ItemAudioLessonDetailBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemAudioLessonDetailBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private ItemAudioLessonDetailBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.studyReadCh.setTag(null);
        this.studyReadEn.setTag(null);
        this.studyReadShow.setTag(null);
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
        boolean itemIsChoose = false;
        boolean itemCH = false;
        com.bfhd.evaluate.vo.LessonDetailVo.SortContentBean item = mItem;
        boolean ItemCH1 = false;
        java.lang.String itemText = null;
        java.lang.String itemEn = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (item != null) {
                    // read item.isChoose
                    itemIsChoose = item.isChoose();
                    // read item.cH
                    itemCH = item.isCH();
                    // read item.text
                    itemText = item.getText();
                    // read item.en
                    itemEn = item.getEn();
                }


                // read !item.cH
                ItemCH1 = !itemCH;
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.studyReadCh, itemText);
            com.docker.common.common.binding.visibleGoneBindingAdapter.showHide(this.studyReadCh, itemCH);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.studyReadEn, itemEn);
            com.bfhd.evaluate.binding.TextBindingAdapter.textIsChoose(this.studyReadEn, itemIsChoose);
            com.docker.common.common.binding.visibleGoneBindingAdapter.showHide(this.studyReadShow, ItemCH1);
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