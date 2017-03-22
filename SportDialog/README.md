As I mentioned above the library available at maven central repository. Add next dependency into gradle project file: 

dependencies {
    compile 'com.github.d-max:spots-dialog:0.7@aar'
}

Javadoc and sources archives are available also. Just use corresponding classifier (read more about classifiers here). 

SpotsDialog class is an inheritor of a AlertDialog class. You can use it just like simple AlertDialog. For example: 

AlertDialog dialog = new SpotsDialog(context);
dialog.show();
...
dialog.dismiss();

Note: The library requires minimum API level 15. 


Customize it

I tried to create the dialog highly customizable. For this purpose I used proper android way - styles. The dialog's default style extends Theme.DeviceDefault.Light.Dialog style. 

Also several custom attributes added for make dialog able to change such properties: 
Title text style
Title text value
Spots count
Spots color

Here are several code snippets which shows how to do that. 

Firstly, it's need to declare you own android style resource file. 

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Custom"
           parent="android:Theme.DeviceDefault.Dialog">
        <item name="DialogTitleAppearance">
            @android:style/TextAppearance.Medium
        </item>
        <item name="DialogTitleText">
            Updating…
        </item>
        <item name="DialogSpotColor">
            @android:color/holo_orange_dark
        </item>
        <item name="DialogSpotCount">
            4
        </item>
    </style>
</resources>

After that just pass style id into constructor. 

new SpotsDialog(context, R.style.Custom).show();

And you will see that dialog's appearance. 




If you need to change just a dialogs message, you can use constructor with custom message param: 

new SpotsDialog(context, "Downloading").show();

Unfortunately, this way of spots color customizing won't work on the pre-lollipop devices (because of this android issue). As workaround you can just override color in the resources. 

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="spots_dialog_color">
        @color/your_own_color
    </color>
</resources>




