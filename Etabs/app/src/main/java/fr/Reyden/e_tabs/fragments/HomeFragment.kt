package fr.Reyden.e_tabs.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.Reyden.e_tabs.MainActivity
import fr.Reyden.e_tabs.R
import fr.Reyden.e_tabs.adapter.EtabsAdapter
import fr.Reyden.e_tabs.etabsModel
import fr.Reyden.e_tabs.etabsRepository.Singleton.etabsList

class HomeFragment(
    private val context:MainActivity
) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_home, container, false)



        //recuperer le recycler view
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = EtabsAdapter(context, etabsList.sortedBy{!it.liked}, R.layout.item_vertical_etabs)

        return view
    }


}