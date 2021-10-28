package fr.Reyden.e_tabs

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.ThemedSpinnerAdapter
import com.bumptech.glide.Glide
import fr.Reyden.e_tabs.adapter.EtabsAdapter

class etabsPopup(private val adapter: EtabsAdapter, private val currentetabs : etabsModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.etabs_tab)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupFavorisButton()
        setupautoButton()

    }

    private fun setupautoButton() {

    }


    private fun updateStar(button : ImageView){
        if(currentetabs.liked){
            button.setImageResource(R.drawable.etoilefavoris)
        }else{
            button.setImageResource(R.drawable.etoileblanche)
        }
    }



    private fun setupFavorisButton() {
        val startbutton = findViewById<ImageView>(R.id.star_button)
       updateStar(startbutton)

        //interaction
        startbutton.setOnClickListener{
            currentetabs.liked = !currentetabs.liked
            val repo = etabsRepository()
            repo.updateEtabs(currentetabs)
           updateStar(startbutton)
        }
    }

    private fun setupDeleteButton() {
        //findViewById<ImageView>(R.id.delete_button).setOnclickListener{
        // val repo = etabsRepository()
        // repo.deleteEtabs(currentetabs)
        //dismiss()
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer la fenètre
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l'image de la plante
        val etabsImage = findViewById<ImageView>(R.id.etabs_item)
        Glide.with(adapter.context).load(Uri.parse(currentetabs.imageurl)).into(etabsImage)

        //actualiser le titre
        findViewById<TextView>(R.id.popup_title).text = currentetabs.title

        //actualiser l'artist
        findViewById<TextView>(R.id.popup_artist).text = currentetabs.artist



    }


}