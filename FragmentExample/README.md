


## Installation

Create Fragment

### Call Fragment function
```
  public void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //Khi được goi, fragment truyền vào sẽ thay thế vào vị trí FrameLayout trong Activity chính
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
```
### Example
```
@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr1:
                callFragment(new Fragment1());
                break;
            case R.id.fr2:
                callFragment(new Fragment2());
                break;
        }

    }
```

