package com.bfhd.evaluate.databinding;
import com.bfhd.evaluate.R;
import com.bfhd.evaluate.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemRadioLessonBindingImpl extends ItemRadioLessonBinding implements com.bfhd.evaluate.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.item_radio_i1, 6);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView5;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemRadioLessonBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private ItemRadioLessonBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            );
        this.itemRadioLw.setTag(null);
        this.itemRadioName.setTag(null);
        this.itemRadioNum.setTag(null);
        this.itemRadioStatus.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.bfhd.evaluate.generated.callback.OnClickListener(this, 1);
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
            setItem((com.bfhd.evaluate.vo.RadioLessonVo) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setItem(@Nullable com.bfhd.evaluate.vo.RadioLessonVo Item) {
        updateRegistration(0, Item);
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
            case 0 :
                return onChangeItem((com.bfhd.evaluate.vo.RadioLessonVo) object, fieldId);
        }
        return false;
    }
    private boolean onChangeItem(com.bfhd.evaluate.vo.RadioLessonVo Item, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
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
        java.lang.String stringFormatMboundView5AndroidStringPMoneyItemGetPrice = null;
        com.bfhd.evaluate.vo.RadioLessonVo item = mItem;
        java.lang.String itemNum = null;
        boolean itemGetPriceInt0 = false;
        java.lang.String itemName = null;
        int itemGetPrice = 0;
        java.lang.String itemIsRead = null;
        java.lang.String itemNumItemRadioNumAndroidStringItemChakan = null;
        boolean ItemGetPriceInt01 = false;

        if ((dirtyFlags & 0x3L) != 0) {



                if (item != null) {
                    // read item.num
                    itemNum = item.getNum();
                    // read item.name
                    itemName = item.getName();
                    // read item.get_price
                    itemGetPrice = item.getGet_price();
                    // read item.is_read
                    itemIsRead = item.getIs_read();
                }


                // read (item.num) + (@android:string/item_chakan)
                itemNumItemRadioNumAndroidStringItemChakan = (itemNum) + (itemRadioNum.getResources().getString(R.string.item_chakan));
                // read String.format(@android:string/p_money, item.get_price)
                stringFormatMboundView5AndroidStringPMoneyItemGetPrice = java.lang.String.format(mboundView5.getResources().getString(R.string.p_money), itemGetPrice);
                // read item.get_price != 0
                itemGetPriceInt0 = (itemGetPrice) != (0);
                // read item.get_price == 0
                ItemGetPriceInt01 = (itemGetPrice) == (0);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.docker.common.common.binding.visibleGoneBindingAdapter.showHide(this.itemRadioLw, ItemGetPriceInt01);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.itemRadioName, itemName);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.itemRadioNum, itemNumItemRadioNumAndroidStringItemChakan);
            com.bfhd.evaluate.binding.TextBindingAdapter.textIsParse(this.itemRadioStatus, itemIsRead);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, stringFormatMboundView5AndroidStringPMoneyItemGetPrice);
            com.docker.common.common.binding.visibleGoneBindingAdapter.showHide(this.mboundView5, itemGetPriceInt0);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            com.docker.common.common.binding.ViewOnClickBindingAdapter.onClick(this.mboundView0, mCallback1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // item
        com.bfhd.evaluate.vo.RadioLessonVo item = mItem;
        // item != null
        boolean itemJavaLangObjectNull = false;
        // item.getOnItemClickListener()
        com.docker.common.common.model.OnItemClickListener itemGetOnItemClickListener = null;
        // item.getOnItemClickListener() != null
        boolean itemGetOnItemClickListenerJavaLangObjectNull = false;



        itemJavaLangObjectNull = (item) != (null);
        if (itemJavaLangObjectNull) {


            itemGetOnItemClickListener = item.getOnItemClickListener();

            itemGetOnItemClickListenerJavaLangObjectNull = (itemGetOnItemClickListener) != (null);
            if (itemGetOnItemClickListenerJavaLangObjectNull) {




                itemGetOnItemClickListener.onItemClick(item, callbackArg_0);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): item
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}