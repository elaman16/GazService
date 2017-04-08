package kz.elaman.gazservice.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import kz.elaman.gazservice.R;
import kz.elaman.gazservice.SendIndicateActivity;
import kz.elaman.gazservice.adapter.JournalAdapter;
import kz.elaman.gazservice.database.DBHelper;
import kz.elaman.gazservice.model.Indicate;
import kz.elaman.gazservice.utils.Constants;
import kz.elaman.gazservice.utils.PrefHelper;


public class JournalFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAG = "DATABASE";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public JournalFragment() {

    }


    public static JournalFragment newInstance(String param1, String param2) {
        JournalFragment fragment = new JournalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private DatabaseReference mDatabase;
    private DatabaseReference mCounters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCounters = mDatabase.child("Показания счетчика");
    }

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private JournalAdapter journalAdapter;
    private List<Indicate> indicates;

    private PrefHelper prefHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        setHasOptionsMenu(true);

        dbHelper = new DBHelper(getContext());
        prefHelper = new PrefHelper(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.journal_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        indicates = new ArrayList<>();
        journalAdapter = new JournalAdapter(getContext(), indicates, recyclerView);
        recyclerView.setAdapter(journalAdapter);

        checkDataWrite();

        getIndicatesFromDB();

        return view;
    }

    private void getIndicatesFromDB() {
        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(Constants.INDICATE_TABLE, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int dateColIndex = c.getColumnIndex("date");
            int priceColIndex = c.getColumnIndex("price");
            int indicateValueColIndex = c.getColumnIndex("indicate_value");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", date = " + c.getString(dateColIndex) +
                                ", price = " + c.getString(priceColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла

                Indicate indicate = new Indicate(c.getString(dateColIndex), c.getString(priceColIndex));
                indicates.add(indicate);

            } while (c.moveToNext());

            journalAdapter.notifyDataSetChanged();

        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        db.close();
    }

    private void checkDataWrite(){
        if (!prefHelper.isWritedDate()){
            setIndicates();
        }
    }

    private void setIndicates() {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        // 1 кубический метр стоит 24,044 тг
        cv.put("date", "Январь 2017");
        cv.put("price", "6072 тг.");
        cv.put("indicate_value", "00253");
        db.insert(Constants.INDICATE_TABLE, null, cv);

        cv.put("date", "Февраль 2017");
        cv.put("price", "4800 тг.");
        cv.put("indicate_value", "00453");
        db.insert(Constants.INDICATE_TABLE, null, cv);

        cv.put("date", "Март 2017");
        cv.put("price", "5904 тг.");
        cv.put("indicate_value", "00699");
        db.insert(Constants.INDICATE_TABLE, null, cv);

        cv.put("date", "Апрель 2017");
        cv.put("price", "4872 тг.");
        cv.put("indicate_value", "00902");
        db.insert(Constants.INDICATE_TABLE, null, cv);

        prefHelper.setWriteDate(true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_journal, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_send_indicate) {
            Intent intent = new Intent(getContext(), SendIndicateActivity.class);
            getActivity().startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
