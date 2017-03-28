As I mentioned above the library available at maven central repository. Add next dependency into gradle project file: 


###Step 1:
```
dependencies {
    compile 'com.github.d-max:spots-dialog:0.7@aar'
}
```

###Step 2:

 For example: 
```
AlertDialog dialog = new SpotsDialog(context);
dialog.show();
...
dialog.dismiss();
```

Note: The library requires minimum API level 15. 





