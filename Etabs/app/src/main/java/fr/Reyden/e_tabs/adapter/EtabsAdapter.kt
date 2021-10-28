package fr.Reyden.e_tabs.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.Reyden.e_tabs.*


class EtabsAdapter(
    val context: MainActivity,
    private val etabsList:List<etabsModel>,
    private val layout: Int
) : RecyclerView.Adapter<EtabsAdapter.ViewHolder>() {

    //boite pour ranger tous les composants à controler
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){

     val etabsImage = view.findViewById<ImageView>(R.id.image_item)
     val etabsTitle:TextView? = view.findViewById(R.id.title_item)
     val etabsArtist:TextView? = view.findViewById(R.id.artist_item)
     val etabsDiff = view.findViewById<ImageView>(R.id.diff_item)
     val favoris = view.findViewById<ImageView>(R.id.liked_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_etabs, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        //recupere les info de la etabs en fonction de la position
        val currentetabs = etabsList[position]

        //recupere le repository
        val repo = etabsRepository()

        //utiliser glide pour recupere l'image a partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentetabs.imageurl)).into(holder.etabsImage)

        //mettre a jour titre
        holder.etabsTitle?.text = currentetabs.title

        //mettre a jour artist
        holder.etabsArtist?.text = currentetabs.artist





        //verifier si le etabs a été liker de la etabs

        if(currentetabs.liked)
        {
            holder.favoris.setImageResource(R.drawable.favoribis_left)
        }else{
            holder.favoris.setImageResource(R.drawable.pr)
        }

        //Rajouter une intéraction  sur le favoris

       holder.favoris.setOnClickListener{
            //inverser si le bt est like ou non
            currentetabs.liked = !currentetabs.liked

           //mettre a jour l'objet etabs
           repo.updateEtabs(currentetabs)
        }

        //Interaction lors du clique sur une etabs
        holder.itemView.setOnClickListener{
            // afficher la popup
            etabsPopup(this, currentetabs).show()
        }


        //verifier le niveau de difficultée :
        val currentDiff = currentetabs.etabsDiff
        if(currentDiff == "Simple")
        {
            holder.etabsDiff.setImageResource(R.drawable.p1)
            repo.updateEtabs(currentetabs)
        }else if(currentDiff == "Moyen")
        {
            holder.etabsDiff.setImageResource(R.drawable.pj)
            repo.updateEtabs(currentetabs)
        }
        else if(currentDiff == "Difficile")
        {
            holder.etabsDiff.setImageResource(R.drawable.po)
            repo.updateEtabs(currentetabs)

        }else if(currentDiff == "Hardcore")
        {
            holder.etabsDiff.setImageResource(R.drawable.ph)
            repo.updateEtabs(currentetabs)
        }
        else if(currentDiff == "Rien")
        {
            holder.etabsDiff.setImageResource(R.drawable.pr)
            repo.updateEtabs(currentetabs)
        }


    }


    override fun getItemCount(): Int = etabsList.size
}