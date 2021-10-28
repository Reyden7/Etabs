package fr.Reyden.e_tabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.Reyden.e_tabs.fragments.HomeFragment
import fr.Reyden.e_tabs.fragments.AddEtabsFragment
import fr.Reyden.e_tabs.fragments.ChordsEtabsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        loadFragment(HomeFragment(this))
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //importer le fragment


        //importer la bottomnav

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.home_page -> {
                    loadFragment(HomeFragment(this))
                    return@setOnNavigationItemSelectedListener  true
                }
                R.id.add_page -> {
                    loadFragment(AddEtabsFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.chords_page -> {
                    loadFragment(ChordsEtabsFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false

            }

        }
        loadFragment(HomeFragment(this))


    }

    private fun loadFragment(fragment: Fragment) {

        // charger repository
        val repo = etabsRepository()

        //mettre a jour la liste de repo
        repo.updateData{
            //injecter le fragment dans la boite
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


}