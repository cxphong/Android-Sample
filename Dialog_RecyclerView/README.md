### Dialog-RecyclerView
Exxample

```
	dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_dialog_search);

        final LinearLayout ll_search = (LinearLayout) dialog.findViewById(R.id.ll_search);
        ll_search.setVisibility(View.GONE);
        ll_result = (LinearLayout) dialog.findViewById(R.id.ll_result);

        RecyclerView recycler_result = (RecyclerView) dialog.findViewById(R.id.recycler_result);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Log.i(TAG, "show_dialog_result: "+recycler_result);
        recycler_result.setLayoutManager(mLayoutManager);
        recycler_result.setItemAnimator(new DefaultItemAnimator());
        mResultAdapter = new ResultAdapter(listResultCustomer, this);
        recycler_result.setAdapter(mResultAdapter);
        mResultAdapter.notifyDataSetChanged();
        dialog.show();
```

### Step1 
Tạo layout_dialog_search.xml
### Step2
Set Content `dialog.setContentView(R.layout.layout_dialog_search);` và ánh xạ phần tử giao diện

### Step3
Cài đặt Adapter chứa mảng hiển thị
