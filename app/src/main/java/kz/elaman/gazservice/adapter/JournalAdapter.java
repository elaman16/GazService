package kz.elaman.gazservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.elaman.gazservice.R;
import kz.elaman.gazservice.model.Indicate;
import kz.elaman.gazservice.utils.RecyclerItemClickListener;


/**
 * Created by Myrzabek on 27/03/2017.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.MyViewHolder> {

    private Context context;
    private List<Indicate> indicationList;
    private RecyclerView recyclerView;

    public JournalAdapter(Context context, List<Indicate> indicationList, RecyclerView recyclerView) {
        this.context = context;
        this.indicationList = indicationList;
        this.recyclerView = recyclerView;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal, parent, false);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {

                           /* Intent intent = new Intent(context, AdvertDetailActivity.class);
                            intent.putExtra(Constants.INTENT_SPECIALITY_ID, indicationList.get(position).getId());
                            context.startActivity(intent);*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Indicate advert = indicationList.get(position);
        holder.indicationDate.setText(advert.getDate());
        holder.indicationPrice.setText(advert.getPrice());
    }

    @Override
    public int getItemCount() {
        return indicationList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView indicationDate, indicationPrice;

        public MyViewHolder(View view){
            super(view);

            indicationDate = (TextView) view.findViewById(R.id.indicationDate);
            indicationPrice = (TextView) view.findViewById(R.id.indicationPrice);

        }
    }


}
