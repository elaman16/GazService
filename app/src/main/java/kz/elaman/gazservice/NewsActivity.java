package kz.elaman.gazservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import kz.elaman.gazservice.utils.CircleIndicator;
import kz.elaman.gazservice.utils.DemoPagerAdapter;

public class NewsActivity extends AppCompatActivity {

    private List<String> pictures = new ArrayList<String>();
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.press_sentr_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Пресс центр");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView image_name = (TextView) findViewById(R.id.img_name);
        TextView description = (TextView) findViewById(R.id.description);
        ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);

        if (getIntent().getExtras().getInt("ID")==0)
        {

            pictures.add("http://nsk.kz/imagine/news_icon/5-let-agentskoi-seti.jpg");
            pictures.add("http://www.kz.all.biz/img/kz/pred/logo/11840.jpeg");

            image_name.setText("25 сентября 2009 года в АО \"НСК\" была создана Агентская сеть");

            description.setText(Html.fromHtml(getString(R.string.ag_set)));

        }

        if (getIntent().getExtras().getInt("ID")==1)
        {

            pictures.add("http://nsk.kz/imagine/news_icon/futbol.jpg");
            pictures.add("http://ffdo.com.ua/wp-content/uploads/161981.jpg");
            image_name.setText("Х Ежегодный Чемпионат по мини-футболу ЕВРОБАК");

            description.setText(Html.fromHtml(getString(R.string.futbol)));

        }

        if (getIntent().getExtras().getInt("ID")==2)
        {
            pictures.add("http://www.aktau-business.com/uploads/posts/2015-01/1421919681_reyting-ot-ekspert-ra-kazahstan-2014.png");
            pictures.add("http://www.insurancejournal.com/wp-content/uploads/2014/01/Grade1-580x580.jpg");
            image_name.setText("«Эксперт РА Казахстан» подтвердил рейтинг надежности АО «Нефтяная страховая компания» на уровне А+");

            description.setText(Html.fromHtml(getString(R.string.Aplus)));

        }

        if (getIntent().getExtras().getInt("ID")==3)
        {

            pictures.add("http://www.economonitor.com/wp-content/uploads/2015/10/6355318323_4c41d3ef76_z.jpg");
            pictures.add("http://im7.asset.yvimg.kz/userimages/gromaler/UGYN2cg3epWP4l78K6XB7Gwt9te9Ob.jpg");
            image_name.setText("НСК выплатила более 1,6 млрд. тенге за 6 мес. 2014 года");

            description.setText(Html.fromHtml(getString(R.string.money)));

        }

        if (getIntent().getExtras().getInt("ID")==4)
        {

            pictures.add("http://nsk.kz/download/images/d184d183d182d0b1d0bed0bbd18cd0bdd0b0d18f20d0bad0bed0bcd0b0d0bdd0b4d0b020d090d181d182d0b0d0bdd0b0_2014.jpg");
            pictures.add("http://tengrinews.kz/userdata/25bc36bb961f94117d4dbbd2559848e5.jpg");
            image_name.setText("«НСК» застраховала футбольный клуб «Астана» на 375 млн. тенге");
            description.setText(Html.fromHtml(getString(R.string.astanafk)));

        }



        viewpager.setAdapter(new DemoPagerAdapter(pictures));
        indicator.setViewPager(viewpager);
        viewpager.setCurrentItem(0);


        /* FAB */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+77072706854"));
                startActivity(intent);
            }
        });
    }

}
