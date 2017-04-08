package kz.elaman.gazservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import kz.elaman.gazservice.NewsActivity;
import kz.elaman.gazservice.R;
import kz.elaman.gazservice.model.NewsAdapter;


public class NewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {

    }


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "des";
    public static final String TIME = "time";
    public static final String IMG_URL = "img_url";
    ArrayList<HashMap<String,String>> data =  new ArrayList<HashMap<String, String>>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_news, container, false);


        HashMap<String, String> hm1 = new HashMap<String, String>();
        hm1.put(TITLE, "25 сентября 2009 года в АО \"НСК\" была создана Агентская сеть");
        hm1.put(DESCRIPTION, "Ее создание стало естественной необходимостью в части улучшения качества обслуживания наших Клиентов.");
        hm1.put(TIME, "14.03.2016");
        hm1.put(IMG_URL, "http://nsk.kz/imagine/news_icon/5-let-agentskoi-seti.jpg");

        HashMap<String, String> hm2 = new HashMap<String, String>();
        hm2.put(TITLE,"Х Ежегодный Чемпионат по мини-футболу ЕВРОБАК");
        hm2.put(DESCRIPTION," 13 сентября состоялся Х Ежегодный Чемпионат по мини-футболу ЕВРОБАК. Приняли участие 16 команд из них 4 - представители страховых компаний.");
        hm2.put(TIME, "11.03.2016");
        hm2.put(IMG_URL, "http://nsk.kz/imagine/news_icon/futbol.jpg");

        HashMap<String, String> hm3 = new HashMap<String, String>();
        hm3.put(TITLE,"«Эксперт РА Казахстан» подтвердил рейтинг надежности АО «Нефтяная страховая компания» на уровне А+");
        hm3.put(DESCRIPTION," Рейтинговое агентство «Эксперт РА Казахстан» подтвердил рейтинг надежности \n" +
                "АО «Нефтяная страховая компания» на уровне A+ «Очень высокий уровень надежности».");
        hm3.put(TIME, "08.03.2016");
        hm3.put(IMG_URL, "http://www.insurancejournal.com/wp-content/uploads/2014/01/Grade1-580x580.jpg");

        HashMap<String, String> hm4 = new HashMap<String, String>();
        hm4.put(TITLE,"НСК выплатила более 1,6 млрд. тенге за 6 мес. 2014 года");
        hm4.put(DESCRIPTION,"  По итогам шести месяцев 2014 года АО «НСК» выплатила своим клиентам 1,68 млрд. тенге в виде страховых выплат. Компания заняла первое место по размеру выплат в страховании грузов и авиаКАСКО.");
        hm4.put(TIME, "02.03.2016");
        hm4.put(IMG_URL, "http://www.economonitor.com/wp-content/uploads/2015/10/6355318323_4c41d3ef76_z.jpg");

        HashMap<String, String> hm5 = new HashMap<String, String>();
        hm5.put(TITLE, "«НСК» застраховала футбольный клуб «Астана» на 375 млн. тенге");
        hm5.put(DESCRIPTION, " Cтраховой защитой обеспечены 98 человек: футболисты (игроки основного состава, запасные), тренерский состав, администрация клуба, технические специалисты.");
        hm5.put(TIME, "22.02.2016");
        hm5.put(IMG_URL, "http://nsk.kz/download/images/d184d183d182d0b1d0bed0bbd18cd0bdd0b0d18f20d0bad0bed0bcd0b0d0bdd0b4d0b020d090d181d182d0b0d0bdd0b0_2014.jpg");

        data.add(hm1);
        data.add(hm2);
        data.add(hm3);
        data.add(hm4);
        data.add(hm5);

        NewsAdapter adapter = new NewsAdapter(getActivity(),data);

        ListView lvNews = (ListView) view.findViewById(R.id.lvNews);
        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appNews = new Intent(getActivity(), NewsActivity.class);
                appNews.putExtra("ID", position);
                startActivity(appNews);
            }
        });

        return view;
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
