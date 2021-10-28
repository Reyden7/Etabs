package fr.Reyden.e_tabs.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import fr.Reyden.e_tabs.MainActivity
import fr.Reyden.e_tabs.R
import fr.Reyden.e_tabs.etabsModel
import fr.Reyden.e_tabs.etabsRepository
import fr.Reyden.e_tabs.etabsRepository.Singleton.downloadUri
import java.util.*

class ChordsEtabsFragment(

    private val context: MainActivity

) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater?.inflate(R.layout.fragment_chords_etabs, container, false)
        return view


    }



}