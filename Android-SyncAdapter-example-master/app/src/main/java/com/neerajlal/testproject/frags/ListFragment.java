package com.neerajlal.testproject.frags;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.neerajlal.testproject.R;
import com.neerajlal.testproject.contentproviders.ScoreProvider;
import com.neerajlal.testproject.database.DBOperations;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    CursorAdapter adapter;
    String[] FROM_IDS = {DBOperations.DBConstants.COL_NAME, DBOperations.DBConstants.COL_SCORE};
    int[] TO_IDS = {android.R.id.text1, android.R.id.text2};

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listview = (ListView) view.findViewById(android.R.id.list);
        getActivity().getSupportLoaderManager().initLoader(1, null, this);
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, FROM_IDS, TO_IDS, 0);
        listview.setAdapter(adapter);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] PROJECTION = {DBOperations.DBConstants.COL_NAME, DBOperations.DBConstants.COL_SCORE};
        String selection = DBOperations.DBConstants.COL_DIRTY + " != ?";
        String[] selectionArgs = {"1"};
        return new CursorLoader(getActivity(), ScoreProvider.CONTENT_URI, PROJECTION, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }
}
