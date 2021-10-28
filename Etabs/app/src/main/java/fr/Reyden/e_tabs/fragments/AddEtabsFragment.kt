package fr.Reyden.e_tabs.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.widget.AlertDialogLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import fr.Reyden.e_tabs.MainActivity
import fr.Reyden.e_tabs.R
import fr.Reyden.e_tabs.etabsModel
import fr.Reyden.e_tabs.etabsRepository
import fr.Reyden.e_tabs.etabsRepository.Singleton.downloadUri
import java.util.*



class AddEtabsFragment(

    private val context: MainActivity

) : Fragment() {

    private var file: Uri? = null
    private var uploadedImage : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_etabs, container, false)

        //recupérer uploadedImage pour lui associé sont composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //recupérer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsque l'on clique dessus ça ouvre les images du téléphone
        pickupImageButton.setOnClickListener{ pickupImageButton() }

        //recupere le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        var loader = view.findViewById<ImageView>(R.id.loader)
        var loadersucces = view.findViewById<TextView>(R.id.text_success)

        confirmButton.setOnClickListener{

            sendForm(view)
            loader.setVisibility(View.VISIBLE)
            loadersucces.setVisibility(View.VISIBLE)



        }


        return view
    }



    private fun sendForm(view : View) {
        val repo = etabsRepository()
        repo.uploadImage(file!!){


            val etabsTitle = view.findViewById<EditText>(R.id.title_input).text.toString()
            val etabsArtist =  view.findViewById<EditText>(R.id.artist_input).text.toString()
            val etabsLike = false
            val etabsDifficult =  view.findViewById<Spinner>(R.id.difficult_spinner).selectedItem.toString()
            val dowloadImageUrl = downloadUri

            //Creer un nouvel objet de type etabsModel
            val etabs = etabsModel(
                UUID.randomUUID().toString(),
                etabsTitle,
                etabsArtist,
                dowloadImageUrl.toString(),
                etabsLike,
                etabsDifficult

            )

            // envoyer en bdd
            if(etabsTitle == "" || etabsTitle.isNullOrEmpty() )
            {
                view.findViewById<TextView>(R.id.warningTitle).isVisible
            }else if(etabsArtist == "" || etabsArtist.isNullOrEmpty())
            {
                view.findViewById<TextView>(R.id.warningArtist).isVisible
            }
            else{
                repo.insertEtabs(etabs)

                println("etabs crée")

            }


        }



    }

    private fun pickupImageButton()
    {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //Vérifier si les donnée receptionné sont nulles
            if(data == null || data.data == null) return

            //recupérer l'image qui à été selectionné
            file = data.data

            //------------------------
                /* A faire
                * scanner l'image afin de detecter automatiquement les accords de la musique */
            //-----------------------
            //mettre a jour l'aperçus de l'image
                uploadedImage?.setImageURI(file)



        }
    }


}