package vtc.room.a101.week8homework3.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vtc.room.a101.week8homework3.R;
import vtc.room.a101.week8homework3.models.People;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    private final Context context;
    private List<People> list;

    public PeopleAdapter(final Context context, final List<People> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PeopleAdapter.PeopleViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.people_item, viewGroup, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PeopleAdapter.PeopleViewHolder holder, final int position) {
        People people = list.get(position);
        holder.nameText.setText(people.getName());
        holder.surnameText.setText(people.getSurname());
        holder.priceText.setText(people.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PeopleViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView surnameText;
        private final TextView priceText;
        private PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            surnameText = itemView.findViewById(R.id.surname_text);
            priceText = itemView.findViewById(R.id.price_text);
        }
    }
}
