package com.uqac.frenchies.izicoloc.activities.menus_semaine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;

import java.util.List;

/**
 * Created by Dylan on 01/12/2016.
 */

public class MenuAdapter extends ArrayAdapter<Menu>
{
    //Creation de la liste des models à afficher
    public MenuAdapter(Context context, List<Menu> menus)
    {
        super(context, 0, menus);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Récupération du layout
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_menu_jour,parent, false);
        }

        //Initialisation d'une case
        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null)
        {
            viewHolder = new TweetViewHolder();
            viewHolder.jour = (TextView) convertView.findViewById(R.id.jour);
            viewHolder.midi = (TextView) convertView.findViewById(R.id.menuMidi);
            viewHolder.soir = (TextView) convertView.findViewById(R.id.menuSoir);
            convertView.setTag(viewHolder);
        }

        Menu menu = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.jour.setText(menu.getJour());
        viewHolder.midi.setText(menu.getMidi());
        viewHolder.soir.setText(menu.getSoir());

        return convertView;
    }

    private class TweetViewHolder
    {
        public TextView jour;
        public TextView midi;
        public TextView soir;
    }
}
