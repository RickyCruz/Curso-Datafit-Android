package mx.datafit.contactswithsoap.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.datafit.contactswithsoap.R;
import mx.datafit.contactswithsoap.models.Contact;

public class ContactAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Contact> items;

    public ContactAdapter(Activity activity, ArrayList<Contact> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.name   = (TextView)  convertView.findViewById(R.id.name);
            holder.phone  = (TextView)  convertView.findViewById(R.id.cellphone);
            holder.email  = (TextView)  convertView.findViewById(R.id.email);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = items.get(position);

        Picasso.with(activity)
                .load(contact.getAvatar())
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .into(holder.avatar);
        holder.name.setText(contact.getFullname());
        holder.phone.setText(contact.getCellphone());
        holder.email.setText(contact.getEmail());

        return convertView;
    }

    static class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView phone;
        TextView email;
    }

}