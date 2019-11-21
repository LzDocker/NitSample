package com.bfhd.evaluate.databinding;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAudioLessonDetailBindingImpl extends ActivityAudioLessonDetailBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.audio_detail_frame, 1);
        sViewsWithIds.put(R.id.study_read_bliner, 2);
        sViewsWithIds.put(R.id.study_read_beisu, 3);
        sViewsWithIds.put(R.id.study_read_xunhuan, 4);
        sViewsWithIds.put(R.id.study_read_gendu, 5);
        sViewsWithIds.put(R.id.study_read_qwkh, 6);
        sViewsWithIds.put(R.id.study_read_qwzs, 7);
        sViewsWithIds.put(R.id.study_read_kcsp, 8);
        sViewsWithIds.put(R.id.study_read_progress, 9);
        sViewsWithIds.put(R.id.study_read_hide, 10);
        sViewsWithIds.put(R.id.study_read_shang_lin, 11);
        sViewsWithIds.put(R.id.study_read_shang, 12);
        sViewsWithIds.put(R.id.study_read_resume, 13);
        sViewsWithIds.put(R.id.study_read_next_lin, 14);
        sViewsWithIds.put(R.id.study_read_next, 15);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAudioLessonDetailBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityAudioLessonDetailBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.FrameLayout) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.TextView) bindings[5]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.ImageView) bindings[15]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.ProgressBar) bindings[9]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (com.bfhd.evaluate.view.PlayPauseView) bindings[13]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.TextView) bindings[4]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
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
            return variableSet;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}