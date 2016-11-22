package com.uqac.frenchies.izicoloc.activities.listecourses;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uqac.frenchies.izicoloc.R;

import java.util.List;

/**
 * Created by Dylan on 14/11/2016.
 */

public class CoursesAdapter extends ArrayAdapter<Course>
{
    //Creation de la liste des models à afficher
    public CoursesAdapter(Context context, List<Course> course)
    {
        super(context, 0, course);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Récupération du layout
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layoutcourses,parent, false);
        }

        //Initialisation d'une case
        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null)
        {
            viewHolder = new TweetViewHolder();
            viewHolder.produit = (TextView) convertView.findViewById(R.id.produit);
            viewHolder.quantite = (TextView) convertView.findViewById(R.id.nombreProd);
            viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Course course = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.produit.setText(course.getProduit());
        viewHolder.quantite.setText(course.getQuantite());
        viewHolder.check.setImageDrawable(new ColorDrawable(course.getColor()));

        return convertView;
    }

    private class TweetViewHolder
    {
        public TextView produit;
        public TextView quantite;
        public ImageView check;
    }
}
