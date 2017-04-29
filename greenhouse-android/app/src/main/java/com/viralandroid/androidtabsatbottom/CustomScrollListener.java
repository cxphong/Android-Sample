package com.viralandroid.androidtabsatbottom;

import android.support.v7.widget.RecyclerView;

/**
 * Created by dinhtho on 08/04/2017.
 */

public class CustomScrollListener extends RecyclerView.OnScrollListener {

    private ScrollListener scrollListener;

    CustomScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                scrollListener.scrollendlistenter();

//                System.out.println("The RecyclerView is not scrolling");
                break;
//            case RecyclerView.SCROLL_STATE_DRAGGING:
//                System.out.println("Scrolling now");
//                break;
//            case RecyclerView.SCROLL_STATE_SETTLING:
//                System.out.println("Scroll Settling");
//                break;

        }

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        if(dx>0)
//        {
//            System.out.println("Scrolled Right");
//
//        }
//        else if(dx < 0)
//        {
//            System.out.println("Scrolled Left");
//
//        }
//        else {
//
//            System.out.println("No Horizontal Scrolled");
//        }
//
//        if(dy>0)
//        {
//            System.out.println("Scrolled Downwards");
//        }
//        else if(dy < 0)
//        {
//            System.out.println("Scrolled Upwards");
//
//        }
//        else {
//
//            System.out.println("No Vertical Scrolled");
//        }
    }


}
